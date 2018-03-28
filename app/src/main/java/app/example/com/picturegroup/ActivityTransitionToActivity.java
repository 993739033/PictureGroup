package app.example.com.picturegroup;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ActivityTransitionToActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_transition_to);
        bindData();
    }

    public void bindData() {
        setTitle("");
        String path = getIntent().getStringExtra("path");
//        Uri uri = ;
        if (path.startsWith("http")) {
            Picasso.with(this).load(path).memoryPolicy(MemoryPolicy.NO_CACHE).into((ImageView) findViewById(R.id.iv_photo));
        } else {
            Picasso.with(this).load(Uri.fromFile(new File(path))).memoryPolicy(MemoryPolicy.NO_CACHE).into((ImageView) findViewById(R.id.iv_photo));
        }
    }
}
