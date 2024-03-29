package com.brb.sample.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.brb.brbcom.common.collections.BrbMap;
import com.brb.brbcom.common.util.AES256Util;
import com.brb.brbcom.common.util.RequestParameterUtil;
import com.brb.sample.service.SampleService;
/**
 *
 * @author back
 *
 */
@Controller
public class SampleController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	private static AES256Util aes256 = AES256Util.getInstance();

	@Autowired
	SampleService sampleService;


	@RequestMapping("sample/sample")
	public ModelAndView sample() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/sample") ;
		return mav;
	}


	@RequestMapping("sample/sample3")
	public ModelAndView sample3() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/sample3") ;
		return mav;
	}

	/**
	 * sample 이미지  업로드
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param imageFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "sample/edit", method = RequestMethod.POST)
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response,
			ModelMap modelMap, @RequestParam MultipartFile imageFile)throws Exception{

		ModelAndView view = new ModelAndView();
	    try{
	    	BrbMap dMap = RequestParameterUtil.getParameterMap(request);

	    	/*String genId = UUID.randomUUID().toString();
	    	String fileName = imageFile.getOriginalFilename();
	    	byte[] bytes = imageFile.getBytes();
	    	String saveFileName = genId + "." + getExtension(fileName);

	    	String savePath = "C:\\work\\" + saveFileName;
	    	if (!"".equals(getExtension(fileName))){
	    		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(savePath));
	    		bos.write(bytes);
	    		bos.flush();
	    		bos.close();
	    	}*/

	    } catch (Exception e){
	      e.printStackTrace();
	    }
	    //view.setViewName("sample/sample2") ;
	    return view;
	}


	@RequestMapping("sample/sample2")
	public ModelAndView sample2() throws DecoderException {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/sample2") ;

		String loginPw = "qweasd1!";
		String strEncoding = "";
		String strDecoding = "";
		String strEncoding2 = "";
		String strDecoding2 = "";
		try {
			strEncoding = aes256.aesEncode(loginPw);
			strDecoding = aes256.aesDecode(strEncoding);
			strEncoding2 = aes256.aesEncode(strDecoding);
			strDecoding2 = aes256.aesDecode(strEncoding2);
			System.out.println("##loginPw :: " + loginPw);
			System.out.println("##strEncoding :: " + strEncoding);
			System.out.println("##strDecoding :: " + strDecoding);
			System.out.println("##strEncoding2 :: " + strEncoding2);
			System.out.println("##strDecoding2 :: " + strDecoding2);
		} catch (Exception e) {
			e.printStackTrace();
		}



		return mav;
	}

	/**
	 * sample 이미지  업로드
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param imageFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "sample/fileupload", method = RequestMethod.POST)
	public ModelAndView inSt(HttpServletRequest request,
			HttpServletResponse response,
			ModelMap modelMap, @RequestParam MultipartFile imageFile)throws Exception{

		ModelAndView view = new ModelAndView();
	    try{
	    	BrbMap dMap = RequestParameterUtil.getParameterMap(request);

	    	String genId = UUID.randomUUID().toString();
	    	String fileName = imageFile.getOriginalFilename();
	    	byte[] bytes = imageFile.getBytes();
	    	String saveFileName = genId + "." + getExtension(fileName);

	    	String savePath = "C:\\work\\" + saveFileName;
	    	if (!"".equals(getExtension(fileName))){
	    		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(savePath));
	    		bos.write(bytes);
	    		bos.flush();
	    		bos.close();
	    	}

	    } catch (Exception e){
	      e.printStackTrace();
	    }
	    view.setViewName("sample/sample2") ;
	    return view;
	}

	/**
	 * getExtension
	 * @param fileName
	 * @return
	 */
	private String getExtension(String fileName){
	    int dotPosition = fileName.lastIndexOf('.');
	    if ((-1 != dotPosition) && (fileName.length() - 1 > dotPosition)) {
	      return fileName.substring(dotPosition + 1);
	    }
	    return "";
	}


	/**
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "sample/testList" , method = RequestMethod.POST)
	public ModelAndView testList(HttpServletRequest request,
			HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		ModelAndView view = new ModelAndView("jsonView");
		BrbMap<Object, Object> dMap	= RequestParameterUtil.getParameterMap(request);
		BrbMap<Object, Object> rMap = null;
		try{
			rMap = sampleService.getTestListAll(dMap);
		} catch(Exception e){
			e.printStackTrace();
		}
		rMap.put("name", "test");
		view.addObject("list", rMap);
		return view;
	}


	/**
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "sample/inTest" , method = RequestMethod.POST)
	public ModelAndView testSetUser(HttpServletRequest request,
			HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		ModelAndView view = new ModelAndView("jsonView");
		BrbMap<Object, Object> dMap	= RequestParameterUtil.getParameterMap(request);
		//BrbMap<Object, Object> rMap = null;
		dMap.put("ID", "bb");
		dMap.put("NAME", "test222");
		dMap.put("JUMIN", "22222");
		int cnt = 0;
		try{
			cnt = sampleService.addTestUser(dMap);
		} catch(Exception e){
			e.printStackTrace();
		}
		//rMap.put("name", "test");

		view.addObject("result", cnt);
		return view;
	}


	/**
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "sample/testListAll"  , method = RequestMethod.POST)
	public ModelAndView testListAll(HttpServletRequest request,
			HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		ModelAndView view = new ModelAndView("jsonView");
		BrbMap<Object, Object> dMap	= RequestParameterUtil.getParameterMap(request);
		BrbMap<Object, Object> rMap = null;
		//Map<String, String> infoMap = new HashMap<String, String>();
		try{
			rMap = sampleService.getTestListAll(dMap);
		} catch(Exception e){
			e.printStackTrace();
		}
		view.addObject("list", rMap);
		return view;
	}


	/**
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "sample/testapi" , method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> testapi(HttpServletRequest request) throws Exception {

		BrbMap<Object, Object> dMap	= RequestParameterUtil.getParameterMap(request);
		BrbMap<Object, Object> rMap = null;
		try{
			rMap = sampleService.getTestList(dMap);
		} catch(Exception e){
			e.printStackTrace();
		}
		return rMap;
	}



	/**
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "sample/testapi/{value}" , method = RequestMethod.PUT)
	public @ResponseBody Map<Object, Object> testapiput(@PathVariable("value") final String value,HttpServletRequest request) throws Exception {

		BrbMap<Object, Object> dMap	= RequestParameterUtil.getParameterMap(request);
		BrbMap<Object, Object> rMap = null;
		Map<Object, Object> infoMap = new HashMap<Object, Object>();
		infoMap.put("value",value);
		try{
			//infoMap = sampleService.getTestList(dMap);
		} catch(Exception e){
			e.printStackTrace();
		}
		return infoMap;
	}



	/**
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "sample/testapi/{id}" , method = RequestMethod.GET)
	public @ResponseBody Map<Object, Object> testapi2(@PathVariable("id") final String id,HttpServletRequest request) throws Exception {

		BrbMap<Object, Object> dMap	= RequestParameterUtil.getParameterMap(request);
		BrbMap<Object, Object> rMap = null;
		Map<Object, Object> infoMap = new HashMap<Object, Object>();
		try{
			infoMap = sampleService.getTestListAll(dMap);
		} catch(Exception e){
			e.printStackTrace();
		}
		return infoMap;
	}



	/**
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "sample/testapi2" , method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> testapiPost(HttpServletRequest request) throws Exception {

		BrbMap<Object, Object> dMap	= RequestParameterUtil.getParameterMap(request);
		BrbMap<Object, Object> rMap = null;
		Map<Object, Object> infoMap = new HashMap<Object, Object>();
		try{
			infoMap = sampleService.getTestList(dMap);
		} catch(Exception e){
			e.printStackTrace();
		}
		return infoMap;
	}

	/**
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused"})
	@RequestMapping(value = "sample/sendmail" , method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> sendmail(HttpServletRequest request) throws Exception {

		BrbMap<Object, Object> dMap	= RequestParameterUtil.getParameterMap(request);
		BrbMap<Object, Object> rMap = null;
		Map<Object, Object> infoMap = new HashMap<Object, Object>();
		String host     = "smtp.naver.com";
		final String user   = "kbearpu";
		final String password  = "";

		String to     = "kbearpu@naver.com";

		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
		   protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(user, password);
		   }
		});

		  // Compose the message
		try {
//			MimeMessage message = sender.createMimeMessage();

			 MimeMessage message = new MimeMessage(session);
			 message.setFrom(new InternetAddress(user));
			 message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			String pw = "test123!";
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			//helper.setTo("kbearpu@naver.com");

			helper.setSubject("[Subject] 대기오염  테스트 메일");
			StringBuffer sb = new StringBuffer();
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
			sb.append("</head>");
			sb.append("<body bgcolor='#FFFFFF'>");
			sb.append("<table style='width:100%; height:50px;'   bgcolor='#999999'><tr><td></td></tr></table>");
			sb.append("<table><tr><td></td><td  bgcolor='#FFFFFF'><div><table><tr><td><h3>임시 비밀번호 </h3>");
			sb.append("<h3 style='color:blue;'>");
			sb.append(pw);
			sb.append("</h3>");
			sb.append("<p>임시비밀번호로 로그인후 비밀번호 변경을 해주세요.</p></td></tr></table></div></td><td></td></tr></table>");
			sb.append("<table style='width:100%; height:50px;'   bgcolor='#999999'><tr><td></td></tr></table>");
			sb.append("</body>");
			sb.append("</html>");

			// 포함된 텍스트가 HTML이라는 의미로 true 플래그를 사용한다
			//helper.setText("<html><body><img src='cid:identifier1234'>afdsfse</body></html>", true);
			helper.setText(sb.toString(), true);
			message.setContent(sb.toString(), "text/html; charset=UTF-8");

			// 악명높은 윈도우의 Sample 파일을 첨부하자 (여기서는 c:/ 에서 복사한다)
			/*FileSystemResource res = new FileSystemResource(new File("c:/sample.jpg"));
			helper.addInline("identifier1234", res);*/



		   // Subject
//		   message.setSubject("[Subject] 대기오염  테스트 메일");

		   // Text
//		   message.setText("대기 오염 머시기머시기 머시기");

		   // send the message
		   Transport.send(message);
		   System.out.println("message sent successfully...");

		} catch (MessagingException e) {
		   e.printStackTrace();
		}
		return infoMap;
	}


	/**
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "sample/getApiCtprvnMesureSidoLIst" , method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> getApiCtprvnMesureSidoLIst(HttpServletRequest request) throws Exception {
		Map<Object, Object> infoMap = new HashMap<Object, Object>();

		List<Map> listMap =  new ArrayList<>();

		//ERxdAS3O6xrAdH250XpVlPLuYMLzckcZ7XPrYnTKteJ4%2Br3z%2B4%2FTZuRJBhJEzIZwI1AaH2V2y9g%2Bca%2Bsy%2BQqSQ%3D%3D
		//=HcMdg5QxKvUQeDrHoYPCxN%2FEh1jitQPqesfFde7UPWna6WyZ5BgjXGL6OgF5yejHbwc1gA3pJz6Ohf%2BzEMOnNA%3D%3D

		StringBuilder urlBuilder = new StringBuilder("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "ERxdAS3O6xrAdH250XpVlPLuYMLzckcZ7XPrYnTKteJ4%2Br3z%2B4%2FTZuRJBhJEzIZwI1AaH2V2y9g%2Bca%2Bsy%2BQqSQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode("서울", "UTF-8")); /*시도 이름 (서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
        urlBuilder.append("&" + URLEncoder.encode("searchCondition","UTF-8") + "=" + URLEncoder.encode("DAILY", "UTF-8")); /*요청 데이터기간 (시간 : HOUR, 하루 : DAILY)*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        //BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream() , "UTF-8"));

        Document doc = parseXML(conn.getInputStream());
        NodeList descNodes = doc.getElementsByTagName("item");

        for(int i=0; i<descNodes.getLength();i++){
        	Node nNode = descNodes.item(i);
        	Element eElement = (Element) nNode;
    		Map<Object, Object> inMap = new HashMap<Object, Object>();
			inMap.put("측정일시", getTagValue("dataTime", eElement));
			inMap.put("시군구 명", getTagValue("cityName", eElement));
			inMap.put("아황산가스 평균농도", getTagValue("so2Value", eElement));
			inMap.put("일산화탄소 평균농도", getTagValue("coValue", eElement));
			inMap.put("오존 평균농도", getTagValue("o3Value", eElement));
			inMap.put("이산화질소 평균농도", getTagValue("no2Value", eElement));
			inMap.put("미세먼지(PM10)평균농도", getTagValue("pm10Value", eElement));
			inMap.put("미세먼지(PM2.5)평균농도", getTagValue("pm25Value", eElement));
			listMap.add(inMap);
        }
        conn.disconnect();
        infoMap.put("list", listMap);
		return infoMap;
	}

	/**
	 * parseXML
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	private static Document parseXML(InputStream stream) throws Exception{
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try{
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
            doc = objDocumentBuilder.parse(stream);
        }catch(Exception ex){
            throw ex;
        }
        return doc;
    }


	/**
	 * getTagValue
	 * @param tag
	 * @param eElement
	 * @return
	 */
	private static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null)
	        return null;
	    return nValue.getNodeValue();
	}




	/**
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "sample/sendsms" , method = RequestMethod.POST)
	public @ResponseBody Map<Object, Object> sedsms(HttpServletRequest request) throws Exception {
		Map<Object, Object> infoMap = new HashMap<Object, Object>();
		BrbMap<Object, Object> dMap	= RequestParameterUtil.getParameterMap(request);

		StringBuffer response = new StringBuffer();

		try {
			String url = "http://www.munja123.com/Remote/RemoteSms.html";
			URL obj = new URL(url);
			URLConnection conn = obj.openConnection();
			String param1="senko";
			String param2="hagood74";

			String urlParameters = "remote_id=" + param1 +"&remote_pass=" + param2+"&remote_num=" + "1"
					+"&remote_reserve="+"0"+"&remote_phone="+"01042233619"+"&remote_name="+"홍길동"
					+"&remote_subject="+""+"&remote_callback="+"01054094926"+"&remote_msg="+"테스트"
					+"&remote_returnurl=";


	        // POST 값 전송일 경우 true
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        // 파라미터를 wr에 넣어주고 flush
	        wr.write(urlParameters);
	        wr.flush();

	        // in에 readLine이 null이 아닐때까지 StringBuffer에 append
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	        wr.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }

		//response
		//0000|전송 성공|1|6496|||||||1520483032
        //infoMap.put("list", listMap);
		return infoMap;
	}
}
