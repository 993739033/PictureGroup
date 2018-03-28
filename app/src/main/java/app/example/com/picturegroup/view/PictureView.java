package app.example.com.picturegroup.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.example.com.picturegroup.R;
import app.example.com.picturegroup.utils.PhotoViewModel;

/**
 * Created by zwx on 2018/3/9.
 */

public class PictureView extends LinearLayout implements PhotoViewModel.OnClickListener, View.OnClickListener {
    private Context context;
    private LayoutInflater layoutInflater;
    private TextView tv_title;
    private ImageView img_take_photo_icon,img_none, img_photo;
    private View layout_take_photo;
    private static PhotoViewModel photoModel;
    private String nameManager="aa";
    private String currentPhotoFile;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public PictureView(Context context) {
        super(context);
        initView(context,null);
    }

    public PictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public PictureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context mContext,@Nullable AttributeSet attrs){
        context = mContext;
        layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.pic_view, this, true);
        tv_title = findViewById(R.id.tv_title);
        img_take_photo_icon = findViewById(R.id.img_take_photo_icon);
        img_none = findViewById(R.id.img_none);
        img_photo = findViewById(R.id.img_photo);
        layout_take_photo = findViewById(R.id.layout_take_photo);
        initListener();
    }

    private void initListener(){
        layout_take_photo.setOnClickListener(this);
        img_photo.setOnClickListener(this);
    }

    public void setTv_title(@NonNull String title) {
        this.tv_title.setText(title);
    }

    public void setImg_take_photo_icon(@DrawableRes int icon) {
        img_take_photo_icon.setBackgroundResource(icon);
    }

    public void setImg_none(@DrawableRes int icon) {
        img_none.setBackgroundResource(icon);
    }

    public void setImg_photo(@DrawableRes int icon) {
        img_photo.setBackgroundResource(icon);
    }

    @Override
    public void onModelTakePhoto(String path) {
        currentPhotoFile = path;
    }

    @Override
    public void onClick(View v) {

    }

    public View getView() {
        return this.getView();
    }
}
