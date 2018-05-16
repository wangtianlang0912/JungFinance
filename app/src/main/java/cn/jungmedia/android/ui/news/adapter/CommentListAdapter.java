package cn.jungmedia.android.ui.news.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;

import java.util.List;

import cn.jungmedia.android.R;
import cn.jungmedia.android.bean.CommentCreateModel;

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


    private OnItemReplayBtnClickListener mClickListener;


    public CommentListAdapter(Context context, List<CommentCreateModel.Comment> datas, OnItemReplayBtnClickListener listener) {
        super(context, R.layout.item_comment, datas);
        this.mClickListener = listener;
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
            holder.setText(R.id.replay_num_view, comment.getrCount() + "");
            if (comment.getTouid() != 0 && !TextUtils.isEmpty(comment.getParentTitle())) {
                holder.setVisible(R.id.replay_content_layout, true);
                holder.setText(R.id.original_title_view, comment.getParentTitle());
                holder.setText(R.id.original_content_view, comment.getParentContent());
            } else {
                holder.setVisible(R.id.replay_content_layout, false);
            }
            holder.setText(R.id.content_view, comment.getBody());
            holder.setImageRoundUrl(R.id.logo_view, comment.getUser().getLogo());
            holder.setOnClickListener(R.id.replay_layout, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onClick(comment.getObjectId());
                    }
                }
            });
        }
    }


    public interface OnItemReplayBtnClickListener {

        public void onClick(int objectd);
    }
}