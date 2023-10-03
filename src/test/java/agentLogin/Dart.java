package agentLogin;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;
public class Dart  extends WebDriverImpl {

	public static WebDriver driver;
	public static Wait<WebDriver> fluentwait;
	public static List<String> arrayList=new ArrayList<>();
	public static List<Double> arrayScoreList=new ArrayList<>();

	@BeforeClass
	public void browser() throws Exception {
		driver = agentLogin();
	}

	@AfterClass
	public void afterClass() throws Exception {
		browserQuit();
	}

	@Test()
	public void tc01_DashboardLandingPage() {

		try {
			wait(3000);
			fluentVisibilityOfElement(driver.findElement(By.id("menuList_campaigns")));
			wait(3000);
			if (loginData.getENV().equals(".preprod")) {
				Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/dashboard");

			}else {
				Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/user-dashboard");
			}
			Assert.assertEquals(driver.findElement(By.id("nav_breadcumb_title")).getText(), "Automation account -Dont delete");
			Assert.assertTrue(driver.findElements(By.cssSelector("*[role='menuitem']")).stream().allMatch(m->m.isDisplayed()),"Left side Menu list not displayed");
			List<String> actualSideMenuList = driver.findElements(By.cssSelector("*[role='menuitem']")).stream().map(m->m.getAttribute("data-menu-id")).collect(Collectors.toList());
			List<String> expectedSideMenuList = Arrays.asList("navSidePanel_div-/user/account/user-dashboard","navSidePanel_div-/user/account/hierarchy/preview","navSidePanel_div-/user/account/reviews","navSidePanel_div-/user/account/integrations",
					"navSidePanel_div-/user/account/settings","navSidePanel_div-/user/locations","navSidePanel_div-/user/account/reports","navSidePanel_div-/user/account/campaigns","navSidePanel_div-/user/account/widgets");
			Assert.assertEquals(actualSideMenuList,expectedSideMenuList);
			Assert.assertTrue(driver.findElement(By.id("page-container")).isDisplayed(), "Dashboard page is not displayed");
			Assert.assertTrue(driver.findElements(By.cssSelector("div.ant-card-head-title  h3")).stream().allMatch(m->m.isDisplayed()), "Dashboard Profile Card is not displayed");
			moveToElement(driver.findElement(By.id("menuList_dashboard_user")));
			waitForElementToBeClickableAndClick(driver.findElement(By.id("menuList_dashboard_user")));
			waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//li[@id='menuList_dashboard']")));
			arrayList=driver.findElements(By.xpath("//div[contains(@id,'filteringandexport') and @label]")).stream()
					.map(d->d.getAttribute("label")).collect(Collectors.toList());
			List<String> asList = Arrays.asList("all","today","7D","1M","6M","1Y");
			Assert.assertEquals(arrayList, asList);
			wait(3000);
			Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("dashboard_div_agentRatings")).getText())>0);
			Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("dashboard_div_tierCount")).getText())>0);
			Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("dashboard_div_completionRate")).getText().replace("%", ""))>0);

		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test()
	public void tc02_leaderboardLandingPage() {

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
			Assert.assertTrue(driver.findElements(By.cssSelector(".ant-table-tbody tr")).size()==10);
			click(By.id("admin_button_filter"));
			Assert.assertTrue(driver.findElement(By.cssSelector("div.ant-card-head-title")).isDisplayed(), "Filter title is not displayed");
			Assert.assertTrue(driver.findElement(By.xpath("//form[@id='leaderboard_filter']/parent::div")).isDisplayed(), "Filter fields are not displayed");
			Assert.assertTrue(driver.findElements(By.cssSelector("span.ant-select-selection-placeholder")).stream().allMatch(m->m.isDisplayed()), "Filters are not displayed");
		} finally {
			back();
		}
	}

	@Test()
	public void tc03_verifyProfilePageShareSocialSites() throws Exception {
		wait(3000);
		moveToElement(driver.findElement(By.xpath("//li[@id='menuList_profile']")));
		click(driver.findElement(By.xpath("//li[@id='menuList_profile']")));
		wait(1000);
		click(driver.findElement(By.xpath("(//div[@id='profile-edit-action']//*[local-name()='svg'])[1]")));
		wait(2000);
		waitForVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-header']")));
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ant-drawer-header']")).getText(),"Share Profile");
		navigationOfSocialSites();
	}

	@Test()
	public void tc04_verifyProfilePageReviewsShareSocialSites() throws Exception {
		wait(3000);
		moveToElement(driver.findElement(By.xpath("//li[@id='menuList_profile']")));
		waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//li[@id='menuList_profile']")));
		waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//li[@data-test-tab='Reviews']")));
		moveToElement(driver.findElement(By.xpath("(//div[contains(@id,'share_review')])[1]")));
		wait(3000);
		moveToElement(driver.findElement(By.xpath("(//div[contains(@id,'share_review')])[1]")));
		click(driver.findElement(By.xpath("(//div[contains(@id,'share_review')])[1]")));
		navigationOfSocialSites();
	}

	@Test()
	public void tc05_reportsLandingPage() {
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

			wait(3000);
			List<String> expectedTableHeader = Arrays.asList("Activity","Status","Action","Date & time");
			List<String> actualTableHeader = driver.findElements(By.cssSelector("thead.ant-table-thead th")).stream().map(d->d.getText()).collect(Collectors.toList());
			Assert.assertEquals(actualTableHeader,expectedTableHeader);

			Assert.assertTrue(driver.findElements(By.cssSelector(".ant-table-tbody tr")).size()>0);
			footerValidation();
		} finally {
			back();
		}
	}

	@Test()
	public void tc06_verifyAgentPageFooter() throws Exception {
		footerValidation();
	}
}
