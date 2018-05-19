package cn.jungmedia.android.ui.blogger.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.leon.common.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.ui.main.bean.MediaInfo;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/16. 下午11:18
 *
 *
 */
public class MediaDiffShowFragment extends BaseFragment {
    @Bind(R.id.update_btn)
    Button updateBtn;
    MediaInfo media = null;

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_media_show;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstant.FLAG_BUNDLE);
        if (bundle != null) {
            media = (MediaInfo) bundle.getSerializable(AppConstant.FLAG_DATA);
            if (media == null) {
                updateBtn.setVisibility(View.GONE);
            } else {
                updateBtn.setVisibility(View.VISIBLE);
            }
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

    @OnClick(R.id.update_btn)
    public void onViewClicked() {
        if (media != null) {
            AppIntent.intentToMediaUpdate(getActivity(), media);
        }
    }
}
