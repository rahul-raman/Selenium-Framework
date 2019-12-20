package com.factory.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

/**
 * Contains options present on to order women product Note: There could have
 * many other functions to verify the current page but implementation has been
 * done keeping in mind the Hello Fresh Challenge Questions and its validations
 */

public class WomenStorePage extends BasePage {
	public WomenStorePage(WebDriver driver) {
		super(driver);
		
		try (InputStream input = new FileInputStream("src/main/resources/config.properties"))
        {
            prop = new Properties();
            prop.load(input);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
	}

	public static final String DATASEEDING_DATA_FILENAME = "WomenStorePage";

	@FindBy(how = How.LINK_TEXT, using = "Women")
	WebElement goToWomenSelection;

	@FindBy(how = How.XPATH, using = "//a[@title='Faded Short Sleeve T-shirts']/ancestor::li")
	WebElement fadedShortSleeve;

	@FindBy(how = How.XPATH, using = "//a[@title='Blouse']/ancestor::li")
	WebElement blouse;

	@FindBy(how = How.ID, using = "group_1")
	WebElement size;

	@FindBy(how = How.NAME, using = "Orange")
	WebElement orangeColor;

	@FindBy(how = How.NAME, using = "Blue")
	WebElement blueColor;

	@FindBy(how = How.NAME, using = "White")
	WebElement whiteColor;

	@FindBy(how = How.NAME, using = "Black")
	WebElement blackColor;

	@FindBy(how = How.NAME, using = "Submit")
	WebElement clickOnSubmit;

	@FindBy(how = How.XPATH, using = "//*[@id='layer_cart']//a[@class and @title='Proceed to checkout']")
	WebElement checkoutButton;

	@FindBy(how = How.XPATH, using = "//*[contains(@class,'cart_navigation')]/a[@title='Proceed to checkout']")
	WebElement proceedCheckoutButton;

	@FindBy(how = How.NAME, using = "processAddress")
	WebElement btn_process_address;

	@FindBy(how = How.ID, using = "uniform-cgv")
	WebElement rbn_accept_term;

	@FindBy(how = How.NAME, using = "processCarrier")
	WebElement btn_process_carrier;

	@FindBy(how = How.CLASS_NAME, using = "bankwire")
	WebElement payByBankwire;

	@FindBy(how = How.XPATH, using = "//*[@id='cart_navigation']/button")
	WebElement btn_confirm_order;

	// verification

	@FindBy(how = How.CSS, using = "h1")
	WebElement orderconfirmationheader;

	@FindBy(how = How.XPATH, using = "//li[@class='step_done step_done_last four']")
	WebElement shippingIsDisplayed;

	@FindBy(how = How.XPATH, using = "//li[@id='step_end' and @class='step_current last']")
	WebElement paymentIsDisplayed;

	@FindBy(how = How.XPATH, using = "//*[@class='cheque-indent']/strong")
	WebElement orderCompletedCheck;

	protected static Properties prop;

	public void clickonWomenoption() {
		wait.until(ExpectedConditions.visibilityOf(goToWomenSelection));
		goToWomenSelection.click();
	}

	public void clickonDressOption(String dressName) {

		if (fadedShortSleeve.getText().contains(dressName)) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", fadedShortSleeve);
			String browser = prop.getProperty("browser");
			if (browser.equalsIgnoreCase("chrome")) {
				wait.until(ExpectedConditions.elementToBeClickable(fadedShortSleeve)).click();
				wait.until(ExpectedConditions.elementToBeClickable(fadedShortSleeve)).click();
			} else if (browser.equalsIgnoreCase("firefox")) {
				wait.until(ExpectedConditions.elementToBeClickable(fadedShortSleeve)).click();
			}
		}

		else if (blouse.getText().contains(dressName)) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", blouse);
			wait.until(ExpectedConditions.visibilityOf(blouse));
			blouse.click();
		}

		else {
			Reporter.log("Check Dress Options");
		}

	}

	public void selectSize(String sizeDetails) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", size);
		Select select = new Select(size);
		select.selectByVisibleText(sizeDetails);
	}

	public void selectColor(String color) {
		if (color.equalsIgnoreCase("orange")) {
			orangeColor.click();
		}

		else if (color.equalsIgnoreCase("blue")) {
			blueColor.click();
		}

		else if (color.equalsIgnoreCase("white")) {
			whiteColor.click();
		}

		else if (color.equalsIgnoreCase("black")) {
			blackColor.click();
		}

		else {
			Reporter.log("Choose Correct color");
		}
	}

	public void click_on_Submit() {
		wait.until(ExpectedConditions.visibilityOf(clickOnSubmit));
		clickOnSubmit.click();
	}

	public void clickoncheckOutButton() {
		wait.until(ExpectedConditions.visibilityOf(checkoutButton));
		checkoutButton.click();
	}

	public void clickonproceedCheckoutButton() {
		wait.until(ExpectedConditions.visibilityOf(proceedCheckoutButton));
		proceedCheckoutButton.click();
	}

	public void clickonbtn_process_address() {
		wait.until(ExpectedConditions.visibilityOf(btn_process_address));
		btn_process_address.click();
	}

	public void clickonrbn_accept_term() {
		wait.until(ExpectedConditions.visibilityOf(rbn_accept_term));
		rbn_accept_term.click();
	}

	public void clickonbtn_process_carrier() {
		wait.until(ExpectedConditions.visibilityOf(btn_process_carrier));
		btn_process_carrier.click();
	}

	public void clickonpayByBankwire() {
		wait.until(ExpectedConditions.visibilityOf(payByBankwire));
		payByBankwire.click();
	}

	public void clickonbtn_confirm_order() {
		wait.until(ExpectedConditions.visibilityOf(btn_confirm_order));
		btn_confirm_order.click();
	}

	public String confirmHeader() {
		return orderconfirmationheader.getText();
	}

	public boolean confirmShippingIsDisplayed() {
		return shippingIsDisplayed.isDisplayed();
	}

	public boolean confirmpaymentIsDisplayed() {
		return paymentIsDisplayed.isDisplayed();
	}

	public String confirmOrderIsComplete() {
		return orderCompletedCheck.getText();
	}

}
