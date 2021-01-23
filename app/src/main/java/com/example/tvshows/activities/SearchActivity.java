package com.example.tvshows.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.tvshows.R;
import com.example.tvshows.adapters.TvShowsAdapter;
import com.example.tvshows.databinding.ActivitySearchBinding;
import com.example.tvshows.listeners.TvShowslistener;
import com.example.tvshows.models.TvShow;
import com.example.tvshows.responses.TvShowResponse;
import com.example.tvshows.viewmodels.MostPopularTvShowsViewModel;
import com.example.tvshows.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TvShowslistener {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel searchViewModel;
    private List<TvShow> tvShows=new ArrayList<>();
    private TvShowsAdapter showsAdapter;
    private int currentPage=1;
    private int totalAvailablePages=1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding= DataBindingUtil.setContentView(this,R.layout.activity_search);
        searchViewModel=new ViewModelProvider(this).get(SearchViewModel.class);
        doInitialization();

    }

    private void doInitialization() {


        activitySearchBinding.imageBack.setOnClickListener(view -> onBackPressed());
        activitySearchBinding.tvShowSearchRecyclerView.setHasFixedSize(true);
        showsAdapter=new TvShowsAdapter(tvShows,this::onTvShowClicked);
        activitySearchBinding.tvShowSearchRecyclerView.setAdapter(showsAdapter);
        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer!=null){
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!editable.toString().trim().isEmpty()){
                    timer=new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    currentPage=1;
                                    totalAvailablePages=1;
                                    searchTVShow(editable.toString());
                                }
                            });
                        }
                    },800);
                }
                else {
                    tvShows.clear();
                    showsAdapter.notifyDataSetChanged();
                }

            }
        });

        activitySearchBinding.tvShowSearchRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activitySearchBinding.inputSearch.getText().toString().isEmpty()){
                    if (currentPage<totalAvailablePages){
                        currentPage+=1;
                        searchTVShow(activitySearchBinding.inputSearch.getText().toString());
                    }
                }
            }
        });
    }

    private void searchTVShow(String quary){
        toggleLoading();
        searchViewModel.searchTVShow(quary,currentPage).observe(this, tvShowResponse -> {

            toggleLoading();

            if(tvShowResponse!=null)
            {
                totalAvailablePages=tvShowResponse.getPages();
                if(tvShowResponse.getTvShows()!=null){
                    int oldCount=tvShows.size();
                    tvShows.addAll(tvShowResponse.getTvShows());
                    showsAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
                }
            }
        });
    }

    private void toggleLoading(){

        if(currentPage==1)
        {
            if(activitySearchBinding.getIsLoading()!=null && activitySearchBinding.getIsLoading())
            {
                activitySearchBinding.setIsLoading(false);
            }
            else {
                activitySearchBinding.setIsLoading(true);
            }
        }
        else {
            if (activitySearchBinding.getIsLoadingMore()!=null && activitySearchBinding.getIsLoadingMore())
            {
                activitySearchBinding.setIsLoadingMore(false);
            }else {
                activitySearchBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {
        Intent intent=new Intent(getApplicationContext(),TvShowDetailsActivity.class);
        intent.putExtra("tvshow",tvShow);
        startActivity(intent);
    }
}