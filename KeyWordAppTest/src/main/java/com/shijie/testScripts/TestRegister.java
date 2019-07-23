package com.shijie.testScripts;

import org.testng.annotations.Test;

import com.shijie.base.BaseActivity;
import com.shijie.util.Action;
import com.shijie.util.Constants;
import com.shijie.util.DataProviderFromExcel;
import com.shijie.util.FindElement;

import io.appium.java_client.MobileElement;

import org.apache.log4j.Logger;
import org.testng.Assert;
import java.lang.reflect.Method;

public class TestRegister extends BaseActivity{
	//AndroidDriver<AndroidElement> driver;
	Logger log = Logger.getLogger(TestRegister.class.getName());
	public static Method method[];
	//对象识别关键字
	public static String inspector;
	//数据
	public static String data;
	//操作
	public static String actionstep;
	//定义类
	public static Action action;
	public static MobileElement mobileElement;
	public static boolean testResult;
	public static String filePath;
	
	@Test
	public void test_register_sucess() throws Exception{
		action = new Action(getDriver());
		method = action.getClass().getMethods();
		//定义excel文件的路径
		String filePath = Constants.excleFile.filepath;
		DataProviderFromExcel.getExcel(filePath);
		String fileSheet = Constants.scheduleFile.suite_sheet;
		//获取测试集合中测试用例的总数
		int testSuiteAllNum = DataProviderFromExcel.getAllRowNum(fileSheet);
		//循环测试调度文件
		for(int testSuiteNum = 1; testSuiteNum <= testSuiteAllNum; testSuiteNum++) {
			//获取测试用例名，直接关联待测用例所在的sheet名
			String testCaseName = DataProviderFromExcel.getCellData(Constants.scheduleFile.suite_sheet, 
					testSuiteNum, Constants.scheduleFile.suite_testCaseName).trim();
			//判断测试用例是否运行
			String isRun = DataProviderFromExcel.getCellData(Constants.scheduleFile.suite_sheet, 
					testSuiteNum, Constants.scheduleFile.suite_isRun).trim();
			//获取测试用例的详细描述，只用来输出日志
			String testCaseDetail = DataProviderFromExcel.getCellData(Constants.scheduleFile.suite_sheet, 
					testSuiteNum, Constants.scheduleFile.suite_testCaseDetail).trim();
			if(isRun.equals("√")) {
				//测试执行结果默认为失败
				log.info("运行测试用例：测试用例名称："+testCaseName+"；测试用例详细描述："+testCaseDetail);
				testResult = true;
				int testCaseAllNum = DataProviderFromExcel.getAllRowNum(testCaseName);
				log.info("测试步骤数："+ testCaseAllNum);
				for(int testCaseNum = 1;testCaseNum<= testCaseAllNum;testCaseNum++) {
					//获取识别方式
					inspector = DataProviderFromExcel.getCellData(testCaseName, testCaseNum, 
							Constants.testFile.Col_inspector).trim();
					//获取操作数据
					data = DataProviderFromExcel.getCellData(testCaseName, testCaseNum, 
							Constants.testFile.Col_data).trim();
					//获取操作方式
					actionstep = DataProviderFromExcel.getCellData(testCaseName, testCaseNum, 
							Constants.testFile.Col_actionStep).trim();
					//识别元素
					mobileElement = null;
					if(!inspector.isEmpty()) {
						mobileElement = FindElement.findElement(driver, inspector);
					}
					log.info("执行测试步骤：识别方式："+inspector+"操作："+actionstep+"；测试数据"+data);
					excute_Actions(testCaseNum,testCaseName);
					if(testResult==false) {
						log.info("测试用例执行结果为false");
						DataProviderFromExcel.setCellData(testSuiteNum, Constants.scheduleFile.suite_result,
								false, fileSheet, filePath);
						Assert.fail("测试步骤有失效，整个测试用例执行失败");
						break;
					}
					if(testResult==true) {
						log.info("测试用例执行结果为true");
						DataProviderFromExcel.setCellData(testSuiteNum, Constants.scheduleFile.suite_result,
								true, fileSheet, filePath);
						Assert.assertTrue(true,"测试用例执行成功");
					}
				}
			}
		}
		
	}

	public void excute_Actions(int testCaseNum, String testCaseName) {
		try {
			for(int i = 0;i<method.length;i++) {
				if(method[i].getName().equals(actionstep)) {
					method[i].invoke(action, mobileElement,data);
					if(testResult==true) {
						log.info("测试步骤执行结果为true");
						DataProviderFromExcel.setCellData(testCaseNum, Constants.testFile.Col_result,
								true, testCaseName, Constants.excleFile.filepath);
						break;
					}
					else {
						log.info("测试步骤执行结果为false");
						DataProviderFromExcel.setCellData(testCaseNum, Constants.testFile.Col_result,
								true, testCaseName, Constants.excleFile.filepath);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
