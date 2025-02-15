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

package com.xuexiang.Photale.fragment.timeline;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuexiang.Photale.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.Photale.activity.timeline.TimeLineActivity;
import com.xuexiang.Photale.activity.timeline.TimeLineModel;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.Photale.fragment.mainPage.mainPagefragment;
import com.xuexiang.xpage.annotation.Page;

@Page
public class TimeLineFragment extends BaseFragment {
    RecyclerView rv;
    TimeLineFragment.TimeLineAdapter timeLineAdapter;
    private static ArrayList<TimeLineModel> rvList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_time_line;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    @Override
    protected void initViews() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("----------------------进入了 onCreateView--------------------------");
        View view = View.inflate(getActivity(),R.layout.fragment_time_line,null);
        rv = view.findViewById(R.id.rv);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);
        timeLineAdapter = new TimeLineFragment.TimeLineAdapter(getActivity(),rvList);
        rv.setAdapter(timeLineAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        onHiddenChanged(false);
        timeLineAdapter.notifyDataSetChanged();
        System.out.println("TimeLine on Resume");
    }

    public static TimeLineFragment newInstance() {
        TimeLineFragment timeLineFragment = new TimeLineFragment();
        return timeLineFragment;
    }

    private void initData() {
        System.out.println("init TimeLine initData()");
        TimeLineModel timeLineDummmy1 = new TimeLineModel(1,R.drawable.sunset,"家中夕阳", "中国广东","JAN","2020");
        rvList.add(timeLineDummmy1);
        TimeLineModel timeLineDummmy2 = new TimeLineModel(2,R.drawable.bg_girl,"美食", "汕头","FEB","2020");
        rvList.add(timeLineDummmy2);
        TimeLineModel timeLineDummmy3 = new TimeLineModel(2,R.drawable.river,"美丽的河", "中国广东","MAR","2020");
        rvList.add(timeLineDummmy3);
        TimeLineModel timeLineDummmy4 = new TimeLineModel(2,R.drawable.bg,"旅途", "中国广东","AUG","2020");
        rvList.add(timeLineDummmy4);
        TimeLineModel timeLineDummmy5 = new TimeLineModel(2,R.drawable.meat,"牛肉", "中国广东汕头","DEC","2020");
        rvList.add(timeLineDummmy5);
        TimeLineModel timeLineDummmy6 = new TimeLineModel(3,R.drawable.sakura,"春游赏樱", "山东青岛","JAN","2021");
        rvList.add(timeLineDummmy6);
        timeLineAdapter.notifyDataSetChanged();
    }

//    private void initLayout() {
//        rv = findViewById(R.id.rv);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        rv.setLayoutManager(mLayoutManager);
//        timeLineAdapter = new TimeLineActivity.TimeLineAdapter(this,rvList);
//        rv.setAdapter(timeLineAdapter);
//    }


    public Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            //Log.i("image", "getBitmapFromUrl: true");
            return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public class TimeLineAdapter extends RecyclerView.Adapter {
        private static final int VIEW_TYPE_DEAL_FIRST_ITEM = 1;
        private static final int VIEW_TYPE_DEAL_MIDDLE_ITEM = 2;
        private static final int VIEW_TYPE_DEAL_LAST_ITEM = 3;

        private Context mContext;
        private ArrayList<TimeLineModel> mDealsList;

        private class MyTask extends AsyncTask<String,String,Bitmap>
        {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Bitmap doInBackground(String... strings) {
                System.out.println("in doInBackground and strings[0] is " + strings[0]);
                publishProgress();
                return getBitmapFromUrl(strings[0]);
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }

        public TimeLineAdapter(Context context, ArrayList<TimeLineModel> mDealsListData) {
            mContext = context;
            mDealsList = mDealsListData;
        }
        @Override
        public int getItemCount() {
            return mDealsList.size();
        }
        @Override
        public int getItemViewType(int position) {
            TimeLineModel deal = mDealsList.get(position);
            if (deal.getType() == 1) {
                return VIEW_TYPE_DEAL_FIRST_ITEM;
            } else if(deal.getType() == 2) {
                return VIEW_TYPE_DEAL_MIDDLE_ITEM;
            } else {
                return VIEW_TYPE_DEAL_LAST_ITEM;
            }
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == VIEW_TYPE_DEAL_FIRST_ITEM) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_type_start, parent, false);
                return new TimeLineFragment.TimeLineAdapter.FirstDealHolder(view);
            } else if (viewType == VIEW_TYPE_DEAL_MIDDLE_ITEM) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item__card_type_middle, parent, false);
                return new TimeLineFragment.TimeLineAdapter.MiddleDealHolder(view);
            } else if(viewType == VIEW_TYPE_DEAL_LAST_ITEM) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_end, parent, false);
                return new TimeLineFragment.TimeLineAdapter.LastDealHolder(view);
            }
            return null;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TimeLineModel dealsModel = mDealsList.get(position);
            switch (holder.getItemViewType()) {
                case VIEW_TYPE_DEAL_FIRST_ITEM:
                    ((TimeLineFragment.TimeLineAdapter.FirstDealHolder) holder).bind(dealsModel);
                    break;
                case VIEW_TYPE_DEAL_MIDDLE_ITEM:
                    ((TimeLineFragment.TimeLineAdapter.MiddleDealHolder) holder).bind(dealsModel);
                    break;
                case VIEW_TYPE_DEAL_LAST_ITEM:
                    ((TimeLineFragment.TimeLineAdapter.LastDealHolder) holder).bind(dealsModel);
                    break;
            }
        }
        private class FirstDealHolder extends RecyclerView.ViewHolder {
            TextView monthPurchased, yearPurchased,productName,productPrice;
            CircleImageView dealProductImage;
            FirstDealHolder(View itemView) {
                super(itemView);
                monthPurchased =  itemView.findViewById(R.id.monthPurchased);
                yearPurchased =  itemView.findViewById(R.id.yearPurchased);
                dealProductImage = itemView.findViewById(R.id.dealProductImage);
                productName = itemView.findViewById(R.id.textView32);
                productPrice = itemView.findViewById(R.id.productPrice);
            }
            void bind(TimeLineModel dealsModel) {
                monthPurchased.setText(dealsModel.month);
                yearPurchased.setText(dealsModel.year);
//                dealProductImage.setImageBitmap(null);
//                new MyTask().execute(dealsModel.imageFileName);
                dealProductImage.setImageResource(dealsModel.imageFileName);
                productName.setText(dealsModel.productName);
                productPrice.setText(dealsModel.productPrice);
            }
        }
        private class MiddleDealHolder extends RecyclerView.ViewHolder {
            TextView monthPurchased, yearPurchased,productName,productPrice;
            CircleImageView dealProductImage;
            MiddleDealHolder(View itemView) {
                super(itemView);
                monthPurchased =  itemView.findViewById(R.id.monthPurchased);
                yearPurchased =  itemView.findViewById(R.id.yearPurchased);
                dealProductImage = itemView.findViewById(R.id.dealProductImage);
                productName = itemView.findViewById(R.id.textView32);
                productPrice = itemView.findViewById(R.id.productPrice);
            }
            void bind(TimeLineModel dealsModel) {
                monthPurchased.setText(dealsModel.month);
                yearPurchased.setText(dealsModel.year);
//                dealProductImage.setImageBitmap(null);
//                new MyTask().execute(dealsModel.imageFileName);
                dealProductImage.setImageResource(dealsModel.imageFileName);
                productName.setText(dealsModel.productName);
                productPrice.setText(dealsModel.productPrice);
            }
        }

        private class LastDealHolder extends RecyclerView.ViewHolder {
            TextView monthPurchased, yearPurchased,productName,productPrice;
            CircleImageView dealProductImage;
            LastDealHolder(View itemView) {
                super(itemView);
                monthPurchased =  itemView.findViewById(R.id.monthPurchased);
                yearPurchased =  itemView.findViewById(R.id.yearPurchased);
                dealProductImage = itemView.findViewById(R.id.dealProductImage);
                productName = itemView.findViewById(R.id.textView32);
                productPrice = itemView.findViewById(R.id.productPrice);
            }
            void bind(TimeLineModel dealsModel) {
                monthPurchased.setText(dealsModel.month);
                yearPurchased.setText(dealsModel.year);
                dealProductImage.setImageResource(dealsModel.imageFileName);
//                dealProductImage.setImageBitmap(null);
//                new MyTask().execute(dealsModel.imageFileName);
                productName.setText(dealsModel.productName);
                productPrice.setText(dealsModel.productPrice);
            }
        }
    }
}