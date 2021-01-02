package com.xuexiang.Photale.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.fragment.record.RecordContentFragment;

import androidx.appcompat.app.AppCompatActivity;

public class RecordContentActivity extends AppCompatActivity {

    public static void actionStart(Context context, String recordTitle, String recordContent, String recordImagePath) {
        Intent intent = new Intent(context, com.xuexiang.Photale.activity.RecordContentActivity.class);
        intent.putExtra("record_title", recordTitle);
        intent.putExtra("record_content", recordContent);
        intent.putExtra("record_image_path", recordImagePath);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_content);
        String recordTitle = getIntent().getStringExtra("record_title");
        String recordContent = getIntent().getStringExtra("record_content");
        String recordImagePath = getIntent().getStringExtra("record_image_path");
        RecordContentFragment recordContentFragment = (RecordContentFragment) getSupportFragmentManager().findFragmentById(R.id.record_content_fragment);
        recordContentFragment.refresh(recordTitle, recordContent, recordImagePath);
    }
}