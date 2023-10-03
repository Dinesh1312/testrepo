package agentLogin;

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

public class ReviewsManagement extends WebDriverImpl {


	public static WebDriver driver;
	public static Wait<WebDriver> fluentwait;
	
	@BeforeClass
	public void browser() throws Exception {
		driver = agentLogin();
		
		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl + "Landing Page URL");
		driver.findElement(By.id("menuList_reviews_management")).click();
	}
	
	@AfterClass
	public void afterClass() {
		//WebDriverImpl.browserQuit();
	}
	
	@Test
	public void tc01_verifyReviewsLandingPage() {
		
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, "https://app"+loginData.getENV()+".experience.com/user/account/reviews");
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.xpath("(//div[@id='total_exp_card']//div)[1]")).getText())>0);	
		Assert.assertEquals(driver.findElement(By.id("nav_breadcumb_title")).getText(), "Automation account -Dont delete");
		Assert.assertEquals(driver.findElement(By.id("ReviewsManagement_nav_label")).getText(), "Reviews Management");
		Assert.assertTrue(driver.findElement(By.xpath("//h3[@id='nav_breadcumb_title']//following::span[2]")).isDisplayed(), "Org name is not displayed");
		Assert.assertTrue(driver.findElement(By.id("product_guide")).isDisplayed(), "Help Icon is not displayed");
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[.='Reviews Overview']")).getText(), "Reviews Overview");
		Assert.assertTrue(driver.findElements(By.xpath("//div[contains(@id,'content')]")).stream().allMatch(m->m.isDisplayed()), "Recent Reviews and replies card are not displayed");
		Assert.assertTrue(driver.findElement(By.id("total_exp_card")).isDisplayed(), "total exp reviews card is displayed");	
		Assert.assertTrue(driver.findElements(By.xpath("//div[contains(@id,'title')]")).stream().allMatch(m->m.getText()!=null),"all source reviews cards are displayed");
		
		Assert.assertTrue(driver.findElement(By.id("agent_filter_button")).isDisplayed(), "filter is not displayed");
		
		Assert.assertTrue(driver.findElement(By.id("agent_reviews_section_sort_button")).isDisplayed(), "sorting is not displayed");
		Assert.assertTrue(driver.findElement(By.id("profile_container_div")).isDisplayed(), "User Profile Name with role is not displayed");
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[.='Reviews']")).getText(), "Reviews");
		
		Assert.assertTrue(driver.findElements(By.xpath("//div[contains(@id,'reviews_card_wrapper')]")).stream().allMatch(m->m.isDisplayed()),"Reviews list are not displayed");
	}
	
	@Test
	public void tc02_verifyFilterCards() {
		driver.findElement(By.id("agent_filter_button")).click();
		
		Assert.assertTrue(driver.findElement(By.id("reviews_filter_reset_button")).isDisplayed(), "reset filter button is not displayed");
		Assert.assertTrue(driver.findElement(By.id("reviews_filter_apply_button")).isDisplayed(), "apply filter button is not displayed");
		
		Assert.assertTrue(driver.findElement(By.xpath("//input[@id='search_filter']//following::div[1]")).isDisplayed(), "Date filter card is not displayed");
	
		List<WebElement> reviewsFilterList = driver.findElements(By.xpath("//div[@id='reviews_filter_section']//input[contains(@class,'ant')]"));
		Assert.assertTrue(reviewsFilterList.stream().allMatch(m->m.isEnabled()),"Reviews filter lists are not displayed");
		
		List<String> actualReviewFilterList = reviewsFilterList.stream().map(m->m.getAttribute("type")).collect(Collectors.toList());
		System.out.println(actualReviewFilterList);
		List<String> expReviewFilterList = Arrays.asList("text","search", "checkbox", "checkbox", "checkbox","checkbox");
		
		Assert.assertEquals(actualReviewFilterList, expReviewFilterList);		
	}
	
	@Test
	public void tc03_verifyFlagReview() {
		flagReview();
	}
	
	@Test
	public void tc04_verifyShareReview() {
		shareReview();
	}
	

	@Test
	public void tc05_activityFeedPage() {

		moveToElement(driver.findElement(By.id("admin_button_activityFeed")));
		click(driver.findElement(By.id("admin_button_activityFeed")));
		Assert.assertTrue(driver.findElement(By.id("admin_button_activityFeed")).isDisplayed(), "Activity Feed is displayed");
		waitForVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-title']")));
		Assert.assertTrue(driver.findElements(By.xpath("//div[contains(@id,'reviewManagement_activityfeed_feedItem')]")).stream().allMatch(m->m.isDisplayed()),"activities list are displayed");

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
		List<String> exp_activityFilterOption = Arrays.asList("Search", "Date");

		Assert.assertEquals(act_activityFilterOption, exp_activityFilterOption);

	}
	
}
