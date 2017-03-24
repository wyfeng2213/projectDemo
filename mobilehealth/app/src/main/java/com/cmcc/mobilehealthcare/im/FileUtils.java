package com.cmcc.mobilehealthcare.im;

import android.content.Context;
import android.text.format.Time;

import java.io.File;
import java.util.Locale;

public class FileUtils {

    public static String getDataDir(Context context) {
        File fileDir = context.getExternalFilesDir(null);
        String dir = null;
        if (fileDir != null) {
            dir = fileDir.getAbsolutePath();
        } else {
            dir = context.getFilesDir().getAbsolutePath();
        }
        dir += "/mtc/data/";
        fileDir = new File(dir);
        fileDir.mkdirs();
        return dir;
    }

    public static String getSendDir(Context context) {
        File fileDir = context.getExternalFilesDir(null);
        String dir = null;
        if (fileDir != null) {
            dir = fileDir.getAbsolutePath();
        } else {
            dir = context.getFilesDir().getAbsolutePath();
        }
        dir += "/mtc/send/";
        fileDir = new File(dir);
        fileDir.mkdirs();
        return dir;
    }
    
    public static String getRecvDir(Context context) {
        File fileDir = context.getExternalFilesDir(null);
        String dir = null;
        if (fileDir != null) {
            dir = fileDir.getAbsolutePath();
        } else {
            dir = context.getFilesDir().getAbsolutePath();
        }
        dir += "/mtc/recv/";
        fileDir = new File(dir);
        fileDir.mkdirs();
        return dir;
    }
    
    public static String getSnapshotPath(Context context) {
        return getRecvDir(context) + getCurrentTimeString() + ".jpeg";
    }
    
    public static String getAudioPath(Context context) {
        return getDataDir(context) + getCurrentTimeString() + ".wav";
    }
    
    public static String getVideoPath(Context context) {
        return getDataDir(context) + getCurrentTimeString() + ".avi";
    }

    private static String getCurrentTimeString() {
        Time time = new Time();
        time.set(System.currentTimeMillis());
        return String.format(Locale.getDefault(),
                "%04d-%02d-%02d-%02d-%02d-%02d", time.year, time.month,
                time.monthDay, time.hour, time.minute, time.second);
    }
}
