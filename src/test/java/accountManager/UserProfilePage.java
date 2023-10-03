package accountManager;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;

public class UserProfilePage extends WebDriverImpl {


	public static WebDriver driver;
	public static Wait<WebDriver> fluentwait;


	@BeforeClass
	public void browser() throws Exception {
		 driver = accountManagerLogin();

		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl + "Landing Page URL");

		click(By.id("menuList_hierarchy"));

		click(By.id("users_button"));
		input(By.xpath("//input[@data-test-mismatches-search-input='true']"), "Automation Agent1");
		wait(3000);
		fluentVisibilityOfElement(driver.findElement(By.xpath("(//span[@id='users_show_action_row'])[1]")));
		moveToElement(driver.findElement(By.xpath("(//span[@id='users_show_action_row'])[1]")));	
		wait(2000);
		click(By.id("hierarchy_region_count"));
		moveToElement( driver.findElement(By.xpath("(//span[@id='users_show_action_row'])[1]")));
		click(By.id("users_show_action_row"));
		wait(1000);
		click(By.id("users_view_row_action"));
		
	}
	
	@AfterClass
	public void afterClass() throws Exception {
		//browserQuit();
	}
	
	@Test
	public void tc01_verifyUserProfileLandingPage() {
		verifyLandingPageForUserProfilePage();
		userProfileTabs();
		Assert.assertTrue(driver.findElement(By.id("back_button")).isDisplayed(),"back button is displayed");
		Assert.assertTrue(driver.findElement(By.id("unpublish_button")).isDisplayed(),"Unpublish button is displayed");
		Assert.assertEquals(driver.findElement(By.id("previewProfile_nav_label")).getText(),"Preview Profile");		
	}

	@Test
	public void tc02_verifyReviewsTab() {	
		verifyReviewsTab();
		userReviewsFilterValidation();
	}

	@Test
	public void tc03_verifyProfessionalSidePage() throws Exception {
		verifyProfessionalsTab();
	}

	@Test
	public void tc04_clickShareProfileIcon() {		
		clickShareProfileIcon();
	}

	@Test
	public void tc05_clickEditProfileIcon() {
		clickEditProfileIcon();
	}

	@Test
	public void tc06_verifyFlagReview() throws Exception {
		safeJavaScriptClick(driver.findElement(By.xpath("//li[@data-test-tab='Reviews']")));
		flagReview();
	}

	@Test
	public  void tc07_verifyShareReview() {
		click(By.xpath("//li[@data-test-tab='Reviews']"));
		shareReview();
	}

	@Test
	public void tc08_verifyRatingTab() {
		verifyRatingTab();
	}

	@Test
	public void tc09_verifyAboutSection() {
		verifyAboutSection();
	}

	@Test
	public void tc10_verifyContactSection() {
		verifyContactSection();

	}
}
