package app.example.com.picturegroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.example.com.picturegroup.view.PictureGroup;

public class MainActivity extends AppCompatActivity {
    private PictureGroup pic_group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pic_group= findViewById(R.id.pic_group);

    }
}
