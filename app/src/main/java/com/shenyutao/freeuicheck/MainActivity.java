package com.shenyutao.freeuicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shenyutao.mylibrary.IgnoreUiUpdateCheck;

import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Activity Thread");
            }
        });

        test();
    }


    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                IgnoreUiUpdateCheck.getInstance().changedViewRootImplThread(MainActivity.this, Thread.currentThread());

                textView.setText("Child Thread");
                IgnoreUiUpdateCheck.getInstance().restoreViewRootImplOriginThread();
            }
        }).start();
    }
}