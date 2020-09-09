package etc.design;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import etc.file.File_Operation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Bottun_Design {
	//名前フィールド
	TextField name_text = new TextField();
	//リーダー名フィールド
	TextField leader_text = new TextField();
    AnchorPane pane = new AnchorPane();

    //スタイルインスタンス
    Style bs = new Style();
    //ラベル
	Label label_name = new Label();
	Label label_leader = new Label();

	//ファイル操作クラス
	File_Operation fo = new File_Operation();

	//テキストインスタンス
    Text  t1 = new Text();

    public void bottun_design(ArrayList<Button> Bottun_List,Stage stage, TextArea textArea,ArrayList<File> File_list) {
    	//ツール名
    	stage.setTitle("報告書作成ツール");

    	//メニューバーインスタンス
        MenuBar menubar = new MenuBar();

        //ヘルプメニュー
        Menu helpMenu = new Menu("ヘルプ(_F)");
        MenuItem helpItem = new MenuItem("ヘルプ(_I)");
        MenuItem userItem = new MenuItem("ユーザー情報(_U)");

        //ショートカットボタン
        helpItem.setAccelerator(KeyCombination.valueOf("Shortcut+i"));
        userItem.setAccelerator(KeyCombination.valueOf("Shortcut+u"));

        //ヘルプメニュー内のヘルプボタンを押したとき
        EventHandler<ActionEvent> btnActionFilter = (event) -> {
        	//ヘルプ配列
        	ArrayList<String> help = new ArrayList<String>();
        	String btn_name = helpItem.getText();
        	//入力した名前とリーダー名を返す
        	fo.users_info(btn_name,File_list,help);
            // 新しいウインドウを生成
            Stage helpStage = new Stage();
            //ウィンドウ名
            helpStage.setTitle("ヘルプ");
            // モーダルウインドウに設定
            helpStage.initModality(Modality.APPLICATION_MODAL);
            // オーナーを設定
            helpStage.initOwner(stage);
            String str = String.join(",", help);

            // 新しいウインドウ内に配置するコンテンツを生成
            HBox hbox = new HBox();

            //ヘルプテキスト内の格納された文字列をStringに変換
            String replace_text = str.toString().replace("[", "").replace("]", "").replace(",", "");
        	t1.setText(replace_text);

            hbox.getChildren().add(t1);
            helpStage.setScene(new Scene(hbox));
            bs.text_style(hbox);
            // 新しいウインドウを表示
            Image img = new Image(File_list.get(2).toURI().toString());
        	helpStage.getIcons().add(img);
            helpStage.show();
        };
        helpItem.addEventHandler( ActionEvent.ANY , btnActionFilter );

        //ユーザー情報の表示
        btnActionFilter = (event) -> {
        	//ユーザー配列
        	ArrayList<String> user = new ArrayList<String>();
        	//ボタン名
        	String btn_name = userItem.getText();
        	//入力した名前とリーダー名を返す
        	fo.users_info(btn_name,File_list,user);

        	// 新しいウインドウを生成
            Stage user_infoStage = new Stage();
            user_infoStage.setTitle("ユーザー情報");
            user_infoStage.setWidth(350);
            user_infoStage.setHeight(250);

            // モーダルウインドウに設定
            user_infoStage.initModality(Modality.APPLICATION_MODAL);
            // オーナーを設定
            user_infoStage.initOwner(stage);
            String str = String.join(",", user);

            // 新しいウインドウ内に配置するコンテンツを生成
            HBox hbox = new HBox();
            String replace_text = str.toString().replace("[", "").replace("]", "").replace(",", "");
            t1.setText(replace_text);

            hbox.getChildren().add(t1);
            bs.text_style(hbox);
            user_infoStage.setScene(new Scene(hbox));
            Image img = new Image(File_list.get(2).toURI().toString());
            user_infoStage.getIcons().add(img);
            // 新しいウインドウを表示
            user_infoStage.show();
        };
        userItem.addEventHandler( ActionEvent.ANY , btnActionFilter );

        helpMenu.getItems().addAll(helpItem,userItem);
        menubar.getMenus().addAll(helpMenu);


        //ボタン位置設定
        //一番上のボタンから150y軸ずれ
        int tate = 150;
        for (int i = 0; i < Bottun_List.size();i++) {
            //ループ最後にテキストエリアをステージに足す
            if(i !=Bottun_List.size()-1){
                pane.getChildren().addAll(Bottun_List.get(i));
            } else {
                pane.getChildren().addAll(menubar,Bottun_List.get(i),textArea);
            }

            //ボタンサイズの繰り返し
            Bottun_List.get(i).setPrefSize(180,50);
            pane.setLeftAnchor(Bottun_List.get(i), 50.);
            pane.setTopAnchor(Bottun_List.get(i), (double) tate);

            //ボタンのデザイン
            bs.def_bottun_style(Bottun_List);

            //y軸の距離
            tate += 80;
        }

        //テキストエリアの大きさ
        textArea.setMinSize(900, 600);

        //テキストエリアの位置
        pane.setLeftAnchor(textArea, 280.);
        pane.setTopAnchor(textArea, 100.);
        Scene scene = null;
        //ステージ全体の大きさ
        scene = new Scene(pane, 1300, 940);
        stage.setScene(scene);

        //アイコン画像
        String[] icon_png =  {"user.png","create.png","send.png","read.png","zip.png"};

        //各ボタンのアイコン画像を設定
        for(int i = 0; i < icon_png.length; i++) {
        	//アイコンのファイルパス
        	File File_icon = new File("C:\\Shuho_Tool\\ico\\botton_icon\\" + icon_png[i]);

        	Image icon_img = new Image(File_icon.toURI().toString());
            Bottun_List.get(i).setGraphic(new ImageView(icon_img));
        }

        //アプリアイコン表示判定
        Image img = new Image(File_list.get(2).toURI().toString());
    	stage.getIcons().add(img);
        stage.show();
    }

    //ユーザー情報の登録
    public void user_con(List<File> File_list, Stage stage,Alert dialog) {
    	// 画面の設定
		stage.setTitle("ユーザー情報の登録");

		// テキストフィールドの内容を反映させるボタン
		Button btn = new Button("完了");
		btn.setPrefWidth(60);
		btn.setPrefHeight(30);

		//ユーザー情報設定ボタンイベントハンドラー
        EventHandler<ActionEvent> btnActionFilter = (event) -> {
        	String name = name_text.getText();
        	String leader_name = leader_text.getText();
        	//入力した名前とリーダー名を保存
        	fo.users_control(name,leader_name,File_list,dialog);
        };
        btn.addEventHandler( ActionEvent.ANY , btnActionFilter );

        // 新しいウインドウを生成
        Stage userStage = new Stage();

        // モーダルウインドウに設定
        userStage.initModality(Modality.APPLICATION_MODAL);

        // オーナーを設定
        userStage.initOwner(stage);
        userStage.setWidth(450);
        userStage.setHeight(400);
        userStage.setTitle("ユーザー登録設定");

        //ラベルの大きさと位置設定
        label_name.setText("名前を入力してください。");
        label_leader.setText("報告先を入力してください。");
        label_name.setPrefWidth(250);
        label_name.setPrefHeight(60);
        label_leader.setPrefWidth(250);
        label_leader.setPrefHeight(60);

        //テキストフィールドの大きさと位置設定
        name_text.setPrefWidth(80);
        name_text.setPrefHeight(40);
        leader_text.setPrefWidth(80);
        leader_text.setPrefHeight(40);

        // 新しいウインドウ内に配置するコンテンツを生成(垂直設定)
        VBox root = new VBox();
        root.setPadding(new Insets(5, 45, 5, 5));
        root.setSpacing(7.0);

        root.getChildren().addAll(label_name,name_text,label_leader,leader_text,btn);

        userStage.setScene(new Scene(root));
        Image img = new Image(File_list.get(2).toURI().toString());
        userStage.getIcons().add(img);
        // 新しいウインドウを表示
        userStage.show();
	}
}
