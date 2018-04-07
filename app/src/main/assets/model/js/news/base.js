
function setArticleData(title,ptime,source,summary,content,views){

    document.getElementById("article.title").innerHTML = title;
    document.getElementById("article.ptime").innerHTML = ptime;
    document.getElementById("article.source").innerHTML = '来源：' + source;
    document.getElementById("article.summary").innerHTML = '摘要：' + summary;
    document.getElementById("article.views").innerHTML = '浏览量：' + views;
    document.getElementById("article.content").innerHTML = content;

}


function setBloggerData(logo,name,gnum,uid,status){

    document.getElementById('article.media.count_all').style.display="inline";
    document.getElementById('article.media.button').style.display="inline";
    document.getElementById('article.media.logo').style.display="inline";


    document.getElementById('article.media.logo').src=logo;
    document.getElementById('article.media.name').innerHTML=name;
    document.getElementById('article.media.count').innerHTML=gnum;
    document.getElementById('article_media').tag = uid;
    document.getElementById('article.media.button').innerHTML = (status == 1 ? "+ 关注" : "已关注");
    document.getElementById('article.media.button').tag = status;


}

function hideBloggerLayout() {
    document.getElementById('article_media').style.display="none";
}

// 修改关注状态
function setBloggerFocusState(status) {
    document.getElementById('article.media.button').innerHTML = (status == 1 ? "+ 关注" : "已关注");
    document.getElementById('article.media.button').tag = status;
}

function loadBloggerInfo() {
    var tag = document.getElementById('article_media').tag;
    window.JsCallBack.intentToBloggerInfo(tag);
}

function focusButtonClick() {
  var status = document.getElementById('article.media.button').tag;
  var bloggerId = document.getElementById('article_media').tag;
  window.JsCallBack.bloggerFocusClicked(bloggerId,status);
}
