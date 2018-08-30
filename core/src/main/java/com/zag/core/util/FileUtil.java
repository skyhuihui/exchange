package com.zag.core.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: web3j-zag
 * @Package: com.zag.core.util
 * @ClassName: ${FileUtil}
 * @Description: 文件处理
 * @Author: skyhuihui
 * @CreateDate: 2018/8/24 11:16
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/8/24 11:16
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class FileUtil {

    /**
     * 获取本地文件内容
     * @param fileName
     * @return String
     * */
    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = null;
            try {
                in = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            in.read(filecontent);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (Exception e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 数据写入本地文件
     * @param url
     * @param content
     * @return void
     * */
    public static void saveFile(String url , String content )
    {
        try{
            File file =new File(url);
            //if file doesnt exists, then create it
            if(!file.exists()){
                file.createNewFile();
            }
            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(),true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(content);
            bufferWritter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取本地文件内容 并返回数组
     * @param fileName
     * @return String
     * */
    public static List readToList(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> strings = new ArrayList<>();
        String str = null;

        while ((str = bufferedReader.readLine()) != null) {
            if (str.trim().length() > 2) {
                strings.add(str);
            }
        }
        return strings;
    }
}
