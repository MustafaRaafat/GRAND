package com.mustafa.grand.network;

import com.mustafa.grand.models.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface GrandApiService {

    @GET("users/9")
    Observable<UserModel> getUser();
}
