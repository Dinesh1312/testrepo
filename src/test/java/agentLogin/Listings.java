package agentLogin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;

public class Listings extends WebDriverImpl {
	
	public static WebDriver driver;	
	
	@BeforeClass
	public void browser() throws Exception {
		driver = agentLogin();
		driver.findElement(By.id("menuList_acc_locations")).click();
	}
	
	@AfterClass
	public void afterClass() {
		WebDriverImpl.browserQuit();
	}
	
	@Test
	public void tc01_verifyListingsLandingPage() {
		//wait(3000);
		String currentUrl = driver.getCurrentUrl();
		Assert.assertEquals(currentUrl, "https://app"+loginData.getENV()+".experience.com/user/locations?type=TierAgent");
		Assert.assertTrue(driver.findElement(By.id("listings_location_targetSites_overviewCard_TierName")).isDisplayed(), "User Name is displayed");
		Assert.assertEquals(driver.findElement(By.id("nav_breadcumb_title")).getText(), "Automation account -Dont delete");
		Assert.assertEquals(driver.findElement(By.id("Listings_nav_label")).getText(), "Listings");
		Assert.assertTrue(driver.findElement(By.xpath("//h3[@id='nav_breadcumb_title']//following::span[2]")).isDisplayed(), "Org name is displayed");
		Assert.assertTrue(driver.findElement(By.id("product_guide")).isDisplayed(), "Help Icon is displayed");
		
		Assert.assertTrue(driver.findElements(By.xpath("listings_location_targetSites_overviewCard_actionButtons")).stream().allMatch(m->m.isDisplayed())," edit agentInfo, Publish and publish toggle displayed");
		Assert.assertEquals(driver.findElement(By.xpath("//h4[text()='Business Hours']")).getText(), "Business Hours");
		
		List<String> agentListingPageListOfDays = driver.findElements(By.xpath("//h4[text()='Business Hours']//parent::div//div[text()]")).stream().map(d->d.getText()).collect(Collectors.toList());
		List<String> listOfDays = Arrays.asList("Mon","Tue", "Wed", "Thu", "Fri","Sat", "Sun");
		Assert.assertEquals(agentListingPageListOfDays, listOfDays);
		System.out.println(agentListingPageListOfDays);
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[.='Authorization Required']")).getText(), "Authorization Required");
		
		List<String> listingPageListOfSites = driver.findElements(By.xpath("//div[contains(text(),'Sites')]")).stream().map(d->d.getText()).collect(Collectors.toList());
		List<String> listOfSites = Arrays.asList("Published Sites","Pending Sites");
		Assert.assertEquals(listingPageListOfSites, listOfSites);
		System.out.println(listingPageListOfSites);
		
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("listings_location_targetSites_overviewCard_publishedSitesCount")).getText())>0);	
		
	}
	
	@Test	
	public void tc02_clickFilter() {
	
	wait(3000);
	driver.findElement(By.id("admin_button_filter")).click();
	List<WebElement> reviewsFilterList = driver.findElements(By.xpath("//input"));
	Assert.assertTrue(reviewsFilterList.stream().allMatch(m->m.isEnabled())," no of reviews filter lists not displayed");
	List<String> actualReviewFilterList = reviewsFilterList.stream().map(m->m.getAttribute("type")).collect(Collectors.toList());
	System.out.println(actualReviewFilterList);
	List<String> expReviewFilterList = Arrays.asList("text","checkbox","checkbox", "checkbox", "checkbox", "checkbox","checkbox","search");
	
	Assert.assertEquals(actualReviewFilterList, expReviewFilterList);

	}
	
}