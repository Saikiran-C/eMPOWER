package webAutomation
import java.nio.file.Files

import org.openqa.selenium.Capabilities
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable


//import kms.turing.katalon.plugins.helper.table.HTMLTableHelper


class WebActions {

	/**
	 * Actions on web elements
	 */

	int waitTime = GlobalVariable.defaultWaitTime

	@Keyword
	def click(TestObject element) {
		wait(element)
		WebUI.waitForElementClickable(element, waitTime)
		WebUI.click(element)
	}

	@Keyword
	def scrollToElement(TestObject element){
		wait(element)
		WebUI.scrollToElement(element, waitTime)
	}



	@Keyword
	def takeScreenshot() {
		File file = new File("passed.png")
		WebUI.takeScreenshot(file.getAbsolutePath())
		byte[] fileContent = Files.readAllBytes(file.toPath());
		//GlobalVariable.screenshorts.add(Base64.getEncoder().encodeToString(fileContent))
		if(file.exists()) {
			file.delete()
		}
	}

	@Keyword
	def sendKeys(TestObject element, String text) {
		wait(element)
		WebUI.waitForElementClickable(element, waitTime)
		WebUI.clearText(element)
		WebUI.sendKeys(element, text)
	}

	@Keyword
	def sendKeysEncrypted(TestObject element, String encryptedText) {
		wait(element)
		WebUI.waitForElementClickable(element, waitTime)
		WebUI.setEncryptedText(element, encryptedText)
	}

	@Keyword
	def getText(TestObject element, int maxWaitTime = waitTime) {
		wait(element, maxWaitTime)
		return WebUI.getText(element).trim()
	}

	@Keyword
	def wait(TestObject element, int maxWaitTime = waitTime) {
		WebUI.waitForElementPresent(element, maxWaitTime)
		WebUI.waitForElementVisible(element, maxWaitTime)
	}

	// @Keyword
	// def getElementCount(TestObject element, int waitTimeLocal = 5) {
	//
	// def count
	// if(!(WebUI.verifyElementPresent(element, waitTimeLocal, FailureHandling.OPTIONAL))) {
	// count = 0
	// }
	// else {
	// count = WebUiCommonHelper.findWebElements(element, waitTime).size()
	// }
	// return count
	// }
	@Keyword
	def getElementCount(TestObject element, int timeout =  GlobalVariable.timeout) {
		WebUI.waitForElementVisible(element, timeout, FailureHandling.CONTINUE_ON_FAILURE)
		//Use Katalon built-in function to find elements with time out 1 seconds
		List<WebElement> elements = WebUI.findWebElements(element, timeout)
		return elements.size()
	}

	@Keyword
	def getColumnIndex(List headers, String columName){

		WebElement table = HTMLTableHelper.identifyTableByColumnHeaders(headers, 10,  FailureHandling.CONTINUE_ON_FAILURE)
		return HTMLTableHelper.getColumnIndexByHeader(table, columName, FailureHandling.STOP_ON_FAILURE)
	}

	@Keyword
	def getUrlAndVerify(String name, String failuredescription ) {

		if(!(WebUI.getUrl().endsWith(name))) {
			WebUI.takeScreenshot()
			KeywordUtil.markFailed(failuredescription)
		}
	}

	@Keyword
	def getBrowser() {

		String browser = DriverFactory.getExecutedBrowser().getName()

		println browser
		if(browser == "CHROME_DRIVER") {
			return "Chrome"
		}
		else if(browser == "FIREFOX_DRIVER") {
			return "Firefox"
		}
		else if (browser == "EDGE_DRIVER") {
			return "Edge"
		}
		else if (browser == "EDGE_CHROMIUM_DRIVER") {
			return "Edge"
		}
	}

	@Keyword
	def getTestExecutionName() {
		Date date = new Date();
		def todaysdate = date.getCalendarDate().toString().split("T")[0];
		return "Test Execution - "+getBrowser()+" - "+todaysdate
	}

	@Keyword
	def stringContains(String sourceText, String textThatNeedToBeVerified,String failureDescription) {
		if(sourceText.indexOf(textThatNeedToBeVerified) == -1)

			KeywordUtil.markFailedAndStop(failureDescription)
	}
}