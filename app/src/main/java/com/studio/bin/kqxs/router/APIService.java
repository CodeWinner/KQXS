package com.studio.bin.kqxs.router;

import com.studio.bin.kqxs.entity.KqxsEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface APIService {

    @GET("get-kqxs")
    Call<KqxsEntity> getKQXS();
}
