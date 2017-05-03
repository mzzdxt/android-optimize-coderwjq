package com.coderwjq.mvplogindemo.presenter;

import android.os.Handler;

import com.coderwjq.mvplogindemo.bean.User;
import com.coderwjq.mvplogindemo.model.ILoginModel;
import com.coderwjq.mvplogindemo.model.LoginModelImpl;
import com.coderwjq.mvplogindemo.view.LoginView;

/**
 * Created by coderwjq on 2017/5/2 10:20.
 * Desc:presenter是model和view交互的桥梁，在activity拿到presenter对象后，实现view接口，调用presenter方法即可
 */

public class LoginPresenter {
    private LoginView mLoginView;
    private LoginModelImpl mLoginModelImpl;
    private Handler mHandler;

    public LoginPresenter(LoginView loginView) {
        mLoginView = loginView;
        mLoginModelImpl = new LoginModelImpl();
        mHandler = new Handler();
    }

    public void login() {
        // 判断输入是否为空
        if (!mLoginView.isUserInfoComplete()) {
            mLoginView.showMessageIncomplete();
            return;
        }

        mLoginView.showLoading();

        mLoginModelImpl.login(mLoginView.getUserName(), mLoginView.getPassword(), new ILoginModel.onLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.hideLoading();
                        mLoginView.showSuccessMsg(user);
                    }
                });
            }

            @Override
            public void loginFailed(final String reason) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.hideLoading();
                        mLoginView.showFailedMsg(reason);
                    }
                });
            }
        });
    }

    public void clear() {
        mLoginView.clearEditText();
    }

}
