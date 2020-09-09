package etc.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;

public class File_Judge {

    public void File_Judge_ari(Alert dialog, int week, List<File> File_List,ArrayList<File> use_rep) throws IOException {
    	//このクラスのインスタンス
    	File_Judge fj = new File_Judge();
    	//報告書フォルダ内（月別）にファイルの有無判定
    	File[] Check_List= fj.File_Contents_Check(File_List);
    	//報告書フォルダ内（月別）にファイルの有無判定後の処理
		if(Check_List.length == 0) {
			//報告書フォルダ内（月別）が空の時の処理
			fj.File_Check(week,use_rep,File_List);
 	    } else {
 	    	//報告書フォルダ内（月別）が空ではない時の処理
 	    	//同じフォルダ内の先週の報告書をコピーする
 	    	fj.File_Check(week,use_rep,File_List);
 	    }
    	dialog.getDialogPane().setPrefSize(600, 200);
     	dialog.setContentText("今週の報告書のコピーが完了しました。Excelデータの編集を行ってください。");
    }

    //報告書フォルダ内（月別）にファイルが存在するかのジャッジ
    public void File_Check(int week, ArrayList<File> use_rep, List<File> File_List) throws IOException {
    	//ファイル読み込みパス
    	Path sourcePath = null;
    	//ファイルコピー先パス
    	Path targetPath = null;

    	//作成日が金曜日の場合
    	if(week == 5) {
    		//先週の報告書ファイルが存在するか判定
    		//存在する時先週の報告書ファイルをコピーし、今週の報告書ファイルを作成する。
    		if(use_rep.get(3).exists()) {
    			sourcePath = Paths.get(String.valueOf(use_rep.get(3)));
	    		targetPath = Paths.get(String.valueOf(use_rep.get(2)));
		        Files.copy(sourcePath, targetPath);
    		} else {
    			//存在しない時template.xslを読み込み今週のファイルとして
    			//フォルダにコピーする
    			sourcePath = Paths.get(String.valueOf(File_List.get(3)));
	    		targetPath = Paths.get(String.valueOf(use_rep.get(4)));
		        Files.copy(sourcePath, targetPath);
    		}
    	//作成日が金曜日以外の処理
    	} else {
    		//先週の報告書ファイルが存在するか判定
    		//存在する時先週の報告書ファイルをコピーし、今週の報告書ファイルを作成する。
    		if(use_rep.get(3).exists()) {
	    		sourcePath = Paths.get(String.valueOf(use_rep.get(3)));
	    		targetPath = Paths.get(String.valueOf(use_rep.get(4)));
		        Files.copy(sourcePath, targetPath);
	    	} else {
	    		//存在しない時template.xslを読み込み今週の報告書ファイルとして
    			//報告書フォルダにコピーする
	    		sourcePath = Paths.get(String.valueOf(File_List.get(3)));
	    		targetPath = Paths.get(String.valueOf(use_rep.get(4)));
		        Files.copy(sourcePath, targetPath);
	    	}
    	}
    }

    //報告書フォルダ内にファイルの有無チェック
    public File[] File_Contents_Check(List<File> File_List) {
    	File[] Check_list = null;
    	//フォルダ内にファイルの存在チェック
    	if(File_List.get(4).exists()) {
    		Check_list = File_List.get(4).listFiles();
    	} else if(File_List.get(5).exists()) {
    		Check_list = File_List.get(5).listFiles();
    	}

    	//フォルダ内検索
	    for(int i=0; i< Check_list.length; i++) {
	    	Check_list[i].getName();      //ファイル名のみ
	    }

	    //結果を返す
	    return Check_list;
    }
}
