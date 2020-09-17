package main;

import static java.util.Calendar.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import etc.file.File_Operation;
import javafx.scene.control.Alert;

public class Base {
	//ユーザー情報格納
	ArrayList<String> user_leader = new ArrayList<String>();

	/*インストーラーに含められているファイルパス*/
	//Register_Name・help_Text・icon_path・template_Cpath
	//build配下のアイコンは、インストーラーで、必要ファイルをインストールした際に追加される

	//windowsユーザー名
	String username = System.getProperty("user.name");

	//rootpath
    String RootPath = "C:\\Users\\"+ username +"\\Desktop\\報告書\\";

    //ユーザー登録ファイルパス
    File Register_Name = new File("C:\\Hokoku\\text\\Register.txt");

    //ダイアログ
    Alert dialog = new Alert(Alert.AlertType.INFORMATION);

    //ファイル操作クラス
    File_Operation fo = new File_Operation();
    Calendar cal = getInstance();

    //ファイルの変数まとめメソッド
    public List<File> base(String event, List<File> File_List, ArrayList<String> YYYY_List,
    		ArrayList<String> Mon_List,ArrayList<String> Day_List){
    	//ユーザーとリーダー名を取得
    	Base base = new Base();
    	String User_Leader = base.base_read_txt();

	    //ヘルプ記載ファイルパス
        File help_Text = new File("C:\\Hokoku\\text\\Help.txt");
	    //アイコンファイルパス
	    File icon_path = new File("C:\\Hokoku\\ico\\shuho.png");
        //テンプレートパスCパス
        File template_Cpath = new File("C:\\Hokoku\\template\\template.xls");
        //翌年度月のフォルダ取得（月跨ぎ）
        File next_yyyy_dir = new File( RootPath + YYYY_List.get(1) + "\\" + Mon_List.get(1));
        //前年年度月のフォルダ取得（月跨ぎではない）
        File last_yyyy_dir = new File( RootPath + YYYY_List.get(3) + "\\" + Mon_List.get(3));
        //今日の年月のフォルダ取得（月跨ぎではない）
        File now_yyyy_dir = new File( RootPath + YYYY_List.get(0) + "\\" + Mon_List.get(0));
        //ZIPファイルパス(作成日が金曜日)
	File Zip_path_Today = new File("C:\\Users\\"+ username +"\\Desktop\\報告_" + Day_List.get(0) + User_Leader + ".zip");
        //ZIPファイルパス(作成日が金曜日じゃない)
	File Zip_path_next = new File("C:\\Users\\"+ username +"\\Desktop\\報告_" + Day_List.get(1) + User_Leader + ".zip");
        //今日の日付の報告書（作成日金曜日のみ）
        File Today_Friday = new File(now_yyyy_dir + "\\報告_"+ Day_List.get(0) + User_Leader + ".xls");
        File last_Friday = new File(last_yyyy_dir + "\\報告_"+ Day_List.get(2) + User_Leader + ".xls");
        //来週の金曜の報告書ファイル変数（月跨ぎではない）
        File next_Friday = new File(next_yyyy_dir + "\\報告_"+ Day_List.get(1) + User_Leader + ".xls");

        //ファイルのリスト配列
        //ツールバーのヘルプメニューの読み込み先
        File_List.add(help_Text);
        //ユーザー登録の読み込み先
        File_List.add(Register_Name);
        //アイコンの読み込み先
        File_List.add(icon_path);
        //テンプレートファイルの読み込み先
        File_List.add(template_Cpath);
        //次年度
        File_List.add(next_yyyy_dir);
        //昨年度
        File_List.add(last_yyyy_dir);
        //今日の日付のZipファイル
        File_List.add(Zip_path_Today);
        //次の金曜日のZipファイルの読み込み先
        File_List.add(Zip_path_next);
        //今日が金曜日
        File_List.add(Today_Friday);
        //先週の金曜日
        File_List.add(last_Friday);
        //次の金曜日
        File_List.add(next_Friday);

        //ファイルのリストを返す
        return File_List;
    }

    //各ユーザー登録ファイルを読み込み
    public  String base_read_txt() {
    	try {
            //ユーザー登録ファイルを読み込む(UTF-8)
    		BufferedReader b_reader = new BufferedReader(new InputStreamReader(new FileInputStream(Register_Name),"UTF-8"));

            //読み込んだファイルを１行ずつ画面出力する
            String line_text;

            //書いてある内容を読み込み終わるまで読み込む
            while ((line_text = b_reader.readLine()) != null) {
            	user_leader.add("_"+ line_text);
            }
            //終了処理
            b_reader.close();
        } catch (IOException ex) {
            //例外発生時処理
            ex.printStackTrace();
        }
    	//ユーザー名：報告先名を成型
    	String Rep_UL = String.join(",", user_leader);
    	String replace_text = Rep_UL.replace(",", "");

    	return replace_text;
    }

    //報告書作成メソッド
    public void Shuho_Bottun_Action(String event, List<File> File_List, ArrayList<String> Day_List,Alert dialog,
    		ArrayList<File> use_rep,String rep_text, ArrayList<String> Mon_List) throws IOException {
    	//このクラスのインスタンス
    	Base base = new Base();
    	//ユーザー名が登録されていない場合
    	if(rep_text.isEmpty()) {
    		dialog.setContentText("ユーザー情報が入力されてません。\nユーザー情報を入力してください。");
			dialog.showAndWait();
    	} else {
        	base.New_User_Array(File_List,use_rep,rep_text);
            cal.add( MONTH, + 0);
            int week = cal.get(DAY_OF_WEEK) - 1;
            //ボタン制御(報告書作成)
            if (event.equals("報告書作成")) {
                //今週分のを作成（先週の報告書をコピーする）
                fo.file_operation(File_List,dialog, week, Day_List,use_rep,Mon_List);
            }
    	}
    }

    //保存されているユーザーデータ取ってくる
    public ArrayList<File> New_User_Array(List<File> File_List, ArrayList<File> use_rep, String rep_text) {
    	for(int i = 6; i < File_List.size(); i++) {
    		if((i == 6 ) || (i == 7)){
    			//ZIP圧縮用の配列
    			String File_List_Str = File_List.get(i).toString();
    			//ユーザー名：報告先名判定
    			if(!File_List_Str.contains(rep_text)) {
    				String cat_F_ListStr = File_List_Str.substring(0,34) + rep_text + ".zip";
        	    	File f = new File(cat_F_ListStr);
        	    	use_rep.add(f);
    			} else {
    				File f = new File(File_List_Str);
        	    	use_rep.add(f);
    			}
    		} else {
    			String File_List_Str = File_List.get(i).toString();
    			//ファイルリストにユーザー名が入ってない場合
    			if(!File_List_Str.contains(rep_text)) {
    				String cat_F_ListStr = File_List_Str.substring(0,48) + rep_text + ".xls";
        	    	File f = new File(cat_F_ListStr);
        	    	use_rep.add(f);
    			} else {
    				//ユーザー名が入っているときはそのまま格納
    				File f = new File(File_List_Str);
        	    	use_rep.add(f);
    			}
    		}
    	}

    	//ユーザー名入りファイル名を返す
    	return use_rep;
    }
}
