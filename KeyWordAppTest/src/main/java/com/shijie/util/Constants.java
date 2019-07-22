package com.shijie.util;

public class Constants {
	public class Vivo_x20{
		public static final String deviceName = "vivo_x20";
		public static final String udid = "c6984f9f";
		public static final String platformVersion = "5.1.1";
		public static final String platformName = "Android";
		public static final String appPackage = "io.selendroid.testapp";
		public static final String appActivity = ".HomeScreenActivity";
		public static final String unicodeKeyboard = "True";
		public static final String noSign = "True";
	}
	
	public class Honor_v9 {
		public static final String deviceName = "honor_v9";
		public static final String udid = "6EBDU17308002466";
		public static final String platformVersion = "7.0";
		public static final String platformName = "Android";
		public static final String appPackage = "io.selendroid.testapp";
		public static final String appActivity = ".HomeScreenActivity";
		public static final String unicodeKeyboard = "True";
		public static final String noSign = "True";
	}
	
	//测试excle文件
	public class excleFile{
		public static final String filepath = "../KeyWordAppTest/data/register.xlsx";
	}
	
	//测试调度文件
	public class scheduleFile{
		public static final String suite_sheet = "Main";
		public static final int suite_testCaseName = 0;
		public static final int suite_testCaseDetail = 1;
		public static final int suite_isRun = 2;
		public static final int suite_result = 3;
	}
	
	//测试用例文件
	public class testFile{
		public static final int Col_testcaseID = 0;
		public static final int Col_testStepID = 1;
		public static final int Col_inspector = 4;
		public static final int Col_data = 6;
		public static final int Col_result = 7;
		public static final int Col_actionStep = 5;
	}
	
	//测试控件
	public class testControl{
		public static final int elementInspectCount = 5;
		public static final int elementInspectInterval = 1000;
		public static final int activityInspectCount = 5;
		public static final int activityInspectInterval = 1000;
	}


	
	
}
