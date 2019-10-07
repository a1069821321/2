package com.server.modules;

public class GetTestData {
    private Thread thr;
    private void kk(){
        int max=100,min=1;
        long randomNum;
        int ran3;
        for (int i =0;i<50;i++) {

            randomNum = System.currentTimeMillis();
            ran3 = (int) (randomNum % 10);
            System.out.println(ran3);
        }
    }

    public static void main(String[] args) {

    }

}
