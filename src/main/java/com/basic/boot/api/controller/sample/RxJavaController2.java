package com.basic.boot.api.controller.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class RxJavaController2 {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RxJavaController2.class);

    @GetMapping(value = "rxSync")
    public String RxJavaSync() throws Exception {
        logger.debug("RX_SYNC_STARTED " + Thread.currentThread().getName());
        String threadName = Thread.currentThread().getName();

        Random random = new Random();
        int randomTime = random.nextInt(5000);
        return threadName;
    }

    @GetMapping(value = "rxAsync")
    public void RxJavaAsync() {
        logger.debug("RX_ASYNC_STARTED");
    }
}
