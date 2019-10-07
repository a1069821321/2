package com.server.modules;

import com.server.Main_window;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

import java.text.DecimalFormat;
import java.util.Random;

public class DrawLineTest {
    private Thread thread;
    public static double maxtemp;
    public static Label tempm;
    public void Linedraw(Polyline polyline){
        polyline.getPoints().addAll(new Double[]{
                0.0, 0.0,
                5.0, 10.0,
                10.0, 20.0,
                15.0 , 34.0,
                20.0,65.0,
                25.0,92.0,
                30.0,15.0
        });
    }
    public void randomline(Polyline polyline, Label tempmaxi, Label tempnowi, Polyline p,
                           Line co_x, double xs, TextArea info, Line alli_bottom, Line alli_top){
                double x = 0;
                //double tems[] ={};
                Random r = new Random();
        //polyline.resize(500,300.0);
        polyline.getPoints().addAll(new Double[]{
                0.0,0.0});
                while(true){
                    try { Thread.sleep(1600);
                    }catch (Exception e ){
                        e.printStackTrace(); }
                    double y1=-r.nextDouble()*320;
                    x+=20;

                    double y=-y1;
                    polyline.getPoints().addAll(new Double[]{
                            x,y1
                    });
                    info.setText(""+polyline.getLayoutY());
                    alli_bottom.setStartY(polyline.getLayoutY());alli_bottom.setEndY(polyline.getLayoutY());
                    alli_top.setStartY(400-polyline.getLayoutY());alli_top.setEndY(400-polyline.getLayoutY());
                    if(x<=20) {maxtemp = y;}
                    Maxtemp(y);
                    if (polyline.getPoints().size()>(co_x.getEndX()-co_x.getStartX())/10)
                        polyline.getPoints().remove(0,2);
                    //info.setText("目前： "+polyline.getPoints().size());
                }
    }



    public void coordinate(Polyline p, Polyline p2, Line co_x, Line co_y, double xs){
        for(double x=20.0;x<co_x.getEndX()-co_x.getStartX();x+=xs){
            p.getPoints().addAll(new Double[]{
                    x,395.0,
                    x,400.0,
                    x+xs,400.0
            });
        }
        xs=40.0;


        for(double y=0.0;y<co_y.getEndY()-co_y.getStartY();y+=xs) {
            p2.getPoints().addAll(new Double[]{
                    0.0, y + xs,
                    5.0, y + xs,
                    0.0, y + xs,
            });
        }
    }
    public void aa(Polyline p, Polyline p2, Line co_x, Line co_y, double xs)
    {
        Platform.runLater(()->coordinate(p,p2,co_x,co_y,xs));
    }

    private void Maxtemp(double y){
        DecimalFormat df = new DecimalFormat("#.0");
        //tems[tems.length]=y;

        if(maxtemp<=y) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //更新JavaFX的主线程的代码放在此处
                    Main_window.tempmaxi.setText(df.format(maxtemp)); }});
            maxtemp = y; }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main_window.tempnowi.setText(df.format(y));
            }});

 }

    public void removeline(Polyline polyline) {
        polyline.getPoints().remove(0,polyline.getPoints().size());
    }

    public void stopline(Polyline polyline, Polyline polyline_stop) {
        polyline.setVisible(false);
        polyline_stop.getPoints().addAll(polyline.getPoints());
    }
    public void startline(Polyline polyline, Polyline polyline_stop) {

        polyline.setVisible(true);
        polyline_stop.getPoints().remove(0,polyline_stop.getPoints().size());
    }

    public static void main(String[] args) {

        //handler();
    }



}
