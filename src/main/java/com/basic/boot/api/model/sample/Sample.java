package com.basic.boot.api.model.sample;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Sample 모델.
 *
 * @author yjkim@ntels.com
 */
@Document(collection = "ParkingSpot")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sample {
    /**
     * Sample.
     */
    @Id
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
