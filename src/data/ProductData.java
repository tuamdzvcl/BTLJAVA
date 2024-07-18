package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beean.Product;
import connect.Connect;

public class ProductData {

	Connect con = new Connect();
	Connection connect = null;
	PreparedStatement p = null;
	ResultSet res = null;

	public List<Product> findAll() {
		List<Product> list = new ArrayList<Product>();
		String sql = "select * from product";
		connect = con.connect();
		try {
			p = connect.prepareStatement(sql);
			res = p.executeQuery();
			while (res.next()) {
				Product pro = new Product(res.getString("code"), res.getString("name"),
						res.getInt("quantity"), res.getString("describe"), res.getFloat("price"),
						res.getString("image"));
				list.add(pro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Product> search(String query) {
		List<Product> list = new ArrayList<Product>();
		String sql = "select * from product where code like ? or name like ? or describe like ?";
		connect = con.connect();
		try {
			p = connect.prepareStatement(sql);
			p.setString(1, "%"+query+"%");
			p.setString(2, "%"+query+"%");
			p.setString(3, "%"+query+"%");
			res = p.executeQuery();
			while (res.next()) {
				Product pro = new Product(res.getString("code"), res.getString("name"),
						res.getInt("quantity"), res.getString("describe"), res.getFloat("price"),
						res.getString("image"));
				list.add(pro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean save(Product product) {
		String sql = "insert into product(code, name,quantity, describe, price, image) values (?,?,?,?,?,?)";
		connect = con.connect();
		try {
			p = connect.prepareStatement(sql);
			p.setString(1, product.getCode());
			p.setString(2, product.getName());
			p.setInt(3, product.getQuantity());
			p.setString(4, product.getDescribe());
			p.setFloat(5, product.getPrice());
			p.setString(6, product.getStringimage());
			return p.executeUpdate()>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Product product) {
		String sql = "update product set name = ?, quantity = ?, describe= ?, price=?, image =? where code = ?";
		connect = con.connect();
		try {
			p = connect.prepareStatement(sql);
			p.setString(1, product.getName());
			p.setInt(2, product.getQuantity());
			p.setString(3, product.getDescribe());
			p.setFloat(4, product.getPrice());
			p.setString(5, product.getStringimage());
			p.setString(6, product.getCode());
			return p.executeUpdate()>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(String code) {
		String sql = "delete from product where code = ?";
		connect = con.connect();
		try {
			p = connect.prepareStatement(sql);
			p.setString(1, code);
			return p.executeUpdate()>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
