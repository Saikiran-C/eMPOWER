import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class TestListener {
	// This will be executed before every test suite
	@BeforeTestSuite
	def navigateToUrl() {
		// Opening the browser
		WebUI.openBrowser('');
		// Maximize the window size
		WebUI.maximizeWindow();
		// Navigating to the application url
		WebUI.navigateToUrl(GlobalVariable.applicationUrl);
		// Login with valid credentials
		CustomKeywords.'webAutomation.Login.loginToApplication'(GlobalVariable.username, GlobalVariable.password)
	}

	// This will be executed after every test case
	@AfterTestCase
	def afterTestCase() {
		// Navigate to home page
		WebUI.navigateToUrl("http://41.211.22.219/eMPOWER_UAT/Home/Index")
	}

	//  This will be executed after every test suite
	@AfterTestSuite
	def closeBrowser() {
		// Closing the browser
		WebUI.closeBrowser();
	}

}