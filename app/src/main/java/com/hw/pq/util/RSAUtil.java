package com.hw.pq.util;

import com.hw.pq.app.Constants;
import com.blakequ.rsa.Base64Utils;
import com.blakequ.rsa.FileEncryptionManager;

public class RSAUtil {

    private static FileEncryptionManager mFileEncryptionManager;

    private static void initKey() throws Exception {
        mFileEncryptionManager = FileEncryptionManager.getInstance();
        mFileEncryptionManager.setRSAKey(Constants.PUBLIC_KEY, Constants.PRIVATE_KEY, true);
    }

    public static String encryptStr(String data) {
        String afterEncrypt = "";
        try {
            if (null == mFileEncryptionManager) {
                initKey();
            }
            byte[] result = mFileEncryptionManager.encryptByPublicKey(data.getBytes());
            afterEncrypt = Base64Utils.encode(result);
            afterEncrypt = afterEncrypt.replaceAll("\r|\n*","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return afterEncrypt;
    }

    public static String decodeStr(String data) {
        String afterDecode = "";
        try {
            if (null == mFileEncryptionManager) {
                initKey();
            }
            byte[] decodeData = Base64Utils.decode(data);
            byte[] result = mFileEncryptionManager.decryptByPrivateKey(decodeData);
            afterDecode = new String(result);
            afterDecode = afterDecode.replaceAll("\r|\n*","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return afterDecode;
    }

}
