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

package com.xuexiang.Photale.activity.timeline;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuexiang.Photale.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.Photale.core.BaseActivity;
import com.xuexiang.Photale.fragment.mainPage.mainPagefragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimeLineActivity extends BaseActivity {

    RecyclerView rv;
    TimeLineAdapter timeLineAdapter;
    ArrayList<TimeLineModel> rvList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        initLayout();
        initData();
    }


    private void initData() {
        TimeLineModel timeLineDummmy1 = new TimeLineModel(1,R.drawable.ic_photale_img,"BlackBerry", "Rs 25000.00","JAN","2018");
        rvList.add(timeLineDummmy1);
        TimeLineModel timeLineDummmy2 = new TimeLineModel(2,R.drawable.bg_girl,"Samsung", "Rs 65000.00","OCt","2017");
        rvList.add(timeLineDummmy2);
        TimeLineModel timeLineDummmy3 = new TimeLineModel(2,R.drawable.bg,"One Plus", "Rs 45000.00","AUG","2018");
        rvList.add(timeLineDummmy3);
        TimeLineModel timeLineDummmy4 = new TimeLineModel(2,R.drawable.bg,"Iphone", "Rs 88500.00","NOV","2016");
        rvList.add(timeLineDummmy4);
        TimeLineModel timeLineDummmy5 = new TimeLineModel(2,R.drawable.bg,"Samsung", "Rs 65000.00","MAR","2015");
        rvList.add(timeLineDummmy5);
        TimeLineModel timeLineDummmy6 = new TimeLineModel(2,R.drawable.bg,"One Plus", "Rs 45000.00","FEB","2018");
        rvList.add(timeLineDummmy6);
//        TimeLineModel timeLineDummmy7 = new TimeLineModel(3,"http://121.5.152.42/images/bg_girl.png","Iphone", "Rs 88500.00","MAY","2006");
//        rvList.add(timeLineDummmy7);
        timeLineAdapter.notifyDataSetChanged();
    }

    private void initLayout() {
        rv = findViewById(R.id.rv);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
        timeLineAdapter = new TimeLineAdapter(this,rvList);
        rv.setAdapter(timeLineAdapter);
    }

    public class TimeLineAdapter extends RecyclerView.Adapter {
        private static final int VIEW_TYPE_DEAL_FIRST_ITEM = 1;
        private static final int VIEW_TYPE_DEAL_MIDDLE_ITEM = 2;
        private static final int VIEW_TYPE_DEAL_LAST_ITEM = 3;

        private Context mContext;
        private ArrayList<TimeLineModel> mDealsList;
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
                return new FirstDealHolder(view);
            } else if (viewType == VIEW_TYPE_DEAL_MIDDLE_ITEM) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item__card_type_middle, parent, false);
                return new MiddleDealHolder(view);
            } else if(viewType == VIEW_TYPE_DEAL_LAST_ITEM) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_card_end, parent, false);
                return new LastDealHolder(view);
            }
            return null;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TimeLineModel dealsModel = mDealsList.get(position);
            switch (holder.getItemViewType()) {
                case VIEW_TYPE_DEAL_FIRST_ITEM:
                    ((FirstDealHolder) holder).bind(dealsModel);
                    break;
                case VIEW_TYPE_DEAL_MIDDLE_ITEM:
                    ((MiddleDealHolder) holder).bind(dealsModel);
                    break;
                case VIEW_TYPE_DEAL_LAST_ITEM:
                    ((LastDealHolder) holder).bind(dealsModel);
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
                productName.setText(dealsModel.productName);
                productPrice.setText(dealsModel.productPrice);
            }
        }
    }
}
