package com.xuexiang.Photale.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.xuexiang.Photale.R;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.Photale.activity.RecordContentActivity;
import com.xuexiang.Photale.fragment.record.Record;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<com.xuexiang.Photale.adapter.RecordAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView recordTitleText;
        ImageView recordImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recordTitleText = itemView.findViewById(R.id.record_title);
            recordImage = itemView.findViewById(R.id.record_image);
        }
    }

    private Context mContext;
    private List<Record> mRecordList;

    public RecordAdapter(Context context, List<Record> recordList) {
        mContext = context;
        mRecordList = recordList;
    }

    public void addRecord(Record record) {
        mRecordList.add(record);
        notifyItemInserted(mRecordList.size()-1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            Record record = mRecordList.get(holder.getAdapterPosition());
            RecordContentActivity.actionStart(mContext, record.getTitle(), record.getContent(), record.getImagePath());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Record record = mRecordList.get(position);
        holder.recordTitleText.setText(record.getTitle());
        Bitmap bitmap = BitmapFactory.decodeFile(record.getImagePath());
        holder.recordImage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }

}
