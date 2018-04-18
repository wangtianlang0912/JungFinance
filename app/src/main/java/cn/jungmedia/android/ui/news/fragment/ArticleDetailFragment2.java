package cn.jungmedia.android.ui.news.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leon.common.base.BaseFragment;
import com.leon.common.commonutils.ImageLoaderUtils;
import com.leon.common.ui.DuAlertDialog;

import net.nightwhistler.htmlspanner.HtmlSpanner;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.bean.ArticleDetail;
import cn.jungmedia.android.bean.ArticleModel;
import cn.jungmedia.android.bean.BloggerModel;
import cn.jungmedia.android.bean.CommentCreateModel;
import cn.jungmedia.android.bean.CommentListModel;
import cn.jungmedia.android.bean.FavActionModel;
import cn.jungmedia.android.ui.news.contract.ArticleDetaiContract;
import cn.jungmedia.android.ui.news.model.ArticleDetailModel;
import cn.jungmedia.android.ui.news.presenter.ArticleDetailPresenter;
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
 * @date 2018/4/18. 下午9:52
 *
 *
 */
public class ArticleDetailFragment2 extends BaseFragment<ArticleDetailPresenter, ArticleDetailModel> implements ArticleDetaiContract.View {
    @Bind(R.id.title_view)
    TextView titleView;
    @Bind(R.id.time_view)
    TextView timeView;
    @Bind(R.id.source_view)
    TextView sourceView;
    @Bind(R.id.scan_view)
    TextView scanView;
    @Bind(R.id.summary_view)
    TextView summaryView;
    @Bind(R.id.content_view)
    TextView contentView;
    @Bind(R.id.zan_imageview)
    ImageView zanImageview;
    @Bind(R.id.zan_view)
    TextView zanView;
    @Bind(R.id.cai_imageview)
    ImageView caiImageview;
    @Bind(R.id.cai_view)
    TextView caiView;
    @Bind(R.id.comment_item_layout)
    LinearLayout commentItemLayout;
    @Bind(R.id.comment_btn)
    TextView commentBtn;
    @Bind(R.id.relate_item_layout)
    LinearLayout relateItemLayout;
    @Bind(R.id.write_comment_view)
    TextView writeCommentView;
    @Bind(R.id.fav_btn)
    ImageView favBtn;
    @Bind(R.id.share_btn)
    ImageView shareBtn;
    @Bind(R.id.btn_layout)
    LinearLayout btnLayout;
    @Bind(R.id.bottom_layout)
    RelativeLayout bottomLayout;
    @Bind(R.id.logo_view)
    ImageView logoView;
    @Bind(R.id.focus_btn)
    TextView focusBtn;
    @Bind(R.id.blogger_name_view)
    TextView bloggerNameView;
    @Bind(R.id.pub_num_view)
    TextView pubNumView;
    @Bind(R.id.blogger_layout)
    RelativeLayout bloggerLayout;

    private Dialog commentDialog;
    protected int articleId;
    protected Intent homeIntent;

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
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
    public void returnArticleData(ArticleDetail data) {

        if (data != null && data.getArticle() != null) {
            final HtmlSpanner htmlSpanner = new HtmlSpanner();
            final ArticleModel.Article article = data.getArticle();

            titleView.setText(article.getTitle());
            timeView.setText(article.getPtime());
            sourceView.setText(article.getSource());
            scanView.setText(article.getPv() + "");

            new Thread() {
                @Override
                public void run() {

                    final Spannable summarySpan = htmlSpanner.fromHtml("摘要：" + article.getSummary());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            summaryView.setText(summarySpan);
                        }
                    });

                    final Spannable contentSpan = htmlSpanner.fromHtml(article.getContent());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            contentView.setText(contentSpan);
                        }
                    });
                }
            }.start();


            if (article.getMedia() != null) {
                BloggerModel.Media media = article.getMedia();

                bloggerNameView.setText(media.getName());
                pubNumView.setText(media.getArticleNum() + "");
                ImageLoaderUtils.displayRound(getContext(), logoView, media.getCoverImage());
                focusBtn.setText(media.getStatus() == 1 ? "+订阅" : "已订阅");
                focusBtn.setTag(media.getStatus());
            }
        }
    }


    @Override
    public void returnRelateList(ArticleModel articleModel) {

    }

    @Override
    public void returnFavArticleState(FavActionModel.Favorite result) {

    }

    @Override
    public void returnFocusBloggerState(boolean result) {

    }

    @Override
    public void returnCreateComment(CommentCreateModel model) {

    }

    @Override
    public void returnCommentList(CommentListModel model) {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.article_layout2;
    }


    @Override
    protected void initView() {
        loadData();
    }

    protected void loadData() {

        homeIntent = getActivity().getIntent();
        Bundle bundle = homeIntent.getBundleExtra(AppConstant.FLAG_BUNDLE);
        articleId = bundle.getInt(AppConstant.FLAG_DATA2);
        mPresenter.getArticleDetail(String.valueOf(articleId));
        mPresenter.getCommentList(articleId);
//        mPresenter.getArticleRelateList(String.valueOf(articleId));
        mPresenter.getArticleFavState(articleId);
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

    @OnClick({R.id.zan_imageview, R.id.cai_imageview, R.id.comment_btn, R.id.write_comment_view, R.id.fav_btn, R.id.share_btn, R.id.focus_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zan_imageview:
                break;
            case R.id.cai_imageview:
                break;
            case R.id.write_comment_view:

                showCommentCreateDialog();
                break;
            case R.id.comment_btn:
                if (articleId <= 0) {
                    return;
                }
                AppIntent.intentToCommentList(getActivity(), articleId);
                break;
            case R.id.fav_btn:
                Object tag = favBtn.getTag();
                if (tag == null) {
                    favBtnClicked(articleId);
                } else {
                    unFavBtnClicked((int) tag);
                }
                break;
            case R.id.share_btn:

//                new ShareHelper().share(getActivity(),);
                break;

            case R.id.focus_btn:

//                mPresenter.focusAction();
                break;
        }
    }


    private void showCommentCreateDialog() {

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

                mPresenter.createComment(articleId, value);
            }
        });

    }


    private void favBtnClicked(int articleId) {

        if (articleId <= 0) {
            return;
        }
        if (!MyUtils.isLogin()) {
            AppIntent.intentToLogin(getContext());
            return;
        }
        mPresenter.favActionArticle(articleId, false);
    }


    private void unFavBtnClicked(int favItemId) {

        if (favItemId <= 0) {
            return;
        }
        if (!MyUtils.isLogin()) {
            AppIntent.intentToLogin(getContext());
            return;
        }
        mPresenter.favActionArticle(favItemId, true);
    }


    private void focusBtnClicked(int bloggerId, boolean status) {

        mPresenter.focusAction(bloggerId, status);
    }


}
