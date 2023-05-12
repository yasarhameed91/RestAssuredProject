package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public static ThreadLocal<ExtentTest> extentTest= new ThreadLocal<>();
	String repName;

	public void onStart(ITestContext context) {
		
		String timStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName="Test-Report-"+timStamp+".html";
		
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);
		
		sparkReporter.config().setDocumentTitle("Rest Assured Automation Project Report");
		sparkReporter.config().setReportName("Pet Store Users API");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application","Pet Store Users API");
		extent.setSystemInfo("Operating System",System.getProperty("os.name"));
		extent.setSystemInfo("Username",System.getProperty("user.name"));
		extent.setSystemInfo("Environment","SIT");
		extent.setSystemInfo("User","Yasar Hameed");
	}
	public void onTestSuccess(ITestResult result) {
		
		test=extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.PASS,"Test Passed");
	}
	public void onTestFailure(ITestResult result) {
		test=extent.createTest(result.getName());
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL,"Test Failed");
		test.log(Status.FAIL,result.getThrowable().getMessage());
	}
	public void onTestSkipped(ITestResult result) {
		test=extent.createTest(result.getName());
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP,"Test Skipped");
		test.log(Status.SKIP,result.getThrowable().getMessage());
	}
	public void onFinish(ITestContext context) {
		extent.flush();
	}
	
	public static void logPassDetails(String log) {
	    extentTest.get().pass(MarkupHelper.createLabel(log,ExtentColor.GREEN));
	}
	public static void logFailureDetails(String log) {
	    extentTest.get().pass(MarkupHelper.createLabel(log,ExtentColor.RED));
	}
	public static void logInfoDetails(String log) {
	    extentTest.get().pass(MarkupHelper.createLabel(log,ExtentColor.GREY));
	}
	public static void logWarningDetails(String log) {
	    extentTest.get().pass(MarkupHelper.createLabel(log,ExtentColor.YELLOW));
	}
}
