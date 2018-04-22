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
import com.leon.common.basebean.BaseRespose;
import com.leon.common.commonutils.ImageLoaderUtils;
import com.leon.common.ui.DuAlertDialog;

import net.nightwhistler.htmlspanner.HtmlSpanner;

import java.util.List;

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
import cn.jungmedia.android.bean.VoteModel;
import cn.jungmedia.android.ui.news.contract.ArticleDetaiContract;
import cn.jungmedia.android.ui.news.model.ArticleDetailModel;
import cn.jungmedia.android.ui.news.presenter.ArticleDetailPresenter;
import cn.jungmedia.android.utils.MyUtils;
import cn.jungmedia.android.utils.ShareHelper;


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
    ImageView commentBtn;
    @Bind(R.id.comment_inner_btn)
    TextView commentInnerBtn;
    @Bind(R.id.relate_layout)
    LinearLayout relateLayout;
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

            final ArticleModel.Article article = data.getArticle();
            article.setUrl(data.getUrl());
            shareBtn.setTag(article);
            titleView.setText(article.getTitle());
            timeView.setText(article.getPtime());
            sourceView.setText(article.getSource());
            scanView.setText(article.getPv() + "");
            zanView.setText(article.getSupport() + "");
            caiView.setText(article.getOppose() + "");

            if (article.getFavorite() != null) {
                favBtn.setImageResource(R.drawable.icon_fav_s);
                favBtn.setTag(article.getFavorite().getObjectId());
            } else {
                favBtn.setImageResource(R.drawable.icon_fav_n);
                favBtn.setTag(null);
            }
            if (TextUtils.isEmpty(article.getSummary())) {
                if (summaryView != null) {
                    summaryView.setVisibility(View.GONE);
                }
            }
            new Thread() {
                @Override
                public void run() {
                    if (!TextUtils.isEmpty(article.getSummary())) {
                        final Spannable summarySpan = new HtmlSpanner().fromHtml("摘要：" + article.getSummary());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (summaryView != null) {
                                    summaryView.setVisibility(View.VISIBLE);
                                    summaryView.setText(summarySpan);
                                }
                            }
                        });
                    }
                    final HtmlSpanner htmlSpanner = new HtmlSpanner(contentView.getWidth());
                    htmlSpanner.setStripExtraWhiteSpace(true);
                    htmlSpanner.setAllowStyling(true);
                    htmlSpanner.setUseColoursFromStyle(true);
                    final Spannable contentSpan = htmlSpanner.fromHtml(article.getContent());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (contentView != null) {
                                contentView.setText(contentSpan);
                            }
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
                focusBtn.setTag(media.getStatus() == 1);
                bloggerLayout.setTag(media.getObjectId());
                bloggerLayout.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void returnRelateList(ArticleModel articleModel) {

        if (articleModel == null || articleModel.getArticles().isEmpty()) {
            return;
        }
        relateLayout.setVisibility(View.VISIBLE);
        relateItemLayout.setVisibility(View.VISIBLE);
        List<ArticleModel.Article> articleList = articleModel.getArticles();
        for (final ArticleModel.Article article : articleList) {
            View itemLayout = LayoutInflater.from(getActivity()).inflate(R.layout.item_news, null);
            relateItemLayout.addView(itemLayout);
            TextView titleView = (TextView) itemLayout.findViewById(R.id.news_summary_title_tv);
            titleView.setText(article.getTitle());
            TextView sourceView = (TextView) itemLayout.findViewById(R.id.source_view);
            sourceView.setText(TextUtils.isEmpty(article.getSource()) ?
                    getActivity().getString(R.string.app_name) : article.getSource());
            TextView scanView = (TextView) itemLayout.findViewById(R.id.see_view);
            scanView.setText(article.getPv() + "");
            ImageView logoView = (ImageView) itemLayout.findViewById(R.id.news_summary_photo_iv);
            ImageLoaderUtils.display(getActivity(), logoView, article.getImage());
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AppIntent.intentToArticleDetail(getActivity(),
                            article.getObjectId());
                }
            });
        }

    }

    @Override
    public void returnFavArticleState(BaseRespose<FavActionModel> respose) {
        if (respose.success()) {
            FavActionModel activityModel = respose.data;
            if (activityModel.getFavorite() != null) {
                if (activityModel.getFavorite().getStatus() == 0) {
                    favBtn.setImageResource(R.drawable.icon_fav_s);
                    favBtn.setTag(activityModel.getFavorite().getObjectId());
                } else {
                    favBtn.setImageResource(R.drawable.icon_fav_n);
                    favBtn.setTag(null);
                }
            }
        } else {
            showErrorTip(respose.msg);
        }
    }

    @Override
    public void returnFocusBloggerState(BaseRespose<FavActionModel> response) {
        if (response.success()) {
            FavActionModel activityModel = response.data;
            if (activityModel.getFavorite() != null) {
                focusBtn.setText("已订阅");
                focusBtn.setTag(true);


            } else {
                focusBtn.setText("+订阅");
                focusBtn.setTag(false);
            }
        } else {
            showErrorTip(response.msg);
        }
    }

    @Override
    public void returnCreateComment(CommentCreateModel model) {
        if (model != null) {
            if (commentDialog != null && commentDialog.isShowing()) {
                commentDialog.dismiss();
            }
            showShortToast(R.string.submit_success);
        } else {
            showShortToast(R.string.submit_failure);
        }
    }

    @Override
    public void returnCommentList(CommentListModel model) {
        if (model != null && model.getComments() != null) {
            commentItemLayout.setVisibility(View.VISIBLE);
            for (CommentCreateModel.Comment comment : model.getComments()) {
                View itemLayout = LayoutInflater.from(getActivity()).inflate(R.layout.item_comment, null);
                commentItemLayout.addView(itemLayout);
                TextView titleView = (TextView) itemLayout.findViewById(R.id.title_view);
                titleView.setText(comment.getUser().getNick());
                TextView pubTimeView = (TextView) itemLayout.findViewById(R.id.pubtime_view);
                pubTimeView.setText(comment.getcTimeStr());
                TextView replayNumView = (TextView) itemLayout.findViewById(R.id.replay_num_view);
                replayNumView.setText(comment.getrCount() + "");
                TextView contentView = (TextView) itemLayout.findViewById(R.id.content_view);
                contentView.setText(comment.getBody());
                ImageView logoView = (ImageView) itemLayout.findViewById(R.id.logo_view);
                ImageLoaderUtils.displayRound(getActivity(), logoView, comment.getUser().getLogo());
            }

            commentInnerBtn.setText("查看全部(" + model.getCounter().getTotal() + ")");
        }

    }

    @Override
    public void returnVoteData(BaseRespose<VoteModel> response) {
        if (response != null) {
            if (response.success()) {
                zanView.setText(response.data.getArticle().getSupport() + "");
                caiView.setText(response.data.getArticle().getOppose() + "");
            } else {
                showErrorTip(response.msg);
            }
        }
    }

    @Override
    public void returnShare(BaseRespose response) {

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
        mPresenter.getArticleRelateList(String.valueOf(articleId));
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

    @OnClick({R.id.zan_imageview, R.id.cai_imageview, R.id.comment_btn, R.id.comment_inner_btn, R.id.write_comment_view, R.id.fav_btn, R.id.share_btn, R.id.focus_btn})
    public void onViewClicked(View view) {
        if (articleId <= 0) {
            return;
        }
        switch (view.getId()) {
            case R.id.zan_imageview:
                mPresenter.support(articleId);
                break;
            case R.id.cai_imageview:
                mPresenter.oppose(articleId);
                break;
            case R.id.write_comment_view:

                showCommentCreateDialog();
                break;
            case R.id.comment_btn:
            case R.id.comment_inner_btn:
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
                Object articleObj = shareBtn.getTag();
                if (articleObj != null) {
                    final ArticleModel.Article article = (ArticleModel.Article) articleObj;
                    new ShareHelper().share(getActivity(), article.getTitle(), article.getSummary(), article.getImage(), article.getUrl(), new ShareHelper.OnSharedListener() {
                        @Override
                        public void onSharedCompleted() {

                            mPresenter.share(article.getObjectId());
                        }
                    });
                }
                break;

            case R.id.focus_btn:

                Object bloggerId = bloggerLayout.getTag();
                if (bloggerId == null) {
                    return;
                }
                Object tag2 = focusBtn.getTag();
                if (tag2 != null) {

                    focusBtnClicked((Integer) bloggerId, (Boolean) tag2);
                }
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
