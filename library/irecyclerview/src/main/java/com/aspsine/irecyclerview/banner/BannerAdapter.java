package com.aspsine.irecyclerview.banner;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.aspsine.irecyclerview.R;
import java.util.List;

public class BannerAdapter extends AbsListAdapter<BannerBean, BannerViewHolder> {

    LayoutParams imageParam = null;
    private int defaultLogoRes = 0;
    private Context mContext;

    public BannerAdapter(Context mcontext,
                         List<BannerBean> list,
                         LayoutParams imageParam) {
        super(mcontext, list);
        this.mContext = mcontext;
        this.imageParam = imageParam;
        if (imageParam == null) {
            calculateScaleHeight();
        }
    }

    private void calculateScaleHeight() {
        float imageWidth = 678f;
        float imageHeight = 300f;
        setScaleVal(imageWidth, imageHeight);
    }

    @Override
    protected int getItemViewLayout() {
        return R.layout.banner_gallery_item;
    }

    @Override
    protected BannerViewHolder buildItemViewHolder(View convertView) {
        BannerViewHolder holder = new BannerViewHolder();
        holder.imageview = (ImageView) convertView
                .findViewById(R.id.banner_gallery_item_imageview);
        holder.imageview.setLayoutParams(imageParam);
        holder.imageview.setScaleType(ScaleType.FIT_XY);
        holder.imageview.setImageResource(getDefaultLogoRes());
        holder.textview = (TextView) convertView
                .findViewById(R.id.banner_gallery_item_textview);

        return holder;
    }

    @Override
    protected void bindDataToView(BannerBean banner, BannerViewHolder holder) {
        if (banner != null) {
            String picUrl = banner.getImgUrl();
            if (picUrl != null) {
                setImageLoader(holder.imageview, picUrl);
            } else {
                holder.imageview.setImageResource(getDefaultLogoRes());
            }
            String adImgName = banner.getTitle();
            holder.textview.setText(adImgName);
            holder.textview.setVisibility(View.VISIBLE);
        }
    }

    public  void setDefaultLogoRes(int defaultLogoRes) {
        this.defaultLogoRes = defaultLogoRes;
    }

    public int getDefaultLogoRes() {
        return defaultLogoRes;
    }

    public void setScaleVal(float width, float height) {

        float scaleVal = width / height;
        int screenWidth = ((Activity) mContext).getWindowManager()
                .getDefaultDisplay().getWidth();
        this.imageParam = new LayoutParams(screenWidth, (int) (screenWidth / scaleVal));
    }
}
