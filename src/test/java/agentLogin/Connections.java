package agentLogin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;

public class Connections extends WebDriverImpl{
	public static WebDriver driver;


	@BeforeClass
	public void beforeClass() throws Exception {
		driver = agentLogin();
	}

	@AfterClass
	public void afterClass() {
		//WebDriverImpl.browserQuit();
	}

	@BeforeMethod
	public void beforeMethod() throws Exception {
		wait(2000);
		waitForVisibilityOfElement(driver.findElement(By.xpath("//li[@id='menuList_integrations']")));
	}

	@Test
	public void tc01_landingConnectionPage() {
		wait(2000);
		waitForElementToBeClickableAndClick(By.id("menuList_integrations"));
		Assert.assertEquals(driver.findElement(By.xpath("//h3[@id='connections-header']")).getText().trim(), "Connections");
		int overAllConnections = driver.findElements(By.xpath("//span[text()='Connect' or text()='Connected']")).size();
		if (overAllConnections==10) {
			Assert.assertTrue(true,"connections having 9 sources to connect");
		}else {
			Assert.fail();
		}
	}

	@Test
	public void tc02_googleMyBusinessConnectionDrawerValidation() {
		try {
			wait(3000);
		int googleConnection = driver.findElements(By.xpath("//div[text()='Google Business Profile']/following::button[@id='connect_button_0']")).size();
		if (googleConnection==1) {
			waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Google Business Profile']/following::button[@id='connect_button_0']")));
			fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='connect_google_button']//preceding::div[1]")));
			String text = driver.findElement(By.xpath("//button[@id='connect_google_button']//preceding::div[1]")).getText();
			if (text.contains("Google Business Profile")) {
				Assert.assertTrue(true, "Google Business Profile Drawer Opened as Expected");
			}else {
				Assert.fail("Google Business Profile Drawer Not Opened as Expected");
			}
		}else if (googleConnection!=1) {
			waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Google Business Profile']/following::div[@id='manage_connection_0']")));
			wait(3000);
			fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")));
			String text = driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")).getText();
			if (text.contains("Google Business Profile")) {
				Assert.assertTrue(true, "Google Business Profile Drawer Opened as Expected");
			}else {
				Assert.fail("Google Business Profile Drawer Not Opened as Expected");
			}
		}
		} finally {
			cancelDrawer();
		}
	}

	@Test
	public void tc03_facebookConnectionDrawerValidation() {
		try {
		wait(2000);
		int facebookConnection = driver.findElements(By.xpath("//div[text()='Facebook']/following::button[@id='connect_button_1']")).size();
		if (facebookConnection==1) {
			waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Facebook']/following::button[@id='connect_button_1']")));
			fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='connect_fb_button']//preceding::div[1]")));
			String text = driver.findElement(By.xpath("//button[@id='connect_fb_button']//preceding::div[1]")).getText();
			if (text.contains("Facebook")) {
				Assert.assertTrue(true, "Facebook Connection Drawer Opened as Expected");
			}else {
				Assert.fail("Facebook Connection Drawer Not Opened as Expected");
			}
		}else if (facebookConnection!=1) {
			waitForElementToBeClickableAndClick(driver.findElement(By.xpath("//div[text()='Facebook']/following::div[@id='manage_connection_1']")));
			fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")));
			String text = driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")).getText();
			if (text.contains("Facebook")) {
				Assert.assertTrue(true, "Facebook Connection Drawer Opened as Expected");
			}else {
				Assert.fail("Facebook Connection Drawer Not Opened as Expected");
			}
		}
		}finally {
		cancelDrawer();
		}
	}

	@Test
	public void tc04_twitterConnectionDrawerValidation() {
		try {
		wait(2000);
		int twitterConnection = driver.findElements(By.xpath("//button[@id='connect_button_2']")).size();
		if (twitterConnection==1) {
			waitForElementToBeClickableAndClick(By.xpath("//button[@id='connect_button_2']"));
			fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='connect_twitter_button']//preceding::div[1]")));
			String text = driver.findElement(By.xpath("//button[@id='connect_twitter_button']//preceding::div[1]")).getText();
			if (text.contains("Twitter")) {
				Assert.assertTrue(true, "Twitter Connection Drawer Opened as Expected");
			}else {
				Assert.fail("Twitter Connection Drawer Not Opened as Expected");
			}
		}else if (twitterConnection!=1) {
			waitForElementToBeClickableAndClick(By.xpath("//div[@id='manage_connection_2']"));
			fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")));
			String text = driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")).getText();
			if (text.contains("Twitter")) {
				Assert.assertTrue(true, "Twitter Connection Drawer Opened as Expected");
			}else {
				Assert.fail("Twitter Connection Drawer Not Opened as Expected");
			}
		}
		}finally {
			cancelDrawer();
		}
	}

	@Test
	public void tc05_linkedinConnectionDrawerValidation() {
		try {
		wait(2000);
		int linkedinConnection = driver.findElements(By.xpath("//button[@id='connect_button_3']")).size();
		if (linkedinConnection==1) {
			waitForElementToBeClickableAndClick(By.xpath("//button[@id='connect_button_3']"));
			wait(3000);
			fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='connect_linkedin_button']//preceding::div[1]")));
			String text = driver.findElement(By.xpath("//button[@id='connect_linkedin_button']//preceding::div[1]")).getText();
			if (text.contains("LinkedIn")) {
				Assert.assertTrue(true, "Linkedin Connection Drawer Opened as Expected");
			}else {
				Assert.fail("Linkedin Connection Drawer Not Opened as Expected");
			}
		}else if (linkedinConnection!=1) {
			waitForElementToBeClickableAndClick(By.xpath("//div[@id='manage_connection_3']"));
			fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")));
			String text = driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")).getText();
			if (text.contains("LinkedIn")) {
				Assert.assertTrue(true, "Linkedin Connection Drawer Opened as Expected");
			}else {
				Assert.fail("Linkedin Connection Drawer Not Opened as Expected");
			}
		}
		}finally {
			cancelDrawer();
		}
	}

	@Test
	public void tc06_zillowConnectionDrawerValidation() {
		try{
		wait(3000);
		int zillowConnection = driver.findElements(By.xpath("//button[@id='connect_button_5']")).size();
		if (zillowConnection==1) {
			waitForElementToBeClickableAndClick(By.xpath("//button[@id='connect_button_5']"));
			fluentVisibilityOfElement(driver.findElement(By.xpath("(//div[@class='ant-drawer-body']//following::div)[1]")));
			String text = driver.findElement(By.xpath("(//div[@class='ant-drawer-body']//following::div)[1]")).getText();
			if (text.contains("Zillow")) {
				Assert.assertTrue(true, "Zillow Connection Drawer Opened as Expected");
			}else {
				Assert.fail("Zillow Connection Drawer Not Opened as Expected");
			}
		}else if (zillowConnection!=1) {
			waitForElementToBeClickableAndClick(By.xpath("//div[@id='manage_connection_5']"));
			fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")));
			String text = driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")).getText();
			if (text.contains("Zillow")) {
				Assert.assertTrue(true, "Zillow Connection Drawer Opened as Expected");
			}else {
				Assert.fail("Zillow Connection Drawer Not Opened as Expected");
			}}
		} finally {
			cancelDrawer();
		}
	}

	@Test
	public void tc07_InstagramConnectionDrawerValidation() {
		try {
			wait(2000);
			int zillowConnection = driver.findElements(By.xpath("//button[@id='connect_button_6']")).size();
			if (zillowConnection == 1) {
				waitForElementToBeClickableAndClick(By.xpath("//button[@id='connect_button_6']"));
				fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='connect_fb_for_ig_button']//preceding::div[1]")));
				String text = driver.findElement(By.xpath("//button[@id='connect_fb_for_ig_button']//preceding::div[1]")).getText();
				if (text.contains("Instagram")) {
					Assert.assertTrue(true, "Instagram Connection Drawer Opened as Expected");
				} else {
					Assert.fail("Instagram Connection Drawer Not Opened as Expected");
				}
			} else if (zillowConnection != 1) {
				waitForElementToBeClickableAndClick(By.xpath("//div[@id='manage_connection_6']"));
				fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")));
				String text = driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")).getText();
				if (text.contains("Instagram")) {
					Assert.assertTrue(true, "Instagram Connection Drawer Opened as Expected");
				} else {
					Assert.fail("Instagram Connection Drawer Not Opened as Expected");
				}
			}
		} finally {
			cancelDrawer();
		}
	}

	@Test
	public void tc08_lendiingTreeConnectionDrawerValidation() {
		try {
			wait(3000);
			int zillowConnection = driver.findElements(By.xpath("//button[@id='connect_button_7']")).size();
			if (zillowConnection == 1) {
				waitForElementToBeClickableAndClick(By.xpath("//button[@id='connect_button_7']"));
				fluentVisibilityOfElement(driver.findElement(By.xpath("//input[@id='Lending Treeform_Lending Tree']//preceding::div[2]")));
				String text = driver.findElement(By.xpath("//input[@id='Lending Treeform_Lending Tree']//preceding::div[2]")).getText();
				if (text.contains("Lending Tree")) {
					Assert.assertTrue(true, "Lending Tree Connection Drawer Opened as Expected");
				} else {
					Assert.fail("Lending Tree Connection Drawer Not Opened as Expected");
				}
			} else if (zillowConnection != 1) {
				waitForElementToBeClickableAndClick(By.xpath("//div[@id='manage_connection_7']"));
				fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")));
				String text = driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")).getText();
				if (text.contains("Lending Tree")) {
					Assert.assertTrue(true, "Lending Tree Connection Drawer Opened as Expected");
				} else {
					Assert.fail("Lending Tree Connection Drawer Not Opened as Expected");
				}
			}
		} finally {
			cancelDrawer();
		}
	}


	@Test
	public void tc09_realtorTreeConnectionDrawerValidation() throws Exception {
		try {
			wait(2000);
			int zillowConnection = driver.findElements(By.xpath("//button[@id='connect_button_8']")).size();
			if (zillowConnection == 1) {
				waitForElementToBeClickableAndClick(By.xpath("//button[@id='connect_button_8']"));
				fluentVisibilityOfElement(driver.findElement(By.xpath("//input[@id='Realtorform_Realtor']//preceding::div[2]")));
				String text = driver.findElement(By.xpath("//input[@id='Realtorform_Realtor']//preceding::div[2]")).getText();
				if (text.contains("Realtor")) {
					Assert.assertTrue(true, "Realtor Connection Drawer Opened as Expected");
				} else {
					Assert.fail("Realtor Tree Connection Drawer Not Opened as Expected");
				}
			} else if (zillowConnection != 1) {
				wait(3000);
				safeJavaScriptClick(driver.findElement(By.xpath("//div[@id='manage_connection_8']")));
				fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")));
				String text = driver.findElement(By.xpath("//button[@id='disconnect_button']//preceding::div[1]")).getText();
				if (text.contains("Realtor")) {
					Assert.assertTrue(true, "Realtor Connection Drawer Opened as Expected");
				} else {
					Assert.fail("Realtor Connection Drawer Not Opened as Expected");
				}
			}
		}finally {
			cancelDrawer();
		}
	}

	/*
	public void tc09_yelpConnectionDrawerValidation() throws Exception {
      try {
    	  wait(2000);
			int zillowConnection = driver.findElements(By.xpath("//button[@id='connect_button_9']")).size();
			if (zillowConnection == 1) {
				waitForElementToBeClickableAndClick(By.xpath("//button[@id='connect_button_8']"));
				fluentVisibilityOfElement(driver.findElement(By.xpath("//input[@id='Realtorform_Realtor']//preceding::div[2]")));
				String text = driver.findElement(By.xpath("//input[@id='Realtorform_Realtor']//preceding::div[2]")).getText();
			
} */

	public void cancelDrawer() {
		wait(2000);
		click(By.xpath("//span[@aria-label='close']"));
	}

}	










