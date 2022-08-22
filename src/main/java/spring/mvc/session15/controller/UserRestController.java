package spring.mvc.session15.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import spring.mvc.session15.entity.User;
import spring.mvc.session15.repository.UserRepository;

@RestController // 會在每一個方法自動加上 @ResponseBody
@RequestMapping("/rest/user")
public class UserRestController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/auto_add") // 自動新增 user 資料
	public String addAuto() {
		Faker faker = new Faker();
		User user = new User();
		user.setName(faker.name().firstName());
		user.setPassword(String.format("%04d", new Random().nextInt(1000)));
		user.setBirth(faker.date().birthday());
		userRepository.save(user);
		// return userRepository.findAll();
		return user.toString();
	}

	// 查詢
	@GetMapping("/")
	public List<User> query() {
		return userRepository.findAll();
	}

	@GetMapping("/{name}")
	public User getByName(@PathVariable("name") String name) {
		return userRepository.getByName(name);
	}

}
