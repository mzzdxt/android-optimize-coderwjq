package com.coderwjq.dagger2demo;

import dagger.Component;

/**
 * Created by coderwjq on 2017/4/28.
 */

@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
