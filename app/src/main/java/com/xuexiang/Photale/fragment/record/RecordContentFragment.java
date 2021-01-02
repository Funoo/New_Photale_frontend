package com.xuexiang.Photale.fragment.record;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.xuexiang.Photale.R;


import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.xpage.annotation.Page;

@Page
public class RecordContentFragment extends BaseFragment {

    private View view;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_content;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_record_content, container, false);
        return view;
    }

    public void refresh(String title, String content, String imagePath) {
        View visibilityLayout = view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView recordTitleText = view.findViewById(R.id.record_title);
        EditText recordContentText = view.findViewById(R.id.record_content);
        ImageView recordImage = view.findViewById(R.id.record_image);
        recordTitleText.setText(title);
        recordContentText.setText(content);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        recordImage.setImageBitmap(bitmap);
    }
}