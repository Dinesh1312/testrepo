package agentLogin;

import driversAndLibrary.WebDriverImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Widgets extends WebDriverImpl {

    public static WebDriver driver;
    public static Wait<WebDriver> fluentwait;
    public static double rating;
    public static int totalReviews;

    @BeforeClass
    public void browser() throws Exception {
    	driver = agentLogin();
    }

    @AfterClass
    public void afterClass() {
        //WebDriverImpl.browserQuit();
    }

    @Test
    public void tc01_widgets()throws Exception {
        wait(3000);
        moveToElement(driver.findElement(By.id("menuList_widget_label")));
        click(By.id("menuList_widget_label"));
        wait(2000);
        moveToElement(driver.findElement(By.id("widget_reviewWidget_card")));
        click(driver.findElement(By.id("widget_reviewWidget_card")));
        wait(2000);
        Assert.assertEquals(driver.getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/widgets/reviewwidget");
        Assert.assertTrue(driver.findElement(By.id("nav_breadcumb_title")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='Widgets_nav_label']")).getText(), "Widgets");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='ReviewWidgets_nav_label']")).getText(), "Review Widgets");
    }

    @Test
    public void tc02_basicReviews()throws Exception {
        wait(2000);
        Assert.assertTrue(driver.findElement(By.id("reviewWidget_div_tabPanel")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("review_widget_screen_width")).isDisplayed());
        wait(1000);
        moveToElement(driver.findElement(By.xpath("//div[text()='Basic Review']")));
        click(By.xpath("//div[text()='Basic Review']"));
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_basicReview_reviewsPerPage']")).isEnabled());
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_basicReview_filter_agent']")).isEnabled());
        wait(1000);
        moveToElement(driver.findElement(By.xpath("//button[@id='widget_reviewWidget_basicReview_getCode']")));
        click(By.xpath("//button[@id='widget_reviewWidget_basicReview_getCode']"));
        wait(2000);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ant-drawer-title']")).getText(),"Get Code");
        wait(1000);
        moveToElement(driver.findElement(By.xpath("//button[@id='widget_reviewWidget_basicReview_getCode_drawer_closeButton']")));
        click(By.xpath("//button[@id='widget_reviewWidget_basicReview_getCode_drawer_closeButton']"));
    }

    @Test
    public void tc03_customizeReviews()throws Exception {
        wait(1000);
        moveToElement(driver.findElement(By.xpath("//div[@id='reviewWidget_div_customizeTab']")));
        click(By.xpath("//div[@id='reviewWidget_div_customizeTab']"));
        wait(1000);
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(@class,'ant-radio-button-wrapper')]")).isEnabled());
        Assert.assertTrue(driver.findElements(By.xpath("//label[contains(@for,'colorPicker')]")).stream().allMatch(m->m.isEnabled()));
        Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Fonts']/following::input[1]")).isEnabled());
        wait(1000);
        moveToElement(driver.findElement(By.xpath(" //span[text()='Select Campaigns']")));
        click(By.xpath(" //span[text()='Select Campaigns']"));
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='SelectCampaign']")).isEnabled());
        click(By.xpath("//span[text()='Filters']"));
        Assert.assertTrue(driver.findElements(By.xpath(" //button[@class='ant-switch ant-switch-checked']")).stream().allMatch(m->m.isEnabled()));
        click(By.xpath("//span[text()='Sort']"));
        Assert.assertTrue(driver.findElements(By.xpath(" //span[text()='Sort']/following::button[@class='ant-switch ant-switch-checked']")).stream().allMatch(m->m.isEnabled()));
        click(By.xpath("//span[text()='Buttons & Links']"));
        Assert.assertTrue(driver.findElements(By.xpath(" //span[text()='Buttons & Links']/following::button[@class='ant-switch ant-switch-checked']")).stream().allMatch(m->m.isEnabled()));
        Assert.assertTrue(driver.findElement(By.xpath(" //input[@id='widget_reviewWidget_customReview_reviewsPerPage']")).isEnabled());
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_customReview_filter_agent']")).isEnabled());
    }

    @Test
    public void tc04_contactUs()throws Exception {
        wait(2000);
        moveToElement(driver.findElement(By.id("widget_widgetReview_reviewHeader_contactUsButton")));
        click(By.id("widget_widgetReview_reviewHeader_contactUsButton"));
        wait(3000);
        Assert.assertEquals(driver.findElement(By.xpath("//span[text()=' Contact Us']")).getText(),"Contact Us");
        Assert.assertTrue(driver.findElement(By.xpath(" //span[text()=' Contact Us']/following::input[1]")).isEnabled());
        wait(1000);
        moveToElement(driver.findElement(By.xpath(" (//span[text()=' Contact Us']/following::*[local-name()='svg'])[1]")));
        click(By.xpath(" (//span[text()=' Contact Us']/following::*[local-name()='svg'])[1]"));
        Assert.assertTrue(driver.findElement(By.id("widget_widgetReview_reviewHeader_writeReviewButton")).isEnabled());
        Assert.assertTrue(driver.findElement(By.id("widget_widgetReview_button_sortList")).isEnabled());
        wait(1000);
        moveToElement(driver.findElement(By.id("admin_button_filter")));
        click(By.id("admin_button_filter"));
        Assert.assertTrue(driver.findElements(By.xpath(" //div[text()='Filters']/following::input")).stream().allMatch(m->m.isEnabled()));

    }
    }


