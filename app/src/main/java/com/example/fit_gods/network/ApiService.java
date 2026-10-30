package com.example.fit_gods.network;

import com.example.fit_gods.model.dto.LoginRequest;
import com.example.fit_gods.model.dto.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest request);
}
