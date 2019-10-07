package com.server;

import javafx.scene.control.Alert;

public class Warning_emit {
    public void  Popup_warning(){

    }
    public static void f_alert_informationDialog(String p_title, String p_message){
        Alert _alert = new Alert(Alert.AlertType.ERROR);
        _alert.setTitle(p_title);
        _alert.setHeaderText(p_message);
        _alert.show();
    }
}
