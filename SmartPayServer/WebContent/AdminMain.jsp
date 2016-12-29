<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
</style>

<script type="text/javascript">
	
</script>
<title>Insert title here</title>
</head>
<body>
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



</body>
</html>