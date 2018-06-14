package com.example.yeshu.sizzling;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yeshu.sizzling.JsonData.IngredientsJsonData;
import com.example.yeshu.sizzling.JsonData.StepsJsonData;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment  {

    public static int flag;
    ArrayList<IngredientsJsonData> ingredientsJsonDataArrayList=null;
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    StepsJsonData stepsJsonData=null;
    ImageView thumbnail_ImageView;
    private boolean playReady=true;
    private String videoURL=null;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        simpleExoPlayerView = rootView.findViewById(R.id.exoplayer_view);
        thumbnail_ImageView = rootView.findViewById(R.id.thumbnailImage);

        Activity activity = this.getActivity();
        assert activity != null;
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (getArguments().getParcelableArrayList("ingredients") != null) {
            ingredientsJsonDataArrayList = getArguments().getParcelableArrayList("ingredients");
            if (appBarLayout != null) {
                appBarLayout.setTitle("Ingredients");
                flag = 1;
            }
            for (int i = 0; i < ingredientsJsonDataArrayList.size(); i++) {

                ((TextView) rootView.findViewById(R.id.item_description)).append(" \u2022 " + ingredientsJsonDataArrayList.get(i).getIngredients() + "\t" +
                        " \n\t" + "Quantity : " + ingredientsJsonDataArrayList.get(i).getQuantity() + " \n\t " + "Measurements : " + ingredientsJsonDataArrayList.get(i).getMeasurement() + "\t\n\n");
            }
        }
            if (getArguments().getParcelable("steps") != null) {
                stepsJsonData = getArguments().getParcelable("steps");
                if (appBarLayout != null) {
                    appBarLayout.setTitle(stepsJsonData.getShortDescription());
                    flag = 0;
                }
                ((TextView) rootView.findViewById(R.id.item_description)).append("Step "+stepsJsonData.getDescription() + "\n");
                if (!stepsJsonData.getVideoURL().isEmpty()) {
                    videoURL = stepsJsonData.getVideoURL();
                    simpleExoPlayerView.setVisibility(View.VISIBLE);
                    VideoPlayerIntializer();
                } else {
                    if (!stepsJsonData.getThumbnailURL().contentEquals("")) {
                        videoURL = stepsJsonData.getThumbnailURL();
                        simpleExoPlayerView.setVisibility(View.VISIBLE);
                        VideoPlayerIntializer();
                        if (stepsJsonData.getThumbnailURL().equalsIgnoreCase("mp4")) {
                            Glide.with(this).load(videoURL).into(thumbnail_ImageView);
                        }
                    }else if (!stepsJsonData.getThumbnailURL().isEmpty()){
                        videoURL=stepsJsonData.getThumbnailURL();
                        if (videoURL.equalsIgnoreCase("mp4"))
                            Glide.with(this).load(videoURL).into(thumbnail_ImageView);
                    }
                    else {
                        Toast.makeText(activity, "No Videos & No Thumbnails", Toast.LENGTH_SHORT).show();
                    }
                }
            if (stepsJsonData.getThumbnailURL().contentEquals(stepsJsonData.getVideoURL()))
                simpleExoPlayerView.setVisibility(View.GONE);
            if (savedInstanceState != null) {
                long currentPlayerPosition = savedInstanceState.getLong(getString(R.string.position));
                simpleExoPlayer.seekTo(currentPlayerPosition);
                playReady = savedInstanceState.getBoolean("play_back");
                simpleExoPlayer.setPlayWhenReady(playReady);
            }
        }
        return rootView;
}

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (simpleExoPlayer!=null) {
            outState.putLong(getString(R.string.position), simpleExoPlayer.getCurrentPosition());
            outState.putBoolean("play_back", simpleExoPlayer.getPlayWhenReady());
        }
    }

    private void VideoPlayerIntializer(){
                if (videoURL!=null) {
                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                    simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

                    Uri videoURI = Uri.parse(videoURL);

                    DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                    simpleExoPlayerView.setPlayer(simpleExoPlayer);
                    simpleExoPlayer.prepare(mediaSource);
                    simpleExoPlayer.setPlayWhenReady(playReady);
                }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer!=null){
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (simpleExoPlayer!=null){
            releasePlayer();
        }
    }

    private void releasePlayer() {
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (simpleExoPlayer!=null){
            releasePlayer();
        }
    }
}
