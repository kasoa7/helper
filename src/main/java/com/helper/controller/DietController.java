package com.helper.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.helper.service.DietService;
import com.helper.domain.DietVO;

@Controller
@RequestMapping("/Diet/*")
public class DietController{
   private static final Logger logger = LoggerFactory.getLogger(DietController.class);
   
   @Inject
   private DietService service;
      
   @RequestMapping(value="/register",method = RequestMethod.GET)
   public void registerGET(DietVO diet, Model model)throws Exception{
      logger.info("register get .............");
   }
   
   @RequestMapping(value="/register",method=RequestMethod.POST)
   public String registPOST(DietVO diet, RedirectAttributes rttr)throws Exception{
      logger.info("regist post...............");
      logger.info(diet.toString());
      
      service.regist(diet);
      
      //return "/member/success";
      rttr.addFlashAttribute("msg","success");
      return "redirect:/diet/listAll";
   }
   
   @RequestMapping(value="/listAll", method=RequestMethod.GET)
   public void listAll(Model model)throws Exception{
	   logger.info("show all list...............");
	  // List<MemberVO> test = service.listAll();
	  //model.addAttribute("list",test);
	   model.addAttribute("list",service.listAll());
   }
   
   @RequestMapping(value="/read", method= RequestMethod.GET)
   public void read(@RequestParam("emailAddress") String emailAddress, Model model) throws Exception{
	   model.addAttribute(service.read(emailAddress));
   }
   
   @RequestMapping(value="/remove", method= RequestMethod.POST)
   public String remove(@RequestParam("emailAddress") String emailAddress, RedirectAttributes rttr)throws Exception{
	   service.remove(emailAddress);
	   
	   rttr.addFlashAttribute("msg","success");
	   
	   return "redirect:/diet/listAll";
   }
   
   @RequestMapping(value="/modify", method=RequestMethod.GET)
   public void modifyGET(String emailAddress,Model model)throws Exception{
	   model.addAttribute(service.read(emailAddress));
   }
   
   @RequestMapping(value="/modify", method=RequestMethod.POST)
   public String modifyPOST(DietVO diet, RedirectAttributes rttr) throws Exception{
	   logger.info("mod post................");
	   
	   service.modify(diet);
	   rttr.addFlashAttribute("msg","SUCCESS");
	   
	   return "redirect:/diet/listAll";
   }
}