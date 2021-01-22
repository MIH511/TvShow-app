package com.example.tvshows.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tvshows.R;
import com.example.tvshows.adapters.WatchlistAdapter;
import com.example.tvshows.databinding.ActivityWatchlistBinding;
import com.example.tvshows.listeners.WatchlistListener;
import com.example.tvshows.models.TvShow;
import com.example.tvshows.utilities.TempDataHolder;
import com.example.tvshows.viewmodels.MostPopularTvShowsViewModel;
import com.example.tvshows.viewmodels.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchlistActivity extends AppCompatActivity implements WatchlistListener {

    private ActivityWatchlistBinding activityWatchlistBinding;
    private WatchlistViewModel watchlistViewModel;
    private WatchlistAdapter watchlistAdapter;
    private List<TvShow> watchList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchlistBinding= DataBindingUtil.setContentView(this,R.layout.activity_watchlist);
        doInitialization();
    }

    private void doInitialization() {
        watchlistViewModel=new ViewModelProvider(this).get(WatchlistViewModel.class);
        activityWatchlistBinding.ImageBack.setOnClickListener(view -> onBackPressed());
        watchList= new ArrayList<>();
        loadWatchlist();
    }
    private void loadWatchlist(){
        activityWatchlistBinding.setIsLoading(true);

        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.loadWatchlist().subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(tvShows -> {
            activityWatchlistBinding.setIsLoading(false);
            if (watchList.size() > 0){
                watchList.clear();
            }
            watchList.addAll(tvShows);
            watchlistAdapter=new WatchlistAdapter(watchList,this);
            activityWatchlistBinding.watchListRecyclerView.setAdapter(watchlistAdapter);
            activityWatchlistBinding.watchListRecyclerView.setVisibility(View.VISIBLE);
            compositeDisposable.dispose();
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TempDataHolder.IS_WATCHLIST_UPDETED)
        {
            loadWatchlist();
            TempDataHolder.IS_WATCHLIST_UPDETED=false;
        }

    }

    @Override
    public void onTVShowClicked(TvShow tvShow) {

        Intent intent=new Intent(getApplicationContext(),TvShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);
    }

    @Override
    public void removeTvShowFormatwatchlist(TvShow tvShow, int position) {

        CompositeDisposable compositeDisposableForDelete=new CompositeDisposable();
        compositeDisposableForDelete.add(watchlistViewModel.removeTVshowFormatWatchlist(tvShow)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(()-> {
            watchList.remove(position);
            watchlistAdapter.notifyItemRemoved(position);
            watchlistAdapter.notifyItemRangeChanged(position,watchlistAdapter.getItemCount());
            compositeDisposableForDelete.dispose();
        }));
    }
}