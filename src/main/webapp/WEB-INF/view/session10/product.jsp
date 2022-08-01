<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>        
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@2.1.0/build/pure-min.css">
		<meta charset="UTF-8">
		<title>Product</title>
	</head>
	<body style="padding: 15px">
		<form class="pure-form" method="post" action="/spring.mvc/mvc/product/">
			<fieldset>
				<legend>Product 表單</legend>
				<input type="text" placeholder="請輸入商品名稱" id="productName" name="productName"><p />
				<input type="number" placeholder="請輸入商品數量" id="quantity" name="quantity"><p />
				<input type="number" placeholder="請輸入商品價格" id="price" name="price"><p />
				<button type="submit" class="pure-button pure-button-primary">Add</button>
			</fieldset>	
		</form>
		
		<form class="pure-form">
			<fieldset>
				<legend>Product 列表</legend>
				<table class="pure-table pure-table-bordered">
					<thead>
						<th>序號</th>	
						<th>商品</th>	
						<th>商品數量</th>	
						<th>商品價格</th>	
						<th>商品小記</th>	
						<th>修改</th>	
						<th>刪除</th>	
					</thead>
					<tbody>
						<c:forEach varStatus="status" var="procuct" items="${ products }">
							<tr>
								<td>${ status.index }</td>
								<td>${ product.productName }</td>
								<td>${ product.quantity }</td>
								<td>
									<fmt:formatNumber type="currency" value="${ product.price }" />
								</td>
								<td>
									<!-- <fmt:formatNumber pattern="####,####.0" value="${ product.quantity * product.price }" /> -->
									<fmt:formatNumber type="currency" value="${ product.quantity * product.price }" />
								</td>
								<td>
									<button type="button"
											onclick="window.location.href='/spring.mvc/mvc/product/${ status.index }';"
											class="pure-button pure-button-primary">Update</button>
								</td>
								<td>
									<button type="button"
											onclick="window.location.href='/spring.mvc/mvc/product/${ status.index }?delete=true';"
											class="pure-button pure-button-primary">Delete</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</fieldset>	
		</form>
	</body>
</html>