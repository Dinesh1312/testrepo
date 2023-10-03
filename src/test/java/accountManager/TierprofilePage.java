package accountManager;

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

public class TierprofilePage extends WebDriverImpl {

	public static WebDriver driver;
	public static Wait<WebDriver> fluentwait;

	@BeforeClass
	public void browser() throws Exception {
		 driver = accountManagerLogin();

		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl + "Landing Page URL");

		click(By.id("menuList_hierarchy"));

		wait(3000);
		fluentVisibilityOfElement(driver.findElement(By.xpath("(//span[contains(@id,'hierarchy_row_actions')])[1]")));
		moveToElement(driver.findElement(By.xpath("(//span[contains(@id,'hierarchy_row_actions')])[1]")));

		click(By.xpath("(//span[contains(@id,'hierarchy_row_actions')]//*[local-name()='svg'])[1]"));
		waitForVisibilityOfElement(driver.findElement(By.id("hierarchy_preview_profile")));
		click(By.id("hierarchy_preview_profile"));
	}
	

	@AfterClass
	public void afterClass() {

		//WebDriverImpl.browserQuit();
	}

	@Test()
	public void tc01_verifyTierProfileLandingPage() {		
		verifyLandingPageForUserProfilePage();
		tierProfileTabs();
		Assert.assertTrue(driver.findElement(By.id("back_button")).isDisplayed(),"back button is not displayed");
		Assert.assertTrue(driver.findElement(By.id("unpublish_button")).isDisplayed(),"Unpublish button is not displayed");
		Assert.assertEquals(driver.findElement(By.id("previewProfile_nav_label")).getText(),"Preview Profile");
	}

	@Test()
	public void tc02_verifyReviewsTab() {	
		verifyReviewsTab();
		tierReviewsFilterValidation();
	}

	@Test()
	public void tc03_verifyProfessionalSidePage() throws Exception {
		verifyProfessionalsTab();
	}

	@Test()
	public void tc04_clickShareProfileIcon() {	
		clickShareProfileIcon();
	}

	@Test()
	public void tc05_clickEditProfileIcon() {
		clickEditProfileIcon();
	}

	@Test()
	public void tc06_verifyFlagReview() {	
		click(By.xpath("//li[@data-test-tab='Reviews']"));
		flagReview();
	}

	@Test()
	public void tc07_verifyShareReview() {
		click(By.xpath("//li[@data-test-tab='Reviews']"));
		shareReview();
	}

	@Test()
	public void tc08_verifyRatingTab() {
		verifyRatingTab();
	}

	@Test()
	public void tc09_verifyAboutSection() {
		verifyAboutSection();
	}

	@Test()
	public void tc10_verifyContactSection() {
		verifyContactSection();	
	}

	@Test()
	public void tc11_verifyUsersSection() {
		driver.findElement(By.xpath("//li[@data-test-tab='Users']")).click();
		Assert.assertTrue(driver.findElement(By.id("filter_button")).isDisplayed(),"filter button is not displayed");
		Assert.assertTrue(driver.findElement(By.id("input_box_")).isDisplayed(),"search field is not displayed");
		driver.findElement(By.id("filter_button")).click();

		Assert.assertTrue(driver.findElement(By.id("reviews_filter_reset_button")).isDisplayed(),"reset button is displayed");
		Assert.assertTrue(driver.findElement(By.id("reviews_filter_apply_button")).isDisplayed(),"apply button is displayed");
		Assert.assertTrue(driver.findElement(By.xpath("(//div[text()='Number of reviews']//parent::div)[1]")).isDisplayed(),"no of reviews filter is displayed");
		Assert.assertTrue(driver.findElement(By.xpath("(//div[text()='Rating']//parent::div)[1]")).isDisplayed(),"no of reviews filter is displayed");

		List<WebElement> reviewsFilterList = driver.findElements(By.xpath("//input"));
		Assert.assertTrue(reviewsFilterList.stream().allMatch(m->m.isEnabled())," no of reviews filter lists not displayed");
		List<String> actualReviewFilterList = reviewsFilterList.stream().map(m->m.getAttribute("type")).collect(Collectors.toList());
		System.out.println(actualReviewFilterList);
		List<String> expReviewFilterList = Arrays.asList("text","checkbox","checkbox", "checkbox", "checkbox", "checkbox","checkbox","search", "search");

		Assert.assertEquals(actualReviewFilterList, expReviewFilterList);
		Assert.assertTrue(driver.findElements(By.id("publish_name_undefined")).stream().map(m->m.getText())!=null, "Lists of agents are displayed");
		wait(3000);
		int reviewRatingSize = driver.findElements(By.id("//div[contains(@id,'rating_tieruser')]")).size();
		for(int i=0;i<reviewRatingSize;i++) {

			String ratingText = driver.findElements(By.xpath("//div[contains(@id,'rating_tieruser')]")).get(i).getText();
			Assert.assertTrue(Double.parseDouble(ratingText)>0);

			String reviewCountText = driver.findElements(By.xpath("//div[contains(@id,'rating_tieruser')]")).get(i).getText().replaceAll("[^a-zA-Z0-9_-]", "");
			Assert.assertTrue(Double.parseDouble(reviewCountText)>0);
		}				 
	}

}
