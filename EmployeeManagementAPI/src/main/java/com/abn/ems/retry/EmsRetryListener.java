package com.abn.ems.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

/**
 * Custom Retry Listener that provides hooks for handling retry events.
 *
 * <p>This class implements the {@link RetryListener} interface, which is part of Spring Retry.
 * The listener provides callbacks for events during the retry process, such as when a retry
 * attempt is about to start, when an attempt fails, or when a recovery attempt is made after
 * the retry limit is exceeded. You can use this listener to perform custom logic during the
 * retry process, such as logging retry attempts, updating counters, or performing any necessary
 * actions based on retry status.</p>
 *
 * <p>This listener can be registered to track retry operations and can be particularly useful
 * when you need to monitor retry counts, handle failure scenarios, or log retry events.</p>
 */
@Component
@Slf4j
public class EmsRetryListener implements RetryListener {

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        if (context.getRetryCount() > 0) {
            log.info("Retryable Count: {} Exception {}", context.getRetryCount(), throwable.toString());
        }
    }
}
