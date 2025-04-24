package com.example.xuexiqiangguo.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xuexiqiangguo.R;

import com.example.xuexiqiangguo.fragments.VideoDetailFragment;
import com.example.xuexiqiangguo.fragments.VideoListFragment;
import com.example.xuexiqiangguo.models.Video;

public class ListActivity extends AppCompatActivity implements VideoListFragment.OnVideoSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list); // 布局文件需要包含一个Fragment容器


        if (findViewById(R.id.detail_fragment_container) != null) {
            // 大屏设备，加载两个Fragment
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_fragment_container, new VideoListFragment())
                        .commit();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, new VideoDetailFragment())
                        .commit();
            }
        } else {
            // 小屏设备，只加载一个
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new VideoListFragment())
                        .commit();
            }

        }
    }

    @Override
    public void onVideoSelected(Video video) {
        VideoDetailFragment fragment = VideoDetailFragment.newInstance(video);

        if (findViewById(R.id.detail_fragment_container) != null) {
            // 大屏，更新详情Fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, fragment)
                    .commit();
        } else {
            // 小屏，替换当前Fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null) // 添加到返回栈以便用户可以回退
                    .commit();
        }
    }
}
