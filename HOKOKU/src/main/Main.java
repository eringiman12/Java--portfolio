package main;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import etc.design.Bottun_Design;
import etc.file.Excel_Action;
import etc.file.File_Operation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
    	launch(args);
    }

    private Stage stage;
    //デスクトップインスタンス
    Desktop desktop = Desktop.getDesktop();

    //ユーザー情報設定
    Button button_user_register = new Button("ユーザー設定");

    //今週の報告書作成ボタン
    Button button_create_shuho = new Button("報告書作成");
    //Excelデータの送信ボタン
    Button button_send_shuho = new Button("Excel送信");
    //該当Excelフォルダを開く
    Button button_openFolda_shuho = new Button("報告書確認");
    //ZIP圧縮
    Button button_zip_comp_shuho = new Button("ZIP圧縮");

    //ダイアログ
    Alert dialog = new Alert(Alert.AlertType.INFORMATION);

    //制御クラスのインスタンス
    Excel_Action ea = new Excel_Action();
    Bottun_Design bd = new Bottun_Design();
    Base base = new Base();
    File_Operation fo = new File_Operation();

    //戻り値
    ArrayList<File> File_List = new ArrayList<>();
    ArrayList<String> Af_hensyu_List = new ArrayList<>();
    ArrayList<File> use_Led = new ArrayList<File>();
    ArrayList<File> use_rep2 = new ArrayList<File>();

    @Override
    public void start(Stage stage) throws Exception {
        //年月日の配列
        ArrayList<String> Day_List = new ArrayList<String>();
        //年の配列
        ArrayList<String> YYYY_List = new ArrayList<String>();
        //月の配列
        ArrayList<String> Mon_List = new ArrayList<String>();

        //各カレンダーインスタンス
        //週カレンダー
 		Calendar cal_week = Calendar.getInstance();
 		//曜日カレンダー
 		Calendar cal_youbi = Calendar.getInstance();
		//現在の曜日カレンダー
 		Calendar now_col = Calendar.getInstance();

 		//金曜ナンバーの変数
	    int Friday_No;

	    //現在の日取り
		Date now = now_col.getTime();

 		//週初めを月曜に設定
 		cal_week.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
 		cal_youbi.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

 		//年月日・曜日をフォーマット
 		//年のフォーマット
 		SimpleDateFormat  df_yyyy = new SimpleDateFormat("yyyy");
 		//月のフォーマット
 		SimpleDateFormat  df_month = new SimpleDateFormat("MM");
 		//年月日を取得
 		SimpleDateFormat  df_week_day = new SimpleDateFormat("yyyyMMdd");
 		//曜日取得
 		SimpleDateFormat  df_youbi = new SimpleDateFormat("E");


 		//現在の年を取得
        String now_Year = new SimpleDateFormat( "yyyy" ).format( now.getTime() );
        String now_Day = new SimpleDateFormat( "yyyyMMdd" ).format( now.getTime() );
        String now_Month = new SimpleDateFormat( "MM" ).format( now.getTime() );

        String now_youbi = new SimpleDateFormat( "E" ).format( now.getTime() );


 		//先週の金曜日の変数初期化
 		String Friday_day_last = null;
 		//来週の金曜日の変数初期化
 		String Friday_day_next = null;

 		//7日前の年の初期化
 		String last_7_day_yyyy = null;
 		//7日後の年の初期化
 		String next_7_day_yyyy = null;

 		//7日前の月の初期化
 		String last_7_day_month = null;
 		//7日後の月の初期化
 		String next_7_day_month = null;

 		//##配列0
 		//年取得し格納
 		YYYY_List.add(now_Year + "年");
 		//日付取得し格納
 		Day_List.add(now_Day);
 		//月取得し格納
 		Mon_List.add(now_Month + "月");

 		//##配列1
 		//今週の処理
 		for (int i = 0; i < 7; i++) {
 			String youbi = df_youbi.format(cal_youbi.getTime());
 			//今週の金曜日の処理
 			if(youbi.equals("金")) {
 				//今週の金曜の年月日を取得しDay_Listに格納
 				Friday_day_next = df_week_day.format(cal_youbi.getTime());
 				Day_List.add(Friday_day_next);

 				//今週の金曜の年を取得しYear_List_dataに格納
 				next_7_day_yyyy = df_yyyy.format(cal_youbi.getTime());
 				YYYY_List.add(next_7_day_yyyy + "年");

 				//今週の金曜の月を取得しMonth_List_dataに格納
 				next_7_day_month = df_month.format(cal_youbi.getTime());
 				Mon_List.add(next_7_day_month + "月");
 			}
 			//カレンダー1日進める
 			cal_youbi.add(Calendar.DATE, 1);
 		}

 		//##配列2
 		//今日の月を格納
 		Mon_List.add(now_Month);
 		//今年度を格納後ろ二つを格納
 		YYYY_List.add(now_Year.substring(2,4));

 		//##配列3
 		//先週の金曜取得し配列に格納
 		for (int i = 0; i < 7; i++) {
 			String youbi = df_youbi.format(cal_week.getTime());
 			//金曜日の時の処理
 			if(youbi.equals("金")) {
 				//7日前の金曜の年月日を取得しDay_Listに格納
 				Friday_day_last = df_week_day.format(cal_week.getTime());
 				Day_List.add(Friday_day_last);

 				//先週の金曜の年を取得しYear_List_dataに格納
 				last_7_day_yyyy = df_yyyy.format(cal_week.getTime());
 				YYYY_List.add(last_7_day_yyyy + "年");


 				//7日前の金曜の月を取得しMonth_List_dataに格納
 				last_7_day_month = df_month.format(cal_week.getTime());
 				Mon_List.add(last_7_day_month + "月");

 			}
 			//カレンダー1日戻す
 			cal_week.add(Calendar.DATE, -1);
 		}

 		//金曜判定用
 		if(now_youbi.equals("金")) {
 			Friday_No = 5;
 		} else {
 			Friday_No = 0;
 		}

        //全体UI作成
        this.stage = stage;

        //ユーザー情報設定ボタンイベントハンドラー
        EventHandler<ActionEvent> btnActionFilter = (event) -> {
        	bd.user_con(File_List,stage,dialog);
        };
        button_user_register.addEventHandler( ActionEvent.ANY , btnActionFilter );

        //テキストボックスに先週の報告書内容表示
        base.base("",File_List,YYYY_List,Mon_List,Day_List);

        //ユーザー名：リーダー名のメソッド変数
        String Rep_txt = base.base_read_txt();

        //ユーザー名：リーダー名込みの報告書ファイル格納
        ArrayList<File> rep_file = base.New_User_Array(File_List,use_Led,Rep_txt);

        //起動時処理 報告書フォルダがない場合フォルダを作成
        //次週の金曜日が月跨ぎになる場合になる場合来月フォルダを作成
        if(!File_List.get(4).exists()) {
    		File_List.get(4).mkdirs();
    	}

        //報告書作成ボタンイベントハンドラー
        btnActionFilter = (event) -> {
            Button b = (Button)event.getSource();
            String event_handle = b.getText();
            try {
            	//Baseインスタンス
              	Base base = new Base();
              	//ユーザー名：リーダー名を再取得
            	String rep_text = base.base_read_txt();
            	//報告書ボタンを押した後の処理
                base.Shuho_Bottun_Action(event_handle,File_List,Day_List,dialog,use_rep2,rep_text,Mon_List);
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.consume();
        };
        button_create_shuho.addEventHandler( ActionEvent.ANY , btnActionFilter );

        //先週の報告書データをテキストエリアに表示
        ea.excel_Action(dialog,File_List,Day_List,Af_hensyu_List,rep_file,Rep_txt);

        java.util.List<String> AF_Henkan = Af_hensyu_List;

        //テキストエリアの初期値
        javafx.scene.control.TextArea textArea = new javafx.scene.control.TextArea(AF_Henkan.toString());

        //Excelに送信ボタンイベントハンドラー
        btnActionFilter = (event) -> {
            //エクセル操作メソッドへ(Excel_Action内メソッド)
            try {
            	//Baseクラスのインスタンス
              	Base base = new Base();
              	//ユーザー登録されたユーザー名：リーダー名
            	String rep_text = base.base_read_txt();
            	use_rep2.clear();
            	base.New_User_Array(File_List,use_rep2,rep_text);
                ea.excel_operation(dialog,textArea,Day_List,use_rep2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.consume();
        };
        button_send_shuho.addEventHandler( ActionEvent.ANY , btnActionFilter );

        //報告書ファイル開くボタンイベントハンドラー
        btnActionFilter = (event) -> {
            //フォルダ開くウィンドウ
            FileChooser fileChooser = new FileChooser();
            //ウィンドウ名
            fileChooser.setTitle( "報告書ファイル選択" );
            //ボタンを押したときの初期フォルダ
            fileChooser.setInitialDirectory( new File(String.valueOf(File_List.get(4))));
            fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter( "Excelファイル", "*.xls" ) );
            File file = fileChooser.showOpenDialog(this.stage);

            //Excelファイルを開く
            if (file != null) {
                EventQueue.invokeLater(() -> {
                    try {
                        desktop.open(file);
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        };
        button_openFolda_shuho.addEventHandler( ActionEvent.ANY , btnActionFilter );

        //ZIP圧縮ボタン
        btnActionFilter = (event) -> {
         	Base base = new Base();
        	String rep_text;
			rep_text = base.base_read_txt();
			use_rep2.clear();
			base.New_User_Array(File_List,use_rep2,rep_text);
			fo.zip_operation(Mon_List,File_List,dialog,use_rep2,Friday_No,YYYY_List);
        };
        button_zip_comp_shuho.addEventHandler( ActionEvent.ANY , btnActionFilter );

        //ボタンの配列指定
        ArrayList<Button> Botton_List = new ArrayList<>();
        Botton_List.add(button_user_register);
        Botton_List.add(button_create_shuho);
        Botton_List.add(button_send_shuho);
        Botton_List.add(button_openFolda_shuho);
        Botton_List.add(button_zip_comp_shuho);

        //ボタンのデザイン
        bd.bottun_design(Botton_List,stage,textArea,(ArrayList<File>) File_List);
    }
}
