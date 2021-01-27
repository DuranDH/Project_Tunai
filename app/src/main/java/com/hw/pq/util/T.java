package com.hw.pq.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hw.pq.app.App;


/**
 * Created by codeest on 2016/8/4.
 */
public class T {

    static T td;

    public static void show(int resId) {
        show(App.getInstance().getString(resId));
    }

    public static void show(String msg) {
        if (td == null) {
            td = new T(App.getInstance());
        }
        td.setText(msg);
        td.create().show();
    }

    public static void shortShow(String msg) {
        if (td == null) {
            td = new T(App.getInstance());
        }
        td.setText(msg);
        td.createShort().show();
    }


    Context context;
    Toast toast;
    String msg;

    public T(Context context) {
        this.context = context;
    }

    public Toast create() {
        View contentView = View.inflate(context, com.hw.pq.R.layout.dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(com.hw.pq.R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 500);
        toast.setDuration(Toast.LENGTH_LONG);
        tvMsg.setText(msg);
        return toast;
    }

    public Toast createShort() {
        View contentView = View.inflate(context, com.hw.pq.R.layout.dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(com.hw.pq.R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 500);
        toast.setDuration(Toast.LENGTH_SHORT);
        tvMsg.setText(msg);
        return toast;
    }

    public void show() {
        if (toast != null) {
            toast.show();
        }
    }

    public void setText(String text) {
        msg = text;
    }
}
