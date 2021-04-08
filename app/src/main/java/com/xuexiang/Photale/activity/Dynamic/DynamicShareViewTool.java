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

package com.xuexiang.Photale.activity.Dynamic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.View;

import androidx.core.content.FileProvider;

import com.xuexiang.Photale.components.library.utils.DensityUtil;
import com.xuexiang.Photale.components.library.utils.ScreenUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class DynamicShareViewTool {

    private Context mContext;

    private String mSavePath;

    public static DynamicShareViewTool create(Context context) {
        return new DynamicShareViewTool(context);
    }

    private DynamicShareViewTool(Context context) {
        mContext = context;
    }

    public void share(View target) {

        measureManually(target, ScreenUtil.getScreenWidth(mContext),
                ScreenUtil.getScreenWidth(mContext) + DensityUtil.dip2px(mContext, 175.0F));
        target.layout(0, 0, target.getMeasuredWidth(), target.getMeasuredHeight());

        //  生成Bitmap
        Bitmap bitmap = generateBitmap(target, target.getMeasuredWidth(), target.getMeasuredHeight());

        //  将 Bitmap 保存为临时文件
        try {
            saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mContext.startActivity(createShareIntent());
    }

    /**
     * 生成Bitmap
     */
    private Bitmap generateBitmap(View target, int width, int height) {

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); // 创建 Bitmap
        target.draw(new Canvas(bitmap)); // 绘制在 Bitmap 上
        return bitmap;
    }

    private void saveBitmap(Bitmap bitmap) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(createTempFile());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private File createTempFile() throws IOException {

        File file = File.createTempFile("pero_share_image_", ".jpg", mContext.getCacheDir());
        mSavePath = file.getAbsolutePath(); // 保存路径
        return file;
    }

    private Intent createShareIntent() {

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, getUriFromFile());
        return shareIntent;
    }

    private Uri getUriFromFile() {

        return FileProvider.getUriForFile(mContext, "com.xuexiang.Photale.fileprovider", new File(mSavePath));
    }

    private void measureManually(View target, int widthSize, int heightSize) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize, View.MeasureSpec.EXACTLY);
        target.measure(widthMeasureSpec, heightMeasureSpec);
    }
}
