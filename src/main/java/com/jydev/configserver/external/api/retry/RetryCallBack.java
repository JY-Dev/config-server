package com.jydev.configserver.external.api.retry;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public record RetryCallBack<T>(
        int retryCount,
        HttpRetryInfo retryInfo,
        RetryRequestInterface retryRequest
) implements Callback<T> {

    @Override
    public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
        if (!response.isSuccessful()) {
            retryRequest(retryInfo);
        }
    }

    @Override
    public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
        retryRequest(retryInfo);
    }

    private void retryRequest(HttpRetryInfo retryInfo){
        if (retryInfo.retryCount() < retryCount) {
            retryRequest.retry(HttpRetryInfo.incrementRetryCount(retryInfo));
        }
    }
}
