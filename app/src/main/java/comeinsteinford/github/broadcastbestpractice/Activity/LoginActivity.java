package comeinsteinford.github.broadcastbestpractice.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import comeinsteinford.github.broadcastbestpractice.R;
import comeinsteinford.github.broadcastbestpractice.Utils.DatabaseUtils;

/**
 * Created by KK on 2016-08-10.
 */
public class LoginActivity extends BaseActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private Button register;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        //实例化SP

        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        boolean isRemember = pref.getBoolean("remember_pass", false);    //读取文件中的布尔值，默认为false
        //此处依然由SP保存
        if (isRemember) {
            String account = pref.getString("account", "");
            String password = DatabaseUtils.getPassword(account, LoginActivity.this);
            //从数据库获取密码

            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        } else {
            String account = pref.getString("account", "");
            accountEdit.setText(account);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                //如果账号和密码与数据库匹配，就登陆成功
                if (DatabaseUtils.Login(account, password, LoginActivity.this)) {
                    editor = pref.edit();   //实例化,准备对SP进行编辑
                    //得记住用户名,让下一次登录显示它
                    editor.putString("account", account);
                    DatabaseUtils.updateLogState(account, true, LoginActivity.this);
                    //设置登录状态为true
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("remember_pass", true);
                    }
//                    else {
//                        editor.putBoolean("remember_pass", false);
//                        //如果没勾选记住，修改为false
//                    }
                    editor.apply(); //不需要返回值

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);//跳转到登陆成功页面
                    finish();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                DatabaseUtils.insertUserInfo(account, password, LoginActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (!rememberPass.isChecked()) {
            if (editor == null) {
                editor = pref.edit();
            }
            editor.putBoolean("remember_pass", false);
            editor.apply();
        }
        super.onDestroy();
    }
}
