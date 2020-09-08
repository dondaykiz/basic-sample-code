package com.basic.boot.api.controller.sample;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class RxJavaTest {
    private static final Logger logger = LoggerFactory.getLogger(RxJavaTest.class);

    @GetMapping(value = "/rxjava")
    public void rxJava() {
        //rxJavaJust();
        //rxJavaArray();
        //rxJavaIterator();
        rxJavaFlatMap();
    }

    public void rxJavaJust() {
        logger.debug("RXJAVA_JUST_STARTED");
        Observable.just("apple", "banana", "grape", "orange", "tomato")
                .subscribe(logger::debug);
    }

    public void rxJavaArray() {
        logger.debug("RXJAVA_ARRAY_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .subscribe(logger::debug);
    }

    public void rxJavaIterator() {
        logger.debug("RXJAVA_ITERABLE_STARTED");
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("grape");
        list.add("orange");
        list.add("tomato");

        Observable.fromIterable(list)
                .subscribe(logger::debug);
    }

    public void rxJavaFlatMap() {
        logger.debug("RXJAVA_FLATMAP_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .flatMap(data ->
                        Observable.fromCallable(() -> formatString(data))
                                .doOnNext(nextData -> logger.debug("DATA : {}, Thread: {}", nextData, Thread.currentThread().getName()))
                                .subscribeOn(Schedulers.single())
                )
                .subscribe();

        logger.debug("RXJAVA_FLATMAP_FINISHED");
    }

    public String formatString(String data) {
        try {
            Random random = new Random();
            int sleep = random.nextInt(5000);
            Thread.sleep(sleep);
            return data + " + @";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
