package com.shijie.util;

import org.apache.log4j.Logger;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import com.shijie.testScripts.TestRegister;

public class FindElement {
	
	static Logger log = Logger.getLogger(FindElement.class.getName());
	/*
	 * 对象是被函数进行二次封装
	 */
	public static AndroidElement findElementbyType (AndroidDriver<?> driver, String controlInfo) throws Exception{
		MobileElement element = null;
		if(controlInfo.startsWith("//")) {
			element = (MobileElement) driver.findElementByXPath(controlInfo);
		}
		else if(controlInfo.contains(":id/")){
			element = (MobileElement) driver.findElementById(controlInfo);
		} else {
			try {
				element = (MobileElement) driver.findElementByAndroidUIAutomator("text(\""+controlInfo+"\")");
			} catch (Exception e) {
				element = (MobileElement) driver.findElementByClassName(controlInfo);
			}
		}
		return (AndroidElement)element; 
	}
	
	public static MobileElement findElement(AndroidDriver<?> driver, String controlInfo) throws Exception{
		TestRegister testRegister = new TestRegister();
		int elementInspectCount,elementInspectInterval;
		elementInspectCount = Constants.testControl.elementInspectCount;
		elementInspectInterval = Constants.testControl.elementInspectInterval;
		MobileElement element = null;
		for(int i=0;i<elementInspectCount;i++) {
			Thread.sleep(elementInspectInterval);
			try {
				element = findElementbyType(driver, controlInfo);
				log.info("已经找到元素对象");
				return element;
			} catch (Exception e) {
				log.info("控件未出现！Waitting..........1s");
				continue;
			}
		}
		log.info("多次查找未找到元素控件，这时测试结果位置false");
		testRegister.testResult = false;
		throw new IllegalAccessException("在指定时间内未找到测试对象");
		
	}
	
}
