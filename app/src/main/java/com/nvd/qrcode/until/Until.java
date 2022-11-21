package com.nvd.qrcode.until;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Objects;

public class Until {


    public static void createQrCode(Context context, String textQr, Bitmap bitmap, ImageView image){
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(textQr, BarcodeFormat.QR_CODE, 350, 350);
            BarcodeEncoder encoder = new BarcodeEncoder();

            // init bitmap
            bitmap = encoder.createBitmap(matrix);
            image.setImageBitmap(bitmap);

        } catch (WriterException e){
            e.printStackTrace();
        }

    }

    public static void saved(ImageView img, Context context){
        Uri images;
        ContentResolver contentResolver = context.getContentResolver();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "images/*");
        Uri uri  = contentResolver.insert(images, contentValues);

        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable(); // img là cái imageView
            Bitmap bitmap = bitmapDrawable.getBitmap();
            OutputStream outputStream = contentResolver.openOutputStream(Objects.requireNonNull(uri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream );
            Objects.requireNonNull(outputStream);
            Toast.makeText(context, "saved", Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(context, " error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public static void shareImage(ImageView img, Context context){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Date now = new Date();
        String fileName = (now.getTime() / 1000) + ".png";
        try {
            File file = new File(context.getExternalCacheDir(), File.separator + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = FileProvider.getUriForFile(context, "com.nvd.qrcode", file);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
            context.startActivity(Intent.createChooser(intent, "Share image via: "));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
