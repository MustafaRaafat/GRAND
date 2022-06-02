package com.mustafa.grand.repository;

import com.mustafa.grand.models.AlbumsModel;
import com.mustafa.grand.models.PhotoModel;
import com.mustafa.grand.models.UserModel;
import com.mustafa.grand.network.GrandApiService;

import java.util.List;

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

    public Observable<List<AlbumsModel>> getAlbums(int userId){
        return apiService.getAlbums(userId);
    }

    public Observable<List<PhotoModel>> getPhotos(int albumId){
        return apiService.getPhotos(albumId);
    }
}
