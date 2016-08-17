package comeinsteinford.github.logininterface;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

import comeinsteinford.github.logininterface.Activity.LoginActivity;

/**
 * Created by KK on 2016-08-10.
 */

public class ForceOfflineReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("提示")
                .setMessage("您已退出登录，将返回首页")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                        Intent dialogIntent = new Intent(context, LoginActivity.class);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(dialogIntent);
                    }
                });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        try {
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
