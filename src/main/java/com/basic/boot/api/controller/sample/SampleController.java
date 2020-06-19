package com.basic.boot.api.controller.sample;

import com.basic.boot.api.exception.InvalidRequestException;
import com.basic.boot.api.model.ApiResponse;
import com.basic.boot.api.model.sample.Sample;
import com.basic.boot.api.service.SampleService;
import com.basic.boot.api.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * MessageUtil.
     */
    @Autowired
    private MessageUtil messageUtil;

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
            throw new InvalidRequestException("sample invalidRequestException");
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
}
