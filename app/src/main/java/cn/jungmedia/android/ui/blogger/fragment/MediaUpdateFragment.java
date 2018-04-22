package cn.jungmedia.android.ui.blogger.fragment;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leon.common.base.BaseFragment;
import com.leon.common.basebean.BaseRespose;
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
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.ui.blogger.bean.MediaInfoBean;
import cn.jungmedia.android.ui.blogger.contract.MediaUpdateContract;
import cn.jungmedia.android.ui.blogger.model.MediaUpdateModelImp;
import cn.jungmedia.android.ui.blogger.presenter.MediaUpdatePresenterImp;
import cn.jungmedia.android.ui.main.bean.MediaInfo;
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
 * 升级为媒体账号
 *
 * @author niufei
 *
 *
 * @date 2018/4/22. 下午9:35
 *
 *
 */
public class MediaUpdateFragment extends BaseFragment<MediaUpdatePresenterImp, MediaUpdateModelImp> implements MediaUpdateContract.View {

    @Bind(R.id.media_name_view)
    EditText mediaNameView;
    @Bind(R.id.real_name_view)
    EditText realNameView;
    @Bind(R.id.wx_editview)
    EditText wxEditview;
    @Bind(R.id.logo_view)
    ImageView logoView;
    @Bind(R.id.wx_qr_view)
    ImageView wxQrView;
    @Bind(R.id.save_btn)
    Button saveBtn;

    private static final int REQUEST_LOGO_CODE = 11;
    private static final int REQUEST_WX_CODE = 12;

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_media_update;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {

        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstant.FLAG_BUNDLE);
        if (bundle != null) {
            MediaInfo media = (MediaInfo) bundle.getSerializable(AppConstant.FLAG_DATA);
            if (media == null) {
                return;
            }
            mediaNameView.setText(media.getApplicant());
            realNameView.setText(media.getName());
            wxEditview.setText(media.getWechatNo());

            String host = ApiConstants.getHost(HostType.Jung_FINANCE);

            ImageLoaderUtils.display(getActivity(), logoView, host + media.getCoverImage());
            logoView.setTag(R.id.flag_url, media.getCoverImage());

            ImageLoaderUtils.display(getActivity(), wxQrView, host + media.getQrImage());
            wxQrView.setTag(R.id.flag_url, media.getQrImage());
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

    @OnClick({R.id.logo_view, R.id.wx_qr_view, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logo_view:
                choosePhoto(REQUEST_LOGO_CODE);
                break;
            case R.id.wx_qr_view:
                choosePhoto(REQUEST_WX_CODE);
                break;
            case R.id.save_btn:
                String mediaName = mediaNameView.getText().toString();
                if (TextUtils.isEmpty(mediaName)) {
                    ToastUitl.showShort("请填写媒体名称");
                    return;
                }
                String realName = realNameView.getText().toString();
                if (TextUtils.isEmpty(realName)) {
                    ToastUitl.showShort("请填写真实姓名");
                    return;
                }

                String wxId = wxEditview.getText().toString();
                if (TextUtils.isEmpty(wxId)) {
                    ToastUitl.showShort("请填写微信号");
                    return;
                }

                Object logoImagePath = logoView.getTag(R.id.flag_local_path);
                Object logoUrl = logoView.getTag(R.id.flag_url);
                if (logoImagePath == null && logoUrl == null) {
                    ToastUitl.showShort("请选择您的头像");
                    return;
                }
                Object wxQrImagePath = wxQrView.getTag(R.id.flag_local_path);
                Object qrUrl = wxQrView.getTag(R.id.flag_url);
                if (wxQrImagePath == null && qrUrl == null) {
                    ToastUitl.showShort("请选择微信二维码");
                    return;
                }

                if (logoImagePath == null && wxQrImagePath == null) {
                    mPresenter.submitMediaInfo(mediaName, realName, wxId, (String) logoUrl, (String) qrUrl);
                } else {

                    if (logoImagePath != null && wxQrImagePath != null) {
                        sumbitInfo(mediaName, realName, wxId, (String) logoImagePath, (String) wxQrImagePath);
                    } else {
                        if (logoImagePath != null) {

                            submitInfoOnePic(mediaName, realName, wxId, (String) logoImagePath);

                        } else if (wxQrImagePath != null) {
                            submitInfoOnePic(mediaName, realName, wxId, (String) wxQrImagePath);
                        }

                    }
                }
                break;
        }
    }


    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoaderUtils.display(context, imageView, path);
        }
    };

    /**
     * 开启图片选择器
     */
    private void choosePhoto(int requestCode) {
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
        ImgSelActivity.startActivity(this, config, requestCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_LOGO_CODE || requestCode == REQUEST_WX_CODE)
                && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (pathList != null && !pathList.isEmpty()) {

                if (requestCode == REQUEST_LOGO_CODE) {
                    Log.d("MeidaUpdateFragment", "pathList.get(0):::" + pathList.get(0));
                    ImageLoaderUtils.display(getContext(), logoView, pathList.get(0));
                    logoView.setTag(R.id.flag_local_path, pathList.get(0));
                } else if (requestCode == REQUEST_WX_CODE) {
                    Log.d("MeidaUpdateFragment", "pathList.get(0):::" + pathList.get(0));
                    ImageLoaderUtils.display(getContext(), wxQrView, pathList.get(0));
                    wxQrView.setTag(R.id.flag_local_path, pathList.get(0));
                }
            }
        }
    }

    private void submitInfoOnePic(final String mediaName, final String realName, final String wxid, String imagePath) {

        File file = new File(imagePath);
        if (!file.exists()) {
            ToastUitl.showShort("未找到图片");
            return;
        }
        updateImage(file, new UploadImageCallback() {
            @Override
            public void callback(final String data) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stopLoading();
                        JSONObject jsonObject = JSON.parseObject(data);
                        if (!jsonObject.containsKey("uri")) {
                            showErrorTip("图片上传失败");
                        }
                        String url = jsonObject.get("uri").toString();
                        wxQrView.setTag(R.id.flag_url, url);

                        mPresenter.submitMediaInfo(mediaName, realName, wxid, (String) logoView.getTag(R.id.flag_url), (String) wxQrView.getTag(R.id.flag_url));
                    }
                });
            }
        });

    }

    private void sumbitInfo(final String mediaName, final String realName, final String wxid, String logoImagePath, String wxImagePath) {

        File file = new File(logoImagePath);
        if (!file.exists()) {
            ToastUitl.showShort("未找到头像图片");
            return;
        }
        final File wxfile = new File(wxImagePath);
        if (!wxfile.exists()) {
            ToastUitl.showShort("未找到微信二维码图片");
            return;
        }
        updateImage(file, new UploadImageCallback() {
            @Override
            public void callback(final String data) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stopLoading();
                        JSONObject jsonObject = JSON.parseObject(data);
                        if (!jsonObject.containsKey("uri")) {
                            showErrorTip("头像图片上传失败");
                        }
                        String url = jsonObject.get("uri").toString();
                        logoView.setTag(R.id.flag_url, url);

                        updateImage(wxfile, new UploadImageCallback() {
                            @Override
                            public void callback(final String data) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        stopLoading();
                                        JSONObject jsonObject = JSON.parseObject(data);
                                        if (!jsonObject.containsKey("uri")) {
                                            showErrorTip("微信图片上传失败");
                                        }
                                        String url = jsonObject.get("uri").toString();
                                        wxQrView.setTag(R.id.flag_url, url);

                                        mPresenter.submitMediaInfo(mediaName, realName, wxid, (String) logoView.getTag(R.id.flag_url), url);
                                    }
                                });
                            }
                        });
                    }
                });


            }
        });

    }

    private void updateImage(File file, final UploadImageCallback callback) {

        showLoading("");
        OkHttpClient mOkHttpClent = new OkHttpClient();

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

                if (callback != null) {
                    String data = response.body().string();
                    callback.callback(data);
                }
            }
        });
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
        showShortToast(msg);
    }

    @Override
    public void returnData(BaseRespose<MediaInfoBean> respose) {
        if (respose.success()) {

            showShortToast("媒体账号修改成功");
        } else {

            showShortToast("媒体账号修改失败");
        }
    }

    private interface UploadImageCallback {

        public void callback(String data);
    }
}
