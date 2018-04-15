package cn.jungmedia.android.ui.score.adapter;


import android.content.Context;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;

import java.util.List;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.score.bean.ScoreBean;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/15. 下午10:35
 *
 *
 */
public class ScoreAdapter extends CommonRecycleViewAdapter<ScoreBean.Score> {


    public ScoreAdapter(Context context, List<ScoreBean.Score> datas) {
        super(context, R.layout.item_score, datas);
    }

    @Override
    public void convert(ViewHolderHelper holder, ScoreBean.Score score) {
        switch (holder.getLayoutId()) {
            case R.layout.item_score:
                setItemValues(holder, score, getPosition(holder));
                break;
        }
    }


    private void setItemValues(final ViewHolderHelper holder, final ScoreBean.Score score, final int position) {

        holder.setText(R.id.title_view, score.getRemark());
        holder.setText(R.id.score_view, "+ " + score.getScore());
        holder.setText(R.id.time_view, score.getShowTime());
        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BloggerModel.Media media = favorite.getMedia();
//                AppIntent.intentToBloggerInfo(mContext, media.getObjectId(), true);
            }
        });
    }


}
