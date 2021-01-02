package com.xuexiang.Photale.fragment.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.xuexiang.Photale.R;
import com.xuexiang.Photale.adapter.RecordAdapter;
import com.xuexiang.Photale.core.BaseFragment;
import com.xuexiang.xpage.annotation.Page;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

@Page
public class RecordTitleFragment extends BaseFragment {

    private RecordAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_main;
    }

    @Override
    protected void initViews() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_title, container, false);
        RecyclerView recordTitleRecyclerView = view.findViewById(R.id.record_title_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recordTitleRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecordAdapter(getActivity(), new ArrayList<>());
        recordTitleRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public RecordAdapter getAdapter() {
        return mAdapter;
    }
}
