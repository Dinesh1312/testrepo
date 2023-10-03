package accountManager;

import driversAndLibrary.WebDriverImpl;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

public class SocialMonitor extends WebDriverImpl {
    public static WebDriver driver;


    @BeforeClass
    public void browser() throws Exception {
		driver = launchBrowser(loginData.getBROWSER_NAME());

		driver.get("https://app"+loginData.getENV()+".experience.com/user/signin");
		fluentVisibilityOfElement(driver.findElement(By.id("UserSignInForm_input_email")));
		if(loginData.getENV().equals(".sandbox")) 
		{
			input(By.id("UserSignInForm_input_email"), "autotester+12345@experience.com");
		}
		else {
			input(By.id("UserSignInForm_input_email"), "autotester+002@experience.com");
		}
		fluentVisibilityOfElement(driver.findElement(By.id("UserSignInForm_input_password")));
		input(By.id("UserSignInForm_input_password"), "password");

		fluentVisibilityOfElement(driver.findElement(By.id("UserSignInForm_input_submit")));
		click(By.id("UserSignInForm_input_submit"));
	}
    
    @AfterClass
	public void afterclass() {
		//browserQuit();
	}

    @Test()
    public void tc_01verifyingSocialMonitorLandingPage() throws Exception {
        wait(3000);
        waitForElementToBeClickableAndClick(driver.findElement(By.id("menuList_socialMonitor")));
        wait(2000);
        //Validating the url, title, breadcrumbs
        waitForElementToBeClickable(driver.findElement(By.id("socialMonitor_div_socialPostingTab_1")));
        Assert.assertEquals(driver.getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/socialmonitor");
        Assert.assertTrue(driver.findElement(By.id("header_acc_subtitle")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("SocialMonitor_nav_label")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("nav_breadcumb_title")).isDisplayed());
    }

    @Test()
    public void tc_02verifyingMacrosReportsEditMonitors() throws Exception {
        //Validating macros
        waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_button_macrosBtn")));
        wait(5000);
        waitForElementToBeClickable(driver.findElement(By.id("socialMonitor_macro_button_addMacro")));
        Assert.assertTrue(driver.findElement(By.xpath("//thead[@class='ant-table-thead']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='ant-table-tbody']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//th[@title='Macro']")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_macro_button_addMacro")));
        waitForElementToBeClickable(driver.findElement(By.id("name")));
        Assert.assertTrue(driver.findElement(By.xpath("(//div[text()='Add new Macro'])[2]")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Add new Macro']/preceding-sibling::button")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Macro Settings']/preceding-sibling::button")));
        wait(1000);
        //Validating reports
        waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_button_ReportsBtn")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='report_type']/following::span[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Social Monitoring Report']")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//input[@id='report_type']/following::span[1]")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='report_type_list']/following::div[contains(@class,'ant-select-item-option')][3]")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//span[text()='Date Range']/following::input[1]")));
        waitForElementToBeClickable(driver.findElement(By.xpath("(//th[text()='Su'])[2]")));
        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-picker-date-panel'])[2]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-picker-header'])[2]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-picker-body'])[2]")).isDisplayed());
        wait(1000);
       safeJavaScriptClick(driver.findElement(By.xpath("//div[text()='Back']//ancestor::button")));
        wait(1000);
        //Validating edit monitors
        waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_button_editMonitorsBtn")));
        waitForElementToBeClickable(driver.findElement(By.id("socialMonitor_searchMonitor_input_search")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Monitors']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Search']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//thead[@class='ant-table-thead']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//th[@aria-label='Name']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='ant-table-tbody']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("(//td[@class='ant-table-cell'])[1]")).isDisplayed());
        wait(1500);
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Monitors']/preceding-sibling::button")));
        fluentWaitJsClick(driver.findElement(By.xpath("//div[text()='Monitors']/preceding-sibling::button")));
    }

    @Test()
    public void tc_03verifyingFilter() throws InterruptedException {
        waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_button_filterBtn")));
        waitForElementToBeClickable(driver.findElement(By.id("socialMonitor_searchFilter_input_searchPost")));
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Search & Filter']")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//input[@id='socialMonitor_searchFilter_datepicker']/ancestor::div[2]")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[@class='ant-picker-footer']")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-picker-header']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-picker-body']")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//a[text()='Today']")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//input[@id='social_monitor_segments_dropdown']/ancestor::div[@class='ant-select-selector']")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@id='social_monitor_segments_dropdown_list']/following::div[contains(@class,'ant-select-item-option-grouped')][1]")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@name='user_social_monitor']/child::div")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[contains(text(),'.com')]")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//input[@id='social_monitor_feed_select']/ancestor::div[1]")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@id='social_monitor_feed_select_list']/following::div[contains(@class,'ant-select-item ant-select-item-option')][1]")));
        waitForElementToBeClickableAndClick(driver.findElement(By.id("filter_clear")));
        wait(1000);
        waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_button_filterBtn")));
        wait(3000);
    }

    @Test()
    public void tc_04verifyingStreams() throws Exception {
        //Validating the flag by selecting 2 user
    	
    	waitForElementToBeClickableAndClick(driver.findElement(By.id("menuList_socialMonitor")));
    	
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='socialMonitor_input_postCheckbox_0']/following-sibling::span")));
        fluentWaitJsClick(driver.findElement(By.xpath("//input[@id='socialMonitor_input_postCheckbox_0']/following-sibling::span")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='socialMonitor_input_postCheckbox_1']/following-sibling::span")));
        fluentWaitJsClick(driver.findElement(By.xpath("//input[@id='socialMonitor_input_postCheckbox_1']/following-sibling::span")));
        fluentWaitMove(driver.findElement(By.xpath("//button[@id='socialMonitor_button_editMonitorsBtn']/following-sibling::button")));
        wait(1000);
        mouseActionClick(driver.findElement(By.xpath("//button[@id='socialMonitor_button_editMonitorsBtn']/following-sibling::button")));
        wait(1000);
        waitForElementToBeClickable(driver.findElement(By.xpath("//li[@id='socialMonitor_moreOption_list_0']")));
        fluentWaitJsClick(driver.findElement(By.xpath("//li[@id='socialMonitor_moreOption_list_0']")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']//following::div[@class='ant-select-selector']")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']/child::div[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("socialMonitor_bulkAction_textArea_comment")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Send Email']/parent::div")));
        Assert.assertTrue(driver.findElement(By.xpath("//textarea[@id='socialMonitor_bulkAction_textArea_comment'][1]")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']//following::div[@class='ant-select-selector']")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']/button")));
        wait(1000);
        //Validating  escalate by selecting 2 user
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//button[@id='socialMonitor_button_editMonitorsBtn']/following-sibling::button")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//li[@id='socialMonitor_moreOption_list_1']")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']//following::div[@class='ant-select-selector']")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']/child::div[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("socialMonitor_bulkAction_textArea_comment")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Send Email']/parent::div")));
        Assert.assertTrue(driver.findElement(By.xpath("//textarea[@id='socialMonitor_bulkAction_textArea_comment'][1]")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']//following::div[@class='ant-select-selector']")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']/button")));
        //Validating sort post
        waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_postsSorter_filter")));
        waitForElementToBeClickable(driver.findElement(By.xpath("//div[@class='ant-popover-inner-content']/div/div[1]")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@class='ant-popover-inner-content']/div/div[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-list-header']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'ant-checkbox-group') or @class='ant-list-empty-text']")).isDisplayed());
        wait(2000);
        waitForElementToBeClickable(driver.findElement(By.xpath("(//ul[@class='ant-list-items']//following::span)[1]")));
        waitForElementToBeClickable(driver.findElement(By.xpath("(//div[contains(@id,'socialMonitor_postDetail_div_postedDate')]/preceding::div[1])[1]")));
        Assert.assertTrue(!(driver.findElement(By.xpath("//div[contains(@id,'socialMonitor_postDetail_div_ownerName')]/child::div[1]")).getText()).isEmpty());
       // Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@alt,'Profile Image')]")).isDisplayed());
        Assert.assertTrue(!(driver.findElement(By.xpath("(//div[@id='socialMonitor_postDetail_div_ownerName_1']//following::div)[1]")).getText()).isEmpty());
        //Validating the apply macro
       // waitForElementToBeClickable(driver.findElement(By.xpath("(//input[@id='socialMonitor_div_stream_applyMacro_1']//ancestor::div[1])[1]")));
        wait(1000);
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//input[@id='socialMonitor_div_stream_applyMacro_1']//ancestor::div[1])[1]")));
//        waitForVisibilityOfElement(driver.findElement(By.xpath("(//div[@id='socialMonitor_div_stream_applyMacro_1_list'])[1]")));
//        Assert.assertTrue(driver.findElement(By.xpath("(//div[@id='socialMonitor_div_stream_applyMacro_1_list'])[1]")).isDisplayed());
        
      
        
        waitForVisibilityOfElement(driver.findElement(By.xpath("//div[@id='socialMonitor_div_stream_applyMacro_1_list']/ancestor::div[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='socialMonitor_div_stream_applyMacro_1_list']/ancestor::div[1]")).isDisplayed());
        
        //validate the flag
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//button[@id='socialMonitor_button_stream_flag_1'])[1]")));
        wait(1000);
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Private Note']/parent::div")));
        waitForElementToBeClickable(driver.findElement(By.xpath("(//textarea[@id='replyBox'])[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("(//form[contains(@class,'ant-form ant-form-horizontal')]/preceding-sibling::div)[1]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.xpath("(//button[@id='socialMonitor_streamForm_button_cancel_1'])[1]")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Send Email']/parent::div")));
        Assert.assertTrue(driver.findElement(By.xpath("(//form[contains(@class,'ant-form ant-form-horizontal')]/preceding-sibling::div)[1]")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//button[@id='socialMonitor_streamForm_button_cancel_1'])[1]")));
        //Validating the escalation
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//button[@id='socialMonitor_button_stream_escalate_1'])[1]")));
        wait(1000);
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Private Note']/parent::div")));
        waitForElementToBeClickable(driver.findElement(By.xpath("(//textarea[@id='replyBox'])[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("(//form[contains(@class,'ant-form ant-form-horizontal')]/preceding-sibling::div)[1]")).isDisplayed());
        waitForElementToBeClickable(driver.findElement(By.xpath("(//button[@id='socialMonitor_streamForm_button_cancel_1'])[1]")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Send Email']/parent::div")));
        Assert.assertTrue(driver.findElement(By.xpath("(//form[contains(@class,'ant-form ant-form-horizontal')]/preceding-sibling::div)[1]")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//button[@id='socialMonitor_streamForm_button_cancel_1'])[1]")));
        wait(2000);
    }

   @Test()
    public void tc_05verifyingAlerts() throws Exception {
	   
	   waitForElementToBeClickableAndClick(driver.findElement(By.id("menuList_socialMonitor")));
	   
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@id='socialMonitor_div_socialPostingTab_2']/ancestor::div[@data-node-key='alert']")));
        Thread.sleep(2000);
       //Validating the unflag by selecting 2 user
       waitForElementToBeClickable(driver.findElement(By.xpath("(//input[@id='socialMonitor_input_postCheckbox_0']/following-sibling::span)[2]")));
       fluentWaitJsClick(driver.findElement(By.xpath("(//input[@id='socialMonitor_input_postCheckbox_0']/following-sibling::span)[2]")));
       waitForElementToBeClickable(driver.findElement(By.xpath("(//input[@id='socialMonitor_input_postCheckbox_1']/following-sibling::span)[2]")));
       fluentWaitJsClick(driver.findElement(By.xpath("(//input[@id='socialMonitor_input_postCheckbox_1']/following-sibling::span)[2]")));
       fluentWaitMove(driver.findElement(By.xpath("//button[@id='socialMonitor_button_editMonitorsBtn']/following-sibling::button")));
       wait(1000);
       mouseActionClick(driver.findElement(By.xpath("//button[@id='socialMonitor_button_editMonitorsBtn']/following-sibling::button")));
       wait(1000);
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//li[@id='socialMonitor_moreOption_list_0']")));
       waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']//following::div[@class='ant-select-selector']")));
       wait(1000);
       Assert.assertTrue(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']/child::div[1]")).isDisplayed());
       Assert.assertTrue(driver.findElement(By.id("socialMonitor_bulkAction_textArea_comment")).isDisplayed());
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Send Email']/parent::div")));
       Assert.assertTrue(driver.findElement(By.xpath("//textarea[@id='socialMonitor_bulkAction_textArea_comment'][1]")).isDisplayed());
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']//following::div[@class='ant-select-selector']")));
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']/button")));
       wait(2000);
       //Validating  escalate by selecting 2 user
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//button[@id='socialMonitor_button_editMonitorsBtn']/following-sibling::button")));
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//li[@id='socialMonitor_moreOption_list_1']")));
       waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']//following::div[@class='ant-select-selector']")));
       Assert.assertTrue(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']/child::div[1]")).isDisplayed());
       Assert.assertTrue(driver.findElement(By.id("socialMonitor_bulkAction_textArea_comment")).isDisplayed());
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Send Email']/parent::div")));
       Assert.assertTrue(driver.findElement(By.xpath("//textarea[@id='socialMonitor_bulkAction_textArea_comment'][1]")).isDisplayed());
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@id='socialMonitor_bulkAction_div_container']//following::div[@class='ant-select-selector']")));
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']/button")));
       waitForElementToBeClickable(driver.findElement(By.xpath("(//input[@id='socialMonitor_input_postCheckbox_0']/parent::span)[2]")));
       waitForElementToBeClickable(driver.findElement(By.xpath("(//div[@id='socialMonitor_postDetail_div_postedDate_2']/preceding::div[1])[1]")));
       Assert.assertTrue(!(driver.findElement(By.xpath("(//div[@id='socialMonitor_postDetail_div_ownerName_2']/child::div[1])[1]")).getText()).isEmpty());
      
       //Logo image validartion for reviews
       //Assert.assertTrue(driver.findElement(By.xpath("(//div[@id='socialMonitor_postDetail_div_ownerName_2']/preceding::span[@class='ant-avatar-string'][1])[1]")).isDisplayed());
       Assert.assertTrue(!(driver.findElement(By.xpath("(//div[@id='socialMonitor_postDetail_div_ownerName_2']//following::div)[1]")).getText()).isEmpty());
       //Validating the apply macro
       waitForElementToBeClickable(driver.findElement(By.xpath("(//button[@id='socialMonitor_button_alert_flagged_2']/preceding::div[@class='ant-select-selector'][1])[1]")));
       wait(1000);
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//button[@id='socialMonitor_button_alert_flagged_2']/preceding::div[@class='ant-select-selector'][1])[1]")));
       
      
       // Assert.assertTrue(driver.findElement(By.xpath("(//div[@id='socialMonitor_button_alert_applyMacro_2_list'])[1]")).isDisplayed());
       waitForVisibilityOfElement(driver.findElement(By.xpath(" //div[@id='socialMonitor_button_alert_applyMacro_2_list']/ancestor::div[1]")));
       Assert.assertTrue(driver.findElement(By.xpath(" //div[@id='socialMonitor_button_alert_applyMacro_2_list']/ancestor::div[1]")).isDisplayed());
    
       
       //validate the flag
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//button[@id='socialMonitor_button_alert_flagged_2'])[1]")));
       wait(1000);
       waitForElementToBeClickable(driver.findElement(By.xpath("(//div[text()='apply macro']/ancestor::div[@class='ant-select-selector'])[last()]")));
       Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'want to remove')]")).isDisplayed());
       Assert.assertTrue(driver.findElement(By.id("socialMonitor_bulkAction_textArea_comment")).isDisplayed());
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Send Email']/parent::div")));
       Assert.assertTrue(driver.findElement(By.xpath("//textarea[@id='socialMonitor_bulkAction_textArea_comment'][1]")).isDisplayed());
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//div[text()='apply macro']/ancestor::div[@class='ant-select-selector'])[last()]")));
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Remove  Flag ']/preceding-sibling::button")));
       wait(1000);
       //Validating the escalation
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//button[@id='socialMonitor_button_alert_escalated_2'])[1]")));
       wait(1000);
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Private Note']/parent::div")));
       waitForElementToBeClickable(driver.findElement(By.xpath("(//textarea[@id='replyBox'])[1]")));
       Assert.assertTrue(driver.findElement(By.xpath("(//form[contains(@class,'ant-form ant-form-horizontal')]/preceding-sibling::div)[1]")).isDisplayed());
       waitForElementToBeClickable(driver.findElement(By.xpath("(//button[@id='socialMonitor_alertForm_button_cancel_2'])[1]")));
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Send Email']/parent::div")));
       Assert.assertTrue(driver.findElement(By.xpath("(//form[contains(@class,'ant-form ant-form-horizontal')]/preceding-sibling::div)[1]")).isDisplayed());
       waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//button[@id='socialMonitor_alertForm_button_cancel_2'])[1]")));
       wait(2000);
    }

    @Test()
    public void tc_06verifyingEscalation() throws Exception {
    	
    	 waitForElementToBeClickableAndClick(driver.findElement(By.id("menuList_socialMonitor")));
    	
        waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_div_socialPostingTab_3")));
        wait(2000);
       waitForElementToBeClickable(driver.findElement(By.xpath("(//input[@id='socialMonitor_input_postCheckbox_0']/following-sibling::span)[3]")));
       
        //its  only working for edge browser need to check
       // waitForElementToBeClickable(driver.findElement(By.xpath("(//input[@id='socialMonitor_input_postCheckbox_0']/following-sibling::span)[2]")));
       
       
        Assert.assertTrue(!(driver.findElement(By.xpath("(//div[@id='socialMonitor_postDetail_div_ownerName_3']/child::div[1])[1]")).getText()).isEmpty());
        Assert.assertTrue(driver.findElement(By.xpath("(//div[@id='socialMonitor_postDetail_div_ownerName_3']//preceding::div[1])[1]")).isDisplayed());
        Assert.assertTrue(!(driver.findElement(By.xpath("(//div[@id='socialMonitor_postDetail_div_ownerName_3']//following::div[1])[1]")).getText()).isEmpty());
        //Validating the apply macro
        waitForElementToBeClickable(driver.findElement(By.xpath("//input[@id='socialMonitor_button_escalation_applyMacro_3']/ancestor::div[1]")));
        wait(1000);
        moveToElement(driver.findElement(By.xpath("//input[@id='socialMonitor_button_escalation_applyMacro_3']/ancestor::div[1]")));
        click(driver.findElement(By.xpath("//input[@id='socialMonitor_button_escalation_applyMacro_3']/ancestor::div[1]")));
        
        waitForVisibilityOfElement(driver.findElement(By.xpath("//div[@id='socialMonitor_button_escalation_applyMacro_3_list']/ancestor::div[1]")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='socialMonitor_button_escalation_applyMacro_3_list']/ancestor::div[1]")).isDisplayed());
        //Validating the escalation
        waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_button_escalation_escalated_3")));
        wait(1000);
        
        //waitForElementToBeClickable(driver.findElement(By.xpath("(//div[text()='apply macro']/ancestor::div[@class='ant-select-selector'])[3]")));
        
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),' want to remove ')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("socialMonitor_bulkAction_textArea_comment")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Send Email']/parent::div")));
        Assert.assertTrue(driver.findElement(By.xpath("(//textarea[@id='socialMonitor_bulkAction_textArea_comment'][1])[1]")).isDisplayed());
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[last()]")));
        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']/button")));
    }

    @Test()
    public void tc_07verifyingResolution() throws Exception {       
    	if(loginData.getENV().equalsIgnoreCase(".sandbox")){
        	  wait(3000);
        	  waitForElementToBeClickableAndClick(driver.findElement(By.id("menuList_socialMonitor")));
        	  waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_div_socialPostingTab_4")));
        	  wait(2000);
        	  Assert.assertTrue(driver.findElements(By.xpath("//div[@id='socialMonitor_livePostsWrapper']/following::li[@class='ant-list-item']")).size()>0);
        	  Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-list-header'])[last()]")).isDisplayed());
        	}else {
        		 waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_div_socialPostingTab_4")));
               wait(2000);
               Assert.assertTrue(driver.findElement(By.xpath("(//div[text()='No data'])[last()]")).isDisplayed());
               Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-list-header'])[last()]")).isDisplayed());
			}
    }
    

    @Test()
    public void tc_08verifyingTrustedSource() throws Exception {
    	if(loginData.getENV().equalsIgnoreCase(".sandbox")){
    	 wait(3000);
    	  waitForElementToBeClickableAndClick(driver.findElement(By.id("menuList_socialMonitor")));
    	  waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_div_socialPostingTab_5")));
    	  Assert.assertTrue(driver.findElement(By.xpath("//div[@id='rc-tabs-2-panel-trusted_source']//following::li[@class='ant-list-item']")).isDisplayed());
    	  Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-list-header'])[last()]")).isDisplayed());
    	}else {
    		 waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_div_socialPostingTab_5")));
           Assert.assertTrue(driver.findElement(By.xpath("(//div[text()='No data'])[last()]")).isDisplayed());
           Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-list-header'])[last()]")).isDisplayed());
       
		}
    	}


    @Test()
    public void tc_09verifyingReports() throws Exception {
        waitForElementToBeClickableAndClick(driver.findElement(By.id("socialMonitor_div_socialPostingTab_6")));
        waitForElementToBeClickable(driver.findElement(By.xpath("(//div[text()='Download again']/parent::td)[last()]")));
        Assert.assertTrue(driver.findElement(By.xpath("//thead[@class='ant-table-thead']")).isDisplayed());
        Assert.assertTrue(!(driver.findElement(By.xpath("(//thead[@class='ant-table-thead']//th)[1]")).getText()).isEmpty());
        Assert.assertTrue(!(driver.findElement(By.xpath("(//tbody[@class='ant-table-tbody']//td)[1]")).getText()).isEmpty());
    }
}