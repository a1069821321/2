package com.server;
import java.awt.*;

import com.server.modules.serial_port.ByteUtils;
import com.server.modules.serial_port.SerialPortManager;
import com.server.modules.serial_port.ShowUtils;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import  javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.StageStyle;

///
import com.server.modules.*;


import java.util.ArrayList;
import java.util.List;
///


public class Main_window extends Application{
    public  static Stage main_stage;
    public static Label tempmaxi;public static Label tempnowi;
    private TextArea infomation = new TextArea();
    private List<Integer> selected_ones = new ArrayList();
    private VBox figure_area_=new VBox();
    private ObservableList<Data> list_p;

    private ObservableList<String> list = null;
    private List<String> mCommList = null;
    private SerialPort mSerialport;
    //Box
    private VBox top = new VBox();
    private VBox root = new VBox();
    private AnchorPane title = new AnchorPane();
    private HBox content = new HBox();
    private VBox first_area = new VBox();
    private VBox second_area = new VBox();
    private GridPane grid = new GridPane();
    private Button connect = new Button();
    private Label coms = new Label("串口与网络状态");
    private Label database_user = new Label("数据库管理");
    private Label test = new Label("测试");

    private Label setting = new Label("设置");
    private ChoiceBox sps_c1 = new ChoiceBox();
    private ChoiceBox sps_c2 = new ChoiceBox();
    private Label title_set = new Label();
    private Label sps_l1 = new Label();
    private Label sps_l2 = new Label();
    private ToggleGroup sps_g1 = new ToggleGroup();
    private RadioButton sps_rb1 = new RadioButton();
    private RadioButton sps_rb2 = new RadioButton();
    private Button sps_renew = new Button("刷新");

    private TextArea data_receive = new TextArea();
    private Label title_operate = new Label();
    private TextArea spo_t1 = new TextArea();
    private Button spo_b1 = new Button();
    private Button spo_b2 = new Button();
    public void start(Stage mainstage) {
        main_stage=mainstage;
        mainstage.initStyle(StageStyle.TRANSPARENT);
        init(mainstage);
        mainstage.show();
    }

    public void init(Stage mainstage) {
        root.setId("root");
        root.getStylesheets().add(Main_window.class.getResource("resources/css/title.css").toString());
        root.getStylesheets().add(Main_window.class.getResource("resources/css/box.css").toString());
        top.setId("top");

        Button close = new Button();
        close.setId("winClose");
        close.setOnMousePressed(event -> {
            main_stage.close();
        });
        Button minis = new Button();
        minis.setId("winMin");
        minis.setOnMousePressed(event -> {
            main_stage.setIconified(true);
        });
        Button maxs = new Button();
        maxs.setId("winMax");
        maxs.setOnMousePressed(event -> {
        });
        Button user = new Button();
        user.setId("winUser");
        user.setOnMousePressed(event -> {
        });
        Button downtray = new Button();
        downtray.setId("winDown");
        downtray.setOnMousePressed(event -> {
            MainTray mt = new MainTray();
            mt.enableTray(main_stage);
        });
        Label logo = new Label();
        logo.setId("logo");
        Label tit = new Label("Server");
        tit.setId("tit");
        title.getChildren().addAll(close, minis, user, downtray, logo, tit, maxs);

        AnchorPane.setRightAnchor(close, 5.0);
        AnchorPane.setTopAnchor(close, 8.0);
        AnchorPane.setRightAnchor(maxs, 30.0);
        AnchorPane.setTopAnchor(maxs, 8.0);
        AnchorPane.setRightAnchor(minis, 55.0);
        AnchorPane.setTopAnchor(minis, 8.0);
        AnchorPane.setRightAnchor(downtray, 85.0);
        AnchorPane.setTopAnchor(downtray, 8.0);
        AnchorPane.setRightAnchor(user, 115.0);
        AnchorPane.setTopAnchor(user, 9.0);
        AnchorPane.setLeftAnchor(logo, 15.0);
        AnchorPane.setTopAnchor(logo, 5.0);
        AnchorPane.setLeftAnchor(tit, 50.0);
        AnchorPane.setTopAnchor(tit, 5.0);

        /*Button Connect = new Button("Conncect");Connect.getStyleClass().addAll("Button_main");
        Connect.setOnMousePressed(event -> {
            Button_Connect();
        });*/

        first_area.setId("box_first_area");
        second_area.setId("box_second_area");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(0, 0, 0, 0));
        grid.setHgap(10);
        grid.setVgap(10);
        second_area.getChildren().addAll(grid);
        connect.setText("启动服务");
        coms.setText("串口与网络状态");


        test.setOnMousePressed(event -> {

        });
        setting.setOnMousePressed(event -> {

        });
        top.getChildren().add(title);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        content.setId("hbox_content");
        content.setPrefSize(screensize.getHeight(), screensize.getHeight() * 0.8);
        first_area.getChildren().addAll(connect, coms, database_user, test, setting);
        content.getChildren().addAll(first_area, second_area);
        root.getChildren().addAll(top, content);
        connect.setOnMousePressed(event -> {
            Server s = new Server();
            s.init();
        });
        initialize();
        Scene scene = new Scene(root);
        mainstage.setScene(scene);
        DragUtil.addDragListener(main_stage, top);

    }

    //窗体大小及设置项
    private void initialize(){
        first_area.prefHeightProperty().bind(content.prefHeightProperty());
        first_area.setPrefWidth(content.getPrefWidth()*0.15);
        addListener();
    }
    private void addListener(){
        coms.setOnMousePressed(event -> {
        //数据接收
        menu_item_selected(coms.getText());

        GridPane grid_coms = new GridPane();
        //串口设置
        GridPane sps_grid = new GridPane();
        VBox set = new VBox();
        set.setMaxWidth(content.getPrefWidth()*0.18);
        //串口操作
        GridPane spo_grid = new GridPane();
        VBox operate = new VBox();
        grid.getChildren().clear();
        grid_coms.setGridLinesVisible(true);
        grid_coms.setPadding(new Insets(4, 4, 4, 4));
        grid_coms.setStyle("-fx-padding: 3,3,3,3;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 2;"
                + "-fx-border-radius: 2;" + "-fx-border-color: black;");
        //数据接收面板
        data_receive.setPrefSize(400, 200);
        //串口设置面板
        title_set.setText("串口设置");
        sps_l1.setText("串口");
        sps_l2.setText("波特率");
        sps_c1.setPrefSize(400, 20);
        sps_c2.setPrefSize(400, 20);
        sps_c2.setItems(FXCollections.observableArrayList(
                "9600", "19200", "38400", "57600", "115200")
        );

        sps_rb1.setText("ASCll");
        sps_rb1.setToggleGroup(sps_g1);
        sps_rb2.setText("Hex");
        sps_rb2.setToggleGroup(sps_g1);
        sps_renew.setId("sps_renew");

        //sps_renew.setGraphic(new ImageView(image));
        //sps_renew.setPrefSize(14,14);
        sps_grid.setHgap(3);
        sps_grid.setVgap(4);
        //sps_grid.setGridLinesVisible(true);
        sps_grid.setPadding(new Insets(0, 0, 0, 0));
        sps_grid.add(sps_renew,2,0,1,1);
        sps_grid.add(title_set, 0, 0, 1, 1);
        sps_grid.add(sps_l1, 0, 1);
        sps_grid.add(sps_l2, 0, 2);
        sps_grid.add(sps_c1, 1, 1, 2, 1);
        sps_grid.add(sps_c2, 1, 2, 2, 1);
        sps_grid.add(sps_rb1, 0, 3, 1, 1);
        sps_grid.add(sps_rb2, 1, 3, 1, 1);
        set.setPrefSize(190, 150);
        set.setStyle("-fx-padding: 3,3,3,3;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;" + "-fx-border-insets: 2;"
                + "-fx-border-radius: 5;" + "-fx-border-color: black;");
        set.getChildren().addAll(sps_grid);

        //串口操作面板
        title_operate.setText("串口操作");
        spo_b1.setText("打开串口");
        spo_b2.setText("发送数据");
        spo_grid.setHgap(3);
        spo_grid.setVgap(4);
        //spo_grid.setGridLinesVisible(true);
        spo_grid.setPadding(new Insets(0, 0, 0, 0));
        spo_grid.add(title_operate, 0, 0, 1, 1);
        spo_grid.add(spo_t1, 0, 1, 3, 1);
        spo_grid.add(spo_b1, 0, 2);
        spo_grid.add(spo_b2, 1, 2);
        operate.setPrefSize(190, 150);
        operate.setStyle("-fx-padding: 3,3,3,3;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;" + "-fx-border-insets: 2;"
                + "-fx-border-radius: 5;" + "-fx-border-color: black;");
        operate.getChildren().addAll(spo_grid);
        //colspan 占列数， rowspan 占行数
        grid_coms.add(data_receive, 0, 0, 2, 1);
        grid_coms.add(set, 0, 1);
        grid_coms.add(operate, 1, 1);

        //串口列表
        ObservableList<String> list = FXCollections.observableArrayList();
        mCommList = SerialPortManager.findPorts();
        // 检查是否有可用串口，有则加入选项中
        if (mCommList == null || mCommList.size() < 1) {
            //
        } else {
            for (String s : mCommList)
                list.add(s);
            sps_c1.setItems(list);
        }
        grid.add(grid_coms, 0, 0, 1, 1);

    });

///////////////////////////////////////////////////////////
        database_user.setOnMousePressed(event -> {

            grid.getChildren().clear();
            TextArea data_receive = new TextArea();
            data_receive.setPrefSize(400, 200);
            Pane set = new Pane();
            set.setPrefSize(160, 150);
            Label title_set = new Label("串口设置");
            set.getChildren().addAll(title_set);
            //colspan 占列数， rowspan 占行数
            grid.add(data_receive, 0, 0, 2, 1);
            grid.add(set, 1, 1);
            menu_item_selected(database_user.getText());
        });
        test.setOnMousePressed(event->{
            menu_item_selected(test.getText()); });
        setting.setOnMousePressed(event->{
            menu_item_selected(setting.getText()); });
        //////////ComboBoxBase.ON_SHOWING

        sps_c1.setOnShowing(event -> {

        });
        sps_renew.setOnMousePressed(event ->{
            mCommList = SerialPortManager.findPorts();
            // 检查是否有可用串口，有则加入选项中
            sps_c1.getItems().removeAll();
            sps_c1.getItems().clear();
            if (mCommList == null || mCommList.size() < 1) {

            } else {
                ObservableList<String> list = FXCollections.observableArrayList();
                Object selected_port = sps_c1.getValue();
                for (String s : mCommList)
                    list.add(s);
                sps_c1.setItems(list);
                sps_c1.setValue(selected_port);
            }
        });

        //打开或关闭串口
        spo_b1.addEventHandler(ActionEvent.ACTION, e ->{

            if ("打开串口".equals(spo_b1.getText()) && mSerialport == null) {
                openSerialPort(e);
                sps_rb1.setDisable(true);
                sps_rb2.setDisable(true);
                sps_c1.setDisable(true);
                sps_c2.setDisable(true);
                sps_renew.setDisable(true);
            } else {
                closeSerialPort(e);
                sps_rb1.setDisable(false);
                sps_rb2.setDisable(false);
                sps_c1.setDisable(false);
                sps_c2.setDisable(false);
                sps_renew.setDisable(false);
            }
        } );
        spo_b2.addEventHandler(ActionEvent.ACTION, e -> {
            sendData(e);
        });

    }

    /**
     * 打开串口
     *
     * //@param evt
     *            点击事件
     */
    private void openSerialPort(ActionEvent evt) {
        // 获取串口名称
        String commName = (String) sps_c1.getValue();
        // 获取波特率，默认为9600
        int baudrate = 9600;
        try{
        String bps = (String)sps_c2.getValue();
        baudrate = Integer.parseInt(bps);
        }
        catch (Exception e){
        }
        // 检查串口名称是否获取正确
        if (commName == null || commName.equals("")) {
        } else {
            try {
                mSerialport = SerialPortManager.openPort(commName, baudrate);
                if (mSerialport != null) {
                    data_receive.setText("串口已打开" + "\r\n");
                    spo_b1.setText("关闭串口");
                }
            } catch (PortInUseException e) {
                //ShowUtils.warningMessage("串口已被占用！");
                Warning_emit.f_alert_informationDialog(
                        "打开端口失败","端口不存在或被占用");
                System.out.println("串口已被占用！");
            }
        }

        // 添加串口监听
        SerialPortManager.addListener(mSerialport, new SerialPortManager.DataAvailableListener() {

            @Override
            public void dataAvailable() {
                byte[] data = null;
                try {
                    if (mSerialport == null) {
                        ShowUtils.errorMessage("串口对象为空，监听失败！");
                    } else {
                        // 读取串口数据
                        data = SerialPortManager.readFromPort(mSerialport);

                        // 以字符串的形式接收数据
                        if (sps_rb1.isSelected()) {
                            data_receive.appendText(new String(data) + "\r\n");
                        }

                        // 以十六进制的形式接收数据
                        if (sps_rb2.isSelected()) {
                            data_receive.appendText(ByteUtils.byteArrayToHexString(data) + "\r\n");
                        }
                    }
                } catch (Exception e) {
                    ShowUtils.errorMessage(e.toString());
                    // 发生读取错误时显示错误信息后退出系统
                }
            }
        });
    }
    /**
     * 关闭串口
     *
     * //@param evt
     *            点击事件
     */
    private void closeSerialPort(ActionEvent evt) {
        SerialPortManager.closePort(mSerialport);
        data_receive.setText("串口已关闭" + "\r\n");
        spo_b1.setText("打开串口");
        mSerialport = null;
    }
    /**
     * 发送数据
     *
     * //@param evt
     *            点击事件
     */
    private void sendData(ActionEvent evt) {
        // 待发送数据
        String data = spo_t1.getText().toString();

        if (mSerialport == null) {
            ShowUtils.warningMessage("请先打开串口！");
            return;
        }

        if ("".equals(data) || data == null) {
            ShowUtils.warningMessage("请输入要发送的数据！");
            return;
        }

        // 以字符串的形式发送数据
        if (sps_rb1.isSelected()) {
            SerialPortManager.sendToPort(mSerialport, data.getBytes());
        }

        // 以十六进制的形式发送数据
        if (sps_rb2.isSelected()) {
            SerialPortManager.sendToPort(mSerialport, ByteUtils.hexStr2Byte(data));
        }
    }
    private void menu_item_selected(String button){
        if (button.equals("串口与网络状态")){
            coms.setTextFill(Color.RED);
            database_user.setTextFill(Color.BLACK);
            test.setTextFill(Color.BLACK);
            setting.setTextFill(Color.BLACK);
        }else if(button.equals("数据库管理")){
            coms.setTextFill(Color.BLACK);
            database_user.setTextFill(Color.RED);
            test.setTextFill(Color.BLACK);
            setting.setTextFill(Color.BLACK);
        }else if(button.equals("测试")){
            coms.setTextFill(Color.BLACK);
            database_user.setTextFill(Color.BLACK);
            test.setTextFill(Color.RED);
            setting.setTextFill(Color.BLACK);
        }else if(button.equals("设置")) {
            coms.setTextFill(Color.BLACK);
            database_user.setTextFill(Color.BLACK);
            test.setTextFill(Color.BLACK);
            setting.setTextFill(Color.RED);
        }else;
    }
    public void tray(){
        MainTray a = new MainTray();
        a.enableTray(main_stage);
    }

    public void connect(){
    }


    public static void main(String[] args) {
        launch(args);
    }
    private void Button_Connect(){

        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
                Server server = new Server();
                server.init();
                return null; }};
        new Thread(task).start();
    }
}

class Data{

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty alive = new SimpleStringProperty();
    private SimpleStringProperty selected = new SimpleStringProperty();
    private SimpleStringProperty none = new SimpleStringProperty();
    public Data(String name, String alive, String selected,String none){
        this.name.set(name);
        this.alive.set(alive);
        this.selected.set(selected);
        this.none.set(none);
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }
    public void setName(String _new){this.name.set(_new);}
    public String getName(){
        return name.get();
    }

    public SimpleStringProperty getAliveProperty() {
        return alive;
    }
    public String getAlive(){
        return alive.get();
    }
    public void setAlive(String _new){this.alive.set(_new);}

    public SimpleStringProperty getSelectedProperty() {
        return selected;
    }
    public void setSelected(String _new){this.selected.set(_new);}
    public String getSelected(){
        return selected.get();
    }
    public SimpleStringProperty getNoneProperty() {
        return none;
    }
    public String getNone(){
        return none.get();
    }
    @Override
    public String toString() {
        return "name=" + name + ", alive=" + alive + ", selected=" + selected+", none="+none;
    }

}
class Ports{
    private SimpleStringProperty port = new SimpleStringProperty();
    public Ports(String port){
        this.port.set(port);
    }
}