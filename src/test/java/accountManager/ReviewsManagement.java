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

public class ReviewsManagement extends WebDriverImpl {

	@BeforeClass
	public void browser() throws Exception {
		
		 driver = accountManagerLogin();
	}

	@AfterClass
	public void afterclass() {
		browserQuit();
	}

	@Test
	public void tc001_reviewsManagementLandingPage() throws Exception {
		
		fluentVisibilityOfElement(driver.findElement(By.id("menuList_reviews_management")));
		click(driver.findElement(By.id("menuList_reviews_management")));
		WebElement title_element = driver.findElement(By.id("nav_breadcumb_title"));
		fluentVisibilityOfElement(title_element);
		Assert.assertEquals(title_element.getText(), "Automation account -Dont delete");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/reviews");
		wait(3000);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("reviewManagement_totalReview_count")).getText())>0);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("reviewManagement_averageScore_count")).getText())>0);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("reviewManagement_totalRepliedReview_count")).getText())>0);
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("reviewManagement_fiveStarReview_count")).getText())>0);
		
		Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-card-body'])[1]")).isDisplayed()) ;
		Assert.assertTrue(driver.findElement(By.id("reviewManagement_review_block")).isDisplayed()) ;
		driver.findElements(By.xpath("//div[contains(@id, 'reviewManagement_review_div_commentSection')]")).stream().map(m -> m.isDisplayed());
		driver.findElements(By.xpath("//div[contains(@id, 'reviewManagement_review_div_commentSection')]")).stream().map(m -> m.isDisplayed());
		driver.findElements(By.xpath("//div[contains(@id, 'reviewManagement_review_div_SocialSection')]")).stream().map(m -> m.isDisplayed());
		
		click(driver.findElement(By.id("admin_button_activityFeed")));
		
		fluentVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-title']")));
	    Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ant-drawer-title']")).getText(),"Activity Feed");
	  
	    driver.findElements(By.id("reviewManagement_activityfeed_feedItem")).stream().map(m -> m.isDisplayed());
	    wait(4000);
	    WebElement viewallActivity = driver.findElement(By.xpath("//a[text()='View All Activity']"));
	    fluentWaitClick(viewallActivity);
	 
	 
	    
	     fluentVisibilityOfElement(driver.findElement(By.id("ActivityFeed_nav_label")));
	     WebElement Breadcum= driver.findElement(By.id("ActivityFeed_nav_label"));
	     Assert.assertEquals(Breadcum.getText(), "Activity Feed");
	    
	     Assert.assertEquals(getCurrentUrl(),"https://app"+loginData.getENV()+".experience.com/user/account/reviews/activityfeed");
	     

	     Assert.assertEquals(driver.findElement(By.xpath("//button[@data-review-activity-export='true']")).getText(),"Export");
	
	    
	    Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='ant-card-body'])[2]")).isDisplayed());
	    Assert.assertEquals(driver.findElement(By.xpath("(//div[@class='ant-card-head-title'])[2]")).getText(),"Activity Feed");
	    
		
		click( driver.findElement(By.id("admin_button_filter")));
		wait(1000);
		List<String> act_activityFilterOption = driver
				.findElements(By.xpath("//div[@class='ant-col ant-form-item-label']//label[@class]")).stream().map(m -> m.getText().trim())
				.collect(Collectors.toList());
		List<String> exp_activityFilterOption = Arrays.asList("Search", "Date", "Tiers");
		
		Assert.assertEquals(act_activityFilterOption, exp_activityFilterOption);
		
		 Assert.assertEquals(driver.findElement(By.xpath("//div[@id='reviewManagement_activity_feed_users_filter']/div[1]")).getText(),"Users"); //  Agent
	}
	
	@Test
	public void tc002_dataAndAnalysisLandingPage() throws Exception {
		wait(2000);
//		fluentVisibilityOfElement(driver.findElement(By.id("menuList_reviews_management")));
//		click(driver.findElement(By.id("menuList_reviews_management")));
//		wait(3000);
		safeJavaScriptClick(driver.findElement(By.xpath("//li[@id='data_analytics_li']")));
		
		WebElement title_element = driver.findElement(By.id("nav_breadcumb_title"));
		fluentVisibilityOfElement(title_element);
		Assert.assertEquals(title_element.getText(), "Automation account -Dont delete");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/dataanalysis");
		Assert.assertTrue(driver.findElement(By.id("product_guide")).isDisplayed()) ;
		Assert.assertTrue(driver.findElement(By.id("input_box_")).isDisplayed()) ;	
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-table-container']")).isDisplayed()) ;
		
		List<String> act_dataAnalysisTableheaders = driver
				.findElements(By.xpath("//span[text()='Name']//following::th[@class='ant-table-cell']")).stream().map(m -> m.getText().trim())
				.collect(Collectors.toList());
		
		List<String> exp_dataAnalysisTableheaders = Arrays.asList("Completion Rate", "Incomplete Surveys", "Status","Actions");
		Assert.assertEquals(act_dataAnalysisTableheaders, exp_dataAnalysisTableheaders);
		wait(3000);		
		Assert.assertTrue((driver.findElements(By.xpath("//tr[@class='ant-table-row ant-table-row-level-0']")).size())>0);
		
		click(driver.findElement(By.xpath("//div[text()='View Data & Analysis']//parent::button[1]")));
		
		WebElement title_element2= driver.findElement(By.id("nav_breadcumb_title"));
		fluentVisibilityOfElement(title_element2);
		Assert.assertEquals(title_element2.getText(), "Automation account -Dont delete");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/dataanalysis/reviewdetails");
		Assert.assertTrue(driver.findElement(By.id("product_guide")).isDisplayed()) ;
		Assert.assertTrue(driver.findElement(By.id("admin_button_search")).isDisplayed()) ;
	
		Assert.assertTrue(driver.findElement(By.id("datafilters")).isDisplayed()) ;
		Assert.assertTrue(driver.findElement(By.id("dataExport")).isDisplayed()) ;
		Assert.assertTrue(driver.findElement(By.id("datatools")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("columnChooser")).isDisplayed());
		
		Assert.assertEquals(driver.findElement(By.id("ReviewDetails_nav_label")).getText(),"Review Details");
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-table-body']")).isDisplayed()) ;
		Assert.assertTrue((driver.findElements(By.xpath("//tr[@class='ant-table-row ant-table-row-level-0']")).size())>0);
		
		scrollingDown(driver.findElement(By.xpath("//tr[2]/td[25]/div/span")));
		moveToElement(driver.findElement(By.xpath("//tr[2]/td[25]/div")));
		mouseActionClick(driver.findElement(By.xpath("//tr[2]/td[25]/div")));
		fluentVisibilityOfElement(driver.findElement(By.xpath("//span[text()='View Response']//parent::li")));
		moveToElement(driver.findElement(By.xpath("//span[text()='View Response']//parent::li")));
		mouseActionClick(driver.findElement(By.xpath("//span[text()='View Response']")));
		
		fluentVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']")));
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']")).getText().trim(), "Response");
		
		Assert.assertTrue((driver.findElements(By.xpath("//div[@class='ant-drawer-body']/div/div")).size())>0);
		
		safeJavaScriptClick(driver.findElement(By.xpath("//div[text()='Close']//parent::button")));
		
		wait(1000);
		moveToElement(driver.findElement(By.xpath("//tr[2]/td[25]/div")));
		mouseActionClick(driver.findElement(By.xpath("//tr[2]/td[25]/div")));
		
		fluentVisibilityOfElement(driver.findElement(By.xpath("//span[text()='Delete Response']//parent::li")));
		moveToElement(driver.findElement(By.xpath("//span[text()='Delete Response']//parent::li")));
		mouseActionClick(driver.findElement(By.xpath("//span[text()='Delete Response']")));
		
		fluentVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']")));
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']")).getText().trim(), "Delete Confirmation");
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ant-drawer-body']/div[1]")).getText().trim(), "Are you sure want to delete this response?");
		
		Assert.assertTrue(driver.findElement(By.xpath("//button[@id='delete_response_confirm_button']")).isEnabled());
		
		safeJavaScriptClick(driver.findElement(By.xpath("//span[text()='Cancel']//parent::button")));
		wait(2000);
		click(driver.findElement(By.id("menuList_dashboard")));
	}
	
	
	@Test()
	public void tc003_verifyReviewsManagementShareSocialSites() throws Exception {
		wait(3000);
		moveToElement(driver.findElement(By.xpath("//li[@id='menuList_reviews_management']")));
		waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//li[@id='menuList_reviews_management']")));
		navigationOfSocialSitesAccountManager();
	}
	
	
	
	
	
	
	
}