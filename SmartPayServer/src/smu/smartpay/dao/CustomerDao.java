package smu.smartpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import smu.smartpay.model.Customer;
import smu.smartpay.util.DBUtil;

public class CustomerDao {
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	ArrayList<Customer> customerList;
	
	//고객 추가
	public int insertCustomer(Customer c) {
		con = DBUtil.getConnection();
		String sql = "insert into customer values (?,?,?,?)";
		pst = null;
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, c.getC_id());
			pst.setString(2, c.getC_pass());
			pst.setString(3, c.getC_name());
			pst.setInt(4, c.getC_sum());
			
			return pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(con);
			DBUtil.close(pst);
		}
		return 0;
	}
	
	//고객 전체조회
	public ArrayList<Customer> getAllCustomer() {
		customerList = new ArrayList<>();
		con = DBUtil.getConnection();
		String sql = "select * from customer";
		pst = null;
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				customerList.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}
			return customerList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(con);
			DBUtil.close(pst);
			DBUtil.close(rs);
		}
		return null;
	}
	
	//해당 고객 조회
	public Customer selectCustomerForId(String id) {
		con = DBUtil.getConnection();
		String sql = "select * from customer where c_id=?";
		pst = null;
		rs = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			rs.next();
			return new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(con);
			DBUtil.close(pst);
			DBUtil.close(rs);
		}
		return null;
	}
	
	//해당 고객 정보 수정
	public int updateCustomer(Customer c) {
		con = DBUtil.getConnection();
		String sql = "update `smartpay`.`customer` set `c_pass`=?, `c_name`=?, `c_sum`=? where `customer`.`c_id`=? limit 1";
		pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, c.getC_pass());
			pst.setString(2, c.getC_name());
			pst.setInt(3, c.getC_sum());
			pst.setString(4, c.getC_id());	
			return pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(con);
			DBUtil.close(pst);
		}
		return 0;
	}
	
	//해당 고객 정보 삭제
	public int deleteCustomer(String id) {
		con = DBUtil.getConnection();
		String sql = "delete from customer where c_id=?";
		pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			
			return pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(con);
			DBUtil.close(pst);
		}
		return 0;
	}
	
	//고객 로그인(검사)
	public boolean customerLoginCheck(String id, String pass) {
		con = DBUtil.getConnection();
		pst = null;
		rs = null;
		String sql = "select * from customer where c_id=? and c_pass=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, pass);
			rs = pst.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(con);
			DBUtil.close(rs);
			DBUtil.close(pst);
		}
		return false;
	}
	
	//고객 로그인
	public Customer customerLogin(String id, String pass) {
		con = DBUtil.getConnection();
		pst = null;
		rs = null;
		String sql = "select * from customer where c_id=? and c_pass=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, pass);
			rs = pst.executeQuery();
			rs.next();
			return new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(con);
			DBUtil.close(rs);
			DBUtil.close(pst);
		}
		return null;
	}
	
	//해당 고객 내역조회
	public ArrayList<Customer> selectCustomerHistory(String id) {
		customerList = new ArrayList<>();
		con = DBUtil.getConnection();
		String sql = "select c_id, c_date, c_price from c_history where c_id=?";
		pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				customerList.add(new Customer(rs.getString(1), rs.getString(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(con);
			DBUtil.close(pst);
			DBUtil.close(rs);
		}
		return null;
	}
}
