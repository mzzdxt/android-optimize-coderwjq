package com.coderwjq.testaidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.coderwjq.a03_androidaidldemo.Book;
import com.coderwjq.a03_androidaidldemo.BookManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private BookManager mBookManager = null;
    // 用来标记当前与服务端连接状况的布尔值
    private boolean mBound = false;

    private List<Book> mBooks;
    private Button mBtnConnRemoteService;
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "]");

            mBookManager = BookManager.Stub.asInterface(service);
            mBound = true;

            if (mBookManager != null) {
                try {
                    mBooks = mBookManager.getBooks();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnConnRemoteService = (Button) findViewById(R.id.btn_conn_remote_service);

        mBtnConnRemoteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connRemoteService();
            }
        });
    }

    private void connRemoteService() {
        if (mBound == false) {
            Toast.makeText(this, "正在连接远程服务，请稍后再试...", Toast.LENGTH_SHORT).show();
            attempToBindService();
            return;
        }

        if (mBookManager == null) {
            return;
        }

        Book book = new Book();
        book.setName("喳喳的新书");
        book.setPrice(30);

        try {
            mBookManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void attempToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.coderwjq.aidl");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        ComponentName cn = new ComponentName("com.coderwjq.a03_androidaidldemo", "com.coderwjq.a03_androidaidldemo" + ".AIDLService");//另一个app的pkg和service名称
        intent.setComponent(cn);

        boolean bindService = bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "attempToBindService() called bindResult:" + bindService);
        Toast.makeText(this, "绑定服务成功...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attempToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConn);
            mBound = false;
        }
    }
}
