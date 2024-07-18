package beean;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Product {

	private String code;
	
	private String name;
	
	private int quantity;
	
	private String describe;
	
	private float price;
	
	private String stringimage;
	
	private ImageView imageView;

	public Product() {
		
	}

	public Product(String code, String name, int quantity, String describe, float price, String stringimage) {
		this.code = code;
		this.name = name;
		this.quantity = quantity;
		this.describe = describe;
		this.price = price;
		this.stringimage = stringimage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getStringimage() {
		return stringimage;
	}

	public void setStringimage(String stringimage) {
		this.stringimage = stringimage;
	}

	public ImageView getImageView() {
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);
		return imageView;
	}

	public void setImageView(Image image) {
		this.imageView = new ImageView(image);;
	}
	
}
