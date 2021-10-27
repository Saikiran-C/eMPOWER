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

import internal.GlobalVariable

public class Login {

	Verifications verifications = new Verifications();
	WebActions actions = new WebActions();

	@Keyword
	def loginToApplication(def userName, def password) {
		// Enter the username
		enterUserName(userName);

		// Enter the password
		enterPassword(password);

		// Clicking on login button
		clickLoginButton();
	}

	@Keyword
	def enterUserName(def userName) {
		actions.sendKeys(findTestObject('Login/usernameinputfield'),userName);
	}

	@Keyword
	def enterPassword(def password) {
		actions.sendKeysEncrypted(findTestObject('Login/passwordinputfield'),password);
	}

	@Keyword
	def clickLoginButton() {
		actions.click(findTestObject('Login/loginbutton'));
	}

	@Keyword
	def verifyLoginSuccessful() {
		verifications.verifyElementPresent(findTestObject('Homepage/Profile/userprofile'),"User is Not successfully loggedIn")
	}

	@Keyword
	def verifyLoginPage() {
		verifications.verifyElementPresent(findTestObject('Object Repository/Login/loginButton'),"User is Not successfully loggedOut")
	}
}
