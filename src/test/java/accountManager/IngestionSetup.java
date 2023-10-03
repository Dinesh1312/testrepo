package accountManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import driversAndLibrary.WebDriverImpl;

public class IngestionSetup extends WebDriverImpl{

    public static WebDriver driver;
    public static String sourceName,connectionName;
    public static int connectionsize,sourceListSize,errorCount=0;


    @BeforeClass
    public void browser() throws Exception {
    	 driver = accountManagerLogin();
    }
    
    @AfterClass
	public void afterclass() {
		browserQuit();
	}

    @Test()
    public void tc001_verifyingSettingsLandingPage() throws Exception {
        try {
            wait(3000);
            waitForElementToBeClickable(driver.findElement(By.id("menuList_settings")));
            fluentWaitJsClick(driver.findElement(By.id("menuList_settings")));
            //Validating the URL
        	wait(2000);
            Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/settings");
            //Validating the breadcrumbs title and breadcrumbs
            waitForElementToBeClickable(driver.findElement(By.id("header_acc_subtitle")));
            Assert.assertTrue(driver.findElement(By.id("header_acc_subtitle")).isDisplayed());
            waitForElementToBeClickable(driver.findElement(By.id("Settings_nav_label")));
            Assert.assertTrue(driver.findElement(By.id("Settings_nav_label")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.id("nav_breadcumb_title")).isDisplayed());
            //Validating the blocks present in the Settings landing page
            waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='account_accountSettings']/child::h3")));
            Assert.assertTrue(driver.findElement(By.xpath("//div[@id='account_accountSettings']/child::h3")).isDisplayed());
            waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='settings_ingestionSetup']/child::h3")));
            Assert.assertTrue(driver.findElement(By.xpath("//div[@id='settings_ingestionSetup']/child::h3")).isDisplayed());
            waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='settings_emailSetup']/child::h3")));
            Assert.assertTrue(driver.findElement(By.xpath("//div[@id='settings_emailSetup']/child::h3")).isDisplayed());
            waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='settings_abusiveword']/child::h3")));
            Assert.assertTrue(driver.findElement(By.xpath("//div[@id='settings_abusiveword']/child::h3")).isDisplayed());
            waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='settings_integrations']/child::h3")));
            Assert.assertTrue(driver.findElement(By.xpath("//div[@id='settings_integrations']/child::h3")).isDisplayed());
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test()
    public void tc002_verifyingIngestionSettingLandingPage() throws InterruptedException {
        try {
            //Validating the Ingestion Settings landing Page
            wait(3000);
            waitForElementToBeClickableAndClick(driver.findElement(By.id("settings_ingestionSetup")));
            wait(1000);
            Assert.assertTrue(fluentVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-title']/div"))).isDisplayed());
            Assert.assertTrue(fluentVisibilityOfElement(driver.findElement(By.xpath("(//div[@class='ant-drawer-body']//child::div)[2]"))).isDisplayed());
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

   // @Test()
    public void tc003_verifyingAllIngestionSource() throws Exception {
        //Validating all Ingestion source
        sourceListSize=driver.findElements(By.xpath("//div[text()='Source Type']/following-sibling::div/div")).size();
        for (int i=0;i<sourceListSize;i++) {
            try {
                //Selecting particular source
                fluentScrollIntoElement(driver.findElements(By.xpath("//div[text()='Source Type']/following-sibling::div/div")).get(i));
                waitForElementToBeClickable(driver.findElements(By.xpath("//div[text()='Source Type']/following-sibling::div/div")).get(i));
                connectionName = ((driver.findElements(By.xpath("//div[text()='Source Type']/following-sibling::div/div/div[1]"))).get(i).getText());
                wait(2000);
                waitForElementToBeClickableAndClick(driver.findElements(By.xpath("//div[text()='Source Type']/following-sibling::div/div")).get(i));
                connectionsize = driver.findElements(By.xpath("(//div[contains(@id,'connection')]//following::div)[1]")).size();
                if (connectionsize != 0) {
                    //Validating the source which is having the connection
                    waitForElementToBeClickable(driver.findElement(By.xpath("(//div[contains(@id,'connection')]//following::div)[1]")));
                    Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Configuration')]/parent::div")).isDisplayed());
                    wait(2000);
                    waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[contains(@id,'connection')]//following::div[1]")));
                    waitForElementToBeClickable(driver.findElement(By.xpath("(//button[@class='ant-drawer-close'])[last()]")));
                    //Validating the point of contact field
                    fluentScrollIntoElement(driver.findElement(By.xpath("//button[contains(@id,'poc')]")));
                    waitForElementToBeClickable(driver.findElement(By.xpath("//button[contains(@id,'poc')]")));
                    Assert.assertTrue((driver.findElement(By.xpath("(//div[contains(text(),'Configuration')])[2]")).getText()).contains("Configuration"));
                    waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//button[contains(@id,'formback')]")));
                    waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Back']//parent::button[1]")));
                    fluentWaitJsClick(driver.findElement(By.xpath("//div[text()='Back']//parent::button[1]")));
                    System.out.println("Successfully Verified : " + connectionName);
                } else {
                    //Validating the source which is not having the connection
                    waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='New Connection']/parent::button")));
                    Assert.assertTrue(driver.findElement(By.xpath("(//div[contains(text(),'Configuration')])[1]")).isDisplayed());
                    wait(2000);
                    sourceName = driver.findElement(By.xpath("//div[contains(text(),'Configuration')]")).getText();
                    //The below-mentioned connections will navigate to their own website - So "Avoid" that.
                    if (!(("Docusign Configuration ,MergeHRI Configuration ,MergeATS Configuration ,Jira Configuration ,HubSpot Configuration ,ZohoCRM Configuration ,Azure Configuration ").contains(sourceName))) {
                        //Validating the connection name field in new connection
                        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='New Connection']/parent::button")));
                        wait(1500);
                        waitForElementToBeClickable(driver.findElement(By.xpath("//input[contains(@id,'connection_name') or contains(@id,'connectionname')]")));
                        Assert.assertTrue(driver.findElement(By.xpath("(//div[contains(text(),'Configuration')])[2]")).isDisplayed());
                        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//button[contains(@id,'formback')]")));
                        wait(2000);
                        waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Back']//parent::button[1]")));
                        fluentWaitJsClick(driver.findElement(By.xpath("//div[text()='Back']//parent::button[1]")));
                    } else if (sourceName.contains("Azure")) {
                        //Validating the Connect Azure Active Directory toggle
                        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='New Connection']/parent::button")));
                        waitForElementToBeClickable(driver.findElement(By.xpath("//span[text()='Connect Azure Active Directory']/following-sibling::button")));
                        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Configuration')]")).isDisplayed());
                        wait(1500);
                        waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//button[contains(@id,'formback') or contains (@id,'drawerback')]")));
                    }else{
                        wait(2000);
                        waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Back']//parent::button[1]")));
                        fluentWaitJsClick(driver.findElement(By.xpath("//div[text()='Back']//parent::button[1]")));
                    }
                    System.out.println("Successfully Verified : " + connectionName);
                }
            } catch (Exception e) {
                System.err.println("Error in Validating : " + connectionName);
                e.printStackTrace();
               Assert.fail();
            }
        }
    }

    @Test()
    public void tc004_VerifyingActivityFeed() {
        try {
            //Validating the activity feed drawer
      
        	wait(3000);
            waitForElementToBeClickableAndClick(driver.findElement(By.id("ingestionSettings_activityFeeds_button")));
            waitForElementToBeClickable(driver.findElement(By.id("ingestionSettings_activityFeeds_viewAllActivity_button")));
            Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Activity Feeds']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-card-body']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-card-meta-avatar'])[1]")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-card-meta-detail'])[1]")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-card-meta-title'])[1]")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-card-meta-description'])[1]")).isDisplayed());
            //Validating the activity feed page url, title, breadcrumbs
            waitForElementToBeClickableAndClick(driver.findElement(By.id("ingestionSettings_activityFeeds_viewAllActivity_button")));
            waitForElementToBeClickable(driver.findElement(By.id("admin_button_filter")));
            Assert.assertEquals(driver.getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/settings/activityfeeds");
            Assert.assertTrue(driver.findElement(By.id("header_acc_subtitle")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.id("Settings_nav_label")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.id("ActivityFeeds_nav_label")).isDisplayed());
            //Validating the table header and body
            Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-table-header']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-table-body']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("//div[text()='No activity found.' or @id='description_0']")).isDisplayed());
            Assert.assertTrue(!(driver.findElement(By.id("name_0")).getText()).isEmpty());
            Assert.assertTrue(driver.findElement(By.xpath("//th[text()='Date & time']")).isDisplayed());
            //Validating search filter
            waitForElementToBeClickableAndClick(driver.findElement(By.id("admin_button_filter")));
            Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Search and Filter']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Search']")).isDisplayed());
            waitForElementToBeClickable(driver.findElement(By.xpath("//div[contains(@id,'input_box')]//following::input")));
            waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//span[text()='Select a Role']//ancestor::div[1]")));
            //validating Role dropdown
            waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Sales Admin']/ancestor::div[2]")));
            waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Super Admin']/ancestor::div[2]")));
            waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='Onboarding Admin']/ancestor::div[2]")));
            waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='CS Admin']/ancestor::div[2]")));
            waitForElementToBeClickable(driver.findElement(By.xpath("//div[text()='XPA Admin']/ancestor::div[2]")));
            //Validating date filter
            waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//input[@placeholder='Start date']")));
            waitForElementToBeClickable(driver.findElement(By.xpath("(//th[text()='Su'])[1]")));
            Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-picker-panels']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-picker-header'])[1]")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-picker-body'])[1]")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-picker-header'])[2]")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-picker-body'])[2]")).isDisplayed());
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

}
