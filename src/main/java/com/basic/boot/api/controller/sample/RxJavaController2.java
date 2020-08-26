package com.basic.boot.api.controller.sample;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
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
    public void RxJavaSync() throws Exception {
        logger.debug("RX_SYNC_STARTED " + Thread.currentThread().getName());
        int i = 3;
        Observable.just("hello", "yjkim", 5, 6, 7, 8, 9, 10)
                .doOnNext(data -> new CustomSubscriber<>().onNext(data))
                .doOnComplete(() -> new CustomSubscriber<>().onComplete())
                .subscribe();
    }

    @GetMapping(value = "rxAsync")
    public void RxJavaAsync() {
        logger.debug("RX_ASYNC_STARTED");
    }

    public static class CustomSubscriber<T>  extends DisposableSubscriber<T> {
        /**
         * Logger.
         */
        private static final Logger logger = LoggerFactory.getLogger(CustomSubscriber.class);

        @Override
        public void onNext(T t) {
            logger.debug("onNext() >>> " + t);
        }

        @Override
        public void onComplete() {
            logger.debug("onComplete() >>> ");
        }

        @Override
        public void onError(Throwable t) {
            logger.debug("onError() >>> " + t);
        }
    }
}
