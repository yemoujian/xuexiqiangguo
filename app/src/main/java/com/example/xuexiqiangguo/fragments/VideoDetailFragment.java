package com.example.xuexiqiangguo.fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.example.xuexiqiangguo.R;
import com.example.xuexiqiangguo.models.Video;

public class VideoDetailFragment extends Fragment {

    private VideoView videoView;
    private Button playButton, stopButton;
    private String videoUrl;
    private RatingBar ratingBar;
    private String videoId;

    public static VideoDetailFragment newInstance(Video video) {
        VideoDetailFragment fragment = new VideoDetailFragment();
        Bundle args = new Bundle();
        args.putString("videoUrl", video.getUrl());
        args.putString("id", video.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoUrl = getArguments().getString("videoUrl");
            videoId = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_detail, container, false);

        videoView = view.findViewById(R.id.video_view);
        playButton = view.findViewById(R.id.btn_play);
        stopButton = view.findViewById(R.id.btn_stop);

        if (videoUrl != null && !videoUrl.isEmpty()) {
            videoView.setVideoURI(Uri.parse(videoUrl));
        } else {
            // 处理无效或空的 videoUrl。例如，显示错误消息或隐藏 videoView
            // 例如:
            videoView.setVisibility(View.GONE);
        }

        playButton.setOnClickListener(v -> videoView.start());
        stopButton.setOnClickListener(v -> videoView.pause()); // 或 videoView.stopPlayback()，具体取决于你的需求


        // 返回按钮
        Button backButton = view.findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> {
            // 返回到前一个Fragment
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        // 打分
        ratingBar = view.findViewById(R.id.rating_bar);
        loadRating(videoId);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    saveRating(videoId, rating);
                }
            }
        });

        return view;
    }

    private void loadRating(String videoId) {
        SharedPreferences prefs = getActivity().getSharedPreferences("VideoRatings", Context.MODE_PRIVATE);
        float rating = prefs.getFloat(videoId, 0f);
        ratingBar.setRating(rating);
    }

    private void saveRating(String videoId, float rating) {
        SharedPreferences prefs = getActivity().getSharedPreferences("VideoRatings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(videoId, rating);
        editor.apply();
    }
}
