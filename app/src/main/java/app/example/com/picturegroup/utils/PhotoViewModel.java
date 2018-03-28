package app.example.com.picturegroup.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.ref.WeakReference;

import app.example.com.picturegroup.ActivityTransitionToActivity;

/**
 * Created by wyw on 2017/5/23.
 */

public class PhotoViewModel {
    private WeakReference<Activity> mActivity;


    public PhotoViewModel(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    public void bindData(final ImageView img, final int requestCode, final String fileName, final File dir, final OnClickListener listener) {
        bindData(img, requestCode, fileName, dir, listener, null);
    }

    public void bindDataOnlyLarge(final ImageView img, final String fileName, final File dir, final String url, final OnClickListener listener) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(mActivity.get()).load(url).memoryPolicy(MemoryPolicy.NO_CACHE).into(img);
            Picasso.with(mActivity.get()).invalidate(url);
        }
        if (new File(dir, fileName).exists())
            Picasso.with(img.getContext()).load(new File(dir, fileName)).into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img.getDrawable() != null) {
                    String path;
                    File file = new File(dir, fileName + PhotoUtils.SUFFIX);
                    if (file.exists()) {
                        path = file.getAbsolutePath();
                    } else {
                        path = url;
                    }
                    if (Build.VERSION.SDK_INT >= 21) {
                        if (null == path) return;
                        img.setTransitionName("photoView");
                        Intent intent = new Intent(mActivity.get(), ActivityTransitionToActivity.class);
                        intent.putExtra("path", path);
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(mActivity.get(), img, "photoView");
                        mActivity.get().startActivity(intent, options.toBundle());
                    } else {
                        Intent intent = new Intent(mActivity.get(), ActivityTransitionToActivity.class);
                        intent.putExtra("path", path);
                        mActivity.get().startActivity(intent);
                    }
                }
            }
        });
    }

    public void bindData(final ImageView img, final int requestCode, final String fileName, final File dir, final OnClickListener listener, final String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(mActivity.get()).load(url).memoryPolicy(MemoryPolicy.NO_CACHE).into(img);
            Picasso.with(mActivity.get()).invalidate(url);
        }
        if (new File(dir, fileName + PhotoUtils.SUFFIX).exists()) {
//            Glide.with(img.getContext()).load(new File(dir, fileName)).into(img);
            Picasso.with(img.getContext()).load(Uri.fromFile(new File(dir, fileName + PhotoUtils.SUFFIX)))
                    .memoryPolicy(MemoryPolicy.NO_CACHE).into(img);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img.getDrawable() == null) {
                    if (listener != null)
                        listener.onModelTakePhoto(PhotoUtils.takePicture(mActivity.get(), requestCode, fileName, dir));
                } else {
                    String path;
                    File file = new File(dir, fileName + PhotoUtils.SUFFIX);
                    if (file.exists()) {
                        path = file.getAbsolutePath();
                    } else {
                        path = url;
                    }
                    if (Build.VERSION.SDK_INT >= 21) {
                        if (null == path) {
                            return;
                        }
                        img.setTransitionName("photoView");
                        Intent intent = new Intent(mActivity.get(), ActivityTransitionToActivity.class);
                        intent.putExtra("path", path);
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(mActivity.get(), img, "photoView");
                        mActivity.get().startActivity(intent, options.toBundle());
                    } else {
                        Intent intent = new Intent(mActivity.get(), ActivityTransitionToActivity.class);
                        intent.putExtra("path", path);
                        mActivity.get().startActivity(intent);
                    }
                }
            }
        });
    }

    public interface OnClickListener {
        void onModelTakePhoto(String path);
    }
}
