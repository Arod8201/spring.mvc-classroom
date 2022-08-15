package spring.mvc.session13.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.mvc.session13.entity.Job;
import spring.mvc.session13.repository.EmployeeDao;
import spring.mvc.session13.repository.JobDao;

@Controller
@RequestMapping("/jdbc/job")
public class JobController {

	@Autowired
	private JobDao jobDao;

	@Autowired
	private EmployeeDao employeeDao;
	
	private int getPageCount() {
		int pageCount = (int)Math.ceil((double)jobDao.getCount() / jobDao.LIMIT);
		return pageCount;
	}

	@GetMapping("/")
	public String index(@ModelAttribute Job job, Model model) { // job.jsp首頁
		model.addAttribute("_method", "POST");
		model.addAttribute("jobs", jobDao.query());
		model.addAttribute("employees", employeeDao.query());
		model.addAttribute("pageCount", getPageCount());
		return "session13/job";
	}

	// 查詢
	@GetMapping("/{jid}")
	public String get(@PathVariable("jid") Integer jid, Model model) {
		model.addAttribute("_method", "PUT");
		model.addAttribute("jobs", jobDao.query());
		model.addAttribute("job", jobDao.get(jid));
		model.addAttribute("employees", employeeDao.query());
		model.addAttribute("pageCount", getPageCount());
		return "session13/job";
	}
	
	@GetMapping("/page/{num}")
	public String page(@ModelAttribute Job job, @PathVariable("num") Integer num, Model model) {
		int offset = (num - 1 ) * jobDao.LIMIT;
		model.addAttribute("_method", "POST");
		model.addAttribute("jobs", jobDao.queryPage(offset));
		model.addAttribute("employees", employeeDao.query());
		model.addAttribute("pageCount", getPageCount());
		return "session13/job";
	}
	
	// 新增
	@PostMapping("/")
	public String add(@Valid Job job, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("_method", "POST");
			model.addAttribute("jobs", jobDao.query());
			model.addAttribute("job", job);
			model.addAttribute("employees", employeeDao.query());
			model.addAttribute("pageCount", getPageCount());
			return "session13/employee";
		}
		jobDao.add(job);
		return "redirect:./";
	}

	// 修改
	@PutMapping("/")
	public String update(@Valid Job job, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("_method", "PUT");
			model.addAttribute("jobs", jobDao.query());
			model.addAttribute("job", job);
			model.addAttribute("employees", employeeDao.query());
			model.addAttribute("pageCount", getPageCount());
			return "session13/job";
		}
		jobDao.update(job);
		return "redirect:./";
	}

	// 刪除
	@DeleteMapping("/")
	public String delete(Job job) {
		jobDao.delete(job.getEid());
		return "redirect:./";
	}

}
