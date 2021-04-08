
package com.xuexiang.Photale.activity.Dynamic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.components.PictureSelectorFragment;
import com.xuexiang.Photale.components.library.core.Bottom;
import com.xuexiang.Photale.components.library.core.DynamicShareView;
import com.xuexiang.Photale.components.library.utils.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class DynamicSampleActivity extends AppCompatActivity {

    private List<String> pictureList;
    private List<String> tag;
    private String article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_sample);
        Intent intent = getIntent();
        pictureList = intent.getStringArrayListExtra(PictureSelectorFragment.SELECT_PICS_INFO);
        tag = intent.getStringArrayListExtra(PictureSelectorFragment.SELECT_TAG_INFO);
        article = intent.getStringExtra(PictureSelectorFragment.SELECT_ARTICLE_INFO);
    }

    @Override
    protected void onStart() {
        super.onStart();
        shareImage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onBackPressed();
    }


    private void shareImage() {

        final DynamicShareView dynamicShareView = new DynamicShareView(this);
        dynamicShareView.setAdapter(
                new MyAdapter(generateImageUrls(), generateBottom(), new MyAdapter.OnImageLoadedListener() {

                    @Override public void onImageLoaded() {

                        DynamicShareViewTool.create(DynamicSampleActivity.this).share(dynamicShareView);
                    }
                }));
    }

    private List<String> generateImageUrls() {
        List<String> results = new ArrayList<>();
        int i;
        for (i = 0; i < pictureList.size(); i ++){
            results.add(i, pictureList.get(i));
        }
//        results.add("https://source.unsplash.com/1600x900/?nature,moon");
//        results.add("https://source.unsplash.com/1600x900/?nature,train");
//        results.add("https://source.unsplash.com/1600x900/?nature,tree");
//        results.add("https://source.unsplash.com/1600x900/?nature,car");
        return results;
    }

    private Bottom generateBottom() {

        return new Bottom() {

            @Override public String getTitle() {

                return article;
//                return "你的专属文案！！";
            }

            @Override public String getNickname() {

                return "Fun";
            }

            @Override public String getAvatarUrl() {

                return "https://source.unsplash.com/900x900/?nature,ship";
            }

            @Override public String getQRCodeUrl() {

                return "https://www.baidu.com/";
            }
        };
    }
}