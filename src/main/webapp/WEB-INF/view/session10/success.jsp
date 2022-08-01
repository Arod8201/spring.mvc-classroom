<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@2.1.0/build/pure-min.css">
		<meta charset="UTF-8">
		<title>Success</title>
	</head>
	<body style="padding: 15px">
		<form class="pure-form">
			<fieldset>
				<legend>Success</legend>
				商品名稱: ${ product.productName }<p />
				商品數量: ${ product.quantity }<p />
				商品價格: ${ product.price }<p />
				<button type="button" 
						onclick="window.location.href='/spring.mvc/mvc/product/';"
						class="pure-button pure-button-primary">Back</button>
			</fieldset>	
		</form>
	</body>
</html>