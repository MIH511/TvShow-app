package com.example.tvshows.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tvshows.R;
import com.example.tvshows.adapters.EpisodesAdapter;
import com.example.tvshows.adapters.ImageSliderAdapter;
import com.example.tvshows.databinding.ActivityMainBinding;
import com.example.tvshows.databinding.ActivityTvShowDetailsBinding;
import com.example.tvshows.databinding.LayoutEpisodesBottomSheetBinding;
import com.example.tvshows.models.TvShow;
import com.example.tvshows.models.TvShowDetails;
import com.example.tvshows.responses.TvShowDetailsResponse;
import com.example.tvshows.responses.TvShowResponse;
import com.example.tvshows.utilities.TempDataHolder;
import com.example.tvshows.viewmodels.TvShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;
import java.util.Locale;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TvShowDetailsActivity extends AppCompatActivity {

    private ActivityTvShowDetailsBinding tvShowDetailsBinding;
    private TvShowDetailsViewModel tvShowDetailsViewModel;
    private ImageSliderAdapter imageSliderAdapter;
    private BottomSheetDialog episodesBottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;
    private TvShow tvShow;
    private Boolean isTvShowAvailableInWatchlist=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvShowDetailsBinding= DataBindingUtil.setContentView(this,R.layout.activity_tv_show_details);

        doInitialization();
    }

    private void doInitialization(){
        tvShowDetailsViewModel=new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        tvShowDetailsBinding.ImageBack.setOnClickListener(view -> onBackPressed());
        tvShow= (TvShow) getIntent().getSerializableExtra("tvShow");
        checkTvShowInWatchList();
        getTvShowDetails();
    }

    private void checkTvShowInWatchList(){
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(tvShowDetailsViewModel.getTVShowFromWatchlist(String.valueOf(tvShow.getId()))
        .observeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(tvShow1 -> {
            isTvShowAvailableInWatchlist=true;

            tvShowDetailsBinding.imagewatchlist.setImageResource(R.drawable.ic_added);
            compositeDisposable.dispose();
        }));
    }

    private void getTvShowDetails(){
        tvShowDetailsBinding.setIsLoading(true);

        String tvShowId=String.valueOf(tvShow.getId());

        tvShowDetailsViewModel.getTvShowDetails(tvShowId).observe(this, new Observer<TvShowDetailsResponse>() {
            @Override
            public void onChanged(TvShowDetailsResponse tvShowDetailsResponse) {
                tvShowDetailsBinding.setIsLoading(false);
                if(tvShowDetailsResponse.getTvShow()!=null){

                    loadImageSlider(tvShowDetailsResponse.tvShow.getPictures());
                    loadData(tvShowDetailsResponse);


                }


            }
        });
    }

    private void loadData(TvShowDetailsResponse tvShowDetailsResponse) {
        tvShowDetailsBinding.setTvShowImageUrl(tvShowDetailsResponse.getTvShow().imagePath);
        tvShowDetailsBinding.setNetworkCountry(tvShowDetailsResponse.getTvShow().network+" ("+tvShowDetailsResponse.getTvShow().country+")");
        tvShowDetailsBinding.setTvShowName(tvShowDetailsResponse.getTvShow().name);
        tvShowDetailsBinding.setStartDate(tvShowDetailsResponse.getTvShow().startDate);
        tvShowDetailsBinding.setStatus(tvShowDetailsResponse.getTvShow().status);
        tvShowDetailsBinding.setDescription(String.valueOf(HtmlCompat.fromHtml(
                tvShowDetailsResponse.getTvShow().description,HtmlCompat.FROM_HTML_MODE_LEGACY
        )));
        tvShowDetailsBinding.setRating(String.format(Locale.getDefault(),"%.2f",
                Double.parseDouble(tvShowDetailsResponse.getTvShow().rating)));

        if(tvShowDetailsResponse.getTvShow().getGenres()!=null){
            tvShowDetailsBinding.setGenre(tvShowDetailsResponse.getTvShow().getGenres().get(0));
        }
        else {
            tvShowDetailsBinding.setGenre("N/A");
        }

        tvShowDetailsBinding.setRuntime(tvShowDetailsResponse.getTvShow().runtime.toString()+" Min");

        tvShowDetailsBinding.imageTvShow.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.textName.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.textStarted.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.textDescription.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);


        tvShowDetailsBinding.buttonWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(tvShowDetailsResponse.getTvShow().getUrl()));
                startActivity(intent);
            }
        });
        tvShowDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.buttonEpisodes.setVisibility(View.VISIBLE);

        tvShowDetailsBinding.textReadMore.setOnClickListener(view -> {

            if(tvShowDetailsBinding.textReadMore.getText().toString().equals("read more"))
            {
                tvShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                tvShowDetailsBinding.textDescription.setEllipsize(null);
                tvShowDetailsBinding.textReadMore.setText(R.string.read_less);
            }
            else{
                tvShowDetailsBinding.textDescription.setMaxLines(4);
                tvShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                tvShowDetailsBinding.textReadMore.setText(R.string.read_more);

            }
        });

        tvShowDetailsBinding.buttonEpisodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(episodesBottomSheetDialog==null){

                    episodesBottomSheetDialog=new BottomSheetDialog(TvShowDetailsActivity.this);

                    layoutEpisodesBottomSheetBinding=DataBindingUtil.inflate(LayoutInflater.from(TvShowDetailsActivity.this)
                    , R.layout.layout_episodes_bottom_sheet,findViewById(R.id.episodesContainer),false);

                    episodesBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());

                    layoutEpisodesBottomSheetBinding.episodesRecyclerView.setAdapter(
                            new EpisodesAdapter(tvShowDetailsResponse.getTvShow().episodes)
                    );
                    layoutEpisodesBottomSheetBinding.textTitle.setText(
                            String.format("Episodes | %s",tvShowDetailsResponse.getTvShow().name)
                    );
                    layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            episodesBottomSheetDialog.dismiss();
                        }
                    });

                    //----optional section start-------//


                    FrameLayout frameLayout=episodesBottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);


                    if(frameLayout!=null){

                        BottomSheetBehavior<View> bottomSheetBehavior=BottomSheetBehavior.from(frameLayout);
                        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }

                    //----------optional section end----------//

                    episodesBottomSheetDialog.show();
                }
            }
        });

        tvShowDetailsBinding.imagewatchlist.setOnClickListener(view -> {

            CompositeDisposable compositeDisposable=new CompositeDisposable();

            if (isTvShowAvailableInWatchlist)
            {
                compositeDisposable.add(tvShowDetailsViewModel.removeTVShowFromWatchlist(tvShow)
                        .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->
                {
                    isTvShowAvailableInWatchlist=false;
                    TempDataHolder.IS_WATCHLIST_UPDETED=true;
                    tvShowDetailsBinding.imagewatchlist.setImageResource(R.drawable.ic_visibility);
                    Toast.makeText(TvShowDetailsActivity.this, "removed from watchList", Toast.LENGTH_SHORT).show();
                    compositeDisposable.dispose();
                }));
            }
            else {
                compositeDisposable.add(tvShowDetailsViewModel.addToWatchList(tvShow)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(()->{
                            tvShowDetailsBinding.imagewatchlist.setImageResource(R.drawable.ic_added);
                            Toast.makeText(TvShowDetailsActivity.this, "added to watchList", Toast.LENGTH_SHORT).show();
                            compositeDisposable.dispose();
                        }
                        ));
            }
        });




        tvShowDetailsBinding.imagewatchlist.setVisibility(View.VISIBLE);
    }

    private void loadImageSlider(List<String> pictures) {

        imageSliderAdapter=new ImageSliderAdapter(pictures);

        tvShowDetailsBinding.sliderViewerPager.setOffscreenPageLimit(1);
        tvShowDetailsBinding.sliderViewerPager.setAdapter(imageSliderAdapter);
        tvShowDetailsBinding.sliderViewerPager.setVisibility(View.VISIBLE);
        tvShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicators(pictures.size());
        tvShowDetailsBinding.sliderViewerPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setupCurrentSliderInducators(position);
            }
        });

    }

    private void setupSliderIndicators(int count){

        ImageView[] indicators = new ImageView[count];

        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);

        for(int i=0; i< indicators.length; i++){

            indicators[i]=new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_inactive));

            indicators[i].setLayoutParams(layoutParams);

            tvShowDetailsBinding.layoutSliderIndicators.addView(indicators[i]);
        }

        tvShowDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setupCurrentSliderInducators(0);
    }

    private void setupCurrentSliderInducators(int position){

        int childcount= tvShowDetailsBinding.layoutSliderIndicators.getChildCount();

        for(int i=0 ; i<childcount ; i++){

            ImageView imageView= (ImageView) tvShowDetailsBinding.layoutSliderIndicators.getChildAt(i);

            if(i==position){

                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_active));
            }
            else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_slider_indicator_inactive));

            }


        }
    }
}