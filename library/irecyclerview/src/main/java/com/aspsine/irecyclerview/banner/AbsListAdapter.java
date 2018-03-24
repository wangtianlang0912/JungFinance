package com.aspsine.irecyclerview.banner;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.leon.common.commonutils.ImageLoaderUtils;

import java.util.List;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/24. 下午11:29
 *
 *
 */
abstract class AbsListAdapter<T, ViewHolder> extends BaseAdapter {

    protected List<T> mList;
    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected int currentPos = 0;

    /**
     * @param mcontext
     * @param list
     */
    public AbsListAdapter(Context mcontext, List<T> list) {
        this(mcontext, null, list);

    }

    public AbsListAdapter(Context mcontext, ListView listView, List<T> list) {

        mContext = mcontext;
        mList = list;
        mLayoutInflater = LayoutInflater.from(mcontext);
    }

    public Context getContext() {
        return mContext;
    }

    public void setImageLoader(final ImageView imageView, final String url) {

        ImageLoaderUtils.display(mContext, imageView, url);

    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public T getItem(int position) {
        return mList != null ? mList.get(position) : null;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getCurrentPosition() {
        return currentPos;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();

        } else {
            int layoutId = getItemViewLayout();
            if (layoutId == 0) {
                throw new RuntimeException("itemLayoutid == 0");
            }
            convertView = mLayoutInflater.inflate(layoutId, parent, false);
            if (convertView == null) {
                throw new RuntimeException("convertView is empty");
            }
            holder = buildItemViewHolder(convertView);
            if (holder == null) {
                throw new RuntimeException("holder is empty");
            }
            convertView.setTag(holder);
        }

        T object = getItem(position);
        currentPos = position;
        if (object != null) {
            bindDataToView(object, holder);
        }
        return convertView;
    }


    protected abstract int getItemViewLayout();


    protected abstract ViewHolder buildItemViewHolder(View convertView);


    protected abstract void bindDataToView(T t, ViewHolder holder);

}
