package com.basic.boot.api.controller.sample;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class RxJavaTestController {
    private static final Logger logger = LoggerFactory.getLogger(RxJavaTestController.class);

    @GetMapping(value = "rxTest")
    public void rxJavaAsync() {
/*        ExecutorService sendPool = Executors.newFixedThreadPool(2);
        Scheduler sendScheduler = Schedulers.from(sendPool);*/
        logger.debug("RX_ASYNC_TEST_STARTED");
        String[] testArr = {"apple", "banana", "covid", "double"};

        Observable.fromArray(testArr)
/*                .map(data -> convertString(data))
                .doOnNext(result -> logger.debug("THREAD >>> " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io())*/

                .flatMap(data ->
                        //Observable.just(convertString(data))
                        Observable.fromCallable(() -> convertString(data))
                                .doOnNext(result -> logger.debug("THREAD >>> " + Thread.currentThread().getName()))
                                .subscribeOn(Schedulers.io())
                                .onErrorReturn(e -> {
                                    e.printStackTrace();
                                    return "EXCEPTION";
                                })
                )
                .subscribe(logger::debug);

/*        Observable.fromArray(testArr)
                .map(data -> convertString(data))
                .doOnNext(result -> logger.debug("THREAD1 >>> " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.newThread())
                .subscribe(logger::debug);*/
        logger.debug("RX_ASYNC_TEST_FINISHED");
    }

    public String convertString(String data) throws Exception {
        Random random = new Random();
        int timer = random.nextInt(5000);
        Thread.sleep(3000);
        String testString = data + " + TEST";
        return testString;
    }
}
