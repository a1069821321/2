package com.server.modules;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class Properate {
    public static void main(String[] args) {
        //
        write("connectset_server","","","a.properties");
        write("connectset_port","","","a.properties");
        readAll("a.properties");
    }
    public static void write(String key, String property, String comment, String path){
        try{
            Properties prop = new Properties();
            ///保存属性到b.properties文件
            FileOutputStream oFile = new FileOutputStream(path, true);//true表示追加打开
            prop.setProperty(key, property);
            prop.store(oFile, comment);
            oFile.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String read(String key, String path){
        String result;
        try {
            Properties prop = new Properties();
            InputStream in = new BufferedInputStream(new FileInputStream(path));
            prop.load(in);
            result =prop.getProperty(key);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "IsError";
    }

    public static void readAll(String path){
        try {
            Properties prop = new Properties();
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream(new FileInputStream(path));
            prop.load(in);     ///加载属性列表
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String key = it.next();
                System.out.println(key + ":" + prop.getProperty(key));
            }
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}