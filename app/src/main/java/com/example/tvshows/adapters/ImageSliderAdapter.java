package com.example.tvshows.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshows.R;
import com.example.tvshows.databinding.ItemContainerSliderImageBinding;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewrHolder>{

    private List<String> sliderImages;
    LayoutInflater layoutInflater;

    public ImageSliderAdapter(List<String> sliderImages) {
        this.sliderImages = sliderImages;
    }

    @NonNull
    @Override
    public ImageSliderViewrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater==null){
            layoutInflater=LayoutInflater.from(parent.getContext());
        }
        ItemContainerSliderImageBinding sliderImageBinding= DataBindingUtil.inflate(layoutInflater, R.layout.item_container_slider_image,parent,false);
        return new ImageSliderViewrHolder(sliderImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewrHolder holder, int position) {

        holder.bindSliderImages(sliderImages.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderImages.size();
    }

    static class ImageSliderViewrHolder extends RecyclerView.ViewHolder {

        private ItemContainerSliderImageBinding itemContainerSliderImageBinding;

        public ImageSliderViewrHolder (ItemContainerSliderImageBinding itemContainerSliderImageBinding){
            super(itemContainerSliderImageBinding.getRoot());

            this.itemContainerSliderImageBinding=itemContainerSliderImageBinding;

        }

        public void bindSliderImages(String imageUrl){

            itemContainerSliderImageBinding.setImageUrl(imageUrl);
        }
    }
}
