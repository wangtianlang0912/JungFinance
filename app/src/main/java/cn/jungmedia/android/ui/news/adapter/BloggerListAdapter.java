package cn.jungmedia.android.ui.news.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;

import java.util.List;

import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.bean.BloggerModel;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/24. 下午5:01
 *
 *
 */
public class BloggerListAdapter extends CommonRecycleViewAdapter<BloggerModel.Media> {

    private OnSubscribeBtnClickListener mClickListener;

    public BloggerListAdapter(Context context, List<BloggerModel.Media> datas, OnSubscribeBtnClickListener listener) {
        super(context, R.layout.item_blogger, datas);
        this.mClickListener = listener;
    }

    @Override
    public void convert(ViewHolderHelper holder, BloggerModel.Media media) {
        switch (holder.getLayoutId()) {
            case R.layout.item_blogger:
                setItemValues(holder, media, getPosition(holder));
                break;
        }
    }


    private void setItemValues(final ViewHolderHelper holder, final BloggerModel.Media media, final int position) {

        holder.setText(R.id.title_view, media.getName());
        holder.setText(R.id.sub_num, media.getGznum() + "");

        holder.setImageResource(R.id.subscribe_btn, media.getFavorite() == null ? R.drawable.btn_pre_subscribe :
                R.drawable.btn_subscribed);
        holder.setTag(R.id.subscribe_btn, media.getFavorite() != null);
        holder.setTag(R.id.subscribe_btn, media.getFavorite() != null ? media.getFavorite().getObjectId() : media.getObjectId());
        holder.setTag(R.id.subscribe_btn, R.id.tag_first, media.getFavorite() != null);
        holder.setText(R.id.content_view, TextUtils.isEmpty(media.getRemark()) ? "暂无简介" : media.getRemark());
        if(!TextUtils.isEmpty(media.getCoverImage())){
            holder.setImageRoundUrl(R.id.logo_view, media.getCoverImage());
        }else {
            holder.setImageResource(R.id.logo_view, R.drawable.blant_logo);
        }
        holder.setText(R.id.public_view, String.format("他共发表%d篇文章", media.getArticleNum()));

        holder.setOnClickListener(R.id.subscribe_btn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mClickListener != null) {
                    mClickListener.onSubscribeChanged(media);
                }
            }
        });

        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppIntent.intentToBloggerInfo(mContext, media.getObjectId(), media.getFavorite() != null);
//                AppIntent.intentToArticleDetail(mContext, media.getObjectId());
            }
        });
    }


    public interface OnSubscribeBtnClickListener {

        public void onSubscribeChanged(BloggerModel.Media media);
    }
}
