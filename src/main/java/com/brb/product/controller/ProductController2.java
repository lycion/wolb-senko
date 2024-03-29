package com.brb.product.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.brb.brbcom.common.collections.BrbMap;
import com.brb.brbcom.common.util.RequestParameterUtil;
import com.brb.product.service.ProductService2;
/**
 *
 * @author back
 *
 */
@Controller
public class ProductController2 {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


	@Autowired
	ProductService2 productService2;

	/**
	 * Breeze 통계
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("product/breezeReport")
	public ModelAndView adminList() {
		ModelAndView mav = new ModelAndView();
		List siList = null;
		List btbList = null;
		List ageList = null;
		List sensorTypeList = null;

		try{
			siList = productService2.getSiList();
			btbList = productService2.getBtbList();
			ageList = productService2.getAgeList();
			sensorTypeList = productService2.getSensorTypeList();
		} catch(Exception e){
			e.printStackTrace();
		}

		mav.setViewName("product/breezeReport");
		mav.addObject("siList", siList);
		mav.addObject("btbList", btbList);
		mav.addObject("ageList", ageList);
		mav.addObject("sensorTypeList", sensorTypeList);
		return mav;
	}

	/**
	 * Breeze 통계
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "product/chartList" , method = RequestMethod.POST)
	public ModelAndView chartTest(HttpServletRequest request,
				HttpServletResponse response,
				ModelMap modelMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		BrbMap<Object, Object> dMap	= RequestParameterUtil.getParameterMap(request);
		dMap.put("start_dt", dMap.getString("start_dt").replaceAll("-", ""));
		dMap.put("end_dt", dMap.getString("end_dt").replaceAll("-", ""));
		List list = null;
		try{
			list = productService2.getChartList(dMap);
		} catch(Exception e){
			e.printStackTrace();
		}

		mav.addObject("list", list);
		return mav;
	}

}
