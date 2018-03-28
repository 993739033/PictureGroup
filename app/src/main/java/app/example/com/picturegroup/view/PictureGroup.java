package app.example.com.picturegroup.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.example.com.picturegroup.R;


/**
 * Created by zwx on 2018/3/9.
 */
//拍照Group
public class PictureGroup extends RecyclerView {
    private Context context;
    private String view_title;
    private boolean view_title_auto_increase;
    private float group_item_margin;
    private int view_take_pic_icon, view_none_icon, group_row, group_column;
    private int mTouchSlop, downX, downY;
    private int REQUEST_CODE_START = 600;
    private String UUID = java.util.UUID.randomUUID().toString();

    public PictureGroup(Context context) {
        super(context);
        initView(context, null);
    }

    public PictureGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public PictureGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context mContext, AttributeSet attrs) {
        context = mContext;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PictureGroup);
        view_title = typedArray.getString(R.styleable.PictureGroup_view_title);
        view_take_pic_icon = typedArray.getInt(R.styleable.PictureGroup_view_take_pic_icon, -1);
        view_none_icon = typedArray.getInt(R.styleable.PictureGroup_view_none_icon, -1);
        view_title_auto_increase = typedArray.getBoolean(R.styleable.PictureGroup_view_title_auto_increase, false);
        group_item_margin = typedArray.getDimension(R.styleable.PictureGroup_group_item_margin, 10);
        group_row = typedArray.getInt(R.styleable.PictureGroup_group_row, 2);
        group_column = typedArray.getInt(R.styleable.PictureGroup_group_column, 2);
        typedArray.recycle();

        final GridLayoutManager manager = new GridLayoutManager(context, group_row, LinearLayoutManager.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 && view_title != null) {
                    return manager.getSpanCount();
                }
                return 1;
            }
        });
        this.setLayoutManager(manager);
        this.setAdapter(new GroupAdapter());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                return super.onInterceptTouchEvent(e);
            case MotionEvent.ACTION_MOVE:
                return super.onInterceptTouchEvent(e);
            case MotionEvent.ACTION_UP:
                return false;
        }
        return super.onTouchEvent(e);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        super.onMeasure(widthSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }

    //注册ActivityResult
    public void registerResult() {

    }

    class GroupAdapter extends Adapter {
        private int TYPE_TITLE = 101;
        private int TYPE_ITEM = 102;
        private LayoutInflater layoutInflater;

        @Override
        public int getItemViewType(int position) {
            if (position == 0 && view_title != null) {
                return TYPE_TITLE;
            } else {
                return TYPE_ITEM;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (layoutInflater == null) {
                layoutInflater = LayoutInflater.from(parent.getContext());
            }
            if (viewType == TYPE_TITLE) {
                View view = layoutInflater.inflate(R.layout.pic_group_title, parent, false);
                return new VH_Title(view);
            } else if (viewType == TYPE_ITEM) {
                View view = layoutInflater.inflate(R.layout.pic_view, parent, false);
                return new VH(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof VH) {
                ((VH) holder).bindView(position, new OnTakePhotoListener() {
                    @Override
                    public void takePhoto(int index) {

                    }
                });
            } else if (holder instanceof VH_Title) {
                ((VH_Title) holder).bindView();
            }
        }

        @Override
        public int getItemCount() {
            if (view_title != null) {
                return group_row * group_column + 1;
            }
            return group_row * group_column;
        }

        class VH extends RecyclerView.ViewHolder {
            private TextView tv_title;//拍照提示
            private ImageView img_take_photo_icon;//拍照提示右侧图片
            private ImageView img_none;//照片显示无时的占位图片
            private ImageView img_photo;//照片
            private View layout_take_photo;//包含 tv_title、img_take_photo_icon
            private String Dir;//图片保存的文件夹
            private String PicName;//图片名称
            private int Index;//position


            public VH(@NonNull View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.tv_title);
                img_take_photo_icon = itemView.findViewById(R.id.img_take_photo_icon);
                img_none = itemView.findViewById(R.id.img_none);
                img_photo = itemView.findViewById(R.id.img_photo);
                layout_take_photo = itemView.findViewById(R.id.layout_take_photo);
            }

            public void bindView(int position, final OnTakePhotoListener takePhotoListener) {
                Index = position;
                img_photo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        takePhotoListener.takePhoto(Index);
                    }
                });

                layout_take_photo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        takePhotoListener.takePhoto(Index);
                    }
                });

            }
        }

        class VH_Title extends ViewHolder {
            TextView tv_title;

            public VH_Title(View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.tv_title);
            }

            public void bindView() {
                tv_title.setText(view_title);
            }
        }
    }


    private interface OnTakePhotoListener{
        void takePhoto(int index);
    }

}
