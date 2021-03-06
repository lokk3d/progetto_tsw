package model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.bean.Image;
import model.bean.Product;

/**
 * Servlet implementation class ImageModelDS
 */
@WebServlet("/ImageModelDS")
public class ImageModelDS implements ImageModel {
	private static DataSource ds;
	private static final String TABLE_NAME = "image";

	public ImageModelDS() {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/Web");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

	public synchronized byte[] load(int idImmagine) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "SELECT img FROM " + ImageModelDS.TABLE_NAME + " WHERE id = ?";

		byte[] bt = null;

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idImmagine);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				bt = rs.getBytes("img");
			}

		} catch (SQLException sqlException) {
			System.out.println(sqlException);
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return bt;
	}

	public synchronized void updatePhoto(String prodotto, String img) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO " + ImageModelDS.TABLE_NAME + "(img,product_id) VALUES (?,?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			File file = new File(img);
			try {
				FileInputStream fis = new FileInputStream(file);
				preparedStatement.setBinaryStream(1, fis, fis.available());
				preparedStatement.setInt(2, Integer.parseInt(prodotto));
				preparedStatement.executeUpdate();

			} catch (FileNotFoundException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
	}

	public void deletePhoto(int idImmagine) throws SQLException {
		System.out.println("id che mi hanno passato: " + idImmagine);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM " + ImageModelDS.TABLE_NAME + " WhERE id= ?";
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idImmagine);
			preparedStatement.executeUpdate();
		} catch (SQLException sqlException) {
			System.out.println(sqlException);
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}

		}
	}

	@Override
	public ArrayList<Image> getImagesByProduct(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "SELECT id FROM " + ImageModelDS.TABLE_NAME + " WHERE product_id = ?";

		ArrayList<Image> images = new ArrayList<>();

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Image image = new Image();
				image.setProduct_id(id);
				image.setId(rs.getInt("id"));

				images.add(image);
			}

		} catch (SQLException sqlException) {
			System.out.println(sqlException);
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return images;
	}
}
