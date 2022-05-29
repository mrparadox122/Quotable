package com.paradox.quotable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String Base_URL = "https://quotable.io/";

    private static Retrofit retrofit = null;

    public static ApiInterface getRetrofitClient()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                           .baseUrl(Base_URL)
                           .addConverterFactory(GsonConverterFactory.create())
                           .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
