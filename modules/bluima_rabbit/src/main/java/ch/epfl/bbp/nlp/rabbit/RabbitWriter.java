package ch.epfl.bbp.nlp.rabbit;

import static ch.epfl.bbp.collections.Create.map;
import static com.rabbitmq.client.MessageProperties.PERSISTENT_BASIC;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.impl.Serialization;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitWriter extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(RabbitWriter.class);

    public final static boolean DURABLE = true;
    public static final String GUEST = "amqp://guest:nub6ga9nqwoinc@128.178.187.160:5672/pubmed";

    private Channel sendChannel;

    public static final String PARAM_AMQP_URI = "amqpUri";
    @ConfigurationParameter(name = PARAM_AMQP_URI, defaultValue = GUEST, mandatory = false,//
    description = "null for localhost, or amqpUri, amqp://userName:password@hostName:portNumber/")
    private final String amqpUri = null;

    public static final String PARAM_QUEUE = "queue";
    @ConfigurationParameter(name = PARAM_QUEUE, description = "")
    private final String queue = null;

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
            LOG.info(" [RabbitWriter] Connected to Rabbit");

            // setup channels
            sendChannel = connection.createChannel();
            Map<String, Object> args = map();
            args.put("x-max-length", 1000);
            sendChannel.queueDeclare(queue, DURABLE, false, false, args);

        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        try {
            sendChannel.basicPublish("", queue, PERSISTENT_BASIC,
                    serialize(jCas.getCas()));
//            LOG.debug(" [RabbitWriter] '" + snippetize(jCas.getDocumentText(), 20)
//                    + "'");
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        try {
            sendChannel.close();
        } catch (IOException e) {// nope
        }
    }

   

    public static void deserialize(CAS cas, byte[] byteArray)
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        ObjectInputStream in = new ObjectInputStream(bis);

        Serialization.deserializeCAS(cas, in);
        in.close();
    }

    public static byte[] serialize(CAS cas) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        Serialization.serializeWithCompression(cas, out);
        out.close();
        return bos.toByteArray();
    }
}
