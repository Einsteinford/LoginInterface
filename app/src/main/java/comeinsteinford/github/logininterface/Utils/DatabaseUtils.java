package comeinsteinford.github.logininterface.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import comeinsteinford.github.logininterface.UserSQLiteHelper;

/**
 * Created by KK on 2016-08-16.
 */
public class DatabaseUtils {
    public static boolean getLoginState(Context context) {
        //一个可用于判断登录状态的静态方法
        SQLiteDatabase db = UserSQLiteHelper.getDatabaseInstance(context);
        Cursor cursor = db.query("userInfo", new String[]{"loginState"}
                , "loginState = ?", new String[]{"true"}
                , null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public static String getPassword(String Username, Context context) {
        //一个可用于返回密码的静态方法
        SQLiteDatabase db = UserSQLiteHelper.getDatabaseInstance(context);
        Cursor cursor = db.query("userInfo", new String[]{"username", "password"}
                , "username = ?", new String[]{Username}
                , null, null, null);
        if (cursor.moveToFirst()) {
            String password = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            return password;
        } else {
            cursor.close();
            //其实按逻辑是不会出现此种else结果
            return "";
        }
    }

    public static void insertUserInfo(String Username, String Password, Context context) {
        //一个可用于添加用户数据的静态方法
        SQLiteDatabase db = UserSQLiteHelper.getDatabaseInstance(context);
        Cursor cursor = db.query("userInfo", new String[]{"username"}
                , "username = ?", new String[]{Username}
                , null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            Toast.makeText(context, "用户名已存在", Toast.LENGTH_SHORT).show();
        } else {
            cursor.close();
            if (!Password.equals("")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("username", Username);
                contentValues.put("password", Password);
                contentValues.put("loginState", "false");    //默认注册后登录状态为false
                db.insert("userInfo", null, contentValues);
                Toast.makeText(context, "注册成功！欢迎您：" + Username, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "请输入初始密码", Toast.LENGTH_SHORT).show();
            }

            //其实按逻辑是不会出现此种else结果
        }
    }

    public static void updateLogState(String Username, boolean isLogin, Context context) {
        //一个可用于修改用户登录状态的静态方法
        SQLiteDatabase db = UserSQLiteHelper.getDatabaseInstance(context);
        ContentValues contentValues = new ContentValues();
        String logTime = DateUtils.getCurrentDate();
        if (isLogin) {
            contentValues.put("loginState", "true");
            contentValues.put("log_time",logTime);
        } else {
            contentValues.put("loginState", "false");
            contentValues.put("log_time",logTime);
        }
        db.update("userInfo", contentValues, "username = ?", new String[]{Username});
    }

    public static boolean Login(String Username, String Password, Context context) {
        //一个可用于判断能否登录的静态方法
        SQLiteDatabase db = UserSQLiteHelper.getDatabaseInstance(context);
        Cursor cursor = db.query("userInfo", new String[]{"username", "password"}
                , "username = ?", new String[]{Username}
                , null, null, null);
        if (cursor.moveToFirst()) {
            String password = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            if (Password.equals(password)) {
                return true;
            } else {
                Toast.makeText(context, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            cursor.close();
            Toast.makeText(context, "用户名不存在", Toast.LENGTH_SHORT).show();
            return false;
            //其实按逻辑是不会出现此种else结果
        }
    }
}
