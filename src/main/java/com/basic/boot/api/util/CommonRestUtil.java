package com.basic.boot.api.util;

import com.yjk.dsb.api.exception.ExternalApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * CommonRestUtil.
 *
 * @author yjkim@ntels.com
 */
@Component
public class CommonRestUtil {
    /**
     * Logger.
     */
    private static Logger logger = LoggerFactory.getLogger(CommonRestUtil.class);

    @Autowired
    RestTemplate restTemplate;

    public Map<String, Object> restTemplate(String url, HttpMethod httpMethod, HttpHeaders headers, Map bodyMap,
                                            String callMethodName, Class dataType) {
        String fullUrl = url;
        HttpEntity<Object> entity = new HttpEntity<>(bodyMap, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity responseEntity = null;

        String responseType = null;
        if(dataType != null) {
            responseType = dataType.getSimpleName();
        }
        logger.debug("---------REST_" + callMethodName + "_REQUEST---------");
        logger.debug("REST_REQUEST_URL: " + fullUrl);
        logger.debug("REST_REQUEST_METHOD: " + httpMethod);
        logger.debug("REST_REQUEST_BODY: " + bodyMap);
        logger.debug("-----------------------------------------------------------------");

        Map<String, Object> resultMap = new HashMap<>();
        try {
            responseEntity = restTemplate.exchange(builder.toUriString(), httpMethod, entity, Map.class);
            logger.debug("---------REST_" + callMethodName + "_RESPONSE---------");
            logger.debug("REST_RESPONSE_STATUS_CODE: " + responseEntity.getStatusCode());
            logger.debug("REST_RESPONSE_BODY: " + responseEntity.getBody());
            logger.debug("-----------------------------------------------------------------");
            resultMap.put("resultData", responseEntity.getBody());

        } catch (HttpClientErrorException e) {
            logger.debug("---------REST_CLIENT_EXCEPTION_" + callMethodName + "_RESPONSE---------");
            logger.debug("REST_RESPONSE_STATUS_CODE: " + e.getStatusCode());
            logger.debug("REST_RESPONSE_BODY: " + e.getResponseBodyAsString());
            logger.debug("-----------------------------------------------------------------");
            throw new ExternalApiException(e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            logger.debug("---------REST_SERVER_EXCEPTION_" + callMethodName + "_RESPONSE---------");
            logger.debug("REST_RESPONSE_STATUS_CODE: " + e.getStatusCode());
            logger.debug("REST_RESPONSE_BODY: " + e.getResponseBodyAsString());
            logger.debug("-----------------------------------------------------------------");
            throw new ExternalApiException(e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.debug("---------REST_EXCEPTION_" + callMethodName + "_RESPONSE---------");
            logger.debug("REST_RESPONSE_BODY: " + e.getMessage());
            logger.debug("-----------------------------------------------------------------");
            throw new ExternalApiException(e.getMessage());
        }
        return resultMap;
    }
}
