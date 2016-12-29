package smu.smartpay.model;

public class Customer {
	private String c_id;
	private String c_pass;
	private String c_name;
	private int c_sum;
	private String c_date;
	private int c_price;
	
	public Customer(String c_id, String c_pass, String c_name, int c_sum) {
		super();
		this.c_id = c_id;
		this.c_pass = c_pass;
		this.c_name = c_name;
		this.c_sum = c_sum;
	}

	public Customer(String c_id, String c_date, int c_price) {
		super();
		this.c_id = c_id;
		this.c_date = c_date;
		this.c_price = c_price;
	}

	public String getC_id() {
		return c_id;
	}

	public void setC_id(String c_id) {
		this.c_id = c_id;
	}

	public String getC_pass() {
		return c_pass;
	}

	public void setC_pass(String c_pass) {
		this.c_pass = c_pass;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public int getC_sum() {
		return c_sum;
	}

	public void setC_sum(int c_sum) {
		this.c_sum = c_sum;
	}

	public String getC_date() {
		return c_date;
	}

	public void setC_date(String c_date) {
		this.c_date = c_date;
	}

	public int getC_price() {
		return c_price;
	}

	public void setC_price(int c_price) {
		this.c_price = c_price;
	}
}
