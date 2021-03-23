

package com.xuexiang.Photale.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.Photale.utils.LongPictures.LongPictureCreate;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.shadowbutton.ShadowButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Page(name = "智能排版")
public class SmartAlbumFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout;



    private List<LocalMedia> mSelectList = new ArrayList<>();

    private String tagString;

    private int tagListNum = 0;

    private List<String> tagInfo = new ArrayList<>();

    List<String> list;

    private LongPictureCreate drawLongPictureUtil;
    private int REQUEST_CODE_CHOOSE = 0x000011;
    private String resultPath;
    private List<String> mCurrentSelectedPath = new ArrayList<>();

    private PictureParameterStyle mPictureParameterStyle;
    private PictureCropParameterStyle mCropParameterStyle;


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
        return R.layout.fragment_smart_album;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
//        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(mAdapter = new ImageSelectGridAdapter(getActivity(), this));
//        mAdapter.setSelectList(mSelectList);
//        mAdapter.setSelectMax(8);
//        mAdapter.setOnItemClickListener((position, v) -> PictureSelector.create(SmartAlbumFragment.this).themeStyle(R.style.XUIPictureStyle).openExternalPreview(position, mSelectList));
//        linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
//        Utils.getPictureSelector(this)
//                .selectionMedia(mSelectList)
//                .forResult(PictureConfig.CHOOSE_REQUEST);
        initMarksView();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                case PictureConfig.CHOOSE_REQUEST:
//                    // 图片选择
//                    mSelectList = PictureSelector.obtainMultipleResult(data);
//                    mAdapter.setSelectList(mSelectList);
//                    mAdapter.notifyDataSetChanged();
//                    break;
//                default:
//                    break;
//            }
//        }
    }


    private void initMarksView() {
        list = new ArrayList<String>();
        for (int i = 1; i < 10; i++) {
            String str = "主题" + i;
            list.add(str);
        }
        for (int i = 0; i < list.size(); i++) {
            View view = View.inflate(getActivity(), R.layout.mark_layout, null);
            ShadowButton shadowButton1 = (ShadowButton) view.findViewById(R.id.themeBtn);
            TextView tv = (TextView) view.findViewById(R.id.test);
            tv.setText(list.get(i));
            tv.setTag(i);
            shadowButton1.setTag(i);
            view.setTag(false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) v.findViewById(R.id.test);
                    ShadowButton shadowButton1 = (ShadowButton) v.findViewById(R.id.themeBtn);
                    Log.i("dxl", "TextView click");
                    if ((Boolean) v.getTag()) {
                        v.setTag(false);
                        tv.setEnabled(false);
                        shadowButton1.setEnabled(false);
                    } else {
                        v.setTag(true);
                        tv.setEnabled(true);
                        shadowButton1.setEnabled(true);
                    }
                }
            });
            linearLayout.addView(view);
        }
    }
}