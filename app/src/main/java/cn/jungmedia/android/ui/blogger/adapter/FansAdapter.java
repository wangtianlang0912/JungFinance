package cn.jungmedia.android.ui.blogger.adapter;


import android.content.Context;
import android.view.View;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;

import java.util.List;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.blogger.bean.FansBean;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/15. 下午10:12
 *
 *
 */
public class FansAdapter extends CommonRecycleViewAdapter<FansBean.Favorite> {


    public FansAdapter(Context context, List<FansBean.Favorite> datas) {
        super(context, R.layout.item_fans, datas);
    }

    @Override
    public void convert(ViewHolderHelper holder, FansBean.Favorite media) {
        switch (holder.getLayoutId()) {
            case R.layout.item_fans:
                setItemValues(holder, media, getPosition(holder));
                break;
        }
    }


    private void setItemValues(final ViewHolderHelper holder, final FansBean.Favorite favorite, final int position) {

        holder.setText(R.id.title_view, favorite.getUser().getUser().getNick());
        holder.setImageRoundUrl(R.id.logo_view, favorite.getUser().getUser().getLogo());

        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BloggerModel.Media media = favorite.getMedia();
//                AppIntent.intentToBloggerInfo(mContext, media.getObjectId(), true);
            }
        });
    }


}
