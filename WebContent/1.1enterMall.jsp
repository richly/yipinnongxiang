<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no">
<link href="./resources/styles/1.1enterMall.css" rel="stylesheet"
	type="text/css" />
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="./resources/bootstrap-3.3.6/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="./resources/bootstrap-3.3.6/js/jquery.1.11.3.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="./resources/bootstrap-3.3.6/js/bootstrap.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min.js"></script>
<script src="resources/javascript/gutabslider.js"></script>
<script type="text/javascript">
           $(function() {
				$("#tabs").gutabslider();
				// Move the active class when you click on a new tab
				$("#tabs li a").click(function() {
				    $("#tabs .active").removeClass("active");
				    $(this).parent("li").addClass("active");
				    $("#tabs").gutabslider("active-tab-changed");
				});
			});
    </script>
<title>一品农香</title>
</head>
<body>
	<!-- searchfield -->
	<div>
		<form>
			<div class="text">
				<input type="search" class="text" id="searchProduct"
					placeholder="搜索你要的商品">
			</div>
		</form>
	</div>
	<!-- nav tabs -->
	<div>
		 <ul id="tabs">
	        <li class="active">
	            <a href="#">首页</a>
	        </li>
	        <li >
	            <a href="#">水果</a>
	        </li>
	        <li >
	            <a href="#">养生保健</a>
	        </li>
	        <li >
	            <a href="#">土特产</a>
	        </li>
	        <li >
	            <a href="#">蜂具</a>
	        </li>
	    </ul>
	</div>
	<!-- carousel -->
	<div id="carousel-example-generic" class="carousel slide">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#carousel-example-generic" data-slide-to="0"
				class="active"></li>
			<li data-target="#carousel-example-generic" data-slide-to="1"></li>
			<li data-target="#carousel-example-generic" data-slide-to="2"></li>
		</ol>

		<!-- Wrapper for slides -->
		<div class="carousel-inner" role="listbox">
			<div class="item active">
				<img src="images/115911679716d78b6dl.jpg" height="150px" alt="">
				<div class="carousel-caption"></div>
			</div>
			<div class="item">
				<img src="images/11591168006939fb02l.jpg" height="150px" alt="">
				<div class="carousel-caption"></div>
			</div>
			<div class="item">
				<img src="images/11591168051b59ca24l.jpg" height="150px" alt="">
				<div class="carousel-caption"></div>
			</div>
		</div>
	</div>

	<div id="todaySeckill">
		<div style="clear: both;" id="">
			<span class="title"><h5>今日秒杀</h5></span> 
			<span class="more"><a
				href="">更多></a></span>
		</div>
        <div style="clear: both;" id=""></div>
       
		<div >
			<div class="product" style="display:inline-block;"></div>
			<div class="product" style="display:inline-block;"></div>
		</div>
	</div>

	<div id="freshHotSale" style="clear: both;">
		<div id="">
			<span class="title"><h5>时鲜水果热卖</h5></span> <span class="more"><a
				href="">更多></a></span>
		</div>
	</div>

	<div id="speciality">
		<div id=""></div>
	</div>
	
	<div id="beekeeping">
	</div>

	<script type="text/javascript">
		$(".carousel").carousel({
			interval : 1200
		});
	</script>
</body>

</html>