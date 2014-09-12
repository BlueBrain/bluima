package ch.epfl.bbp.nlp.rabbit;

import static ch.epfl.bbp.StringUtils.snippetize;
import static ch.epfl.bbp.nlp.rabbit.RabbitWriter.DURABLE;
import static ch.epfl.bbp.nlp.rabbit.RabbitWriter.GUEST;

import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * Allow to read Pubmed articles based on PubMed ids from Rabbit
 * 
 * @author renaud.richardet@epfl.ch
 */
public class RabbitReader extends JCasCollectionReader_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(RabbitReader.class);

    public static final String PARAM_AMQP_URI = "amqpUri";
    @ConfigurationParameter(name = PARAM_AMQP_URI, defaultValue = GUEST, mandatory = false,//
    description = "null for localhost, or amqpUri, amqp://userName:password@hostName:portNumber/")
    private String amqpUri;

    public static final String PARAM_QUEUE = "queue";
    @ConfigurationParameter(name = PARAM_QUEUE, description = "")
    private String queue;

    public static final String PARAM_TIMEOUT = "timeout";
    @ConfigurationParameter(name = PARAM_TIMEOUT, mandatory = false, defaultValue = "10",//
    description = "how long to wait (in seconds) before exiting the queue")
    private int timeout;

    private Channel receiveChannel;
    private QueueingConsumer consumer;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        try {
            // setup connection
            ConnectionFactory factory = new ConnectionFactory();
            if (amqpUri == null) {
                factory.setHost("localhost");
            } else {
                factory.setUri(amqpUri);
            }
            Connection connection = factory.newConnection();
            LOG.info(" [RabbitReader] connected to Rabbit");

            // receiving
            // setup channels
            receiveChannel = connection.createChannel();
            receiveChannel.queueDeclare(queue, DURABLE, false, false, null);
            receiveChannel.basicQos(1); // max 1 msg at a time to each slave
            // receiving
            consumer = new QueueingConsumer(receiveChannel);
            receiveChannel.basicConsume(queue, false, consumer);
            LOG.debug(" [RabbitReader] Waiting for messages...");

        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    /** contains serialized version of message */
    private byte[] nextDelivery;
    /** to acknowledge message in getNext() */
    private long deliveryTag;

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {
        try {
            RabbitWriter.deserialize(jCas.getCas(), nextDelivery);
            LOG.debug(" [Reader] '" + snippetize(jCas.getDocumentText(), 20)
                    + "'");
            receiveChannel.basicAck(deliveryTag, false);

        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {

        try {
            Delivery d = consumer.nextDelivery(timeout * 1000);
            if (d == null) {
                LOG.debug(" [RabbitReader] timout, exiting reader!");
                return false;
            }
            nextDelivery = d.getBody();
            deliveryTag = d.getEnvelope().getDeliveryTag();
            return true;

        } catch (InterruptedException ie) {
            LOG.debug(" [RabbitReader] timout2, exiting reader!");
            return false;
        } catch (Exception e) {
            throw new CollectionException(e);
        }
    }

    @Override
    public Progress[] getProgress() {// nope
        return null;
    }

    @Override
    public void close() throws IOException {
        try {
            receiveChannel.close();
        } catch (Exception e) {// nope
        }
    }
}