package accountManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;

public class Settings extends WebDriverImpl {

	@BeforeClass
	public void browser() throws Exception {
		 driver = accountManagerLogin();

	}


	@AfterClass
	public void afterclass() {
		browserQuit();
	}
	@Test
	public void tc01_settingsLandingPage() throws Exception {
		List<String> exp_integerationlist, exp_accountsettingDrawerHeade;
		click(driver.findElement(By.id("menuList_settings")));
		WebElement title_element = driver.findElement(By.id("nav_breadcumb_title"));
		fluentVisibilityOfElement(title_element);
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/settings");

		Assert.assertEquals(title_element.getText(), "Automation account -Dont delete");

		List<String> act_settingcardsHeaders = driver
				.findElements(By.xpath("//h3[@id='nav_breadcumb_title']//following::h3")).stream().map(m -> m.getText())
				.collect(Collectors.toList());
		List<String> exp_settingscardsHeaders = Arrays.asList("Account Settings", "Ingestion Setup", "Email setup",
				"Abusive Word Management", "Integrations");
		Assert.assertEquals(act_settingcardsHeaders, exp_settingscardsHeaders);
		click(driver.findElement(By.id("account_accountSettings")));
		fluentVisibilityOfElement(driver.findElement(By.xpath("//div[@title='" + title_element.getText() + "']")));
		wait(3000);
		List<String> act_accountsettingDrawerHeader = driver
				.findElements(By.xpath("//span[@class='ant-collapse-header-text']/div")).stream().map(m -> m.getText())
				.collect(Collectors.toList());
		//if(loginData.getENV().equalsIgnoreCase(".sandbox")){
			 exp_accountsettingDrawerHeade = Arrays.asList("General Settings", "Advanced Analytics","Hierarchy Settings",
					"Public Profile Settings", "Review Management Settings", "Social Share Settings", "Facebook", "Twitter",
					"LinkedIn", "Send Settings", "Account");
			 
		
		//}
//		else {
//			 exp_accountsettingDrawerHeade = Arrays.asList("General Settings", "Advanced Analytics",
//					"Public Profile Settings", "Review Management Settings", "Social Share Settings", "Facebook", "Twitter",
//					"LinkedIn", "Send Settings", "Account");
//		}

		System.out.println(act_accountsettingDrawerHeader);
		Assert.assertEquals(act_accountsettingDrawerHeader, exp_accountsettingDrawerHeade);
		click(driver.findElement(By.id("accountSettings_button_cancelAccSetting")));

		// ==========Email SetUp=================

		click(driver.findElement(By.id("settings_emailSetup")));
		wait(3000);
		fluentVisibilityOfElement(driver.findElement(By.id("nav_breadcumb_title")));
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/settings/emailsetup");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='jodit-toolbar__box']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='jodit-workplace']")).isDisplayed());

		Assert.assertTrue(
				driver.findElement(By.id("settings_emailsetup_dynamic_fields_dynamicfields_card")).isDisplayed());
		
		List<String> act_Emaildynamicfields = driver
				.findElements(By.xpath("//input[@placeholder='Eg.Account Name']//following::input[contains(@id,'field')]")).stream().map(m -> m.getAttribute("value").trim())
				.collect(Collectors.toList());
		List<String> exp_Emaildynamicfields = Arrays.asList("[Account Name]", "[Organization Name]","[VerificationURL]", "[User Name]",
				"[User First Name]","[Recipient Email Address]");
		Assert.assertEquals(act_Emaildynamicfields, exp_Emaildynamicfields);
		
		click(driver.findElement(By.id("Settings_nav_label")));

		// ===========AbusiveManagement==============

		click(driver.findElement(By.id("settings_abusiveword")));
		fluentVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']")));
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']")).getText(),
				"Abusive Word Management");
		Assert.assertTrue(driver.findElement(By.name("swearWords")).isDisplayed());

		Assert.assertTrue(driver.findElement(By.id("abusiveWordSettings_submit_button")).isEnabled());
		fluentVisibilityOfElement(driver.findElement(By.id("abusiveWordSettings_back_button")));
		safeJavaScriptClick(driver.findElement(By.id("abusiveWordSettings_back_button")));
		fluentVisibilityOfElement(driver.findElement(By.id("nav_breadcumb_title")));

		// ======integeration========================
		click(driver.findElement(By.id("settings_integrations")));
		fluentVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-title']")));
		String integerationDrawerTitle = driver.findElement(By.xpath("//div[@class='ant-drawer-title']")).getText().trim();
		Assert.assertEquals(integerationDrawerTitle.trim(), "Integration Settings");

		List<String> act_integerationlist = driver.findElements(By.xpath("//span[@style='margin-left: 10px;']"))
				.stream().map(m -> m.getText().trim()).collect(Collectors.toList());

//				if(loginData.getENV().equalsIgnoreCase(".sandbox")){
				 exp_integerationlist = Arrays.asList("MicrosoftTeams", "Salesforce", "Slack","TotalExpert");
//				}
//				else {
//					 exp_integerationlist = Arrays.asList("MicrosoftTeams", "Slack", "TotalExpert");
//				}
		System.out.println(act_integerationlist);
		Assert.assertEquals(act_integerationlist, exp_integerationlist);
		safeJavaScriptClick(driver.findElement(By.id("ingestion_MicrosoftTeams")));
		fluentVisibilityOfElement(driver.findElement(By.id("integration_title")));
		String integerationDrawerMSTitle = driver.findElement(By.id("integration_title")).getText().trim();
		Assert.assertEquals(integerationDrawerMSTitle, "MS Teams Configuration");
		List<String> act_integerationMslist = driver
				.findElements(By.xpath("//button[@role='switch']//preceding::span[1]")).stream().map(m -> m.getText().trim())
				.collect(Collectors.toList());
		List<String> exp_integerationMslist = Arrays.asList("Automation", "Transferred Reviews","Public Reviews Campaign");
		Assert.assertEquals(act_integerationMslist, exp_integerationMslist);
		click(driver.findElement(By.id("msteams_drawerback")));
		fluentVisibilityOfElement(driver.findElement(By.id("ingestion_Slack")));
		safeJavaScriptClick(driver.findElement(By.id("ingestion_Slack")));
		fluentVisibilityOfElement(driver.findElement(By.id("integration_title")));
		Assert.assertEquals(driver.findElement(By.id("integration_title")).getText().trim(), "Slack Configuration");
		Assert.assertTrue(driver.findElement(By.id("slack_newconnection")).isEnabled());
		safeJavaScriptClick(driver.findElement(By.id("slack_drawerback")));
		
		fluentVisibilityOfElement(driver.findElement(By.id("ingestion_TotalExpert")));
		safeJavaScriptClick(driver.findElement(By.id("ingestion_TotalExpert")));
		fluentVisibilityOfElement(driver.findElement(By.id("integration_title")));
		Assert.assertEquals(driver.findElement(By.id("integration_title")).getText().trim(), "TotalExpert Configuration");
		Assert.assertTrue(driver.findElement(By.id("totalexpert_newconnection")).isEnabled());
		safeJavaScriptClick(driver.findElement(By.id("totalexpert_drawerback")));
		
		fluentVisibilityOfElement(driver.findElement(By.id("integrationSettings_drawer_closeButton")));
		safeJavaScriptClick(driver.findElement(By.id("integrationSettings_drawer_closeButton")));
		fluentVisibilityOfElement(driver.findElement(By.id("menuList_dashboard")));
	    wait(2000);
		click(driver.findElement(By.id("menuList_dashboard")));
	}
	

}




















