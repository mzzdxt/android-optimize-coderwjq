package com.coderwjq.mvplogindemo.model;

import android.os.SystemClock;

import com.coderwjq.mvplogindemo.bean.User;

/**
 * Created by coderwjq on 2017/5/2.
 * Desc:处理登陆逻辑的实现类
 */

public class LoginModelImpl implements ILoginModel {
    @Override
    public void login(final String userName, final String password, final onLoginListener listener) {
        // 模拟登陆请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);

                if (userName.equals("coderwjq") && password.equals("coderwjq")) {
                    listener.loginSuccess(new User(userName, password));
                } else {
                    listener.loginFailed("用户名或密码错误");
                }
            }
        }).start();
    }
}
