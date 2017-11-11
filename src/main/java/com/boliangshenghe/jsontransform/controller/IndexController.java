package com.boliangshenghe.jsontransform.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.boliangshenghe.jsontransform.entity.Jdata;
import com.boliangshenghe.jsontransform.service.JdataService;
import com.boliangshenghe.jsontransform.util.FtpClienUtil;
import com.boliangshenghe.jsontransform.vo.internal.Internal;
import com.boliangshenghe.jsontransform.vo.overseas.Overseas;
import com.boliangshenghe.jsontransform.vo.sea.Sea;

/**
 * eventID 地点 日期  标题 内容
 * @author xuzj
 * 
 *
 */
@Controller
public class IndexController {

	
	@Autowired
	public JdataService jdataService;

	public String path = "/ftpdata";
	@RequestMapping("/")
	public String index(){
		return "redirect:/manage/list";
	}
	
	@RequestMapping("/data")
	public String data(){
		// linksController.runTask();
		System.out.println("/data start" +new Date());
		TypeUtils.compatibleWithJavaBean = true;
		Jdata record = new Jdata();
		Set<String> jset = jdataService.selectEventidSet(record);
		try {
			FtpClienUtil.connectFTP();
			List<String> names =  FtpClienUtil.getFileNameList("");
			for (String name : names) {

				System.out.println(name);
				//根据文件名去重
				if(jset.contains(name)){
					continue;
				}
				String content = FtpClienUtil.download(name);
				content = content.substring(1, content.length() - 1);
				JSONObject jsonObj = JSONObject.parseObject(content);
				if(content.indexOf("DistanceToCapital")!=-1){
					StringBuffer sb = new StringBuffer();
					//海外模板
					Overseas overseas = JSON.toJavaObject(jsonObj, Overseas.class);
					sb.append("据中国地震台网测定，北京时间"+overseas.getTime().getYear()+"年"+overseas.getTime().getMonth()+"月"+overseas.getTime().getDay()+"日");
					sb.append(overseas.getTime().getHour()+"时"+overseas.getTime().getMinute()+"分，在"+overseas.getLocation());
					if(overseas.getLat().startsWith("-")){
						sb.append("(南纬"+overseas.getLat().substring(1, overseas.getLat().length())+"度，");
					}else{
						sb.append("(北纬"+overseas.getLat()+"度，");
					}
					if(overseas.getLon().startsWith("-")){
						sb.append("西经"+overseas.getLon().substring(1, overseas.getLon().length())+"度)发生");
					}else{
						sb.append("东经"+overseas.getLon()+"度)发生");
					}
					
					sb.append(overseas.getMagnitude()+"级地震，震源深度约"+overseas.getDepth()+"公里。震中距离");
					sb.append(overseas.getName().getStateName()+"区首府"+overseas.getName().getStateCapitalName()+"约");
					sb.append(overseas.getDistance().getDistanceToStateCapital()+"公里，距"+overseas.getName().getCapitalName()+"约");
					sb.append(overseas.getDistance().getDistanceToCapital()+"公里。");
					
					Jdata jdata = new Jdata();
					jdata.setEventid(name);
					jdata.setLocation(overseas.getLocation());
					jdata.setTitle(overseas.getLocation()+"发生"+overseas.getMagnitude()+"级地震");
					jdata.setType("3");
					jdata.setCreatorTime(new Date());
					jdata.setContent(sb.toString());
					jdata.setTime(overseas.getTime().getYear()+"年"+overseas.getTime().getMonth()+"月"+overseas.getTime().getDay()+"日"+
							overseas.getTime().getHour()+"时"+overseas.getTime().getMinute()+"分");
					jdataService.insertSelective(jdata);
					//System.out.println(sb.toString());
					
				}else if(content.indexOf("hazard")!=-1){
					StringBuffer sb = new StringBuffer();
					//内地大陆
					//TypeUtils.compatibleWithJavaBean
					Internal internal= JSON.toJavaObject(jsonObj, Internal.class);
					sb.append("据中国地震台网测定，北京时间"+internal.getTime().getYear()
							  +"年"+internal.getTime().getMonth()+"月"+internal.getTime().getDay()+"日");
					sb.append(internal.getTime().getHour()+"时"+internal.getTime().getMinute()
							  +"分，在"+internal.getLocation());
					if(internal.getLat().startsWith("-")){
						sb.append("(南纬"+internal.getLat().substring(1, internal.getLat().length())+"度，");
					}else{
						sb.append("(北纬"+internal.getLat()+"度，");
					}
					if(internal.getLon().startsWith("-")){
						sb.append("西经"+internal.getLon().substring(1, internal.getLon().length())+"度)发生");
					}else{
						sb.append("东经"+internal.getLon()+"度)发生");
					}
					sb.append(internal.getMagnitude()+"级地震，震源深度约"+internal.getDepth()+"公里。<p/>震中距离最近的");
					sb.append(internal.getName().getNearestCtyName()+""+internal.getName().getNearestTownName()
								+"约"+internal.getDistance().getNearestTownDistance()+"公里，");
					if(internal.getName().getCountyName()!=null){//可能没有countyName 县数据
						sb.append("距"
								+internal.getName().getCountyName()+"县城约"
								+internal.getDistance().getDistanceToCounty()+"公里，");
					}
					sb.append("距"+internal.getName().getCityName()+"城区约"
							   +internal.getDistance().getDistanceToCity()+"公里,距");
					System.out.println(internal.getName().getProCapName()+"  "+internal.getDistance().getDistanceToProvince());
					sb.append(internal.getName().getProCapName()+"约"
							   	+internal.getDistance().getDistanceToProvince()+"公里。<p/>震中50公里范围内");
					
					if(internal.getPopulation().getPop_aver()==0){
						sb.append("人口稀少");
					}else{
						sb.append("的人口密度约");
						sb.append(internal.getPopulation().getPop_aver()+"人/平方公里");
					}
					
					sb.append(",人口约"+internal.getPopulation().getPop_sum()+"人。");
					//sb.append("震中10公里范围内平均海拔约");
					sb.append("<p/>震中20公里范围内有"+internal.getStastics().getTownCount()+"个乡（镇、街道）,有"
							   	+internal.getStastics().getVillageCount()+"个村。");
					sb.append("<p/>震区未来3天气象信息："+internal.getWeather());
					sb.append("<p/>"+internal.getHazard().getEarthquakeActivity()+""+internal.getHazard().getQuakeMax()+""+internal.getHazard().getDisasterMax());
					
					Jdata jdata = new Jdata();
					jdata.setEventid(name);
					jdata.setLocation(internal.getLocation());
					jdata.setTitle(internal.getLocation()+"发生"+internal.getMagnitude()+"级地震");
					jdata.setType("1");
					jdata.setCreatorTime(new Date());
					jdata.setContent(sb.toString());
					jdata.setTime(internal.getTime().getYear()+"年"+internal.getTime().getMonth()+"月"+internal.getTime().getDay()+"日"+
							internal.getTime().getHour()+"时"+internal.getTime().getMinute()+"分");
					jdataService.insertSelective(jdata);
					//System.out.println(sb.toString());
				}else{//海洋
					StringBuffer sb = new StringBuffer();
					Sea sea = JSON.toJavaObject(jsonObj, Sea.class);
					sb.append("据中国地震台网测定，北京时间"+sea.getTime().getYear()+"年"+sea.getTime().getMonth()+"月"+sea.getTime().getDay()+"日");
					sb.append(sea.getTime().getHour()+"时"+sea.getTime().getMinute()+"分，在"+sea.getLocation());
					if(sea.getLat().startsWith("-")){
						sb.append("(南纬"+sea.getLat().substring(1, sea.getLat().length())+"度，");
					}else{
						sb.append("(北纬"+sea.getLat()+"度，");
					}
					if(sea.getLon().startsWith("-")){
						sb.append("西经"+sea.getLon().substring(1, sea.getLon().length())+"度)发生");
					}else{
						sb.append("东经"+sea.getLon()+"度)发生");
					}
					sb.append(sea.getMagnitude()+"级地震，震源深度约"+sea.getDepth()+"公里。");
					Jdata jdata = new Jdata();
					jdata.setEventid(name);
					jdata.setLocation(sea.getLocation());
					jdata.setTitle(sea.getLocation()+"发生"+sea.getMagnitude()+"级地震");
					jdata.setType("2");
					jdata.setCreatorTime(new Date());
					jdata.setContent(sb.toString());
					jdata.setTime(sea.getTime().getYear()+"年"+sea.getTime().getMonth()+"月"+sea.getTime().getDay()+"日"+
							sea.getTime().getHour()+"时"+sea.getTime().getMinute()+"分");
					jdataService.insertSelective(jdata);
					//System.out.println(sea.getLocation());
				}
			}
		}catch (Exception c) {
			// TODO: handle exception
		}finally{
			try {
				FtpClienUtil.ftp.close();
			} catch (IOException c) {
				// TODO Auto-generated catch block
				c.printStackTrace();
			}
		}
		return "redirect:/manage/list";
	}
	
	@RequestMapping("/test")
	public String test(Model model) {
		TypeUtils.compatibleWithJavaBean = true;
		model.addAttribute("hello", "22222");
		System.out.println("1");
		StringBuffer sb = new StringBuffer();
		
		try {
			File pFile = new File(path);
			File[] files = pFile.listFiles();
			System.out.println(files.length);
			for (File file : files) {
				if(file.isFile()){
					/*System.out.println(file.getName());
					System.out.println(file.getAbsolutePath());*/
					InputStreamReader isr = new InputStreamReader(new FileInputStream(
							file.getAbsolutePath()), "GBK");

					BufferedReader br = new BufferedReader(isr);

					String s = null;
					while ((s = br.readLine()) != null) {
						// System.out.println(s);
						s = s.substring(1, s.length() - 1);
						JSONObject jsonObj = JSONObject.parseObject(s);
						
						if(s.indexOf("DistanceToCapital")!=-1){
							//海外模板
							Overseas overseas = JSON.toJavaObject(jsonObj, Overseas.class);
							sb.append("<p/>据中国地震台网测定，北京时间"+overseas.getTime().getYear()+"年"+overseas.getTime().getMonth()+"月"+overseas.getTime().getDay()+"日");
							sb.append(overseas.getTime().getHour()+"时"+overseas.getTime().getMinute()+"分，在"+overseas.getLocation());
							if(overseas.getLat().startsWith("-")){
								sb.append("(南纬"+overseas.getLat().substring(1, overseas.getLat().length())+"度，");
							}else{
								sb.append("(北纬"+overseas.getLat()+"度，");
							}
							if(overseas.getLon().startsWith("-")){
								sb.append("西经"+overseas.getLon().substring(1, overseas.getLon().length())+"度)发生");
							}else{
								sb.append("东经"+overseas.getLon()+"度)发生");
							}
							
							sb.append(overseas.getMagnitude()+"级地震，震源深度约"+overseas.getDepth()+"公里。震中距离");
							sb.append(overseas.getName().getStateName()+"区首府"+overseas.getName().getStateCapitalName()+"约");
							sb.append(overseas.getDistance().getDistanceToStateCapital()+"公里，距"+overseas.getName().getCapitalName()+"约");
							sb.append(overseas.getDistance().getDistanceToCapital()+"公里。<br/>");
							
							//System.out.println(sb.toString());
							
						}else if(s.indexOf("hazard")!=-1){
							//内地大陆
							//TypeUtils.compatibleWithJavaBean
							Internal internal= JSON.toJavaObject(jsonObj, Internal.class);
							sb.append("<p/>据中国地震台网测定，北京时间"+internal.getTime().getYear()
									  +"年"+internal.getTime().getMonth()+"月"+internal.getTime().getDay()+"日");
							sb.append(internal.getTime().getHour()+"时"+internal.getTime().getMinute()
									  +"分，在"+internal.getLocation());
							if(internal.getLat().startsWith("-")){
								sb.append("(南纬"+internal.getLat().substring(1, internal.getLat().length())+"度，");
							}else{
								sb.append("(北纬"+internal.getLat()+"度，");
							}
							if(internal.getLon().startsWith("-")){
								sb.append("西经"+internal.getLon().substring(1, internal.getLon().length())+"度)发生");
							}else{
								sb.append("东经"+internal.getLon()+"度)发生");
							}
							sb.append(internal.getMagnitude()+"级地震，震源深度约"+internal.getDepth()+"公里。<p/>震中距离最近的");
							sb.append(internal.getName().getNearestCtyName()+""+internal.getName().getNearestTownName()
										+"约"+internal.getDistance().getNearestTownDistance()+"公里，");
							if(internal.getName().getCountyName()!=null){//可能没有countyName 县数据
								sb.append("距"
										+internal.getName().getCountyName()+"县城约"
										+internal.getDistance().getDistanceToCounty()+"公里，");
							}
							
							
							sb.append("距"+internal.getName().getCityName()+"城区约"
									   +internal.getDistance().getDistanceToCity()+"公里,距");
							System.out.println(internal.getName().getProCapName()+"  "+internal.getDistance().getDistanceToProvince());
							sb.append(internal.getName().getProCapName()+"约"
									   	+internal.getDistance().getDistanceToProvince()+"公里。<p/>震中50公里范围内的人口密度约");
							sb.append(internal.getPopulation().getPop_aver()+"人/平方公里,人口约"
									   	+internal.getPopulation().getPop_sum()+"人。震中10公里范围内平均海拔约");
							sb.append("<p/>震中20公里范围内有"+internal.getStastics().getTownCount()+"个乡（镇、街道）,有"
									   	+internal.getStastics().getVillageCount()+"个村。");
							sb.append("<p/>震区未来3天气象信息："+internal.getWeather());
							sb.append(internal.getHazard().getEarthquakeActivity()+""+internal.getHazard().getQuakeMax()+""+internal.getHazard().getDisasterMax()+"<br/>");
							
							//System.out.println(sb.toString());
						}else{
							Sea sea = JSON.toJavaObject(jsonObj, Sea.class);
							sb.append("<p/>据中国地震台网测定，北京时间"+sea.getTime().getYear()+"年"+sea.getTime().getMonth()+"月"+sea.getTime().getDay()+"日");
							sb.append(sea.getTime().getHour()+"时"+sea.getTime().getMinute()+"分，在"+sea.getLocation());
							if(sea.getLat().startsWith("-")){
								sb.append("(南纬"+sea.getLat().substring(1, sea.getLat().length())+"度，");
							}else{
								sb.append("(北纬"+sea.getLat()+"度，");
							}
							if(sea.getLon().startsWith("-")){
								sb.append("西经"+sea.getLon().substring(1, sea.getLon().length())+"度)发生");
							}else{
								sb.append("东经"+sea.getLon()+"度)发生");
							}
							sb.append(sea.getMagnitude()+"级地震，震源深度约"+sea.getDepth()+"公里。"+"<br/>");
							//System.out.println(sea.getLocation());
							
						}
						
						/*System.out.println(i.getTime().getDay());
						System.out.println(jsonObj.get("elevation"));
						Object obj = jsonObj.get("hazard");
						// JSONObject.getObject(obj, Hazard.class);
						// Hazard hazard = (Hazard) jsonObj.get("hazard");
						System.out.println(obj);*/
					}

					br.close();
				}
			}
			
		} catch (UnsupportedEncodingException c) {
			// TODO Auto-generated catch block
			c.printStackTrace();
		} catch (FileNotFoundException c) {
			// TODO Auto-generated catch block
			c.printStackTrace();
		} catch (IOException c) {
			// TODO Auto-generated catch block
			c.printStackTrace();
		}
		model.addAttribute("sb", sb);
		return "test";
	}
}
