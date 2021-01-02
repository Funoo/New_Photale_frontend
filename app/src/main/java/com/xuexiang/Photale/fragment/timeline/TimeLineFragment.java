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


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.Photale.fragment.mainPage.mainPagefragment;
import com.xuexiang.xpage.annotation.Page;

@Page
public class TimeLineFragment extends BaseFragment {
    RecyclerView rv;
    TimeLineFragment.TimeLineAdapter timeLineAdapter;
    ArrayList<TimeLineModel> rvList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_time_line;
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
        initData();
        return view;
    }

    public static TimeLineFragment newInstance() {
        TimeLineFragment timeLineFragment = new TimeLineFragment();
        return timeLineFragment;
    }

    private void initData() {
        TimeLineModel timeLineDummmy1 = new TimeLineModel(1,R.drawable.ic_photale_img,"旅途", "中国广东","JAN","2020");
        rvList.add(timeLineDummmy1);
        TimeLineModel timeLineDummmy2 = new TimeLineModel(2,R.drawable.meat,"美食", "汕头","FEB","2020");
        rvList.add(timeLineDummmy2);
        TimeLineModel timeLineDummmy3 = new TimeLineModel(2,R.drawable.bg,"旅途", "中国广东","MAR","2020");
        rvList.add(timeLineDummmy3);
        TimeLineModel timeLineDummmy4 = new TimeLineModel(2,R.drawable.bg,"旅途", "中国广东","AUG","2020");
        rvList.add(timeLineDummmy4);
        TimeLineModel timeLineDummmy5 = new TimeLineModel(2,R.drawable.bg,"旅途", "中国广东","NOV","2020");
        rvList.add(timeLineDummmy5);
        TimeLineModel timeLineDummmy6 = new TimeLineModel(2,R.drawable.bg,"旅途", "中国广东","DEC","2020");
        rvList.add(timeLineDummmy6);
        TimeLineModel timeLineDummmy7 = new TimeLineModel(3,R.drawable.bg,"旅途", "中国广东","JAN","2021");
        rvList.add(timeLineDummmy7);
        timeLineAdapter.notifyDataSetChanged();
    }

//    private void initLayout() {
//        rv = findViewById(R.id.rv);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        rv.setLayoutManager(mLayoutManager);
//        timeLineAdapter = new TimeLineActivity.TimeLineAdapter(this,rvList);
//        rv.setAdapter(timeLineAdapter);
//    }

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