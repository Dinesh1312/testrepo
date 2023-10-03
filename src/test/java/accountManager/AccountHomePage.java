package accountManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;

public class AccountHomePage extends WebDriverImpl{

	@BeforeClass
		public void browser() throws Exception {
		 driver = accountManagerLogin();

			String currentUrl = driver.getCurrentUrl();
			System.out.println(currentUrl + "Landing Page URL");
			
			click(driver.findElement(By.id("header_acc_subtitle")));
	}
	

	@AfterClass
	public void afterClass() {
		WebDriverImpl.browserQuit();
	}
	
	@Test
	public void tc01_navigateToAccountsPage() {
		
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/accounts");
		Assert.assertEquals(driver.findElement(By.id("nav_breadcumb_title")).getText(), "Accounts");
		
		Assert.assertTrue(driver.findElements(By.xpath("//input")).stream().allMatch(d->d.isEnabled()), "search and status fields are not displayed");
		List<String> listOfAccountPageFilterFields = driver.findElements(By.xpath("//input")).stream().map(d->d.getAttribute("type")).collect(Collectors.toList());
		List<String> listOfFilter = Arrays.asList("text","search");
		Assert.assertEquals(listOfAccountPageFilterFields, listOfFilter);
		
		click(driver.findElement(By.id("admin_button_closeFilter")));
		List<String> listOfAccountsPageColumns = driver.findElements(By.xpath("//thead[@class='ant-table-thead']//th[@class='ant-table-cell' or @class='ant-table-cell ant-table-column-has-sorters']")).stream().map(d->d.getText()).collect(Collectors.toList());
		System.out.println(listOfAccountsPageColumns);
		
		List<String> listOfSortColumns = Arrays.asList("Accounts","Tiers","Users","Info","Activated On","Status","Action");
		Assert.assertEquals(listOfAccountsPageColumns, listOfSortColumns);
	}
	
	@Test
	public void tc02_reportsLandingPage() {
		click(driver.findElement(By.id("menuList_reports_generate")));
	
		fluentVisibilityOfElement(driver.findElement(By.id("Reports_nav_label")));
		Assert.assertEquals(driver.findElement(By.id("Reports_nav_label")).getText(), "Reports");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/generatereports");
		
		Assert.assertTrue(driver.findElement(By.id("reports_reportCard_container")).isDisplayed(),"Report card container is not displayed");
		Assert.assertTrue(driver.findElements(By.cssSelector("span.ant-select-selection-search")).stream().allMatch(m->m.isDisplayed()), "Reports fields is not displayed");
		Assert.assertEquals(driver.findElement(By.cssSelector("#reports_reportCard_reportButton_submitButton div")).getText().trim(),"Export Report");
		Assert.assertTrue(driver.findElements(By.cssSelector("#reports_activityFeeds_activityCard_cardBlock>div")).stream().allMatch(m->m.isDisplayed()), "Activity field container card block is not displayed");
		Assert.assertTrue(driver.findElements(By.cssSelector("#reports_activityFeeds_activityCard_cardBlock>div")).size()<=10);
	
		}
	
	@Test
	public void tc03_reportsActivityFeedLandingPage() {
		try {
		waitForElementToBeClickableAndClick(By.id("reports_activityFeeds_viewAllButton"));
		wait(3000);
		fluentVisibilityOfElement(driver.findElement(By.id("ActivityFeeds_nav_label")));
		Assert.assertEquals(driver.findElement(By.id("ActivityFeeds_nav_label")).getText(), "Activity Feeds");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/generatereports/viewallactivity");
		
		List<String> expectedTableHeader = Arrays.asList("Activity","Status","Action","Date & time");
		List<String> actualTableHeader = driver.findElements(By.cssSelector("thead.ant-table-thead th")).stream().map(d->d.getText()).collect(Collectors.toList());
		Assert.assertEquals(actualTableHeader,expectedTableHeader);
		
		Assert.assertTrue(driver.findElements(By.cssSelector(".ant-table-tbody tr")).size()>0);
		
		} finally {
			back();
		}
	}	

	@Test
	public void tc04_navigateToDashboardFromHomePage() {
			
		click(driver.findElement(By.cssSelector("[path='/user/accounts']")));
		wait(2000);
		click(driver.findElement(By.id("account_button_view")));
		waitForVisibilityOfElement(driver.findElement(By.id(("AccountManagerDashboard_nav_label"))));
		Assert.assertEquals(driver.findElement(By.id(("AccountManagerDashboard_nav_label"))).getText(),"Account Manager Dashboard");
	}
}
