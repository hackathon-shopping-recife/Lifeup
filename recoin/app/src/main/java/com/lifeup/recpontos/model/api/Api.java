package com.lifeup.recpontos.model.api;


import com.lifeup.recpontos.model.domain.Compra;
import com.lifeup.recpontos.model.domain.Promo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("points")
    Call<Void> insertTicket(@Field("deviceToken") String deviceToken,
                               @Field("ticket") String texto_nota
                               );

    @FormUrlEncoded
    @POST("product")
    Call<List<Promo>> getPromo(@Field("category") String category);

    @GET("points")
    Call<List<Compra>> getCompras(@Query("deviceToken") String deviceToken);

}
