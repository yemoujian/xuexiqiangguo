package com.example.xuexiqiangguo.adapters;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuexiqiangguo.R;
import com.example.xuexiqiangguo.fragments.VideoDetailFragment;
import com.example.xuexiqiangguo.fragments.VideoListFragment;
import com.example.xuexiqiangguo.models.Video;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private static List<Video> videoList;
    private VideoListFragment.OnVideoSelectedListener listener;

    public VideoListAdapter(List<Video> videoList, VideoListFragment.OnVideoSelectedListener listener) {
        this.videoList = videoList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.tvTitle.setText(video.getTitle());

        holder.tvDuration.setText(video.getDurationString());
        holder.tvSource.setText(video.getSource());
        holder.tvPublishTime.setText(video.getPublishTime());
        holder.tvTopic.setText(video.getTopic());
        holder.tvEditor.setText(video.getEditor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onVideoSelected(video);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDuration, tvSource, tvPublishTime, tvTopic, tvEditor;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_video_title);
            tvDuration = view.findViewById(R.id.tv_video_duration);
            tvSource = view.findViewById(R.id.tv_video_source);
            tvPublishTime = view.findViewById(R.id.tv_video_publish_time);
            tvTopic = view.findViewById(R.id.tv_video_topic);
            tvEditor = view.findViewById(R.id.tv_video_editor);

            // 设置点击监听器
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 获取当前点击的视频信息
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Video video = videoList.get(position);
                        // 创建并显示 VideoDetailFragment
                        Fragment videoDetailFragment = VideoDetailFragment.newInstance(video);
                        ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, videoDetailFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
        }
    }

    public static String parseDate(Integer millis) {
        Integer seconds = millis / 1000;
        Integer minutes = seconds / 60;
        Integer remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}
