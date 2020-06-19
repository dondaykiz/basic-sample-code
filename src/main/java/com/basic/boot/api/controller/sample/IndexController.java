package com.basic.boot.api.controller.sample;

import com.basic.boot.api.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
     * index.
     *
     * @return ApiResponse
     */
    @GetMapping(value = "/")
    public ApiResponse index() {
        logger.debug("INDEX_CONTROLLER");
        String[] participant = {"leo", "kiki", "eden"};
        String[] completion = {"eden", "kiki"};
        String answer = "";
        Map<String, Integer> map = new HashMap<>();
        for (String person : participant) map.put(person, map.getOrDefault(person, 0) + 1);
        for (String person : completion) map.put(person, map.get(person) - 1);
        logger.debug("MAP >>> " + map);
        for(String key : map.keySet()) {
            if(map.get(key) != 0) {
                logger.debug("RESULT >>> " + key);
            }
        }

        ApiResponse apiResponse = new ApiResponse();
        return apiResponse;
    }
}
