package com.hw.pq.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SwitchTextView extends TextSwitcher {

    public SwitchTextView(Context context) {
        super(context);
    }

    public SwitchTextView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                //设置显示文字的TextView，内部实际会调用这个方法两次，进入和出去的textview就是从这里创建的
                TextView tv = new TextView(context);
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                tv.setSingleLine();
                tv.setEllipsize(TextUtils.TruncateAt.END);
                return tv;
            }
        });

        TranslateAnimation translateIn = new TranslateAnimation(0, 0, 50, 0);
        TranslateAnimation translateOut = new TranslateAnimation(0, 0, 0, -50);

        AlphaAnimation alphaIn = new AlphaAnimation(0, 1);
        AlphaAnimation alphaOut = new AlphaAnimation(1, 0);

        AnimationSet animatorSetIn = new AnimationSet(true);
        animatorSetIn.addAnimation(translateIn);
        animatorSetIn.addAnimation(alphaIn);
        animatorSetIn.setDuration(2000);

        AnimationSet animatorSetOut = new AnimationSet(true);
        animatorSetOut.addAnimation(translateOut);
        animatorSetOut.addAnimation(alphaOut);
        animatorSetOut.setDuration(2000);

        //设置文字进入和出去的动画
        setInAnimation(animatorSetIn);
        setOutAnimation(animatorSetOut);

    }

    int position = 0;
    int len;
    Timer timer = null;
    //这里将所有需要滚动的文字传进来，并设置循环显示的时间
    public void startPlay(final List<String> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        position = 0;
        len = data.size();
        //设置下一个显示的文字
        setText(data.get(position));
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                position++;
                post(new Runnable() {
                    @Override
                    public void run() {
                        setText(data.get(position%len));
                    }
                });
            }
        }, 3000, 3000);
    }

    public void stopPlay() {
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }

}
