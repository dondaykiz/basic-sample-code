package com.basic.boot.api.model.sample;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Sample 모델.
 *
 * @author yjkim@ntels.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sample {
    /**
     * Sample.
     */
    private String sample;

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "sample='" + sample + '\'' +
                '}';
    }
}
