package com.jydev.configserver.external.api.config;

import com.jydev.configserver.external.api.actuator.ActuatorAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

@Configuration
public class RetrofitConfig {

    @Bean
    public Retrofit retrofit() {
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        return new Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .client(httpClient)
                .build();
    }

    @Bean
    public ActuatorAPI actuatorAPI() {
        return retrofit().create(ActuatorAPI.class);
    }
}
