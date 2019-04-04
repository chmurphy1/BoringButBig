package com.christopherwmurphy.boringbutbigapp.JsonUtil;

import com.christopherwmurphy.boringbutbigapp.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
/*
 * Implements Singleton Pattern in this class
 */

public class RetrofitUtil{

    private static Retrofit retrofitInstance;
    static {
        setRetrofitInstance();
    }

    private static void setRetrofitInstance(){
        if (retrofitInstance == null) {

            retrofitInstance = new retrofit2.Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
    }
    public static Retrofit getRetrofitInstance(){
        return retrofitInstance;
    }
}
