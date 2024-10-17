package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import model.dao.DepartmentDao;
import model.db.exceptions.DbException;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

	Connection conn = null;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department obj) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(""
				+ "INSERT INTO Department "
				+ "(Name) VALUES (?) ", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultset(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}

		
	}

	@Override
	public void update(Department obj) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(""
				+ "UPDATE Department "
				+ "SET Name = ? "
				+ "WHERE Id = ? ");
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
			
		} 
		catch (Exception e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(""
				+ "DELETE FROM Department "
				+ "WHERE Id = ? ");
			
			st.setInt(1, id);
			
			st.executeUpdate();
			
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(""
				+ "SELECT Name, Id FROM Department "
				+ "WHERE Id = ? ");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;
			}
		
			return null;
			
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultset(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Department> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(""
				+ "SELECT Id, Name FROM Department ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Department> dep = new ArrayList<Department>();
			while(rs.next()) {
				dep.add(new Department(rs.getInt("Id"), rs.getString("Name")));
			}
			
			return dep;
			
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultset(rs);
			DB.closeStatement(st);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		Department dep =  new Department();
		dep.setName(rs.getString("Name"));
		dep.setId(rs.getInt("Id"));
		return dep;
		
	}
	
}
