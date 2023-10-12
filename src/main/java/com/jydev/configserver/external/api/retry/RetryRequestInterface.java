package com.jydev.configserver.external.api.retry;

@FunctionalInterface
public interface RetryRequestInterface {
    void retry(HttpRetryInfo httpRetryInfo);
}