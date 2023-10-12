package com.jydev.configserver.api;

import com.jydev.configserver.external.api.actuator.ActuatorService;
import com.jydev.configserver.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RefreshScope
public class ConfigController {
    private static final String META_DATA_PROFILE_KEY = "profile";
    private static final String META_DATA_PROFILE_ALLOW_ALL = "all";
    private static final String META_DATA_DEFAULT_KEY = "default";


    @Value("${spring.application.name}")
    private String data;

    private final DiscoveryClient discoveryClient;

    private final ActuatorService actuatorService;

    @PostMapping("/{profile}")
    public void refreshConfig(@PathVariable String profile) {
        List<String> urls = discoveryClient.getServices()
                .stream()
                .flatMap(serviceId -> discoveryClient.getInstances(serviceId).stream())
                .filter(instance -> !isConfigServer(instance.getServiceId()) && isAllowAllProfile(profile) || extractActiveProfiles(instance).contains(profile))
                .map(instance -> instance.getUri().toString())
                .toList();

        actuatorService.callRefresh(urls);
    }

    private boolean isConfigServer(String serviceId){
        return data.toUpperCase().equals(serviceId);
    }

    private boolean isAllowAllProfile(String profile){
        return META_DATA_PROFILE_ALLOW_ALL.equals(profile);
    }

    private List<String> extractActiveProfiles(ServiceInstance instance) {
        String profilesString = instance.getMetadata()
                .getOrDefault(META_DATA_PROFILE_KEY, META_DATA_DEFAULT_KEY);

        return Arrays.stream(StringUtil.removeAllWhiteSpace(profilesString)
                .split(","))
                .toList();
    }
}
