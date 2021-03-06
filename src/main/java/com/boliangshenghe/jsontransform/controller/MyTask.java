package com.boliangshenghe.jsontransform.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.boliangshenghe.jsontransform.entity.Jdata;
import com.boliangshenghe.jsontransform.service.JdataService;
import com.boliangshenghe.jsontransform.util.FtpClienUtil;
import com.boliangshenghe.jsontransform.vo.internal.Internal;
import com.boliangshenghe.jsontransform.vo.internaltw.InternalTW;
import com.boliangshenghe.jsontransform.vo.overseas.Overseas;
import com.boliangshenghe.jsontransform.vo.sea.Sea;

@Component
public class MyTask {
	
	@Autowired
	public JdataService jdataService;

	//0 0/2 * * * ?
	// 0 0 0/2 * * ? *
	//@Scheduled(cron = "0 0 0/12 * * ?")
	@Scheduled(cron = "0 0/10 * * * ?")
	public void taskCycle() {
		// linksController.runTask();
		System.out.println("MyTask start" +new Date());

		TypeUtils.compatibleWithJavaBean = true;
		Jdata record = new Jdata();
		Set<String> jset = jdataService.selectEventidSet(record);
		try {
			FtpClienUtil.connectFTP();
			List<String> names =  FtpClienUtil.getFileNameList("");
			for (String name : names) {

				//System.out.println(name);
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
					//System.out.println(internal.getName().getProCapName()+"  "+internal.getDistance().getDistanceToProvince());
					sb.append(internal.getName().getProCapName()+"约"
							   	+internal.getDistance().getDistanceToProvince()+"公里。<p/>震中50公里范围内");
					
					String peoplesum = "震中50公里范围内";
					if(internal.getPopulation().getPop_aver()==0){
						sb.append("人口稀少");
						peoplesum = peoplesum+"人口稀少";
					}else{
						sb.append("的人口密度约");
						sb.append(internal.getPopulation().getPop_aver()+"人/平方公里");
						peoplesum = peoplesum+"的人口密度约"+internal.getPopulation().getPop_aver()+"人/平方公里";
					}
					
					sb.append(",人口约"+internal.getPopulation().getPop_sum()+"人。");
					peoplesum = peoplesum+",人口约"+internal.getPopulation().getPop_sum()+"人。";
				
					sb.append("震中10公里范围内平均海拔约"+internal.getDem_aver()+"米。");
					
					String demaver = "10公里范围内平均海拔约"+internal.getDem_aver()+"米。";
					
					sb.append("<p/>震中20公里范围内有"+internal.getStastics().getTownCount()+"个乡（镇、街道）,有"
							   	+internal.getStastics().getVillageCount()+"个村。");
					
					String towncount ="震中20公里范围内有"+internal.getStastics().getTownCount()+"个乡（镇、街道）,有"
						   			+internal.getStastics().getVillageCount()+"个村。";
					
					String weather = "震区未来3天气象信息："+internal.getWeather();
					
					sb.append("<p/>震区未来3天气象信息："+internal.getWeather());
					sb.append("<p/>"+internal.getHazard().getEarthquakeActivity()+""+internal.getHazard().getQuakeMax()+""+internal.getHazard().getDisasterMax());
					
					String hazardcount = internal.getHazard().getEarthquakeActivity()+""+internal.getHazard().getQuakeMax()+""+internal.getHazard().getDisasterMax();
					
					Jdata jdata = new Jdata();
					jdata.setEventid(name);
					jdata.setLocation(internal.getLocation());
					jdata.setTitle(internal.getLocation()+"发生"+internal.getMagnitude()+"级地震");
					jdata.setType("1");
					jdata.setCreatorTime(new Date());
					jdata.setContent(sb.toString());
					jdata.setTime(internal.getTime().getYear()+"年"+internal.getTime().getMonth()+"月"+internal.getTime().getDay()+"日"+
							internal.getTime().getHour()+"时"+internal.getTime().getMinute()+"分");
					
					jdata.setDemaver(demaver);
					jdata.setHazardcount(hazardcount);
					jdata.setPeoplesum(peoplesum);
					jdata.setTowncount(towncount);
					jdata.setWeather(weather);
					jdataService.insertSelective(jdata);
					//System.out.println(sb.toString());
				}else if(content.indexOf("hazard")!=-1 && content.indexOf("population")==-1){
					StringBuffer sb = new StringBuffer();
					//台湾
					//TypeUtils.compatibleWithJavaBean
					InternalTW internaltw= JSON.toJavaObject(jsonObj, InternalTW.class);
					sb.append("据中国地震台网测定，北京时间"+internaltw.getTime().getYear()
							  +"年"+internaltw.getTime().getMonth()+"月"+internaltw.getTime().getDay()+"日");
					sb.append(internaltw.getTime().getHour()+"时"+internaltw.getTime().getMinute()
							  +"分，在"+internaltw.getLocation());
					if(internaltw.getLat().startsWith("-")){
						sb.append("(南纬"+internaltw.getLat().substring(1, internaltw.getLat().length())+"度，");
					}else{
						sb.append("(北纬"+internaltw.getLat()+"度，");
					}
					if(internaltw.getLon().startsWith("-")){
						sb.append("西经"+internaltw.getLon().substring(1, internaltw.getLon().length())+"度)发生");
					}else{
						sb.append("东经"+internaltw.getLon()+"度)发生");
					}
					sb.append(internaltw.getMagnitude()+"级地震，震源深度约"+internaltw.getDepth()+"公里。");
					sb.append("<p/>震中距"+internaltw.getName().getCountyName()+""
								+"县城约"+internaltw.getDistance().getDistanceToCounty()+"公里，");
					
					sb.append("距"+internaltw.getName().getProCapName()
							+"约"+internaltw.getDistance().getDistanceToProvince()+"公里。");
					
				
					sb.append("震中10公里范围内平均海拔约"+internaltw.getDem_aver()+"米。");
					
					//String demaver = "震中10公里范围内平均海拔约"+internaltw.getDem_aver()+"米。";
					
					
					//String weather = "震区未来3天气象信息："+internaltw.getWeather()+internaltw.getHazard().getEarthquakeActivity()+""+internaltw.getHazard().getQuakeMax()+""+internaltw.getHazard().getDisasterMax();
					
					sb.append("<p/>震区未来3天气象信息："+internaltw.getWeather());
					sb.append("<p/>有历史地震记录以来,"+internaltw.getHazard().getEarthquakeActivity()+""+internaltw.getHazard().getQuakeMax()+""+internaltw.getHazard().getDisasterMax());
					
					//String hazardcount = internal.getHazard().getEarthquakeActivity()+""+internal.getHazard().getQuakeMax()+""+internal.getHazard().getDisasterMax();
					Jdata jdata = new Jdata();
					jdata.setEventid(name);
					jdata.setLocation(internaltw.getLocation());
					jdata.setTitle(internaltw.getLocation()+"发生"+internaltw.getMagnitude()+"级地震");
					jdata.setType("4");
					jdata.setCreatorTime(new Date());
					jdata.setContent(sb.toString());
					jdata.setTime(internaltw.getTime().getYear()+"年"+internaltw.getTime().getMonth()+"月"+internaltw.getTime().getDay()+"日"+
							internaltw.getTime().getHour()+"时"+internaltw.getTime().getMinute()+"分");
					
					/*jdata.setDemaver(demaver);
					jdata.setHazardcount(hazardcount);
					jdata.setPeoplesum(peoplesum);
					jdata.setTowncount(towncount);
					jdata.setWeather(weather);*/
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
	}
}
