package accountManager;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;


public class AccountDashBoard extends WebDriverImpl{

	public static WebDriver driver;
	public static Wait<WebDriver> fluentwait;
	public static List<String> arrayList=new ArrayList<>();
	public static List<Double> arrayScoreList=new ArrayList<>();

	@BeforeClass
	public void browser() throws Exception {
		
		 driver = accountManagerLogin();
	}

	@AfterClass
	public void afterClass() throws Exception {
		browserQuit();
	}

	@Test()
	public void tc001_accountDashboardLandingPage() {

		try {
		fluentVisibilityOfElement(driver.findElement(By.id("menuList_campaigns")));

		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/dashboard");

		Assert.assertEquals(driver.findElement(By.id("nav_breadcumb_title")).getText(), "Automation account -Dont delete");

		Assert.assertTrue(driver.findElements(By.cssSelector("*[role='menuitem']")).stream().allMatch(m->m.isDisplayed()),"Left side Menu list not displayed");

		List<String> actualSideMenuList = driver.findElements(By.cssSelector("*[role='menuitem']")).stream().map(m->m.getAttribute("data-menu-id")).collect(Collectors.toList());

		List<String> expectedSideMenuList = Arrays.asList("navSidePanel_div-/user/account/dashboard","navSidePanel_div-/user/account/hierarchy","navSidePanel_div-/user/account/unprocessed","navSidePanel_div-/user/account/settings",
				"navSidePanel_div-/user/account/listing/dashboard","navSidePanel_div-/user/account/reviews","navSidePanel_div-/user/account/reports","navSidePanel_div-/user/account/campaigns","navSidePanel_div-/user/account/socialmonitor",
				"navSidePanel_div-/user/account/transaction","navSidePanel_div-/user/account/widgets");
		Assert.assertEquals(actualSideMenuList,expectedSideMenuList);

		Assert.assertTrue(driver.findElement(By.id("page-container")).isDisplayed(), "Dashboard page is not displayed");
		Assert.assertTrue(driver.findElements(By.cssSelector("div.ant-card-head-title  h3")).stream().allMatch(m->m.isDisplayed()), "Dashboard Profile Card is not displayed");

		List<String> dashboardPageProfileCardTitle = Arrays.asList("Survey Trend","Reviews","Listings","Profiles","Advanced Analytics","Industry Benchmark Analytics","Active Campaigns","Leaderboard");
		List<String> acutalDashboardPageProfileCardTitle = driver.findElements(By.cssSelector("div.ant-card-head-title  h3")).stream().map(d->d.getText().trim()).collect(Collectors.toList());
		Assert.assertEquals(acutalDashboardPageProfileCardTitle, dashboardPageProfileCardTitle);

		waitForElementToBeClickable(driver.findElement(By.id("filteringandexport_all")));
		arrayList=driver.findElements(By.xpath("//div[contains(@id,'filteringandexport') and @label]")).stream()
				.map(d->d.getAttribute("label")).collect(Collectors.toList());
		List<String> asList = Arrays.asList("all","today","7D","1M","6M","1Y");
		Assert.assertEquals(arrayList, asList);

		wait(3000);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("dashboard_summaryCard_account_starRating_count")).getText())>0);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("dashboard_summaryCard_account_usersCount")).getText())>0);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("dashboard_summaryCard_account_tiers_count")).getText())>0);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("dashboard_summaryCard_account_completionRate_percent")).getText().replace("%", ""))>0);
		
		} catch (Exception e) {
			Assert.fail();
		}
	}

		
	@Test()
	public void tc002_leaderboardLandingPage() {

		try {
		waitForElementToBeClickableAndClick(By.id("dashboard_leaderBoard_viewArrow"));
		wait(3000);
		fluentVisibilityOfElement(driver.findElement(By.id("Leaderboard_nav_label")));
		Assert.assertEquals(driver.findElement(By.id("Leaderboard_nav_label")).getText(), "Leaderboard");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/leaderboard");
		

		Assert.assertTrue(driver.findElements(By.cssSelector("#leaderBoard_topRanks_cardBlocks>div")).stream().allMatch(m->m.isDisplayed()), "Leaderboard TopRank Cards is not displayed");

		List<String> expectedTableHeader = Arrays.asList("Rank","User","Reviews","Ranking Score","NPS","Average Score","SPS","Completion Rate");

		List<String> actualTableHeader = driver.findElements(By.cssSelector("thead.ant-table-thead th")).stream().map(d->d.getText()).collect(Collectors.toList());

		Assert.assertEquals(actualTableHeader,expectedTableHeader);
		Assert.assertTrue(driver.findElements(By.cssSelector(".ant-table-tbody tr")).size()>0);
		
		click(By.id("admin_button_filter"));
		
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ant-card-head-title")).isDisplayed(), "Filter title is not displayed");
		
		Assert.assertTrue(driver.findElement(By.xpath("//form[@id='leaderboard_filter']/parent::div")).isDisplayed(), "Filter fields are not displayed");
		
		Assert.assertTrue(driver.findElements(By.cssSelector("span.ant-select-selection-placeholder")).stream().allMatch(m->m.isDisplayed()), "Filters are not displayed");

		
		
		} finally {
			back();
		}
	}

	
	
	@Test()
	public void tc003_reportsLandingPage() {
		try {
		waitForElementToBeClickableAndClick(By.id("menuList_reports"));
		wait(5000);
		fluentVisibilityOfElement(driver.findElement(By.id("Reports_nav_label")));
		Assert.assertEquals(driver.findElement(By.id("Reports_nav_label")).getText(), "Reports");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/reports");
		
		Assert.assertTrue(driver.findElement(By.id("reports_reportCard_container")).isDisplayed(),"Report card container is not displayed");
		Assert.assertTrue(driver.findElements(By.cssSelector("span.ant-select-selection-search")).stream().allMatch(m->m.isDisplayed()), "Reports fields is not displayed");
		Assert.assertEquals(driver.findElement(By.cssSelector("#reports_reportCard_reportButton_submitButton div")).getText().trim(),"Export Report");
		Assert.assertTrue(driver.findElements(By.cssSelector("#reports_activityFeeds_activityCard_cardBlock>div")).stream().allMatch(m->m.isDisplayed()), "Activity field container card block is not displayed");
		Assert.assertTrue(driver.findElements(By.cssSelector("#reports_activityFeeds_activityCard_cardBlock>div")).size()<=10);
		
		} catch (Exception e) {
			Assert.fail();
		}
		reportsActivityFeedLandingPage();
		}
	
	
	
	
	
	
	
	
	public void reportsActivityFeedLandingPage() {
		try {
		waitForElementToBeClickableAndClick(By.id("reports_activityFeeds_viewAllButton"));
		wait(5000);
		fluentVisibilityOfElement(driver.findElement(By.id("ActivityFeeds_nav_label")));
		Assert.assertEquals(driver.findElement(By.id("ActivityFeeds_nav_label")).getText(), "Activity Feeds");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/reports/viewallactivity");
		
		
		List<String> expectedTableHeader = Arrays.asList("Activity","Status","Action","Date & time");
		List<String> actualTableHeader = driver.findElements(By.cssSelector("thead.ant-table-thead th")).stream().map(d->d.getText()).collect(Collectors.toList());
		Assert.assertEquals(actualTableHeader,expectedTableHeader);
		
		Assert.assertTrue(driver.findElements(By.cssSelector(".ant-table-tbody tr")).size()==10);
		footerValidation();
		} finally {
			back();
		}
	}
	
	
	
}
