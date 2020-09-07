package com.basic.boot.api.controller.sample;
import com.basic.boot.api.service.BlobService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class RxJavaTest2 {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RxJavaTest2.class);

    @Autowired
    BlobService blobService;

    @GetMapping(value = "rxjava")
    public void rxJava() {
        //rxJavaJust();
        //rxJavaArray();
        //rxJavaIterator();
        //rxJavaMap();
        //rxJavaFlatMap();
        //uploadBlob();
    }

    @GetMapping(value = "just")
    public void rxJavaJust() {
        logger.debug("RXJAVA_JUST_STARTED");
        Observable.just("apple", "banana", "grape", "orange", "tomato")
                .doOnNext(nextData -> logger.debug("DATA : {}, Thread: {}", nextData, Thread.currentThread().getName()))
                .subscribe(logger::debug);
        logger.debug("RXJAVA_JUST_FINISHED");
    }

    @GetMapping(value = "array")
    public void rxJavaArray() {
        logger.debug("RXJAVA_ARRAY_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .subscribe(logger::debug);
        logger.debug("RXJAVA_ARRAY_FINISHED");
    }

    @GetMapping(value = "list")
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
        logger.debug("RXJAVA_ITERABLE_FINISHED");
    }

    @GetMapping(value = "flatMap")
    public void rxJavaFlatMap() {
        logger.debug("RXJAVA_FLATMAP_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .flatMap(data ->
                        Observable.fromCallable(() -> formatString(data))
                                .doOnNext(nextData -> logger.debug("DATA : {}, Thread: {}", nextData, Thread.currentThread().getName()))
                                .subscribeOn(Schedulers.io())
                )
                .subscribe(logger::debug);

        logger.debug("RXJAVA_FLATMAP_FINISHED");
    }

    @GetMapping(value = "map")
    public void rxJavaMap() {
        logger.debug("RXJAVA_MAP_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .map(data -> formatString(data))
                .doOnNext(nextData -> logger.debug("DATA : {}, Thread: {}", nextData, Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io())
                .subscribe(logger::debug);
        logger.debug("RXJAVA_MAP_FINISHED");
    }

    @GetMapping(value = "upload")
    public void uploadBlob() {
        logger.debug("BLOB_UPLOAD_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .flatMap(data ->
                        Observable.fromCallable(() -> blobService.uploadBlob(data))
                                .subscribeOn(Schedulers.io())
                                .onErrorReturn(e -> {
                                        e.printStackTrace();
                                        return "EXCEPTION_TEST";
                                })
                )
                .subscribe(result -> logger.debug("RESULT >> " + result));
        logger.debug("BLOB_UPLOAD_FINISHED");
    }

    public String formatString(String data) {
        try {
            Random random = new Random();
            int sleep = random.nextInt(4000);
            Thread.sleep(sleep);
            return data + " + @";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
