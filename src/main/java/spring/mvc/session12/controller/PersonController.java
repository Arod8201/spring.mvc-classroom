package spring.mvc.session12.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.mvc.session12.entity.Person;

@Controller
@RequestMapping("/person")
public class PersonController {
	private List<Person> people = new CopyOnWriteArrayList<Person>();

	@GetMapping("/")
	public String index(Model model, @ModelAttribute Person person) {
		// Person person = new Person();
		// model.addAttribute("person", person);
		model.addAttribute("people", people);
		return "session12/person";
	}

	@PostMapping("/")
	public String add(Model model, @Valid Person person, BindingResult result) {
		System.out.println(person);
		// 驗證結果會存放在 result 中
		if (result.hasErrors()) {
			// 若有錯誤發生, 會自動將錯誤訊息傳送到指定的 view 中
			model.addAttribute("people", people);
			return "session12/person";
		}
		people.add(person);
		return "redirect:./";
	}

}