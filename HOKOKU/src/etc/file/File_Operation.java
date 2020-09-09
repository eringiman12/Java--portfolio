package etc.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;

//ファイル操作クラス
public class File_Operation {
    File_Judge fj = new File_Judge();
    //報告書ファイル、フォルダの作成
    public void file_operation(List<File> File_List, Alert dialog, int week, ArrayList<String> DayList,
    	ArrayList<File> use_rep, ArrayList<String> Mon_List) throws IOException {

    	//報告書フォルダの有無
        if(use_rep.get(4).exists()) {
        	dialog.getDialogPane().setPrefSize(600, 200);
            dialog.setContentText("指定したファイルもしくはディレクトリは存在します。\n");
        } else {
        	//報告書ファイルの作成ジャッジ
        	fj.File_Judge_ari(dialog,week,File_List,use_rep);
        }

        dialog.showAndWait();
    }

	//ZIPファイルの作成
	public void zip_operation(ArrayList<String> Mon_List, List<File> File_List, Alert dialog,
			ArrayList<File> use_rep, int Friday_No, ArrayList<String> YYYY_List) {
	   ZipParameters zipParameters = new ZipParameters();
       zipParameters.setEncryptFiles(true);
       //暗号化方式はAES
       zipParameters.setEncryptionMethod(EncryptionMethod.AES);
       //暗号強度はAES256
       zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);

       try {
    	   //パスワード設定
           String password = "tpi-" + YYYY_List.get(2) +Mon_List.get(2);

           //ZIPにするファイル
           //金曜日に作成する場合とそうではない場合
           if(Friday_No == 5) {
               // コンストラクタの引数に、"出力先" と "パスワード" を指定します。
               ZipFile zipFile = new ZipFile(use_rep.get(0), password.toCharArray());
               //windows10の文字化け対策
               zipFile.setCharset(Charset.forName("windows-31j"));
        	   zipFile.addFile(use_rep.get(2), zipParameters);
           } else {
               // コンストラクタの引数に、"出力先" と "パスワード" を指定します。
               ZipFile zipFile = new ZipFile(use_rep.get(1), password.toCharArray());
               //windows10の文字化け対策
               zipFile.setCharset(Charset.forName("windows-31j"));System.out.println("aaaaaaaaaaaaaaaaa");
        	   zipFile.addFile(use_rep.get(4), zipParameters);
           }

           //ダイアログの設定
           dialog.getDialogPane().setPrefSize(600, 200);
           dialog.setContentText("正常にZIP圧縮を行いました。");
       } catch (ZipException ze) {
       	   dialog.getDialogPane().setPrefSize(600, 200);
           dialog.setContentText("ZIP圧縮に失敗しました。フォルダ内を確認してください。");
       }

       dialog.showAndWait();
	}

	//ユーザー登録テキストファイルに書き込む
	public void users_control(String name,String leader_name,List<File> File_list,Alert dialog) {
		try {
			if(name.isEmpty() || leader_name.isEmpty()) {
				dialog.setContentText("名前と報告先名を入力してください。");
			} else {
			   if(File_list.get(1).exists()) {
					// 文字コードを指定する
		            PrintWriter p_writer = new PrintWriter(new BufferedWriter
		                (new OutputStreamWriter(new FileOutputStream(File_list.get(1)),"UTF-8")));

		            //ファイルに書き込む
		            p_writer.println(name);
		            p_writer.println(leader_name);

		            //ファイルを閉じる
		            p_writer.close();

		            dialog.setContentText("ユーザー設定が完了しました。");
				} else {
					dialog.setContentText("Register.txtが見つかりません。\textフォルダ内を確認してください。");
				}
			}
        } catch (IOException e) {
            e.printStackTrace();
            dialog.setContentText("ユーザー設定に失敗しました。");
        }
		dialog.showAndWait();
	}

	//ユーザー登録ファイルの読み込み
	public ArrayList<String> users_info(String btn_name,List<File> File_list,ArrayList<String> user) {
		if(btn_name.equals("ユーザー情報(_U)")) {
			try {
				BufferedReader b_reader = new BufferedReader(new InputStreamReader(new FileInputStream(File_list.get(1)),"UTF-8"));

                //読み込んだファイルを１行ずつ画面出力する
                String line;
                int i = 0;
                while ((line = b_reader.readLine()) != null) {
                	if(i == 0) {
                		user.add("ユーザー名：" + line + "\n");
                	} else if(i == 1) {
                		user.add("報告先：" + line + "\n");
                	}
                	i++;
                }
                b_reader.close();

            } catch (IOException ex) {
                //例外発生時処理
                ex.printStackTrace();
            }
		} else if(btn_name.equals("ヘルプ(_I)")) {
			//ヘルプメニュー
			try {
				//読み込むヘルプファイルの文字コードの指定
				BufferedReader b_reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(File_list.get(0)),"UTF-8"
				));

                //読み込んだファイルを１行ずつ画面出力する
                String line;
                while ((line = b_reader.readLine()) != null) {
                    user.add(line + "\n");
                }
                //終了処理
                b_reader.close();

            } catch (IOException ex) {
                //例外発生時処理
                ex.printStackTrace();
            }
		}

		return user;
	}
}
