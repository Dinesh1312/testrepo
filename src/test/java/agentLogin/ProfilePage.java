package agentLogin;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;

public class ProfilePage extends WebDriverImpl {

	public static WebDriver driver;
	public static Wait<WebDriver> fluentwait;
	public static List<String> arrayList=new ArrayList<>();



	@BeforeClass
	public void browser() throws Exception {
		driver = agentLogin();
		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl + "Landing Page URL");
		driver.findElement(By.id("menuList_profile")).click();
	}

	@AfterClass
	public void afterClass() {
		WebDriverImpl.browserQuit();
	}

	@Test	
	public void tc01_verifyProfileLandingPage() {
		userProfileTabs();
		verifyLandingPageForUserProfilePage();
		Assert.assertEquals(driver.findElement(By.id("UserProfile_nav_label")).getText(), "User Profile");
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
	public  static void tc06_verifyFlagReview() {	
		click(By.xpath("//li[@data-test-tab='Reviews']"));
		flagReview();
	}

	@Test
	public static void tc07_verifyShareReview() {
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
	public void tc10_verifyUserContactSection() {
		verifyContactSection();

	}
	
}

