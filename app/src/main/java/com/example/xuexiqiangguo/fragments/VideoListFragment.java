package com.example.xuexiqiangguo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuexiqiangguo.R;
import com.example.xuexiqiangguo.adapters.VideoListAdapter;
import com.example.xuexiqiangguo.models.Video;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class VideoListFragment extends Fragment {

    private RecyclerView recyclerView;
    private OnVideoSelectedListener listener;

    public VideoListFragment() {
        // Required empty public constructor
    }

    // 定义接口，用于与宿主Activity通信
    public interface OnVideoSelectedListener {
        void onVideoSelected(Video video);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 确保宿主Activity实现了回调接口
        try {
            listener = (OnVideoSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnVideoSelectedListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_videos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 获取视频数据
        List<Video> videos = getVideosFromJson();

        VideoListAdapter adapter = new VideoListAdapter(videos, listener);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Video> getHardcodedVideos() {
        List<Video> videos = new ArrayList<>();

        videos.add(new Video("1", "https://boot-video.xuexi.cn/video/1006/p/d7a7719546fec035b5fc452f678a3a73-c78f0c6b48f5473080709dd8f19ca212-2.mp4", "视频标题 1", 180000, "来源 1", "2023-01-01", "主题 1", "责任编辑 1"));
        videos.add(new Video("2", "https://boot-video.xuexi.cn/video/1006/p/31537aac06c7e197d307d86296cd4b8f-0d1f4ac810904168bb17e0f2094f3ca3-2.mp4", "视频标题 2", 300000, "来源 2", "2023-01-02", "主题 2", "责任编辑 2"));
        videos.add(new Video("3", "https://boot-video.xuexi.cn/video/1006/p/04b62fd6020653cbd88de078c28a22e7-871f164fa2a44187b266ab6548a4a5a3-2.mp4", "视频标题 3", 600000, "来源 3", "2023-01-03", "主题 3", "责任编辑 3"));

        return videos;
    }

    private List<Video> getVideosFromJson() {
        List<Video> videoList = new ArrayList<>();
        try {
            InputStream is = getResources().openRawResource(R.raw.data);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("id");
                String url = jsonObject.getString("url");
                String title = jsonObject.getString("title");
                Integer duration = jsonObject.getInt("duration");
                String source = jsonObject.getString("source");
                String publishTime = jsonObject.getString("publishTime");
                String topic = jsonObject.getString("topic");
                String editor = jsonObject.getString("editor");

                Video video = new Video(id, url, title, duration, source, publishTime, topic, editor);
                videoList.add(video);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoList;
    }
}

