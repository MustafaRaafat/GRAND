package com.mustafa.grand;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mustafa.grand.databinding.FragmentProfileScreenBinding;
import com.mustafa.grand.viewmodels.UserViewModel;

public class Profile extends Fragment {

    private FragmentProfileScreenBinding binding;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel=new ViewModelProvider(getActivity()).get(UserViewModel.class);
        userViewModel.getUserFromApi();
        userViewModel.getUser().observe(getViewLifecycleOwner(),userModel -> {
            binding.userName.setText(userModel.getName());
            binding.userAddress.setText(userModel.getAddress().getStreet());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}