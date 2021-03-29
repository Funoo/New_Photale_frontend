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

package com.xuexiang.Photale.components;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.broadcast.BroadcastAction;
import com.luck.picture.lib.broadcast.BroadcastManager;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.luck.picture.lib.style.PictureParameterStyle;

import com.luck.picture.lib.tools.PictureFileUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.ToastUtils;
import com.xuexiang.Photale.adapter.GridImageAdapter;
import com.xuexiang.Photale.components.LongPictureStyle.GlideEngine;
import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.Photale.utils.LongPictures.LongPictureCreate;
import com.xuexiang.Photale.utils.Utils;
import com.xuexiang.Photale.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import com.xuexiang.Photale.R;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.xuexiang.xutil.XUtil.runOnUiThread;

@Page(name = "文案生成")
public class PictureSelectorFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.flowlayout_display)
    FlowTagLayout mDisplayFlowTagLayout;

    @BindView(R.id.tagEdit)
    MaterialEditText tagEdit;

    @BindView(R.id.article)
    MultiLineEditText myarticle;

    @BindView(R.id.generateText)
    RoundButton generateText;


    private List<LocalMedia> mSelectList = new ArrayList<>();

    private String tagString;

    private int tagListNum = 0;

    private int maxSelectNum = 12;

    private GridImageAdapter mAdapter;

    private List<String> tagInfo = new ArrayList<>();

    private int chooseMode = PictureMimeType.ofAll();


    private int REQUEST_CODE_CHOOSE = 0x000011;
    private String resultPath;
    private List<String> mCurrentSelectedPath = new ArrayList<>();

    private PictureParameterStyle mPictureParameterStyle;
    private PictureCropParameterStyle mCropParameterStyle;

    private final static String INFO = PictureSelectorFragment.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // 被回收
        } else {
            clearCache();
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setBackgroundColor(Color.parseColor("#62c2af"));

        return titleBar;
    }

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_picture_selector;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getContext(),
                4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(getContext(), 8), false));
        mAdapter = new GridImageAdapter(getContext(), onAddPicClickListener);
//        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList("selectorList") != null) {
//            mAdapter.setList(savedInstanceState.getParcelableArrayList("selectorList"));
//        }
        mAdapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((v, position) -> {
            List<LocalMedia> selectList = mAdapter.getData();
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                switch (mediaType) {
                    case PictureConfig.TYPE_VIDEO:
                        // 预览视频
                        PictureSelector.create(PictureSelectorFragment.this).externalPictureVideo(media.getPath());
                        break;
                    case PictureConfig.TYPE_AUDIO:
                        // 预览音频
                        PictureSelector.create(PictureSelectorFragment.this).externalPictureAudio(media.getPath());
                        break;
                    default:
                        // 预览图片 可自定长按保存路径
//                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
//                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
//                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                        PictureSelector.create(PictureSelectorFragment.this)
                                //.themeStyle(themeId) // xml设置主题
                                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                                //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                .openExternalPreview(position, selectList);
                        break;
                }
            }
        });
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        getDefaultStyle();
        myarticle.setVisibility(View.GONE);
    }



//    public void initClicked() {
//        Utils.getPictureSelector(this)
//                .selectionMedia(mSelectList)
//                .forResult(PictureConfig.CHOOSE_REQUEST);
//        System.out.println("--------step in click status---------------");
//    }
//
//    @OnClick(R.id.button_no_camera)
//    public void onViewClicked() {
//        initClicked();
//    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = true;
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(PictureSelectorFragment.this)
                        .openGallery(PictureMimeType.ofImage())
                        .setPictureStyle(mPictureParameterStyle)
                        .isEnableCrop(true)// 是否裁剪 true or false
                        .maxSelectNum(10)// 最大图片选择数量 int
                        .minSelectNum(1)// 最小选择数量 int
                        .hideBottomControls(false)
                        .imageSpanCount(4)// 每行显示个数 int
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .selectionMode(PictureConfig.MULTIPLE)
                        .isCamera(true)
                        .selectionData(mSelectList)
                        .selectionData(mAdapter.getData())// 是否传入已选图片
                        .forResult(new MyResultCallback(mAdapter));
                        //.forResult(REQUEST_CODE_CHOOSE);结果回调onActivityResult code
            } else {
                // 单独拍照
                PictureSelector.create(PictureSelectorFragment.this)
                        .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .setPictureStyle(mPictureParameterStyle)
                        .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                        .setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
                        .maxSelectNum(10)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
//                        .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
                        .compressQuality(60)// 图片压缩后输出质量
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .selectionData(mAdapter.getData())// 是否传入已选图片
                        .isPreviewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        .cutOutQuality(90)// 裁剪输出质量 默认100
                        .selectionData(mSelectList)
                        .selectionData(mAdapter.getData())// 是否传入已选图片
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(new MyResultCallback(mAdapter));
            }
        }

    };

    /**
     * 返回结果回调
     */
    private static class MyResultCallback implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<GridImageAdapter> mAdapterWeakReference;

        public MyResultCallback(GridImageAdapter adapter) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            for (LocalMedia media : result) {
                Log.i(INFO, "是否压缩:" + media.isCompressed());
                Log.i(INFO, "压缩:" + media.getCompressPath());
                Log.i(INFO, "原图:" + media.getPath());
                Log.i(INFO, "绝对路径:" + media.getRealPath());
                Log.i(INFO, "是否裁剪:" + media.isCut());
                Log.i(INFO, "裁剪:" + media.getCutPath());
                Log.i(INFO, "是否开启原图:" + media.isOriginal());
                Log.i(INFO, "原图路径:" + media.getOriginalPath());
                Log.i(INFO, "Android Q 特有Path:" + media.getAndroidQToPath());
                Log.i(INFO, "宽高: " + media.getWidth() + "x" + media.getHeight());
                Log.i(INFO, "Size: " + media.getSize());
                // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
            }
            if (mAdapterWeakReference.get() != null) {
                mAdapterWeakReference.get().setList(result);
                mAdapterWeakReference.get().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancel() {
            Log.i(INFO, "PictureSelector Cancel");
        }
    }



    @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回五种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                    // 5.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
                    // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                    System.out.println("onActivityResult");
                    for (LocalMedia media : selectList) {
                        Log.i(INFO, "是否压缩:" + media.isCompressed());
                        Log.i(INFO, "压缩:" + media.getCompressPath());
                        Log.i(INFO, "原图:" + media.getPath());
                        Log.i(INFO, "绝对路径:" + media.getRealPath());
                        Log.i(INFO, "是否裁剪:" + media.isCut());
                        Log.i(INFO, "裁剪:" + media.getCutPath());
                        Log.i(INFO, "是否开启原图:" + media.isOriginal());
                        Log.i(INFO, "原图路径:" + media.getOriginalPath());
                        Log.i(INFO, "Android Q 特有Path:" + media.getAndroidQToPath());
                    }
                    mAdapter.setList(selectList);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }


    @OnClick({R.id.btn_add_tag, R.id.btn_clear_tag})
    void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_add_tag:
                if (tagEdit.getText().length() == 0){
                    XToastUtils.toast("请输入标签内容喔~");
                }else {
                    tagString = tagEdit.getText().toString();
                    mDisplayFlowTagLayout.addTag(tagString);
                    tagInfo.add(tagListNum,tagString);
                    tagListNum++;
                    System.out.println("---------" + tagInfo + "-------------" + tagListNum);
                    tagEdit.clear();
                }
                break;
            case R.id.btn_clear_tag:
                mDisplayFlowTagLayout.clearTags();
                tagInfo.clear();
                tagListNum = 0;
                System.out.println("---------" + tagInfo + "-------------" + tagListNum);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.generateText)
    public void onGenerateClicked() {
        if (generateText.getText().toString().equals("生成文案")){
            myarticle.setVisibility(View.VISIBLE);
            myarticle.setContentText("      我们要学会珍惜我们生活的每一天，因为，这每一天的开始，都将是我们余下生命之中的第一天。除非我们即将死去。");
            generateText.setText("换个文案");
        }else {
            myarticle.setVisibility(View.VISIBLE);
            myarticle.setContentText("      也许每一个男子全都有过这样的两个女人，至少两个。娶了红玫瑰，久而久之，红的变了墙上的一抹蚊子血，白的还是“床前明月光”；娶了白玫瑰，白的便是衣服上的一粒饭粘子，红的却是心口上的一颗朱砂痣。");
        }
    }

    /**
     * 清空缓存包括裁剪、压缩、AndroidQToPath所生成的文件，注意调用时机必须是处理完本身的业务逻辑后调用；非强制性
     */
    private void clearCache() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        if (getContext() != null) {
            if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //PictureFileUtils.deleteCacheDirFile(this, PictureMimeType.ofImage());
                PictureFileUtils.deleteAllCacheDirFile(getContext());
            } else {
                PermissionChecker.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE:
                // 存储权限
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        PictureFileUtils.deleteCacheDirFile(getContext(), PictureMimeType.ofImage());
                    } else {
                        Toast.makeText(getContext(),
                                getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAdapter != null) {
            outState.putParcelableArrayList("selectorList",
                    (ArrayList<? extends Parcelable>) mAdapter.getData());
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            if (BroadcastAction.ACTION_DELETE_PREVIEW_POSITION.equals(action)) {// 外部预览删除按钮回调
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    ToastUtils.s(context, "delete image index:" + position);
                    mAdapter.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            if (getContext() != null) {
                BroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver,
                        BroadcastAction.ACTION_DELETE_PREVIEW_POSITION);
            }
        }
    }

    private void getDefaultStyle() {
        // 相册主题
        mPictureParameterStyle = new PictureParameterStyle();
        // 是否改变状态栏字体颜色(黑白切换)
        mPictureParameterStyle.isChangeStatusBarFontColor = false;
        // 是否开启右下角已完成(0/9)风格
        mPictureParameterStyle.isOpenCompletedNumStyle = false;
        // 是否开启类似QQ相册带数字选择风格
        mPictureParameterStyle.isOpenCheckNumStyle = false;
        // 相册状态栏背景色
        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#62c2af");
        // 相册列表标题栏背景色
        mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#62c2af");
        // 相册列表标题栏右侧上拉箭头
        mPictureParameterStyle.pictureTitleUpResId = R.drawable.picture_icon_arrow_up;
        // 相册列表标题栏右侧下拉箭头
        mPictureParameterStyle.pictureTitleDownResId = R.drawable.picture_icon_arrow_down;
        // 相册文件夹列表选中圆点
        mPictureParameterStyle.pictureFolderCheckedDotStyle = R.drawable.picture_orange_oval;
        // 相册返回箭头
        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.picture_icon_back;
        // 标题栏字体颜色
//        mPictureParameterStyle.pictureTitleTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 相册右侧取消按钮字体颜色  废弃 改用.pictureRightDefaultTextColor和.pictureRightDefaultTextColor
//        mPictureParameterStyle.pictureCancelTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 相册列表勾选图片样式
        mPictureParameterStyle.pictureCheckedStyle = R.drawable.picture_checkbox_selector;
        // 相册列表底部背景色
//        mPictureParameterStyle.pictureBottomBgColor = ContextCompat.getColor(getContext(), R.color.picture_color_grey);
        // 已选数量圆点背景样式
        mPictureParameterStyle.pictureCheckNumBgStyle = R.drawable.picture_num_oval;
        // 相册列表底下预览文字色值(预览按钮可点击时的色值)
//        mPictureParameterStyle.picturePreviewTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_fa632d);
        // 相册列表底下不可预览文字色值(预览按钮不可点击时的色值)
//        mPictureParameterStyle.pictureUnPreviewTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 相册列表已完成色值(已完成 可点击色值)
//        mPictureParameterStyle.pictureCompleteTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_fa632d);
        // 相册列表未完成色值(请选择 不可点击色值)
//        mPictureParameterStyle.pictureUnCompleteTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 预览界面底部背景色
//        mPictureParameterStyle.picturePreviewBottomBgColor = ContextCompat.getColor(getContext(), R.color.picture_color_grey);
        // 外部预览界面删除按钮样式
        mPictureParameterStyle.pictureExternalPreviewDeleteStyle = R.drawable.picture_icon_delete;
        // 原图按钮勾选样式  需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalControlStyle = R.drawable.picture_original_wechat_checkbox;
        // 原图文字颜色 需设置.isOriginalImageControl(true); 才有效
//        mPictureParameterStyle.pictureOriginalFontColor = ContextCompat.getColor(getContext(), R.color.app_color_white);
        // 外部预览界面是否显示删除按钮
        mPictureParameterStyle.pictureExternalPreviewGonePreviewDelete = true;
        // 设置NavBar Color SDK Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP有效
        mPictureParameterStyle.pictureNavBarColor = Color.parseColor("#62c2af");


        // 裁剪主题
        mCropParameterStyle = new PictureCropParameterStyle(
                ContextCompat.getColor(getContext(), R.color.app_color_grey),
                ContextCompat.getColor(getContext(), R.color.app_color_grey),
                Color.parseColor("#62c2af"),
                ContextCompat.getColor(getContext(), R.color.app_color_white),
                mPictureParameterStyle.isChangeStatusBarFontColor);

    }
}