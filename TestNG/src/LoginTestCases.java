import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

public class LoginTestCases {

    //declare webdriver object
    WebDriver driver;

    @BeforeMethod
     public void openBrowser() {

        //1-bridge between code and browser
        String value=System.getProperty("user.dir")+"\\Browsers\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver",value);

        //2-create new web driver object
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver=new ChromeDriver(options);

        //3-configuration to browser
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //4-navigate to website
        String url="https://the-internet.herokuapp.com/";
        driver.navigate().to(url);

        //5-click on form authentication
        driver.findElement(By.cssSelector("a[href=\"/login\"]")).click();

    }

    @Test(priority = 0)
    public void validLogin(){

        //step1: enter valid username and password
        driver.findElement(By.xpath("//input[@type=\"text\"]")).sendKeys("tomsmith");
        driver.findElement(By.xpath("//input[@type=\"password\" and @name=\"password\"]")).sendKeys("SuperSecretPassword!");

        //step2: click login
        driver.findElement(By.xpath("//button[@class=\"radius\"]")).click();

        //compare actual result with expected result

        /*
        //Hard Assertion
        //1-verify this url:https://the-internet.herokuapp.com/secure
        String actualUrl= driver.getCurrentUrl();
        String expectedUrl= "https://the-internet.herokuapp.com/secure";
        Assert.assertEquals(actualUrl, expectedUrl);

        //verify that h2 element contains text "Secure Area"
        String actualText= driver.findElement(By.cssSelector("div[class=\"example\"] h2")).getText();
        String expectedText= "Secure Area";
        Assert.assertEquals(actualText,expectedText);

        //verify that page contains: "You logged into a secure area!"
        String actualFlash= driver.findElement(By.id("flash")).getText();
        String expectedFlash = "You logged into a secure area!";
        Assert.assertTrue(actualFlash.contains(expectedFlash));

        //verify that success message background color is green
        String actualBackgroundColor= driver.findElement(By.id("flash")).getCssValue("background-color");
        actualBackgroundColor= Color.fromString(actualBackgroundColor).asHex();
        String expectedBackgroundColor= "#5da423";
        Assert.assertEquals(actualBackgroundColor,expectedBackgroundColor);

        //verify that logout is displayed

        boolean logoutStatus= driver.findElement(By.cssSelector("a[href=\"/logout\"]")).isDisplayed();
        Assert.assertTrue(logoutStatus);

         */

        //Soft Assertion

        SoftAssert soft = new SoftAssert();
        //1-verify this url:https://the-internet.herokuapp.com/secure
        String actualUrl= driver.getCurrentUrl();
        String expectedUrl= "https://the-internet.herokuapp.com/secure";
        soft.assertEquals(actualUrl, expectedUrl);

        //verify that h2 element contains text "Secure Area"
        String actualText= driver.findElement(By.cssSelector("div[class=\"example\"] h2")).getText();
        String expectedText= "Secure Area";

        soft.assertAll();

    }

    @Test(priority = 1)
    public void invalidLogin(){

        //step1: enter invalid username and password using XPath
        driver.findElement(By.xpath("//input[@type=\"text\"]")).sendKeys("tomsm");
        driver.findElement(By.xpath("//input[@type=\"password\" and @name=\"password\"]")).sendKeys("SuperSecretPasswor");

        //step2:click login
        driver.findElement(By.xpath("//button[@class=\"radius\"]")).click();
    }

    @AfterMethod
    public void quitDriver() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
