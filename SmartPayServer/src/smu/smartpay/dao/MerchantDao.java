package smu.smartpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import smu.smartpay.model.Merchant;
import smu.smartpay.util.DBUtil;

public class MerchantDao {
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
ArrayList<Merchant> merchantList;
	
	//상인 추가
	public int insertMerchant(Merchant m) {
		con = DBUtil.getConnection();
		String sql = "insert into merchant values (?,?,?,?)";
		pst = null;
		
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, m.getM_id());
			pst.setString(2, m.getM_pass());
			pst.setString(3, m.getM_name());
			pst.setInt(4, m.getM_sum());
			
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
	
	//상인 전체조회
	public ArrayList<Merchant> getAllMerchant() {
		merchantList = new ArrayList<>();
		con = DBUtil.getConnection();
		String sql = "select * from merchant";
		pst = null;
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				merchantList.add(new Merchant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}
			return merchantList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//해당 상인 조회
	public Merchant selectMerchantForId(String id) {
		con = DBUtil.getConnection();
		String sql = "select * from merchant where m_id=?";
		pst = null;
		rs = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			rs.next();
			return new Merchant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
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
	
	//해당 상인 정보 수정
	public int updateMerchant(Merchant m) {
		con = DBUtil.getConnection();
		String sql = "update `smartpay`.`merchant` set `m_pass`=?, `m_name`=?, `m_sum`=? where `merchant`.`m_id`=? limit 1";
		pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, m.getM_pass());
			pst.setString(2, m.getM_name());
			pst.setInt(3, m.getM_sum());
			pst.setString(4, m.getM_id());	
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
	
	//해당 상인 정보 삭제
	public int deleteMerchant(String id) {
		con = DBUtil.getConnection();
		String sql = "delete from merchant where m_id=?";
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
	
	//상인 로그인(검사)
	public boolean merchantLoginCheck(String id, String pass) {
		con = DBUtil.getConnection();
		pst = null;
		rs = null;
		String sql = "select * from merchant where m_id=? and m_pass=?";
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
	
	//상인 로그인
	public Merchant merchantLogin(String id, String pass) {
		con = DBUtil.getConnection();
		pst = null;
		rs = null;
		String sql = "select * from merchant where m_id=? and m_pass=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, pass);
			rs = pst.executeQuery();
			rs.next();
			return new Merchant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
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
	
	//해당 상인 내역조회
	public ArrayList<Merchant> selectMerchantHistory(String id) {
		merchantList = new ArrayList<>();
		con = DBUtil.getConnection();
		String sql = "select m_id, m_date, m_price from m_history where m_id=?";
		pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				merchantList.add(new Merchant(rs.getString(1), rs.getString(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
