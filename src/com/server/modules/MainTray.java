package com.server.modules;

import com.server.Main_window;
import javafx.application.Platform;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class MainTray {
    private TrayIcon trayIcon;


    public void enableTray(Stage qstage) {
        PopupMenu popupMenu = new PopupMenu();
        java.awt.MenuItem openItem = new java.awt.MenuItem("显示");
        java.awt.MenuItem hideItem = new java.awt.MenuItem("最小化");
        java.awt.MenuItem quitItem = new java.awt.MenuItem("退出");
        qstage.hide();
        ActionListener acl = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                java.awt.MenuItem item = (java.awt.MenuItem) e.getSource();
                Platform.setImplicitExit(false); //多次使用显示和隐藏设置false

                if (item.getLabel().equals("退出")) {
                    java.awt.SystemTray.getSystemTray().remove(trayIcon);
                    Platform.exit();
                    return;
                }
                if (item.getLabel().equals("显示")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            qstage.show();
                        }
                    });
                }
                if (item.getLabel().equals("最小化")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            qstage.hide();
                        }
                    });
                }

            }

        };

        //双击事件方法
        MouseListener sj = new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseClicked(MouseEvent e) {
                Platform.setImplicitExit(false); //多次使用显示和隐藏设置false
                if (e.getClickCount() == 2) {
                    if (qstage.isShowing()) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                qstage.hide();
                            }
                        });
                    }else{
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                qstage.show();
                            }
                        });
                    }
                }
            }
        };



        openItem.addActionListener(acl);
        quitItem.addActionListener(acl);
        hideItem.addActionListener(acl);

        popupMenu.add(openItem);
        popupMenu.add(hideItem);
        popupMenu.add(quitItem);

        try {
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            BufferedImage image = ImageIO.read(Main_window.class
                    .getResourceAsStream("resources/imgs/winClose_0.png"));
            trayIcon = new TrayIcon(image, "自动备份工具", popupMenu);
            trayIcon.setToolTip("自动备份工具");
            tray.add(trayIcon);
            trayIcon.addMouseListener(sj);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
