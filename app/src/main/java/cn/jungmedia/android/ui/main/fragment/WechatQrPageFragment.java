package cn.jungmedia.android.ui.main.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leon.common.base.BaseFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import cn.jungmedia.android.R;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/5/9. 下午9:46
 *
 *
 */
public class WechatQrPageFragment extends BaseFragment {
    @Bind(R.id.imageView)
    ImageView imageView;

    @Override
    protected int getLayoutResource() {
        return R.layout.frag_wechat_qr;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
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

    @OnLongClick(R.id.imageView)
    public void onViewClicked() {
        saveRes2Gallery(R.drawable.wechat_qr, "JungFinance_wechat.png");
    }

    /**
     * @param resId   获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public void saveRes2Gallery(int resId, String picName) {

        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            //使用BitmapFactory把res下的图片转换成Bitmap对象
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
            //使用图片压缩对图片进行处理  压缩的格式  可以是JPEG、PNG、WEBP
            //第二个参数是图片的压缩比例，第三个参数是写入流
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);

            //通知相册更新
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                    bitmap, fileName, null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            getActivity().sendBroadcast(intent);

            showShortToast("图片已保存到相册");

        } catch (Exception e) {
            Log.e(TAG, "saveRes2Gallery", e);
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
