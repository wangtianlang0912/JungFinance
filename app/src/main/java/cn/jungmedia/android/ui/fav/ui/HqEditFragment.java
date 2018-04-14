package cn.jungmedia.android.ui.fav.ui;

import cn.jungmedia.android.ui.fav.contract.HqEditContract;
import cn.jungmedia.android.ui.fav.presenter.HqEditPresenter;
import cn.jungmedia.android.ui.fav.model.HqEditModelImp;
import com.leon.common.base.BaseFragment;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/10. 下午11:13
 *
 *
 */
public class HqEditFragment extends BaseFragment<HqEditPresenter,HqEditModelImp> implements HqEditContract.View {
    @Override
    public void returnListData() {

    }

    @Override
    public void returnUnFavAction() {

    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    protected void initView() {

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
}
