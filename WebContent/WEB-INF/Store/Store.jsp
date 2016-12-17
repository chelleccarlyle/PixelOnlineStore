<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Pixel Store Main Page</title>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/store.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>	
</head>
<body>
<nav class="navbar navbar-inverse">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">PIXEL</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <form class="navbar-form navbar-left" action="Store" method="get">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search" name="query">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="ShoppingCart"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> Shopping Cart <c:if test="${not empty cart and fn:length(cart) > 0}"><span class="badge">${fn:length(cart)}</span></c:if></a></li>
       	<li><a href="Inventory">Inventory</a></li>
       	<li><a href="History">Transaction History</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<!-- Image 1 -->
<div class="bgimg-1">
  <div class="caption">
    <span class="border">WELCOME TO PIXEL</span>
  </div>
</div>

<div class="container">

	<!-- Page Header -->
	<div class="page-header">
		<h2 style="text-align:center;">Latest Items</h2>
	</div>
	
	<!-- If there are no items -->
	<c:if test="${empty items}">
		<div class="jumbotron">
			<h1>There are no items.</h1>
		</div>
	</c:if>
	
	<c:if test="${not empty items}">
		<%-- <c:forEach items="${items}" var="item">
			<div class="row">
 				<div class="col-xs-9"><img alt="Image not found" src="DisplayImage?id=${item.id}" height="100"></div>
  				<div class="col-xs-4">
  					<h3>${item.name}</h3>
  					$${item.price}
  				</div>
			</div>
		</c:forEach> --%>
		
		<div class="row top-buffer">
			
		<c:forEach items="${items}" var="item" varStatus="loop">

			<!-- If element is 4th element, start a new row -->
			<c:if test="${not loop.first and loop.index % 4 == 0}"> 
           	
        </div>                    
        <div class="row top-buffer">                
        	     
            </c:if>
            
            <!-- Display item -->
            <div class="col-md-3">
            
            	<%-- Construct Details URL --%>
				<c:url value="Details" var="detailsUrl">
					<c:param name="id" value="${item.id}"/>
				</c:url>
            
            	<a href="${detailsUrl}"><img alt="Image not found" src="DisplayImage?id=${item.id}" height="100"></a>
            	<a href="${detailsUrl}" style="text-decoration: none; color: #111;"><h3>${item.name}</h3></a>
  				<fmt:formatNumber value="${item.price}" type="currency" /> <br>
  				<a class="btn btn-primary add-cart" href="ShoppingCart?action=add&id=${item.id}" role="button">Add to Cart</a>
  			</div>
        
		</c:forEach>
		
		</div>
		
	</c:if>
	
</div>
</body>
</html>	