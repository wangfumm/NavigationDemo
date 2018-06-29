package com.hans.wangfu.navigation;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * CommonViewHolder
 * Created by brin on 2016/12/1.
 */
public class CommonViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConverView;
    private Context mContext;


    public CommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {

        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mContext = context;

        mConverView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConverView.setTag(this);
    }

    /**
     * viewholder的入口方法
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static CommonViewHolder getViewHolder(Context context, View convertView,
                                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new CommonViewHolder(context, parent, layoutId, position);
        } else {
            CommonViewHolder holder = ((CommonViewHolder) convertView.getTag());
            holder.mPosition = position;
            return holder;
        }
    }

    public View getConverView() {
        return mConverView;
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConverView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return ((T) view);
    }


    /**
     * 设置字体颜色
     *
     * @param viewId
     * @param resId
     * @return
     */
    public CommonViewHolder setTextColor(int viewId, int resId) {

        TextView tv = getView(viewId);
        tv.setTextColor(resId);
        return this;//链式编程

    }

    /**
     * 给textview设置值
     *
     * @param viewId
     * @param text
     * @return
     */
    public CommonViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;//链式编程
    }

    public CommonViewHolder setViewBackground(int viewId, int res) {
        View view = getView(viewId);
        view.setBackgroundDrawable(mContext.getResources().getDrawable(res));
        return this;//链式编程
    }


    public CommonViewHolder setText(int viewId, SpannableString sp) {
        TextView tv = getView(viewId);
        tv.setText(sp);
        return this;
    }

    /**
     * 给imageview设置resource
     *
     * @param viewId
     * @param resId
     * @return
     */
    public CommonViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
//        Glide.with(mContext).load(resId).into(iv);
        return this;
    }

    /**
     * 给imageview设置resource
     *
     * @param viewId
     * @param resId
     * @return
     */
    public CommonViewHolder setImageResourceChanged(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }


    /**
     * 给imageview设置bitmap
     *
     * @param viewId
     * @param bitmap
     * @return
     */
    public CommonViewHolder setImageBitMap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    //TextView隐藏
    public CommonViewHolder setTextViewVisibily(int viewId, int i) {
        TextView tv = getView(viewId);
        if (1 == i) {
            tv.setVisibility(View.VISIBLE);
        } else if (2 == i) {
            tv.setVisibility(View.INVISIBLE);
        } else if (3 == i) {
            tv.setVisibility(View.GONE);
        }
        return this;

    }


    //TextView隐藏
    public CommonViewHolder setViewVisibily(int viewId, int i) {
        View view = getView(viewId);
        if (1 == i) {
            view.setVisibility(View.VISIBLE);
        } else if (2 == i) {
            view.setVisibility(View.INVISIBLE);
        } else if (3 == i) {
            view.setVisibility(View.GONE);
        }
        return this;

    }

    //ImageView隐藏
    public CommonViewHolder setImageViewVisibily(int viewId, int i) {
        ImageView tv = getView(viewId);
        if (1 == i) {
            tv.setVisibility(View.VISIBLE);
        } else if (2 == i) {
            tv.setVisibility(View.INVISIBLE);
        } else if (3 == i) {
            tv.setVisibility(View.GONE);
        }
        return this;

    }

    //LinearLayout隐藏
    public CommonViewHolder setLinearLayoutVisibily(int viewId, int i) {
        LinearLayout tv = getView(viewId);
        if (1 == i) {
            tv.setVisibility(View.VISIBLE);
        } else if (2 == i) {
            tv.setVisibility(View.INVISIBLE);
        } else if (3 == i) {
            tv.setVisibility(View.GONE);
        }
        return this;

    }
}
