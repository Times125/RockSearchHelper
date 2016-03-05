package com.administratoraiyan.rocksearchhelper;

import android.content.Context;

import java.io.File;

/**
 * Created by 李春辉 on 2016/2/24.
 */
public class SearchFile {
/***
 public static void searchFile(String filename, String path) {
 File[] files = null;
 try {
 files = new File(path).listFiles();
 } catch (Exception e) {
 files = null;
 return;
 }

 for (File file : files) {
 if (file.isDirectory() && file.listFiles() != null) {
 searchFile(filename, file.getPath());
 } else if (file.isFile()) {
 if (filename == null || filename.isEmpty()) {
 MainActivity.search_result.add(file.getPath());
 } else {
 String name = file.getName();
 if (name.indexOf(filename) != -1) {
 MainActivity. search_result.add(file.getPath());
 }
 }
 }
 }
 }
 *****/

    /****************************************************************************
     * 文件搜索
     * @param filename
     * @param path
     **************************************************************************/
    public static void searchFile(Context context,String filename,String path)  {


        File[] files = null;
        try {
            files = new File(path).listFiles();
        }catch (Exception e) {
            files = null;
            return;
        }
        /**
         * File f =new File("TileTest.java");
         String fileName=f.getName();
         String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
         System.out.println(prefix);
         */

        for (File file : files) {
            if(file.isDirectory() && file.listFiles() != null) {
                searchFile(context,filename, file.getPath());
            }else if (file.isFile()) {
                if(filename == null || filename.isEmpty()) {
                    String fileName=file.getName();
                    String prefix1=fileName.substring(fileName.lastIndexOf(".")+1);
                    if(prefix1.equals("doc")) {
                        String prefix=fileName.substring(0,fileName.indexOf("."));
                        MainActivity.search_result.add(prefix);//listview显示搜索结果
                    }

                }
                else {
                    String name = file.getName();
                    if(name.indexOf(filename) != -1) {
                        String fileName=file.getName();
                        String prefix1=fileName.substring(fileName.lastIndexOf(".") + 1);
                        if (prefix1.equals("doc")) {
                            String prefix = fileName.substring(0, fileName.indexOf("."));
                            MainActivity.search_result.add(prefix);//listview显示搜索结果
                        }
                    }
                }
            }
        }
    }
}


