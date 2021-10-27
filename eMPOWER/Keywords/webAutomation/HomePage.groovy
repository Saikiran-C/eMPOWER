package webAutomation

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import webAutomation.Verifications
import webAutomation.WebActions
import internal.GlobalVariable

public class HomePage {

	// Creating an object for Verifications class.
	Verifications verifications = new Verifications();
	// Creating an object for WebActions class.
	WebActions actions = new WebActions();

	@Keyword
	def navigateToCreateNewOrderPage() {
		// Clicking on Emt in left side bar
		clickOnEmt();

		// Clicking on order management in Emt
		clickOnOrderManagement();

		// Clicking on create new order in order management
		clickOnCreateNewOrder();
	}

	@Keyword
	def clickSignoutButtonInUserProfile() {
		// Clicking on user profile on top right
		clickUserProfile();

		// Clicking on signout
		clickSignout();
	}

	@Keyword
	def clickOnEmt() {
		actions.click(findTestObject('Homepage/Sidebar/emtbutton'));
	}


	@Keyword
	def clickOnOrderManagement() {
		actions.click(findTestObject('Homepage/Sidebar/ordermanagement'));
	}

	@Keyword
	def clickOnCreateNewOrder() {
		actions.click(findTestObject('Homepage/Sidebar/createneworderbutton'));
	}

	@Keyword
	def clickUserProfile() {
		actions.click(findTestObject('Homepage/Profile/userprofile'));
	}

	@Keyword
	def clickSignout() {
		actions.click(findTestObject('Object Repository/Homepage/Profile/signOutButton'));
	}
}

