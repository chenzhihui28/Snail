// (c)2016 Flipboard Inc, All Rights Reserved.

package com.czh.snail.model.network.apis;


import com.czh.snail.model.beans.GankResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GankApi {
//    @GET("data/福利/{number}/{page}")
//    Observable<GankBeautyResult> getBeauties(@Path("number") int number, @Path("page") int page);

    @GET("data/福利/{number}/{page}")
    Observable<GankResult> getBeauties(@Path("number") int number, @Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankResult> getKnowledge(@Path("year") int year, @Path("month") int month, @Path("day") int day);
}
