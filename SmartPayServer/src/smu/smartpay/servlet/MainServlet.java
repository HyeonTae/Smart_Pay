package smu.smartpay.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import smu.smartpay.dao.CustomerDao;
import smu.smartpay.dao.MerchantDao;
import smu.smartpay.model.Customer;
import smu.smartpay.model.Merchant;

/**
 * Servlet implementation class Servlet
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CustomerDao cDao = new CustomerDao();
	MerchantDao mDao = new MerchantDao();
	ObjectMapper mapper = new ObjectMapper();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("-------------------------------------------");
		String action = req.getParameter("action");
		if (action.equals("loginAdmin")) { // 관리자 로그인
			loginAdmin(req, res);
		} else if (action.equals("loginToMerchant")) { // 상인 로그인
			loginToMerchant(req, res);
		} else if (action.equals("loginToCustomer")) { // 고객 로그인
			loginToCustomer(req, res);
		} else if (action.equals("searchAllMerchant")) { // 전체 상인 조회
			searchAllMerchant(req, res);
		} else if (action.equals("searchAllCustomer")) { // 전체 고객 조회
			searchAllCustomer(req, res);
		} else if (action.equals("addMerchant")) { // 상인추가페이지
			res.sendRedirect("addMerchantPage.jsp");
		} else if (action.equals("addCustomer")) { // 고객추가페이지
			res.sendRedirect("addCustomerPage.jsp");
		} else if (action.equals("addMerchantToAdmin")) { // 상인 추가
			addMerchantToAdmin(req, res);
		} else if (action.equals("addCustomerToAdmin")) { // 고객 추가
			addCustomerToAdmin(req, res);
		} else if (action.equals("updateMerchantPage")) { // 상인 수정 페이지 이동
			res.sendRedirect("updateMerchantPage.jsp");
		} else if (action.equals("searchMerchantForID")) { // 상인 ID로 검색
			searchMerchantForID(req, res);
		} else if (action.equals("updateMerchant")) { // 상인정보수정
			updateMerchant(req, res);
		} else if (action.equals("deleteMerchant")) { // 상인정보삭제
			deleteMerchant(req, res);
		} else if (action.equals("updateCustomerPage")) { // 고객 수정 페이지 이동
			res.sendRedirect("updateCustomerPage.jsp");
		} else if (action.equals("searchCustomerForID")) { // 고객 ID로 검색
			searchCustomerForID(req, res);
		} else if (action.equals("updateCustomer")) { // 고객정보수정
			updateCustomer(req, res);
		} else if (action.equals("deleteCustomer")) { // 고객정보삭제
			deleteCustomer(req, res);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doPost..");
		req.setCharacterEncoding("utf-8");
		doGet(req, res);
	}

	// 관리자 로그인
	private void loginAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String adminId = request.getParameter("id");
		String adminPw = request.getParameter("pass");
		if (adminId.equals("admin") & adminPw.equals("1234")) {
			response.sendRedirect("AdminMain.jsp");
			System.out.println("관리자 로그인 성공");
		} else {
			String msg = "* ID 혹은 패스워드가 틀렸습니다.";
			request.setAttribute("msg", msg);
			try {
				request.getRequestDispatcher("/Login.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 상인 로그인
	private void loginToMerchant(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		System.out.println("접근 ID : " + id);
		boolean flag = mDao.merchantLoginCheck(id, pass);
		if (flag == true) {
			Merchant c = mDao.merchantLogin(id, pass);
			String name = c.getM_name();
			HashMap<String, String> map = new HashMap<>();
			map.put("name", name);
			String result = mapper.writeValueAsString(map);
			System.out.println("*상인" + name + "님 접속");
			request.setAttribute("result", result);
			request.getRequestDispatcher("/result.jsp").forward(request, response);
		} else { // 해당되는 ID가 없을때
			String name = "error"; // null이 아닌 "error"를 리턴함
			HashMap<String, String> map = new HashMap<>();
			map.put("name", name);
			String result = mapper.writeValueAsString(map);
			System.out.println("접속실패!");
			request.setAttribute("result", result);
			request.getRequestDispatcher("/result.jsp").forward(request, response);
		}
	}

	// 고객 로그인
	private void loginToCustomer(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		System.out.println("접근 ID : " + id);
		boolean flag = cDao.customerLoginCheck(id, pass);
		if (flag == true) {
			Customer c = cDao.customerLogin(id, pass);
			String name = c.getC_name();
			HashMap<String, String> map = new HashMap<>();
			map.put("name", name);
			String result = mapper.writeValueAsString(map);
			System.out.println("*고객" + name + "님 접속");
			request.setAttribute("result", result);
			request.getRequestDispatcher("/result.jsp").forward(request, response);
		} else { // 해당되는 ID가 없을때
			String name = "error"; // null이 아닌 "error"를 리턴함
			HashMap<String, String> map = new HashMap<>();
			map.put("name", name);
			String result = mapper.writeValueAsString(map);
			System.out.println("접속실패!");
			request.setAttribute("result", result);
			request.getRequestDispatcher("/result.jsp").forward(request, response);
		}
	}

	// 전체 상인 조회
	public void searchAllMerchant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("merchantList", mDao.getAllMerchant());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/SearchAllMerchantPage.jsp");
		System.out.println("모든 상인 조회");
		dispatcher.forward(request, response);
	}

	// 전체 고객 조회
	public void searchAllCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("customerList", cDao.getAllCustomer());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/SearchAllCustomerPage.jsp");
		System.out.println("모든 고객 조회");
		dispatcher.forward(request, response);
	}

	// 상인 추가
	public void addMerchantToAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		mDao.insertMerchant(new Merchant(request.getParameter("id"), request.getParameter("pass"),
				request.getParameter("name"), Integer.parseInt(request.getParameter("sum"))));
		System.out.println("상인추가완료");
		response.sendRedirect("AdminMain.jsp");
	}

	// 고객 추가
	public void addCustomerToAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		cDao.insertCustomer(new Customer(request.getParameter("id"), request.getParameter("pass"),
				request.getParameter("name"), Integer.parseInt(request.getParameter("sum"))));
		System.out.println("고객추가완료");
		response.sendRedirect("AdminMain.jsp");
	}

	// 상인 ID로 검색
	public void searchMerchantForID(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("merchant", mDao.selectMerchantForId(request.getParameter("id")));
		RequestDispatcher rd = request.getRequestDispatcher("/updateMerchantPage.jsp");
		System.out.println("상인조회완료");
		rd.forward(request, response);
	}

	// 상인정보 수정
	public void updateMerchant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		int sum = Integer.parseInt(request.getParameter("sum"));
		if (mDao.updateMerchant(new Merchant(id, pass, name, sum)) > 0) {
			System.out.println("상인 " + id + " 님이 수정되었습니다.");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminMain.jsp");
		dispatcher.forward(request, response);
	}

	// 상인정보 삭제
	public void deleteMerchant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		if (mDao.deleteMerchant(id) > 0) {
			System.out.println("상인 " + id + " 님이 삭제 되었습니다.");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminMain.jsp");
		dispatcher.forward(request, response);
	}

	// 고객 ID로 검색
	public void searchCustomerForID(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("customer", cDao.selectCustomerForId(request.getParameter("id")));
		RequestDispatcher rd = request.getRequestDispatcher("/updateCustomerPage.jsp");
		System.out.println("고객조회완료");
		rd.forward(request, response);
	}

	// 상인정보 수정
	public void updateCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		int sum = Integer.parseInt(request.getParameter("sum"));
		if (cDao.updateCustomer(new Customer(id, pass, name, sum)) > 0) {
			System.out.println("고객  " + id + " 님이 수정되었습니다.");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminMain.jsp");
		dispatcher.forward(request, response);
	}

	// 상인정보 삭제
	public void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		if (cDao.deleteCustomer(id) > 0) {
			System.out.println("고객  " + id + " 님이 삭제 되었습니다.");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminMain.jsp");
		dispatcher.forward(request, response);
	}
}
