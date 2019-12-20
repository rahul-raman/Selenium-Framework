package com.testcases;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.factory.pages.DashboardPage;
import com.factory.pages.LoginPage;
import com.tests.constant.Constant;
import com.tests.support.TestBaseUI;
import com.tests.utils.CSVReader;

/**
 * Test the Log In functionality
 * 
 * @author rahul.raman
 * @steps 1.Set the context 2.Initialize the Page Factory elements 3.Read
 *        required Data from csv 4.Complete Test Steps 6.Validate the Test
 */

public class LoginTest extends TestBaseUI
{
    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context)
    {
        logger.info("Setting context");
        Test test = method.getAnnotation(Test.class);
        context.setAttribute("TestName", test.testName());
    }

    @Test(testName = "Challenge-1", description = "To Login a existing User in the system", priority = 101)
    public void LoginExistingUser(ITestContext context) throws Exception
    {

        logger.info("initializing Factory Elements");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        DashboardPage dashboard = PageFactory.initElements(driver, DashboardPage.class);

        String absolutePathLP = getDataPath(LoginPage.DATASEEDING_DATA_FILENAME, dataFolderDP);
        logger.info("reading number of lines of csv");
        int count0 = readLines(absolutePathLP);
        if (count0 > 0)
        {
            List<Map<String, Object>> rows = CSVReader.readResourceCSV(absolutePathLP);
            for (int row = 0; row < rows.size(); row++)
            {
                // Given
                String key = rows.get(row).get(Constant.KEY.getValue()).toString();
                String validemail = rows.get(row).get(Constant.EMAIL.getValue()).toString();
                String validpassword = rows.get(row).get(Constant.PASSWORD.getValue()).toString();
                String customerfirstname = rows.get(row).get(Constant.FIRSTNAME.getValue()).toString();
                String customerlastname = rows.get(row).get(Constant.LASTNAME.getValue()).toString();

                if (!((key.contains((CharSequence) context.getAttribute("TestName"))))) continue;

                // When
                loginPage.click_on_SignIn();
                loginPage.enterexistingemail(validemail);
                loginPage.enterexistingpassword(validpassword);
                loginPage.logintoSite();

                // Then
                Assert.assertTrue(driver.getCurrentUrl().contains("?controller=my-account"));
                Assert.assertEquals(dashboard.getUserName(), customerfirstname + " " + customerlastname);
                Assert.assertTrue(dashboard.logOut.isDisplayed());
            }
        }
    }

}
