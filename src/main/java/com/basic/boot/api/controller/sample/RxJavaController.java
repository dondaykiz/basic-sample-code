package com.basic.boot.api.controller.sample;

import com.basic.boot.api.model.sample.Sample;
import com.basic.boot.api.util.CommonRestUtil;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author yjkim@ntels.com
 * BatchController.
 */
@RestController
public class RxJavaController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RxJavaController.class);

    @Autowired
    CommonRestUtil commonRestUtil;

    @GetMapping(value = "/rxJava")
    public void getRxJava() throws Exception {
        logger.debug("RxJava_STARTED >>> " + Thread.currentThread().getName());
        Observable.just(Thread.currentThread().getName())
                .subscribe(data -> logger.debug("DATA >>> " + data));
    }

    public static class CustomSubscriber<T> extends DisposableSubscriber<T> {
        @Override
        public void onNext(T t) {
            logger.debug("");
        }
        @Override
        public void onError(Throwable t) {
            logger.debug("");
        }
        @Override
        public void onComplete() {
            logger.debug("");
        }
    }
}
