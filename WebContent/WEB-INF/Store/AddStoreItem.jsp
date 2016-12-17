<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Add Item</title>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">	
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

<div class="container">

	<!-- Page Header -->
	<div class="page-header">
		<h2>Add an Item</h2>
	</div>
	
	<form action="AddStoreItem" method="post" enctype="multipart/form-data">
		
		<div class="form-group">
			<label for="name">Name</label>
			<input type="text" class="form-control" id="name" name="name" value="${name}" placeholder="Enter the name of the item">
		</div>
		<c:if test="${not empty errorName}">
			<p class="well-sm bg-danger">${errorName}</p>
		</c:if>
		
		<div class="form-group">
			<label for="description">Description</label>
			<textarea class="form-control" rows="3" id="description" name="description" placeholder="Enter the description of the item">${description}</textarea>
		</div>
		<c:if test="${not empty errorDescription}">
			<p class="well-sm bg-danger">${errorDescription}</p>
		</c:if>
		
		<div class="form-group">
			<label for="quantity">Quantity</label>
			<input type="text" class="form-control" id="quantity" name="quantity" value="${quantity}">
		</div>
		<c:if test="${not empty errorQuantity}">
			<p class="well-sm bg-danger">${errorQuantity}</p>
		</c:if>
		
		<div class="form-group">
			<label for="price">Price</label>
			<input type="text" class="form-control" id="price" name="price" value="${price}">
		</div>
		<c:if test="${not empty errorPrice}">
			<p class="well-sm bg-danger">${errorPrice}</p>
		</c:if>
		
		<!-- File upload field -->
		<div class="form-group">
    		<label for="exampleInputFile">Upload Image</label>
    		<input type="file" id="exampleInputFile" name="image">
    		<p class="help-block">Please upload .jpg, .png (Max: 256KB)</p>
  		</div>
  		<c:if test="${not empty errorImageType}">
			<p class="well-sm bg-danger">${errorImageType}</p>
		</c:if>
		<c:if test="${not empty errorFileSize}">
			<p class="well-sm bg-danger">${errorFileSize}</p>
		</c:if>
		
		<div class="col-xs-4">
		<button type="submit" class="btn btn-success btn-block">Submit</button>	
		</div>
		
		<a href="Inventory" class="btn btn-danger">Cancel</a>
		
	</form>
	
</div>
</body>
</html>	