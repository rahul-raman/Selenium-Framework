package com.factory.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage
{

    public LoginPage(WebDriver driver)
    {
        super(driver);
    }

    public static final String DATASEEDING_DATA_FILENAME = "LoginPage";
    @FindBy(how = How.CLASS_NAME, using = "login")
    WebElement signInButton;

    @FindBy(how = How.ID, using = "email")
    @CacheLookup
    WebElement enterexistingemail;

    @FindBy(how = How.ID, using = "passwd")
    @CacheLookup
    WebElement enterexistingpassword;

    @FindBy(how = How.ID, using = "SubmitLogin")
    @CacheLookup
    WebElement clickonLogin;

    @FindBy(how = How.CSS, using = "h1")
    WebElement headerverification;

    @FindBy(how = How.CLASS_NAME, using = "account")
    public WebElement usernameverfication;

    public void click_on_SignIn()
    {
        try
        {
            if (signInButton.isDisplayed())
            {
                signInButton.click();
            }
        }
        catch (NoSuchElementException ex)
        {

        }
    }

    public void enterexistingemail(String validemail)
    {
        try
        {
            enterexistingemail.sendKeys(validemail);
        }
        
        catch(NoSuchElementException e)
        {
            wait.until(ExpectedConditions.visibilityOf(enterexistingemail));
            enterexistingemail.sendKeys(validemail);
          
        }
        
    }

    public void enterexistingpassword(String validpassword)
    {
        enterexistingpassword.sendKeys(validpassword);
    }

    public void logintoSite()
    {
        clickonLogin.click();
    }

}
