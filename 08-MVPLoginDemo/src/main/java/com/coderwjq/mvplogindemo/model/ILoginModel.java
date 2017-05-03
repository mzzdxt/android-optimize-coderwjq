package com.coderwjq.mvplogindemo.model;

import com.coderwjq.mvplogindemo.bean.User;

/**
 * Created by coderwjq on 2017/5/2.
 * Desc:用于编写处理数据的逻辑
 */

public interface ILoginModel {
    // 登陆
    void login(String userName, String password, onLoginListener listener);

    interface onLoginListener {
        void loginSuccess(User user);

        void loginFailed(String reason);
    }
}
