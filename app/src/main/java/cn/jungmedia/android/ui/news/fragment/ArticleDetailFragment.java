//package cn.jungmedia.android.ui.news.fragment;
//
//import android.app.Dialog;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.leon.common.base.BaseFragment;
//import com.leon.common.browser.HostJsScope;
//import com.leon.common.browser.InjectedChromeClient;
//import com.leon.common.browser.InnerWebViewClient;
//import com.leon.common.browser.ProgressWebView;
//import com.leon.common.ui.DuAlertDialog;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import cn.jungmedia.android.R;
//import cn.jungmedia.android.app.AppConstant;
//import cn.jungmedia.android.app.AppIntent;
//import cn.jungmedia.android.bean.ArticleDetail;
//import cn.jungmedia.android.bean.ArticleModel;
//import cn.jungmedia.android.bean.BloggerModel;
//import cn.jungmedia.android.bean.CommentCreateModel;
//import cn.jungmedia.android.bean.CommentListModel;
//import cn.jungmedia.android.bean.FavActionModel;
//import cn.jungmedia.android.ui.common.CommonActivity;
//import cn.jungmedia.android.ui.news.contract.ArticleDetaiContract;
//import cn.jungmedia.android.ui.news.model.ArticleDetailModel;
//import cn.jungmedia.android.ui.news.presenter.ArticleDetailPresenter;
//import cn.jungmedia.android.utils.MyUtils;
//
//
///***
// *
// * @Copyright 2018
// *
// * @TODO
// *
// * @author niufei
// *
// *
// * @date 2018/3/31. 下午11:58
// *
// *
// */
//public class ArticleDetailFragment extends BaseFragment<ArticleDetailPresenter, ArticleDetailModel> implements ArticleDetaiContract.View {
//
//    protected int articleId;
//
//    @Bind(R.id.common_web_main_web_view)
//    protected ProgressWebView progressWebView;
//    protected WebView detailwebview;
//    protected Intent homeIntent;
//    @Bind(R.id.write_comment_view)
//    TextView writeCommentView;
//    @Bind(R.id.comment_btn)
//    ImageView commentBtn;
//    @Bind(R.id.fav_btn)
//    ImageView favBtn;
//    @Bind(R.id.share_btn)
//    ImageView shareBtn;
//    @Bind(R.id.bottom_layout)
//    RelativeLayout bottomLayout;
//
//    private Dialog commentDialog;
//
//    @Override
//    protected int getLayoutResource() {
//        return R.layout.article_layout;
//    }
//
//
//    @Override
//    protected void initView() {
//        detailwebview = progressWebView.getWebView();
//        detailwebview.getSettings().setJavaScriptEnabled(true);
//        detailwebview.getSettings().setDefaultTextEncodingName("utf-8");// 设置编码
//        detailwebview.setBackgroundColor(Color.argb(0, 0, 0, 0));// 设置背景颜色透明
//        detailwebview.canGoBack();
//        String userAgent = detailwebview.getSettings().getUserAgentString();
//        detailwebview.getSettings().setUserAgentString(userAgent + ";" + "cn.efunding.app");
//        detailwebview.setVerticalScrollBarEnabled(true);
//        detailwebview.requestFocus();
//        detailwebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        detailwebview.setWebViewClient(new InnerWebViewClient(getActivity()));
//        detailwebview.setWebChromeClient(new InjectedChromeClient(progressWebView.getProgressbar(), "JsCallBack", InnerHostJsScope.class));
//        detailwebview.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                detailwebview.requestFocus();
//                return false;
//            }
//        });
//        ((CommonActivity) getActivity()).getNtb().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!detailwebview.canGoBack()) {
//                    getActivity().finish();
//                } else {
//                    detailwebview.goBack();
//                }
//            }
//        });
//
//        loadData();
//    }
//
//    protected void loadData() {
//
//        homeIntent = getActivity().getIntent();
//        Bundle bundle = homeIntent.getBundleExtra(AppConstant.FLAG_BUNDLE);
//        articleId = bundle.getInt(AppConstant.FLAG_DATA2);
//        mPresenter.getArticleDetail(String.valueOf(articleId));
//        detailwebview.loadUrl("file:///android_asset/model/article.html");
//
//        mPresenter.getCommentList(articleId);
////        mPresenter.getArticleRelateList(String.valueOf(articleId));
//        mPresenter.getArticleFavState(articleId);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        detailwebview.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        detailwebview.onPause();
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (!detailwebview.canGoBack()) {
//                // detailwebview.clearHistory();
//                // detailwebview.clearCache(true);
//                getActivity().finish();
//            } else {
//                detailwebview.goBack();
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        ButterKnife.bind(this, rootView);
//        return rootView;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
//
//    @OnClick({R.id.write_comment_view, R.id.comment_btn, R.id.fav_btn, R.id.share_btn})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.write_comment_view:
//
//                showCommentCreateDialog();
//                break;
//            case R.id.comment_btn:
//                if (articleId <= 0) {
//                    return;
//                }
//                AppIntent.intentToCommentList(getActivity(), articleId);
//                break;
//            case R.id.fav_btn:
//                Object tag = favBtn.getTag();
//                if (tag == null) {
//                    favBtnClicked(articleId);
//                } else {
//                    unFavBtnClicked((int) tag);
//                }
//                break;
//            case R.id.share_btn:
//
////                new ShareHelper().share(getActivity(),);
//                break;
//        }
//    }
//
//    private void showCommentCreateDialog() {
//
//        DuAlertDialog.Builder builder = new DuAlertDialog().createBottomBuilder(getActivity());
//        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_comment_publish, null);
//        builder.setView(commentView);
//        Button button = (Button) commentView.findViewById(R.id.sub_layout);
//        final EditText editText = (EditText) commentView.findViewById(R.id.edit_view);
//        editText.requestFocus();
//        commentDialog = builder.create();
//        commentDialog.getWindow().setGravity(Gravity.BOTTOM);
//        commentDialog.show();
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String value = editText.getText().toString().trim();
//                if (TextUtils.isEmpty(value)) {
//                    return;
//                }
//
//                mPresenter.createComment(articleId, value);
//            }
//        });
//
//    }
//
//
//    private void favBtnClicked(int articleId) {
//
//        if (articleId <= 0) {
//            return;
//        }
//        if (!MyUtils.isLogin()) {
//            AppIntent.intentToLogin(getContext());
//            return;
//        }
//        mPresenter.favActionArticle(articleId, false);
//    }
//
//
//    private void unFavBtnClicked(int favItemId) {
//
//        if (favItemId <= 0) {
//            return;
//        }
//        if (!MyUtils.isLogin()) {
//            AppIntent.intentToLogin(getContext());
//            return;
//        }
//        mPresenter.favActionArticle(favItemId, true);
//    }
//
//
//    private void focusBtnClicked(int bloggerId, boolean status) {
//
//        mPresenter.focusAction(bloggerId, status);
//    }
//
//
//    public static class InnerHostJsScope extends HostJsScope {
//
//        public static void getSuccessFeedBack(final WebView webView, String message) {
//            alert(webView, message);
//        }
//
//        public static void getFailureFeedBack(WebView webView, String message) {
//            alert(webView, message);
//        }
//
//        public static void getUrl(WebView webView, String param, String url) {
//            if (url != null) {
//
//                AppIntent.intentToCommonWeb(webView.getContext(), param, url);
//            }
//        }
//
//        public static void login(WebView webView) {
//
//            AppIntent.intentToLogin(webView.getContext());
//        }
//
//        public static void intentToBloggerInfo(WebView webView, String bloggerId, boolean status) {
//
//            if (!TextUtils.isEmpty(bloggerId)) {
//                AppIntent.intentToBloggerInfo(webView.getContext(), Integer.valueOf(bloggerId), status);
//            }
//        }
//
//        public static void bloggerFocusClicked(WebView webView, String bloggerId, String status) {
//            if (!MyUtils.isLogin()) {
//                AppIntent.intentToLogin(webView.getContext());
//                return;
//            }
//            if (!TextUtils.isEmpty(bloggerId)) {
////                focusBtnClicked(Integer.valueOf(bloggerId), Integer.valueOf(status) == 0);
//            }
//        }
//
//    }
//
//
//    private class MInjectedChromeClient extends InjectedChromeClient {
//
//        public MInjectedChromeClient(ProgressBar progressbar, String injectedName, Class injectedCls) {
//            super(progressbar, injectedName, injectedCls);
//        }
//    }
//
//
//    @Override
//    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
//    }
//
//    @Override
//    public void showLoading(String title) {
//        showProgressBar();
//    }
//
//    @Override
//    public void stopLoading() {
//        dismissProgressBar();
//    }
//
//    @Override
//    public void showErrorTip(String msg) {
//        showShortToast(msg);
//    }
//
//    @Override
//    public void returnArticleData(ArticleDetail data) {
//
//        if (data != null && data.getArticle() != null) {
//
//            ArticleModel.Article article = data.getArticle();
//
//            detailwebview.loadUrl("javascript:setArticleData('" + article.getTitle() + "'" +
//                    ",'" + article.getPtime() + "'" +
//                    ",'" + article.getSource() + "'" +
//                    ",'" + article.getSummary() + "'" +
//                    ",'" + article.getContent() + "'" +
//                    ",'" + article.getPv() + "');");
//
//            if (article.getMedia() != null) {
//                BloggerModel.Media media = article.getMedia();
//
//                detailwebview.loadUrl("javascript:setBloggerData('" + media.getCoverImage() + "'" +
//                        ",'" + media.getName() + "'" +
//                        ",'" + media.getArticleNum() + "'" +
//                        ",'" + media.getObjectId() + "'" +
//                        ",'" + media.getStatus() + "')");
//
//            } else {
//                detailwebview.loadUrl("javascript:hideBloggerLayout();");
//            }
//
//        }
//    }
//
//    @Override
//    public void returnRelateList(ArticleModel articleModel) {
//
//    }
//
//    @Override
//    public void returnFavArticleState(FavActionModel.Favorite result) {
//
//        if (result != null) {
//            favBtn.setTag(result.getObjectId());
//            favBtn.setImageResource(R.drawable.icon_fav_s);
//        } else {
//
//            favBtn.setImageResource(R.drawable.icon_fav_n);
//            favBtn.setTag(null);
//        }
//    }
//
//    @Override
//    public void returnFocusBloggerState(boolean result) {
//        detailwebview.loadUrl("javascript:setBloggerFocusState('" + (result ? 0 : 1) + "');");
//    }
//
//    @Override
//    public void returnCreateComment(CommentCreateModel model) {
//
//        if (model != null) {
//            if (commentDialog != null && commentDialog.isShowing()) {
//                commentDialog.dismiss();
//            }
//            showShortToast(R.string.submit_success);
//        }
//
//    }
//
//    @Override
//    public void returnCommentList(CommentListModel model) {
//        if (model != null && model.getComments() != null) {
////            detailwebview.loadUrl("javascript:setCommentList('" + (result ? 0 : 1) + "');");
//        }
//    }
//}
//
