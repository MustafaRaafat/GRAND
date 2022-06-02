package com.mustafa.grand.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.mustafa.grand.R;
import com.mustafa.grand.models.PhotoModel;
import com.mustafa.grand.ui.photos.AlbumDetailsDirections;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosHolder> {
    List<PhotoModel> data = new ArrayList<>();
    Context context;

    public PhotosAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PhotoModel> data) {
        this.data = data;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public PhotosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotosHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosHolder holder, int position) {
        Picasso.get()
                .load(data.get(position).getThumbnailUrl())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(view -> {
//            show photo in full screen
            NavDirections directions = AlbumDetailsDirections
                    .actionAlbumDetailsToFullScreenImage(data.get(position).getUrl());
            Navigation.findNavController(view).navigate(directions);
        });
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public class PhotosHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PhotosHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo_item);
        }
    }
}
