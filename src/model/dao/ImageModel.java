package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.Category;

public interface ImageModel {
	
	public void updatePhoto(String prodotto, String img) throws SQLException;
	//public boolean delete(int id)  throws SQLException;
}
