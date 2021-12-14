package com.example.resultchecker;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.resultchecker.Constants.ADD_URL;

public class RetrofitClient {

    public static Api getApi(){
        HttpLoggingInterceptor interceptorObj = new HttpLoggingInterceptor();
        interceptorObj.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient clientObj = new OkHttpClient
                .Builder()
                .addInterceptor(interceptorObj)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ADD_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .client(clientObj).build();

        return retrofit.create(Api.class);
    }
}
