package com.basic.boot.api.controller.sample;

import com.basic.boot.api.exception.InvalidRequestException;
import com.basic.boot.api.model.ApiResponse;
import com.basic.boot.api.model.Test;
import com.basic.boot.api.util.CommonRestUtil;
import com.basic.boot.api.util.RestTemplateUtil;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * IndexController.
 *
 * @author yjkim@ntels.com
 */
@RestController
public class IndexController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    /**
     * CommonRestUtil.
     */
    @Autowired
    RestTemplateUtil restTemplateUtil;
    /**
     * index.
     *
     * @return ApiResponse
     */
    @GetMapping(value = "/")
    public ApiResponse index() throws Exception {
        logger.debug("INDEX_CONTROLLER");
        ApiResponse apiResponse = new ApiResponse();
        return apiResponse;
    }

    @PostMapping(value = "/test")
    public void test(@Validated @RequestBody Test test, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }
    }

    @GetMapping(value = "/test")
    public void test() throws Exception {
        Socket socket;
        logger.debug("SOCKET_CLIENT_STARTED");
        //socket = IO.socket("http://112.219.69.210:16819/api/event/receive?event=true");
        socket = IO.socket("http://localhost:8002/test");
        socket.connect();
        logger.debug("SOCKET: connected={}, id={} ", socket.connected(), socket.id());
        socket.close();
        logger.debug("SOCKET_CLOSE: connected={}, id={} ", socket.connected(), socket.id());
        socket.connect();
        logger.debug("SOCKET: connected={}, id={} ", socket.connected(), socket.id());

    }

    @GetMapping(value = "/rest")
    public void testRestTemplate() throws Exception {
        logger.debug("REST_REQUEST_STARTED : " + Thread.currentThread().getName());
        Map<String, Object> resultMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

        ResponseEntity<Map> responseEntity = restTemplate.exchange("http://localhost:8002/test", HttpMethod.GET, null, Map.class);
        logger.debug("###[REST_RESPONSE_INFO] >>  statusCode={}, body={}", responseEntity.getStatusCode(), responseEntity.getBody());
    }

    @GetMapping(value = "/rest2")
    public void testRestTemplate2() throws Exception {
        logger.debug("REST_REQUEST_STARTED : " + Thread.currentThread().getName());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap = restTemplateUtil.restTemplate("http://localhost:8002/test", HttpMethod.GET, null, null, "TEST");
    }
}
