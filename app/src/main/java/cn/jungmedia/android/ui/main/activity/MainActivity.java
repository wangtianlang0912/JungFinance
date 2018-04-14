package com.jung.android.ui.main.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jung.android.app.AppConstant;
import com.jung.finance.R;
import com.jung.android.bean.TabEntity;
import com.jung.android.ui.main.fragment.ActivityMainFragment;
import com.jung.android.ui.main.fragment.FastMainFragment;
import com.jung.android.ui.main.fragment.MineFragment;
import com.jung.android.ui.main.fragment.NewsMainFragment;
import com.leon.common.base.BaseActivity;
import com.leon.common.commonutils.LogUtils;
import com.leon.common.daynightmodeutils.ChangeModeController;

import java.util.ArrayList;

import butterknife.Bind;
import cn.hugeterry.updatefun.UpdateFunGO;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import rx.functions.Action1;

/**
 * des:主界面
 * Created by xsf
 * on 2016.09.15:32
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private int[] mTitles = {R.string.main_tab_news, R.string.main_tab_fast, R.string.main_tab_activity, R.string.main_tab_mine};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal, R.mipmap.ic_info_normal, R.mipmap.ic_active_normal, R.mipmap.ic_mine_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_info_selected, R.mipmap.ic_active_selected, R.mipmap.ic_mine_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private NewsMainFragment newsMainFragment;
    private FastMainFragment fastMainFragment;
    //    private VideoMainFragment videoMainFragment;
    private ActivityMainFragment activityListFragment;
    private MineFragment mineFragment;
    private static int tabLayoutHeight;


    private boolean isExit;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                com.leon.common.R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
//        //此处填上在http://fir.im/注册账号后获得的API_TOKEN以及APP的应用ID
//        UpdateKey.API_TOKEN = AppConfig.API_FIRE_TOKEN;
//        UpdateKey.APP_ID = AppConfig.APP_FIRE_ID;
//        //如果你想通过Dialog来进行下载，可以如下设置
////        UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG;
//        UpdateFunGO.init(this);
        //初始化菜单
        initTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //切换daynight模式要立即变色的页面
        ChangeModeController.getInstance().init(this, R.attr.class);
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
        tabLayout.measure(0, 0);
        tabLayoutHeight = tabLayout.getMeasuredHeight();
        //监听菜单显示或隐藏
        mRxManager.on(AppConstant.MENU_SHOW_HIDE, new Action1<Boolean>() {

            @Override
            public void call(Boolean hideOrShow) {
                startAnimation(hideOrShow);
            }
        });
    }

    /**
     * 初始化tab
     */
    private void initTab() {

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(getString(mTitles[i]), mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            newsMainFragment = (NewsMainFragment) getSupportFragmentManager().findFragmentByTag("newsMainFragment");
            fastMainFragment = (FastMainFragment) getSupportFragmentManager().findFragmentByTag("fastMainFragment");
//            videoMainFragment = (VideoMainFragment) getSupportFragmentManager().findFragmentByTag("videoMainFragment");
            activityListFragment = (ActivityMainFragment) getSupportFragmentManager().findFragmentByTag("activityListFragment");
            mineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag("mineFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            newsMainFragment = new NewsMainFragment();
            fastMainFragment = new FastMainFragment();
//            videoMainFragment = new VideoMainFragment();
            activityListFragment = new ActivityMainFragment();
            mineFragment = new MineFragment();

            transaction.add(R.id.fl_body, newsMainFragment, "newsMainFragment");
            transaction.add(R.id.fl_body, fastMainFragment, "fastMainFragment");
//            transaction.add(R.id.fl_body, videoMainFragment, "videoMainFragment");
            transaction.add(R.id.fl_body, activityListFragment, "activityListFragment");
            transaction.add(R.id.fl_body, mineFragment, "mineFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.hide(fastMainFragment);
//                transaction.hide(videoMainFragment);
                transaction.hide(activityListFragment);
                transaction.hide(mineFragment);
                transaction.show(newsMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            // 快评
            case 1:
                transaction.hide(newsMainFragment);
//                transaction.hide(videoMainFragment);
                transaction.hide(activityListFragment);
                transaction.hide(mineFragment);
                transaction.show(fastMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //视频
//            case 2:
//                transaction.hide(newsMainFragment);
//                transaction.hide(photosMainFragment);
//                transaction.hide(activityListFragment);
//                transaction.hide(mineFragment);
//                transaction.show(videoMainFragment);
//                transaction.commitAllowingStateLoss();
//                break;
            // 活动
            case 2:
                transaction.hide(newsMainFragment);
                transaction.hide(fastMainFragment);
//                transaction.hide(videoMainFragment);
                transaction.hide(mineFragment);
                transaction.show(activityListFragment);
                transaction.commitAllowingStateLoss();
                break;
            // 我的
            case 3:
                transaction.hide(newsMainFragment);
                transaction.hide(fastMainFragment);
//                transaction.hide(videoMainFragment);
                transaction.hide(activityListFragment);
                transaction.show(mineFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    /**
     * 菜单显示隐藏动画
     *
     * @param showOrHide
     */
    private void startAnimation(boolean showOrHide) {
        final ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        if (!showOrHide) {
            valueAnimator = ValueAnimator.ofInt(tabLayoutHeight, 0);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 1, 0);
        } else {
            valueAnimator = ValueAnimator.ofInt(0, tabLayoutHeight);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 0, 1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                tabLayout.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator, alpha);
        animatorSet.start();
    }

    /**
     * 监听全屏视频时返回键
     */
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        if (!isExit) {
            isExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 3000);
            Toast.makeText(this, "再次点击退出客户端", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isFinishing()) {
            // super.onBackPressed();
            // You can't call onBackPressed() when your activity is paused.
            // You should make sure in your onBackPressed()'s override that
            // the activity is going to finish.
            // Back can be pressed for other reasons.
            finish();
        }
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        LogUtils.loge("onSaveInstanceState进来了1");
        if (tabLayout != null) {
            LogUtils.loge("onSaveInstanceState进来了2");
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateFunGO.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        UpdateFunGO.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChangeModeController.onDestory();
    }
}
