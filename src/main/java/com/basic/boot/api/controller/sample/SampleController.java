package com.basic.boot.api.controller.sample;

import com.basic.boot.api.exception.InvalidRequestException;
import com.basic.boot.api.model.ApiResponse;
import com.basic.boot.api.model.sample.Sample;
import com.basic.boot.api.service.SampleService;
import com.basic.boot.api.util.MessageUtil;
import com.google.gson.Gson;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SampleController.
 *
 * @author yjkim@ntels.com
 */
@RestController
@RequestMapping(headers = "Accept=application/json")
public class SampleController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    SampleService sampleService;

    Socket socket;

    /**
     * MessageUtil.
     */
    @Autowired
    private MessageUtil messageUtil;

    /**
     * 발급받은 인증 토큰.
     */
    private String authToken = "1234";

    /**
     * Sample 조회.
     *
     * @return ApiResponse
     */
    @GetMapping(value = "/sample")
    public ApiResponse getSample() {
        Sample sample = sampleService.getSample();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResultData(sample);
        return apiResponse;
    }

    /**
     *
     * @param sample Sample 정보.
     * @return ApiResponse
     */
    @PostMapping(value = "/sample")
    public ApiResponse addSample(@Validated @RequestBody Sample sample, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException();
        }
        sampleService.addSample();
        ApiResponse apiResponse = new ApiResponse();
        return apiResponse;
    }

    /**
     * Sample 수정.
     */
    @PutMapping(value = "/sample")
    public void modifySample(@RequestBody Sample sample) {
        if (sample.getSample() == null || sample.getSample().equals("")) {
            throw new InvalidRequestException("sample1 invalidRequestException");
        }
        sampleService.modifySample();
    }

    /**
     * Sample 삭제.
     */
    @DeleteMapping(value = "/sample")
    public void removeSample() {
        sampleService.removeSample();
    }

    /**
     * Test Client Code.
     * @return ApiResponse
     */
    @GetMapping(value = "/api/sample")
    public ApiResponse testClientSample() {
        Map<String, Object> resultMap = sampleService.testClientSample();
        logger.debug("resultMap >>> " + resultMap.get("resultData"));
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResultData(resultMap);
        return apiResponse;
    }

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
        logger.debug("###SOCKET_STATUS: connected={}, id={} ", socket.connected(), socket.id());
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
        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                logger.debug("SOCKET_EVENT_CONNECT_TIMEOUT: status={}, error={}, id={} ", socket.connected(), objects, socket.id());

            }
        }).on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                logger.debug("SOCKET_RECONNECT: status={}, error={}. id={} ", socket.connected(), objects, socket.id());
            }
        }).on(Socket.EVENT_RECONNECT_ATTEMPT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                logger.debug("SOCKET_RECONNECT_ATTEMPT: status={}, error={}. id={} ", socket.connected(), objects, socket.id());
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                logger.debug("SOCKET_DISCONNECT: status={}, error={}. id={} ", socket.connected(), objects, socket.id());
                authToken = "12345";
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
                        headers.put("x-auth-token", Arrays.asList(authToken));
                        headers.put("x-api-serial", Arrays.asList("apiSerial.toString()"));
                    }
                });
            }
        });
    }
}
