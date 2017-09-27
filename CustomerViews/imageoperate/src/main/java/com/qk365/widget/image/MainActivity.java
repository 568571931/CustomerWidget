package com.qk365.widget.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qk365.widget.image.utils.BitmapCompressor;
import com.qk365.widget.image.utils.FileUtil;
import com.qk365.widget.image.utils.PictureUtil;
import com.qk365.widget.image.utils.compressor.Compressor;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 10;
    private ImageView ivCompressImg;
    private TextView tvCompressImgInfo;
    private ImageView ivCompressImg1, ivCompressImg2, ivCompressImg3;
    private TextView tvCompressImgInfo1, tvCompressImgInfo2, tvCompressImgInfo3;
    private TextView tvImgInfo;
    private File actualImage;
    private File compressedImage;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivCompressImg = (ImageView) findViewById(R.id.iv_compress_img);
        tvCompressImgInfo = (TextView) findViewById(R.id.tv_compress_img_info);
        ivCompressImg1 = (ImageView) findViewById(R.id.iv_compress_img1);
        tvCompressImgInfo1 = (TextView) findViewById(R.id.tv_compress_img_info1);
        ivCompressImg2 = (ImageView) findViewById(R.id.iv_compress_img2);
        tvCompressImgInfo2 = (TextView) findViewById(R.id.tv_compress_img_info2);
        ivCompressImg3 = (ImageView) findViewById(R.id.iv_compress_img3);
        tvCompressImgInfo3 = (TextView) findViewById(R.id.tv_compress_img_info3);
        tvImgInfo = (TextView) findViewById(R.id.tv_img_info);
    }

    public void chooseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void compress1(View view) {
        // Compress image in main thread using custom Compressor
        try {
            if (actualImage == null) {
                toast("Please choose an image!");
                return;
            }
            compressedImage = new Compressor(this)
                    .setMaxWidth(540)
                    .setMaxHeight(480)
                    .setQuality(50)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(actualImage);
            Log.e("xwk", "path=" + Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES).getAbsolutePath());
            ivCompressImg.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
            tvCompressImgInfo.setText("方式一" + String.format("Size : %s", getReadableFileSize(compressedImage.length())));

        } catch (IOException e) {
            e.printStackTrace();
            tvCompressImgInfo.setText(e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void compress2(View view) {
        if (actualImage == null) {
            toast("Please choose an image!");
            return;
        }
        Bitmap bitmap = BitmapCompressor.
                decodeSampledBitmapFromFile(actualImage.getAbsolutePath(), 300, 300);
        ivCompressImg1.setImageBitmap(bitmap);
        tvCompressImgInfo1.setText("方式二" + String.format("Size : %s", getReadableFileSize(bitmap.getByteCount())));
    }

    public void compress3(View view) {
        try {
            Bitmap bitmap = null;
            if (TextUtils.isEmpty(imgPath)) {
                toast("Please choose an image!");
                tvCompressImgInfo2.setText("imgPath=" + imgPath);
                return;
            }
            bitmap = BitmapCompressor.decodeSampledBitmapFromFile(imgPath, 300, 300);
            Log.e("xwk", "bitmap is null:" + (bitmap == null));
            ivCompressImg2.setImageBitmap(bitmap);
            tvCompressImgInfo2.setText("方式三" + String.format("Size : %s", getReadableFileSize(bitmap.getByteCount())));
        } catch (Exception e) {
            e.printStackTrace();
            tvCompressImgInfo2.setText(e.getMessage());
        }
    }

    public void compress4(View view) {
        if (TextUtils.isEmpty(imgPath)) {
            toast("Please choose an image!");
            tvCompressImgInfo3.setText("imgPath=" + imgPath);
            return;
        }
        Bitmap b = PictureUtil.getSmallBitmap(imgPath, 80, 120);
//        Bitmap bitmap = BitmapCompressor.decodeSampledBitmapFromFile(imgPath, 300, 300);
//        bitmap = BitmapCompressor.compressBitmap(bitmap, 75);
        ivCompressImg3.setImageBitmap(b);
        tvCompressImgInfo3.setText("方式四" + String.format("Size : %s", getReadableFileSize(b.getByteCount())));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                toast("Failed to open picture!");
                return;
            }
            try {
                actualImage = FileUtil.from(this, data.getData());
                imgPath =actualImage.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (actualImage == null) {
                toast("Please choose an image!");
            } else {
                tvImgInfo.setText("原图" + String.format("Size : %s", getReadableFileSize(actualImage.length())));
            }
        }
    }


    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
