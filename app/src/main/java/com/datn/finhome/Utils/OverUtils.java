package com.datn.finhome.Utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class OverUtils {
    public static int REQUEST_CODE_LOGIN_WITH_GOOGLE = 99;
    public static int CHECK_TYPE_PROVIDER_LOGIN = 0;
    public static int CODE_PROVIDER_LOGIN_WITH_GOOGLE = 1;
    private static SharedPreferences sharedPreferences;
    public static final String SHARE_UID = "currentUserId";
    public static final String PREFS_DATA_NAME = "currentUserId";
    public static final String ERROR_MESSAGE_LOGIN = "Tên đăng nhập hoặc mật khẩu không đúng";
    public static final String LOGIN_successfully = "Đăng nhập thành công";
    public static final String ERROR_EMAIL = "Vui lòng nhập email";
    public static final String ERROR_PASS = "Vui lòng nhập mật khẩu";
    public static final String CHECK_PASS = "Mật khẩu không khớp vui lòng nhập lại!";
    public static final String ERROR_SIGNIN = "Đăng ký thất bại";

    public static void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static SharedPreferences getSPInstance(Context context, String nameFile) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(nameFile, MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}
