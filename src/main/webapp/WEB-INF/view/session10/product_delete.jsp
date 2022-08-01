<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@2.1.0/build/pure-min.css">
		<meta charset="UTF-8">
		<title>Product 刪除表單</title>
		<!--  
		<script>
			function checkDelete() {
				return confirm('是否要刪除');
			}
		</script>
		-->
	</head>
	<body style="padding: 15px">
		<form class="pure-form" method="post" onsubmit="return confirm('是否要刪除');" action="/spring.mvc/mvc/product/${ index }">
			<fieldset>
				<legend>Product 刪除表單</legend>
				<input type="hidden" name="_method" id="_method" value="DELETE">
				<input type="text" readonly placeholder="請輸入商品名稱" value="${ product.productName }" id="productName" name="productName"><p />
				<input type="number" readonly placeholder="請輸入商品數量" value="${ product.quantity }" id="quantity" name="quantity"><p />
				<input type="number" readonly placeholder="請輸入商品價格" value="${ product.price }" id="price" name="price"><p />
				<button type="submit" class="pure-button pure-button-primary">DELETE</button>
			</fieldset>	
		</form>
	</body>	
</html>