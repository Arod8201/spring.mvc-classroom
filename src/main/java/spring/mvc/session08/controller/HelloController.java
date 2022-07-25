package spring.mvc.session08.controller;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import spring.mvc.session08.entity.User;

@Controller
@RequestMapping("/hello")
public class HelloController {

	/*
	 * 1.
	 * 執行路徑: http://localhost:8080/spring.mvc/mvc/hello/welcome 
	 * 執行路徑: /mvc/hello/welcome 
	 * /mvc 在 web.xml 中有定義 
	 * /hello 找到 HelloController 
	 * /welcome 執行 welcome() 方法
	 */
	@RequestMapping("/welcome")
	@ResponseBody // 直接將資料回應給前端
	public String welcome() {
		return "Welcome SpringMVC !";
	}
	
	/*
	 * 2. 
	 * ? 後面帶參數 @RequestParam
	 * 執行路徑: /mvc/hello/sayhi?name=John&age=18
	 * */
	@RequestMapping(value = "/sayhi")
	@ResponseBody
	public String sayHi(@RequestParam(value = "name", required = false) String name,
					    @RequestParam(value = "age", defaultValue = "10") Integer age) {
		return String.format("Hi %s %d", name, age);
	}
	
	/*
	 * 3. Lab 練習
	 * 執行路徑: /mvc/hello/bmi?h=170&w=60
	 * 執行結果: bmi = 20.76
	 * */
	// 自行寫法
	@RequestMapping(value = "/bmi")
	@ResponseBody
	public String bmi(@RequestParam(value = "h") Double h,
					  @RequestParam(value = "w") Double w) {
		return String.format(String.valueOf(w / Math.pow(h/100, 2)));
	}	
	// 老師寫法
//	@RequestMapping(value = "/bmi")
//	@ResponseBody
//	public String bmi(@RequestParam(value = "h") Double h,
//					  @RequestParam(value = "w") Double w) {
//		double bmi = w / Math.pow(h/100, 2);
//		return String.format("bmi = %.2f", bmi);
//	}
	
	/*
	 * 4. 同名多參數資料
	 * 執行路徑: /mvc/hello/age?age=18&age=19&age=20
	 * 計算出: 總和 , 平均 , 最大值 , 最小值
	 * */
	@RequestMapping(value = "/age")
	@ResponseBody
	public String age(@RequestParam("age") List<Integer> ageList) {
		// Int 的統計物件
		IntSummaryStatistics stat = ageList.stream()
										   .mapToInt(Integer::intValue)
										   .summaryStatistics();
		return String.format("%s", stat.toString());
	}
	
	/*
	 * 5.Map 參數(form 表單)
	 * 執行路徑: /mvc/hello/person?name=Tomcat&score=95.5&age=18&pass=true
	 * */
	@RequestMapping(value = "/person")
	@ResponseBody
	public String getPerson(@RequestParam Map<String, String> person) {
		return person.toString();
	}
	
	/*
	 * 6. pojo(entity) 參數自動配置
	 * 執行路徑: /mvc/hello/user?name=Tomcat&score=95.5&age=18&pass=true
	 * */
	@RequestMapping(value = "/user")
	@ResponseBody
	public String getUser(User user) {
		return user.toString();
	}
	
	/*
	 * 7. 在 body 中傳送 json 資料
	 * {
	 * 		"name": "John",
	 * 		"age": 18,
	 * 		"score": 90.5,
	 * 		"pasee": true
	 * 
	 * }
	 * 執行路徑: /mvc/hello/create/user
	 * */
	@RequestMapping(value = "/create/user",
					consumes = "application/json;charser=UTF-8",
				    produces = "application/json;charser=UTF-8")
	@ResponseBody
	public User createUser(@RequestBody User user) {
		//user.setAge(10);
		return user;
	}
	
	/*
	 * 8. 路徑參數 @PathVariable
	 * 執行路徑: /mvc/hello/exam/75 -> 印出結果: 75 pass
	 * 執行路徑: /mvc/hello/exam/45 -> 印出結果: 45 fail
	 * */
	@RequestMapping(value = "/exam/{score}")
	@ResponseBody
	public String examScore(@PathVariable("score") Integer score) {
		return String.format("%d %s", score, (score>=60)?"pass":"fail");
	}
	
	/*
	 * 9. 路徑參數 @PathVariable (萬用字元: * 任意多字, ? 任意一字 )
	 * 執行路徑: /mvc/hello/any/abc/java8
	 * 執行路徑: /mvc/hello/any/defgh/java9
	 * */
	@RequestMapping(value = "/any/*/java?")
	@ResponseBody
	public String any() {
		return "Hello any";
	}
	
	/*
	 * 10. @RequestParam + @PathVariable (Lab 練習)
	 * 子路徑：/mvc/hello/calc/add?x=30&y=20  -> Result：50
	 * 子路徑：/mvc/hello/calc/sub?x=30&y=20  -> Result：10
	 * 子路徑：/mvc/hello/calc/sub?y=20       -> Result：-20
	 * 子路徑：/mvc/hello/calc/add            -> Result：0
	 */
	// 請設計方法 api
	@RequestMapping(value = "/calc/{exp}")
	@ResponseBody
	public String calcExp(@PathVariable("exp") String exp,
					 	  @RequestParam(value = "x", required = false, defaultValue = "0") Integer x,
					 	 @RequestParam(value = "y", required = false, defaultValue = "0") Integer y) {
		int result = 0;
		switch (exp) {
		  case "add":
			  result = x + y;
			  break;
		  case "sub":
			  result = x - y;
			  break;

		default:
			  return "exp path value error!";
		}
		return String.format("Result: %d", result);
	}
	
}
