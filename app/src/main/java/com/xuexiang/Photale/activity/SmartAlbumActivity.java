

package com.xuexiang.Photale.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.adapter.GridImageAdapter;
import com.xuexiang.Photale.components.FullyGridLayoutManager;
import com.xuexiang.Photale.components.LongPictureStyle.GlideEngine;
import com.xuexiang.Photale.components.PictureSelectorFragment;
import com.xuexiang.Photale.core.BaseActivity;
import com.xuexiang.Photale.utils.LongPictures.LongPictureCreate;
import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xui.widget.slideback.SlideBack;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.tool.ui.ToastUtil;

public class SmartAlbumActivity extends BaseActivity {

    private static final String TAG = "TestActivity";

    private static int flag = 0;

    private static int initView_flag = 0;

    private GridImageAdapter mAdapter;

    private int REQUEST_CODE_CHOOSE = 0x000011;

    private static final int CONTENT_VIEW_ID = 10101010;

    private LongPictureCreate drawLongPictureUtil;

    private String resultPath;

    private List<String> mCurrentSelectedPath = new ArrayList<>();

    private int maxSelectNum = 12;

    private PictureParameterStyle mPictureParameterStyle;
    private PictureCropParameterStyle mCropParameterStyle;

    private List<LocalMedia> mSelectList = new ArrayList<>();

    private int chooseMode = PictureMimeType.ofAll();

    private final static String INFO = PictureSelectorFragment.class.getSimpleName();
    private FragmentManager.FragmentLifecycleCallbacks mFragmentLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
        //        @Override
//        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
//            super.onFragmentCreated(fm, f, savedInstanceState);
//            System.out.println("fragment created");
//        }
        @Override
        public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
            super.onFragmentPreAttached(fm, f, context);
            Log.i(TAG, "onFragmentPreAttached: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
            super.onFragmentActivityCreated(fm, f, savedInstanceState);
            Log.i(TAG, "onFragmentActivityCreated: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
            super.onFragmentAttached(fm, f, context);
            Log.i(TAG, "onFragmentAttached: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
            super.onFragmentPreCreated(fm, f, savedInstanceState);
            Log.i(TAG, "onFragmentPreCreated: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
            super.onFragmentCreated(fm, f, savedInstanceState);
            Log.i(TAG, "onFragmentCreated: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState);
            Log.i(TAG, "onFragmentViewCreated: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentStarted(fm, f);
            Log.i(TAG, "onFragmentStarted: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentResumed(fm, f);
            Log.i(TAG, "onFragmentResumed: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentPaused(fm, f);
            Log.i(TAG, "onFragmentPaused: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentStopped(fm, f);
            Log.i(TAG, "onFragmentStopped: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
            super.onFragmentSaveInstanceState(fm, f, outState);
            Log.i(TAG, "onFragmentSaveInstanceState: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentViewDestroyed(fm, f);
            Log.i(TAG, "onFragmentViewDestroyed: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentDestroyed(fm, f);
            Log.i(TAG, "onFragmentDestroyed: "+f.getClass().getSimpleName());
        }

        @Override
        public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentDetached(fm, f);
            Log.i(TAG, "onFragmentDetached: "+f.getClass().getSimpleName());
        }
    };


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        System.out.println("oncreate");
        initView();
        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentPreAttached(@NotNull FragmentManager fm, @NotNull Fragment f, Context context) {
                super.onFragmentPreAttached(fm, f, context);
                System.out.println("FragmentPreAttached");
            }

            @Override
            public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
                super.onFragmentAttached(fm, f, context);
                System.out.println("FragmentAttached");
            }

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
                System.out.println("FragmentCreated");
            }

            @Override
            public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                super.onFragmentActivityCreated(fm, f, savedInstanceState);
                System.out.println("FragmentActivityCreated");
            }

            @Override
            public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState);
                System.out.println("FragmentViewCreated");
            }

            @Override
            public void onFragmentStarted(FragmentManager fm, Fragment f) {
                super.onFragmentStarted(fm, f);
                System.out.println("FragmentStarted");
                System.out.println(fm.findFragmentById(R.id.smart_album_fragment).getView().findViewById(R.id.recycler_view_2));
//                System.out.println(f.getView().findViewById(R.id.recycler_view_2));
                setView(f);
            }

            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                super.onFragmentResumed(fm, f);
                System.out.println("FragmentResumed");
                System.out.println(fm.findFragmentById(R.id.smart_album_fragment).getView());
            }

            @Override
            public void onFragmentPaused(FragmentManager fm, Fragment f) {
                super.onFragmentPaused(fm, f);
                System.out.println("FragmentPaused");
            }

            @Override
            public void onFragmentStopped(FragmentManager fm, Fragment f) {
                super.onFragmentStopped(fm, f);
                System.out.println("FragmentStopped");
            }

            @Override
            public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
                super.onFragmentSaveInstanceState(fm, f, outState);
                System.out.println("FragmentSaveInstanceState");
            }

            @Override
            public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
                super.onFragmentViewDestroyed(fm, f);
                System.out.println("FragmentViewDestroyed");
            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                super.onFragmentDestroyed(fm, f);
                System.out.println("FragmentDestroyed");
            }

            @Override
            public void onFragmentDetached(FragmentManager fm, Fragment f) {
                super.onFragmentDetached(fm, f);
                System.out.println("FragmentDetached");
            }
        }, true);
    }




    //
    private void initView() {
            System.out.println("step in initView");
            drawLongPictureUtil = new LongPictureCreate(SmartAlbumActivity.this);
            System.out.println("step in initView ahead of setListener");
            drawLongPictureUtil.setListener(new LongPictureCreate.Listener() {
                @Override
                public void onSuccess(String path) {
                    System.out.println("step in onSuccess");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultPath = path;
                        }
                    });
                }

                @Override
                public void onFail() {
                    System.out.println("step in onFail");
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            Toast.makeText(SmartAlbumActivity.this.getApplicationContext(), "onFail！",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
    }



    private void setView(Fragment fragment) {
        if (flag == 0){
            System.out.println(fragment.getView().findViewById(R.id.recycler_view_2));
            RecyclerView mRecyclerView =  fragment.getView().findViewById(R.id.recycler_view_2);
            FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                    4, GridLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4,
                    ScreenUtils.dip2px(getContext(), 8), false));
            mAdapter = new GridImageAdapter(getContext(), onAddPicClickListener);
            mAdapter.setSelectMax(maxSelectNum);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener((v, position) -> {
                List<LocalMedia> selectList = mAdapter.getData();
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case PictureConfig.TYPE_VIDEO:
                            // 预览视频
                            PictureSelector.create(SmartAlbumActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case PictureConfig.TYPE_AUDIO:
                            // 预览音频
                            PictureSelector.create(SmartAlbumActivity.this).externalPictureAudio(media.getPath());
                            break;
                        default:
                            PictureSelector.create(SmartAlbumActivity.this)
                                    .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                                    .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                    .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                    .openExternalPreview(position, selectList);
                            break;
                    }
                }
            });
            flag = 1;
        }else {
            System.out.println("flag is " + flag);
        }
    }



    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = true;
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(SmartAlbumActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .setPictureStyle(mPictureParameterStyle)
                        .isEnableCrop(true)// 是否裁剪 true or false
                        .maxSelectNum(10)// 最大图片选择数量 int
                        .minSelectNum(1)// 最小选择数量 int
                        .hideBottomControls(false)
                        .imageSpanCount(4)// 每行显示个数 int
                        .imageEngine(GlideEngine.createGlideEngine())
                        .selectionMode(PictureConfig.MULTIPLE)
                        .isCamera(true)
                        .selectionData(mSelectList)
                        .selectionData(mAdapter.getData())
                        .isOriginalImageControl(true)
                        .forResult(new MyResultCallback(mAdapter));
//                .forResult(REQUEST_CODE_CHOOSE);结果回调onActivityResult code
            } else {
                // 单独拍照
                PictureSelector.create(SmartAlbumActivity.this)
                        .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .setPictureStyle(mPictureParameterStyle)
                        .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                        .setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
                        .maxSelectNum(10)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
//                        .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
                        .compressQuality(60)// 图片压缩后输出质量
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .isPreviewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        .selectionData(mSelectList)
                        .selectionData(mAdapter.getData())// 是否传入已选图片
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(new MyResultCallback(mAdapter));
            }
        }

    };


    public void generateLongImages(View view){
        System.out.println(mCurrentSelectedPath.size());
        if (mCurrentSelectedPath.size() <1 ) {
            ToastUtil.getInstance()._short(this,"请选择长图");
            return;
        }
        System.out.println("resultPath is " + resultPath);
        ImagePreview
                .getInstance()
                .setContext(SmartAlbumActivity.this)
                .setIndex(0)
                .setImage(resultPath)
                .start();
    }

    /**
     * 返回结果回调
     */
    private class MyResultCallback implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<GridImageAdapter> mAdapterWeakReference;

        public MyResultCallback(GridImageAdapter adapter) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            System.out.println("jinru onResult");
            List<LocalMedia> selectList = result;
            mCurrentSelectedPath.clear();
            final StringBuffer stringBuffer = new StringBuffer();
            for (LocalMedia paths : selectList) {
                stringBuffer.append(paths.getCutPath()).append("\n");
                mCurrentSelectedPath.add(paths.getCutPath());
            }
            System.out.println("ahead of  Glide.with");
            Glide.with(SmartAlbumActivity.this).asBitmap().load("https://source.unsplash.com/random").into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    System.out.println("in Glide.with ahead of setButtomBitmap");
                    drawLongPictureUtil.setButtomBitmap(resource);
                    System.out.println("in Glide.with ahead of setData");
                    drawLongPictureUtil.setData(mCurrentSelectedPath);
                    System.out.println("in Glide.with ahead of startDraw");
                    drawLongPictureUtil.startDraw();
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
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

    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
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
                    Log.i(INFO, "宽高: " + media.getWidth() + "x" + media.getHeight());
                    Log.i(INFO, "Size: " + media.getSize());

                    // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
                }
//                mAdapter.setList(selectList);
//                mAdapter.notifyDataSetChanged();
            }
        }
    }
    public void btnEvent(View view) {
        System.out.println("click");
    }

    private void clearCache() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //PictureFileUtils.deleteCacheDirFile(this, PictureMimeType.ofImage());
            PictureFileUtils.deleteAllCacheDirFile(getContext());
        } else {
            PermissionChecker.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE) {// 存储权限
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    PictureFileUtils.deleteCacheDirFile(getContext(), PictureMimeType.ofImage());
                } else {
                    Toast.makeText(SmartAlbumActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAdapter != null && mAdapter.getData() != null && mAdapter.getData().size() > 0) {
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
            if (BroadcastAction.ACTION_DELETE_PREVIEW_POSITION.equals(action)) {
                // 外部预览删除按钮回调
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    ToastUtils.s(getContext(), "delete image index:" + position);
                    mAdapter.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            BroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver,
                    BroadcastAction.ACTION_DELETE_PREVIEW_POSITION);
        }
        flag = 0;
        initView_flag = 0;
        System.out.println("into OnDestroy");
    }

    public Context getContext() {
        return this;
    }
}