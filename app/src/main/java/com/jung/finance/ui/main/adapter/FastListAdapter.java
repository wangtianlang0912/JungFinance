package com.jung.finance.ui.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemRecycleViewAdapter;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemTypeSupport;
import com.jung.finance.R;
import com.jung.finance.app.AppIntent;
import com.jung.finance.bean.FastModel;

import java.util.List;

/**
 * des:新闻列表适配器
 * Created by xsf
 * on 2016.09.10:49
 */
public class FastListAdapter extends MultiItemRecycleViewAdapter<FastModel.FastComment> {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_PHOTO_ITEM = 1;
    public static final int TYPE_AD_ITEM = 2;

    public FastListAdapter(Context context, final List<FastModel.FastComment> datas) {
        super(context, datas, new MultiItemTypeSupport<FastModel.FastComment>() {

            @Override
            public int getLayoutId(int type) {
                return R.layout.item_fast;
            }

            @Override
            public int getItemViewType(int position, FastModel.FastComment msg) {

                int typeVal = TYPE_ITEM;
                return typeVal;
            }
        });
    }

    @Override
    public void convert(ViewHolderHelper holder, FastModel.FastComment article) {
        switch (holder.getLayoutId()) {
            case R.layout.item_fast:
                setItemValues(holder, article, getPosition(holder));
                break;
        }
    }

    /**
     * 普通样式
     *
     * @param holder
     * @param article
     * @param position
     */
    private void setItemValues(final ViewHolderHelper holder, final FastModel.FastComment article, final int position) {

        holder.setText(R.id.news_summary_title_tv, article.getTitle());
        holder.setText(R.id.source_view, TextUtils.isEmpty(article.getAuthor()) ?
                mContext.getString(R.string.app_name) : article.getAuthor());
        holder.setText(R.id.see_view, article.getPv() + "");
        holder.setImageUrl(R.id.news_summary_photo_iv, article.getImage());
        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppIntent.intentToArticleDetail(mContext, article.getObjectId());

            }
        });
    }

}
