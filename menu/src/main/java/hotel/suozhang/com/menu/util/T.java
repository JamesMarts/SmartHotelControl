package hotel.suozhang.com.menu.util;

import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

import org.xutils.x;

/**
 * Toast统一管理类
 */
public class T {

    private T() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static TastyToast mToast;

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(String message) {
        showShort(x.app(), message);
    }




    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(String message) {
        showLong(x.app(), message);

    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(int message) {
        showLong(x.app(), message);

    }



    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(int message, final int duration) {
        show(x.app(), message, duration);
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(final Context context, final String message) {
        x.task().autoPost(new Runnable() {
            @Override
            public void run() {
                TastyToast.makeText(context,message, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

            }
        });
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(final Context context, final String message) {
        x.task().autoPost(new Runnable() {
            @Override
            public void run() {
                TastyToast.makeText(context,message, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            }
        });
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(final Context context, final int message) {
        x.task().autoPost(new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                TastyToast.makeText(context,context.getString(message), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            }
        });
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(final Context context, final String message, final int duration) {
        x.task().autoPost(new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(context, message, duration).show();
                TastyToast.makeText(context,message, duration, TastyToast.SUCCESS);
            }
        });
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(final Context context, final int message, final int duration) {
        x.task().autoPost(new Runnable() {
            @Override
            public void run() {
                TastyToast.makeText(context,context.getString(message), duration, TastyToast.SUCCESS);
            }
        });
    }


}