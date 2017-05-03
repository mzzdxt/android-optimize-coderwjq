package com.coderwjq.mvplogindemo.view;

import com.coderwjq.mvplogindemo.bean.User;

/**
 * Created by coderwjq on 2017/5/2 10:17.
 * Desc:View是Presenter和View的接口，提供方法供Activity使用
 */

public interface LoginView {
    // 获取用户输入的数据
    String getUserName();

    String getPassword();

    // 显示和隐藏登陆的对话框
    void showLoading();

    void hideLoading();

    // 登陆成功或失败后的提示
    void showSuccessMsg(User user);

    void showFailedMsg(String reason);

    // 清除数据
    void clearEditText();

    // 判断信息是否输入完整
    boolean isUserInfoComplete();

    void showMessageIncomplete();

}
