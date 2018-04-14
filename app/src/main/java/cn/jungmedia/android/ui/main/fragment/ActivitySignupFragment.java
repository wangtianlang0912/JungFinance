package cn.jungmedia.android.ui.main.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.leon.common.base.BaseFragment;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.commonutils.ToastUitl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.ui.main.bean.CrewBean;
import cn.jungmedia.android.ui.main.contract.ActivitySignupContract;
import cn.jungmedia.android.ui.main.model.ActivitySignupModelImp;
import cn.jungmedia.android.ui.main.presenter.ActivitySignupPresenter;


/***
 *
 * @Copyright 2018
 *
 *  活动报名页面
 *
 * @author niufei
 *
 *
 * @date 2018/4/14. 下午9:17
 *
 *
 */
public class ActivitySignupFragment extends BaseFragment<ActivitySignupPresenter, ActivitySignupModelImp> implements ActivitySignupContract.View {
    @Bind(R.id.phone_edit)
    EditText phoneEdit;
    @Bind(R.id.name_edit)
    EditText nameEdit;
    @Bind(R.id.member_edit)
    EditText memberEdit;
    @Bind(R.id.company_edit)
    EditText companyEdit;
    @Bind(R.id.submit_btn)
    Button submitBtn;

    private int mActiveId;

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
    public void returnSignup(BaseRespose<CrewBean> baseRespose) {

        if (baseRespose.success()) {
            ToastUitl.showShort("报名成功");
            getActivity().finish();
        } else {
            ToastUitl.showShort("报名失败");
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_active_signup;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstant.FLAG_BUNDLE);
        if (bundle != null) {
            mActiveId = bundle.getInt(AppConstant.FLAG_DATA);
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

    @OnClick(R.id.submit_btn)
    public void onViewClicked() {

        if (mActiveId == 0) {
            return;
        }
        String phone = phoneEdit.getText().toString().trim();
        String name = nameEdit.getText().toString().trim();
        String mumberNum = memberEdit.getText().toString().trim();
        String company = companyEdit.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            ToastUitl.showShort("请输入联系电话");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            ToastUitl.showShort("请输入真实姓名");
            return;
        }

        if (TextUtils.isEmpty(mumberNum)) {
            ToastUitl.showShort("请输入随行人数");
            return;
        }

        mPresenter.signup(mActiveId, phone, name, Integer.valueOf(mumberNum), company);
    }
}
