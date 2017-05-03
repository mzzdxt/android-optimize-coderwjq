package com.coderwjq.dagger2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    // 当看到某个类被@Inject标记时，就会到他的构造方法中，
    // 如果这个构造方法也被@Inject标记的话，就会自动初始化这个类，从而完成依赖注入。
    @Inject
    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void updateUI() {

    }
}
