package net.dereva.taxi.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PhotoHelper {

    private static final long TEN_MINUTES = 600000;


    public static void savePhotoInCache(Context context, Bitmap b, String picName) {
        File cache = context.getCacheDir();
        File file = new File(cache, picName);

        try (FileOutputStream fos = new FileOutputStream(file)){
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadPhotoFromCache(Context context, String picName) {
        File cache = context.getCacheDir();
        File file = new File(cache, picName);
        Bitmap b = null;

        try (FileInputStream fis = new FileInputStream(file)) {
            b = BitmapFactory.decodeStream(fis);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    private static void deletePhotoFromCache(Context context, String picName) {
        File cache = context.getCacheDir();
        File file = new File(cache, picName);
        file.delete();

    }

    public static void startTimerToDeletePhotoFromCache(Context context, String picName) {
        CleanCacheTask cleanCacheTask = new CleanCacheTask(context, picName);
        Timer timer = new Timer();
        timer.schedule(cleanCacheTask, TEN_MINUTES);

    }

    private static class CleanCacheTask extends TimerTask {
        private Context context;
        private String picName;

        CleanCacheTask(Context context, String picName){
            this.context = context;
            this.picName = picName;
        }

        @Override
        public void run() {
            PhotoHelper.deletePhotoFromCache(context, picName);
        }

    }

}