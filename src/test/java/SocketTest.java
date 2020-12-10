import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SocketTest {
    Socket socket;
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(SocketTest.class);
    @Test
    @GetMapping(value = "/socket")
    public void socketTest() {
        logger.debug("SOCKET_IO_STARTED");
        String vurixUrl = "http://localhost:8082";

        IO.Options options = new IO.Options();
        options.path = "/test";
        try {
            socket = IO.socket(vurixUrl, options);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.connect();
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                logger.debug("SOCKET_EVENT_CONNECTED: connected={}, message={}, id={} ", socket.connected(), objects, socket.id());
            }
        }).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                logger.debug("SOCKET: message={}", objects);

            }
        }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                logger.debug("SOCKET_EVENT_ERROR: status={}, error={}, id={} ", socket.connected(), objects, socket.id());

            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                logger.debug("SOCKET_EVENT_CONNECT_ERROR: status={}, error={}, id={} ", socket.connected(), objects, socket.id());
                socket.close();
            }
        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                logger.debug("SOCKET_EVENT_CONNECT_TIMEOUT: status={}, error={}, id={} ", socket.connected(), objects, socket.id());

            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                logger.debug("SOCKET_DISCONNECT: status={}, error={}. id={} ", socket.connected(), objects, socket.id());

            }
        });
        socket.io().on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Transport transport = (Transport)objects[0];
                transport.on(Transport.EVENT_REQUEST_HEADERS, new Emitter.Listener() {
                    @Override
                    public void call(Object... objects) {
                        @SuppressWarnings("unchecked")
                        Map<String, List<String>> headers = (Map<String, List<String>>)objects[0];
                        headers.put("x-auth-token", Arrays.asList("1234"));
                        headers.put("x-api-serial", Arrays.asList("apiSerial.toString()"));
                    }
                });
            }
        });
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
