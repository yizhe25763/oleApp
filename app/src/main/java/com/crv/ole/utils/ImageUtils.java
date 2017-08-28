package com.crv.ole.utils;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.crv.ole.BuildConfig;

/**
 * Created by Administrator on 2017/1/5.
 */

public class ImageUtils {
    @NonNull
    public static String getImageUrl(String image) {
        if (TextUtils.isEmpty(image)) {
            return "";
        } else if (!image.startsWith("http")) {
            return BuildConfig.APIHOST + image.replace(BuildConfig.APIHOST, "");
        } else {
            return image;
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public static String getPathFromUri(Context context, Uri uri) {
        // just some safety built in
        if (uri == null) {
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            cursor.close();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }
}
