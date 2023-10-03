package driversAndLibrary;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.base.Function;

import constant.LoginData;

public class WebDriverImpl {

	public static WebDriver driver;
	public static WebDriverWait webdriverWait;
	public static Wait<WebDriver> fluentwait;
	public static int MAX_WAIT= 30,MAX_LIMIT=5;
	public static JavascriptExecutor js;
	public static Actions action;
	public static Robot robot;
	public static List<String> arrayList=new ArrayList<>();
	protected LoginData loginData=new LoginData();

	//	 browseropen
	public static WebDriver launchBrowser(String browsername) throws Exception {

		try {

			if (browsername.equalsIgnoreCase("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-notifications");
				options.addArguments("--disable-popupblocking");
				options.addArguments("--incognito");
				options.addArguments("--disable-cookies");
				options.addArguments("--disable-cache");
				options.setAcceptInsecureCerts(true);
				
				driver = new ChromeDriver(options);
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
			}  
			else if (browsername.equalsIgnoreCase("firefox")) {
				FirefoxOptions options = new FirefoxOptions();
				options.addPreference("dom.webnotifications.enabled",false);
				options.addArguments("--disable-notifications");
				options.addArguments("--disable-popupblocking");
				options.addArguments("--incognito");
				options.addArguments("--disable-cookies");
				options.addArguments("--disable-cache");
			
				options.setAcceptInsecureCerts(true);
				driver = new FirefoxDriver(options);
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
			} 
			else if (browsername.equalsIgnoreCase("edge")) {
				EdgeOptions options = new EdgeOptions();
				options.addArguments("--disable-notifications");
				options.setAcceptInsecureCerts(true);
				driver = new EdgeDriver(options);
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
			}
			else if (browsername.equalsIgnoreCase("ie")) {
				InternetExplorerOptions  options = new InternetExplorerOptions();
				options.setAcceptInsecureCerts(true);
				driver = new InternetExplorerDriver(options);
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
			}
			else if(browsername.equalsIgnoreCase("safari")) {
				driver = new SafariDriver();
				driver.manage().window().maximize();
			}
			else {
				System.out.println("Invalid browsername");
			}}
		catch (TimeoutException e) {
			e.printStackTrace();
		}
		System.out.println("Current OS" +System.getProperty("os.name"));
		implicitlyWait(10);

		return driver;

	}


	// applaunch
	public static void appLaunch(String url) {
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	//Thread sleep
	public static void wait(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//keypressenter
	public static void keyPressEnter() throws AWTException {
		robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	// browserquit
	public static void browserQuit() {
		driver.quit();
	}

	// alert_accept
	public static void alertAccept() {
		Alert clickaccept = driver.switchTo().alert();
		clickaccept.accept();
	}

	// alert_dismiss
	public static void alertDismiss() {
		Alert clickdismiss = driver.switchTo().alert();
		clickdismiss.dismiss();
	}

	// alert_Writeaccept
	public static void alertWriteAccept(String dialouge) {
		Alert write = driver.switchTo().alert();
		write.sendKeys(dialouge);
		write.accept();
	}

	// singleframe
	public static void singleFrame(String namevalue) {
		driver.switchTo().frame(namevalue);
	}
	
	public static void singleFrame(WebElement namevalue) {
		driver.switchTo().frame(namevalue);
	}

	public static void jsClick(WebElement element) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	// scrollingup
	public static void scrollingUp(WebElement element) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);

	}

	public static void scrollingDown(WebElement element) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(false);", element);
	}

	public static void scrollDownBottomPage() {
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

	}
	//scroll till find the element
	public static void scrollDownVisible(WebElement Element)
	{
		js = (JavascriptExecutor) driver;
		System.out.println("print"+Element);
		js.executeScript("arguments[0].scrollIntoView();", Element);
	}	


	// scrollingup
	public static void scrollingUp(By locator) {
		WebElement element = findElement(locator);
		scrollingUp(element);
	}

	public static void scrollDownUsingYaxis(int y) {
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scroll(0,"+y+")");
	}
	// dropdown
	public static void dropDown(WebElement element, String option, String value) {
		Select s = new Select(element);
		try {
			if (option.equals("listindex")) {
				s.selectByIndex(Integer.parseInt(value));
			} else if (option.equals("value")) {
				s.selectByValue(value);
			} else if (option.equals("text")) {
				s.selectByVisibleText(value);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static WebElement findElement(By locator) {
		int counter = 0;
		boolean isFound = false;
		WebElement element = null;
		while (counter < MAX_LIMIT && !isFound) {
			try {
				element = driver.findElement(locator);
				if (!element.isDisplayed() || !element.isEnabled()) {
					throw new ElementNotInteractableException("Element not visible. Retry...");
				}
				break;

			}catch (Exception e) {
				System.out.println("Retrying find element, till now tried > " + counter + "< times. ");
			}
			counter++;
		}
		return element;
	}

	// inputonelement
	public static void input(WebElement element, String value) throws Exception {
		keyPressDelete(element,"a");
		element.sendKeys(value);
	}

	public static void inputWithoutClearing(WebElement element, String value) {
		element.sendKeys(value);
	}

	public static void input(By locator, String value) throws Exception {
		WebElement element = findElement(locator);
		keyPressDelete(element,"a");
		input(element, value);
	}

	// click on Element
	public static void click(WebElement element) {
		try {
			waitForVisibilityOfElement(element);
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	// click on Element with Scroll
	public static void clickWithScroll(WebElement element, boolean doScroll) {
		if (doScroll) {
			scrollingUp(element);
		}
		click(element);
	}

	//Click on locator
	public static void click(By locator) {
		WebElement element = findElement(locator);
		click(element);
	}

	//Click on locator with Scroll
	public static void clickWithScroll(By locator, boolean doScroll) {
		WebElement element = findElement(locator);
		if (doScroll) {
			scrollingUp(element);
		}
		click(element);
	}

	// clearElement
	public static void clearElement(WebElement element) {
		element.clear();
	}

	// getcurrenturl
	public static String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	// getTitle
	public static String getTitle() {
		String title = null;
		try {
			title = driver.getTitle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return title;
	}

	// getText
	public static void getText(WebElement element) {
		System.out.println(element.getText());
	}

	// radiobutton
	public static void radioButton(WebElement element) {
		element.click();
	}

	// getoptions
	public static void getOptions(WebElement element) {
		Select y = new Select(element);
		List<WebElement> options = y.getOptions();
		for (WebElement y1 : options) {
			System.out.println(y1.getText());
		}
	}

	public static void keyPressDelete(WebElement element, String value) throws AWTException{

		if (System.getProperty("os.name").contains("Mac")) {
			element.sendKeys(Keys.COMMAND, value);  
			element.sendKeys(Keys.DELETE);

		} else {
			element.sendKeys(Keys.CONTROL, value); 
			element.sendKeys(Keys.DELETE);
		}


	} 

	// firstSelectedOp
	public static void firstSelectedOp(WebElement element) {
		Select y = new Select(element);
		WebElement firstSelectedOption = y.getFirstSelectedOption();
		System.out.println(firstSelectedOption.getText());
	}

	public static void back() {
		driver.navigate().back();
	}

	// enabledElement
	public static void enabledElement(WebElement check) {
		try {
			boolean enabled = check.isEnabled();
			System.out.println(check + "isEnabled:" + enabled);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// isSelected
	public static void isSelected(WebElement check) {
		try {
			boolean selected = check.isSelected();
			System.out.println(check + "isSelected:" + selected);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// isDisplayed
	public static void isDisplayed(WebElement Check) {
		boolean displayed = Check.isDisplayed();
		System.out.println(Check + "isDisplayed:" + displayed);
	}

	// navigateto
	public static void navigateTo(String url) {
		driver.navigate().to(url);
	}

	// navigateback
	public static void navigateBack(String url) {
		driver.navigate().back();
	}

	// navigateforward
	public static void navigateForward() {
		driver.navigate().forward();
	}

	// refreshpage
	public static void refreshPage() {
		driver.navigate().refresh();
	}

	// Movetoelement
	public static void moveToElement(WebElement element) {
		scrollingDown(element);
		action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	public static void sliding(WebElement element) {
		action = new Actions(driver);
		action.clickAndHold(element);
		action.moveByOffset(50,50);
		action.build().perform();
	}

	public static void sliding(WebElement element,int Xoffset, int Yoffest ) {
		action = new Actions(driver);
		action.clickAndHold(element);
		action.moveByOffset(Xoffset,Yoffest);
		action.build().perform();
	}

	// rightclick
	public static void rightClick(WebElement element) {
		action = new Actions(driver);
		action.contextClick(element).build().perform();
	}

	// doubleclick
	public static void doubleClick(WebElement element) {
		action = new Actions(driver);
		action.doubleClick(element).build().perform();
	}

	// clickk
	public static void mouseActionClick(WebElement elemet) {
		action = new Actions(driver);
		action.click().build().perform();
	}

	// sendkeysAction
	public static void sendKeysAction(WebElement element, String value) {
		action = new Actions(driver);
		action.sendKeys(element, value).build().perform();
	}

	// keypressDown
	public static void keyPressDown() throws AWTException {
		robot= new Robot();
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
	}

	// keypressDown
	public static void keyPressEnd() throws AWTException {
		robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_DOWN);
	}

	// keyreleaseUp
	public static void keyReleaseUp() throws AWTException {
		robot= new Robot();
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_UP);
	}

	// dragdrop
	public static void dragDrop(WebElement element1, WebElement element2) {
		action = new Actions(driver);
		action.dragAndDrop(element1, element2).build().perform();
	}

	public static void dragDropBy(WebElement element1, int x, int y) {
		action = new Actions(driver);
		action.dragAndDropBy(element1, x, y).build().perform();
	}

	public static void waitForVisibilityOfElements(List<WebElement>  element) {
		try {
			WebDriverWait webdriverWait = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT));
			webdriverWait.until(ExpectedConditions.visibilityOf( (WebElement) element));
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}


	// waitforvisibilityofelement
	public static void waitForVisibilityOfElement(WebElement element) {
		try {
			WebDriverWait webdriverWait = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT));
			webdriverWait.until(ExpectedConditions.visibilityOf(element));
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	// waitforinvisibilityofelement
	public static void waitForInvisibilityOfElement(WebElement element) {
		try {
			WebDriverWait webdriverWait = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT));
			webdriverWait.until(ExpectedConditions.invisibilityOf(element));

		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	public static void waitForVisibilityOfList(List<WebElement> element) {
		try {
			WebDriverWait webdriverWait = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT));
			webdriverWait.until(ExpectedConditions.visibilityOfAllElements(element));

		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	// implicitlywait
	public static void implicitlyWait(int time) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));

	}

	// getAttribute
	public static void getAttribute(WebElement element) {
		String attribute = element.getAttribute("value");
		System.out.println(attribute);
	}

	public static void closee() {
		driver.close();
	}

	public static void newTab() {
		js = (JavascriptExecutor) driver;
		js.executeScript("window.open('about:blank', '_blank');");
	}


	public static void navigateTab(int num) {
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(num));
	}

	public static void navigateTabMac(int num) {
		((JavascriptExecutor)driver).executeScript("window.open()");
	    ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(num));
	}
	
	public void switchToWindow() {
	       String curWinHandle = driver.getWindowHandle();
	        Set<String> availableWindows = driver.getWindowHandles();
	        for (String strWinHandle : availableWindows) {
	            if(!curWinHandle.equals(strWinHandle))
	               driver.switchTo().window(strWinHandle);
	        }
	    }
	


	public static void safeJavaScriptClick(WebElement element) throws Exception {

		if (element.isEnabled() && element.isDisplayed()) {
			System.out.println("Clicking on element with using java script click");
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		} else {
			System.out.println("Unable to click on element");
		}
	}
	
	public static void safeJavaScriptClear(WebElement element) throws Exception {

		if (element.isEnabled() && element.isDisplayed()) {
			System.out.println("Clicking on element with using java script clear");
			((JavascriptExecutor) driver).executeScript("arguments[0].value ='';", element);
		} else {
			System.out.println("Unable to clear on element");
		}
	}



	// waitforElementToBeClickableAndThenClick
	public static void waitForElementToBeClickableAndClick(WebElement element) {
		try {
			WebDriverWait webdriverWait = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT));
			webdriverWait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	public static void waitForElementToBeClickableAndClick(By locator) {
		try {
			WebDriverWait webdriverWait = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT));
			webdriverWait.until(ExpectedConditions.elementToBeClickable(locator));
			click(locator);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void waitForElementToBeClickable(WebElement element) {
		try {
			WebDriverWait webdriverWait = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT));
			webdriverWait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	public static void waitForElementToBeClickable(By locator) {
		try {
			WebDriverWait webdriverWait = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT));
			webdriverWait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	public static void fluentWaitWithoutCondition() {
		fluentwait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(20L))
				.pollingEvery(Duration.ofMillis(250L))
				.ignoring(Exception.class);
	}


	public static void fluentWaitClick(WebElement element) {
		fluentWaitWithoutCondition();
		WebElement element1 = fluentwait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return element;
			}
		});
		element1.click();
	}

	public static void fluentWaitMoveClick(WebElement element) {
		fluentWaitWithoutCondition();
		fluentwait.until(ExpectedConditions.visibilityOf(element));
		moveToElement(element);
		fluentwait.until(ExpectedConditions.elementToBeClickable(element));
		action.click(element).build().perform();
	}


	public static WebElement fluentVisibilityOfElement(WebElement element) {
		fluentWaitWithoutCondition();
		fluentwait.until(ExpectedConditions.visibilityOf(element));
		return element;
	}


	//vig


	public static void fluentWaitJsClick(WebElement element) {
		fluentWaitWithoutCondition();
		fluentwait.until(ExpectedConditions.visibilityOf(element));
		fluentwait.until(ExpectedConditions.elementToBeClickable(element));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

	}


	public static WebElement fluentInvisibilityOfElement(WebElement element) {
		fluentWaitWithoutCondition();
		fluentwait.until(ExpectedConditions.invisibilityOf(element));
		return element;
	}

	public static List<WebElement> fluentVisibilityOfElements(List<WebElement> elements) {
		fluentWaitWithoutCondition();
		fluentwait.until(ExpectedConditions.visibilityOfAllElements(elements));
		return elements;
	}

	public static List<WebElement> fluentInvisibilityOfElements(List<WebElement> elements) {
		fluentWaitWithoutCondition();
		fluentwait.until(ExpectedConditions.invisibilityOfAllElements(elements));
		return elements;
	}

	public static void scrollBottom() {
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public static void fluentWaitMove(WebElement element) throws InterruptedException {
		fluentWaitWithoutCondition();
		fluentwait.until(ExpectedConditions.visibilityOf(element));
		moveToElement(element);
	}

	public static void fluentScrollIntoElement(WebElement element) throws InterruptedException {
		fluentWaitWithoutCondition();
		fluentwait.until(ExpectedConditions.visibilityOf(element));
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void fluentWaitDoubleClick(WebElement element) {
		fluentWaitWithoutCondition();
		fluentwait.until(ExpectedConditions.visibilityOf(element));
		fluentwait.until(ExpectedConditions.elementToBeClickable(element));
		doubleClick(element);
	}

	public static void fluentSendKeys(WebElement element, String value) {
		fluentWaitWithoutCondition();
		fluentwait.until(ExpectedConditions.visibilityOf(element));
		sendKeysAction(element, value);
	}

	public static void dropdownScrollJS(String cssAttribute, int scrollHeight) {
		wait(2000);
		js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelector('"+cssAttribute +"').scrollTop="+scrollHeight+";");
	}

	public static void dropdownScrollByJS(String cssAttribute, int scrollHeight) {
		wait(2000);
		js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelector('"+cssAttribute +"').scrollBy(0,"+scrollHeight+");");
	}

	public void listingEditLocation(String editInformation) {

		try {
			waitForElementToBeClickableAndClick(By.xpath("//button[contains(@id,'listings_location_targetSites_overviewCard_actionButtons_edit')]"));
			wait(2000);
			fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
			Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText(), editInformation);
			if (editInformation.equals("Edit Location Profile Information")) {
				List<String> arrayList = driver.findElements(By.xpath("//span[@class='ant-collapse-header-text']//span")).stream().map(d->d.getText()).collect(Collectors.toList());
				List<String> listOfDays = Arrays.asList("Business Information", "Business Hours","Address","Change Address", "Contact Information", "Social Media Links", "Images", "Awards", "Products & Services Offered", "Additional Information");
				Assert.assertEquals(arrayList, listOfDays);
			} else {
				List<String> arrayList = driver.findElements(By.xpath("//span[@class='ant-collapse-header-text']//span")).stream().map(d->d.getText()).collect(Collectors.toList());
				List<String> listOfDays = Arrays.asList("Business Information", "Business Hours","Address","Add Address", "Contact Information", "Social Media Links", "Images", "Awards","Position", "Products & Services Offered","Memberships","Specialities","Achievements","Hobbies","Licenses","Additional Information");
				arrayList.forEach(System.out::println);
				Assert.assertEquals(arrayList, listOfDays);
			}

		} finally {
			waitForElementToBeClickableAndClick(By.id("profile_button_cancelBtn_editProfile"));
		}
	}


	public void listingPublishLocation() {

		try {
			waitForElementToBeClickableAndClick(By.id("listings_location_targetSites_overviewCard_actionButtons_publish"));
			fluentVisibilityOfElement(driver.findElement(By.cssSelector("div.ant-drawer-title")));
			Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-drawer-title")).getText().trim(), "Publish To Target Sites");
			List<String> arrayList = driver.findElements(By.xpath("//span[contains(@class,'ant-checkbox ')]/input[@type='checkbox']")).stream().map(d->d.getAttribute("value")).collect(Collectors.toList());
			List<String> listOfDays = Arrays.asList("gmb","facebook","allOther");
			Assert.assertEquals(arrayList, listOfDays);
			System.out.println(arrayList);

		} finally {
			waitForElementToBeClickableAndClick(By.cssSelector("button.ant-drawer-close"));
		}
	}

	public void clickFilterAndConnectionValidation() throws Exception {
		click(By.id("admin_button_filter"));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-checkbox-group']")).isDisplayed(), "publish status is displayed");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ant-row ant-form-item-row']")).isDisplayed(), "search field card is displayed");

//		String pagecount=driver.findElement(By.xpath("//li[@title='Next Page']/preceding::li[1]")).getAttribute("title");
//		int paginationtotalcount=Integer.parseInt(pagecount);
//		for (int i = 1; i <=paginationtotalcount; i++) {
//			scrollDownBottomPage();
//			safeJavaScriptClick(driver.findElement(By.xpath("//li[@title="+i+"]")));
//
//			int size = driver.findElements(By.xpath("//div[contains(@id,'listings_location_targetSites_table')]/div[2]/span")).size();
//			for (int j = 0; j < size ; j++){
//
//				String connectionstatus = driver.findElements(By.xpath("//div[contains(@id,'listings_location_targetSites_table')]/div[2]/span")).get(j).getText();
//				String[] nameArray = new String[]{"Published","Synced","Failed","Unpublished"};
//				List<String> PublishedsitesText = new ArrayList<>(Arrays.asList(nameArray));
//				Assert.assertTrue(PublishedsitesText.contains(connectionstatus));
//			}
//		}
	}


	public void listingsPublishStatus(String idName) {
	
		try {
		waitForElementToBeClickableAndClick(driver.findElement(By.id(idName)));
		wait(5000);
		if (idName.equals("listing_publishedChart_locationChart_authorizeRight_arrow")) {
			waitForElementToBeClickableAndClick(By.id("listing_users_button"));
			wait(1000);
			waitForElementToBeClickableAndClick(By.id("listing_location_button"));
			List<String> expectedTableHeader = Arrays.asList("Location Name","Tier Name","Targets","Status","Action");
			List<String> actualTableHeader = driver.findElements(By.xpath("//th[@class='ant-table-cell ant-table-cell-ellipsis' or @class='ant-table-cell']")).stream().map(d->d.getText()).collect(Collectors.toList());
			Assert.assertEquals(actualTableHeader,expectedTableHeader);
		} else {
			waitForElementToBeClickableAndClick(By.id("listing_location_button"));
			waitForElementToBeClickableAndClick(By.id("listing_users_button"));
			List<String> expectedTableHeader = Arrays.asList("Name","Publish Name","Targets","Status","Action");
			List<String> actualTableHeader = driver.findElements(By.xpath("//th[@class='ant-table-cell ant-table-cell-ellipsis' or @class='ant-table-cell']")).stream().map(d->d.getText()).collect(Collectors.toList());
			Assert.assertEquals(actualTableHeader,expectedTableHeader);
		}
		fluentVisibilityOfElement(driver.findElement(By.id("PublishStatus_nav_label")));
		Assert.assertEquals(driver.findElement(By.id("PublishStatus_nav_label")).getText(), "Publish Status");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/listing/targetsites");
		Assert.assertTrue(driver.findElement(By.id("listings_button_group")).isDisplayed(), "location buttons is not displayed");
		click(driver.findElement(By.id("admin_button_filter")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ant-card-head-title span")).getText(), "Search & Filter");
		Assert.assertTrue(driver.findElements(By.xpath("//form[@id='target_site_filter']//following::div[@class='ant-form-item-control-input-content']")).stream().allMatch(m->m.isDisplayed()), "Filter fields are not displayed");
		Assert.assertTrue(driver.findElement(By.id("pagination_div_footer")).isDisplayed(), "pagination is not displayed");
		} 
		finally {
			back();
		}
		
	}
	
	public void hierarchyLandingPage() {
		
		wait(3000);
		fluentVisibilityOfElement(driver.findElement(By.id("Hierarchy_nav_label")));
		Assert.assertEquals(driver.findElement(By.id("Hierarchy_nav_label")).getText(), "Hierarchy");
		Assert.assertEquals(getCurrentUrl(), "https://app"+loginData.getENV()+".experience.com/user/account/hierarchy");
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("hierarchy_region_count")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("hierarchy_branch_count")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("hierarchy_user_count")).getText())>0);
		Assert.assertTrue(Integer.parseInt(driver.findElement(By.id("hierarchy_publishedProfile_count")).getText())>0);
		Assert.assertTrue(driver.findElement(By.id("hierarchy_tabs")).isDisplayed());
	}
	
	public void footerValidation() {
		scrollDownBottomPage();
		
		Assert.assertEquals(driver.findElement(By.xpath("//footer/div[1]")).getText().trim(),"© 2015 - 2023 Experience.com created by BuyersRoad, Inc. All rights reserved.");
		Assert.assertEquals(driver.findElement(By.xpath("//footer/div[2]")).getText().trim(),"Privacy Policy|Accessibility Statement");
		Assert.assertEquals(driver.findElement(By.xpath("//footer/div[1]/a")).getAttribute("href").trim(),"https://www.experience.com/");
		Assert.assertEquals(driver.findElement(By.xpath("//footer/div[2]/text[1]/a")).getAttribute("href").trim(),"https://www.experience.com/privacy-policy/");
		Assert.assertEquals(driver.findElement(By.xpath("//footer/div[2]/text[2]/a")).getAttribute("href").trim(),"https://www.experience.com/accessibility-statement/");
			waitForElementToBeClickableAndClick(By.cssSelector("div.copyright>a"));
		try {
			switchToWindow();
			wait(3000);
			Assert.assertEquals(getCurrentUrl(), "https://www.experience.com/");
			Assert.assertTrue(driver.findElement(By.cssSelector("#content>div")).isDisplayed(), "Experience page is not displayed");
			Assert.assertTrue(driver.findElement(By.cssSelector("a.header-logo")).isDisplayed(), "Experience logo is not displayed");
		} finally  {
			closee();
		}
		navigateTab(0);
		waitForElementToBeClickableAndClick(By.xpath("(//div[@class='privacy-policy']//a)[1]"));
		try {
			
			switchToWindow();
			wait(3000);
			Assert.assertEquals(getCurrentUrl(), "https://www.experience.com/privacy-policy/");
			Assert.assertTrue(driver.findElement(By.cssSelector("#content>div")).isDisplayed(), "Experience page is not displayed");
			Assert.assertTrue(driver.findElement(By.cssSelector("a.header-logo")).isDisplayed(), "Experience logo is not displayed");
			Assert.assertEquals(driver.findElement(By.cssSelector(".breadcrumb_last")).getText(), "Privacy Policy");
			
		} finally  {
			closee();
		}
		
		navigateTab(0);
		waitForElementToBeClickableAndClick(By.xpath("(//div[@class='privacy-policy']//a)[2]"));
		try {
			switchToWindow();
			wait(3000);
			Assert.assertEquals(getCurrentUrl(), "https://www.experience.com/accessibility-statement/");
			Assert.assertTrue(driver.findElement(By.cssSelector("#content>div")).isDisplayed(), "Experience page is not displayed");
			Assert.assertTrue(driver.findElement(By.cssSelector("a.header-logo")).isDisplayed(), "Experience logo is not displayed");
			
		} finally  {
			closee();
		}
		navigateTab(0);

	}

	
	//sangeetha

	public static void flagReview() {
		try {
		waitForVisibilityOfElement(driver.findElement(By.xpath("(//div[contains(@id,'reviews_card_wrapper')]//button[contains(@id,'flag')])[1]")));
		click(driver.findElement(By.xpath("(//div[contains(@id,'reviews_card_wrapper')]//button[contains(@id,'flag')])[1]")));
		waitForVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-header-title']//div[text()]")));
		Assert.assertTrue(driver.findElements(By.xpath("//div[contains(@id,'report_choice')]")).stream().allMatch(m->m.isDisplayed()),"flag review content list displayed");
		}
		finally {
		click(driver.findElement(By.id("report_drawer_close")));
		}
	}
	
	public static void shareReview() {
		try {
		waitForVisibilityOfElement(driver.findElement(By.xpath("(//div[contains(@id,'reviews_card_wrapper')]//button[contains(@id,'share_review')])[1]")));
		click(driver.findElement(By.xpath("(//div[contains(@id,'reviews_card_wrapper')]//button[contains(@id,'share_review')])[1]")));
		waitForVisibilityOfElement(driver.findElement(By.xpath("//div[@class='ant-drawer-title']")));
		Assert.assertTrue(driver.findElements(By.xpath("//a[contains(@title,'Share it on')]")).stream().allMatch(m->m.isDisplayed()),"social media list displayed");
		}
		finally{
			click(driver.findElement(By.id("share_link_drawer_close")));
		}
	}
	
	public void verifyLandingPageForUserProfilePage() {
	String currentUrl = driver.getCurrentUrl();
	System.out.println(currentUrl + "ProfilePage  url");
	wait(3000);
	Assert.assertTrue(Double.parseDouble(driver.findElement(By.id("rating_profile_summary")).getText())>0, "Rating is equal to zero");		
	Assert.assertTrue(driver.findElement(By.xpath("(//img[@id='avatar_photo_undefined']//following::div[@data-test-publish-name='true'])[1]"))!=null, "User Name is not displayed");
	Assert.assertTrue(driver.findElement(By.xpath("(//img[@id='avatar_photo_undefined']//following::div[@data-test-title='true'])[1]"))!=null, "User title is not displayed");
	
	Assert.assertEquals(driver.findElement(By.id("nav_breadcumb_title")).getText(), "Automation account -Dont delete");
	Assert.assertTrue(driver.findElement(By.id("product_guide")).isDisplayed(), "Help Icon is not displayed");
	
	Assert.assertTrue(driver.findElement(By.id("profile_container_div")).isDisplayed(), "Login User Name with role  not displayed");
	Assert.assertTrue(driver.findElement(By.id("profile-link"))!=null, "Profile link is not displayed");
	driver.findElement(By.id("profile-link")).click();
	switchToWindow();
	
	System.out.println(driver.getCurrentUrl() + "Swiss Page URL");
	wait(3000);
	closee();
	navigateTab(0);
	
	Assert.assertTrue(driver.findElement(By.xpath("//button[@id='product_guide']//preceding::span[1]")).isDisplayed(), "Org name not is displayed");	
	Assert.assertTrue(driver.findElement(By.xpath("//div[@id='review_count_profile_summary']//following::div[1]")).getText()!=null, "Location details are  not displayed");
	
	}
	
	public void tierProfileTabs() {
		wait(3000);
	if(driver.findElements(By.xpath("//li[@data-test-tab='Users']")).size()==1) {
		if (driver.findElements(By.xpath("//li[@data-test-tab='Quick Actions']")).size()==1) {
			List<String> listOfTierProfilePageTabsWithActionTab = driver.findElements(By.xpath("//li[contains(@id,'tab')]")).stream().map(d->d.getAttribute("data-test-tab")).collect(Collectors.toList());
			System.out.println(listOfTierProfilePageTabsWithActionTab);		
			List<String> listOfTabsForTiers = Arrays.asList("Quick Actions","Reviews","Users" , "Rating", "About", "Contact");
			Assert.assertTrue(listOfTierProfilePageTabsWithActionTab.equals(listOfTabsForTiers));
	
		} else {
			List<String> listOfTierProfilePageTabs = driver.findElements(By.xpath("//li[contains(@id,'tab')]")).stream().map(d->d.getAttribute("data-test-tab")).collect(Collectors.toList());
				System.out.println(listOfTierProfilePageTabs);		
				List<String> listOfTabsTier2 = Arrays.asList("Reviews", "Users", "Rating", "About", "Contact");
				Assert.assertTrue(listOfTierProfilePageTabs.equals(listOfTabsTier2));		
		}
	}
	}
	
	public void userProfileTabs() {
		wait(3000);
			if(driver.findElements(By.xpath("//li[@data-test-tab='Quick Actions']")).size()==1) {
				List<String> userProfilePageTabsWithActionsTab = driver.findElements(By.xpath("//li[contains(@id,'tab')]")).stream().map(d->d.getAttribute("data-test-tab")).collect(Collectors.toList());
				System.out.println(userProfilePageTabsWithActionsTab);
				List<String> listOfTabsUsers = Arrays.asList("Quick Actions","Reviews", "Rating", "About", "Contact");
				Assert.assertTrue(userProfilePageTabsWithActionsTab.equals(listOfTabsUsers));
				
				Assert.assertTrue(driver.findElement(By.id("quick_actions_container")).isDisplayed(), "Complete your profile container displayed");
				List<String> listOfTextMessage = driver.findElements(By.xpath("//div[@id='quick_actions_container']//h3")).stream().map(d->d.getText()).collect(Collectors.toList());
				Assert.assertTrue(listOfTextMessage!=null);
				
				List<String> listOfText = driver.findElements(By.xpath("//div[@id='quick_actions_container']//h3")).stream().map(d->d.getText()).collect(Collectors.toList());
				Assert.assertTrue(listOfText!=null);
				
				}
			else {
				List<String> userProfilePageTabs = driver.findElements(By.xpath("//li[contains(@id,'tab')]")).stream().map(d->d.getAttribute("data-test-tab")).collect(Collectors.toList());
				System.out.println(userProfilePageTabs);
				List<String> listOfTabsAgent = Arrays.asList("Reviews", "Rating", "About", "Contact");
				Assert.assertTrue(userProfilePageTabs.equals(listOfTabsAgent));			
			}
	}

	public void verifyProfessionalsTab() throws Exception {
		wait(3000);
		List<String> listOfProfessionalLinks = driver.findElements(By.xpath("//div[@id='professionals_container']//div[contains(@id,'rating')]")).stream().map(d->d.getText()).collect(Collectors.toList());
		System.out.println(listOfProfessionalLinks);
		int reviewRating = driver.findElements(By.xpath("//div[text()='Top Rated Professionals']//following::div[contains(@id,'rating')]")).size();
		wait(5000);

		String profileReviewCountText = driver.findElement(By.id("review_count_profile_summary")).getText().replaceAll("[^a-zA-Z0-9_-]", "");
		Assert.assertTrue(Integer.parseInt(profileReviewCountText)>0);
		for(int i =0;i<reviewRating;i++)
		{
			String ratingText = driver.findElements(By.xpath("//div[text()='Top Rated Professionals']//following::div[contains(@id,'rating')]")).get(i).getText();
			Assert.assertTrue(Double.parseDouble(ratingText)>0);

			String reviewCountText = driver.findElements(By.xpath("//div[text()='Top Rated Professionals']//following::div[contains(@id,'review_count')]")).get(i).getText().replaceAll("[^a-zA-Z0-9_-]", "");
			Assert.assertTrue(Integer.parseInt(reviewCountText)>0);

			String nameText = driver.findElements(By.xpath("//div[contains(@id,'publish_name')]")).get(i).getText();
			Assert.assertTrue(nameText!=null);
		}

		Assert.assertTrue(driver.findElement(By.id("container_Find Nearby Professionals")).isDisplayed(), "find near by professional container is  not displayed");
		Assert.assertTrue(driver.findElement(By.id("container_Top Rated Professionals")).isDisplayed(), "top rated professional container is  not displayed");
		Assert.assertTrue(driver.findElement(By.id("container_Top Viewed Professionals")).isDisplayed(), "top viewed professional container is  not displayed");

		int listofProfessionalLinks = driver.findElements(By.xpath("//div[@id='professionals_container']//a[@id='profile-link']")).size();
		for(int i=0;i<listofProfessionalLinks;i++) {
			safeJavaScriptClick(driver.findElements(By.xpath("//div[@id='professionals_container']//a[@id='profile-link']")).get(i));
			try {
				switchToWindow();
				wait(5000);
				System.out.println(getCurrentUrl() + "Navigated to the Professionals page");
				closee();

			} finally {

				wait(3000);
				navigateTab(0);
			}
		}}
	
	public void verifyReviewsTab() {	
		click(By.xpath("//li[@data-test-tab='Reviews']"));
		Assert.assertTrue(driver.findElements(By.xpath("//div[contains(@id,'reviews_card_wrapper')]")).stream().allMatch(m->m.isDisplayed()),"Reviews list is not displayed");
				
		Assert.assertTrue(driver.findElement(By.id("profile_reviews_section_sort_button")).isEnabled(),"Reviews sort button is not enabled");
		Assert.assertTrue(driver.findElement(By.id("filter_button")).isDisplayed(),"filter button is not displayed");
		
		click(driver.findElement(By.id("filter_button")));
		
		waitForVisibilityOfElement(driver.findElement(By.xpath("(//input[@id='search_filter']//parent::div)[1]")));
		Assert.assertTrue(driver.findElement(By.xpath("(//div[text()='Rating']//parent::div)[1]")).isDisplayed(), "Rating card is not displayed");
		Assert.assertTrue(driver.findElement(By.id("review_filter_date")).isEnabled(), "Date filter card is not displayed");
		
		Assert.assertTrue(driver.findElement(By.id("reviews_filter_reset_button")).isDisplayed(), "rest filter button is not displayed");
		Assert.assertTrue(driver.findElement(By.id("reviews_filter_apply_button")).isDisplayed(), "apply filter button is  not displayed");
	}
		
		public static void tierReviewsFilterValidation() {
		
		List<WebElement> tierReviewsFilterList = driver.findElements(By.xpath("//div[@id='reviews_filter_section']//input[contains(@class,'ant')]"));
		Assert.assertTrue(tierReviewsFilterList.stream().allMatch(m->m.isEnabled()),"Reviews list are not displayed");
		List<String> actualReviewFilterList = tierReviewsFilterList.stream().map(m->m.getAttribute("type")).collect(Collectors.toList());
		
		List<String> expReviewFilterList = Arrays.asList("text","search", "search");		
		Assert.assertEquals(actualReviewFilterList, expReviewFilterList);
		}
		
		public static void userReviewsFilterValidation() {
			List<WebElement> userReviewsFilterList = driver.findElements(By.xpath("//div[@id='reviews_filter_section']//input[contains(@class,'ant')]"));
			Assert.assertTrue(userReviewsFilterList.stream().allMatch(m->m.isEnabled()),"Reviews list are not displayed");
			List<String> actualReviewFilterList = userReviewsFilterList.stream().map(m->m.getAttribute("type")).collect(Collectors.toList());
			
			List<String> expReviewFilterList = Arrays.asList("text","search");		
			Assert.assertEquals(actualReviewFilterList, expReviewFilterList);
			}		
	
	
	
public static void clickShareProfileIcon() {
		try {
	  Assert.assertTrue(driver.findElement(By.id("sharelink-button")).isDisplayed(), "Share button is not displayed");
		moveToElement(driver.findElement(By.id("sharelink-button")));
		driver.findElement(By.id("sharelink-button")).click();
		Assert.assertTrue(driver.findElements(By.xpath("//a[contains(@title,'Share it on')]")).stream().allMatch(m->m.isEnabled()),"social media list not displayed");
		}
		finally {
			click(By.id("share_link_drawer_close"));
		}
	}
	
	public static void clickEditProfileIcon() {
		try {
		wait(3000);
	Assert.assertTrue(driver.findElement(By.id("editprofile-button")).isDisplayed(), "Edit Profile button is not displayed");
	driver.findElement(By.id("editprofile-button")).click();
	
	Assert.assertTrue(driver.findElements(By.xpath("//input[contains(@class,'ant-input')]")).stream().allMatch(m->m.isDisplayed()),"all edit page fields are not displayed");
		}finally {
			moveToElement(driver.findElement(By.id("edit_profile_cancel")));
			click(By.id("edit_profile_cancel"));
		}
}
	
	public static void verifyRatingTab() {
		driver.findElement(By.xpath("//li[@data-test-tab='Rating']")).click();
		
		Assert.assertTrue(driver.findElements(By.xpath("//div[contains(@id,'card')]")).stream().allMatch(m->m.isDisplayed()),"review source cards list not  displayed");
					
		Assert.assertTrue(Double.parseDouble(driver.findElement(By.xpath("(//div[@id='total_exp_card']//div[text()])[1]")).getText())>0, "Review Count is less than zero");
				
		int reviewCountSize = driver.findElements(By.xpath("//div[contains(@id,'review_count_rating')]")).size();
		for(int i=0;i<reviewCountSize;i++) {
				
		 String ratingText = driver.findElements(By.xpath("//div[contains(@id,'rating_rating')]")).get(i).getText();
		 Assert.assertTrue(Double.parseDouble(ratingText)>0);
						 
		 String reviewCountText = driver.findElements(By.xpath("//div[contains(@id,'review_count_rating')]")).get(i).getText().replaceAll("[^a-zA-Z0-9_-]", "");
		 Assert.assertTrue(Double.parseDouble(reviewCountText)>0);
		}		
	}
	
	public static void verifyAboutSection() {
		driver.findElement(By.xpath("//li[@data-test-tab='About']")).click();
		Assert.assertTrue(driver.findElements(By.xpath("//div[@data-test-aboutcard-title]")).stream().allMatch(m->m.getText()!=null),"all about section fields are not displayed");
		Assert.assertTrue(driver.findElements(By.xpath("//div[@data-test-card-edit-btn]")).stream().allMatch(m->m.getText()!=null),"edit icon is not enabled for fields");
	}			
	
	public static void verifyContactSection() {
		wait(3000);
		
		driver.findElement(By.xpath("//li[@data-test-tab='Contact']")).click();
		Assert.assertTrue(driver.findElements(By.xpath("//div[@data-test-aboutcard-title]")).stream().allMatch(m->m.getText()!=null),"About section titles are not displayed");
		wait(3000);		
		driver.findElement(By.id("contact_editButton")).click();
		Assert.assertTrue(driver.findElements(By.xpath("//form[@id='contactform']//input")).stream().allMatch(m->m.getText()!=null),"all fields are not displayed with data");
		Assert.assertTrue(driver.findElement(By.id("add_service_area_button")).isDisplayed(), "Add service area button is not displayed");
		Assert.assertTrue(driver.findElements(By.id("google-map-marker")).stream().allMatch(m->m.getText()!=null),"Google Map is not displayed");
		driver.findElement(By.id("Profile-edit-cancel-button")).click();
					
		List<String> contactSectionListOfDays = driver.findElements(By.xpath("//div[@id='business_hours_content']//span[1]")).stream().map(d->d.getText()).collect(Collectors.toList());
		List<String> listOfDays = Arrays.asList("Monday","Tuesday", "Wednesday", "Thursday", "Friday","Saturday", "Sunday");
		Assert.assertEquals(contactSectionListOfDays, listOfDays);
					
		driver.findElement(By.id("business_hours_editButton")).click();
		Assert.assertTrue(driver.findElement(By.id("business_hours_wrapper")).isDisplayed(), "Business hours card is not displayed");
		driver.findElement(By.id("Profile-edit-cancel-button")).click();
	}
		
		//mani
	
	public  void navigationOfSocialSites() throws Exception  {
		try {
			wait(2000);
		List<WebElement> findElements = driver.findElements(By.xpath("//div[@class='ant-drawer-title']//following::a[@title]"));
		for (WebElement webElement : findElements) {
			safeJavaScriptClick(webElement);
			switchToWindow();
			wait(3000);
			System.out.println(driver.getCurrentUrl());
			wait(3000);
			Assert.assertTrue( driver.findElement(By.xpath("//input[@id='username' or @id='email' or @name='text']")).isDisplayed(),"Social Site Page is Displayed");
			closee();
			navigateTab(0);
		}
		}finally {
			wait(2000);
			safeJavaScriptClick(driver.findElement(By.id("share_link_drawer_close")));
		 }
		}
	public  void navigationOfSocialSitesAccountManager()  {
		
		
		
		List<WebElement> findElements = driver.findElements(By.xpath("//div[@id='reviewManagement_review_div_SocialSection_0']//child::img"));
		
		for (WebElement webElement : findElements) {
		webElement.click();
			wait(3000);
			switchToWindow();
			wait(3000);
			System.out.println(driver.getCurrentUrl());
			Assert.assertTrue( driver.findElement(By.xpath("//input[@id='username' or @id='email' or @name='text']")).isDisplayed(),"Social Site Page is Displayed");
			closee();
			navigateTab(0);
		    }
	}
	
	public WebDriver accountManagerLogin() throws Exception {
		driver = launchBrowser(loginData.getBROWSER_NAME());

		driver.get("https://app"+loginData.getENV()+".experience.com/user/signin");

		//fluentVisibilityOfElement(driver.findElement(By.id("UserSignInForm_input_email")));
		//input(By.id("UserSignInForm_input_email"), "autotester+002@experience.com");   //   autotester+12345@experience.com
		fluentVisibilityOfElement(driver.findElement(By.xpath("//input[@id='signup_mail' or @id='UserSignInForm_input_email']")));
		input(By.xpath("//input[@id='signup_mail' or @id='UserSignInForm_input_email']"), "autotester+002@experience.com");

//		fluentVisibilityOfElement(driver.findElement(By.id("UserSignInForm_input_password")));
//		input(By.id("UserSignInForm_input_password"), "password");
		fluentVisibilityOfElement(driver.findElement(By.xpath("//input[@id='signup_password' or @id='UserSignInForm_input_password']")));
		input(By.xpath("//input[@id='signup_password' or @id='UserSignInForm_input_password']"), "password");
		
//		fluentVisibilityOfElement(driver.findElement(By.id("UserSignInForm_input_submit")));
//		click(By.id("UserSignInForm_input_submit"));
		
		fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@type='submit']")));
		click(By.xpath("//button[@type='submit']"));
		return driver;
	}
	
	public WebDriver agentLogin() throws Exception {
		driver = launchBrowser(loginData.getBROWSER_NAME());

		driver.get("https://app"+loginData.getENV()+".experience.com/user/signin");
		fluentVisibilityOfElement(driver.findElement(By.xpath("//input[@id='signup_mail' or @id='UserSignInForm_input_email']")));
		input(By.xpath("//input[@id='signup_mail' or @id='UserSignInForm_input_email']"), "autotester+123@experience.com");
//		wait(2000);
		fluentVisibilityOfElement(driver.findElement(By.xpath("//input[@id='signup_password' or @id='UserSignInForm_input_password']")));
		input(By.xpath("//input[@id='signup_password' or @id='UserSignInForm_input_password']"), "password");

		fluentVisibilityOfElement(driver.findElement(By.xpath("//button[@type='submit']")));
		click(By.xpath("//button[@type='submit']"));

		return driver;
	}
	
	public static void defaultFrame() {
		driver.switchTo().defaultContent();
	}
	
	

}