package net.dereva.taxi.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageHelper {


    public static void saveBitmapInCache(Context context, Bitmap b, String picName) {
        File cache = context.getCacheDir();
        File file = new File(cache, picName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadBitmapFromCache(Context context, String picName) {
        File cache = context.getCacheDir();
        File file = new File(cache, picName);
        FileInputStream fis;
        Bitmap b = null;

        try {
            fis = new FileInputStream(file);
            b = BitmapFactory.decodeStream(fis);
            fis.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    public static void deletePictureFromCache(Context context, String picName) {
        File cache = context.getCacheDir();
        File file = new File(cache, picName);
        file.delete();

    }

}