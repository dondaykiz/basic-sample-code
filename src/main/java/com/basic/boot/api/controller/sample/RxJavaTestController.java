package com.basic.boot.api.controller.sample;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RxJavaTestController {
    private static final Logger logger = LoggerFactory.getLogger(RxJavaTestController.class);

    @GetMapping(value = "rxTest")
    public void rxJavaAsync() {
        logger.debug("RX_ASYNC_TEST_STARTED");
        String[] testArr = {"apple", "banana", "covid"};

        Observable.fromArray(testArr)
                .flatMap(data ->
                        Observable.fromCallable(() -> convertString(data))
                        .doOnNext(result -> logger.debug("THREAD >>> " + Thread.currentThread().getName()))
                        .subscribeOn(Schedulers.single())
                        .onErrorReturn(e -> {
                            e.printStackTrace();
                            return "EXCEPTION";
                        })
                )
                .subscribe(logger::debug);

        logger.debug("RX_ASYNC_TEST_FINISHED");

    }

    public String convertString(String data) throws Exception {
        Thread.sleep(5000);
        String testString = data + " + TEST";
        return testString;
    }

    public String convertString2(String data) throws Exception {
        String testString = data + " + ASYNC";
        return testString;
    }
}
