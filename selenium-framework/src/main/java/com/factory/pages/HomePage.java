package com.factory.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class HomePage extends BasePage
{
    public HomePage(WebDriver driver)
    {
    	super(driver);
    }
    /* Contains options present on Home Page
     * 1. Sign In Button
     * 2. Header Verify
     * 3. Title Verify
     * Note: There could have many other functions to verify the current page
     *       but implementaion has been done keeping in mind the Hello Fresh Challenge
     *       Questions and its validations
     */
    
    @FindBy(how=How.CLASS_NAME,using="login")
    public WebElement signInButton;
    
    public void click_on_SignIn()
    {
        try {
            if(signInButton.isDisplayed())
            {
                signInButton.click();
            }
        }
            catch (NoSuchElementException ex) {
                

        }
    }
    
    
}
