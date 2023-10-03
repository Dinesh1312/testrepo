package accountManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

public class Listings extends WebDriverImpl {


	public static WebDriver driver;

	@BeforeClass
	public void browser() throws Exception {
		 driver = accountManagerLogin();
	}

	@org.testng.annotations.AfterClass
	public void AfterClass() {
		browserQuit();
	}
	
	@Feature("Related To Listings")
	@Owner("Ashwin")
	@Test
	public void tc01_listingsLandingPage() {

		waitForElementToBeClickableAndClick(By.id("menuList_listings"));
		wait(5000);
		fluentVisibilityOfElement(driver.findElement(By.id("Listings_nav_label")));
		Assert.assertEquals(driver.findElement(By.id("Listings_nav_label")).getText(), "Listings");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/listing/dashboard");

		Assert.assertTrue(driver.findElements(By.cssSelector("div.ant-card-head-title  h3")).stream().allMatch(m->m.isDisplayed()), "Listings Profile Card is not displayed");

		List<String> listingsProfileCardTitle = Arrays.asList("Locations","Users","Activity Feeds");
		List<String> acutalListingsProfileCardTitle = driver.findElements(By.cssSelector("div.ant-card-head-title  h3 a")).stream().map(d->d.getText()).collect(Collectors.toList());
		Assert.assertEquals(acutalListingsProfileCardTitle, listingsProfileCardTitle);


		List<String> listingsDataIssueCardTitle = Arrays.asList("Location Data Issues","Users Data Issues");
		List<String> acutalListingsDataIssueCardTitle = driver.findElements(By.xpath("//span[contains(@id,'DataIssues_totalCount')]/parent::h3")).stream().map(d->d.getText().replaceAll("[0-9]", "").trim()).collect(Collectors.toList());
		Assert.assertEquals(acutalListingsDataIssueCardTitle, listingsDataIssueCardTitle);

		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='listing_publishInfo_lastPublishedCount']/preceding::h3[1]")).getText(), "Publish Info");

		Assert.assertTrue(driver.findElements(By.xpath("//div[@class='ant-card-meta-detail']/ancestor::div[@class='ant-card-meta']")).size()==10,"Activity feed container card blocks is not displayed");

		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_summaryCard_locationCount")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_summaryCard_RegionCount")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_summaryCard_BranchCount")).getText())>0);

		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_summaryCard_AgentCount")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_publishedChart_locationChart_publishedCount")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_publishedChart_locationChart_NotYetPublishedCount")).getText())>0);

		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_publishedChart_agentChart_publishedCount")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_publishedChart_agentChart_NotYetPublishedCount")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_publishInfo_lastPublishedCount")).getText())>0);

	}



	@Feature("Related To Listings")
	@Owner("Ashwin")
	@Test()
	public void tc02_reportsActivityFeedLandingPage() {
		waitForElementToBeClickableAndClick(By.id("menuList_listings"));
		waitForElementToBeClickableAndClick(By.xpath("(//a[@data-test-arrow='listing_activityFeeds_header_viewRightArrow'])[2]"));
		wait(5000);
		fluentVisibilityOfElement(driver.findElement(By.id("ActivityFeeds_nav_label")));
		Assert.assertEquals(driver.findElement(By.id("ActivityFeeds_nav_label")).getText(), "Activity Feeds");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/listing/viewallactivity");


		List<String> expectedTableHeader = Arrays.asList("Activity","Status","Date & time");
		List<String> actualTableHeader = driver.findElements(By.cssSelector("thead.ant-table-thead th[class='ant-table-cell']")).stream().map(d->d.getText()).collect(Collectors.toList());
		Assert.assertEquals(actualTableHeader,expectedTableHeader);

		Assert.assertTrue(driver.findElements(By.cssSelector("tr[class='ant-table-row ant-table-row-level-0']")).size()==10);

		click(By.id("admin_button_filter"));
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-card-head-title span")).getText(), "Search and Filter");
		Assert.assertTrue(driver.findElements(By.cssSelector("div.ant-form-item-control-input-content")).stream().allMatch(m->m.isDisplayed()), "Filter fields are not displayed");
	}

	@Feature("Related To Listings")
	@Owner("Ashwin")
	@Test()
	public void tc03_listingsLocationDataIssue() {

		waitForElementToBeClickableAndClick(By.id("menuList_listings"));
		if (driver.findElements(By.xpath("(//button[@id='listing_dataIssues_locationDataIssues_rowListViewButton'])[1]")).size()>=1) {
			waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//button[@id='listing_dataIssues_locationDataIssues_rowListViewButton'])[1]")));

			wait(5000);
			fluentVisibilityOfElement(driver.findElement(By.id("DataIssues_nav_label")));
			Assert.assertEquals(driver.findElement(By.id("DataIssues_nav_label")).getText(), "Data Issues");
			Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/listing/dataissues");


			List<String> expectedTableHeader = Arrays.asList("Location Name","Tier Name","Issue","Action");
			List<String> actualTableHeader = driver.findElements(By.xpath("//th[@class='ant-table-cell' or @class='ant-table-cell ant-table-cell-ellipsis']")).stream().map(d->d.getText()).collect(Collectors.toList());
			Assert.assertEquals(actualTableHeader,expectedTableHeader);
			Assert.assertTrue(driver.findElements(By.cssSelector("tr[class='ant-table-row ant-table-row-level-0']")).stream().allMatch(m->m.isDisplayed()));

			click(By.id("admin_button_filter"));
			Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-card-head-title span")).getText(), "Search & Filter");
			Assert.assertTrue(driver.findElements(By.xpath("//form[@id='data_issues_filter']//following::div[@class='ant-form-item-control-input-content']")).stream().allMatch(m->m.isDisplayed()), "Filter fields are not displayed");

		} else {
			System.out.println("No data issue on location");
		}

	}

	@Feature("Related To Listings")
	@Owner("Ashwin")
	@Test()
	public void tc04_listingsUserDataIssue() {

		waitForElementToBeClickableAndClick(By.id("menuList_listings"));
		if (driver.findElements(By.xpath("(//button[@id='listing_dataIssues_agentDataIssues_rowListViewButton'])[1]")).size()>=1) {
			waitForElementToBeClickableAndClick(driver.findElement(By.xpath("(//button[@id='listing_dataIssues_agentDataIssues_rowListViewButton'])[1]")));

			wait(5000);
			fluentVisibilityOfElement(driver.findElement(By.id("DataIssues_nav_label")));
			Assert.assertEquals(driver.findElement(By.id("DataIssues_nav_label")).getText(), "Data Issues");
			Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/listing/dataissues");


			List<String> expectedTableHeader = Arrays.asList("Name","Tier Name","Publish Name","Issue","Action");
			List<String> actualTableHeader = driver.findElements(By.xpath("//th[@class='ant-table-cell' or @class='ant-table-cell ant-table-cell-ellipsis']")).stream().map(d->d.getText()).collect(Collectors.toList());
			Assert.assertEquals(actualTableHeader,expectedTableHeader);
			Assert.assertTrue(driver.findElements(By.cssSelector("tr[class='ant-table-row ant-table-row-level-0']")).stream().allMatch(m->m.isDisplayed()));

			click(By.id("admin_button_filter"));
			Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-card-head-title span")).getText(), "Search & Filter");
			Assert.assertTrue(driver.findElements(By.xpath("//form[@id='data_issues_filter']//following::div[@class='ant-form-item-control-input-content']")).stream().allMatch(m->m.isDisplayed()), "Filter fields are not displayed");

		} else {
			System.out.println("No data issue on Users");
		}

	}

	@Feature("Related To Listings")
	@Owner("Ashwin")
	@Test()
	public void tc05_verifyListingsLocationsLandingPage() throws Exception {

		waitForElementToBeClickableAndClick(By.id("menuList_listings"));
		waitForElementToBeClickableAndClick(By.xpath("//a[@data-test-arrow='listing_publishedChart_locationChart_header_rightArrow']"));
		wait(5000);
		fluentVisibilityOfElement(driver.findElement(By.id("Locations_nav_label")));
		Assert.assertEquals(driver.findElement(By.id("Locations_nav_label")).getText(), "Locations");
		Assert.assertEquals(getCurrentUrl(),"https://app"+loginData.getENV()+".experience.com/user/account/alllocations");

		Assert.assertTrue(driver.findElement(By.id("listing_locations_summaryCard_HQTierName")).getText()!=null);
		Assert.assertTrue(driver.findElement(By.id("listing_locations_summaryCard_TierCategory")).getText()!=null);
		Assert.assertTrue(driver.findElement(By.id("listing_locations_summaryCard_TierDescription_showMore")).getText()!=null);

		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_locations_summaryCard_publishedCount")).getText())>0);

		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_locations_summaryCard_agent_agentCount")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_locations_summaryCard_type_RegionCount")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_locations_summaryCard_type_BranchCount")).getText())>0);
		Assert.assertTrue(driver.findElement(By.id("listing_locations_table_view")).isDisplayed());
		Assert.assertTrue(driver.findElements(By.xpath("//div[@class='List']/div/div")).size()>0);


		List<String> expectedTableHeader = Arrays.asList("","name","type","data issue","location name","publishing","status","action");
		List<String> actualTableHeader = driver.findElements(By.xpath("//div[@id='listing_locations_table_view']/div/div[1]/div")).stream().map(d->d.getText().toLowerCase()).collect(Collectors.toList());
		Assert.assertEquals(actualTableHeader,expectedTableHeader);
		verifyLocationsTierLandingPage();

	}


	
	@Feature("Related To Listings")
	@Owner("Ashwin")
	@Test()
	public void tc06_verifyListingsUserLandingPage() throws Exception {

		waitForElementToBeClickableAndClick(By.id("menuList_listings"));

		waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//a[@data-test-arrow='listing_publishedChart_agentChart_header_rightArrow']")));
		wait(5000);
		fluentVisibilityOfElement(driver.findElement(By.id("Users_nav_label")));
		Assert.assertEquals(driver.findElement(By.id("Users_nav_label")).getText(), "Users");
		Assert.assertEquals(getCurrentUrl(),"https://app"+loginData.getENV()+".experience.com/user/account/alllocations");

		Assert.assertTrue(driver.findElement(By.id("listing_locations_summaryCard_HQTierName")).getText()!=null);
		Assert.assertTrue(driver.findElement(By.id("listing_locations_summaryCard_TierCategory")).getText()!=null);
		Assert.assertTrue(driver.findElement(By.id("listing_locations_summaryCard_TierDescription_showMore")).getText()!=null);

		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_locations_summaryCard_publishedCount")).getText())>0);

		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_locations_summaryCard_agent_agentCount")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_locations_summaryCard_type_RegionCount")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("listing_locations_summaryCard_type_BranchCount")).getText())>0);
		Assert.assertTrue(driver.findElement(By.id("listing_agents_agentListTable_table_view")).isDisplayed());
		Assert.assertTrue(driver.findElements(By.cssSelector("*[class='ant-table-row ant-table-row-level-0']")).size()>0);

		Assert.assertEquals(driver.findElement(By.xpath("(//th[@title])[1]")).getText().trim(), "Name");

		Assert.assertTrue(driver.findElement(By.xpath("//th[@class='ant-table-cell ant-table-cell-ellipsis']/div")).getText().contains("Publish Name"));

		List<String> expectedTableHeader = Arrays.asList("Tier","Data Issue","Publishing","Status","Action");
		List<String> actualTableHeader = driver.findElements(By.xpath("//div[contains(text(),'Publish Name')]/following::th[@class='ant-table-cell' or @class='ant-table-cell ant-table-cell-ellipsis']")).stream().map(d->d.getText().trim()).collect(Collectors.toList());
		actualTableHeader.forEach(System.out::println);
		Assert.assertEquals(actualTableHeader,expectedTableHeader);
		verifyLocationsUserLandingPage();
	}

	
	@Feature("Related To Listings")
	@Owner("Ashwin")
	@Test()
	public void tc07_listingsPublishStatus() {
		waitForElementToBeClickableAndClick(By.id("menuList_listings"));
		listingsPublishStatus("listing_publishedChart_locationChart_authorizeRight_arrow");
		listingsPublishStatus("listing_publishedChart_agentChart_authorizeRequire_arrow");
	}







	public void verifyLocationsTierLandingPage() throws Exception {

		try {
			waitForElementToBeClickableAndClick(By.xpath("(//button[@id='listing_locations_viewActionundefined_textButton'])[1]"));
			wait(5000);
			String currentUrl = driver.getCurrentUrl();
			fluentVisibilityOfElement(driver.findElement(By.xpath("(//span[contains(@id,'nav_label')])[3]")));
			Assert.assertTrue(driver.findElement(By.xpath("(//span[contains(@id,'nav_label')])[3]")).isDisplayed());
			Assert.assertEquals(currentUrl, "https://app"+loginData.getENV()+".experience.com/user/locations?type=TierLocation");
			Assert.assertTrue(driver.findElement(By.id("listings_location_targetSites_overviewCard_TierName")).isDisplayed(), "Location Name is not displayed");

			Assert.assertTrue(driver.findElement(By.id("listings_location_targetSites_overviewCard_TierName")).getText()!=null);
			Assert.assertTrue(driver.findElement(By.id("listings_location_targetSites_overviewCard_categoryName")).getText()!=null);
			Assert.assertTrue(driver.findElement(By.id("listings_location_targetSites_overviewCard_showMoreDescription")).getText()!=null);

			Assert.assertTrue(driver.findElement(By.xpath("//h3[@id='nav_breadcumb_title']//following::span[2]")).isDisplayed(), "Org name is displayed");
			Assert.assertTrue(driver.findElement(By.id("product_guide")).isDisplayed(), "Help Icon is displayed");

			Assert.assertTrue(driver.findElements(By.xpath("listings_location_targetSites_overviewCard_actionButtons")).stream().allMatch(m->m.isDisplayed())," edit agentInfo, Publish and publish toggle displayed");
			Assert.assertEquals(driver.findElement(By.xpath("//h4[text()='Business Hours']")).getText(), "Business Hours");

			List<String> arrayList = driver.findElements(By.xpath("//h4[text()='Business Hours']//parent::div//div[text()]")).stream().map(d->d.getText()).collect(Collectors.toList());
			List<String> listOfDays = Arrays.asList("Mon","Tue", "Wed", "Thu", "Fri","Sat", "Sun");
			Assert.assertEquals(arrayList, listOfDays);
			System.out.println(arrayList);

			Assert.assertEquals(driver.findElement(By.xpath("//div[.='Published Sites']")).getText(), "Published Sites");
			Assert.assertEquals(driver.findElement(By.xpath("//div[.='Pending Sites']")).getText(), "Pending Sites");
			Assert.assertEquals(driver.findElement(By.xpath("//div[.='Authorization Required']")).getText(), "Authorization Required");
			Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("listings_location_targetSites_overviewCard_publishedSitesCount")).getText())>0);	


			List<String> actualTableList = driver.findElements(By.cssSelector("*[class='ant-table-thead'] th[class='ant-table-cell']")).stream().map(d->d.getText()).collect(Collectors.toList());
			List<String> expectedTableList = Arrays.asList("Target","Status", "Actions");
			Assert.assertEquals(actualTableList, expectedTableList);
			listingEditLocation("Edit Location Profile Information");
			listingPublishLocation();
			clickFilterAndConnectionValidation();

		} finally {
			waitForElementToBeClickableAndClick(driver.findElement(By.id("Listings_nav_label")));
		}
	}


	public void verifyLocationsUserLandingPage() throws Exception {

		try {
			waitForElementToBeClickableAndClick(By.xpath("(//button[@id='listing_agents_agentListTable_viewAction_textButton'])[1]"));
			wait(5000);
			String currentUrl = driver.getCurrentUrl();
			fluentVisibilityOfElement(driver.findElement(By.xpath("(//span[contains(@id,'nav_label')])[3]")));
			Assert.assertTrue(driver.findElement(By.xpath("(//span[contains(@id,'nav_label')])[3]")).isDisplayed());
			Assert.assertEquals(currentUrl, "https://app"+loginData.getENV()+".experience.com/user/locations?type=TierAgent");
			Assert.assertTrue(driver.findElement(By.id("listings_location_targetSites_overviewCard_TierName")).isDisplayed(), "Location Name is not displayed");

			Assert.assertTrue(driver.findElement(By.id("listings_location_targetSites_overviewCard_TierName")).getText()!=null);
			Assert.assertTrue(driver.findElement(By.id("listings_location_targetSites_overviewCard_categoryName")).getText()!=null);
			Assert.assertTrue(driver.findElement(By.id("listings_location_targetSites_overviewCard_showMoreDescription")).getText()!=null);

			Assert.assertTrue(driver.findElement(By.xpath("//h3[@id='nav_breadcumb_title']//following::span[2]")).isDisplayed(), "Org name is displayed");
			Assert.assertTrue(driver.findElement(By.id("product_guide")).isDisplayed(), "Help Icon is displayed");

			Assert.assertTrue(driver.findElements(By.xpath("listings_location_targetSites_overviewCard_actionButtons")).stream().allMatch(m->m.isDisplayed())," edit agentInfo, Publish and publish toggle displayed");
			Assert.assertEquals(driver.findElement(By.xpath("//h4[text()='Business Hours']")).getText(), "Business Hours");

			List<String> arrayList = driver.findElements(By.xpath("//h4[text()='Business Hours']//parent::div//div[text()]")).stream().map(d->d.getText()).collect(Collectors.toList());
			List<String> listOfDays = Arrays.asList("Mon","Tue", "Wed", "Thu", "Fri","Sat", "Sun");
			Assert.assertEquals(arrayList, listOfDays);
			System.out.println(arrayList);

			Assert.assertEquals(driver.findElement(By.xpath("//div[.='Published Sites']")).getText(), "Published Sites");
			Assert.assertEquals(driver.findElement(By.xpath("//div[.='Pending Sites']")).getText(), "Pending Sites");
			Assert.assertEquals(driver.findElement(By.xpath("//div[.='Authorization Required']")).getText(), "Authorization Required");	

			List<String> actualTableList = driver.findElements(By.cssSelector("*[class='ant-table-thead'] th[class='ant-table-cell']")).stream().map(d->d.getText()).collect(Collectors.toList());
			List<String> expectedTableList = Arrays.asList("Target","Status", "Actions");
			Assert.assertEquals(actualTableList, expectedTableList);

			listingEditLocation("Edit User Profile Information");
			listingPublishLocation();
			clickFilterAndConnectionValidation();

		}
		finally {
			waitForElementToBeClickableAndClick(driver.findElement(By.id("Listings_nav_label")));
		}

	}


}





