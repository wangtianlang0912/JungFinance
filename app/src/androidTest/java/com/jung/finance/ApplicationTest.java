package com.jung.finance;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jung.finance.api.Api;
import com.jung.finance.bean.ArticleModel;
import com.leon.common.basebean.BaseRespose;

import java.lang.reflect.Type;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);


        String data = "{\"articles\":[{\"summary\":\"彼得·泰尔对当前主流媒体经常提到的“区块链而不是比特币”的炒作也持怀疑态度。\",\"isHead\":1,\"keyword\":\"比特币，区块链\",\"image\":\"/image/2018-03-20/1c970c0a9452fb1baab3e58c772a31b1.jpg\",\"columnId\":21,\"vtime\":1521555816,\"author\":\"\",\"title\":\"PayPal创始人：比特币将是全球金融危机的风险对冲\",\"source\":\"\",\"objectId\":308780,\"column\":{\"objectId\":21,\"title\":\"区块链\",\"keyword\":\"区块链，ICO，币圈\"},\"subtitle\":\"PayPal创始人：比特币将是全球金融危机的风险对冲\",\"pv\":762,\"attributes\":[],\"isRecommend\":1},{\"summary\":\"\",\"isHead\":1,\"keyword\":\"加密货币\",\"image\":\"/image/swoop/default.jpg\",\"columnId\":21,\"vtime\":1521475200,\"author\":\"火讯财经\",\"title\":\"无惧Twitter加密货币广告禁令 比特币价格回升至8000美元\",\"source\":\"火讯财经\",\"objectId\":307276,\"column\":{\"objectId\":21,\"title\":\"区块链\",\"keyword\":\"区块链，ICO，币圈\"},\"subtitle\":\"无惧Twitter加密货币广告禁令 比特币价格回升至8000美元\",\"pv\":509,\"attributes\":[],\"isRecommend\":0},{\"summary\":\"\",\"isHead\":1,\"keyword\":\"投资，区块链，币圈\",\"image\":\"/image/swoop/default.jpg\",\"columnId\":21,\"vtime\":1521539619,\"author\":\"火讯财经\",\"title\":\"“币圈90后贾跃亭”\",\"source\":\"\",\"objectId\":307073,\"column\":{\"objectId\":21,\"title\":\"区块链\",\"keyword\":\"区块链，ICO，币圈\"},\"subtitle\":\"“币圈90后贾跃亭”\",\"pv\":818,\"attributes\":[],\"isRecommend\":0},{\"summary\":\"\",\"isHead\":1,\"keyword\":\"ico\",\"image\":\"/image/swoop/default.jpg\",\"columnId\":21,\"vtime\":1521388800,\"author\":\"火讯财经\",\"title\":\"立场180度大转弯！法国监管机构将立法使ICO合法化\",\"source\":\"火讯财经\",\"objectId\":306165,\"column\":{\"objectId\":21,\"title\":\"区块链\",\"keyword\":\"区块链，ICO，币圈\"},\"subtitle\":\"立场180度大转弯！法国监管机构将立法使ICO合法化\",\"pv\":518,\"attributes\":[],\"isRecommend\":0},{\"summary\":\"\",\"isHead\":1,\"keyword\":\"火讯早上好\",\"image\":\"/image/swoop/default.jpg\",\"columnId\":21,\"vtime\":1520956800,\"author\":\"火讯财经\",\"title\":\"火讯早上好：Binance正式启动Binance Chain公有链 A股公司对区块链展开竞逐逾70家区块链概念公司上市\",\"source\":\"火讯财经\",\"objectId\":299902,\"column\":{\"objectId\":21,\"title\":\"区块链\",\"keyword\":\"区块链，ICO，币圈\"},\"subtitle\":\"火讯早上好：Binance正式启动Binance Chain公有链 A股公司对区块链展开竞逐逾70家区块链概念公司上市\",\"pv\":547,\"attributes\":[],\"isRecommend\":0},{\"summary\":\"\u200B万达进军区块链有人说王健林后知后觉，跟很多人想当然以为的相反，其实万达很早就开始涉足区块链领域。\",\"isHead\":1,\"keyword\":\"区块链\",\"image\":\"/image/swoop/default.jpg\",\"columnId\":21,\"vtime\":1520956800,\"author\":\"\",\"title\":\"万达迟迟入场区块链？其实人家是区块链先锋\",\"source\":\"火讯财经\",\"objectId\":299901,\"column\":{\"objectId\":21,\"title\":\"区块链\",\"keyword\":\"区块链，ICO，币圈\"},\"subtitle\":\"万达迟迟入场区块链？其实人家是区块链先锋\",\"pv\":520,\"attributes\":[],\"isRecommend\":0},{\"summary\":\"\",\"isHead\":1,\"keyword\":\"区款链 全节点 播酷云\",\"image\":\"/image/swoop/default.jpg\",\"columnId\":21,\"vtime\":1520956800,\"author\":\"火讯财经\",\"title\":\"中国比特币全节点跃居全球第二\",\"source\":\"火讯财经\",\"objectId\":299573,\"column\":{\"objectId\":21,\"title\":\"区块链\",\"keyword\":\"区块链，ICO，币圈\"},\"subtitle\":\"中国比特币全节点跃居全球第二\",\"pv\":530,\"attributes\":[],\"isRecommend\":0},{\"summary\":\"\",\"isHead\":1,\"keyword\":\"比特币\",\"image\":\"/image/swoop/default.jpg\",\"columnId\":21,\"vtime\":1520956800,\"author\":\"火讯财经\",\"title\":\"日本Coincheck称开始补偿丢失新经币的用户恢复提币\",\"source\":\"火讯财经\",\"objectId\":299565,\"column\":{\"objectId\":21,\"title\":\"区块链\",\"keyword\":\"区块链，ICO，币圈\"},\"subtitle\":\"Coincheck：开始补偿丢失新经币的用户，并恢复部分业务\",\"pv\":519,\"attributes\":[],\"isRecommend\":0},{\"summary\":\"近日，比利时金融监管部门便公布了遭到投诉的19个加密货币交易平台，认为这些平台存在欺诈可能。\",\"isHead\":1,\"keyword\":\"区块链\",\"image\":\"/image/swoop/default.jpg\",\"columnId\":21,\"vtime\":1520784000,\"author\":\"火讯财经\",\"title\":\"比利时监管部门揭露欺诈性加密货币交易平台高达19家：回报率高的通常是假的\",\"source\":\"火讯财经\",\"objectId\":297123,\"column\":{\"objectId\":21,\"title\":\"区块链\",\"keyword\":\"区块链，ICO，币圈\"},\"subtitle\":\"比利时监管部门揭露欺诈性加密货币交易平台高达19家：回报率高的通常是假的\",\"pv\":595,\"attributes\":[],\"isRecommend\":1},{\"summary\":\"今日北京一则年薪500万元人民币区块链招聘广告刷爆朋友圈，要求应聘者对区块链技术和密码学有深入研究，旺盛的人才需求吸引了大量的培训机构趁机敛财。\",\"isHead\":1,\"keyword\":\"区块链\",\"image\":\"/image/swoop/default.jpg\",\"columnId\":21,\"vtime\":1520784000,\"author\":\"火讯财经\",\"title\":\"区块链高烧“后遗症”：500万元年薪有价无市 培训机构借机揽财\",\"source\":\"火讯财经\",\"objectId\":297122,\"column\":{\"objectId\":21,\"title\":\"区块链\",\"keyword\":\"区块链，ICO，币圈\"},\"subtitle\":\"区块链高烧“后遗症”：500万元年薪有价无市\",\"pv\":591,\"attributes\":[],\"isRecommend\":1}],\"counter\":{\"pageSize\":10,\"total\":3625,\"pageCount\":363,\"pageIndex\":1}}";

        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls();
        gsonBuilder.registerTypeAdapter(BaseRespose.class, new Api.BaseResponseJsonDeserializer());
        Gson gson = gsonBuilder.create();
        Type type = new TypeToken<BaseRespose<ArticleModel>>() {
        }.getType();
        BaseRespose<ArticleModel> baseRespose = gson.fromJson(data, type);

        assertEquals(true, !baseRespose.data.getArticles().isEmpty());

    }


}