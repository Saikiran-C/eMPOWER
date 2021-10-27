import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// Declaring the variables
def products = [["Super Moted Spyr","2000"]];
def message = "Order cannot be submitted. Please make sure order value is between 1 to 10000."
def customerName = "Anthony Baidoo";
def paymentMode = "Bank";
def paymentPeriod = "7";

// Navigating to new order page
CustomKeywords.'webAutomation.HomePage.navigateToCreateNewOrderPage'();

// Selecting the customer based on input
CustomKeywords.'webAutomation.OrderPage.selectCustomer'(customerName);

// Verifying error message if price exceeds 
CustomKeywords.'webAutomation.OrderPage.validateErrorMessageIfPriceExceeds'(products, paymentMode, paymentPeriod, message)