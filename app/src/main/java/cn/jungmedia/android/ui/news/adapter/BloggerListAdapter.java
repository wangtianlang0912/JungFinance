package com.jung.android.ui.news.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.jung.finance.R;
import com.jung.android.app.AppIntent;
import com.jung.android.bean.BloggerModel;

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
 * @date 2018/3/24. 下午5:01
 *
 *
 */
public class BloggerListAdapter extends CommonRecycleViewAdapter<BloggerModel.Media> {


    public BloggerListAdapter(Context context, List<BloggerModel.Media> datas) {
        super(context, R.layout.item_blogger, datas);
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
        holder.setText(R.id.content_view, TextUtils.isEmpty(media.getRemark()) ? "暂无简介" : media.getRemark());
        holder.setImageRoundUrl(R.id.logo_view, media.getCoverImage());
        holder.setText(R.id.public_view, String.format("他共发表%d篇文章", media.getArticleNum()));

        holder.setOnClickListener(R.id.logo_view, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppIntent.intentToBloggerInfo(mContext, media.getObjectId());
            }
        });
        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppIntent.intentToBloggerArticleDetail(mContext,
                        media.getObjectId(), media.getUid());
            }
        });
    }

}
