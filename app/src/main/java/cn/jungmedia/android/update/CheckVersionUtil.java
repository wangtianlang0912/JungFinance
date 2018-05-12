package cn.jungmedia.android.update;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.baidu.cn.vm.version.BuilderImpl;
import com.baidu.cn.vm.version.UpdaterIml;
import com.baidu.cn.vm.version.VCCallback;
import com.baidu.cn.vm.version.VersionHelper;
import com.baidu.cn.vm.version.VersionManager;
import com.google.gson.Gson;

import cn.jungmedia.android.R;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.utils.MyUtils;

/**
 * Created by zhangmeng on 16/5/16.
 */
public class CheckVersionUtil {

    public void checkUpdate(Activity context, VCCallback callback) {
        checkUpdate(context, false, callback);
    }

    public void checkUpdate(final Activity context, final boolean autoCheck, final VCCallback callback) {
//        String url = getUrl(context);
        String url = ApiConstants.URL_UPDATE;
        VersionHelper.update(url, new BuilderImpl() {
            @Override
            public VersionManager.Builder builder(String json) {
                //解析数据
                Gson gson = new Gson();
                OnlineVersion.VersionModel versionModel = gson.fromJson(json, OnlineVersion.VersionModel.class);
                OnlineVersion onLineVersion1 = versionModel.data;

                if (onLineVersion1 == null) {
                    if (callback != null) {
                        callback.hasUpdate(false);
                    }
                    return null;
                }

                if ("1".equals(onLineVersion1.getForce())) {
                    onLineVersion1.setForceUpdate(true);
                } else {
                    onLineVersion1.setForceUpdate(false);
                }
                //构造数据
                VersionManager.Builder builder = new VersionManager.Builder(context);
                builder.setApkUrl(onLineVersion1.getUrl());
                builder.setDescribe(onLineVersion1.getDescription());
                builder.setIsForce(onLineVersion1.isForceUpdate());
                builder.setAutoDownloadByWifi(true);
                builder.setAutoCheck(autoCheck);
                builder.setVersionName(onLineVersion1.getVersion());
                builder.setVersionCode(onLineVersion1.getVersionCode());
                builder.setIcon(getNotificationIcon());
                builder.setDirName(context.getPackageName());
                builder.setFileName(MyUtils.getAppName(context) + ".apk");
                builder.setVcCallback(callback);
                builder.setDialogTheme(R.style.Theme_Transparent_Background_Dialog);
                return builder;
            }
        });
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

    UpdaterIml createUpdater() {
        UpdaterIml updater = new UpdaterIml();
        return null;
    }

    private OnlineVersion securityCheck(Context context, OnlineVersion onlineVersion) {
        String[] cNums = this.getSplitVersionName(this.getCurrentVersionName(context));

//        OnlineVersion onlineVersion = this.getOnlineVersion(context);
        if (onlineVersion != null) {
            String[] oNums = this.getSplitVersionName(onlineVersion.getVersion());
            if (compareNums(oNums, cNums) == 1) {
                return onlineVersion;
            }
        }
        return null;
    }

    /**
     * 取最新版本号
     * <p/>
     * 财经IOS使用的参数有  udid=openUDID guid= mac md5
     * 体育android使用的参数是guid=imei ： null ？  WeiboDeviceId ： IMEI
     * <p/>
     * 本接口的用途除了检查版本号之外，还用于dss平台统计dau及新增激活，因此
     * 任何改动应该事先与产品负责人及信息系统部统计工作人员沟通清楚；本接口必须在每次启动时调用，否则会影响KPI
     * <p/>
     * 目前直观的看到就是
     * http://mobile.dp.erp.sina.com.cn/index.php/main/mIndex
     * 右侧的“新增激活量”
     */
//    private String getUrl(Context context) {
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        LogUtils logUtils = LogUtils.getInstance(context);
//        params.add(new BasicNameValuePair("guid", logUtils.getDeviceId()));
//        params.add(new BasicNameValuePair("av", this.getCurrentVersionName(context)));
//        params.add(new BasicNameValuePair("ov", Build.VERSION.RELEASE));
//        params.add(new BasicNameValuePair("sr", SinaUtils.getScreenResolution(context)));
//        params.add(new BasicNameValuePair("dn", getSystemDN()));
//        params.add(new BasicNameValuePair("nt", SinaUtils.getNetworkTypeName(context)));
//        params.add(new BasicNameValuePair("ci", "sina"));
//        params.add(new BasicNameValuePair("from", getFrom(context)));
//        String url_Get = SinaHttpUtils.getURL(this.URL_Version, params);
//        return url_Get;
//    }
    private String getCurrentVersionName(Context context) {
        String curVersion = null;
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            curVersion = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("getCurrentVersion", "NameNotFoundException");
        }
        return curVersion;
    }

    private String getSystemDN() {
        String s = Build.MODEL;
        if (!"".equals(s)) {
            s = s.replaceAll(" ", "_");
        }
        return s;
    }

//    private String getFrom(Context context) {
//        String from = "";
//        String fromFirst = SharedPreferenceData.getStringSp(context, R.string.key_apk_from_value);
//        //取渠道号CHWM
//        String fromLast = SinaUtils.getCHWM(context);
//
//        if (fromFirst != null && fromFirst.length() > 0) {
//            if (fromFirst.equals(fromLast)) {
//                return fromFirst;
//            }
//            from = fromFirst;
//        }
//        if (fromLast != null && fromLast.length() > 0) {
//            if (from != null && from.length() > 0) {
//                from += ",";
//            } else {
//                //第一次下载渠道号没有值时，已读取的配置文件属性为准
//                SharedPreferenceData.writeStringSp(context, R.string.key_apk_from_value, fromLast);
//            }
//            from += fromLast;
//        }
//
//        return from;
//    }

    private String[] getSplitVersionName(String versionName) {
        if (versionName != null) {
            return versionName.split("\\.");
        } else {
            return null;
        }
    }

    private int getIntOfString(String s) {
        int n = 0;
        if (s != null) {
            try {
                n = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                n = 0;
            }
        }
        return n;
    }

    private int compareNums(String[] newVersion, String[] oldVersion) {
        if (newVersion != null && oldVersion != null) {
            int newNum = 0;
            int oldNum = 0;

            for (int i = 0; i < newVersion.length; i++) {
                newNum = this.getIntOfString(newVersion[i]);

                if (oldVersion.length > i) {
                    oldNum = this.getIntOfString(oldVersion[i]);
                } else {
                    oldNum = -1;
                }
                if (newNum > oldNum) {
                    return 1;
                } else if (newNum < oldNum) {
                    return -1;
                }
            }

            if (newVersion.length < oldVersion.length) {
                return -1;
            } else if (newVersion.length > oldVersion.length) {
                return 1;
            }
        }
        return 0;
    }

}
