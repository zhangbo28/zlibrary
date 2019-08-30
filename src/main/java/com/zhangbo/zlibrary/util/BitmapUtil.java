package com.zhangbo.zlibrary.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhangbo on 2017/7/11.
 */

public class BitmapUtil {
        private LruCache<String, Bitmap> mCache = null;
        private static BitmapUtil imgUtil = null;

        private BitmapUtil() {
            mCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 8)) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        public static BitmapUtil getInstance() {
            if (imgUtil == null) {
                synchronized (BitmapUtil.class) {
                    if (null == imgUtil) {
                        imgUtil = new BitmapUtil();
                    }
                }
            }
            return imgUtil;
        }

        public Bitmap put(String key, Bitmap value) {
            return mCache.put(key, value);
        }

        public Bitmap get(String key) {
            return mCache.get(key);
        }



    /**
     * 目前使用的图片压缩方案：
     * 现将图片压缩至指定宽高(取计算比例较大值)
     * 再进行质量压缩至70%转成字节码 直接上传至服务器
     * @return
     */
    public static byte[] compress2byte(String srcPath, int tarWid, int tarHei){
        Bitmap bitmap = compressImage(srcPath,tarWid,tarHei);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);

        return baos.toByteArray();
    }


         /** 质量压缩
         * 将图片压缩至指定容量以下
         * @param maxSize 单位kb
         */
        public static byte[] compressImage(String path, int maxSize) {
            Bitmap imgBm = getBitmap(path);
            if (null == imgBm) {
                return null;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imgBm.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > maxSize && options > 10) { // 循环判断如果压缩后图片是否大于指定值,大于继续压缩并且保证optuins大于10
                baos.reset();// 重置baos即清空baos
                options -= 10;// 每次都减少10
                imgBm.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            }
            return baos.toByteArray();
        }

        /**
         * 尺寸压缩
         * @param tarWid 最终图片的wid
         * @param tarHei 最终图片的hei
         */
        public static Bitmap compressImage(String srcPath, int tarWid, int tarHei) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(srcPath,options);//此时返回bm为空

            options.inSampleSize = calculateInSampleSize1(options,tarWid,tarHei);
            options.inJustDecodeBounds = false;

            return BitmapFactory.decodeFile(srcPath, options);
        }



    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }
    public void storeImage(Bitmap bitmap, String outPath){
        FileOutputStream fos=null;
        try {
            fos = new FileOutputStream(outPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // do nothing here
                }
            }
        }
//        if(false){
//            // 把文件插入到系统图库(如果需要可打开)
//            try {
//                MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                        file.getAbsolutePath(), fileName, null);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            // 最后通知图库更新
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
//        }
    }


    /**
     * 图片压缩尺寸计算
     * 算法一：图片长与目标长比，图片宽与目标宽比，取最大值（不过也有人取最小值，怕压缩的太多吗？取最小值会遇到的问题举个例子，满屏加载长截图的时候，
     * 图片宽与屏幕宽为1：1，这样inSampleSize就为1，没有压缩那么很容易就内存溢出了），不管怎么都欠妥；因为如果手机是横屏拍摄，或者是拍摄的全景图，
     * 那么图片宽与目标宽的比例会很增大，这样压缩的比例会偏大。
     */
    public static int calculateInSampleSize1(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            //计算图片高度和我们需要高度的最接近比例值
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            //宽度比例值
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            //取比例值中的较大值作为inSampleSize
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * 图片压缩尺寸计算
     * 算法二：取目标长宽的最大值来计算，这样会减少过度的尺寸压缩
     */
    public static int calculateInSampleSize2(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            //使用需要的宽高的最大值来计算比率
            final int suitedValue = reqHeight > reqWidth ? reqHeight : reqWidth;
            final int heightRatio = Math.round((float) height / (float) suitedValue);
            final int widthRatio = Math.round((float) width / (float) suitedValue);

            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;//用最大
        }

        return inSampleSize;
    }

//    /**
//     * 根据最终图片尺寸，计算压缩比例
//     *
//     */
//    public static int calculateInSampleSize(BitmapFactory.Options options, int tarWid, int tarHei) {
//        int height = options.outHeight;
//        int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > tarHei || width > tarWid) {
//            int halfHeight = height / 2;
//            int halfWidth = width / 2;
//            // 在保证解析出的bitmap宽高分别大于目标尺寸宽高的前提下，取可能的inSampleSize的最大值
//            while ((halfHeight / inSampleSize) > tarHei
//                    && (halfWidth / inSampleSize) > tarWid) {
//                inSampleSize *= 2;
//            }
//        }
//
//        return inSampleSize;
//    }
}
