package agentLogin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import driversAndLibrary.WebDriverImpl;

public class Settings extends WebDriverImpl {
	public static WebDriver driver;

	@BeforeClass
	public void beforeClass() throws Exception {
		driver = agentLogin();
	}

	@AfterClass
	public void afterClass() {
		WebDriverImpl.browserQuit();
	}
	@Test()
	public void tc01_verifySettings() throws Exception {
		try {
			wait(3000);
			moveToElement(driver.findElement(By.id("menuList_settings")));
			click(By.id("menuList_settings"));
			Assert.assertTrue(driver.findElement(By.id("page-container")).isDisplayed());
			Assert.assertEquals(driver.findElement(By.id("Settings_nav_label")).getText(), "Settings");
			Assert.assertEquals(driver.findElement(By.xpath("//form[@id='edit_group']//preceding::h1")).getText(), "Password Management");
			Assert.assertTrue(driver.findElements(By.xpath("//input[@type='password']")).stream().allMatch(m -> m.isEnabled()));
			fluentVisibilityOfElement(driver.findElement(By.id("profile_view_button")));
			wait(1000);
			moveToElement(driver.findElement(By.id("profile_view_button")));
			click(By.id("profile_view_button"));
			Assert.assertEquals(driver.findElement(By.xpath("//button[@id='review_settings_button']//following::h1")).getText(), "Profile View");
			wait(3000);
			List<String> profileviewCategoryLists = driver.findElements(By.xpath("//button[contains(@id,'toggle_')]/preceding::div[1]")).stream().map(m -> m.getText().trim()).collect(Collectors.toList());
			profileviewCategoryLists.forEach(System.out::println);
			List<String> expectpedProfileviewCategoryLists = Arrays.asList("Contact Form", "Reviews", "Description", "Positions", "Licenses", "NPS Score", "Year Started", "Secondary Business Category", "Products & Services Offered"
					, "Specialities", "Awards", "Achievements", "Memberships", "Social Links", "Gallery", "Hobbies", "Additional Info", "Disclaimer", "Display Address on Profile Page",
					"Display Service Area on Profile Page", "Business Hours", "Hide breadcrumbs on public pages");
			Assert.assertEquals(profileviewCategoryLists, expectpedProfileviewCategoryLists);
			Assert.assertTrue(driver.findElement(By.xpath("//button[contains(@id,'toggle')]")).isEnabled());
			waitForElementToBeClickableAndClick(By.id("review_settings_button"));
			fluentVisibilityOfElement(driver.findElement(By.xpath("//span[text()='Reviews Settings']//following::div[1]")));
			Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Reviews Settings']//following::div[1]")).isDisplayed());
			List<String> reviewsSettingsSocialShareLists = driver.findElements(By.xpath("//span[@class='ant-collapse-header-text']//span[text()]")).stream().map(M -> M.getText()).collect(Collectors.toList());
			List<String> expectedReviewsSettingsSocialShareLists = Arrays.asList("Facebook", "Twitter", "LinkedIn");
			Assert.assertEquals(reviewsSettingsSocialShareLists, expectedReviewsSettingsSocialShareLists);
			Assert.assertTrue(driver.findElement(By.xpath("//button[contains(@id,'toggle')]")).isEnabled());
			moveToElement(driver.findElement(By.xpath("(//ul[@id='navSidePanel_div']//div)[1]")));
			wait(1000);
			moveToElement(driver.findElement(By.id("globalMenu_changePass_btn")));
			click(By.id("globalMenu_changePass_btn"));
			fluentVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-header']")));
			wait(1000);
			Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ant-drawer-header']")).getText(), "User Settings");
			Assert.assertTrue(driver.findElements(By.xpath("//input[@type='password']")).stream().allMatch(m -> m.isEnabled()));
		}finally {
			wait(2000);
			moveToElement(driver.findElement(By.xpath("//span[@aria-label='close']")));
			click(By.xpath("//span[@aria-label='close']"));
		}




	

	}
}
