package com.basic.boot.api.service;

import com.basic.boot.api.dao.SampleDao;
import com.basic.boot.api.model.sample.Sample;
import com.basic.boot.api.util.CommonRestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * SampleServiceImpl.
 *
 * @author yjkim@ntels.com
 */
@Service
public class SampleServiceImpl implements SampleService {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);

    /**
     * SampleDao.
     */
    @Autowired
    SampleDao sampleDao;

    /**
     * CommonRestUtil.
     */
    @Autowired
    CommonRestUtil commonRestUtil;

    @Override
    public Sample getSample() {
        logger.debug("GET_SAMPLE_SERVICE_LOGIC");
        Sample sample = sampleDao.getSample();
        return sample;
    }

    @Override
    public void addSample() {

    }

    @Override
    public void modifySample() {

    }

    @Override
    public void removeSample() {

    }

    @Override
    public Map<String, Object> testClientSample() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap = commonRestUtil.restTemplate("http://localhost:8001/sample", HttpMethod.GET, null, null, "testClientSample", Map.class);
        return resultMap;
    }
}
