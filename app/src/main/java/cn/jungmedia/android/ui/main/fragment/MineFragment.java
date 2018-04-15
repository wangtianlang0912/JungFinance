package cn.jungmedia.android.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leon.common.base.BaseFragment;
import com.leon.common.commonutils.ImageLoaderUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.ui.main.contract.MineContract;
import cn.jungmedia.android.ui.main.model.MineModelImp;
import cn.jungmedia.android.ui.main.presenter.MinePresenterImp;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.utils.MyUtils;

/**
 * des:我的主页
 * Created by xsf
 * on 2016.09.17:07
 */
public class MineFragment extends BaseFragment<MinePresenterImp, MineModelImp> implements MineContract.IMineView {
    @Bind(R.id.setting)
    ImageView setting;
    @Bind(R.id.img_logo)
    ImageView imgLogo;
    @Bind(R.id.v_view)
    ImageView vView;
    @Bind(R.id.img_logo_layout)
    RelativeLayout imgLogoLayout;
    @Bind(R.id.nick_view)
    TextView nickView;
    @Bind(R.id.desp_view)
    TextView despView;
    @Bind(R.id.subscribe_num)
    TextView subscribeNum;
    @Bind(R.id.subscribe_layout)
    LinearLayout subscribeLayout;
    @Bind(R.id.fans_num)
    TextView fansNum;
    @Bind(R.id.fans_layout)
    LinearLayout fansLayout;
    @Bind(R.id.score_num)
    TextView scoreNum;
    @Bind(R.id.score_layout)
    LinearLayout scoreLayout;
    @Bind(R.id.media_account_btn)
    Button mediaAccountBtn;
    @Bind(R.id.refer_view)
    ImageView referView;
    @Bind(R.id.info_layout)
    LinearLayout infoLayout;
    @Bind(R.id.analyze_layout)
    LinearLayout analyzeLayout;
    @Bind(R.id.fast_layout)
    LinearLayout fastLayout;
    @Bind(R.id.activity_layout)
    LinearLayout activityLayout;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_mine_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);

    }


    @Override
    public void onResume() {
        super.onResume();

        if (MyUtils.isLogin()) {
            mPresenter.getUserInfo();
        } else {
            resetViews();
        }
    }

    private void resetViews() {

        nickView.setText(null);
        imgLogo.setImageResource(R.drawable.blant_logo);
        despView.setText(null);
        subscribeNum.setText("0");
        fansNum.setText("0");
        scoreNum.setText("0");
    }

    @Override
    protected void initView() {
        if (MyUtils.isLogin()) {
            UserInfo userInfo = MyUtils.getUserInfoFromPreference(getActivity());
            if (userInfo != null && userInfo.getUser() != null) {
                ImageLoaderUtils.displayRound(getContext(), imgLogo, userInfo.getUser().getLogo());
                nickView.setText(userInfo.getUser().getNick());
                despView.setText(userInfo.getUser().getRemark());
                subscribeNum.setText(userInfo.getUser().getMCount() + "");
                fansNum.setText(userInfo.getUser().getRole() + "");
                if (userInfo.getUser().getMember() != null) {
                    scoreNum.setText(userInfo.getUser().getMember().getScore() + "");
                }
            }
        } else {
            resetViews();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.setting, R.id.img_logo_layout, R.id.subscribe_layout, R.id.fans_layout, R.id.score_layout, R.id.media_account_btn, R.id.refer_view, R.id.info_layout, R.id.analyze_layout, R.id.fast_layout, R.id.activity_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting:
                AppIntent.intentToSetting(getActivity());
                break;
            case R.id.img_logo_layout:
                if (MyUtils.isLogin()) {
                    AppIntent.intentToUserInfo(getActivity());
                } else {
                    AppIntent.intentToLogin(getActivity());
                }
                break;
            case R.id.subscribe_layout:
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getContext());
                    return;
                }
                AppIntent.intentToBloggerFav(getContext());
                break;
            case R.id.fans_layout:
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getContext());
                    return;
                }
                AppIntent.intentToFans(getContext());
                break;
            case R.id.score_layout:
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getContext());
                    return;
                }
                AppIntent.intentToScoreList(getContext());
                break;
            case R.id.media_account_btn:
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getContext());
                    return;
                }

                break;
            case R.id.refer_view:
                break;
            case R.id.info_layout:
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getContext());
                    return;
                }
                AppIntent.intentToNewsEdit(getActivity());
                break;
            case R.id.analyze_layout:
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getContext());
                    return;
                }
                AppIntent.intentToHqEdit(getActivity());
                break;
            case R.id.fast_layout:
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getContext());
                    return;
                }
                AppIntent.intentToFastEdit(getActivity());
                break;
            case R.id.activity_layout:
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getContext());
                    return;
                }
                AppIntent.intentToActivityEdit(getActivity());
                break;
        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {
    }

    @Override
    public void showErrorTip(String msg) {
    }

    @Override
    public void returnUserInfoResponse(UserInfo userInfo) {
        if (userInfo != null && userInfo.getUser() != null) {

            MyUtils.saveUserInfo(getActivity(), userInfo);

            ImageLoaderUtils.displayRound(getContext(), imgLogo, userInfo.getUser().getLogo());
            nickView.setText(userInfo.getUser().getNick());
            despView.setText(userInfo.getUser().getRemark());
            subscribeNum.setText(userInfo.getUser().getMCount() + "");
            fansNum.setText(userInfo.getUser().getRole() + "");
            if (userInfo.getUser().getMember() != null) {
                scoreNum.setText(userInfo.getUser().getMember().getScore() + "");
            }
        }
    }
}
