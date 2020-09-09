package etc.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javafx.scene.control.Alert;

public class Excel_Action {
	File_Judge fj = new File_Judge();
    int Shuho_Array[] = {0,1,10,11,21,22,47,48,51,52};
    InputStream is = null;

    //先週の報告書の内容を読み込む
    public List<String> excel_Action(Alert dialog,List<File> File_List,List<String> Day_List,ArrayList<String> Af_hensyu_List,
    		ArrayList<File> use_rep, String rep_txt) {
    	//前週のファイルを読み込む
    	//フォルダ内にファイルの存在チェック
    	File[] Check_List= fj.File_Contents_Check(File_List);

        Workbook wb = null;try {
        	//ユーザー名：報告先と報告書ファイルが無ければテンプレートを呼び出す。（テキストエリア）
        	if(rep_txt.isEmpty() || Check_List.length == 0) {
        		is = new FileInputStream(File_List.get(3));
        	} else {
        		//ユーザー登録後の処理（テキスト）
                if (use_rep.get(3).exists()) {
                	//先週の金曜日の報告書を参照する
                    is = new FileInputStream(use_rep.get(3));
                } else {
                	//先週の金曜日の報告書が見つからない場合template.xslを参照する
                	is = new FileInputStream(File_List.get(3));
                }
        	}

            wb = WorkbookFactory.create(is);
            //1番目のシートを読み込む
            for (int i = 0; i < Shuho_Array.length; i++){
                Sheet sh = wb.getSheetAt(0);
                //1行目を読み込む
                Row row = sh.getRow(Shuho_Array[i]);
                //1セル目を読み込む
                Cell cell = row.getCell(0);
                //セルの値をString値として読み込む
                String value = cell.getStringCellValue();

                //1行目以外改行
                if(i == 0) {
                    if (value.contains("■") || value.contains("*")) {
                        value = value + "\n";
                    }
                } else {
                    if (value.contains("■") || value.contains("*")) {
                        value = "\n" + value + "\n";
                    }
                }
                //配列に格納
                Af_hensyu_List.add(value);

            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        //配列に格納した先週の報告書内容を返す
        return Af_hensyu_List;
    }

    //今週の報告書ファイルに書き込み
    public void excel_operation(Alert dialog, javafx.scene.control.TextArea textArea,ArrayList<String> Day_List,
    		ArrayList<File> use_rep) throws IOException {
        dialog.getDialogPane().setPrefSize(600, 200);
        //テキストエリアに記入した文字列を受け取る
        String text = textArea.getText();
        String replace_text = text.replace("[", "").replace("]", "");
        //取ってきた文字列を配列に格納
        String[] Array_text = replace_text.split(",", 0);

        //今週の金曜日報告書ファイルがある場合
        if (use_rep.get(4).exists()) {
            //書き込む報告書ファイルを指定し書き込む
            FileInputStream in  = new FileInputStream(use_rep.get(4));
            Workbook wb = null;
            try {
                // 既存のエクセルファイルを編集する際は、WorkbookFactoryを使用
                wb = WorkbookFactory.create(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //各行に報告書の内容を書き込む
            for (int i = 0;i < Shuho_Array.length; i++) {
                //書き込むブックの指定
                Sheet sheet = wb.getSheet("Sheet1");
                //書き込む行の指定
                Row row = sheet.getRow(Shuho_Array[i]);
                //書き込むセルの指定
                Cell cell = row.getCell(0);
                //書き込んだ後のセル
                cell.setCellValue(Array_text[i]);
            }

            //書き込み先
            FileOutputStream out = null;
            try {
                // 変更するエクセルファイルを指定
                out = new FileOutputStream(use_rep.get(4));
                // 書き込み
                wb.write(out);
            }catch(Exception e){
                e.printStackTrace();
            } finally{
                out.close();
                wb.close();
            }
            dialog.setContentText("今週の報告書ファイルに正常に書き込みました。");
        } else {
            dialog.setContentText("報告書に書き込めません。本月フォルダ内を確認してください。");
        }

        dialog.showAndWait();
    }

}
