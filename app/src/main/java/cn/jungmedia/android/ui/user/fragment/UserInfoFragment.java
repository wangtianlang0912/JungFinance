package cn.jungmedia.android.ui.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leon.common.base.BaseFragment;
import com.leon.common.commonutils.ImageLoaderUtils;
import com.leon.common.commonutils.ToastUitl;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.ui.user.model.UserInfoModelImp;
import cn.jungmedia.android.ui.user.presenter.UserContract;
import cn.jungmedia.android.ui.user.presenter.UserInfoPresenterImp;
import cn.jungmedia.android.utils.MyUtils;

import static android.app.Activity.RESULT_OK;


/***
 *
 * @Copyright 2018
 *
 * @TODO 用户信息页面
 *
 * @author niufei
 *
 *
 * @date 2018/3/12. 下午11:02
 *
 *
 */
public class UserInfoFragment extends BaseFragment<UserInfoPresenterImp, UserInfoModelImp> implements UserContract.IUserInfoView {


    @Bind(R.id.logo_view)
    ImageView logoView;
    @Bind(R.id.nick_text)
    TextView nickText;
    @Bind(R.id.nick_edit)
    EditText nickEdit;
    @Bind(R.id.desp_text)
    TextView despText;
    @Bind(R.id.desp_edit)
    EditText despEdit;
    @Bind(R.id.submit_btn)
    Button submitBtn;

    private UserInfo userInfo;
    private int REQUEST_CODE = 120;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_userinfo;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (MyUtils.isLogin()) {
            userInfo = MyUtils.getUserInfoFromPreference(getActivity());
            if (userInfo != null && userInfo.getUser() != null) {
                ImageLoaderUtils.displayRound(getContext(), logoView, userInfo.getUser().getLogo());
                nickEdit.setText(userInfo.getUser().getNick());
                despEdit.setText(userInfo.getUser().getRemark());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showLoading(String title) {
        showProgressBar();
    }

    @Override
    public void stopLoading() {
        dismissProgressBar();
    }

    @Override
    public void showErrorTip(String msg) {
        ToastUitl.showShort(msg);
    }

    @Override
    public void returnSubmitResponse(UserInfo response) {
        if (response != null) {
            MyUtils.saveUserInfo(getActivity(), response);
            getActivity().finish();
        }
    }

    @Override
    public void returnUploadImage(String url) {

        String nick = nickEdit.getText().toString().trim();
        String desp = despEdit.getText().toString().trim();
        if (userInfo != null && userInfo.getUser() != null) {
            mPresenter.submit(nick, desp, userInfo.getUser().getPhone(), url);
        }
    }

    @OnClick({R.id.logo_view, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logo_view:
                choosePhoto();
                break;
            case R.id.submit_btn:

                if (logoView.getTag() != null) {
                    String imagePath = (String) logoView.getTag();
                    if (imagePath != null) {
                        mPresenter.uploadImage(imagePath);
                    }
                } else {

                    String nick = nickEdit.getText().toString().trim();
                    String desp = despEdit.getText().toString().trim();
                    if (userInfo != null && userInfo.getUser() != null) {
                        mPresenter.submit(nick, desp, userInfo.getUser().getPhone(), userInfo.getUser().getLogo());
                    }
                }
                break;
        }
    }


    /**
     * 开启图片选择器
     */
    private void choosePhoto() {
        ImgSelConfig config = new ImgSelConfig.Builder(loader)
                // 是否多选
                .multiSelect(false)
                // 确定按钮背景色
                .btnBgColor(Color.TRANSPARENT)
                .titleBgColor(ContextCompat.getColor(getContext(), R.color.main_color))
                // 使用沉浸式状态栏
                .statusBarColor(ContextCompat.getColor(getContext(), R.color.main_color))
                // 返回图标ResId
                .backResId(R.drawable.ic_arrow_back)
                .title("图片")
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(1)
                .build();
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }


    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoaderUtils.display(context, imageView, path);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (pathList != null && !pathList.isEmpty()) {

                Log.d("UserInfoFragment", "pathList.get(0):::" + pathList.get(0));
                ImageLoaderUtils.displayRound(getContext(), logoView, pathList.get(0));
                logoView.setTag(pathList.get(0));
            }
        }
    }
}
