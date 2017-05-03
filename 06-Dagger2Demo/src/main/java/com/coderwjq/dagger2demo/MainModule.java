package com.coderwjq.dagger2demo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by coderwjq on 2017/4/28.
 */

@Module
public class MainModule {
    private final MainContract.View mView;

    public MainModule(MainContract.View view) {
        mView = view;
    }

    @Provides
    MainContract.View provideMainView() {
        return mView;
    }
}
