/*
 * Copyright (C) 2021 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.Photale.components.library.utils;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.zxing.WriterException;
import com.xuexiang.Photale.components.library.core.Adapter;
import com.xuexiang.Photale.components.library.core.Bottom;
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.components.library.qrcode.QRCodeEncoder;

import java.util.List;

public class MyAdapter implements Adapter {

    private List<String> mImages;

    private Bottom mBottom;

    private OnImageLoadedListener mOnImageLoadedListener;

    private int mLoadedImageCount = 0;

    public MyAdapter(List<String> images, @NonNull Bottom bottom,
                     @NonNull OnImageLoadedListener onImageLoadedListener) {
        mImages = images;
        mBottom = bottom;
        mOnImageLoadedListener = onImageLoadedListener;
    }

    @Override public int getCoverCount() {

        return mImages.size();
    }

    @Override public View getImage(ViewGroup viewGroup, int position) {

        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.osl_ui_widgets_shareview_imageview_wrapper, viewGroup, false);

        Glide.with(view.getContext())
                .asBitmap()
                .load(mImages.get(position))
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ImageView ivImage = view.findViewById(R.id.iv_image);
                        ivImage.setImageBitmap(resource);
                        mLoadedImageCount++;

                        if (mLoadedImageCount == getCoverCount() + 1) {
                            mOnImageLoadedListener.onImageLoaded();
                        }
                    }
                });

        return view;
    }

    @Override public View getBottom(ViewGroup viewGroup) {

        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.osl_ui_widgets_shareview_bottom_part, viewGroup, false);

        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(mBottom.getTitle());

        Glide.with(viewGroup.getContext())
                .asBitmap()
                .load(mBottom.getAvatarUrl())
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ImageView ivAvatar = view.findViewById(R.id.iv_avatar);
                        ivAvatar.setImageBitmap(resource);
                        mLoadedImageCount++;

                        if (mLoadedImageCount == getCoverCount() + 1) {
                            mOnImageLoadedListener.onImageLoaded();
                        }
                    }
                });

        TextView tvNickname = view.findViewById(R.id.tv_nickname);
        tvNickname.setText(mBottom.getNickname());

        try {
            Bitmap qrCodeBitmap = QRCodeEncoder.encodeAsBitmap(mBottom.getQRCodeUrl(),
                    DensityUtil.dip2px(viewGroup.getContext(), 60.0F));
            ImageView ivQRCode = view.findViewById(R.id.iv_qr_code);
            ivQRCode.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return view;
    }

    public interface OnImageLoadedListener {

        void onImageLoaded();
    }
}
