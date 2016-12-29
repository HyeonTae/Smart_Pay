package smu.smartpay.model;

public class Merchant {
	private String m_id;
	private String m_pass;
	private String m_name;
	private int m_sum;
	private String m_date;
	private int m_price;
	
	public Merchant(String m_id, String m_pass, String m_name, int m_sum) {
		super();
		this.m_id = m_id;
		this.m_pass = m_pass;
		this.m_name = m_name;
		this.m_sum = m_sum;
	}

	public Merchant(String m_id, String m_date, int m_price) {
		super();
		this.m_id = m_id;
		this.m_date = m_date;
		this.m_price = m_price;
	}

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public String getM_pass() {
		return m_pass;
	}

	public void setM_pass(String m_pass) {
		this.m_pass = m_pass;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public int getM_sum() {
		return m_sum;
	}

	public void setM_sum(int m_sum) {
		this.m_sum = m_sum;
	}

	public String getM_date() {
		return m_date;
	}

	public void setM_date(String m_date) {
		this.m_date = m_date;
	}

	public int getM_price() {
		return m_price;
	}

	public void setM_price(int m_price) {
		this.m_price = m_price;
	}
}
