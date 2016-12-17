<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
	<title>Transaction Detail</title>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">	
	<%-- <link rel="stylesheet" href="https://bootswatch.com/paper/bootstrap.min.css"> --%>
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
	<div class="page-header">
		<h2>Order Details</h2>
	</div>
	
	<label for="id">Order #</label>
	<p id="id">${id}</p>
	<label for="name">Full Name</label>
	<p id="name">${name}</p>
	<label for="email">Email Address</label>
	<p id="email">${email}</p>

	<fmt:setLocale value="en-US" />
	<label for="order">Purchased Items</label>
	<table id="order" class="table table-hover table-striped table-bordered">
		<thead>
			<tr>
				<th>Item</th>
				<th>Name</th>
				<th>Qty</th>
				<th>Price</th>
				<th>Total Price</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${items}" var="item">
				<tr>
					<td><img alt="Image not found" src="DisplayImage?id=${item.id}" height="32"></td>
					<td>${item.name}</td>
					<td>${item.quantity}</td>
					<td>
						<fmt:formatNumber
						value="${item.price}"
						type="currency" />
					</td>
					<td>
						<fmt:formatNumber
						value="${item.price * item.quantity}"
						type="currency" />
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
  			<tr>
   				<th colspan="4" class="table-borderless text-right">Subtotal</th>
   				<td>
   					<fmt:formatNumber
					value="${subtotal}"
					type="currency" />
   				</td>
  			</tr>
  			<tr>
   				<th colspan="4" class="table-borderless text-right">Tax (8.25%)</th>
   				<td>
   					<fmt:formatNumber
					value="${subtotal * 0.0825}"
					type="currency" />
   				</td>
  			</tr>
  			<tr>
   				<th colspan="4" class="table-borderless text-right">Grand Total</th>
   				<td>
   					<fmt:formatNumber
					value="${subtotal * 1.0825}"
					type="currency" />
   				</td>
  			</tr>
 		</tfoot>
	</table>
	
	<br>
	
	<a href="History" class="btn btn-warning">Go Back</a>
</div>

</body>
</html>
