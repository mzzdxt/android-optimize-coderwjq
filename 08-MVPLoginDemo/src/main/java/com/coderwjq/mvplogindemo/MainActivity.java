package com.coderwjq.mvplogindemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coderwjq.mvplogindemo.bean.User;
import com.coderwjq.mvplogindemo.presenter.LoginPresenter;
import com.coderwjq.mvplogindemo.view.LoginView;
import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LoginView {
    private static final String TAG = "MainActivity";

    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_user_password)
    EditText mEtUserPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_clear)
    Button mBtnClear;
    @BindView(R.id.btn_finger_login)
    Button mBtnFingerLogin;
    private LoginPresenter mLoginPresenter;

    private ProgressDialog mProgressDialog;

    private FingerprintIdentify mFingerprintIdentify;
    private boolean isCalledStartIdentify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initProgressDialog();

        mLoginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initFingerIdentify();
    }

    private void initFingerIdentify() {
        if (isCalledStartIdentify) {
            // 如果已经显示，则调用resumeIdntify()
            mFingerprintIdentify.resumeIdentify();
            return;
        }

        isCalledStartIdentify = true;
        mFingerprintIdentify = new FingerprintIdentify(this, new BaseFingerprint.FingerprintIdentifyExceptionListener() {
            @Override
            public void onCatchException(Throwable exception) {
                Toast.makeText(MainActivity.this, "指纹识别失败：" + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // 判断硬件是否支持指纹识别
        Log.d(TAG, "initFingerIdentify() called isHardwareEnable" + mFingerprintIdentify.isHardwareEnable());
        // 判断是否已经注册过指纹
        Log.d(TAG, "initFingerIdentify() called isRegisteredFingerprint" + mFingerprintIdentify.isRegisteredFingerprint());
        // 判断指纹是否可用
        Log.d(TAG, "initFingerIdentify() called isFingerprintEnable" + mFingerprintIdentify.isFingerprintEnable());

        if (!mFingerprintIdentify.isFingerprintEnable()) {
            Toast.makeText(MainActivity.this, "指纹不可用 %>_<%", Toast.LENGTH_SHORT).show();
        }
    }


    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在登陆，请等待...");
    }

    @Override
    public String getUserName() {
        return mEtUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtUserPassword.getText().toString();
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }

    }

    @Override
    public void hideLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showSuccessMsg(User user) {
        Toast.makeText(this, "登陆成功:" + user.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedMsg(String reason) {
        Toast.makeText(this, "登陆失败" + reason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearEditText() {
        mEtUserName.setText("");
        mEtUserPassword.setText("");

        // 让用户名输入框获取焦点
        mEtUserName.requestFocus();
    }

    @Override
    public boolean isUserInfoComplete() {
        boolean isUserNameNull = mEtUserName.getText().toString().equals("");
        boolean isPasswordNameNull = mEtUserPassword.getText().toString().equals("");
        return !(isUserNameNull || isPasswordNameNull);
    }

    @Override
    public void showMessageIncomplete() {
        Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_login, R.id.btn_clear, R.id.btn_finger_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Log.d(TAG, "onClick() called with: view = [" + view + "]");
                mLoginPresenter.login();
                break;
            case R.id.btn_clear:
                mLoginPresenter.clear();
                break;
            case R.id.btn_finger_login:
                loginWithPrintIdentify();
                break;
        }
    }

    private void loginWithPrintIdentify() {
        showLoading();
        mFingerprintIdentify.resumeIdentify();

        mFingerprintIdentify.startIdentify(0, new BaseFingerprint.FingerprintIdentifyListener() {
            @Override
            public void onSucceed() {
                hideLoading();

                mEtUserName.setText("coderwjq");
                mEtUserPassword.setText("coderwjq");
                mEtUserPassword.requestFocus();
                mEtUserPassword.setSelection(mEtUserPassword.getText().length());
            }

            @Override
            public void onNotMatch(int availableTimes) {
                hideLoading();
                Toast.makeText(MainActivity.this, "finger idntify not match", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                hideLoading();
                Toast.makeText(MainActivity.this, "finger idntify failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFingerprintIdentify.cancelIdentify();
    }
}
