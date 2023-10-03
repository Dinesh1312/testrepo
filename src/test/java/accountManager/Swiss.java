package accountManager;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;

public class Swiss extends WebDriverImpl {
	public static WebDriver driver;
	public static List<String> arrayList=new ArrayList<>();

	@BeforeClass

	public void browser() throws Exception {
		
		driver =launchBrowser(loginData.getBROWSER_NAME());
  
		driver.get("https://pro"+loginData.getENV()+".experience.com");
		driver.manage().window().maximize();

		System.out.println("Landing Page URL" + " " +driver.getCurrentUrl().equals("https://pro"+loginData.getENV()+".experience.com"));
		driver.manage().deleteAllCookies();
		Assert.assertTrue(driver.findElement(By.xpath("(//*[local-name()='svg'])[3]")).isDisplayed(), "Experience logo is not present");
	}

	@AfterMethod()
	public void afterMethod() {
		driver.findElement(By.xpath("(//*[local-name()='svg'])[3]")).click();
	}
	
	@AfterClass
	public void afterClass() throws Exception {
		//browserQuit();
	}

	@Test
	public void tc01_swissLandingPageLinks() {
		String currentUrl;	
		HttpURLConnection huc = null;
		int respCode = 200;
		List<WebElement> links = driver.findElements(By.xpath("//a[@href]"));

		Iterator<WebElement> it = links.iterator();

		while(it.hasNext()){
			currentUrl = it.next().getAttribute("href");

			if(links == null || currentUrl.isEmpty()){
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
			}

//			 if(!currentUrl.startsWith("https")){
				if(!currentUrl.contains("https://pro"+loginData.getENV()+".experience.com/")){
				System.out.println("URL belongs to another domain, skipping it.");
				continue;
			}

			try {
				huc = (HttpURLConnection)(new URL(currentUrl).openConnection());

				huc.setRequestMethod("HEAD");

				huc.connect();

				respCode = huc.getResponseCode();

				if(respCode >= 400){
					System.out.println(currentUrl+" is a broken link");
					Assert.fail();
				}
				else{
					System.out.println(currentUrl+" is a valid link");
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void tc02_verifyLandingPage() {

		Assert.assertEquals(getCurrentUrl(), "https://pro"+loginData.getENV()+".experience.com/");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='__next']//h1")).getText(),"Find and refer the best professionals.");

		Assert.assertTrue(driver.findElement(By.xpath("(//div[@id='__next']//p)[1]")).isDisplayed(),"Millions of reviews from verified customers is not displayed");

		Assert.assertEquals(driver.findElement(By.xpath("//div[.='Business Categories']")).getText(),"Business Categories");

		Assert.assertTrue(driver.findElement(By.xpath("//div[.='Business Categories']")).isDisplayed(),"Business Card is displayed");

		List<String> homePageListOfCategories = driver.findElements(By.xpath("//div[@class='iconWrap']")).stream().map(d->d.getText()).collect(Collectors.toList());
		List<String> listOfCategories = Arrays.asList("Mortgage","Real Estate","Insurance","Banking","Computer Technology");
		Assert.assertEquals(homePageListOfCategories, listOfCategories);
		System.out.println(homePageListOfCategories);

		Assert.assertTrue(driver.findElements(By.xpath("//*[contains(@id,'homeContainer_input')]")).stream().allMatch(m->m.isDisplayed()),"all textfields are not displayed");

		Assert.assertTrue(driver.findElement(By.id("homeContainer_button_searchProfiles")).isDisplayed(),"HomePageSearch is not available");
	}

	@Test
	public void tc03_verifySwissPageFooterIndustries() {	
		Assert.assertEquals(driver.findElement(By.xpath("//div[text()='INDUSTRIES']")).getText(),"INDUSTRIES");

		List<String> swissFooterPageListOfSources = driver.findElements(By.xpath("//div[.='INDUSTRIES']//following::a[contains(@id,'homeContainer_link')]")).stream().map(d->d.getText()).collect(Collectors.toList());
		List<String> listOfResources = Arrays.asList("Mortgage","Real Estate","Insurance","Banking","Computer Technology","Resource Center","Contact Us");
		Assert.assertEquals(swissFooterPageListOfSources, listOfResources);
		System.out.println(swissFooterPageListOfSources);

		Assert.assertEquals(driver.findElement(By.xpath("//div[.='RESOURCES']")).getText(), "RESOURCES");

		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='contact-mail']")).isDisplayed(), "contact details not present");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='contact-phone']")).isDisplayed(), "Contact By Phone is  not present");
		Assert.assertTrue(driver.findElement(By.xpath("(//div[text()='LOCATIONS']//parent::div)[1]")).isDisplayed(), "Location details is not present");
	}

	@Test
	public void tc04_verifySearchResultsPage() throws AWTException {
		
		driver.findElement(By.xpath("(//a[@id='homeContainer_link_MortgagePage'])[1]")).click();		
		
		wait(5000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ESCAPE);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
		wait(3000);
		System.out.println(driver.getCurrentUrl());
		Assert.assertTrue(driver.findElement(By.xpath("(//*[local-name()='svg'])[3]")).isDisplayed(), "Experience logo is  not present");
		
		List<WebElement> reviewsFilterList = driver.findElements(By.xpath("//input"));
		Assert.assertTrue(reviewsFilterList.stream().allMatch(m->m.isEnabled())," no of reviews filter lists not displayed");
		List<String> actualReviewFilterList = reviewsFilterList.stream().map(m->m.getAttribute("type")).collect(Collectors.toList());
		System.out.println(actualReviewFilterList);
		
		List<String> expReviewFilterList = Arrays.asList("search","search","checkbox","checkbox", "checkbox", "checkbox", 
				"checkbox","checkbox","checkbox","checkbox", "checkbox", "checkbox", "checkbox","checkbox","checkbox", "checkbox","checkbox","checkbox");
		Assert.assertEquals(actualReviewFilterList,expReviewFilterList);
		
		int reviewRating = driver.findElements(By.xpath("//div[@class='score']")).size();
		for(int i =0;i<reviewRating;i++) 
		{
			
			String ratingText = driver.findElements(By.xpath("//div[@class='score']")).get(i).getText();
			Assert.assertTrue(Double.parseDouble(ratingText)>0);

			String reviewCountText = driver.findElements(By.xpath("//div[contains(text(),'(')]")).get(i).getText().replaceAll("[^a-zA-Z0-9_-]", "");
			Assert.assertTrue(Double.parseDouble(reviewCountText)>0);

			String nameText = driver.findElements(By.xpath("//a[contains(@id,'searchContainer_link')]//h2[1]")).get(i).getText();
			Assert.assertTrue(nameText!=null);
		}

	}


}
