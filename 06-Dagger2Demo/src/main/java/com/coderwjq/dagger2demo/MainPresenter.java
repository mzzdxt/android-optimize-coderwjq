package com.coderwjq.dagger2demo;

import javax.inject.Inject;

/**
 * Created by coderwjq on 2017/4/28.
 */

public class MainPresenter {

    private MainContract.View mView;

    @Inject
    public MainPresenter(MainContract.View view) {
        mView = view;
    }

    public void loadData() {
        // 调用model层的方法，加载数据

        // 回调方法成功时
        mView.updateUI();
    }
}
