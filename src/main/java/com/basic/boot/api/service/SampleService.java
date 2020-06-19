package com.basic.boot.api.service;

import com.basic.boot.api.model.sample.Sample;

import java.util.Map;

/**
 * SampleService.
 *
 * @author yjkim@ntels.com
 */
public interface SampleService {
    /**
     * Sample 조회.
     * @return 조회된 Sample 정보
     */
    Sample getSample();
    /**
     * Sample 등록.
     */
    void addSample();
    /**
     * Sample 수정.
     */
    void modifySample();
    /**
     * Sample 삭제.
     */
    void removeSample();

    Map<String, Object> testClientSample();
}
