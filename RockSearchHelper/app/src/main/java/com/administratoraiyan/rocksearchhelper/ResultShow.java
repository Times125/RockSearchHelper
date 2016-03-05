package com.administratoraiyan.rocksearchhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.w3c.dom.Document;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by 李春辉 on 2016/2/18.
 */
public class ResultShow extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.result_show);

        Intent intent = getIntent();
        String FilePath = intent.getStringExtra("path");//Environment.getExternalStorageDirectory() + "/RockSearchHelper/";
        String FileName = intent.getStringExtra("name");//filechoose
        String docName = FileName + ".doc";//filechoose.doc

        // Toast.makeText(this,FilePath + FileName,Toast.LENGTH_SHORT).show();

        try {
            if(!(new File(FilePath + FileName).exists()))
                new File(FilePath + FileName).mkdirs();
            transformToHtml(FilePath + docName, FilePath + FileName + ".html",FileName,FilePath);
        }catch (Exception e) {
            e.printStackTrace();
        }
        WebView webView = (WebView)findViewById(R.id.office);
        WebSettings webSettings = webView.getSettings();
        //使页面自适应屏幕大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //使页面支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webView.loadUrl("file://" + FilePath + FileName + ".html");
    }

    /**
     * word文档转换为html文件
     */
    public void transformToHtml (String FileName1,String OutputFile, final String filename,String FilePath)
            throws TransformerException, IOException,ParserConfigurationException {

        HWPFDocument hwpfDocument = new HWPFDocument(new FileInputStream(FileName1));
        WordToHtmlConverter wordToHtmlConverter =
                new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //设置图片保存的目录
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            @Override
            public String savePicture(byte[] content, PictureType pictureType, String s, float v, float v1) {
                String name = filename;
                return name + "/" + s;
            }
        });
        //保存图片
        List<Picture> pictures = hwpfDocument.getPicturesTable().getAllPictures();
        if(pictures != null) {
            for(int i=0;i < pictures.size();i++) {
                Picture picture = (Picture)pictures.get(i);
                System.out.println( picture.suggestFullFileName());
                try {
                    String name = filename;
                    picture.writeImageContent(new FileOutputStream(FilePath + name + "/"
                            + picture.suggestFullFileName()));
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        wordToHtmlConverter.processDocument(hwpfDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        out.close();
        //保存HTML文件
        writeFile(new String(out.toByteArray()), OutputFile);
    }
    public void writeFile(String context,String path ) {

        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(path);
            if(!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream,"utf-8"));
            bufferedWriter.write(context);
        }  catch (FileNotFoundException FNF){
            FNF.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufferedWriter != null)
                    bufferedWriter.close();
                if(fileOutputStream != null)
                    fileOutputStream.close();
            }catch (IOException e) {
            }
        }
    }
}
