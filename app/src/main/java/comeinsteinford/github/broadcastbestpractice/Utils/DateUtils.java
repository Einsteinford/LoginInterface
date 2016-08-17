package comeinsteinford.github.broadcastbestpractice.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by KK on 2016-08-17.
 */

public class DateUtils {

    //获取系统时间
    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒", Locale.getDefault());
        return simpleDateFormat.format(d);
    }
}
