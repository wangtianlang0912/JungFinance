package cn.jungmedia.android.ui.fav.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemRecycleViewAdapter;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemTypeSupport;

import java.util.List;

import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.bean.ArticleModel;
import cn.jungmedia.android.ui.fav.bean.NewsFavBean;

/**
 * des:新闻收藏列表适配器
 * Created by xsf
 * on 2016.09.10:49
 */
public class FastEditListAdapter extends MultiItemRecycleViewAdapter<NewsFavBean.Favorite> {

    OnDeleteBtnClickListener mClickListener;

    public FastEditListAdapter(Context context, final List<NewsFavBean.Favorite> datas, OnDeleteBtnClickListener clickListener) {
        super(context, datas, new MultiItemTypeSupport<NewsFavBean.Favorite>() {

            @Override
            public int getLayoutId(int type) {
                return R.layout.item_swipe_fast;
            }

            @Override
            public int getItemViewType(int position, NewsFavBean.Favorite msg) {

                return 0;
            }
        });

        this.mClickListener = clickListener;
    }

    @Override
    public void convert(ViewHolderHelper holder, NewsFavBean.Favorite article) {
        switch (holder.getLayoutId()) {
            case R.layout.item_swipe_fast:
                setItemValues(holder, article, getPosition(holder));
                break;
        }
    }


    /**
     * 普通样式
     *
     * @param holder
     * @param position
     */
    private void setItemValues(final ViewHolderHelper holder, final NewsFavBean.Favorite favorite, final int position) {

        final ArticleModel.Article article = favorite.getArticle();
        holder.setText(R.id.news_summary_title_tv, article.getTitle());
        ((TextView) holder.getView(R.id.news_summary)).setText(Html.fromHtml(article.getContent()));
        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (article.getMedia() != null) {
                    AppIntent.intentToBloggerArticleDetail(mContext,
                            article.getObjectId(),
                            article.getMedia().getObjectId());
                } else {
                    AppIntent.intentToArticleDetail(mContext,
                            article.getObjectId());
                }
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

        public void onBtnClicked(NewsFavBean.Favorite favorite);
    }
}