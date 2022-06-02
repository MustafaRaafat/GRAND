package com.mustafa.grand.network;

import com.mustafa.grand.models.AlbumsModel;
import com.mustafa.grand.models.PhotoModel;
import com.mustafa.grand.models.UserModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GrandApiService {

    @GET("users/6")
    Observable<UserModel> getUser();

    @GET("albums")
    Observable<List<AlbumsModel>> getAlbums(@Query("userId") int userId);

    @GET("photos")
    Observable<List<PhotoModel>> getPhotos(@Query("albumId") int albumId);
}
