package me.liujia95.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewStub;

/**
 * Author: LiuJia on 2017/11/10 0010 18:26.
 * Email: liujia95me@126.com
 */

public class PageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        int viewLayoutRes = getIntent().getIntExtra("viewLayoutRes", 0);
        ViewStub sampleStub = (ViewStub) findViewById(R.id.viewStub);
        sampleStub.setLayoutResource(viewLayoutRes);
        sampleStub.inflate();
    }
}
