package com.czh.snail.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chenzhihui on 16/8/30.
 */

public class FileUtils {

    /**
     * * 复制单个文件
     *
     * @param newPath String 原文件路径
     * @param newPath String 复制后路径
     * @return boolean
     */
    public static boolean copyFile(String oldPathAndName,String newPath,String name) throws IOException {
        int bytesum = 0;
        int byteread = 0;
        File oldfile = new File(oldPathAndName);
        File cachedir = new File(newPath);
        File newPathAndName = new File(newPath + name);
        if(!cachedir.exists()){
            cachedir.mkdir();
        }
        newPathAndName.createNewFile();
        if (oldfile.exists()) {                  //文件存在时
            InputStream inStream = new FileInputStream(oldPathAndName);      //读入原文件
            FileOutputStream fs = new FileOutputStream(newPath+File.separator+name);
            byte[] buffer = new byte[1444];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;            //字节数 文件大小
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();
        }
        return true;
    }


}
