package com.example.tvshows.utilities;

import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url){
        try {

            imageView.setAlpha(0f);
            Picasso.get().load(url).noFade().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    imageView.animate().alpha(300).alpha(1f).start();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }catch (Exception exception){

        }
    }
}
