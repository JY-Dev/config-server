package com.jydev.configserver.external.api.actuator;

import com.jydev.configserver.external.api.retry.HttpRetryInfo;
import com.jydev.configserver.external.api.retry.RetryCallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ActuatorService {

    private static final int RETRY_COUNT = 3;
    private static final String ACTUATOR_REFRESH = "/actuator/refresh";

    private final ActuatorAPI actuatorAPI;

    public void callRefresh(List<String> urls) {
        urls.stream()
            .map(url -> HttpRetryInfo.fromUrl(url, ACTUATOR_REFRESH))
            .forEach(this::request);
    }

    private void request(HttpRetryInfo retryInfo) {
        actuatorAPI.refresh(retryInfo.url())
                .enqueue(new RetryCallBack<>(RETRY_COUNT , retryInfo, this::request));
    }

}
