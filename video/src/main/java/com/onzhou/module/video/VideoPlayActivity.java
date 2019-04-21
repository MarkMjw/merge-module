package com.onzhou.module.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * @author: andy
 * @date: 2018-06-19
 **/
public class VideoPlayActivity extends AppCompatActivity {

    public static void intentStart(Context ctx) {
        Intent intent = new Intent(ctx, VideoPlayActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        TextView textView = findViewById(R.id.text);
        textView.setText(new Gson().toJson(new User()));
    }
}
