package comeinsteinford.github.broadcastbestpractice.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import comeinsteinford.github.broadcastbestpractice.R;
import comeinsteinford.github.broadcastbestpractice.Utils.DatabaseUtils;

/**
 * Created by KK on 2016-08-10.
 */
public class MainActivity extends BaseActivity {

    public static final String FORCE_OFFLINE = "comeinsteinford.github.broadcastbestpractice.FORCE_OFFLINE";
//    private ForceOfflineReceiver mForceOfflineReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button forceOffline = (Button) findViewById(R.id.force_offline);

        if (forceOffline != null) {
            forceOffline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    String account = pref.getString("account", "");
                    DatabaseUtils.updateLogState(account, false, MainActivity.this);
                    //将数据库内的登录状态修改为false
                    Intent intent = new Intent(FORCE_OFFLINE);
                    sendBroadcast(intent);
                }
            });
        }
    }
}
