package com.jung.finance.ui.news.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemRecycleViewAdapter;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemTypeSupport;
import com.jung.finance.R;
import com.jung.finance.api.ApiConstants;
import com.jung.finance.app.AppIntent;
import com.jung.finance.bean.ArticleModel;

import java.util.List;

/**
 * des:新闻列表适配器
 * Created by xsf
 * on 2016.09.10:49
 */
public class NewListAdapter extends MultiItemRecycleViewAdapter<ArticleModel.Article> {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_PHOTO_ITEM = 1;
    public static final int TYPE_AD_ITEM = 2;

    public NewListAdapter(Context context, final List<ArticleModel.Article> datas) {
        super(context, datas, new MultiItemTypeSupport<ArticleModel.Article>() {

            @Override
            public int getLayoutId(int type) {
                if (type == TYPE_PHOTO_ITEM) {
                    return R.layout.item_news_photo;

                } else if (type == TYPE_AD_ITEM) {
                    return R.layout.item_ad;
                } else {
                    return R.layout.item_news;
                }
            }

            @Override
            public int getItemViewType(int position, ArticleModel.Article msg) {

                int typeVal = TYPE_ITEM;
                switch (msg.getType()) {

                    case DEFAULT:

                        typeVal = TYPE_ITEM;
                        break;
                    case AD:
                        typeVal = TYPE_AD_ITEM;
                        break;
                    case PIC:
                        typeVal = TYPE_PHOTO_ITEM;
                        break;
                    default:
                        break;
                }
                return typeVal;
            }
        });
    }

    @Override
    public void convert(ViewHolderHelper holder, ArticleModel.Article article) {
        switch (holder.getLayoutId()) {
            case R.layout.item_news:
                setItemValues(holder, article, getPosition(holder));
                break;
            case R.layout.item_news_photo:
//                setPhotoItemValues(holder, article, getPosition(holder));
                break;
            case R.layout.item_ad:
                setAdItemValues(holder, article, getPosition(holder));
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
    private void setItemValues(final ViewHolderHelper holder, final ArticleModel.Article article, final int position) {

        holder.setText(R.id.news_summary_title_tv, article.getTitle());
        holder.setText(R.id.source_view, TextUtils.isEmpty(article.getSource()) ?
                mContext.getString(R.string.app_name) : article.getSource());
        holder.setText(R.id.see_view, article.getPv() + "");
        holder.setImageUrl(R.id.news_summary_photo_iv, article.getImage());
        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (article.getMedia() != null) {
                    AppIntent.intentToBloggerArticleDetail(mContext,
                            String.format(ApiConstants.URL + "media/i-%d.html", article.getObjectId()),
                            article.getObjectId(),
                            article.getMedia().getObjectId());
                } else {
                    AppIntent.intentToArticleDetail(mContext,
                            String.format(ApiConstants.URL + "news/i-%d.html", article.getObjectId()),
                            article.getObjectId());
                }
            }
        });
    }

    private void setAdItemValues(final ViewHolderHelper holder, final ArticleModel.Article article, final int position) {

        holder.setText(R.id.title_view, article.getTitle());
        holder.setImageUrl(R.id.logo_view, article.getImage());
        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppIntent.intentToCommonWeb(mContext, R.string.ad, article.getSummary());
            }
        });
    }

}
