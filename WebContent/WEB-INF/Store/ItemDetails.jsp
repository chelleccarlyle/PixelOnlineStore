<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${currentItem.name} - Pixel Store</title>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/item-details.css">
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
       	<li><a href="Inventory">Inventory</a></li>
       	<li><a href="History">Transaction History</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div class="container-fluid">

	<div class="col-md-4 col-md-offset-1">
		<div class="row item-name">
		<h2>${currentItem.name}</h2>
		</div>
		<div class="row">
		<img alt="Image not found" src="DisplayImage?id=${currentItem.id}" width="500">
		</div>
	</div>
	<div class="col-md-4 col-md-offset-2 item-details">
		<div class="item-detail">
		<a class="btn btn-primary btn-lg btn-block" href="ShoppingCart?action=add&id=${currentItem.id}" role="button">Add to Cart</a>
		</div>
		<div class="item-price"><fmt:formatNumber value="${currentItem.price}" type="currency" /></div>
		<small>Quantity Available: ${currentItem.quantity}</small>
		<div class="item-detail item-description">
		<p class="lead">${currentItem.description}</p>
		</div>
	</div>
	
</div>

</body>
</html>	