package spring.mvc.session08.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.mvc.session08.entity.User;

@RequestMapping("/mv")
@Controller
public class MVController {

	@GetMapping("/case1")
	// @RequestMapping(value = "/case1",method = {RequestMethod.GET}) // 一樣的意思
	public ModelAndView case1() {
		String data = "Hello Model and View"; // 資料(Model)
		String view = "/WEB-INF/view/show_data.jsp"; // 選染地(View)
		ModelAndView mv = new ModelAndView();
		mv.addObject("data", data);
		mv.setViewName(view);
		return mv;
	}

	@GetMapping("/case2")
	public ModelAndView case2() {
		String data = "Hello Model and View II"; // 資料(Model)
		String view = "/WEB-INF/view/show_data.jsp"; // 選染地(View)
		return new ModelAndView(view, "data", data);
	}

	@GetMapping("/case3")
	public ModelAndView case3() {
		ModelAndView mv = new ModelAndView();
		String data = "Hello Model and View II"; // 資料(Model)
		mv.setViewName("/WEB-INF/view/show_data.jsp"); // 選染地(View)
		mv.addObject("data", "Hello Model and View I");
		mv.addObject("data2", "Hello Model and View II");
		mv.addObject("data3", "Hello Model and View III");
		return mv;
	}

	// 宣告 Model 參數 , 並調用 addAttribute()
	// 回傳值就是 view 的路徑 (String 型態)
	// 要配置 springmvc-servlet.xml
	@GetMapping("/case4")
	public String case4(Model model) {
		model.addAttribute("data", "Hello Model and View I");
		model.addAttribute("data2", "Hello Model and View II");
		model.addAttribute("data3", "Hello Model and View III");
		return "show_data"; // 取代 /WEB-INF/view/show_data.jsp
	}

	// Model 是數組/集合資料: List, Map etc...
	@GetMapping("/case5")
	public String case5(Model model) {
		List<String> names = Arrays.asList("John", "Mary", "Helen");
//		names=new ArrayList<>(names);
//		names.add("Bob");
		Map<String, Integer> fruits = new LinkedHashMap<>();
		fruits.put("watermelon", 100);
		fruits.put("mango", 80);
		model.addAttribute("data", names);
		model.addAttribute("data2", fruits);
		model.addAttribute("data3", new User());
		return "show_data";
	}

	// 重定向
	// redirect: 由 server 端發出重定向命令(放在 header 中) 由 client 端去執行
	// 不論內網或外網都可以指派
	@GetMapping("/case6")
	public String case6() {
		return "redirect:/index.jsp"; // 重定向首頁
	}

	@GetMapping("/case7")
	public String case7() {
		//http://localhost:8080/spring.mvc/mvc/mv/case7
		//http://localhost:8080/spring.mvc/mvc/hello/welcome
		return "redirect:../hello/welcome"; // 重定向到其它 controller
	}

}
