package cn.jungmedia.android.ui.fav.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemRecycleViewAdapter;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemTypeSupport;

import java.util.List;

import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.bean.ActivityModel;
import cn.jungmedia.android.ui.fav.bean.ActiveFavBean;

/**
 * des:新闻收藏列表适配器
 * Created by xsf
 * on 2016.09.10:49
 */
public class ActiveEditListAdapter extends MultiItemRecycleViewAdapter<ActiveFavBean.Favorite> {

    OnDeleteBtnClickListener mClickListener;

    public ActiveEditListAdapter(Context context, final List<ActiveFavBean.Favorite> datas, OnDeleteBtnClickListener clickListener) {
        super(context, datas, new MultiItemTypeSupport<ActiveFavBean.Favorite>() {

            @Override
            public int getLayoutId(int type) {
                return R.layout.item_swipe_active;
            }

            @Override
            public int getItemViewType(int position, ActiveFavBean.Favorite msg) {

                return 0;
            }
        });

        this.mClickListener = clickListener;
    }

    @Override
    public void convert(ViewHolderHelper holder, ActiveFavBean.Favorite favorite) {
        switch (holder.getLayoutId()) {
            case R.layout.item_swipe_active:
                setItemValues(holder, favorite, getPosition(holder));
                break;
        }
    }

    /**
     * 普通样式
     *
     * @param holder
     * @param favorite
     * @param position
     */
    private void setItemValues(final ViewHolderHelper holder, final ActiveFavBean.Favorite favorite, final int position) {

        final ActivityModel.Activity activity = favorite.getActivity();
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

                    AppIntent.intentToActivityInfo(mContext, activity);

                    return;
                }
                AppIntent.intentToCommonWeb(mContext, R.string.activity, activity.getUrl());
            }
        });

        holder.setOnClickListener(R.id.delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onBtnClicked(favorite);
                }

            }
        });
    }


    public interface OnDeleteBtnClickListener {

        public void onBtnClicked(ActiveFavBean.Favorite favorite);
    }
}
