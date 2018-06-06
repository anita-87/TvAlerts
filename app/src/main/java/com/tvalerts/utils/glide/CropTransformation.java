package com.tvalerts.utils.glide;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Custom implementation for the BitmapTransformation for Glide.
 * It allows to crop images in three ways: TOP Crop, CENTER Crop and BOTTOM Crop.
 */
public class CropTransformation extends BitmapTransformation {
    /**
     * Parameter that represents the version of the transformation.
     */
    private static final int VERSION = 1;
    /**
     * Parameter that represents the identifier of the transformation.
     */
    private static final String ID = CropTransformation.class.getPackage().toString()
            + "." + VERSION;

    /**
     * Enumeration that represents the Cropping types.
     */
    public enum CropType {
        TOP,
        CENTER,
        BOTTOM
    }

    /**
     * The width of the image.
     */
    private int width;
    /**
     * The height of the image.
     */
    private int height;

    /**
     * The crop type to be applied. Default type is Center crop.
     */
    private CropType cropType = CropType.CENTER;

    /**
     * Public constructor for the CropTransformation class.
     * @param width - the width of the image.
     * @param height - the heigh of the image.
     */
    public CropTransformation(int width, int height) {
        this(width, height, CropType.CENTER);
    }

    /**
     * Public constructor for the CropTransformation class.
     * @param width - the width of the image.
     * @param height - the heigh of the image.
     * @param cropType - the type of crop to be apply.
     */
    public CropTransformation(int width, int height, CropType cropType) {
        this.width = width;
        this.height = height;
        this.cropType = cropType;
    }

    /**
     * Transforms the given {@link android.graphics.Bitmap} based on the given dimensions and returns
     * the transformed result.
     *
     *
     * @param pool        A {@link com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool} that can
     *                    be used to obtain and return intermediate {@link Bitmap}s used in this
     *                    transformation. For every {@link android.graphics.Bitmap} obtained from the
     *                    pool during this transformation, a {@link android.graphics.Bitmap} must also
     *                    be returned.
     * @param toTransform The {@link android.graphics.Bitmap} to transform.
     * @param outWidth    The ideal width of the transformed bitmap (the transformed width does not
     *                    need to match exactly).
     * @param outHeight   The ideal height of the transformed bitmap (the transformed height does not
     *                    need to match exactly).
     */
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform,
                               int outWidth, int outHeight) {
        width = width == 0 ? toTransform.getWidth() : width;
        height = height == 0 ? toTransform.getHeight() : height;
        Bitmap.Config config =
                toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = pool.get(width, height, config);

        bitmap.setHasAlpha(true);

        float scaleX = (float) width / toTransform.getWidth();
        float scaleY = (float) height / toTransform.getHeight();
        float scale = Math.max(scaleX, scaleY);

        float scaledWidth = scale * toTransform.getWidth();
        float scaledHeight = scale * toTransform.getHeight();
        float left = (width - scaledWidth) / 2;
        float top = getTop(scaledHeight);
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(toTransform, null, targetRect, null);

        return bitmap;
    }

    /**
     * Adds all uniquely identifying information to the given digest.
     * @param messageDigest - a cryptographic hash function
     *                      which can calculate a message digest from binary data.
     */
    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update((ID + width + height + cropType).getBytes(CHARSET));
    }

    /**
     * Returns a string representation of the Crop transformation.
     * @return the string representation of the Crop transformation.
     */
    @Override public String toString() {
        return "CropTransformation(width=" + width + ", height=" + height
                + ", cropType=" + cropType + ")";
    }

    /**
     * Compares two Crop transformations to determine if they are equal.
     * @param object - the object to compare.
     * @return true if the transformations are equal, false otherwise.
     */
    @Override public boolean equals(Object object) {
        return object instanceof CropTransformation &&
                ((CropTransformation) object).width == width &&
                ((CropTransformation) object).height == height &&
                ((CropTransformation) object).cropType == cropType;
    }

    /**
     * Digests the data stored in an instance of the class into a single hash value.
     * @return the digested value.
     */
    @Override public int hashCode() {
        return ID.hashCode() + width * 100000 + height * 1000 + cropType.ordinal() * 10;
    }

    /**
     * Returns the Top position given the scaled height.
     * @param scaledHeight - the scaled height used to calculate the top position.
     * @return the top position calculated depending on the scaled height and the crop type.
     */
    private float getTop(float scaledHeight) {
        switch (cropType) {
            case TOP:
                return 0;
            case CENTER:
                return (height - scaledHeight) / 2;
            case BOTTOM:
                return height - scaledHeight;
            default:
                return 0;
        }
    }
}
