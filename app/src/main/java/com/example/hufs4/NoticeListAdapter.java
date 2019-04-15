package com.example.hufs4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter {

    private Context context;
    private List<Notice>noticeList;

    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View View, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.notice, null);
        TextView courseText = (TextView) v.findViewById(R.id.courseText);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);
        TextView contentText = (TextView) v.findViewById(R.id.contentText);


        courseText.setText(noticeList.get(i).getCourse());
        dateText.setText(noticeList.get(i).getDate());
        contentText.setText(noticeList.get(i).getContent());

        v.setTag(noticeList.get(i).getCourse());
        return v;
    }

}
