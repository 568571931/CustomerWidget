package com.qk365.widget.image;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qk365.widget.image.utils.FileUtil;
import com.qk365.widget.image.utils.compressor.Compressor;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompressActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 10;
    private static final int REQUEST_CODE = 100;
    private static final int CODE_TAKE_PHOTO = 20;
    private static final int TYPE_TAKE_PHOTO = 21;
    //    private ImageView ivCompressImg;
//    private TextView tvCompressImgInfo;
    private TextView tvImgInfo;
    private File actualImage;
    private File compressedImage;
    private ImageView ivCompressImg;
    private TextView tvCompressImgInfo;
    private Uri photoUri;
    private String takePhotoFileName = "imgTest";
    private String photoCompressFileName = "compress";
    private File imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress);
        ivCompressImg = (ImageView) findViewById(R.id.iv_compress_img);
        tvCompressImgInfo = (TextView) findViewById(R.id.tv_compress_img_info);
        tvImgInfo = (TextView) findViewById(R.id.tv_img_info);
        imgFile = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        String str = "/storage/emulated/0/Pictures/IMG_20170923_130639.jpg";
        File file = new File(str);
        Picasso.with(this)
//                .load("file:///storage/emulated/0/Pictures/IMG_20170923_130639.jpg")
                .load(file)
                .error(R.mipmap.ic_launcher)
                .into(ivCompressImg);

    }

    private void chooseAndCompress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            compress1();
        }
    }

    /**
     * 选择图库图片
     */
    public void chooseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /**
     * 拍照
     */
    public void takePhoto(View view) {

        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= 24) {
            photoUri = get24MediaFileUri(TYPE_TAKE_PHOTO);
        } else {
            photoUri = getMediaFileUri(TYPE_TAKE_PHOTO);
        }
        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(takeIntent, CODE_TAKE_PHOTO);

    }


    private Uri getMediaFileUri(int type) {
        File mediaStorageDir = new File(imgFile, takePhotoFileName);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == TYPE_TAKE_PHOTO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return Uri.fromFile(mediaFile);
    }

    /**
     * 版本24以上
     */
    private Uri get24MediaFileUri(int type) {
        File mediaStorageDir = new File(imgFile, takePhotoFileName);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == TYPE_TAKE_PHOTO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", mediaFile);
    }

    private void compress1() {
        // Compress image in main thread using custom Compressor
        try {
            compressedImage = new Compressor(this)
                    .setMaxWidth(540)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(imgFile.getAbsolutePath())
                    .compressToFile(actualImage);
            Log.e("xwk", "compressedImage path=" + compressedImage.getAbsolutePath());
            ivCompressImg.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
            tvCompressImgInfo.setText("压缩结果" + String.format("Size : %s", getReadableFileSize(compressedImage.length())));
        } catch (IOException e) {
            e.printStackTrace();
            tvCompressImgInfo.setText(e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE_REQUEST:
                    if (data == null) {
                        toast("Failed to open picture!");
                        return;
                    }
                    try {
                        actualImage = FileUtil.from(this, data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (actualImage == null) {
                        toast("Please choose an image!");
                    } else {
                        tvImgInfo.setText("原图" + String.format("Size : %s", getReadableFileSize(actualImage.length())));
                        chooseAndCompress();
                    }
                    break;
                case CODE_TAKE_PHOTO:
                    if (data != null) {
                        if (data.hasExtra("data")) {
                            Log.i("URI", "data is not null");
                        }
                    } else {
                        Log.i("URI", "Data is null");
//                        if (Build.VERSION.SDK_INT >= 24) {
//                            Bitmap bitmap = null;
//                            try {
//                                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoUri));
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            }
//                            imageView.setImageBitmap(bitmap);
//                        } else {
//                            Bitmap bitmap = BitmapFactory.decodeFile(photoUri.getPath());
//                        }
                        try {
                            actualImage = FileUtil.from(this, photoUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (actualImage == null) {
                            toast("Please choose an image!");
                        } else {
                            tvImgInfo.setText("原图" + String.format("Size : %s", getReadableFileSize(actualImage.length())));
                            Log.e("xwk", "onActivityResult: path:"+photoUri.getPath() );

                            chooseAndCompress();
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            compress1();
        } else {
            toast("请允许存储权限");
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
