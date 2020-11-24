import io.socket.client.IO;
import io.socket.client.Socket;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SocketTest {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(SocketTest.class);
    @Test
    public void testSocketIO() {
        logger.debug("STARTED");
        Socket socket;
        try {
            socket = IO.socket("http://localhost:8001");
            socket.connect();
            logger.debug("CON : " + socket.connected());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSocket() throws Exception {
        java.net.Socket socket = new java.net.Socket("112.219.69.210", 16819);
        logger.debug("CON : " + socket.isConnected());
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String clientMessage = bufferedReader.readLine();
        logger.debug("RESULT >> " + clientMessage);
    }
}
