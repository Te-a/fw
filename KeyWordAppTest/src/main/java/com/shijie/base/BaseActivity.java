package com.shijie.base;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.shijie.util.Constants;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class BaseActivity {
	public static AndroidDriver<AndroidElement> driver;
	public static Logger logger = Logger.getLogger(BaseActivity.class.getName());
	private static AppiumDriverLocalService service;
	private static AppiumServiceBuilder builder;
	
	@BeforeClass
	public void setUp() throws Exception {
		logger.info("-----------------测试开始-----------------");
		builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		logger.info("appium启动");
//		if(service == null||!service.isRunning()) {
//			throw new AppiumServerHasNotBeenStartedLocallyException("An appium service node is not started");
//		}
		DesiredCapabilities cap = new DesiredCapabilities();
		// 平台名称
		cap.setCapability("platformName", Constants.Vivo_x20.platformName);
		// 操作系统
		cap.setCapability("platformVersion", Constants.Vivo_x20.platformVersion);
		// 连接的物理设备的唯一设备标识
		cap.setCapability("deviceName", Constants.Vivo_x20.deviceName);
		cap.setCapability("udid", Constants.Vivo_x20.udid);
		// App安装后的包名,注意与原来的CalcTest.apk不一样
		cap.setCapability("appPackage", Constants.Vivo_x20.appPackage);
		// app测试人员常常要获取activity，进行相关测试
		cap.setCapability("appActivity", Constants.Vivo_x20.appActivity);

		cap.setCapability("unicodeKeyboard", Constants.Vivo_x20.unicodeKeyboard);
		cap.setCapability("noSign", Constants.Vivo_x20.noSign);
		driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@AfterClass
	public void tearDown() {
		logger.info("-----------------测试结束-----------------");
		driver.quit();
		if(service != null) {
			service.stop();
		}
	}
	
	public AndroidDriver<AndroidElement> getDriver(){
		return driver;
	}
	
	
}
