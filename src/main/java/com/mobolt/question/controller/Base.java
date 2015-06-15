package com.mobolt.question.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mobolt.mercury.dtos.core.QuestionDTO;
import com.mobolt.question.model.ExtractQuestions;
import com.mobolt.utils.JsonUtils;

@Controller
public class Base {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView home()
  {
    return new ModelAndView("/WEB-INF/views/index.html");
  }

//  @RequestMapping(value = "/doExtract", method = RequestMethod.GET , params = "extract")
//  public String extract(@ModelAttribute("inputDto") InputDto inputDto) {
//    System.out.println("inside Extractions: " + inputDto.getUrl());
//    
//    String url = inputDto.getUrl();
//    
////    System.setProperty(SystemProperties.WEB_DRIVER_CHROME,
////            SystemProperties.WEB_DRIVER_CHROME_PATH);
////        WebDriver driver = new ChromeDriver();
////        
////        driver.get(url);
//
//    return "/WEB-INF/views/Success.html";
//  }
  

  @RequestMapping(value = "/doExtract", method = RequestMethod.GET)
  public @ResponseBody String display(@ModelAttribute("inputDto") InputDto inputDto) {
	  List<QuestionDTO> qDTO = ExtractQuestions.extract();
    return String.valueOf(4);
  }
  
  @RequestMapping(value = "/getQuestions", method = RequestMethod.GET )
  public @ResponseBody String getQuestions() {
	  System.out.println("Inside get questions");
	  List<QuestionDTO> qDTO = ExtractQuestions.extract();
    try {
    	 return JsonUtils.toString(qDTO);
	} catch (Exception e) {
		e.printStackTrace();
		return "[]";
	}
  }
  @RequestMapping(value = "/getPages", method = RequestMethod.POST )
  public @ResponseBody String getPages(@RequestParam("seletedQues") String[] arr) {
	  System.out.println("Inside get pages: "+arr);
	  List<QuestionDTO> qDTO = ExtractQuestions.extract();
    try {
    	 return JsonUtils.toString(qDTO);
	} catch (Exception e) {
		e.printStackTrace();
		return "[]";
	}
  }
}
