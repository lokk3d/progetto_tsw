package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.bean.ChoosenProduct;
import model.bean.Order;
import model.bean.Product;

public class OrderModelDS implements OrderModel {
	private static DataSource ds;

	public OrderModelDS() {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/Web");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

	private static final String TABLE_NAME = "`order`";

	public long add(Order order) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + OrderModelDS.TABLE_NAME
				+ " (date, city, address, state, zip_code, details, track_id, shipping_price, payment_id, shipping_type_id, payment_code, id_user, order_state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setTimestamp(1, order.getDate());
			preparedStatement.setString(2, order.getCity());
			preparedStatement.setString(3, order.getAddress());
			preparedStatement.setString(4, order.getState());
			preparedStatement.setString(5, order.getZipCode());
			preparedStatement.setString(6, order.getDetails());
			preparedStatement.setString(7, order.getTrack_id());
			preparedStatement.setDouble(8, order.getShippingPrice());
			preparedStatement.setString(9, order.getPaymentId());
			preparedStatement.setInt(10, order.getShippingTypeId());
			preparedStatement.setString(11, order.getPaymentCode());
			preparedStatement.setInt(12, order.getIdUser());
			preparedStatement.setInt(13, order.getOrderState());
			int affectedRows = preparedStatement.executeUpdate();

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			}

		}
		return -1;

	}

	public boolean delete(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE from `order` WHERE id = ?";
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();
			if (preparedStatement != null)
				preparedStatement.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
			return false;
		}
	}

	public ArrayList<Order> getByDates(Date startDate, Date endDate) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Order> orders = new ArrayList<Order>();
		String selectSQL = "SELECT * FROM " + OrderModelDS.TABLE_NAME + " WHERE date BETWEEN ? and ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));

			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Order bean = new Order();
				bean.setId(rs.getInt("id"));
				bean.setDate(rs.getTimestamp("date"));
				bean.setCity(rs.getString("city"));
				bean.setAddress(rs.getString("address"));
				bean.setState(rs.getString("state"));
				bean.setZipCode(rs.getString("zip_code"));
				bean.setDetails(rs.getString("details"));
				bean.setTrack_id(rs.getString("track_id"));
				bean.setShippingPrice(rs.getDouble("shipping_price"));
				bean.setPaymentId(rs.getString("payment_id"));
				bean.setShippingTypeId(rs.getInt("shipping_type_id"));
				bean.setPaymentCode(rs.getString("payment_code"));
				bean.setIdUser(rs.getInt("id_user"));
				bean.setOrderState(rs.getInt("order_state"));
				orders.add(bean);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		return orders;
	}

	public ArrayList<Order> get() {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Order> orders = new ArrayList<Order>();
		String selectSQL = "SELECT * FROM " + OrderModelDS.TABLE_NAME;

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Order bean = new Order();
				bean.setId(rs.getInt("id"));
				bean.setDate(rs.getTimestamp("date"));
				bean.setCity(rs.getString("city"));
				bean.setAddress(rs.getString("address"));
				bean.setState(rs.getString("state"));
				bean.setZipCode(rs.getString("zip_code"));
				bean.setDetails(rs.getString("details"));
				bean.setTrack_id(rs.getString("track_id"));
				bean.setShippingPrice(rs.getDouble("shipping_price"));
				bean.setPaymentId(rs.getString("payment_id"));
				bean.setShippingTypeId(rs.getInt("shipping_type_id"));
				bean.setPaymentCode(rs.getString("payment_code"));
				bean.setIdUser(rs.getInt("id_user"));
				bean.setOrderState(rs.getInt("order_state"));
				orders.add(bean);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		return orders;

	}

	public ArrayList<Order> getByUser(int idUser) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Order> orderUser = new ArrayList<Order>();
		String selectSQL = "SELECT * FROM " + OrderModelDS.TABLE_NAME + " WHERE id_user = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, idUser);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Order bean = new Order();
				bean.setId(rs.getInt("id"));
				bean.setDate(rs.getTimestamp("date"));
				bean.setCity(rs.getString("city"));
				bean.setAddress(rs.getString("address"));
				bean.setState(rs.getString("state"));
				bean.setZipCode(rs.getString("zip_code"));
				bean.setDetails(rs.getString("details"));
				bean.setTrack_id(rs.getString("track_id"));
				bean.setShippingPrice(rs.getDouble("shipping_price"));
				bean.setPaymentId(rs.getString("payment_id"));
				bean.setShippingTypeId(rs.getInt("shipping_type_id"));
				bean.setPaymentCode(rs.getString("payment_code"));
				bean.setIdUser(rs.getInt("id_user"));
				bean.setOrderState(rs.getInt("order_state"));
				orderUser.add(bean);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		return orderUser;

	}

	public ArrayList<Order> getByState(int idUser) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Order> orderUser = new ArrayList<Order>();
		String selectSQL = "SELECT * FROM " + OrderModelDS.TABLE_NAME + " WHERE id_user = ? AND order_state !=4";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, idUser);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Order bean = new Order();
				bean.setId(rs.getInt("id"));
				bean.setDate(rs.getTimestamp("date"));
				bean.setCity(rs.getString("city"));
				bean.setAddress(rs.getString("address"));
				bean.setState(rs.getString("state"));
				bean.setZipCode(rs.getString("zip_code"));
				bean.setDetails(rs.getString("details"));
				bean.setTrack_id(rs.getString("track_id"));
				bean.setShippingPrice(rs.getDouble("shipping_price"));
				bean.setPaymentId(rs.getString("payment_id"));
				bean.setShippingTypeId(rs.getInt("shipping_type_id"));
				bean.setPaymentCode(rs.getString("payment_code"));
				bean.setIdUser(rs.getInt("id_user"));
				bean.setOrderState(rs.getInt("order_state"));
				orderUser.add(bean);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		return orderUser;
	}

	public void updateTracking(int id, String newTracking) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String updateSQL;
		updateSQL = "UPDATE `order` SET track_id = ? WHERE id = ?";
		// System.out.println(newTracking);

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			if (!newTracking.isEmpty()) {
				preparedStatement.setString(1, newTracking);
				preparedStatement.setInt(2, id);
			}
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			}

		}

	}

	public void updateOrderState(int id, int newOrderState) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String updateSQL;
		updateSQL = "UPDATE `order` SET order_state = ? WHERE id = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			if (newOrderState == 1 || newOrderState == 2 || newOrderState == 3 || newOrderState == 4) {
				preparedStatement.setInt(1, newOrderState);
				preparedStatement.setInt(2, id);
			}

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			}

		}
	}

	@Override
	public Order getById(int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Order order = new Order();
		String selectSQL = "SELECT * FROM " + OrderModelDS.TABLE_NAME + " WHERE  id = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				order.setId(rs.getInt("id"));
				order.setDate(rs.getTimestamp("date"));
				order.setCity(rs.getString("city"));
				order.setAddress(rs.getString("address"));
				order.setState(rs.getString("state"));
				order.setZipCode(rs.getString("zip_code"));
				order.setDetails(rs.getString("details"));
				order.setTrack_id(rs.getString("track_id"));
				order.setShippingPrice(rs.getDouble("shipping_price"));
				order.setPaymentId(rs.getString("payment_id"));
				order.setShippingTypeId(rs.getInt("shipping_type_id"));
				order.setPaymentCode(rs.getString("payment_code"));
				order.setIdUser(rs.getInt("id_user"));
				order.setOrderState(rs.getInt("order_state"));
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		return order;

	}

	public Order getCompleteOrderById(int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Order order = new Order();
		String selectSQL = "SELECT * FROM " + OrderModelDS.TABLE_NAME + " WHERE  id = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				order.setId(rs.getInt("id"));
				order.setDate(rs.getTimestamp("date"));
				order.setCity(rs.getString("city"));
				order.setAddress(rs.getString("address"));
				order.setState(rs.getString("state"));
				order.setZipCode(rs.getString("zip_code"));
				order.setDetails(rs.getString("details"));
				order.setTrack_id(rs.getString("track_id"));
				order.setShippingPrice(rs.getDouble("shipping_price"));
				order.setPaymentId(rs.getString("payment_id"));
				order.setShippingTypeId(rs.getInt("shipping_type_id"));
				order.setPaymentCode(rs.getString("payment_code"));
				order.setIdUser(rs.getInt("id_user"));
				order.setOrderState(rs.getInt("order_state"));
			}

			String selectSQLContains = "SELECT * FROM contains JOIN product on product_id=id WHERE  order_id = ?";
			preparedStatement = connection.prepareStatement(selectSQLContains);
			preparedStatement.setInt(1, id);

			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Product bean = new Product();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setDescription(rs.getString("description"));
				bean.setPrice(rs.getDouble("price")); // TODO: verifica cosa succede se il prezzo cambia
				bean.setVisible(rs.getBoolean("visible"));
				bean.setIva(rs.getInt("iva"));

				ChoosenProduct cp = new ChoosenProduct();
				cp.setProduct(bean);
				cp.setQuantity(rs.getInt("quantity"));
				order.getProducts().add(cp);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		return order;

	}

}
