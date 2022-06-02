package com.mustafa.grand.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mustafa.grand.models.PhotoModel;
import com.mustafa.grand.repository.GrandRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PhotosViewModel extends ViewModel {
    private static final String TAG = "photo viewmodel";
    private GrandRepository repository;

    MutableLiveData<List<PhotoModel>> photos=new MutableLiveData<>();

    @Inject
    public PhotosViewModel(GrandRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<PhotoModel>> getPhotos() {
        return photos;
    }

    public void getPhotosFromApi(int albumId){
        repository.getPhotos(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoModels -> {
                    photos.setValue(photoModels);
                },throwable -> {
                    Log.d(TAG,throwable.toString());
                });
    }
}
