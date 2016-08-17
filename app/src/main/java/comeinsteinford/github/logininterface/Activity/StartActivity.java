package comeinsteinford.github.logininterface.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import comeinsteinford.github.logininterface.R;
import comeinsteinford.github.logininterface.Utils.DatabaseUtils;

/**
 * Created by KK on 2016-08-15.
 */
public class StartActivity extends Activity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);      //隐藏标题栏,需要继承自Activity
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);    //调用全屏显示
        setContentView(R.layout.activity_start);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = DatabaseUtils.getLoginState(StartActivity.this);
                if (isLogin) {
                    intent = new Intent(StartActivity.this, MainActivity.class);
                    //如果登录中，跳转到主界面
                } else {
                    intent = new Intent(StartActivity.this, LoginActivity.class);
                    //如果没有登录中，则跳转到登录注册画面
                }
                startActivity(intent);
                finish();
                //关闭自己
            }
        }, 2000);
    }
}
