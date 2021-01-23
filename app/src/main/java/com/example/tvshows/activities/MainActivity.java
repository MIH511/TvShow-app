package com.example.tvshows.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tvshows.R;
import com.example.tvshows.adapters.TvShowsAdapter;
import com.example.tvshows.databinding.ActivityMainBinding;
import com.example.tvshows.listeners.TvShowslistener;
import com.example.tvshows.models.TvShow;
import com.example.tvshows.responses.TvShowResponse;
import com.example.tvshows.viewmodels.MostPopularTvShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TvShowslistener {

    MostPopularTvShowsViewModel mostPopularTvShowsViewModel;
    ActivityMainBinding binding;
    private List<TvShow> tvShows=new ArrayList<>();
    private TvShowsAdapter tvShowsAdapter;
    private int currentPage=1;
    private int totalAvailablePages=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        doInitialization();
    }

    private void doInitialization(){
        binding.recyclerViewMostPopularShows.setHasFixedSize(true);
        mostPopularTvShowsViewModel=new ViewModelProvider(this).get(MostPopularTvShowsViewModel.class);
        tvShowsAdapter=new TvShowsAdapter(tvShows,this);
        binding.recyclerViewMostPopularShows.setAdapter(tvShowsAdapter);
        binding.recyclerViewMostPopularShows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!binding.recyclerViewMostPopularShows.canScrollVertically(1)){
                    if(currentPage<=totalAvailablePages)
                    {
                        currentPage+=1;
                        getMostPopularTvShows();
                    }
                }
            }
        });
        binding.imagewatchlist.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),WatchlistActivity.class)));

        binding.imagesearch.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
            startActivity(intent);
        });
        getMostPopularTvShows();
    }

    private void getMostPopularTvShows(){

        toggleLoading();
        mostPopularTvShowsViewModel.getMostPopularTvShows(currentPage)
                .observe(this, mostPopularTvShowsViewModel->{
                    toggleLoading();
                    if (mostPopularTvShowsViewModel!=null)
                    {
                        totalAvailablePages=mostPopularTvShowsViewModel.getPages();

                        if(mostPopularTvShowsViewModel.getTvShows()!=null){
                            int oldCount=tvShows.size();
                            tvShows.addAll(mostPopularTvShowsViewModel.getTvShows());
                            tvShowsAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
                        }
                    }
                        }
                );
    }

    private void toggleLoading(){

        if(currentPage==1)
        {
            if(binding.getIsLoading()!=null && binding.getIsLoading())
            {
                binding.setIsLoading(false);
            }else {
                binding.setIsLoading(true);
            }
        }else {
            if (binding.getIsLoadingMore()!=null && binding.getIsLoadingMore())
            {
                binding.setIsLoadingMore(false);
            }else {
                binding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {

        Intent intent=new Intent(getApplicationContext(),TvShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);
    }
}