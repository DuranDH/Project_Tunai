package com.hw.pq.mvp.activity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hw.pq.R;
import com.hw.pq.base.SimpleActivity;
import com.hw.pq.util.T;
import com.hw.pq.util.ThreadPoolUtil;
import com.socks.library.KLog;
import com.zhangke.websocket.SimpleListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class PQMainActivity extends SimpleActivity {
    private String TAG = "PQMainActivity";
    private EditText textView;
    private EditText tvSend;
    private final String address  = "ws://192.168.99.117:8000/";// 服务器地址
    private WebSocketSetting sSetting;
    private WebSocketManager sManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_pq_main;
    }

    @Override
    protected void initEventAndData() {
        textView = (EditText) findViewById(R.id.tv);
        tvSend = (EditText) findViewById(R.id.tv_send);
        init();
    }

    public void init() {
        sSetting = new WebSocketSetting();
        sSetting.setConnectUrl(address);
        sManager = WebSocketHandler.init(sSetting);
        sManager.addListener(new SimpleListener() {
            @Override
            public <T> void onMessage(String message, T data) {
                super.onMessage(message, data);
                KLog.d("message: " + message);
                textView.setText(textView.getText().toString() + "\n\n" + message);
            }

            @Override
            public <T> void onMessage(ByteBuffer bytes, T data) {
                super.onMessage(bytes, data);
                KLog.d("message: " + bytes.toString());
                textView.setText(bytes.toString());
            }
        });
        sManager.start();
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delay:
                sendMessage(delay);
                break;
            case R.id.btn_location:
                sendMessage(nextLoc);
                break;
            case R.id.btn_adjust:
                sendMessage(adjustMac);
                break;
            default:
                break;
        }
    }

    public void sendMessage(final String message) {
        ThreadPoolUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                sManager.send(message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSend.setText(message);
                    }
                });
            }
        });
    }

    // 设置寻迹到达定位点后的延时
    String delay = "{\"instruction\": \"configure_operation\"," +
            "\"type\": \"set\"," +
            "\"value\": {" +
            "\"operation\": \"follow_waypoint\"," +
            "\"value\": {" +
            "\"post_delay\": 1" +
            "}" +
            "}" +
            "}\n";

    // 前往下一个定位点
    String nextLoc = "{\"instruction\": \"start_operation\"," +
            "\"type\": \"set\"," +
            "\"value\": {" +
            "\"operation\": \"follow_waypoint\"," +
            "\"value\": {" +
            "\"target\": \"next_keypoint\"" +
            "}" +
            "}" +
            "}\n";

    // 微调机械臂
    String adjustMac = "{\"instruction\": \"start_operation\"," +
            "\"type\": \"set\"," +
            "\"value\": {\n" +
            "\"operation\": \"fine_tune_manipulator\"," +
            "\"value\": {" +
            "\"target\": {" +
            "\"x\": 1.0," +
            "\"y\": 0.0," +
            "\"z\": 0.0," +
            "\"roll\": 0.0," +
            "\"pitch\": 0.0," +
            "\"yaw\": 0.75，" +
            "}," +
            "\"current\": {" +
            "\"x\": 1.0," +
            "\"y\": 0.0," +
            "\"z\": 0.0," +
            "\"roll\": 0.0," +
            "\"pitch\": 0.0," +
            "\"yaw\": 0.75，" +
            "}" +
            "}" +
            "}" +
            "}\n";

}
