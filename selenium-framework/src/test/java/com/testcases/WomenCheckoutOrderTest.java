package com.testcases;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.factory.pages.LoginPage;
import com.factory.pages.WomenStorePage;
import com.tests.constant.Constant;
import com.tests.support.TestBaseUI;
import com.tests.utils.CSVReader;

/**
 * Test the Order functionality
 * 
 * @author rahul.raman
 * @steps 1.Set the context 2.Initialize the Page Factory elements 3.Read
 *        required Data from csv 4.Complete Test Steps 6.Validate the Test
 */

public class WomenCheckoutOrderTest extends TestBaseUI
{
    @BeforeMethod
    public void beforeMethod(Method method, ITestContext context)
    {
        logger.info("Setting context");
        Test test = method.getAnnotation(Test.class);
        context.setAttribute("TestName", test.testName()); 
        logger.info("initializing Login Factory Elements");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        
      //Prerequisites
        logger.info("Completing Prerequisites of Order - Login In the System");
        String absolutePathLP = getDataPath(loginPage.DATASEEDING_DATA_FILENAME, dataFolderDP);
        List<Map<String, Object>> loginPagerows = CSVReader.readResourceCSV(absolutePathLP);
        String validemail = loginPagerows.get(0).get(Constant.EMAIL.getValue()).toString();
        String validpassword = loginPagerows.get(0).get(Constant.PASSWORD.getValue()).toString();
        loginPage.click_on_SignIn();
        loginPage.enterexistingemail(validemail);
        loginPage.enterexistingpassword(validpassword);
        loginPage.logintoSite();

    }

    @Test(testName = "Challenge-1", description = "To order a women dress", priority = 101)
    public void orderAProduct(ITestContext context) throws Exception
    {
        logger.info("Initializing WomenStorePage Factory Elements");
        WomenStorePage womenStorePage = PageFactory.initElements(driver, WomenStorePage.class);
        String absolutePathWSP = getDataPath(womenStorePage.DATASEEDING_DATA_FILENAME, dataFolderDP);

        logger.info("reading number of lines of csv");
        int count0 = readLines(absolutePathWSP);
        if (count0 > 0)
        {
            List<Map<String, Object>> rows = CSVReader.readResourceCSV(absolutePathWSP);
            for (int row = 0; row < rows.size(); row++)
            {
                //Given
                String key = rows.get(row).get(Constant.KEY.getValue()).toString();
                String dressName = rows.get(row).get(Constant.DRESS_NAME.getValue()).toString();
                String sizeDetails = rows.get(row).get(Constant.SIZE.getValue()).toString();
                String selectColor = rows.get(row).get(Constant.COLOR.getValue()).toString();
                if (!((key.contains((CharSequence) context.getAttribute("TestName"))))) continue;
                
                //When
                womenStorePage.clickonWomenoption();
                womenStorePage.clickonDressOption(dressName);
                womenStorePage.selectSize(sizeDetails);
                womenStorePage.selectColor(selectColor);
                womenStorePage.click_on_Submit();
                womenStorePage.clickoncheckOutButton();
                womenStorePage.clickonproceedCheckoutButton();
                womenStorePage.clickonbtn_process_address();
                womenStorePage.clickonrbn_accept_term();
                womenStorePage.clickonbtn_process_carrier();
                womenStorePage.clickonpayByBankwire();
                womenStorePage.clickonbtn_confirm_order();

                //Then
                logger.info("Validating Test case");
                
                Assert.assertEquals("ORDER CONFIRMATION", womenStorePage.confirmHeader());
                Assert.assertTrue(womenStorePage.confirmShippingIsDisplayed());
                Assert.assertTrue(womenStorePage.confirmpaymentIsDisplayed());
                Assert.assertTrue(
                    womenStorePage.confirmOrderIsComplete().contains("Your order on My Store is complete."));
                Assert.assertTrue(driver.getCurrentUrl().contains("controller=order-confirmation"));

            }
        }
    }

}
