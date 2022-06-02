package com.mustafa.grand.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mustafa.grand.R;
import com.mustafa.grand.databinding.FragmentAlbumDetailsScreenBinding;
import com.mustafa.grand.ui.adapters.PhotosAdapter;
import com.mustafa.grand.viewmodels.PhotosViewModel;


public class AlbumDetails extends Fragment {

    private FragmentAlbumDetailsScreenBinding binding;
    private PhotosViewModel photosViewModel;
    private PhotosAdapter photosAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAlbumDetailsScreenBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initialize the view model
        photosViewModel=new ViewModelProvider(getActivity()).get(PhotosViewModel.class);

        int albumId=AlbumDetailsArgs.fromBundle(getArguments()).getAlbumId();
        photosViewModel.getPhotosFromApi(albumId);

        photosAdapter=new PhotosAdapter(getContext());
        binding.photoRecycler.setAdapter(photosAdapter);

        photosViewModel.getPhotos().observe(getViewLifecycleOwner(),photoModels -> {
            photosAdapter.setData(photoModels);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}