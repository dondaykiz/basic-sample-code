package com.basic.boot.api.controller.sample;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RxJavaTest {
    private static final Logger logger = LoggerFactory.getLogger(RxJavaTest.class);

    public static void main(String[] args) {
        //rxJavaJust();
        //rxJavaArray();
        //rxJavaIterator();
        rxJavaFlatMap();
    }

    public static void rxJavaJust() {
        logger.debug("RXJAVA_JUST_STARTED");
        Observable.just("apple", "banana", "grape", "orange", "tomato")
                .subscribe(logger::debug);
    }

    public static void rxJavaArray() {
        logger.debug("RXJAVA_ARRAY_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .subscribe(logger::debug);
    }

    public static void rxJavaIterator() {
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

    public static void rxJavaFlatMap() {
        logger.debug("RXJAVA_FLATMAP_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .flatMap(data ->
                        Observable.fromCallable(() -> formatString(data))
                                .doOnNext(nextData -> logger.debug("DATA : {}, Thread: {}", nextData, Thread.currentThread().getName()))
                                .subscribeOn(Schedulers.io())
                )
                .subscribe();

        logger.debug("RXJAVA_FLATMAP_FINISHED");
    }

    public static String formatString(String data) {
        try {
            Thread.sleep(3000);
            return data + " + @";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
