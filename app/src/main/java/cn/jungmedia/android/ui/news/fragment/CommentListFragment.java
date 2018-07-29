package cn.jungmedia.android.ui.news.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.leon.common.base.BaseFragment;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.commonutils.ToastUitl;
import com.leon.common.commonwidget.LoadingTip;
import com.leon.common.ui.DuAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.bean.CommentCreateModel;
import cn.jungmedia.android.bean.CommentListModel;
import cn.jungmedia.android.bean.Counter;
import cn.jungmedia.android.ui.news.adapter.CommentListAdapter;
import cn.jungmedia.android.ui.news.contract.CommentListContract;
import cn.jungmedia.android.ui.news.model.CommentListModelImp;
import cn.jungmedia.android.ui.news.presenter.CommentListPresenter;
import cn.jungmedia.android.utils.MyUtils;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/7. 下午10:58
 *
 *
 */
public class CommentListFragment extends BaseFragment<CommentListPresenter, CommentListModelImp> implements CommentListContract.View, OnRefreshListener, OnLoadMoreListener, CommentListAdapter.OnItemReplayBtnClickListener {
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    @Bind(R.id.bottom_layout)
    RelativeLayout bottomLayout;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;
    private int mStartPage;
    private CommentListAdapter commentListAdapter;
    private List<CommentCreateModel.Comment> datas = new ArrayList<>();
    private int articleId;
    private Dialog commentDialog;

    @Override
    protected int getLayoutResource() {
        return R.layout.frag_comment_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        datas.clear();
        commentListAdapter = new CommentListAdapter(getContext(), datas, this);
//        commentListAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(commentListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);

        emptyLayout.setVisibility(View.GONE);
        Intent homeIntent = getActivity().getIntent();
        Bundle bundle = homeIntent.getBundleExtra(AppConstant.FLAG_BUNDLE);
        articleId = bundle.getInt(AppConstant.FLAG_DATA);
        //数据为空才重新发起请求
        if (commentListAdapter.getSize() <= 0) {
            mStartPage = 1;
            mPresenter.getCommentList(articleId, mStartPage, 0);
        }
    }

    @Override
    public void returnCreateComment(BaseRespose<CommentCreateModel> respose) {

        if (respose != null && respose.success()) {
            if (commentDialog != null && commentDialog.isShowing()) {
                commentDialog.dismiss();
            }
            showShortToast(R.string.submit_success);

            onRefresh();
        } else {
            ToastUitl.showShort("内容提交失败");
        }
    }

    @Override
    public void returnCommentList(CommentListModel model) {
        List<CommentCreateModel.Comment> list = model.getComments();
        if (list != null) {
            if (commentListAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                commentListAdapter.replaceAll(list);
            } else {
                commentListAdapter.addAll(list);
            }
        }

        commentListAdapter.notifyDataSetChanged();
        Counter counter = model.getCounter();
        if (counter != null) {

            if (counter.getPageIndex() < counter.getPageCount()) {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                mStartPage++;
            } else {
                if (commentListAdapter.getSize() > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    emptyLayout.setVisibility(View.VISIBLE);
                }
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

    @OnClick(R.id.bottom_layout)
    public void onViewClicked() {
        showCommentCreateDialog(0);
    }

    @Override
    public void onRefresh() {
        emptyLayout.setVisibility(View.GONE);
        commentListAdapter.getPageBean().setRefresh(true);
        mStartPage = 1;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getCommentList(articleId, mStartPage, 0);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        if (!irc.canLoadMore()) {
            return;
        }
        commentListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getCommentList(articleId, mStartPage, 0);
    }

    @Override
    public void showLoading(String title) {
        if (commentListAdapter.getPageBean().isRefresh()) {
            if (commentListAdapter.getSize() <= 0) {
                irc.setRefreshing(true);
            }
        }
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if (commentListAdapter.getPageBean().isRefresh()) {
            if (commentListAdapter.getSize() <= 0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadedTip.setTips(msg);
            }
            irc.setRefreshing(false);
        } else {
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    private void showCommentCreateDialog(final int touid) {

        DuAlertDialog.Builder builder = new DuAlertDialog().createBottomBuilder(getActivity());
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_comment_publish, null);
        builder.setView(commentView);
        Button button = (Button) commentView.findViewById(R.id.sub_layout);
        final EditText editText = (EditText) commentView.findViewById(R.id.edit_view);
        editText.requestFocus();
        commentDialog = builder.create();
        commentDialog.getWindow().setGravity(Gravity.BOTTOM);
        commentDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = editText.getText().toString().trim();
                if (TextUtils.isEmpty(value)) {
                    return;
                }
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getContext());
                    return;
                }
                mPresenter.createComment(articleId, value, touid);
            }
        });

    }

    @Override
    public void onClick(int objectd) {
        showCommentCreateDialog(objectd);
    }
}
