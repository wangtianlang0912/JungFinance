package com.jung.finance.ui.main.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemRecycleViewAdapter;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemTypeSupport;
import com.jung.finance.R;
import com.jung.finance.app.AppIntent;
import com.jung.finance.bean.ActivityModel;

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
 * @date 2018/3/31. 下午5:52
 *
 *
 */
public class ActivityListAdapter extends MultiItemRecycleViewAdapter<ActivityModel.Activity> {
    public static final int TYPE_ITEM = 0;

    public ActivityListAdapter(Context context, final List<ActivityModel.Activity> datas) {
        super(context, datas, new MultiItemTypeSupport<ActivityModel.Activity>() {

            @Override
            public int getLayoutId(int type) {
                return R.layout.item_activity;
            }

            @Override
            public int getItemViewType(int position, ActivityModel.Activity msg) {

                int typeVal = TYPE_ITEM;
                return typeVal;
            }
        });
    }

    @Override
    public void convert(ViewHolderHelper holder, ActivityModel.Activity activity) {
        switch (holder.getLayoutId()) {
            case R.layout.item_activity:
                setItemValues(holder, activity, getPosition(holder));
                break;
        }
    }

    /**
     * 普通样式
     *
     * @param holder
     * @param activity
     * @param position
     */
    private void setItemValues(final ViewHolderHelper holder, final ActivityModel.Activity activity, final int position) {

        holder.setText(R.id.title_view, activity.getName());
        holder.setImageUrl(R.id.logo_view, activity.getCoverImage());
        if (activity.getStatus() == ActivityModel.Status.IDLE) {
            holder.setText(R.id.lable_view, "未开始");
            holder.setText(R.id.time_view, activity.getStartTime());
            holder.setVisible(R.id.time_view, true);
        } else if (activity.getStatus() == ActivityModel.Status.SIGNUP) {
            holder.setText(R.id.lable_view, "报名中");
            holder.setText(R.id.time_view, activity.getStartTime());
            holder.setVisible(R.id.time_view, true);
        } else if (activity.getStatus() == ActivityModel.Status.START) {
            holder.setText(R.id.lable_view, "正在进行中");
            holder.setText(R.id.time_view, activity.getEndTime());
            holder.setVisible(R.id.time_view, true);
        } else if (activity.getStatus() == ActivityModel.Status.FINISH) {
            holder.setText(R.id.lable_view, "已结束");
            holder.setVisible(R.id.time_view, false);
        }
        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(activity.getUrl())) {
                    return;
                }
                AppIntent.intentToCommonWeb(mContext, R.string.activity, activity.getUrl());
            }
        });
    }

}
