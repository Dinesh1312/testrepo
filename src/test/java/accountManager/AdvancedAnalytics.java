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
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

public class AdvancedAnalytics extends WebDriverImpl {

	@BeforeClass
	public void browser() throws Exception {
		driver = accountManagerLogin();
		wait(5000);
		click(driver.findElement(By.id("menuList_dashboard")));
		click(driver.findElement(By.id("menuList_analytics")));
	}

	@AfterClass
	public void afterclass() {
		browserQuit();
	}


	//iframe related testcases
	@Feature("Related To Dart")
	@Owner("Ashwin")
	@Test()
	public void tc001_advancedAnalyticsLandingPage() {
		try {
			wait(5000);
			Assert.assertEquals(getCurrentUrl(),"https://app"+loginData.getENV()+".experience.com/user/account/analytics");
			Assert.assertTrue(driver.findElement(By.id("nav_breadcumb_title")).getText()!=null);
			Assert.assertTrue(driver.findElement(By.id("profile_container_div")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.id("product_guide")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.id("data-test-menu")).isDisplayed());
			wait(3000);
			fluentVisibilityOfElement(driver.findElement(By.cssSelector("div#advancedAnalytics>iframe")));
			singleFrame(driver.findElement(By.cssSelector("div#advancedAnalytics>iframe")));
			fluentVisibilityOfElement(driver.findElement(By.id("viz-viewer-toolbar")));
			Assert.assertTrue(driver.findElements(By.cssSelector("div.tab-zone-padding>div")).size()==5);
			Assert.assertTrue(driver.findElements(By.cssSelector("div.tab-zone-padding>div")).stream().allMatch(m->m.isDisplayed()), "Analytics landing page Fields are not displayed");
			Assert.assertEquals(driver.findElement(By.xpath("(//div[@class='tab-textRegion-content']//child::span)[2]")).getText(),"Welcome to Experience.com Advanced Analytics section");
			Assert.assertTrue(driver.findElement(By.id("viz-viewer-toolbar")).isDisplayed());

		} catch (Exception e) {
			// TODO: handle exception
			Assert.fail();
		}
		finally {
			defaultFrame();
		}
	}

	@Feature("Related To Dart")
	@Owner("Ashwin")
	@Test()
	public void tc002_advancedAnalyticsAccountStatisticsLandingPage() {
		try {
			fluentVisibilityOfElement(driver.findElement(By.cssSelector("div#advancedAnalytics>iframe")));
			singleFrame(driver.findElement(By.cssSelector("div#advancedAnalytics>iframe")));
			wait(3000);
			fluentVisibilityOfElement(driver.findElement(By.id("tabZoneId5")));
			click(By.id("tabZoneId5"));
			wait(3000);
			System.out.println(driver.findElements(By.cssSelector("div.tab-zone-padding")).size());
			Assert.assertTrue(driver.findElements(By.cssSelector("div.tab-zone-padding")).size()==13);
			Assert.assertTrue(driver.findElements(By.cssSelector("div.tab-zone-padding")).stream().allMatch(m->m.isDisplayed()), "Analytics landing page Fields are not displayed");

			Assert.assertTrue(driver.findElements(By.cssSelector("div[data-testid='tab-custom-button']")).stream().allMatch(m->m.isEnabled()), "Buttons are not enabled");

			List<WebElement> accountStaticsDataFields = driver.findElements(By.xpath("//div[@id='tabZoneId28']/preceding::div[@class='tvimagesContainer']/canvas[2]"));
			for (WebElement dataFields : accountStaticsDataFields) {
				moveToElement(dataFields);
				wait(1000);
				System.out.println("Surveys Data"+Integer.parseInt(driver.findElement(By.xpath("//div[@class='tab-ubertipTooltip']//following::span")).getText().replace(",", "")));
				Assert.assertTrue(Integer.parseInt(driver.findElement(By.xpath("//div[@class='tab-ubertipTooltip']//following::span")).getText().replace(",", ""))>0);
			}

			Assert.assertTrue(driver.findElement(By.cssSelector("div.CategoricalFilterBox")).isDisplayed(),"Filters are not displayed");


			List<String> expectedGraphTableHeader = Arrays.asList("Reviews Count on Social Sites","Gateway Response","Overall Survey Stats Heatmap");
			List<String> actualGraphTableHeader = driver.findElements(By.xpath("//div[@class='tab-textRegion tab-widget']//following::div[@class='tab-textRegion-content tab-selectable']/span")).stream().map(d->d.getText()).collect(Collectors.toList());
			actualGraphTableHeader.forEach(System.out::println);
			Assert.assertEquals(actualGraphTableHeader,expectedGraphTableHeader);

			Assert.assertTrue(driver.findElement(By.id("viz-viewer-toolbar")).isDisplayed());

		} finally {
			waitForElementToBeClickableAndClick(By.xpath("//div[@data-testid='tab-button-zone-image-enabled-container']"));
			defaultFrame();
		}
	}

	@Feature("Related To Dart")
	@Owner("Ashwin")
	@Test()
	public void tc003_advancedAnalyticsTierStatisticsLandingPage() {
		try {
			fluentVisibilityOfElement(driver.findElement(By.cssSelector("div#advancedAnalytics>iframe")));
			singleFrame(driver.findElement(By.cssSelector("div#advancedAnalytics>iframe")));
			wait(3000);

			fluentVisibilityOfElement(driver.findElement(By.id("tabZoneId7")));
			click(By.id("tabZoneId7"));
			wait(3000);
			System.out.println(driver.findElements(By.xpath("//div[@id='tabZoneId60']//following::div[@class='tab-zone-padding']")).size());
			Assert.assertTrue(driver.findElements(By.xpath("//div[@id='tabZoneId60']//following::div[@class='tab-zone-padding']")).size()==11);
			Assert.assertTrue(driver.findElements(By.xpath("//div[@id='tabZoneId60']//following::div[@class='tab-zone-padding']")).stream().allMatch(m->m.isDisplayed()), "Analytics landing page Fields are not displayed");

			Assert.assertTrue(driver.findElements(By.cssSelector("div[data-testid='tab-custom-button']")).size()==3);
			Assert.assertTrue(driver.findElements(By.cssSelector("div[data-testid='tab-custom-button']")).stream().allMatch(m->m.isEnabled()), "Buttons are not enabled");

			List<WebElement> accountStaticsDataFields = driver.findElements(By.xpath("//div[@id='tabZoneId11']/preceding::div[@class='tvimagesContainer']/canvas[2]"));
			for (WebElement dataFields : accountStaticsDataFields) {
				moveToElement(dataFields);
				wait(1000);
				System.out.println("Surveys Data"+Integer.parseInt(driver.findElement(By.xpath("//div[@class='tab-ubertipTooltip']//following::span")).getText().replace(",", "")));
				Assert.assertTrue(Integer.parseInt(driver.findElement(By.xpath("//div[@class='tab-ubertipTooltip']//following::span")).getText().replace(",", ""))>=0);
			}

			Assert.assertTrue(driver.findElements(By.cssSelector("div.CategoricalFilterBox")).stream().allMatch(m->m.isDisplayed()), "Fields are not displayed");

			Assert.assertTrue(driver.findElements(By.xpath("(//div[@class='tab-vizHeaderHolderWrapper'])[1]/div[@class='tab-vizHeaderWrapper']")).size()>0);

			Assert.assertTrue(driver.findElements(By.xpath("//div[@class='tab-vizLeftSceneMargin']")).size()==3);
			Assert.assertTrue(driver.findElements(By.xpath("//div[@class='tab-vizLeftSceneMargin']")).stream().allMatch(m->m.isDisplayed()), "Y-axis are not displayed");

			Assert.assertTrue(driver.findElements(By.xpath("//div[@class='tab-vizBottomSceneMargin']")).size()==2);
			Assert.assertTrue(driver.findElements(By.xpath("//div[@class='tab-vizBottomSceneMargin']")).stream().allMatch(m->m.isDisplayed()), "X-axis are not displayed");


			List<String> expectedGraphTableHeader = Arrays.asList("Tier Leaderboard","Tiers Survey Stats Heatmap","Incomplete Surveys by Tiers","Survey Score Timeline of Tiers");
			List<String> actualGraphTableHeader = driver.findElements(By.xpath("//div[@class='tab-textRegion tab-widget']//following::div[@class='tab-textRegion-content tab-selectable']/span")).stream().map(d->d.getText()).collect(Collectors.toList());
			actualGraphTableHeader.forEach(System.out::println);
			Assert.assertEquals(actualGraphTableHeader,expectedGraphTableHeader);

			Assert.assertTrue(driver.findElement(By.id("viz-viewer-toolbar")).isDisplayed());

		} finally {
			waitForElementToBeClickableAndClick(By.xpath("//div[@data-testid='tab-button-zone-image-enabled-container']"));
			defaultFrame();
		}
	}

	@Feature("Related To Dart")
	@Owner("Ashwin")
	@Test()
	public void tc004_advancedAnalyticsTierStatisticsLandingPage() {
		try {
			fluentVisibilityOfElement(driver.findElement(By.cssSelector("div#advancedAnalytics>iframe")));
			singleFrame(driver.findElement(By.cssSelector("div#advancedAnalytics>iframe")));
			wait(3000);
			fluentVisibilityOfElement(driver.findElement(By.id("tabZoneId6")));
			click(By.id("tabZoneId6"));
			wait(3000);
			System.out.println(driver.findElements(By.xpath("//div[@id='tabZoneId57']//following::div[@class='tab-zone-padding']")).size());
			Assert.assertTrue(driver.findElements(By.xpath("//div[@id='tabZoneId57']//following::div[@class='tab-zone-padding']")).size()==11);
			Assert.assertTrue(driver.findElements(By.xpath("//div[@id='tabZoneId57']//following::div[@class='tab-zone-padding']")).stream().allMatch(m->m.isDisplayed()), "Analytics landing page Fields are not displayed");

			Assert.assertTrue(driver.findElements(By.cssSelector("div[data-testid='tab-custom-button']")).size()==3);
			Assert.assertTrue(driver.findElements(By.cssSelector("div[data-testid='tab-custom-button']")).stream().allMatch(m->m.isEnabled()), "Buttons are not enabled");

			List<WebElement> accountStaticsDataFields = driver.findElements(By.xpath("//div[@id='tabZoneId12']/preceding::div[@class='tvimagesContainer']/canvas[2]"));
			for (WebElement dataFields : accountStaticsDataFields) {
				moveToElement(dataFields);
				wait(1000);
				System.out.println("Surveys Data"+Integer.parseInt(driver.findElement(By.xpath("//div[@class='tab-ubertipTooltip']//following::span")).getText().replace(",", "")));
				Assert.assertTrue(Integer.parseInt(driver.findElement(By.xpath("//div[@class='tab-ubertipTooltip']//following::span")).getText().replace(",", ""))>0);
			}

			Assert.assertTrue(driver.findElements(By.cssSelector("div.CategoricalFilterBox")).stream().allMatch(m->m.isDisplayed()), "Fields are not displayed");

			Assert.assertTrue(driver.findElements(By.xpath("(//div[@class='tab-vizHeaderHolderWrapper'])[1]/div[@class='tab-vizHeaderWrapper']")).size()>0);

			Assert.assertTrue(driver.findElements(By.xpath("//div[@class='tab-vizLeftSceneMargin']")).size()==3);
			Assert.assertTrue(driver.findElements(By.xpath("//div[@class='tab-vizLeftSceneMargin']")).stream().allMatch(m->m.isDisplayed()), "Y-axis are not displayed");

			Assert.assertTrue(driver.findElements(By.xpath("//div[@class='tab-vizBottomSceneMargin']")).size()==2);
			Assert.assertTrue(driver.findElements(By.xpath("//div[@class='tab-vizBottomSceneMargin']")).stream().allMatch(m->m.isDisplayed()), "X-axis are not displayed");

			List<String> expectedGraphTableHeader = Arrays.asList("Agent Leaderboard","Agent Survey Stats Heatmap","Survey Score Timeline of Agents","Incomplete Surveys by Agent");
			List<String> actualGraphTableHeader = driver.findElements(By.xpath("//div[@class='tab-textRegion tab-widget']//following::div[@class='tab-textRegion-content tab-selectable']/span")).stream().map(d->d.getText().trim()).collect(Collectors.toList());
			actualGraphTableHeader.forEach(System.out::println);
			Assert.assertEquals(actualGraphTableHeader,expectedGraphTableHeader);

			Assert.assertTrue(driver.findElement(By.id("viz-viewer-toolbar")).isDisplayed());

		} finally {
			waitForElementToBeClickableAndClick(By.xpath("//div[@data-testid='tab-button-zone-image-enabled-container']"));
			defaultFrame();
		}
	}




}
