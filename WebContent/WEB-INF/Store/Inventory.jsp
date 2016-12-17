<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Store Inventory</title>
	<!-- Latest compiled and minified CSS -->
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
      <a class="navbar-brand" href="Store">PIXEL</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="ShoppingCart"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> Shopping Cart <c:if test="${not empty cart and fn:length(cart) > 0}"><span class="badge">${fn:length(cart)}</span></c:if></a></li>
       	<li class="active"><a href="#">Inventory</a></li>
       	<li><a href="History">Transaction History</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div class="container-fluid">

	<!-- Page Header -->
	<div class="page-header">
		<h2>Store Inventory <small>[<a href="AddStoreItem">Add Item</a>]</small></h2>
	</div>
	
	<!-- If there are no items -->
	<c:if test="${empty items}">
		<div class="jumbotron">
			<h1>There are no items.</h1>
			<img src="${pageContext.request.contextPath}/images/globe.png" alt="globe" class="img-rounded">
		</div>
	</c:if>
	
	<c:if test="${not empty items}">
		<!-- Table -->
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>Image</th>
					<th>Name</th>
					<th>Description</th>
					<th>Quantity</th>
					<th>Price</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${items}" var="item">
					<tr>
						<td><img alt="Image not found" src="DisplayImage?id=${item.id}" height="100"></td>
						<td>${item.name}</td>
						<td>${item.description}</td>
						<td>${item.quantity}</td>
						<td><fmt:formatNumber value="${item.price}" type="currency" /></td>
						<td>
							
							<%-- Construct Edit URL --%>
							<c:url value="EditStoreItem" var="editItem">
								<c:param name="id" value="${item.id}"/>
							</c:url>
							
							<div class="row">
								<div class="col-md-2"><a href="${editItem}" class="btn btn-primary">Edit</a></div>
							</div>
							
							<%-- Construct Remove URL --%>
							<c:url value="RemoveStoreItem" var="removeItem">
							   <c:param name="id" value="${item.id}"/>
							</c:url>
							
							<div class="row">
								<div class="col-md-2">
								<a href="${removeItem}" class="btn btn-danger">Remove</a>
								</div>
							</div>
							
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>
</body>
</html>