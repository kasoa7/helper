package com.helper.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.helper.service.DietService;
import com.helper.util.CalendarEvents;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.helper.domain.BreakfastVO;
import com.helper.domain.DietVO;
import com.helper.domain.DinnerVO;
import com.helper.domain.LaunchVO;
import com.helper.domain.TotalkcalVO;

@Controller
@RequestMapping("/diet/*")
public class DietController {
	private static final Logger logger = LoggerFactory.getLogger(DietController.class);

	@Inject
	private DietService service;
	
	
	/*
	 * Email을 세션으로부터 받아와, DB에 저장된 식단 정보를 불러온다
	 * Chart를 그려주기 위해 json객체 생성
	 */
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public String FullCalendarGET(HttpServletRequest req, Model model) throws Exception {
		logger.info("register get .............");
		String userEmail = (String) req.getSession().getAttribute("user");

		JSONObject jsonObject = new JSONObject();
		JSONArray chartArray = new JSONArray();
		JSONObject chartInfo = new JSONObject();

		List<DietVO> vo = new ArrayList<DietVO>();
		vo = service.read(userEmail);

		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (int i = 0; i < vo.size(); i++) {
			chartInfo.put("date", transFormat.format(vo.get(i).getMealDays()));
			chartInfo.put("Bmenu", vo.get(i).getBmenu());
			chartInfo.put("Bkcal", vo.get(i).getBkcal());
			chartInfo.put("Lmenu", vo.get(i).getLmenu());
			chartInfo.put("Lkcal", vo.get(i).getLkcal());
			chartInfo.put("Dmenu", vo.get(i).getDmenu());
			chartInfo.put("Dkcal", vo.get(i).getDkcal());
			chartArray.add(chartInfo);
			chartInfo = new JSONObject();
		}
		jsonObject.put("chartJSON", chartArray);

		ArrayList<TotalkcalVO> totalkcal = new ArrayList<TotalkcalVO>();
		totalkcal = (ArrayList<TotalkcalVO>) service.TotalkcallistAll(userEmail);

		ArrayList<BreakfastVO> breakfastmenu = new ArrayList<BreakfastVO>();
		breakfastmenu = (ArrayList<BreakfastVO>) service.BreakfastlistAll(userEmail);

		ArrayList<LaunchVO> launchmenu = new ArrayList<LaunchVO>();
		launchmenu = (ArrayList<LaunchVO>) service.LaunchlistAll(userEmail);

		ArrayList<DinnerVO> dinnermenu = new ArrayList<DinnerVO>();
		dinnermenu = (ArrayList<DinnerVO>) service.DinnerlistAll(userEmail);

		for (int i = 0; i < breakfastmenu.size(); i++) {
			breakfastmenu.get(i)
					.setTitle(breakfastmenu.get(i).getTitle() + "   " + breakfastmenu.get(i).getTitle2() + "kcal");
		}
		for (int i = 0; i < launchmenu.size(); i++) {
			launchmenu.get(i).setTitle(launchmenu.get(i).getTitle() + "   " + launchmenu.get(i).getTitle2() + "kcal");
		}
		for (int i = 0; i < dinnermenu.size(); i++) {
			dinnermenu.get(i).setTitle(dinnermenu.get(i).getTitle() + "   " + dinnermenu.get(i).getTitle2() + "kcal");
		}

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String total = gson.toJson(totalkcal);
		String breakfast = gson.toJson(breakfastmenu);
		String launch = gson.toJson(launchmenu);
		String dinner = gson.toJson(dinnermenu);

		model.addAttribute("totalkcal", total);
		model.addAttribute("bmenuobject", breakfast);
		model.addAttribute("lmenuobject", launch);
		model.addAttribute("dmenuobject", dinner);
		model.addAttribute("jsonObject", jsonObject);
		return "/diet/calendar";
	}

	/*
	 * 날짜 클릭시 식단 정보 불러오기 위함
	 */
	@ResponseBody
	@RequestMapping(value = "/calendar", method = RequestMethod.POST)
	public DietVO FullCalendarPOST(HttpServletRequest req) throws Exception {
		DietVO vo = new DietVO();
		String emailAddress = req.getParameter("emailAddress");
		String MealDays = req.getParameter("MealDays");

		Date transMealDays = Date.valueOf(MealDays);

		DietVO test = service.read(emailAddress, transMealDays);
		CalendarEvents.getInstance().setCalendar_flag(false);
		try {
			if (test != null) {
				return test;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET(DietVO diet, Model model) throws Exception {
		logger.info("register get .............");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(DietVO diet) throws Exception {
		logger.info("regist post...............");
		logger.info(diet.toString());

		service.regist(diet);

		return "redirect:/diet/calendar";
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("emailAddress") String emailAddress, @RequestParam("MealDays") Date MealDays,
			Model model) throws Exception {
		model.addAttribute(service.read(emailAddress, MealDays));
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(@RequestParam("emailAddress") String emailAddress, @RequestParam("MealDays") Date MealDays,
			RedirectAttributes rttr) throws Exception {
		service.remove(emailAddress, MealDays);

		rttr.addFlashAttribute("msg", "success");

		return "redirect:/diet/calendar";
	}
}