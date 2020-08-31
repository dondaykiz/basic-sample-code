package com.basic.boot.api.util;

import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RxSubscriberUtil<T> extends DisposableSubscriber<T> {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RxSubscriberUtil.class);

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
