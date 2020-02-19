package playStoreTests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class playStoreTestSteps {

    public static void main(String[] args) {

        System.out.println("jefk - setting the capabilities?");

        //Set the Desired Capabilities
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Samsung S8");
        caps.setCapability("udid", "988b9b3848304d5846"); //Give Device ID of your mobile phone
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "9.0");
        caps.setCapability("appPackage", "com.android.vending");
        caps.setCapability("appActivity", "com.android.vending.AssetBrowserActivity");
        caps.setCapability("noReset", "true");

        //Instantiate Appium Driver
        try {
            AppiumDriver<MobileElement> driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);
            System.out.println("jefk - Appium is running");

        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            System.out.println("jefk - caught the exception, so Appium isn't running?");
        }
    }

}

