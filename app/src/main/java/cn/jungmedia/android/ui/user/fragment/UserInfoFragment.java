package cn.jungmedia.android.ui.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonutils.ImageLoaderUtils;
import com.leon.common.commonutils.ToastUitl;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.ui.user.model.UserInfoModelImp;
import cn.jungmedia.android.ui.user.presenter.UserContract;
import cn.jungmedia.android.ui.user.presenter.UserInfoPresenterImp;
import cn.jungmedia.android.utils.MyUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                        updateImage(imagePath);
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


    private void updateImage(String urlPath) {

        OkHttpClient mOkHttpClent = new OkHttpClient();
        File file = new File(urlPath);
        if (!file.exists()) {
            ToastUitl.showShort("未找到图片");
            return;
        }
        showLoading("");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), file));

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(ApiConstants.getHost(HostType.Jung_FINANCE) + "app/image/create")
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("UserInfoFragment", "onFailure: " + e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showErrorTip("图片上传失败");
                        stopLoading();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            stopLoading();
                            String data = response.body().string();
                            if (!TextUtils.isEmpty(data)) {

                                JSONObject jsonObject = JSON.parseObject(data);
                                if (jsonObject.containsKey("uri")) {
                                    String url = jsonObject.get("uri").toString();

                                    String nick = nickEdit.getText().toString().trim();
                                    String desp = despEdit.getText().toString().trim();
                                    if (userInfo != null && userInfo.getUser() != null) {
                                        mPresenter.submit(nick, desp, userInfo.getUser().getPhone(), url);
                                    }
                                }
                            }

                        } catch (IOException e) {
                            ToastUitl.showShort("解析错误");
                        }


                    }
                });
            }
        });
    }
}
