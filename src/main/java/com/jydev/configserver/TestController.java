package com.jydev.configserver;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RefreshScope
public class TestController {

    private final DiscoveryClient discoveryClient;

    @GetMapping("/local")
    public List<String> test(){
        return discoveryClient.getServices().stream().flatMap(id ->
                discoveryClient.getInstances(id).stream().filter(instance -> Arrays.stream(instance.getMetadata().getOrDefault("profile","default")
                        .trim().split(",")).toList().contains("local"))
        ).map(instance -> instance.getUri().toString()).toList();
    }

    @GetMapping("/dev")
    public List<String      > dev(){
        return discoveryClient.getServices().stream().flatMap(id ->
                discoveryClient.getInstances(id).stream().filter(instance -> Arrays.stream(instance.getMetadata().getOrDefault("profile","default")
                        .trim().split(",")).toList().contains("dev"))
        ).map(instance -> instance.getUri().toString()).toList();
    }
}
