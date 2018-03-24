package com.aspsine.irecyclerview.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.aspsine.irecyclerview.R;
import com.aspsine.irecyclerview.RefreshTrigger;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/24. 下午10:29
 *
 *
 */
public class ProgressBarHeaderView extends RelativeLayout implements RefreshTrigger {

    protected final ImageView mHeaderImage;
    protected final ProgressBar mHeaderProgress;

    public ProgressBarHeaderView(Context context) {
        this(context, null);
    }

    public ProgressBarHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public ProgressBarHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.layout_irecyclerview_progress_refresh_header_view, this);

        mHeaderImage  = (ImageView) findViewById(R.id.pull_to_refresh_image);
        mHeaderProgress = (ProgressBar) findViewById(R.id.pull_to_refresh_progress);
    }

    @Override
    public void onStart(boolean automatic, int headerHeight, int finalHeight) {
        mHeaderImage.setVisibility(VISIBLE);
        mHeaderProgress.setVisibility(GONE);
    }

    @Override
    public void onMove(boolean finished, boolean automatic, int moved) {
    }

    @Override
    public void onRefresh() {
        mHeaderImage.setVisibility(VISIBLE);
        mHeaderProgress.setVisibility(GONE);
    }

    @Override
    public void onRelease() {
        mHeaderImage.setVisibility(GONE);
        mHeaderProgress.setVisibility(VISIBLE);
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onReset() {
        mHeaderImage.setVisibility(VISIBLE);
        mHeaderProgress.setVisibility(GONE);
    }
}
