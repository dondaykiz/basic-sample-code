package com.basic.boot.api.util;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * RestTemplateMapUtil.
 *
 * @author yjkim@ntels.com
 */
@Component
public class RestTemplateUtil {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);
    /**
     * RestTemplate.
     */
    @Autowired
    RestTemplate restTemplate;

    public Map<String, Object> restTemplate(String url, HttpMethod httpMethod, HttpHeaders headers, Object body, String eventType) {
        long trId = System.currentTimeMillis();
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        Gson gson = new Gson();
        String jsonBody = gson.toJson(body);
        logger.debug("###[REST_REQUEST_INFO] >> " + eventType +" : trId={}, url={}, method={}, headers={}, body={}", trId, url, httpMethod, headers, jsonBody);
        try {
            ResponseEntity<Map> responseEntity = restTemplate.exchange(url, httpMethod, entity, Map.class);
            logger.debug("###[REST_RESPONSE_INFO] >> " + eventType +" : trId={}, statusCode={}, body={}", trId, responseEntity.getStatusCode(), responseEntity.getBody());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            logger.debug("###[REST_RESPONSE_ERROR_INFO] >> " + eventType +" : trId={}, statusCode={}, body={}", trId, e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            logger.debug("###[REST_EXCEPTION] >> " + eventType + " : trId={}, body={}", trId, e.getMessage());
            throw e;
        }
    }
}
