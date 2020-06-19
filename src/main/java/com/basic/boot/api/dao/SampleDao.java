package com.basic.boot.api.dao;

import com.basic.boot.api.model.sample.Sample;

/**
 * SampleDao.
 *
 * @author yjkim@ntels.com
 */
public interface SampleDao {
    /**
     * Sample 조회.
     * @return 조회된 Sample 정보
     */
    Sample getSample();
}
