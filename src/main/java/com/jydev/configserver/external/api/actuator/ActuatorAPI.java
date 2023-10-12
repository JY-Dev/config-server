package com.jydev.configserver.external.api.actuator;


import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ActuatorAPI {

    @POST
    Call<Void> refresh(@Url String url);
}
