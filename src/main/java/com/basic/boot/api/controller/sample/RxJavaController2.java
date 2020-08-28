package com.basic.boot.api.controller.sample;

import com.basic.boot.api.util.RxSubscriberUtil;
import io.reactivex.rxjava3.core.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RxJavaController2 {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RxJavaController2.class);

    /**
     * RxSubscriberUtil.
     */
    @Autowired
    RxSubscriberUtil rxSubscriberUtil;

    @GetMapping(value = "just")
    public void rxJavaJust() throws Exception {
        logger.debug("RX_JUST_STARTED " + Thread.currentThread().getName());
        Observable.just("a", "b", "c", "d", "e")
                .doOnNext(data -> rxSubscriberUtil.onNext(data))
                .doOnComplete(() -> rxSubscriberUtil.onComplete())
                .doOnError(e -> rxSubscriberUtil.onError(new Exception()))
                .subscribe();
        logger.debug("RX_JUST_FINISHED");
    }

    @GetMapping(value = "fromArray")
    public String RxJavaArray() {
        logger.debug("RX_Array_STARTED");
        String[] rxArray = {"a","b","c","d","e"};
        Observable.fromArray(rxArray)
                .flatMap(data -> Observable.fromArray(data + "+ flatMap"))
                .subscribe(logger::debug);
        logger.debug("RX_ARRAY_FINISHED");
        String result = "RX_ARRAY_FINISHED";
        return result;
    }

    @GetMapping(value = "fromIter")
    public void RxJavaIterable() {
        logger.debug("RX_Iterable_STARTED");
        List list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        Observable.fromIterable(list)
                .doOnNext(data -> rxSubscriberUtil.onNext(data))
                .doOnComplete(() -> rxSubscriberUtil.onComplete())
                .doOnError(e -> rxSubscriberUtil.onError(new Exception()))
                .subscribe();
        logger.debug("RX_ITERABLE_FINISHED");
    }

    @GetMapping(value = "fromCallable")
    public void RxJavaCallable() {
        logger.debug("RX_CALLABLE_STARTED");
        String[] rxArray = {"1","2","3","4","5"};
        Observable.fromArray(rxArray)
                .flatMap(data ->
                        Observable.fromCallable(() -> RxJavaArray())
                        .doOnNext(result -> rxSubscriberUtil.onNext(result))
                )
                .subscribe(logger::debug);
        logger.debug("RX_CALLABLE_FINISHED");
    }
}
