package com.mustafa.grand.repository;

import com.mustafa.grand.models.UserModel;
import com.mustafa.grand.network.GrandApiService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GrandRepository {

    private GrandApiService apiService;

    @Inject
    public GrandRepository(GrandApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<UserModel> getUser(){
        return apiService.getUser();
    }
}
