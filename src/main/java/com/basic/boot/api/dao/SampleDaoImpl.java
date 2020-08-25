package com.basic.boot.api.dao;

import com.basic.boot.api.model.sample.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * SampleDaoImpl.
 *
 * @author yjkim@ntels.com
 */
@Repository
public class SampleDaoImpl implements SampleDao {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(SampleDaoImpl.class);

    @Override
    public Sample getSample() {
        logger.debug("GET_SAMPLE_DAO_LOGIC");
        Sample sample = new Sample();
        sample.setSample("daoSample");
        return sample;
    }
}
