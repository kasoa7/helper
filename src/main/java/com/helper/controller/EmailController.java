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
import com.helper.service.EmailService;
import com.helper.domain.EmailVO;

@Controller
@RequestMapping("/email/*")
public class EmailController{
   private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
   
   @Inject
   private EmailService service;
   
   @RequestMapping(value="/register",method = RequestMethod.GET)
   public void registerGET(EmailVO email, Model model)throws Exception{
      logger.info("register get .............");
   }
   
   @RequestMapping(value="/register",method=RequestMethod.POST)
   public String registPOST(EmailVO email, RedirectAttributes rttr)throws Exception{
      logger.info("regist post...............");
      logger.info(email.toString());
      
      service.regist(email);
      
      //return "/member/success";
      rttr.addFlashAttribute("msg","success");
      return "redirect:/email/listAll";
   }
   
   @RequestMapping(value="/listAll", method=RequestMethod.GET)
   public void listAll(Model model)throws Exception{
	   logger.info("show all list...............");
	  // List<EmailVO> test = service.listAll();
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
	   
	   return "redirect:/email/listAll";
   }
   
   @RequestMapping(value="/modify", method=RequestMethod.GET)
   public void modifyGET(String emailAddress,Model model)throws Exception{
	   model.addAttribute(service.read(emailAddress));
   }
   
   @RequestMapping(value="/modify", method=RequestMethod.POST)
   public String modifyPOST(EmailVO email, RedirectAttributes rttr) throws Exception{
	   logger.info("mod post................");
	   
	   service.modify(email);
	   rttr.addFlashAttribute("msg","SUCCESS");
	   
	   return "redirect:/email/listAll";
   }
}