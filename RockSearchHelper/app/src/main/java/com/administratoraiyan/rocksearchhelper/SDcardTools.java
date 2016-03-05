package com.administratoraiyan.rocksearchhelper;

import android.os.Environment;
import java.io.File;

/**
 * Created by 李春辉 on 2016/2/23.
 */
/*****************************************************************************************
 * SD卡操作
 *************************************************************************************/
public class SDcardTools {

    public static void creatFile() {//创建程序所需文件目录
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))  {
            File sdcardDir = Environment.getExternalStorageDirectory();
            String path = sdcardDir.getPath() + "/RockSearchHelper";
            File path1 = new File(path);

            if(!path1.exists())  {
                path1.mkdirs();
            }
        }else {
            return;
        }
    }
}
