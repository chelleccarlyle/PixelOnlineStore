<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
	<title>Pixel Shopping Cart</title>
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
        <li class="active"><a href="#"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> Shopping Cart <c:if test="${not empty cart and fn:length(cart) > 0}"><span class="badge">${fn:length(cart)}</span></c:if></a></li>
       	<li><a href="Inventory">Inventory</a></li>
       	<li><a href="History">Transaction History</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div class="container">

	<!-- Page Header -->
	<div class="page-header">
		<h2>Pixel Shopping Cart</h2>
	</div>

	<c:if test="${empty cart}">
		<div class="jumbotron">
			<h1>Uh-Oh! <small>Your shopping cart is empty.</small></h1>
		</div>
	</c:if>

	<c:if test="${not empty cart}">
	<fmt:setLocale value="en-US" />
	<table class="table table-hover table-striped table-bordered">
		<thead>
			<tr>
				<th>Item</th>
				<th>Name</th>
				<th>Qty</th>
				<th>Price</th>
				<th>Total Price</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cart}" var="item">
				<tr>
					<td><img alt="Image not found" src="DisplayImage?id=${item.id}" height="32"></td>
					<td>${item.name}</td>
					<td>
						<form action="ShoppingCart" method="POST">
							<input type="hidden" name="action" value="update" />
							<input type="hidden" name="id" value="${item.id}" />
							<input type="text" name="selectedQty" value="${item.selectedQty}" />
							<button type="submit" class="btn btn-success btn-xs">Update</button>
						</form>
					</td>
					<td>
						<fmt:formatNumber
						value="${item.price}"
						type="currency" />
					</td>
					<td>
						<fmt:formatNumber
						value="${item.price * item.selectedQty}"
						type="currency" />
					</td>
					<td>
						<form action="ShoppingCart" method="POST">
							<input type="hidden" name="action" value="remove" />
							<input type="hidden" name="id" value="${item.id}" />
							<button type="submit" class="btn btn-danger btn-xs">Delete</button>
						</form>
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
	</c:if>
	
	<br>
	<c:if test="${not empty cart}">
	<a href="Checkout" class="btn btn-info">Proceed to Checkout</a>
	</c:if>
</div>

</body>
</html>
