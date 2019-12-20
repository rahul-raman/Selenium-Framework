package com.factory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class DashboardPage extends BasePage
{
    public DashboardPage(WebDriver driver)
    {
    	super(driver);
    }
   
    @FindBy(how=How.CLASS_NAME,using="account")
    public WebElement userName;
    
    @FindBy(how=How.CLASS_NAME,using="logout")
    public WebElement logOut;
    
    public String getUserName()
    {
        return userName.getText();
    }
        
}
