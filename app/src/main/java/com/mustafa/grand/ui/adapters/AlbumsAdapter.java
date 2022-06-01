package com.mustafa.grand.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mustafa.grand.R;
import com.mustafa.grand.models.AlbumsModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumHolder> {
    List<AlbumsModel> data = new ArrayList<>();

    public void setData(List<AlbumsModel> data) {
        this.data = data;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {
        TextView title;
        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.album_title);
        }
    }
}
