package com.jung.finance.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.jung.finance.R;
import com.jung.finance.app.AppConstant;
import com.leon.common.base.BaseActivity;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonutils.LogUtils;
import com.leon.common.commonwidget.NormalTitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/15. 下午11:40
 *
 *
 */
public class CommonActivity extends BaseActivity {

    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    private Fragment fragment;


    @Override
    public int getLayoutId() {
        return R.layout.act_common;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {


        String title = getIntent().getStringExtra(AppConstant.FLAG_NAME);
        if (!TextUtils.isEmpty(title)) {
            ntb.setTitleText(title);
        }
        ntb.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (fragment == null) {
            String className = getIntent().getStringExtra(AppConstant.FLAG_FRAGMENT);
            if (className != null) {
                fragment = getFragment(className);
                if (fragment instanceof BaseFragment) {
                    FragmentTransaction trans = this.getSupportFragmentManager().beginTransaction();
                    trans.replace(R.id.common_layout, fragment);
                    trans.commitAllowingStateLoss();
                    trans.show(fragment);
                } else {
                    LogUtils.loge("CommonActivity", "illegal fragment");
                }
            }
        }
    }

    protected Fragment getFragment(String className) {
        Bundle args = getIntent().getExtras();
        Fragment fragment = Fragment.instantiate(this, className, args);
        return fragment;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle newBundle = intent.getExtras();
        if (fragment != null) {
            fragment.setArguments(newBundle);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
