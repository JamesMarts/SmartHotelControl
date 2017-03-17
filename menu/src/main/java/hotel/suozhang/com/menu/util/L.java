package hotel.suozhang.com.menu.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Log统一管理类，类似android.util.Log。
 * tag自动产生，格式: customTag:className.methodName(L:lineNumber),
 * customTag为空时只输出：className.methodName(L:lineNumber)。
 */
public class L {

    private static boolean isDebug = false;
    private static String customTag = "SZ_LOG";

    private L() {
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        L.isDebug = isDebug;
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTag) ? tag : customTag + ":" + tag;
        return tag;
    }

    public static void d(String tag, String content) {
        if (!isDebug()) return;

        Log.d(tag, content);
    }

    public static void d(String tag, String content, Throwable tr) {
        if (!isDebug()) return;

        Log.d(tag, content, tr);
    }

    public static void e(String tag, String content) {
        if (!isDebug()) return;

        Log.e(tag, content);
    }

    public static void e(String tag, String content, Throwable tr) {
        if (!isDebug()) return;

        Log.e(tag, content, tr);
    }

    public static void i(String tag, String content) {
        if (!isDebug()) return;

        Log.i(tag, content);
    }

    public static void i(String tag, String content, Throwable tr) {
        if (!isDebug()) return;

        Log.i(tag, content, tr);
    }

    public static void v(String tag, String content) {
        if (!isDebug()) return;

        Log.v(tag, content);
    }

    public static void v(String tag, String content, Throwable tr) {
        if (!isDebug()) return;

        Log.v(tag, content, tr);
    }

    public static void w(String tag, String content) {
        if (!isDebug()) return;

        Log.w(tag, content);
    }

    public static void w(String tag, String content, Throwable tr) {
        if (!isDebug()) return;

        Log.w(tag, content, tr);
    }

    public static void wtf(String tag, String content) {
        if (!isDebug()) return;

        Log.wtf(tag, content);
    }

    public static void wtf(String tag, String content, Throwable tr) {
        if (!isDebug()) return;

        Log.wtf(tag, content, tr);
    }


    ////////////////////////////////自动获取TAG////////////////////////////////////

    public static void d(String content) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.d(tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.d(tag, content, tr);
    }

    public static void e(String content) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.e(tag, content);
    }

    public static void e(String content, Throwable tr) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.e(tag, content, tr);
    }

    public static void i(String content) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.i(tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.i(tag, content, tr);
    }

    public static void v(String content) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.v(tag, content, tr);
    }

    public static void w(String content) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.w(tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.w(tag, content, tr);
    }

    public static void w(Throwable tr) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.w(tag, tr);
    }


    public static void wtf(String content) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, content, tr);
    }

    public static void wtf(Throwable tr) {
        if (!isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, tr);
    }
}