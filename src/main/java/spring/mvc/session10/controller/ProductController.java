package spring.mvc.session10.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import spring.mvc.session10.entity.Product;

@Controller
@RequestMapping("/product")
public class ProductController {

	private List<Product> products = new CopyOnWriteArrayList<>();

	{
		products.add(new Product("Java", 100, 50.0));
		products.add(new Product("Python", 80, 350.0));
		products.add(new Product("MySQL", 120, 750.0));
	}

	// 商品首頁(輸入商品 , 查詢所有商品)
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("products", products);
		return "session10/product";
	}

	// 新增商品
	@PostMapping("/")
	public String add(Product product, RedirectAttributes attr) {
		products.add(product);
		// 將 product 資料傳給 /addOk 讓 success.jsp 來呈現 (防止網頁重新整理導致二次 submit)
		attr.addFlashAttribute("product", product);
		return "redirect:addOk";
	}

	// 成功
	@GetMapping(value = { "/addOk", "/updateOk" })
	public String success() {
		return "session10/success";
	}

	// 查詢單一商品]
	@GetMapping("/{index}")
	public String get(@PathVariable("index") int index, Model model,
					  @RequestParam(name = "delete", required = false, defaultValue = "false") boolean delete) {
		Product product = products.get(index);
		model.addAttribute("product", product);
		model.addAttribute("index", index);
		return delete ? "session10/product_delete" : "session10/product_update";
	}

	// 修改商品
	@PutMapping("/{index}")
	public String update(@PathVariable("index") int index, Product product, RedirectAttributes attr) {
		products.set(index, product);
		attr.addFlashAttribute("product", product);
		return "redirect:updateOk";
	}

	// 刪除商品
	@DeleteMapping("/{index}")
	public String delete(@PathVariable("index") int index) {
		products.remove(index);
		return "redirect:./";
	}

}
