package cn.jungmedia.android.ui.blogger.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;

import java.util.List;

import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.bean.BloggerModel;
import cn.jungmedia.android.ui.blogger.bean.BloggerFavBean;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/15. 下午8:47
 *
 *
 */
public class BloggerFavAdapter extends CommonRecycleViewAdapter<BloggerFavBean.Favorite> {

    OnDeleteBtnClickListener btnClickListener;

    public BloggerFavAdapter(Context context, List<BloggerFavBean.Favorite> datas, OnDeleteBtnClickListener listener) {
        super(context, R.layout.item_blogger_fav, datas);
        this.btnClickListener = listener;
    }

    @Override
    public void convert(ViewHolderHelper holder, BloggerFavBean.Favorite media) {
        switch (holder.getLayoutId()) {
            case R.layout.item_blogger_fav:
                setItemValues(holder, media, getPosition(holder));
                break;
        }
    }


    private void setItemValues(final ViewHolderHelper holder, final BloggerFavBean.Favorite favorite, final int position) {

        holder.setText(R.id.title_view, favorite.getTitle());
        holder.setText(R.id.content_view, TextUtils.isEmpty(favorite.getSummary()) ? "暂无简介" : favorite.getSummary());
        holder.setImageRoundUrl(R.id.logo_view, favorite.getImage());

        holder.setOnClickListener(R.id.cancel_action, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnClickListener != null) {
                    btnClickListener.onBtnClicked(favorite);
                }

            }
        });
        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BloggerModel.Media media = favorite.getMedia();
                AppIntent.intentToBloggerInfo(mContext, media.getObjectId(), true);
            }
        });
    }


    public interface OnDeleteBtnClickListener {

        public void onBtnClicked(BloggerFavBean.Favorite favorite);
    }
}

