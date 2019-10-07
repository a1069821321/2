package com.server.modules;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 48464;//监听的端口号
    public static void main(String[] args) {
        Server server = new Server();
        server.init();
    }
    public void init() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket client = serverSocket.accept();
                //一个客户端连接开两个线程处理读写
                new Thread(new ReadHandlerThread(client)).start();
            }
        } catch (Exception e) {

        } finally{
            try {
                if(serverSocket != null){
                    serverSocket.close();
                }
            } catch (IOException e) {

            }
        }
    }

}

//处理读操作的线程
class ReadHandlerThread implements Runnable {
    private Socket client;
    private String information;
    public ReadHandlerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try{

            while(true){//读取客户端数据
                dis = new DataInputStream(client.getInputStream());
                String reciver = dis.readUTF();
                System.out.println("客户端发过来的内容:" + reciver);
                //下面可能需要新线程。
                Server_RequestResponse s = new Server_RequestResponse();
                s.deal(client,reciver);
                /*
                if (reciver.equals("heat2picture")) {
                    information="heat2picture";
                    new Thread(new WriteHandlerThread(client,information)).start();
                }
                 */
            }

        }catch(Exception e){
            if(e.toString().equals("java.net.SocketException: Connection reset"))
                System.out.println("客户端掉线");
            else
                e.printStackTrace();
        }finally{
            try {
                if(dis != null){
                    dis.close();
                }/*
                if(client != null){
                    client = null;
                }
                */
            } catch (IOException e) {

            }
        }
    }
}

//处理写操作的线程
class WriteHandlerThread implements Runnable {
    private Socket client;
    private String information;
    public  WriteHandlerThread(Socket client, String information) {
        this.client = client;
        this.information = information;
    }
    @Override
    public void run() {
        DataOutputStream dos = null;
        BufferedReader br = null;
        try{
                System.out.println(information);
                dos = new DataOutputStream(client.getOutputStream());
                String data = information;
                dos.writeUTF(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("向客户端的数据传输失败！");
        }finally {
        }
    }
}
