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

public class Transactions extends WebDriverImpl{

	@BeforeClass
	public void browser() throws Exception {

		 driver = accountManagerLogin();
	}

	@AfterClass
	public void afterclass() {
		browserQuit();
	}


	@Test
	public void transactionsLandingPage() {
		wait(4000);
		fluentVisibilityOfElement(
				driver.findElement(By.xpath("//div[@data-menu-id='navSidePanel_div-/user/account/unprocessed']")));
		click(driver.findElement(By.xpath("//div[@data-menu-id='navSidePanel_div-/user/account/unprocessed']")));
		fluentVisibilityOfElement(driver.findElement(By.id("menuList_unprocessed")));
		click(driver.findElement(By.id("menuList_unprocessed")));
		WebElement title_element = driver.findElement(By.id("nav_breadcumb_title"));
		fluentVisibilityOfElement(title_element);
		Assert.assertEquals(title_element.getText(), "Automation account -Dont delete");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/unprocessed");
		Assert.assertEquals(driver.findElement(By.id("Transactions_nav_label")).getText(), "Transactions");
		wait(3000);
		List<String> act_unprocessedlist = driver.findElements(By.xpath("//div[text()='Duplicate']/preceding::div[@id='mismatch_tab_0']/div")).stream()
				.map(m -> m.getText().trim()).collect(Collectors.toList());
		act_unprocessedlist.forEach(System.out::println);
		moveToElement((driver.findElement(By.xpath("//div[text()='Duplicate']"))));
		act_unprocessedlist.add(driver.findElement(By.xpath("//div[text()='Duplicate']")).getText().trim());
		List<String> exp_act_unprocessedlist = Arrays.asList("Mismatched", "Processed", "Uncollected", "Unsubscribed",
				"Mapped", "Archived", "Corrupt", "Ignored","Duplicate");
		Assert.assertEquals(act_unprocessedlist, exp_act_unprocessedlist);
		List<String> transactionTableHeaders = driver
				.findElements(By.xpath("//input[@aria-label='Select all']//following::th")).stream().map(m -> m.getText().trim())
				.collect(Collectors.toList());
		
		List<String> exp_transactionTableHeaders = Arrays.asList("Name", "Customer Name", "Source Transaction ID","Mismatch Reason","Date",
				"Action");

		Assert.assertEquals(transactionTableHeaders, exp_transactionTableHeaders);
		Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='ant-table-tbody']")).isDisplayed()) ;
		click(driver.findElement(By.id("menuList_dashboard")));
	}

}
