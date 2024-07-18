package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;

import beean.Product;
import data.ProductData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class HomeController implements Initializable {
	@FXML
	private TableView<Product> table;

	@FXML
	private TableColumn<Product, String> col_code;

	@FXML
	private TableColumn<Product, String> col_name;

	@FXML
	private TableColumn<Product, Integer> col_quan;

	@FXML
	private TableColumn<Product, Float> col_price;

	@FXML
	private TableColumn<Product, String> col_describe;

	@FXML
	private TableColumn<Product, ImageView> col_image;

	@FXML
	private TextField txt_search;

	@FXML
	private TextField txt_code;

	@FXML
	private TextField txt_name;

	@FXML
	private TextField txt_quantity;

	@FXML
	private TextField txt_describe;

	@FXML
	private TextField txt_price;

	@FXML
	private ImageView fastImage;

	private static String url = "";
	
	private String code = null;

	private ObservableList<Product> listProduct = FXCollections.observableArrayList();

	private ProductData productData = new ProductData();

	@FXML
	void Btn_Add(ActionEvent event) {
		if (checkInput()) {
			Product product = new Product(txt_code.getText(), txt_name.getText(),
					Integer.valueOf(txt_quantity.getText()), txt_describe.getText(), Float.valueOf(txt_price.getText()),
					url);
			if(productData.save(product)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Thêm thành công!");
				alert.show();
				clearInput();
				setInitTable(null);
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Thêm thất bại!");
				alert.show();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Không được để trống dữ liệu!");
			alert.show();
		}
	}

	@FXML
	void Btn_Delete(ActionEvent event) {
		if(!code.equals("")) {
			if(productData.delete(code)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Xóa thành công!");
				alert.show();
				clearInput();
				setInitTable(null);
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Xóa thất bại");
				alert.show();
			}
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Hãy chọn 1 dòng!");
			alert.show();
		}
	}

	@FXML
	void Btn_Update(ActionEvent event) {
		if(!code.equals("")) {
			if(checkInput()) {
				Product product = new Product(txt_code.getText(), txt_name.getText(),
						Integer.valueOf(txt_quantity.getText()), txt_describe.getText(), Float.valueOf(txt_price.getText()),
						url);
				if(productData.update(product)) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Cập nhật thành công!");
					alert.show();
					clearInput();
					setInitTable(null);
				}
				else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Cập nhật thất bại!");
					alert.show();
				}
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Không được để trống dữ liệu!");
				alert.show();
			}
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Hãy chọn 1 dòng!");
			alert.show();
		}
	}

	@FXML
	void OpenImage(MouseEvent event) throws IOException {
		FileChooser fc = new FileChooser();
		File f = fc.showOpenDialog(null);
		if (f != null) {
			String s = f.getName();
			Path urlFile = f.toPath();
			url = urlFile.toString();
			File file = new File(url);
			String path = file.toURI().toURL().toString();
			Image image = new Image(path);
			fastImage.setImage(image);

			Path source = Paths.get(url);
			Path targetDir = Paths.get("image");
			Files.createDirectories(targetDir);

			Path target = targetDir.resolve(source.getFileName());
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			url = "image/" + source.getFileName();
		}
	}

	@FXML
	void Search(KeyEvent event) {
		if(!txt_search.getText().equals("")) {
			setInitTable(productData.search(txt_search.getText()));
		}
		else {
			setInitTable(null);
		}
	}

	public void setInitTable(List<Product> listPro) {
		listProduct.clear();
		if (listPro == null) {
			productData.findAll().forEach(p -> {
				File file = new File(p.getStringimage());
				String path = null;
				try {
					path = file.toURI().toURL().toString();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Image image = new Image(path);
				p.setImageView(image);
				listProduct.add(p);
			});
		} else {
			listPro.forEach(p -> {
				File file = new File(p.getStringimage());
				String path = null;
				try {
					path = file.toURI().toURL().toString();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Image image = new Image(path);
				p.setImageView(image);
				listProduct.add(p);
			});
		}
		col_code.setCellValueFactory(new PropertyValueFactory<>("code"));
		col_describe.setCellValueFactory(new PropertyValueFactory<>("describe"));
		col_image.setCellValueFactory(new PropertyValueFactory<>("imageView"));
		col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		col_quan.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		table.setItems(listProduct);
	}

	public boolean checkInput() {
		if (!txt_code.getText().equals("") && !txt_describe.getText().equals("") && !txt_name.getText().equals("")
				&& !txt_price.getText().equals("") && !txt_quantity.getText().equals("") && url != "") {
			return true;
		}
		return false;
	}
	
	public void clearInput() {
		txt_code.setText("");
		txt_describe.setText("");
		txt_name.setText("");
		txt_price.setText("");
		txt_quantity.setText("");
		url = "";
	}

	public void clickRowTable() {
		table.setRowFactory(tv -> {
			TableRow<Product> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					txt_code.setText(row.getItem().getCode());
					txt_describe.setText(row.getItem().getDescribe());
					txt_quantity.setText(String.valueOf(row.getItem().getQuantity()));
					txt_name.setText(row.getItem().getName());
					url = row.getItem().getStringimage();
					txt_price.setText(String.valueOf(row.getItem().getPrice()));
					File file = new File(url);
					String path = null;
					try {
						path = file.toURI().toURL().toString();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					Image image = new Image(path);
					fastImage.setImage(image);
					
					code = row.getItem().getCode();
				}
			});
			return row;
		});
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setInitTable(null);
		clickRowTable();
	}
}
