package com.basic.boot.api.model;

import javax.validation.constraints.NotEmpty;

public class Test {
    /**
     * 이벤트 시간.
     */
    @NotEmpty
    private String event_time;

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }
}
