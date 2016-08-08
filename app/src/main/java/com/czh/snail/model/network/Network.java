package com.czh.snail.model.network;

import com.czh.snail.model.network.apis.GankApi;
import com.czh.snail.utils.Constants;
import com.czh.snail.utils.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static final String TAG = "Network";
    private static GankApi gankApi;
    private static OkHttpClient okHttpClient = initOkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static GankApi getGankApi() {
        if (gankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASEURL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .client(okHttpClient)
                    .build();
            gankApi = retrofit.create(GankApi.class);
        }
        return gankApi;
    }



    public static OkHttpClient initOkHttpClient(){
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .build();
        return okHttpClient;
    }

    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            L.v(TAG, "request:" + request.toString());
//            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
//            long t2 = System.nanoTime();
//            L.v(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            L.i(TAG, "response body:" + content);
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }



}
