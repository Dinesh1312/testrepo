package accountManager;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hslf.record.Sound;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;

public class TransactionMonitor extends WebDriverImpl {

	@BeforeClass
	public void browser() throws Exception {
		 driver = accountManagerLogin();

		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl + "Landing Page URL");
		wait(3000);
		click(driver.findElement(By.cssSelector("[path='/user/account/transaction']")));
	}
	
	@AfterClass
	public void afterClass() throws Exception {
		//browserQuit();
	}
	
	@Test
	public void verifyLandingPage() {

		
		String currentUrl = driver.getCurrentUrl();
		
		List<String> transactionMonitorPageDateFilterList = driver.findElements(By.xpath("//div[contains(@id,'filteringandexport')]//div[text()]")).stream()
				.map(d->d.getText()).collect(Collectors.toList());
		List<String> asList = Arrays.asList("Today","7D","15D","1M","6M","1Y");
		Assert.assertEquals(transactionMonitorPageDateFilterList, asList);

		Assert.assertEquals(driver.findElement(By.id("transactionMonitor_transactionCardList_acc_header_name")).getText(), 
				driver.findElement(By.id("nav_breadcumb_title")).getText());
		Assert.assertEquals(currentUrl, "https://app"+loginData.getENV()+".experience.com/user/account/transaction");

		Assert.assertEquals(driver.findElement(By.id("TransactionMonitor_nav_label")).getText(), "Transaction Monitor");
		Assert.assertEquals(driver.findElement(By.id("nav_breadcumb_title")).getText(), "Automation account -Dont delete");
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'nav_label')]//following::span[1]")).isDisplayed(), "Org name is not displayed");
		Assert.assertTrue(driver.findElement(By.id("product_guide")).isDisplayed(), "Help Icon is not displayed");
		Assert.assertTrue(driver.findElement(By.id("profile_container_div")).isDisplayed(), "right side Profile conatiner displayed");

		List<String> actualTransactionsList = driver.findElements(By.xpath("//div[contains(@id,'transactionMonitor_transactionCardList_acc_summayCard')]/following::div[2]")).stream().map(m->m.getText()).collect(Collectors.toList());

		List<String> expectedTransactionsList = Arrays.asList("Processed Transactions","Surveys Sent","Surveys Completed","Reminder Sent",
				"Unprocessed Transactions","Mismatched Transactions","Uncollected Transactions","Uncategorized Transactions");
		Assert.assertEquals(actualTransactionsList,expectedTransactionsList);

		List<String> transactionMonitorPageFilterList = driver.findElements(By.xpath("//div[@class='ant-card-body']//input")).stream()
				.map(d->d.getAttribute("type")).collect(Collectors.toList());
		List<String> listOfFilters = Arrays.asList("search","search");
		Assert.assertEquals(transactionMonitorPageFilterList, listOfFilters);

		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-collapse-content-box']")).isDisplayed(), "transaction monitor graph is displayed");
		wait(3000);
		Assert.assertTrue(driver.findElement(By.id("undefined_refreshedTime")).getText().contains("Refreshed on"));
		Assert.assertTrue(driver.findElement(By.xpath("(//span[@id='undefined_refreshedTime']//span)[1]")).isDisplayed(), "refreshed time is displayed");
        System.out.println("PASSED");
	}
}
