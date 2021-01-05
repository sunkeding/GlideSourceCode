package com.example.glidesourcecode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Created by cxj on 2017/10/25.
 */

public class GlideRoundCornersTransformation extends BitmapTransformation {
    private static final String ID = "com.leoao.commonui.utils.glide.GlideRoundCornersTransformation";
    private static byte[] ID_BYTES = new byte[]{};

    static {
        try {
            ID_BYTES = ID.getBytes(STRING_CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GlideRoundCornersTransformation;
    }

    public enum CornerType {
        TOP,
        ALL,
        LEFT,
        RIGHT,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    private int radius;
    private CornerType mCornerType;
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public GlideRoundCornersTransformation(Context context) {
        super();
        this.radius = dip2px(context,3);
        this.mCornerType = CornerType.ALL;
    }


    public GlideRoundCornersTransformation(Context context, int radius, CornerType cornerType) {
        super();
        this.radius = radius;
        this.mCornerType = cornerType;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        return roundCrop(pool, bitmap);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
//        if(new DefaultImageHeaderParser().getType(source.)){
//
//        }
//        ImageHeaderParserUtils.getType(Glide.get(SdkConfig.getApplicationContext()).getRegistry().getImageHeaderParsers(),)

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        drawRoundRect(canvas, paint, source.getWidth(), source.getHeight());
        return result;
    }

    private void drawRoundRect(Canvas canvas, Paint paint, float width, float height) {
        switch (mCornerType) {
            case TOP:
                drawTopRoundRect(canvas, paint, width, height);
                break;
            case ALL:
                drawAllRoundRect(canvas, paint, width, height);
                break;
            case LEFT:
                drawLeftRoundRect(canvas, paint, width, height);
                break;
            case RIGHT:
                drawRightRoundRect(canvas, paint, width, height);
                break;
            case TOP_LEFT:
                drawTopLeftRoundRect(canvas, paint, width, height);
                break;
            case TOP_RIGHT:
                drawTopRightRoundRect(canvas, paint, width, height);
                break;
            case BOTTOM_LEFT:
                drawBottomLeftRoundRect(canvas, paint, width, height);
                break;
            case BOTTOM_RIGHT:
                drawBottomRightRoundRect(canvas, paint, width, height);
                break;
        }
    }

    private void drawTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, right, bottom), radius, radius, paint);
        canvas.drawRect(new RectF(right-radius, 0, right, bottom), paint); //遮盖右部分部分圆角
        canvas.drawRect(0, bottom - radius, radius, bottom, paint);//遮盖 下左部分右边圆角
    }

    private void drawBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, right, bottom), radius, radius, paint);
        canvas.drawRect(new RectF(right-radius, 0, right, bottom), paint);
        canvas.drawRect(0, 0, radius, radius, paint); //遮盖上左的圆角
    }

    private void drawTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, right, bottom), radius, radius, paint);
        canvas.drawRect(new RectF(0, 0, right - radius, bottom), paint); //遮盖左边的部分圆角
        canvas.drawRect(right - radius, bottom - radius, right, bottom, paint); //遮盖下右边圆角
    }

    private void drawBottomRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, right, bottom), radius, radius, paint);
        canvas.drawRect(new RectF(0, 0, right - radius, bottom), paint);
        canvas.drawRect(right - radius, 0, right, radius, paint);
    }


    private void drawAllRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, right, bottom), radius, radius, paint);
    }

    private void drawTopRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, right, bottom), radius, radius, paint);
        canvas.drawRect(new RectF(0, radius, right, bottom), paint);
    }

    private void drawLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, right, bottom), radius, radius, paint);
        canvas.drawRect(new RectF(radius, 0, right, bottom), paint);
    }

    private void drawRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, right, bottom), radius, radius, paint);
        canvas.drawRect(new RectF(0, 0, right - radius, bottom), paint);
    }

    public String getId() {
        return "com.leoao.commonui.utils.glide.GlideRoundCornersTransformation" + mCornerType + Math.round(radius);
    }
}
