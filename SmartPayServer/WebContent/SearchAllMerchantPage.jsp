<%@page import="smu.smartpay.model.Merchant"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<table width=500>
	<html xmlns="http://www.w3.org/1999/xhtml">
<!--JS추가-->
<style>
/*               메인 스타일                         */
@import url(https://fonts.googleapis.com/css?family=Roboto:400,700,500);

html {
	box-sizing: border-box;
}

*, *:before, *:after {
	box-sizing: inherit;
}

body {
	background: #fafafa;
	font-family: "Roboto", sans-serif;
	font-size: 14px;
	margin: 0;
}

a {
	text-decoration: none;
}

.container {
	width: 1000px;
	margin: auto;
}

h1 {
	text-align: center;
	margin-top: 150px;
}
/*                       메뉴스타일                                 */
nav {
	background: #2ba0db;
} /*무슨색을할까*/
nav ul {
	font-size: 0;
	margin: 0;
	padding: 0;
}

nav ul li {
	display: inline-block;
	position: relative;
}

nav ul li a {
	color: #fff;
	display: block;
	font-size: 17px;
	padding: 16px 13px;
	transition: ?????????;
}

nav ul li :hover {
	background: #126d9b;
} /*메뉴와비슷한색*/
nav ul li ul {
	border-bottom: 5px solid #2ba0db; /*메뉴색이랑 같게*/
	display: none;
	position: absolute;
	width: 250px;
}

nav ul li ul li {
	border-top: 1px solid #444;
	display: block;
}

nav ul li ul li :first-child {border - top :none;
	
}

nav ul li ul li a {
	background: #373737;
	display: block;
	padding: 10px 14px;
}

nav ul li ul li a :hover {
	background: #000000;
}

nav .fa.fa-angle-down {margin - left :6px;
	
}
/*메뉴끝 =========================================*/
table, td {
	font: 100% Arial, Helvetica, sans-serif;
}

table {
	width: 70%;
	border-collapse: collapse;
	margin: auto;
}

th, td {
	text-align: left;
	padding: .5em;
	border: 1px solid #fff;
}

th {
	background: #328aa4 url(tr_back.gif) repeat-x;
	color: #fff;
}

td {
	background: #e5f1f4;
}

tr.even td {
	background: #000000;
}

tr.odd td {
	background: #000000;
}

th.over, tr.even th.over, tr.odd th.over {
	background: #000000;
}

th.down, tr.even th.down, tr.odd th.down {
	background: #000000;
}

th.selected, tr.even th.selected, tr.odd th.selected {
	
}

td.over, tr.even td.over, tr.odd td.over {
	background: #000000;
}

td.down, tr.even td.down, tr.odd td.down {
	background: #bce774;
	color: #fff;
}

td.selected, tr.even td.selected, tr.odd td.selected {
	background: #bce774;
	color: #555;
}

td.empty, tr.odd td.empty, tr.even td.empty {
	background: #fff;
}

body { /*테이블밖*/
	margin: 0;
	padding: 0;
	background: #f1f1f1;
	font: 70% Arial, Helvetica, sans-serif;
	color: #555;
	line-height: 150%;
	text-align: left;
}

a {
	text-decoration: none;
	color: #000000;
}

a:hover {
	text-decoration: none;
	color: #999;
}

h1 {
	font-size: 200%; /*100퍼센트 너무작*/
	margin: 0 20px;
	line-height: 80px;
}

h2 {
	font-size: 120%;
}

#container {
	margin: 0 auto;
	width: 680px;
	background: #fff; /*테이블 전체(바디색상)*/
	padding-bottom: 20px;
}

#content {
	margin: 0 20px;
}

p.sig {
	margin: 0 auto;
	width: 680px;
	padding: 1em 0;
}

form {
	margin: 1em 0;
	padding: .2em 20px;
	background: #eee;
}
</style>

</head>

<script type="text/javascript">
	//draft
</script>


<body>

	<!--메뉴시작-->

	<nav>
	<div class="container">
		<ul>
			<li><a href="AdminMain.jsp">Home</a></li>
			<li><a href="#">상인<i class='fa fa-angle-down'></i></a>
				<ul>
					<li><a href="main.do?action=addMerchant">상인 추가</a></li>
					<li><a href="main.do?action=updateMerchantPage">상인 수정 페이지</a></li>
					<li><a href="main.do?action=searchAllMerchant">모든 상인 조회</a></li>
				</ul></li>
			<li class='sub-menu'><a href="#">고객<i
					class='fa fa-angle-down'></i></a>
				<ul>
					<li><a href="main.do?action=addCustomer">고객 추가</a></li>
					<li><a href="main.do?action=updateCustomerPage">고객 수정 페이지</a></li>
					<li><a href="main.do?action=searchAllCustomer">모든 고객 조회</a></li>
				</ul></li>
		</ul>
	</div>
	</nav>

	<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script>
		$('nav li').hover(function() {
			$('ul', this).stop().slideDown(200);
		}, function() {
			$('ul', this).stop().slideUp(200);
		});
	</script>



	<!--메뉴끝-->


	<!--테이블 시작-->

	<%
		ArrayList<Merchant> merchantList = (ArrayList) request.getAttribute("merchantList");
	%>



	<div id="container">
		<center>
			<h1>모든 상인 조회</h1>
		</center>

		<div id="content">
			<center>
				<h2>상인목록</h2>
			</center>
			<table cellspacing="0" cellpadding="0">
				<tr>
					<th align="center">상인ID</th>
					<th align="center">비밀번호</th>
					<th align="center">이름</th>
					<th align="center">총금액</th>
				</tr>
				<%
					for (Merchant m : merchantList) {
				%>
				<tr>
					<td><%=m.getM_id()%></td>
					<td><%=m.getM_pass()%></td>
					<td><%=m.getM_name()%></td>
					<td><%=m.getM_sum()%></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>
	<!--테이블 끝~~-->

</body>
	</html>