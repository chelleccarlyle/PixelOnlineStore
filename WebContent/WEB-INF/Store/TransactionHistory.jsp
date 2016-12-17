<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Pixel Store Transaction History</title>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/store.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<style>
	.table {
    	border-bottom:0px !important;
	}
	.table th, .table td {
    	border: 1px !important;
	}
	.fixed-table-container {
    	border:0px !important;
	}
	</style>
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
      <a class="navbar-brand" href="Store">PIXEL</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="ShoppingCart"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> Shopping Cart <c:if test="${not empty cart and fn:length(cart) > 0}"><span class="badge">${fn:length(cart)}</span></c:if></a></li>
       	<li><a href="Inventory">Inventory</a></li>
       	<li class="active"><a href="#">Transaction History</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div class="container">

	<!-- Page Header -->
	<div class="page-header">
		<h2 style="text-align:center;">Transaction History</h2>
	</div>
	
	<!-- If there are no items -->
	<c:if test="${empty orders}">
		<div class="jumbotron">
			<h1>There are no transactions.</h1>
		</div>
	</c:if>
	
	<c:if test="${not empty orders}">
			<table class="table table-striped">
  				<thead>
  					<tr>
  						<th>Order ID</th>
  						<th>Customer Name</th>
  						<th>E-Mail</th>
  						<th>Items (Qty)</th>
  						<th>Total</th>
  						<th>Order Date</th>
  						<th>Action</th>
  					</tr>
  				</thead>
  				<tbody>
  					<c:forEach items="${orders}" var="order">
  						<tr>
  							<td>${order.orderId}</td>
  							<td>${order.name}</td>
  							<td>${order.email}</td>
  							<td>${order.quantity} (${order.totalQty})</td>
  							<td><fmt:formatNumber value="${order.price * 1.0825}" type="currency" /></td>
  							<td>${order.date}</td>
  							<td><a href="History?id=${order.orderId}" class="btn btn-info btn-xs">View</a></td>
  						</tr>
  					</c:forEach>
  				</tbody>		
		</table>
	</c:if>
	
</div>
</body>
</html>	