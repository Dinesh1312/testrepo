package accountManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;

public class Widgets extends WebDriverImpl {

    public static WebDriver driver;
    public static Wait<WebDriver> fluentwait;
    public static double rating;
    public static int totalReviews;

    @BeforeClass
    public void browser() throws Exception {
    	
    	 driver = accountManagerLogin();
    }
    
    @AfterClass
	public void afterClass() throws Exception {
		browserQuit();
	}

    @Test
    public void tc01_widgets()throws Exception {
    	wait(5000);
        waitForElementToBeClickableAndClick(driver.findElement(By.id("menuList_widget_label")));
        wait(2000);
        waitForElementToBeClickable(driver.findElement(By.id("widget_reviewWidget_card")));
        Assert.assertEquals(driver.getCurrentUrl(),"https://app"+loginData.getENV()+".experience.com/user/account/widgets");

        Assert.assertTrue(driver.findElement(By.id("nav_breadcumb_title")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("header_acc_subtitle")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("Widgets_nav_label")).isDisplayed());

        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Review Widget']")));

        waitForElementToBeClickable(driver.findElement(By.id("widget_reviewWidget_basicReview_getCode")));

        Assert.assertEquals(driver.getCurrentUrl(),"https://app"+loginData.getENV()+".experience.com/user/account/widgets/reviewwidget");
        Assert.assertTrue(driver.findElement(By.id("header_acc_subtitle")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("Widgets_nav_label")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("ReviewWidgets_nav_label")).isDisplayed());

        Assert.assertTrue(driver.findElement(By.id("reviewWidget_div_basicTab")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.id("reviewWidget_div_basicTab")));
        Assert.assertTrue(driver.findElement(By.id("reviewWidget_div_customizeTab")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.id("reviewWidget_div_customizeTab")));
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_basicReview_reviewsPerPage']/preceding::div[3]")).isDisplayed());

        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_basicReview_reviewsPerPage']/preceding::div[1]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_basicReview_reviewsPerPage']//ancestor::div[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_basicReview_filter_tier']/preceding::div[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_basicReview_filter_tier']/following::span[2]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_basicReview_filter_tier']/following::span[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_basicReview_filter_agent']/following::span[2]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_basicReview_filter_agent']/following::span[1]")));

        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_basicReview_filter_agent']/following::div[2]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[contains(text(),'Select ')]/following::div[2]")));
        Assert.assertTrue(driver.findElement(By.xpath("//button[@id='widget_reviewWidget_basicReview_getCode']/span")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.id("widget_reviewWidget_basicReview_getCode")));

        Assert.assertTrue(driver.findElement(By.xpath("//button[@id='widget_reviewWidget_basicReview_getCode']//following::span[2]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//button[@id='widget_reviewWidget_basicReview_filter_sendEmail']/preceding::div[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//button[@id='widget_reviewWidget_basicReview_filter_sendEmail']//following ::span[1]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.id("widget_reviewWidget_basicReview_filter_sendEmail")));

        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@id='reviewWidget_div_customizeTab']//ancestor::div[2]")));

        waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='reviewWidget_customizeReview_div_themeButton']/child::label[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='reviewWidget_customizeReview_div_themeButton']//preceding::span[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='reviewWidget_div_appearanceCollapse']//following::span[3]")).isDisplayed());

        waitForElementToBeClickableAndClick(driver.findElement(By.id("reviewWidget_div_selectCampaignsCollapse")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='reviewWidget_div_selectCampaignsCollapse']//following::span[3]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='SelectCampaign']//ancestor::div[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='reviewWidget_div_selectCampaignsCollapse']//following::label")).isDisplayed());

        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//span[text()='Filters']")));
        wait(2000);
        waitForElementToBeClickable(driver.findElement(By.xpath("//span[text()='Source']//child::button[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='reviewWidget_div_filtersSortCollapse']//following::span[3]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Source']")).isDisplayed());

        waitForElementToBeClickableAndClick(driver.findElement(By.id("reviewWidget_div_SortCollapse")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='reviewWidget_div_SortCollapse']//following::span[3]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.xpath("//span[text()='Rating']//child::button")));
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Rating']")).isDisplayed());

        waitForElementToBeClickableAndClick(driver.findElement(By.id("reviewWidget_div_buttonsLinksCollapse")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='reviewWidget_div_buttonsLinksCollapse']//following::span[3]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.id("widget_reviewWidget_customReview_filter_buttonLink_enableWriteReviewButton")));
        Assert.assertTrue(driver.findElement(By.xpath("//button[@id='widget_reviewWidget_customReview_filter_buttonLink_enableWriteReviewButton']//preceding::span[1]")).isDisplayed());

        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='reviewWidget_div_buttonsLinksCollapse']/following::div[3]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_customReview_reviewsPerPage']/ancestor::div[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_customReview_reviewsPerPage']/following::div[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_customReview_filter_tier']/following::span[2]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_customReview_filter_tier']/parent::span[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_customReview_filter_agent']/following::span[2]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='widget_reviewWidget_customReview_filter_agent']/parent::span[1]")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[contains(text(),'Select ')]/following::div[2]")));
        waitForElementToBeClickable(driver.findElement(By.id("widget_reviewWidget_customReview_filter_sendEmail")));

        waitForElementToBeClickable(driver.findElement(By.id("widget_reviewWidget_div_webView")));
        waitForElementToBeClickable(driver.findElement(By.id("widget_reviewWidget_div_mobileView")));
        waitForElementToBeClickable(driver.findElement(By.id("widget_widgetReview_reviewHeader_contactUsButton")));
        waitForElementToBeClickable(driver.findElement(By.id("widget_widgetReview_reviewHeader_writeReviewButton")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='widget_widgetReview_review_header_RatingsCount']/following-sibling::div[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='widget_widgetReview_review_header_totalReviewCount']/following-sibling::div[1]")).isDisplayed());

        waitForElementToBeClickableAndClick(driver.findElement(By.id("widget_widgetReview_button_sortList")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Oldest']")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Highest Rated']")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Lowest Rated']")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Pinned Latest']")));

        rating=Double.parseDouble(driver.findElement(By.id("widget_widgetReview_review_header_RatingsCount")).getText());
        Assert.assertTrue(rating>0);
        totalReviews=Integer.parseInt(driver.findElement(By.id("widget_widgetReview_review_header_totalReviewCount")).getText());
        Assert.assertTrue(totalReviews>0);
        waitForElementToBeClickableAndClick(driver.findElement(By.id("admin_button_filter")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='input_box_Search']//following::input[1]")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='widget_widgetReview_span_dateFilter']/ancestor::div[2]")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='input_box_Search']/child::div[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("widget_widgetReview_review_reviewerName")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("widget_widgetReview_review_agentName")).isDisplayed());

     /*   waitForElementToBeClickableAndClick(By.id("widget_widgetReview_reviewHeader_contactUsButton"));
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()=' Contact Us']")).isDisplayed());
        wait(3000);
        click(driver.findElement(By.xpath("//input[@id='contact_selectAgent_filter_agent']/parent::span")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//span[text()='Continue']/parent::button")));
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()=' Contact Us']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Please select the profile to Contact']")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//span[text()=' Contact Us']//following::div[1]")));
*/
        waitForElementToBeClickableAndClick(driver.findElement(By.id("widget_widgetReview_reviewHeader_writeReviewButton")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//span[text()='Select a user']/ancestor::div[1]")));
        waitForElementToBeClickable(driver.findElement(By.xpath("(//span[contains(text(),'@experience')]/ancestor::span[2])[2]")));

        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//span[contains(text(),'@experience')]/ancestor::span[2])[2]")));
        switchToWindow();
        wait(3000);
        waitForElementToBeClickable(driver.findElement(By.id("profileContainer_input_review_firstName")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Enter your details and write your review:']")).isDisplayed());
        driver.close();
        navigateTab(0);

        refreshPage();
        wait(3000);
        waitForElementToBeClickableAndClick(driver.findElement(By.id("widget_reviewWidget_div_mobileView")));

        wait(3000);
        waitForElementToBeClickable(driver.findElement(By.id("widget_widgetReview_reviewHeader_contactUsButton")));
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Contact Us']")).isDisplayed());
   /*     
        waitForElementToBeClickableAndClick(driver.findElement(By.id("widget_widgetReview_reviewHeader_contactUsButton")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//span[text()='Select User']")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//span[text()='Continue']/parent::button")));
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()=' Contact Us']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Please select the profile to Contact']")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//span[text()=' Contact Us']//following::div[1]")));
*/
        waitForElementToBeClickableAndClick(driver.findElement(By.id("widget_widgetReview_button_sortList")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Oldest']")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Highest Rated']")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Lowest Rated']")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Pinned Latest']")));

        wait(2000);
        rating=Double.parseDouble(driver.findElement(By.id("widget_widgetReview_review_header_RatingsCount")).getText());
        System.out.println(rating);
        Assert.assertTrue(rating>0);
        totalReviews=Integer.parseInt(driver.findElement(By.id("widget_widgetReview_review_header_totalReviewCount")).getText());
        Assert.assertTrue(totalReviews>0);

        waitForElementToBeClickableAndClick(driver.findElement(By.id("admin_button_filter")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='input_box_Search']//following::input[1]")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='widget_widgetReview_span_dateFilter']/ancestor::div[2]")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='input_box_Search']/child::div[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("widget_widgetReview_review_reviewerName")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("widget_widgetReview_review_agentName")).isDisplayed());

        waitForElementToBeClickableAndClick(driver.findElement(By.id("widget_widgetReview_reviewHeader_writeReviewButton")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//span[text()='Select a user']/ancestor::div[1]")));
        waitForElementToBeClickable(driver.findElement(By.xpath("(//span[contains(text(),'@experience')]/ancestor::span[2])[2]")));

        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//span[contains(text(),'@experience')]/ancestor::span[2])[2]")));
        switchToWindow();
        wait(3000);
        waitForElementToBeClickable(driver.findElement(By.id("profileContainer_input_review_firstName")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Enter your details and write your review:']")).isDisplayed());
        driver.close();


    }
    	
    	
}
