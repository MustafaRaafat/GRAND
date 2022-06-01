package com.mustafa.grand.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mustafa.grand.models.UserModel;
import com.mustafa.grand.repository.GrandRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class UserViewModel extends ViewModel {
    private static final String TAG = "viewmodel";
    private GrandRepository repository;

    MutableLiveData<UserModel> user = new MutableLiveData<>();

    @Inject
    public UserViewModel(GrandRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<UserModel> getUser() {
        return user;
    }

    public void getUserFromApi(){
        repository.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    user.setValue(userModel);
                },throwable -> {
                    Log.d(TAG,throwable.toString());
                });
    }
}
