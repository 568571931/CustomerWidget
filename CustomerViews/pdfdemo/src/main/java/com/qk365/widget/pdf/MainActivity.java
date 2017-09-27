package com.qk365.widget.pdf;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {


    private PDFView pdfView;
    private int pageNumber;
    //    private String url = "http://file.chmsp.com.cn/colligate/file/00100000224821.pdf";
    private String url = "http://116.228.53.202/vipapp_data/pdf_files/Agreement/agreement/201612/%E8%83%A1%E6%B3%BD%E5%89%91_LXL-3489-20-602-A-3SY201703111520/%E5%80%9F%E6%AC%BE%E5%90%88%E5%90%8C.pdf";
    //    private String url = "http://123.249.94.248:8080/xwk/pdf2.pdf";
    private TextView tvPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        tvPage = (TextView) findViewById(R.id.tv_page);
        loadPad(pdfView, url);
    }

    public void loadPad(final PDFView pdfview, final String pdfUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(pdfUrl);
                    HttpURLConnection connection = (HttpURLConnection)
                            url.openConnection();
                    connection.setRequestMethod("GET");//试过POST 可能报错
                    connection.setDoInput(true);
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    //实现连接
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        final InputStream is = connection.getInputStream();
                        //这里给过去就行了
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pdfview.fromStream(is)//从输入流中加载pdf
                                        .defaultPage(pageNumber)//默认页数
                                        .onPageChange(MainActivity.this)
                                        .enableAnnotationRendering(true)
                                        .onLoad(MainActivity.this)
                                        .scrollHandle(new DefaultScrollHandle(MainActivity.this))
                                        .spacing(10) // in dp
                                        .load();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
//        setTitle(String.format("%s %s / %setTitle", "", page + 1, pageCount));
        tvPage.setText(String.format("%s %s / %s", "", page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {

    }

    /**
     * 从assets中打开pdf
     */
    private void displayFromAsset(String assetFileName) {
        pdfView.fromAsset("test.pdf")
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .load();
    }

    /**
     * 根据Uri打开pdf，一般是用户选择本地文件时使用该方法
     */
    private void displayFromUri(Uri uri) {
        pdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .load();
    }


}
