package spring.mvc.session09.controller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toList;

import java.util.LinkedHashMap;

import static java.util.stream.Collectors.groupingBy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/lotto")
public class LottoController {

	private List<Set<Integer>> lottos = new CopyOnWriteArrayList<>();

	// Lotto 主畫面
	// @GetMapping("/")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("lottos", lottos); // 樂透號碼歷史紀錄
		return "session09/lotto";
	}

	// 取得最新電腦選號
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(Model model) {
		// 取得樂透號碼
		Set<Integer> lotto = getRandomLotto();
		// 將最新樂透號碼存放在歷史紀錄中
		lottos.add(lotto);
		// 將必要資料傳給 jsp 呈現/處理
		model.addAttribute("lotto", lotto); // 最新電腦選號
		model.addAttribute("lottos", lottos); // 樂透號碼歷史紀錄
		return "session09/lotto";
	}

	// 更新指定位置的電腦選號
	@RequestMapping(value = "/update/{index}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("index") int index) {
		// 重新取得樂透號碼
		Set<Integer> lotto = getRandomLotto();
		// 更新指定位置的樂透號碼
		lottos.set(index, lotto);
		return "redirect:/mvc/lotto/";
	}

	// 刪除指定位置的電腦選號
	@RequestMapping(value = "/delete/{index}", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("index") int index) {
		// 刪除指定位置的樂透號碼
		lottos.remove(index);
		return "redirect:/mvc/lotto/";
	}

	// 統計運算
	@RequestMapping(value = "/stat", method = RequestMethod.GET)
	public String stat(Model model) {
		// 1. 將所有的資料彙集 (flatMap 將資料拆散並透過 collect 收集)
		List<Integer> nums = lottos.stream()
								   .parallel()
								   .flatMap(lotto -> lotto.stream()) // Stream<Integer>
								   .collect(toList()); 	 // List<Integer> 
		// 2. 透過 groupingBy 將資料分組
		Map<Integer, Long> stat = nums.stream()
									  .parallel()
									  .collect(groupingBy(identity(), counting()));
		
		// 3.加上排序
		Map<Integer, Long> stat2 = new LinkedHashMap<>();
		stat.entrySet().stream()
					   .sorted(Map.Entry.<Integer, Long> comparingByValue().reversed())
					   .forEachOrdered(e -> stat2.put(e.getKey(), e.getValue()));
		
//		// 4.數列排序
//		Map<Integer, Long> stat3 = new LinkedHashMap<>();
//		stat.entrySet().stream()
//					   .sorted(Map.Entry.<Integer, Long> comparingByValue())
//					   .forEachOrdered(e -> stat3.put(e.getKey(), e.getValue()));
		
		model.addAttribute("lotto", null); // 最新電腦選號
		model.addAttribute("lottos", lottos); // 樂透號碼歷史紀錄
		model.addAttribute("stat", stat); // 統計資料
		model.addAttribute("stat2", stat2); // 統計資料
		return "session09/lotto";
	}

	// 隨機生成取得最新電腦選號
	private Set<Integer> getRandomLotto() {
		Random r = new Random();
		// 樂透 539 , 1~39 不重複數字取5個
		Set<Integer> lotto = new LinkedHashSet<>();
		while (lotto.size() < 5) {
			lotto.add(r.nextInt(39) + 1);
		}
		return lotto;
	}

}
