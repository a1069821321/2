package com.server.modules;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

public class Server_Writer implements Callable<String> {
    private Socket client;
    private String information;
    public  Server_Writer(Socket client, String information) {
        this.client = client;
        this.information = information;
    }
    @Override
    public String call(){
        DataOutputStream dos = null;
        try{
            //System.out.println(information);
            dos = new DataOutputStream(client.getOutputStream());
            String data = information;
            dos.writeUTF(data);
            return "1";
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("向客户端的数据传输失败！");
            return "0";
        }finally {

        }
    }
}