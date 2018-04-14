package cn.jungmedia.android.ui.news.adapter;


import android.content.Context;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import cn.jungmedia.android.R;
import cn.jungmedia.android.bean.CommentCreateModel;

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
 * @date 2018/4/7. 下午11:15
 *
 *
 */
public class CommentListAdapter extends CommonRecycleViewAdapter<CommentCreateModel.Comment> {


    public CommentListAdapter(Context context, List<CommentCreateModel.Comment> datas) {
        super(context, R.layout.item_comment, datas);
    }

    @Override
    public void convert(ViewHolderHelper holder, CommentCreateModel.Comment media) {
        switch (holder.getLayoutId()) {
            case R.layout.item_comment:
                setItemValues(holder, media, getPosition(holder));
                break;
        }
    }


    private void setItemValues(final ViewHolderHelper holder, final CommentCreateModel.Comment comment, final int position) {
        if (comment.getUser() != null) {
            holder.setText(R.id.title_view, comment.getUser().getNick());
            holder.setText(R.id.pubtime_view, comment.getcTimeStr());
            holder.setText(R.id.replay_num_view, comment.getrCount() +"");
            holder.setText(R.id.content_view, comment.getBody());
            holder.setImageRoundUrl(R.id.logo_view, comment.getUser().getLogo());
        }
    }
}