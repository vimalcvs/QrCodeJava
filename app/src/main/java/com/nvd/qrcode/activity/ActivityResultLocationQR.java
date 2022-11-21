package com.nvd.qrcode.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.nvd.qrcode.R;
import com.nvd.qrcode.database.Item_Qr;
import com.nvd.qrcode.database.QRDatabase;
import com.nvd.qrcode.until.Until;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class ActivityResultLocationQR extends AppCompatActivity {

    ImageView imgResultQR;
    Bitmap bitmap;
    ImageView imgShare, imgCopy, imgDownload, imgOpen;
    TextView txtLongtitude, txtLatitude ;
    String textResult, latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_location_qr);

        imgResultQR = findViewById(R.id.img_result);
        imgShare = findViewById(R.id.img_share);
        imgDownload = findViewById(R.id.img_download);
        imgCopy = findViewById(R.id.img_copy);
        imgOpen = findViewById(R.id.img_open);
        txtLongtitude = findViewById(R.id.longtitude_result);
        txtLatitude = findViewById(R.id.latitude_result);

        Intent intent = getIntent();
        if (intent != null){
            longitude = intent.getStringExtra("longitude");
            latitude = intent.getStringExtra("latitude");
            textResult = "longitude: " + longitude + ", Latitude: " + latitude;

            txtLongtitude.setText(longitude);
            txtLatitude.setText(latitude);
            Item_Qr qrCode = new Item_Qr(textResult);
            QRDatabase.getInstance(this).userDAO().insertUser(qrCode);
        }

        Until.createQrCode(getApplicationContext(), textResult, bitmap, imgResultQR);

        imgShare.setOnClickListener(v -> {

            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgResultQR.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            Date now = new Date();
            String fileName = (now.getTime() / 1000) + ".png";
            try {
                File file = new File(getExternalCacheDir(), File.separator + fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Intent intent12 = new Intent(Intent.ACTION_SEND);
                intent12.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.nvd.qrcode", file);
                intent12.putExtra(Intent.EXTRA_STREAM, uri);
                intent12.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent12.setType("image/*");
                startActivity(Intent.createChooser(intent12, "Share image via: "));
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        imgDownload.setOnClickListener(v -> Until.saved(imgResultQR, getApplicationContext()));

        imgCopy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("simple text", textResult);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(ActivityResultLocationQR.this, R.string.copied, Toast.LENGTH_SHORT).show();
        });

        imgOpen.setOnClickListener(v -> {
            Intent intent1 = new Intent();
            intent1.setData(Uri.parse("geo:"+latitude+","+longitude));
            Intent chooser = Intent.createChooser(intent1, "Launch Maps");
            startActivity(chooser);

        });
    }
}