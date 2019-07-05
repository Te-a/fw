package com.shijie.util;


import org.apache.log4j.Logger;

import com.shijie.testScripts.TestRegister;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

public class Action {
	TestRegister testRegister = new TestRegister();
	static Logger log = Logger.getLogger(Action.class.getName());
	private static AndroidDriver<AndroidElement> driver;
	public Action(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
	}
	
	
	/*
	 * 实现单击操作，这里data没有任何意义，只是为了维护脚本的完成性
	 */
	public void click(MobileElement mobileElement,String data) {
		try {
			mobileElement.click();
		} catch (Exception e) {
			try {
				Thread.sleep(Constants.testControl.elementInspectInterval);
			} catch (InterruptedException e1) {
				testRegister.testResult = false;
				e1.printStackTrace();
			}
			mobileElement.click();
		}
	}
	
	/*
	 * 针对单选框进行单击，单击前判断是否已经处于选择状态
	 */
	public void click_radio(MobileElement mobileElement,String data) {
		try {
			if(data.toLowerCase().equals("yes")) {
				if(!mobileElement.isSelected()) {
					mobileElement.click();
				}
			}
		} catch (Exception e) {
			testRegister.testResult = false;
			e.printStackTrace();
		}
	}
	
	/*
	 * 在编辑框输入指定的数据
	 */
	public void input(MobileElement mobileElement,String data) {
		try {
			this.click(mobileElement, data);
			mobileElement.clear();
			mobileElement.sendKeys(data);
		} catch (Exception e) {
			testRegister.testResult = false;
			e.printStackTrace();
		}
	}
	
	/*
	 * 后退操作
	 */
	public void back(MobileElement mobileElement,String data) {
		driver.pressKeyCode(AndroidKeyCode.BACK);
	}
	
	/*
	 * 验证操作
	 */
	public void verify(MobileElement mobileElement,String data) {
		String actualResult;
		try {
			actualResult = mobileElement.getAttribute("text");
			if(!actualResult.equals(data)) {
				testRegister.testResult = false;
			}
		} catch (Exception e) {
			testRegister.testResult = false;
		}
	}
	
	/*
	 * 等待Activity跳转
	 */
	public static void waitForLoadingActivity(MobileElement mobileElement,String data) throws InterruptedException{
		TestRegister testRegister = new TestRegister();
		Thread.sleep(3000);
		log.info(driver.currentActivity());
		int activityInspectCount,activityInspectInterval;
		activityInspectCount = Constants.testControl.activityInspectCount;
		activityInspectInterval = Constants.testControl.activityInspectInterval;
		
		int i = 0;
		Thread.sleep(activityInspectInterval);
		while(i<activityInspectCount) {
			try {
				if(data.contains(driver.currentActivity())) {
					log.info(data+"出现！");
					break;
				}
				else {
					log.info(data+"未出现!Wait..........1s");
					Thread.sleep(activityInspectInterval);
					i++;
				}
			} catch (Exception e) {
				i++;
				log.info("尝试"+activityInspectCount+"次,"+data+",未出现!");
				testRegister.testResult = false;
			}
		}
		
	}
	
}
