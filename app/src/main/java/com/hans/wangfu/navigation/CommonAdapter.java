package com.hans.wangfu.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用适配器
 * Created by brin on 2016/12/1.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int mLayoutId;


    public CommonAdapter(Context context, List<T> datas, int layoutId) {

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        mLayoutId = layoutId;

    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder = CommonViewHolder.getViewHolder(mContext, convertView,
                parent, mLayoutId, position);

        convert(holder, getItem(position));


        return holder.getConverView();
    }

    /**
     * 需要暴露的方法
     *
     * @param holder
     * @param t
     */
    public abstract void convert(CommonViewHolder holder, T t);


}
