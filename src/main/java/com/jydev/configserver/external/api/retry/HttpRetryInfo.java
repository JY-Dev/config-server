package com.jydev.configserver.external.api.retry;

public record HttpRetryInfo(
        int retryCount,
        String url
) {

    public static HttpRetryInfo fromUrl(String url,String path) {
        return new HttpRetryInfo(0, url+path);
    }

    public static HttpRetryInfo incrementRetryCount(HttpRetryInfo retryInfo){
        return new HttpRetryInfo(retryInfo.retryCount+1, retryInfo.url);
    }
}
