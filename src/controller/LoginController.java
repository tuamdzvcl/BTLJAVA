package controller;

import java.io.IOException;

import beean.Account;
import data.AccountData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private TextField txt_username;

	@FXML
	private TextField txt_password;

	@FXML
	private Label lable_showAndHide;

	@FXML
	private PasswordField txt_passwordHide;

	private int check = 1;

	private AccountData accountData = new AccountData();

	@FXML
	void Btn_Login(ActionEvent event) {
		String str = "";
		if (check % 2 == 0) {
			str = txt_password.getText();
		} else {
			str = txt_passwordHide.getText();
		}
		Account account = new Account(txt_username.getText(), str);
		if (accountData.checkLogin(account)) {
			Parent root;
			Scene scene;
			Stage stage;
			try {
				root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setTitle("Trang chủ");
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("tài khoản hoặc mật khẩu không chính xác!");
			alert.show();
		}
	}
	
}

	
