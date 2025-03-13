package com.example.perfectmovie;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestBuilder {
    private static String URL = "https://kinopoiskapiunofficial.tech/api/";
    private static Retrofit retrofit = null;

    static Retrofit buildRequest(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new Interceptor() {

                    @Override

                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                // .addHeader(Constant.Header, authToken)
                                .build();
                        return chain.proceed(request);
                    }
                }).build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client) // This line is important
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }
}
