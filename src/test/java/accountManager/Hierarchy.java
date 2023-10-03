package accountManager;

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
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

public class Hierarchy extends WebDriverImpl {
	
	public static WebDriver driver;
	
	List<String> expectedUserEditProfileInfoHeader = Arrays.asList("Business Information","Business Hours","Address","Add Address","Contact Information","Social Media Links",
			"Images","Awards","Position","Products & Services Offered","Memberships","Specialities","Achievements","Hobbies","Licenses","Additional Information");
	
	List<String> expectedUserEditProfileSettingsHeader = Arrays.asList("Contact Form on the Pro Page","Reviews","Description","Positions","Licenses",
			"NPS Score","Year Started","Secondary Business Category","Products & Services Offered","Specialities","Awards","Achievements","Memberships","Social Links","Gallery",
			"Hobbies","Additional Info","Disclaimer","Business Hours","Hide breadcrumbs on public pages");

	List<String> expectedTierEditProfileInfoHeader = Arrays.asList("Business Information","Business Hours","Address","Change Address","Contact Information","Social Media Links",
			"Images","Awards","Products & Services Offered","Additional Information");
	
	List<String> expectedTierEditProfileSettingsHeader = Arrays.asList("Contact Form on the Pro Page","Reviews","Our Professionals","Description","Our Company",
			"NPS Score","Year Started","Secondary Business Category","Products & Services Offered","Awards","Social Links","Gallery",
			"Additional Info","Point of Contact","Disclaimer","Business Hours","Hide breadcrumbs on public pages");
	
	String expectedDeactivateUserText="Are you sure want to deactivate the user and dependent profiles?";
	
	String expectedDeactivateTierText="Are you sure you want to deactivate the selected tier?";
	
	
	
	@BeforeClass
	public void browser() throws Exception {
		 driver = accountManagerLogin();
	}
	
	@AfterClass
	public void afterclass() {
		//browserQuit();
	}

	@Feature("Related To Core,Hierarchy Module")
	@Owner("Ashwin")
	@Test
	public void tc01_hierarchyLandingPage() {
		waitForElementToBeClickableAndClick(By.id("menuList_hierarchy"));
		hierarchyLandingPage();
		
		Assert.assertTrue(driver.findElement(By.id("hierarchy_upload_button")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("hierarchy_addnew_button")).isDisplayed());
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-card-head-title span")).getText(), "Filter Hierarchy");
		Assert.assertTrue(driver.findElements(By.cssSelector("div.ant-form-item-control-input-content")).stream().allMatch(m->m.isDisplayed()), "Filter fields are not displayed");
		
		List<String> expectedTableHeader = Arrays.asList("Search","Filter by Hierarchy","Category","Profile","Status","Columns");
		List<String> actualTableHeader = driver.findElements(By.xpath("//label[@title]")).stream().map(d->d.getText()).collect(Collectors.toList());
		Assert.assertEquals(actualTableHeader,expectedTableHeader);
		
		Assert.assertTrue(driver.findElement(By.id("hierarchy_table_view")).isDisplayed());
		
		Assert.assertTrue(driver.findElements(By.xpath("//div[@data-tier-checkbox]/parent::div")).size()>0);
		
		click(By.id("hierarchy_upload_button"));
		try {
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText(), "Bulk Upload");
		Assert.assertTrue(driver.findElement(By.id("hierarchy_download_data")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='ant-upload ant-upload-btn']")).isDisplayed());
		} finally {
			click(By.cssSelector("button[class='ant-drawer-close']"));
		}
		
		waitForElementToBeClickableAndClick(By.id("hierarchy_addnew_button"));
		try {
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		wait(2000);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText(), "Create Tier");
		Assert.assertTrue(driver.findElement(By.id("tierName")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("parentTier")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-form-item']/preceding::span[text()='Tier Type']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Create Tier']/ancestor::button[1]")).isEnabled());
		} finally {
			waitForElementToBeClickableAndClick(By.xpath("//div[text()='Cancel']/parent::button[1]"));
		}
		
	}
	
	
	@Feature("Related To Core,Hierarchy Module")
	@Owner("Ashwin")
	@Test
	public void tc02_hierarchyUserLandingPage() {
		
		waitForElementToBeClickableAndClick(By.id("menuList_hierarchy"));
		waitForElementToBeClickableAndClick(By.id("users_button"));
		hierarchyLandingPage();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-card-head-title span")).getText(), "Filter Users");
		Assert.assertTrue(driver.findElements(By.cssSelector("div.ant-form-item-control-input-content")).stream().allMatch(m->m.isDisplayed()), "Filter fields are not displayed");
		
		List<String> expectedFilterHeader = Arrays.asList("Search","Filter by Hierarchy","Select Role","Status","Profile","Verification status","Columns");
		List<String> actualFilterHeader = driver.findElements(By.xpath("//label[@title]")).stream().map(d->d.getText()).collect(Collectors.toList());
		Assert.assertEquals(actualFilterHeader,expectedFilterHeader);
		
		Assert.assertTrue(driver.findElement(By.id("hierarchy_users_select_all")).isDisplayed());
		Assert.assertTrue(driver.findElements(By.cssSelector("tr[class='ant-table-row ant-table-row-level-0']")).size()>0);
		
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='ant-table-column-title']")).getText(), "User Information");
		Assert.assertTrue(driver.findElement(By.xpath("//th[@class='ant-table-cell']/div")).getText().contains("Publish Name"));
		
		moveToElement( driver.findElement(By.xpath("(//span[@id='users_show_action_row'])[1]")));
		wait(2000);
		List<String> expectedUserTableHeader = Arrays.asList("Status","Social Connection","Roles","Published","Verified","Actions");
		List<String> actualUserTableHeader = driver.findElements(By.xpath("//div[text()='Publish Name']/following::th[@class='ant-table-cell']")).stream().map(d->d.getText().trim()).collect(Collectors.toList());
		actualUserTableHeader.forEach(System.out::println);
		Assert.assertEquals(actualUserTableHeader,expectedUserTableHeader);
		
		Assert.assertTrue(driver.findElement(By.id("hierarchy_addnew_button")).isDisplayed());
		waitForElementToBeClickableAndClick(By.id("hierarchy_addnew_button"));
		try {
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText(), "Create New User");
		Assert.assertTrue(driver.findElements(By.cssSelector("div.ant-form-item-control-input-content")).stream().allMatch(m->m.isDisplayed()), "Filter fields are not displayed");
		
		} finally {
			waitForElementToBeClickableAndClick(By.cssSelector("button.ant-drawer-close"));
		}
		
		
	}
	
	@Feature("Related To Core,Hierarchy Module")
	@Owner("Ashwin")
	@Test
	public void tc03_hierarchyUserDrawerLandingPage() throws Exception {
		hierarchyUserThreeDotPage("Admin User");
		hierarchyEditDrawerPage(expectedUserEditProfileInfoHeader,expectedUserEditProfileSettingsHeader);
		hierarchyUserThreeDotPage("Admin User");
		hierarchyMoveUserDrawerPage();
		hierarchyUserThreeDotPage("Admin User");
		hierarchyDeactivateUserDrawerPage("Deactivate User",expectedDeactivateUserText);
		hierarchyUserThreeDotPage("Admin User");
		hierarchyPublishUserDrawerPage();
		hierarchyUserThreeDotPage("Automation Agent3");
		hierarchyUnPublishUserDrawerPage();
		hierarchyUserThreeDotPage("Automation agent Admin");
		hierarchyUserForcePasswordResetDrawerPage();
	}
	
	@Feature("Related To Core,Hierarchy Module")
	@Owner("Ashwin")
	@Test
	public void tc04_hierarchyTierLandingPage() throws Exception {
		
		waitForElementToBeClickableAndClick(By.id("menuList_hierarchy"));
		hierarchyLandingPage();
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-card-head-title span")).getText(), "Filter Hierarchy");
		Assert.assertTrue(driver.findElements(By.cssSelector("div.ant-form-item-control-input-content")).stream().allMatch(m->m.isDisplayed()), "Filter fields are not displayed");
		
		List<String> expectedFilterHeader = Arrays.asList("Search","Filter by Hierarchy","Category","Profile","Status","Columns");
		List<String> actualFilterHeader = driver.findElements(By.xpath("//label[@title]")).stream().map(d->d.getText()).collect(Collectors.toList());
		Assert.assertEquals(actualFilterHeader,expectedFilterHeader);
		
		for (WebElement dropdownfield : driver.findElements(By.cssSelector("div.ant-select-selector"))) {
			fluentVisibilityOfElement(dropdownfield);
			click(dropdownfield);
			Assert.assertTrue(driver.findElements(By.xpath("//div[contains(@class,'ant-select-item ant-select-item-option')]")).size()>0);
			click(By.id("hierarchy_region_count"));
		}
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='hierarchy_table_view']/div/div[1]")).isDisplayed());
		
		Assert.assertTrue(driver.findElement(By.id("hierarchy_table_view")).isDisplayed());
		Assert.assertTrue(driver.findElements(By.xpath("//div[@data-tier-checkbox]/parent::div")).size()>0);
		hierarchyTierUploadDrawerPage();
		hierarchyAddNewTierDrawerPage();
		
	}
	
	@Feature("Related To Core,Hierarchy Module")
	@Owner("Ashwin")
	@Test
	public void tc05_hierarchyTierDrawerLandingPage() {
		hierarchyTierThreeDotPage();
		hierarchyEditDrawerPage(expectedTierEditProfileInfoHeader,expectedTierEditProfileSettingsHeader);
		hierarchyTierThreeDotPage();
		hierarchyDeactivateUserDrawerPage("Deactivate tier",expectedDeactivateTierText);
	}
	
	
	@Feature("Related To Core,Hierarchy Module")
	@Owner("Ashwin")
	@Test
	public void tc06_hierarchyTierBulkOptionsLandingPage() throws Exception {
		waitForElementToBeClickableAndClick(By.id("menuList_hierarchy"));
		hierarchyBulkThreeDotPage();
		hierarchyBulkEditDrawerPage();
		hierarchyBulkProfileSettingsDrawerPage();
		click(By.id("hierarchy_bulk_menu_button"));
		hierarchyDeactivateUserDrawerPage("Deactivate tiers","Are you sure you want to deactivate the selected tiers?");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Methods
	
	
	
	
	public void hierarchyBulkThreeDotPage() {
		
		wait(3000);
		fluentVisibilityOfElement(driver.findElement(By.xpath("(//label[@class='ant-checkbox-wrapper'])[1]/span")));
		click(By.xpath("(//label[@class='ant-checkbox-wrapper'])[1]/span"));
		click(By.id("hierarchy_bulk_menu_button"));
		}
	
	
	public void hierarchyBulkProfileSettingsDrawerPage() {
		
		waitForElementToBeClickableAndClick(By.id("hierarchy_bulk_menu_button"));
		waitForElementToBeClickableAndClick(By.id("hierarchy_bulk_profile_settings"));
		try {
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ant-drawer-title")).isDisplayed());
		wait(3000);
		List<String> expectedTierEditProfileInfoHeader = Arrays.asList("DBA (Doing Business As)","Public Profile Settings","Review Management Settings",
				"Social Share Settings","Facebook","Twitter","LinkedIn");
		List<String> actualUserTableHeader = driver.findElements(By.cssSelector("span.ant-collapse-header-text")).stream().map(d->d.getText()).collect(Collectors.toList());
		actualUserTableHeader.forEach(System.out::println);
		Assert.assertEquals(actualUserTableHeader,expectedTierEditProfileInfoHeader);
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-drawer-body']//following::button[@role='switch']")).isDisplayed());
		Assert.assertTrue(driver.findElements(By.xpath("//div[@class='ant-drawer-body']//following::button[@role='switch']")).size()==12);
		Assert.assertTrue(driver.findElements(By.xpath("//button[contains(@id,'hierarchy_settings')]")).stream().allMatch(d->d.isEnabled()),"Buttons are not enabled");
		
		} finally {
			waitForElementToBeClickableAndClick(By.cssSelector("button.ant-drawer-close"));
			
		}	
		
	}
	
	public void hierarchyBulkEditDrawerPage() throws Exception {
		waitForElementToBeClickableAndClick(By.xpath("//li[contains(@id,'bulk_edit')]"));
		try {
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ant-drawer-title")).isDisplayed());
		wait(2000);
		List<String> expectedTierEditProfileInfoHeader = Arrays.asList("Business Information","Business Hours","Contact Information",
				"Images","Additional Information");
		List<String> actualUserTableHeader = driver.findElements(By.xpath("//span[@class='ant-collapse-header-text']//span")).stream().map(d->d.getText()).collect(Collectors.toList());
		actualUserTableHeader.forEach(System.out::println);
		Assert.assertEquals(actualUserTableHeader,expectedTierEditProfileInfoHeader);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-alert-message")).getText(), "Any changes will override on all selected tiers.");
		wait(2000);
		moveToElement(driver.findElement(By.xpath("//span[contains(text(),'View Selected')]")));
		safeJavaScriptClick(driver.findElement(By.xpath("//span[contains(text(),'View Selected')]")));
		fluentVisibilityOfElement(driver.findElement(By.xpath("(//div[@class='ant-drawer-title'])[2]")));
		Assert.assertEquals(driver.findElement(By.xpath("(//div[@class='ant-drawer-title'])[2]")).getText(),"Locations");
		
		Assert.assertEquals(driver.findElement(By.xpath("(//div[@class='ant-alert-message'])[2]")).getText(),"Locations without profiles are excluded for bulk edit.");
		
		Assert.assertTrue(driver.findElement(By.xpath("(//button[@data-test-add-location='true'])/parent::div")).isDisplayed());
		
		Assert.assertTrue(driver.findElements(By.xpath("//button[@data-test-add-location='true']/following::div/div")).size()>=2);
			
		Assert.assertTrue(driver.findElements(By.xpath("//span[text()='Save' or text()='Cancel']/parent::button")).stream().allMatch(d->d.isEnabled()),"Buttons are not enabled");
		
		} finally {
			waitForElementToBeClickableAndClick(By.cssSelector("button.ant-drawer-close"));
			waitForElementToBeClickableAndClick(By.cssSelector("button[class='ant-btn ant-btn-default']"));
		}	
	}
	

	
	
	public void hierarchyUserThreeDotPage(String agentName) throws Exception {
		
	waitForElementToBeClickableAndClick(By.id("menuList_hierarchy"));
	waitForElementToBeClickableAndClick(By.id("users_button"));
	wait(3000);
	input(By.xpath("//input[@placeholder='Search']"), agentName);
	wait(3000);
	moveToElement( driver.findElement(By.xpath("(//span[contains(@id,'row')])[1]")));
	click(By.id("hierarchy_region_count"));
	moveToElement( driver.findElement(By.xpath("(//span[contains(@id,'row')])[1]")));
	click(By.xpath("(//span[contains(@id,'row')])[1]"));
	wait(1000);
	}
	
	public void hierarchyEditDrawerPage(List<String> expectedEditProfileInfo, List<String> expectedEditProfileSettingsHeader) {
		
		waitForElementToBeClickableAndClick(By.xpath("//li[@id='users_edit_row_action' or @id='hierarchy_edit_item']"));
		try {
		waitForVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ant-drawer-title")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ant-tabs-nav-list")).isDisplayed());
		wait(2000);
		List<String> actualUserTableHeader = driver.findElements(By.xpath("//span[@class='ant-collapse-header-text']//span")).stream().map(d->d.getText()).collect(Collectors.toList());
		actualUserTableHeader.forEach(System.out::println);
		Assert.assertEquals(actualUserTableHeader,expectedEditProfileInfo);
		
		waitForElementToBeClickableAndClick(By.xpath("//div[@id='rc-tabs-2-tab-2' or @id='rc-tabs-3-tab-2']"));
		
		wait(3000);
		
		List<String> actualProfileSettings = driver.findElements(By.xpath("//button[contains(@id,'editProfile_ps')]/preceding::span[1]")).stream().map(d->d.getText()).collect(Collectors.toList());
		actualProfileSettings.forEach(System.out::println);
		Assert.assertEquals(actualProfileSettings,expectedEditProfileSettingsHeader);
		
		
		List<String> expectedProfileSettings1 = Arrays.asList("Display Address on Profile Page","Display Service Area on Profile Page");
		
		List<String> actualProfileSettings1 = driver.findElements(By.xpath("//span[contains(text(),'Display')]")).stream().map(d->d.getText()).collect(Collectors.toList());
		actualProfileSettings1.forEach(System.out::println);
		Assert.assertEquals(actualProfileSettings1,expectedProfileSettings1);
		Assert.assertEquals(driver.findElements(By.xpath("//button[contains(@id,'editProfile_ps')]")).size(),actualProfileSettings.size());
		Assert.assertTrue(driver.findElements(By.xpath("//button[contains(@id,'editProfile_ps')]")).stream().allMatch(m->m.isDisplayed()), "Toggles are not displayed");
		
		} finally {
			waitForElementToBeClickableAndClick(By.cssSelector("button.ant-drawer-close"));
		}	
	}
	
	public void hierarchyMoveUserDrawerPage() {

		waitForElementToBeClickableAndClick(By.id("move_agent_onclick"));
		try {
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ant-drawer-title")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText(), "Move User");
		Assert.assertTrue(driver.findElements(By.xpath("//*[@class='ant-form ant-form-vertical']/div[1]/div")).stream().allMatch(m->m.isDisplayed()), "Move User fields are not displayed");
		wait(2000);
		Assert.assertTrue(driver.findElements(By.xpath("//label[contains(@class,'ant-radio-wrapper ant-radio-wrapper')]")).stream().allMatch(m->m.isDisplayed()), "Radio buttons are not displayed");
		
		waitForElementToBeClickable(driver.findElement(By.id("move_agent_select_account")));
		waitForElementToBeClickable(driver.findElement(By.id("move_agent_select_tier")));
		
		List<String> expectedUserTableHeader = Arrays.asList("Copy reviews","Move reviews");
		List<String> actualUserTableHeader = driver.findElements(By.xpath("//span[contains(text(),'reviews')]")).stream().map(d->d.getText()).collect(Collectors.toList());
		actualUserTableHeader.forEach(System.out::println);
		Assert.assertEquals(actualUserTableHeader,expectedUserTableHeader);
		
		Assert.assertTrue(driver.findElement(By.id("move_agent_validate_agent_continue")).isEnabled(), "Continue button are not enabled");
		
		} finally {
			waitForElementToBeClickableAndClick(By.id("move_agent_validate_agent_back"));
		}	
	}
	
	public void hierarchyDeactivateUserDrawerPage(String deactivateTitle,String deactivateText) {

		waitForElementToBeClickableAndClick(By.xpath("//li[contains(@id,'deactivate')]"));
		try {
		System.out.println("Landing on deactivate drawer page");
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ant-drawer-title")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText(), deactivateTitle);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ant-drawer-body']/div[1]")).getText(), deactivateText);
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Info']/ancestor::div[1]/parent::div")).isDisplayed(), "Informations are not displayed");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-row']")).isDisplayed(), "User is not displayed");
		Assert.assertTrue(driver.findElements(By.xpath("//div[@class='ant-drawer-title']/following::button")).stream().allMatch(d->d.isEnabled()),"Buttons are not enabled");
		
		} finally {
			waitForElementToBeClickableAndClick(By.xpath("//span[text()='Cancel']/parent::button"));
		}	
	}
	
	public void hierarchyPublishUserDrawerPage() {

		waitForElementToBeClickableAndClick(By.id("users_publish_row_action"));
		try {
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ant-drawer-title")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText(), "Publish Profiles");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-body>div>span")).getText(), "The following user profiles will be published");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-row']")).isDisplayed(), "User is not displayed");
		Assert.assertTrue(driver.findElements(By.xpath("//div[@class='ant-drawer-title']/following::button")).stream().allMatch(d->d.isEnabled()),"Buttons are not enabled");
		
		} finally {
			waitForElementToBeClickableAndClick(By.xpath("//div[text()='Continue']/preceding::button[1]"));
		}	
	}
	
	public void hierarchyUnPublishUserDrawerPage() {

		waitForElementToBeClickableAndClick(By.id("users_unpublish_row_action"));
		try {
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ant-drawer-title")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText(), "Unpublish Profile");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-body>div>span")).getText(), "The following user profiles will be unpublished");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-row']")).isDisplayed(), "User is not displayed");
		Assert.assertTrue(driver.findElements(By.xpath("//div[@class='ant-drawer-title']/following::button")).stream().allMatch(d->d.isEnabled()),"Buttons are not enabled");
		
		} finally {
			waitForElementToBeClickableAndClick(By.xpath("//span[text()='Cancel']/ancestor::button[1]"));
		}	
	}
	
	
	public void hierarchyUserForcePasswordResetDrawerPage() {

		waitForElementToBeClickableAndClick(By.id("users_force_password_reset"));
		try {
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ant-drawer-title")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText(), "Force Password Reset");
		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'Once the password is reset')]")).getText(), "Once the password is reset, the selected user will receive a mail to set a new password.");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-body>div>span")).getText(), "Are you sure want reset the password for the following user?");
		Assert.assertTrue(driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-default']/preceding::span[1]")).isDisplayed(), "User is not displayed");
		Assert.assertTrue(driver.findElements(By.xpath("//div[@class='ant-drawer-title']/following::button")).stream().allMatch(d->d.isEnabled()),"Buttons are not enabled");
		
		} finally {
			waitForElementToBeClickableAndClick(By.xpath("//span[text()='Cancel']/ancestor::button[1]"));
		}	
	}
	
	
	
	
	
	public void hierarchyTierUploadDrawerPage() {

		waitForElementToBeClickableAndClick(By.id("hierarchy_upload_button"));
		try {
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText(), "Bulk Upload");
		waitForElementToBeClickable(By.id("hierarchy_download_data"));
		waitForElementToBeClickable(By.cssSelector("span[class='ant-upload ant-upload-btn']"));
		
		} finally {
			waitForElementToBeClickableAndClick(By.cssSelector("button.ant-drawer-close"));
		}	
	}
	
	
	public void hierarchyAddNewTierDrawerPage() {

		waitForElementToBeClickableAndClick(By.id("hierarchy_addnew_button"));
		try {
		fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText(), "Create Tier");
		
		Assert.assertTrue(driver.findElements(By.xpath("//div[@class='ant-drawer-body']//div[contains(@class,'ant-row ant-form-item-row')]")).size()==3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-alert-description")).getText(), "By default tier will be created under Newly Created tier list. Activate the tier manually from the Newly Created list.");
		Assert.assertTrue(driver.findElements(By.xpath("//div[@class='ant-drawer-title']/following::button")).stream().allMatch(d->d.isEnabled()),"Buttons are not enabled");
		
		
		List<String> drawerFileds = Arrays.asList("Tier Name","Parent Tier","Tier Type","Tier Type");
		List<String> actualdrawerFileds = driver.findElements(By.xpath("//div[@class='ant-drawer-title']/following::span[contains(text(),'Tier')]")).stream().map(d->d.getText()).collect(Collectors.toList());
		actualdrawerFileds.forEach(System.out::println);
		Assert.assertEquals(actualdrawerFileds, drawerFileds);
		
		} finally {
			waitForElementToBeClickableAndClick(By.cssSelector("button.ant-drawer-close"));
		}	
	}
	
	
	public void hierarchyTierThreeDotPage() {
		
		waitForElementToBeClickableAndClick(By.id("menuList_hierarchy"));
		wait(3000);
		fluentVisibilityOfElement(driver.findElement(By.xpath("(//span[contains(@id,'hierarchy_row_actions')])[1]")));
		moveToElement( driver.findElement(By.xpath("(//span[contains(@id,'hierarchy_row_actions')])[1]")));
		wait(1000);
		click( driver.findElement(By.xpath("(//span[contains(@id,'hierarchy_row_actions')])[1]")));
		wait(2000);
		}
	
}
