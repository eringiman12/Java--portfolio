package etc.design;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Style {
    public void help_style(javafx.scene.control.TextArea textArea_help) {
        textArea_help.setStyle(
            "-fx-background-color: orange;" +
            "-fx-padding:10px;" +
            "-fx-effect: dropshadow(three-pass-box, rgb(0, 0, 0, 0.6), 5, 0.0, 0, 2); "
        );
    }

    public void def_bottun_style(ArrayList<Button> bottun_list) {
        for (int i =0; i < bottun_list.size(); i++) {
            bottun_list.get(i).setStyle(
                "-fx-background-radius: 10px 0;" +
                "-fx-background-color: linear-gradient(#66A5AD, #ffffff);" +
                " -fx-effect: dropshadow(three-pass-box, rgb(0, 0, 0, 0.6), 1, 0.0, 0, 2); "
            );
        }
    }

    public void text_style(HBox hbox) {
    	hbox.setStyle(
    			"-fx-background-radius: 10px 0;" +
                "-fx-background-color: linear-gradient(#66A5AD, #ffffff);" +
                " -fx-effect: dropshadow(three-pass-box, rgb(0, 0, 0, 0.6), 1, 0.0, 0, 2); " +
                "-fx-padding:80px;" +
    	        "-fx-font-size:20px;"
        );
    }
}
