package com.hw.pq.app;

import android.os.Environment;

import com.hw.pq.model.http.ApiService;

import java.io.File;

/**
 * Created by codeest on 2016/8/3.
 */
public class Constants {

    //================= TYPE ====================

    public static final int TYPE_FRAME1 = 101;

    public static final int TYPE_FRAME3 = 106;

    public static final int TYPE_FRAME2 = 107;

    public static final int TYPE_SETTING = 108;

    public static final int TYPE_LIKE = 109;

    public static final int TYPE_ABOUT = 110;


    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";


    public static final String SP_NIGHT_MODE = "night_mode";

    public static final String SP_NO_IMAGE = "no_image";

    public static final String SP_AUTO_CACHE = "auto_cache";

    public static final String SP_CURRENT_ITEM = "current_item";

    public static final String SP_LIKE_POINT = "like_point";

    public static final String SP_VERSION_POINT = "version_point";

    public static final String COOKIE = "cookie";

    //===================== 签名KEY =======================
    public static final String SIGN_KEY = "qwewqrrmrtrefcodsfkew234324`1@!~dsasadsadas";

    public static final String PID = "4";

    public static final String UDKey =  "54f473c8-f023-4189-8790-1f9a79e0d174";

    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDT//6/KM3nW0Pwx6rlSb22Z4zf" +
            "DaIYVPtBAAillsAxeyL/HcCwowQ0JGbWjYoxMgkn2zRyAXWOCWAUJY5LFYfKrkAo" +
            "e0J6Hv9GKv0XPkL3zcMKuAeXdEG/R2Xmp14dnzv6baM8gF0yf7Oj6jFpNtzh6a84" +
            "B4kKou+A+03GjxCF5QIDAQAB";

    public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANP//r8ozedbQ/DH" +
            "quVJvbZnjN8NohhU+0EACKWWwDF7Iv8dwLCjBDQkZtaNijEyCSfbNHIBdY4JYBQl" +
            "jksVh8quQCh7Qnoe/0Yq/Rc+QvfNwwq4B5d0Qb9HZeanXh2fO/ptozyAXTJ/s6Pq" +
            "MWk23OHprzgHiQqi74D7TcaPEIXlAgMBAAECgYAu/L68JLGaVR6WV9LXVgQ/0Oym" +
            "pXFnXjKAAh9ogCSh8u5bR/9kxwTP/79xT1axN6tS06FooU0qSYargHVsy91YFgkg" +
            "bLHH4CmFDO6RGS2b0cR3Oq44OIsAjagqtzV9NtOBRxuSOQouwGGFl5AtBZUA3ho1" +
            "9jKeR0nYusshXK5kUwJBAOjtu4aFXQdrw70Dk/47w+jIyZKy4rJ7lcogiAV7SPhx" +
            "B53Qdg/0udVu/87wGez4UEX18CotGv56liFO7FWmPacCQQDo/5V35Ozomc5vSRyc" +
            "hCP7Y/bf/Uz7LA33ZNJ+84oszZuffd+oR0VlL3LNI9ly52PAzWFFhEB38dQUIZ9I" +
            "lcmTAkBYBa698Rvst/6qKPX+NxublnUGKO+ePzGlxpFgcOxsZlpYevSCpxqq2110" +
            "GCvT7yp2pa/yYEU5MZ4WTDrnSwE1AkEA1ujP0/uq7UzY1VrpchCMI9droeTAubhD" +
            "1ZoAcipum662SWFQB6ZdgtxGFqNncY2uO+r0OqumzzuWc08j3U6T0wJAbtNFiitn" +
            "hoBKWOpGJgEMNtcHmh3LxSTykulSK81QmQtk3i6Z4CzrzT66/1sP0ymL/PrSECha" +
            "GfmsNBGIzpkIEQ==";

}
