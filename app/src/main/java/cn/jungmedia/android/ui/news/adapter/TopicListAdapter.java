package cn.jungmedia.android.ui.news.adapter;


import android.content.Context;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import cn.jungmedia.android.bean.TopicModel;
import cn.jungmedia.android.R;

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
public class TopicListAdapter extends CommonRecycleViewAdapter<TopicModel.Theme> {


    public TopicListAdapter(Context context, List<TopicModel.Theme> datas) {
        super(context, R.layout.item_topic, datas);
    }

    @Override
    public void convert(ViewHolderHelper holder, TopicModel.Theme media) {
        switch (holder.getLayoutId()) {
            case R.layout.item_topic:
                setItemValues(holder, media, getPosition(holder));
                break;
        }
    }


    private void setItemValues(final ViewHolderHelper holder, final TopicModel.Theme theme, final int position) {

        holder.setText(R.id.title_view, theme.getTitle());
        holder.setImageUrl(R.id.logo_view, theme.getWapImage());
        holder.setText(R.id.pubtime_view, theme.getPubTime());
        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NewsDetailActivity.startAction(mContext, holder.getView(R.id.news_summary_photo_iv), String.valueOf(article.getObjectId()), article.getImage());
            }
        });
    }

}
