package agentLogin;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;


public class Campaign extends WebDriverImpl{
	public static WebDriver driver;

	@BeforeClass
	public void beforeClass() throws Exception {
		driver = agentLogin();
	}

	@AfterClass
	public void afterClass() {
		WebDriverImpl.browserQuit();
	}

	@Test()
	public void tc01_verifyAgentLandingPage() throws Exception {
		wait(3000);
		Assert.assertEquals(driver.getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/user-dashboard");
		Assert.assertEquals(driver.findElement(By.id("nav_breadcumb_title")).getText(),"Automation account -Dont delete");
		Assert.assertEquals(driver.findElement(By.id("AccountManagerDashboard_nav_label")).getText(),"User Dashboard");
		if(loginData.getENV().equals(".sandbox")) {
			Assert.assertEquals(driver.findElement(By.xpath("//button[@id='product_guide']//preceding::span[1]")).getText().toLowerCase(),"org - automation organization -dont delete".toLowerCase());
		}else if(loginData.getENV().equals(".preprod")){
			Assert.assertEquals(driver.findElement(By.xpath("//button[@id='product_guide']//preceding::span[1]")).getText().toLowerCase(),"org - automation organization".toLowerCase());
		}
		else {
			Assert.assertEquals(driver.findElement(By.xpath("//button[@id='product_guide']//preceding::span[1]")).getText().toLowerCase(),"ORG - EXPERIENCE.COM ONBOARDING TEST ORG".toLowerCase());
		}
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='drawerProfile']/label")).getText(),"Automation Agent1");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='drawerProfile']//following::label")).getText(),"Agent");
		Assert.assertEquals(driver.findElement(By.xpath("(//div[contains(@id,'publish_name')])[1]")).getText(),"Automation Agent1");
		System.out.println("Landed successfully");
		wait(2000);
		click(By.xpath(("//li[@id='menuList_campaigns']/div")));
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='drawerProfile']/label")).getText(),"Automation Agent1");
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='Campaigns_nav_label']")).getText(), "Campaigns");
		Assert.assertTrue(driver.findElement(By.id("campaign_dashboard_page")).isDisplayed(), "Campaign page is not displayed");
		implicitlyWait(20);
		wait(2000);
		int campaignRatingsCards = driver.findElements(By.xpath("//span[contains(@id,'campaign_dashboard_metricsBar')]/following-sibling::span")).size();
		if (campaignRatingsCards<=4) {	 
			Assert.assertTrue(true,"Campaign Ratings List are Present");
		}else {
			Assert.assertFalse(true, "Campaign Ratings List are not availbale");
		}
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("campaign_dashboard_metricsBar_Total Surveys Sent")).getText())>0);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("campaign_dashboard_metricsBar_Total Responses")).getText())>0);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("campaign_dashboard_metricsBar_Average Completion Rate")).getText())>0);
	}	

	@Test()
	public void tc02_verifyActivityFeedDrawer() throws Exception {
		try {
			Assert.assertTrue(driver.findElement(By.id("campaign_activity_feed")).isEnabled());
			click( driver.findElement(By.id("campaign_activity_feed")));
			wait(2000);
			fluentVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']")));
			Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']")).getText(),"Activity Feeds");
		}finally {
			click(driver.findElement(By.xpath("//span[@aria-label='close']//*[local-name()='svg']")));
		}
	}

	@Test()
	public void tc03_verifyManualSurveyFields() throws Exception {
		try {
			wait(2000);
			waitForVisibilityOfElement(driver.findElement(By.xpath("//input[@placeholder='Search']")));
			input(By.xpath("//input[@placeholder='Search']"),"Automation");
			//click(driver.findElement(By.xpath("//a[@title='Automation']/following::span[@id='campaign_campaignsList_list_threeDot']")));
			click(driver.findElement(By.xpath("//a[@title='Automation']/following::span[@id='undefined_list_threeDot']")));
			click(driver.findElement(By.xpath("	//span[text()='Send Manual Survey']")));
			waitForVisibilityOfElement(driver.findElement(By.xpath("//div[text()='Send Manual Survey']")));
			Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Send Manual Survey']")).getText(), "Send Manual Survey");
			fluentVisibilityOfElement(driver.findElement(By.id("campaign_drawer_manualSurveySend_addDetailsButton")));
			wait(2000);
			moveToElement(driver.findElement(By.id("campaign_drawer_manualSurveySend_addDetailsButton")));
			safeJavaScriptClick(driver.findElement(By.id("campaign_drawer_manualSurveySend_addDetailsButton")));
			Assert.assertTrue(driver.findElements(By.xpath("//div[text()='Customer First Name ']//following::input")).stream().allMatch(m -> m.isEnabled()));
		}finally {
			wait(1000);
			moveToElement(driver.findElement(By.id("undefined_closeButton")));
			safeJavaScriptClick(driver.findElement(By.id("undefined_closeButton")));
			wait(1000);
			click(driver.findElement(By.id("campaign_drawer_manualSurveySend_closeButton")));
		}
	}

	@Test()
	public void tc04_verifyViewAnalytics() throws Exception {
		wait(2000);
		click(driver.findElement(By.xpath("//a[@title='Automation']/following::span[text()='View Analytics']")));
		fluentVisibilityOfElement(driver.findElement(By.id("campaign_container_card")));
		Assert.assertTrue(driver.findElement(By.id("campaign_container_card")).isDisplayed(), "Campaign page is not displayed");
		if (driver.findElement(By.id("undefined_public_campaignName")).isEnabled() && driver.findElement(By.id("undefined_internal_campaignName")).isEnabled()) {
			Assert.fail();
		}
		List<String> campaignDetailList = driver.findElements(By.xpath("//li[@id='campaign_sideBar_campaignDetails_li']//div/span")).stream().map(M->M.getText()).collect(Collectors.toList());
		List<String> expectedCampaignDetailList = Arrays.asList("Campaign Settings","Primary Questions","Email Setup","SMS Setup","Secondary Workflow","Set Conditions","Update Campaign");
		Assert.assertEquals(campaignDetailList,expectedCampaignDetailList);
	}

	@Test()
	public void tc05_verifyOverview() throws Exception {
		wait(2000);
		moveToElement(driver.findElement(By.xpath("(//li[@id='campaign_sideBar_analytics_li'])[1]/span")));
		List<String> analyticsList = driver.findElements(By.xpath("//li[@id='campaign_sideBar_analytics_li']//div")).stream().map(M->M.getText()).collect(Collectors.toList());
		List<String> expectedAnalyticsList = Arrays.asList("Overview","Question Summary","Reviews","Leaderboard","Incomplete Surveys");
		Assert.assertEquals(analyticsList,expectedAnalyticsList);
		fluentVisibilityOfElement(driver.findElement(By.xpath("(//li[@id='campaign_sideBar_analytics_li'])[1]")));
		click(By.xpath("(//li[@id='campaign_sideBar_analytics_li'])[1]/span"));
		Assert.assertTrue(driver.findElement(By.id("campaign_content_body_wrapper")).isDisplayed(), "Campaign Overview Page is not displayed");
		List<String> leaderboardTableColumnLists = driver.findElements(By.xpath("//div[contains(@id,'campaign_date_filter_range')]")).stream().map(M->M.getText()).collect(Collectors.toList());
		List<String> expectedLeaderboardTableColumnLists = Arrays.asList("All","Today","7D","1M","6M","1Y");
		Assert.assertEquals(leaderboardTableColumnLists,expectedLeaderboardTableColumnLists);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("campaign_overview_primaryStatus_totalResponses")).getText())>0);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("campaign_overview_primaryStatus_totalReviews")).getText())>0);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("campaign_overview_primaryStatus_ratings")).getText())>0);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("campaign_overview_promoterScoreGauge_gatewayPromoterCount")).getText())>0);
	}

	@Test()
	public void tc06_verifyQuestionSummary() throws Exception {	
		wait(2000);
		moveToElement(driver.findElement(By.xpath("(//li[@id='campaign_sideBar_analytics_li'])[2]/span")));
		click(By.xpath("(//li[@id='campaign_sideBar_analytics_li'])[2]/span"));
		wait(5000);
		fluentVisibilityOfElement(driver.findElement(By.id("campaign_reports_surveyStats_parentBlock")));
		Assert.assertTrue(driver.findElement(By.id("campaign_reports_surveyStats_parentBlock")).isDisplayed(),"Campaign Question Summary Page is not displayed");
		System.out.println(driver.findElement(By.xpath("(//div[text()='Ratings']//preceding::div[1])[1]")).getText());
		System.out.println(driver.findElement(By.xpath("(//div[text()='Ratings']//preceding::div[1])[2]")).getText());
	}

	@Test()
	public void tc07_verifyReviews() throws Exception {	
		wait(2000);
		moveToElement(driver.findElement(By.xpath("(//li[@id='campaign_sideBar_analytics_li'])[3]/span")));
		click(By.xpath("(//li[@id='campaign_sideBar_analytics_li'])[3]/span"));
		waitForVisibilityOfElement(driver.findElement(By.id("campaign_content_body_wrapper")));
		Assert.assertTrue(driver.findElement(By.id("campaign_content_body_wrapper")).isDisplayed(),"Campaign Reviews Page is not displayed");
		waitForVisibilityOfElement(driver.findElement(By.xpath("//div[@id='campaign_content_header_wrapper']//button")));
		wait(2000);
		moveToElement(driver.findElement(By.xpath("//div[@id='campaign_content_header_wrapper']//button")));
		click(By.xpath("//div[@id='campaign_content_header_wrapper']//button"));
		Assert.assertTrue(driver.findElements(By.xpath("//div[text()='Search']//following::input")).stream().allMatch(m->m.isEnabled()));
	}

	@Test()
	public void tc08_verifyLeaderboard() throws Exception {
		wait(2000);
		click(By.xpath("(//li[@id='campaign_sideBar_analytics_li'])[4]/span"));
		fluentVisibilityOfElement(driver.findElement(By.id("campaign_content_body_wrapper")));
		Assert.assertTrue(driver.findElement(By.id("campaign_content_body_wrapper")).isDisplayed(),"Campaign Leaderboard Page is not displayed");
		fluentVisibilityOfElement(driver.findElement(By.xpath("//th[@class='ant-table-cell']")));
		wait(5000);
		
		List<String> leaderboardTableColumnLists = driver.findElements(By.xpath("//th[text()='Completion Rate']/preceding::th")).stream().map(M->M.getText()).collect(Collectors.toList());
		List<String> expectedLeaderboardTableColumnLists = Arrays.asList("Rank","User","Reviews","Ranking Score","NPS","Average Score","SPS");
		Assert.assertEquals(leaderboardTableColumnLists,expectedLeaderboardTableColumnLists);
		moveToElement(driver.findElement(By.xpath("//th[text()='Completion Rate']")));
		Assert.assertEquals(driver.findElement(By.xpath("//th[text()='Completion Rate']")).getText(), "Completion Rate");
	}

	@Test()
	public void tc09_verifyIncompleteSurveys() throws Exception {	
		wait(2000);
		click(By.xpath("(//li[@id='campaign_sideBar_analytics_li'])[5]/span"));
		//fluentVisibilityOfElement(driver.findElement(By.xpath("//th[@class='ant-table-cell']/span")));
		wait(3000);
		List<String> incompleteSurveysTableColumnLists = driver.findElements(By.xpath("//th[@class='ant-table-cell']/span")).stream().map(M->M.getText()).collect(Collectors.toList());
		List<String> expectedIncompleteSurveysTableColumnLists = Arrays.asList("Customer Info","User Info","Participant Type","Transaction ID","Email & SMS Sent");
		Assert.assertEquals(incompleteSurveysTableColumnLists,expectedIncompleteSurveysTableColumnLists);
		wait(1000);
		safeJavaScriptClick(driver.findElement(By.id("admin_button_search")));
		Assert.assertTrue(driver.findElement(By.id("admin_input_searchInputField")).isEnabled());
	}	

	@Test()
	public void tc10_verifyVersionHistory() throws Exception {
	try {
		wait(2000);
		moveToElement(driver.findElement(By.id("campaign_sideBar_versionHistory")));
		click(By.id("campaign_sideBar_versionHistory"));
		waitForVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-header']")));
		wait(2000);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ant-drawer-header']")).getText(), "Version History");
		fluentVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-body']")));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-drawer-body']")).isDisplayed(), "Version History Drawer Not contains data");
	}finally {
		fluentVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-body']//button")));
		moveToElement(driver.findElement(By.xpath("//div[@class='ant-drawer-body']//button")));
		wait(3000);
		safeJavaScriptClick(driver.findElement(By.xpath("//div[@class='ant-drawer-body']//button")));
	}
	}		

	@Test()
	public void tc11_verifyIncompleteSurveyInSideTab() throws Exception {
		wait(5000);
		moveToElement(driver.findElement(By.id("menu_incomplete_survey_li")));
	    click(driver.findElement(By.id("menu_incomplete_survey_li")));
		wait(5000);	
		Assert.assertTrue(driver.findElement(By.xpath("//th[@title='Campaign Name']")).isDisplayed());
		click(By.id("admin_button_search"));
		Assert.assertTrue(driver.findElement(By.id("admin_input_searchInputField")).isEnabled());
		driver.findElement(By.xpath("//div[text()=' Filters ']//ancestor::button[1]")).isEnabled();
		click(By.xpath("//div[text()=' Filters ']//ancestor::button[1]"));
		Assert.assertTrue(driver.findElements(By.id("campaignsselectincomplete")).stream().allMatch(m->m.isEnabled()));
		Assert.assertTrue(driver.findElement(By.id("incomplete_survey_rangepicker")).isEnabled());
	}

}


