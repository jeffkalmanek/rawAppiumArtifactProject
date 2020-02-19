package testCalculator;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CalculatorDemo {
    AndroidDriver<WebElement> driver;
    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Android");
        //cap.setCapability("platformVersion", "9");
        //cap.setCapability("deviceName", "emulator-5554");
        cap.setCapability("deviceName", "30494b554b373098");
        //cap.setCapability("appPackage", "com.google.android.calculator");
        cap.setCapability("appPackage", "com.sec.android.app.popupcalculator");
        //cap.setCapability("appActivity", "com.android.calculator2.Calculator");
        cap.setCapability("appActivity", "com.sec.android.app.popupcalculator.Calculator");

//        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
        driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @Test (priority=1)
    public void multiply() throws InterruptedException {
        driver.findElementByAccessibilityId("open").isDisplayed();
        driver.findElementById("travel").isEnabled();
        driver.findElementByClassName("textView").isDisplayed();
        driver.findElementByName("open").isDisplayed();
        driver.findElementsByName("open").size(); // the plural returns a list?  so can't click because it's a list
        driver.findElementByTagName("div").isDisplayed();
        driver.findElementByImage("base64encoding").isDisplayed(); // so could save example images for image search
        driver.findElementByAndroidUIAutomator("openButton").isDisplayed();
        driver.findElements(By.className("textView")).clear();
        driver.openNotifications();

        driver.findElementByClassName("android.widget.TextView").getAttribute("text");

        driver.findElementByAndroidViewTag("string").getAttribute("text").matches("messages");


        driver.findElementByXPath("//android.widget.Button[@text='5']").click();
        System.out.println("Clicked on 5");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@content-desc='Multiplication']").click();
        System.out.println("Clicked on x");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@text='6']").click();
        System.out.println("Clicked on 6");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@text='=']").click();
        System.out.println("Clicked on =");
        Thread.sleep(6000);
        //WebElement results=driver.findElementById("com.google.android.calculator:id/result_final");
        WebElement results=driver.findElementById("com.sec.android.app.popupcalculator:id/calc_edt_formula");
        System.out.println("Multiplication Result is : "+results.getText());
        assert results.getText().equals("30"):"Actual value is : "+results.getText()+
                " did not match with expected value: 30";
    }

    @Test (priority=3)
    public void addition() throws InterruptedException {
        driver.findElementByXPath("//android.widget.Button[@text='5']").click();
        System.out.println("Clicked on 5");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@content-desc='Plus']").click();
        System.out.println("Clicked on +");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@text='6']").click();
        System.out.println("Clicked on 6");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@text='=']").click();
        System.out.println("Clicked on =");
        Thread.sleep(6000);
        //WebElement results=driver.findElementById("com.google.android.calculator:id/result_final");
        WebElement results=driver.findElementById("com.sec.android.app.popupcalculator:id/calc_edt_formula");
        System.out.println("Addition Result is : "+results.getText());
        assert results.getText().equals("11"):"Actual value is : "+results.getText()+
                " did not match with expected value: 11";
    }

    @Test (priority=4)
    public void subtraction() throws InterruptedException {
        driver.findElementByXPath("//android.widget.Button[@text='1']").click();
        driver.findElementByXPath("//android.widget.Button[@text='7']").click();
        System.out.println("Clicked on 1 and 7");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@content-desc='Minus']").click();
        System.out.println("Clicked on -");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@text='5']").click();
        System.out.println("Clicked on 5");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@text='=']").click();
        System.out.println("Clicked on =");
        Thread.sleep(6000);
        //WebElement results=driver.findElementById("com.google.android.calculator:id/result_final");
        WebElement results=driver.findElementById("com.sec.android.app.popupcalculator:id/calc_edt_formula");
        System.out.println("Addition Result is : "+results.getText());
        assert results.getText().equals("12"):"Actual value is : "+results.getText()+
                " did not match with expected value: 12";
    }

    @Test (priority=2)
    public void division() throws InterruptedException {
        driver.findElementByXPath("//android.widget.Button[@text='8']").click();
        System.out.println("Clicked on 8");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@content-desc='Division']").click();
        System.out.println("Clicked on /");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@text='4']").click();
        System.out.println("Clicked on 4");
        Thread.sleep(3000);
        driver.findElementByXPath("//android.widget.Button[@text='=']").click();
        System.out.println("Clicked on =");
        Thread.sleep(6000);
        //WebElement results=driver.findElementById("com.google.android.calculator:id/result_final");
        WebElement results=driver.findElementById("com.sec.android.app.popupcalculator:id/calc_edt_formula");
        System.out.println("Addition Result is : "+results.getText());
        assert results.getText().equals("2"):"Actual value is : "+results.getText()+
                " did not match with expected value: 2";
    }
    @AfterTest
    public void afterTest() {
        driver.closeApp();
    }
}