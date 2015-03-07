package ch.epfl.bbp.nlp.rabbit;

import static ch.epfl.bbp.nlp.rabbit.Rabbitify.RABBITIFY_HOME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.nlp.rabbit.Rabbitify.Mode;
import ch.epfl.bbp.uima.testutils.UimaTests;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitifyTest {

    public static final String TEST_QUEUE_ID = "test";

    @Test
    public void test() throws Exception {

        // delete existing 'test' channels
        deleteChannel(Rabbitify.getMasterToSlaveQueue(TEST_QUEUE_ID));
        deleteChannel(Rabbitify.getSlaveToMasterQueue(TEST_QUEUE_ID));

        File script = new File(RABBITIFY_HOME
                + "src/test/resources/test.pipeline");
        String docs = RABBITIFY_HOME + "src/test/resources/test_docs.txt";
        // inputFile, timeout
        String[] args = { docs, "1" };

        Rabbitify.run(script, args, Mode.sender, TEST_QUEUE_ID, 1);
        Rabbitify.run(script, args, Mode.slave, TEST_QUEUE_ID, 1);
        Rabbitify.run(script, args, Mode.receiver, TEST_QUEUE_ID, 1);
    }

    private void deleteChannel(String channelName)
            throws KeyManagementException, NoSuchAlgorithmException,
            URISyntaxException, IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(RabbitWriter.GUEST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        try {
            channel.queueDelete(channelName);
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());
            assertTrue(e.getCause().getMessage()
                    .indexOf("no queue '" + channelName) > -1);
        }
    }

    // @Test
    public void testSer() throws Exception {

        // serialize
        JCas jCas = UimaTests.getTokenizedTestCas();
        byte[] serialized = RabbitWriter.serialize(jCas.getCas());

        // deserialize
        JCas jCas2 = JCasFactory.createJCas();
        RabbitWriter.deserialize(jCas2.getCas(), serialized);

        assertEquals(jCas.getDocumentText(), jCas2.getDocumentText());
    }
}
