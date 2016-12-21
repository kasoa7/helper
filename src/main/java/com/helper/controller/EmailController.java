package com.helper.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.helper.service.EmailService;
import com.helper.util.CalendarEvents;
import com.helper.util.UserUtils;

import com.helper.domain.EmailVO;

@Controller
@RequestMapping("/email/*")
public class EmailController {
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

	@Inject
	private EmailService service;
	private final GoogleAuthHelper helper = new GoogleAuthHelper();

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String loginGET() {
		return "/diet/googlelogin";
	}

	@RequestMapping(value = "/tmpLogin", method = RequestMethod.GET)
	public String loginTempGET() {
		return "/diet/tmpLogin";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET(EmailVO email, Model model) throws Exception {
		logger.info("register get .............");
	}

	/*
	 * 로그인 과정에서 첫 로그인 시 DB저장, 아니라면 DB 내용 호출
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(HttpServletRequest req) throws Exception {
		EmailVO vo = new EmailVO();
		vo.setEmailAddress(UserUtils.getInstance().getEmailAddress().replaceAll("\"", ""));

		String email = service.getEmailByString(vo.getEmailAddress());
		if (email == null) {
			email = new String();
		}
		if (!email.equals(vo.getEmailAddress())) {
			service.regist(vo);
		}
		req.getSession().setAttribute("user", vo.getEmailAddress());
		CalendarEvents.getInstance().setCalendar_flag(true);

		return "OK";
	}

	/*
	 * Calendar Event Save
	 */
	@ResponseBody
	@RequestMapping(value = "/newCalendarEvent", method = RequestMethod.POST)
	public String newCalendarEventPOST(@RequestBody Object data) throws Exception {
		String[] str = data.toString().replaceAll("\\{", "").replaceAll("\\}", "").split(",");
		str[0] = str[0].trim().replaceAll("title=", "");
		str[1] = str[1].trim().replaceAll("start=", "");
		str[2] = str[2].trim().replaceAll("end=", "");
		str[3] = str[3].trim().replaceAll("location=", "");
		str[4] = str[4].trim().replaceAll("description=", "");

		CalendarEvents.getInstance().setCalendar_flag(true);

		if (!helper.insertEventToCalendar(str)) {
			return "FAIL";
		}
		return "OK";
	}

	/*
	 * Calendar Event Update
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCalendarEvent", method = RequestMethod.POST)
	public String updateCalendarEventPOST(@RequestBody Object data) throws Exception {
		String[] str = data.toString().replaceAll("\\{", "").replaceAll("\\}", "").split(",");
		str[0] = str[0].trim().replaceAll("title=", "");
		str[1] = str[1].trim().replaceAll("start=", "");
		str[2] = str[2].trim().replaceAll("end=", "");
		str[3] = str[3].trim().replaceAll("location=", "");
		str[4] = str[4].trim().replaceAll("description=", "");
		str[5] = str[5].trim().replaceAll("id=", "");

		CalendarEvents.getInstance().setCalendar_flag(true);

		if (!helper.updateEventToCalendar(str)) {
			return "FAIL";
		}

		return "OK";
	}

	/*
	 * Calendar Event Delete
	 */
	@ResponseBody
	@RequestMapping(value = "/removeCalendarEvent", method = RequestMethod.POST)
	public String removeCalendarEventPOST(@RequestBody Object data) throws Exception {
		String str = data.toString().replaceAll("\\{id=", "").replaceAll("\\}", "");

		CalendarEvents.getInstance().setCalendar_flag(true);

		if (!helper.deleteEventToCalendar(str)) {
			return "FAIL";
		}

		return "OK";
	}


	//나이 입력받아서 DB저장
	@ResponseBody
	@RequestMapping(value = "/age", method = RequestMethod.POST)
	public String registAgePOST(@RequestBody String age) throws Exception {
		EmailVO vo = new EmailVO();
		vo.setEmailAddress(UserUtils.getInstance().getEmailAddress().replaceAll("\"", ""));
		vo.setAge(Integer.parseInt(age.replaceAll("=", "")));

		try {
			service.updateAge(vo);
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}

	}
}