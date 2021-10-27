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
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class OrderPage {

	// Creating an object for Verifications class.
	Verifications verifications = new Verifications();
	// Creating an object for WebActions class.
	WebActions actions = new WebActions();

	@Keyword
	def validateErrorMessageWithoutProduct(def mode, def period, def message) {
		selectPaymentMode(mode);
		selectPaymentPeriod(period);
		clickOnSubmitButton();
		switchTODefaultContent()
		verifyErrorMessage(message);
	}

	@Keyword
	def validateErrorMessageWithoutPayment(def products, def message, def message1) {
		addProducts(products);
		switchTODefaultContent()
		WebUI.executeJavaScript('window.scrollTo(0,0)', []);
		switchTOFrame();
		clickOnSubmitButton();
		switchTODefaultContent();
		verifyErrorMessage(message);
		verifyErrorMessage(message1);
	}

	@Keyword
	def createOrder(def mode, def period, def products) {
		addProducts(products);
		switchTODefaultContent();
		WebUI.executeJavaScript('window.scrollTo(0,0)', []);
		switchTOFrame();
		selectPaymentMode(mode);
		selectPaymentPeriod(period);
		clickOnSubmitButton();
		switchTODefaultContent();
		verifyOrderSuccessful();
	}

	@Keyword
	def addProducts(def products) {
		for(int i=0;i<products.size();i++) {
			// Clicking on add product
			clickOnAddProduct();

			// Selecting the product
			selectTheProduct(i, products[i][0]);

			// Enter the quantity
			enterQuantity(i, products[i][1]);
		}
	}

	@Keyword
	def validateErrorMessageWithSameProducts(def row1, def row2, def product1, def message, def qty) {
		clickOnAddProduct();
		selectTheProduct(row1,product1);
		enterQuantity(row1, qty);
		clickOnAddProduct();
		selectTheProduct(row2,product1);
		switchTODefaultContent();
		verifyErrorMessage(message);
	}

	@Keyword
	def validateErrorMessageWithoutQuantity(def row1, def product1, def message, def mode, def period) {
		clickOnAddProduct();
		selectTheProduct(row1,product1);
		switchTODefaultContent()
		WebUI.executeJavaScript('window.scrollTo(0,0)', []);
		switchTOFrame();
		selectPaymentMode(mode);
		selectPaymentPeriod(period);
		clickOnSubmitButton();
		switchTODefaultContent()
		verifyErrorMessage(message);
	}

	@Keyword
	def verifyCustomerDetails(def customerName) {
		switchTOFrame();
		def customerDetails1 = getCustomerDetailsFromCustomerTable(customerName);
		switchTODefaultContent();
		selectCustomer(customerName);
		def customerDetails = getCustomerDetailsFromOrderPage();
		verifications.verifyTextMatch(customerDetails['customerType'], customerDetails1['customerType'], "selected customer type is not reflected in order page")
		verifications.verifyTextMatch(customerDetails['customerCategory'], customerDetails1['customerType'], "selected customer category is not reflected in order page")
		verifications.verifyTextMatch(customerDetails['customerName'], customerDetails1['customerName'], "selected customer name is not reflected in order page")
		verifications.verifyTextMatch(customerDetails['district'], customerDetails1['district'], "selected district is not reflected in order page")
		verifications.verifyTextMatch(customerDetails['society'], customerDetails1['society'], "selected society is not reflected in order page")
		verifications.verifyTextMatch(customerDetails['mobileNo'], customerDetails1['mobileNo'], "selected mobileNo is not reflected in order page")
	}

	@Keyword
	def compareNetPriceValuesFromTable(def products, def row) {
		addProducts(products);
		getNetPrice(row);
	}

	@Keyword
	def compareNetPriceValuesFromTableForMultipleProducts(def products, def row1, def row2) {
		addProducts(products);
		getNetPriceForMultipleProducts(row1,row2);
	}

	@Keyword
	def getNetBillValue() {
		def netBill = WebUI.getAttribute(findTestObject('Homepage/Orders/netBill'), 'value')
		return netBill
	}

	@Keyword
	def getNetTotalValue(def row) {
		def netTotal = actions.getText(findTestObject('Homepage/Orders/netTotalInProductTable',['number':row]));
		return netTotal
	}

	@Keyword
	def getNetPrice(def row) {
		actions.click(findTestObject('Homepage/Orders/chooseProductTitle'));
		// Declaring the variable
		def netTotal = getNetTotalValue(row);
		switchTODefaultContent();
		WebUI.executeJavaScript('window.scrollTo(0,0)', []);
		switchTOFrame();
		// Declaring the variable
		def netBill = getNetBillValue();
		verifications.verifyTextMatch(netBill, netTotal, "Price does not match");
	}

	@Keyword
	def getNetPriceForMultipleProducts(def row1, def row2) {
		actions.click(findTestObject('Homepage/Orders/chooseProductTitle'));
		// Declaring the variables
		def netTotal1 = getNetTotalValue(row1).toFloat();
		def netTotal2 = getNetTotalValue(row2).toFloat();
		def netTotal = netTotal1+netTotal2;
		netTotal=netTotal.toFloat();
		switchTODefaultContent();
		WebUI.executeJavaScript('window.scrollTo(0,0)', []);
		switchTOFrame();
		def netBill = getNetBillValue();
		netBill=netBill.toFloat();
		if(netBill != netTotal) {
			KeywordUtil.markFailed("Net bill price does not match for multiple products");
		}
	}

	@Keyword
	def removeRowAfterSelectingProduct(def products) {
		addProducts(products);
		def number = products.size()-1;
		clickRemoveButton(number);
		verifyRows(number);
	}

	@Keyword
	def removeRowBeforeSelectingProduct(def number) {
		clickOnAddProduct();
		clickRemoveButton(number);
		verifyRows(number);
	}

	@Keyword
	def validateCancelButtonAfterFillingDetails(def mode, def period, def products) {
		addProducts(products);
		switchTODefaultContent();
		WebUI.executeJavaScript('window.scrollTo(0,0)', []);
		switchTOFrame();
		selectPaymentMode(mode);
		selectPaymentPeriod(period);
		clickCancelButton();
		switchTODefaultContent();
		verifyChooseCustomerTable();
	}

	@Keyword
	def validateCancelButtonWithoutFillingDetails() {
		clickCancelButton();
		switchTODefaultContent();
		verifyChooseCustomerTable();
	}

	@Keyword
	def validateErrorMessageIfPriceExceeds(def products, def mode, def period, def message) {
		addProducts(products);
		switchTODefaultContent();
		WebUI.executeJavaScript('window.scrollTo(0,0)', []);
		switchTOFrame();
		selectPaymentMode(mode);
		selectPaymentPeriod(period);
		clickOnSubmitButton();
		switchTODefaultContent()
		verifyErrorMessage(message);
	}

	@Keyword
	def createOrderAfterSelectingPropertyGroup(def products, def row, def group, def mode, def period) {
		addProducts(products);
		selectPropertyGroup(row, group)
		switchTODefaultContent();
		WebUI.executeJavaScript('window.scrollTo(0,0)', []);
		switchTOFrame();
		selectPaymentMode(mode);
		selectPaymentPeriod(period);
		clickOnSubmitButton();
		switchTODefaultContent()
		verifyOrderSuccessful();
	}

	@Keyword
	def verifyRows(def number) {
		verifications.verifyElementNotPresent(findTestObject('Homepage/Orders/rowNumber',['number':number]), "Row is still shown even though user clicks remove button")
	}

	@Keyword
	def clickRemoveButton(def row) {
		// click remove product button
		actions.click(findTestObject('Object Repository/Homepage/Orders/minusButton',['number':row]));
	}

	@Keyword
	def switchTODefaultContent() {
		WebUI.switchToDefaultContent();
	}

	@Keyword
	def switchTOFrame() {
		WebUI.switchToFrame(findTestObject('Object Repository/Homepage/Customertable/iframe'),GlobalVariable.defaultWaitTime);
	}

	@Keyword
	def selectPaymentMode(def mode) {
		// Selecting the payment mode
		actions.click(findTestObject('Homepage/Orders/paymentModeDropdown'));
		selectDropdownOption(mode);
	}

	@Keyword
	def selectDropdownOption(def option) {
		actions.click(findTestObject('Homepage/Orders/paymentOptions',['name':option]));
	}

	@Keyword
	def selectPaymentPeriod(def period) {
		// Select the payment period
		actions.click(findTestObject('Homepage/Orders/paymentPeriodDropdown'));
		selectDropdownOption(period);
	}
	@Keyword
	def clickOnAddProduct() {
		// Click on Add button
		actions.click(findTestObject('Homepage/Orders/addproductbutton'));
	}

	@Keyword
	def selectTheProduct(def row, def product) {
		verifications.verifyElementPresent(findTestObject('Homepage/Orders/productselectbutton',['number':row]), "product table not opened")
		// Select the product
		WebUI.selectOptionByLabel(findTestObject('Homepage/Orders/productselectbutton',['number':row]), product, false)
	}

	@Keyword
	def verifyOrderCreationPage() {
		verifications.verifyElementPresent(findTestObject('Homepage/Orders/addproductbutton'),"Page is not navigated to order creation page");
	}

	@Keyword
	def enterQuantity(def row, def qty) {
		// Enter the quantity
		actions.sendKeys(findTestObject('Homepage/Orders/productquantity',['number':row]), qty)
	}

	@Keyword
	def selectPropertyGroup(def row, def group) {
		actions.click(findTestObject('Homepage/Orders/chooseProductTitle'));
		WebUI.selectOptionByLabel(findTestObject('Object Repository/Homepage/Orders/propertyGroup',['number':row]), group, false)
	}

	@Keyword
	def verifyChooseCustomerTable() {
		switchTOFrame();
		verifications.verifyElementPresent(findTestObject('Object Repository/Homepage/Customertable/choosecustomertable'),"Customer table is not shown on clicking create new order button ");
	}

	@Keyword
	def selectCustomer(def customerName) {
		switchTOFrame();
		actions.click(findTestObject('Object Repository/Homepage/Customertable/selectbuttonforcustomer',['name':customerName]));
	}

	@Keyword
	def clickOnSubmitButton() {
		// Click on submit button
		actions.click(findTestObject('Homepage/Orders/submitButton'));
	}

	@Keyword
	def clickCancelButton() {
		// Click on cancel button
		actions.click(findTestObject('Object Repository/Homepage/Orders/cancelButton'));
	}

	@Keyword
	def verifyOrderSuccessful() {
		verifications.verifyElementPresent(findTestObject('Homepage/Orders/orderSuccessful'), "Order is not placed successfully");
	}

	@Keyword
	def verifyErrorMessage(def error) {
		verifications.verifyElementPresent(findTestObject('Homepage/Orders/errorMessage',['message':error]), "Error message is not shown even though roduct is not selected");
	}

	@Keyword
	def getCustomerDetailsFromCustomerTable(def customerName) {
		// Declaring variable
		def customerDetails = [:]
		customerDetails.put('customerType', actions.getText(findTestObject('Homepage/Customertable/Customer data/customeType',['name':customerName])).trim());
		customerDetails.put('customerName', actions.getText(findTestObject('Homepage/Customertable/Customer data/customerName',['name':customerName])).trim());
		customerDetails.put('district', actions.getText(findTestObject('Homepage/Customertable/Customer data/district',['name':customerName])).trim());
		customerDetails.put('society', actions.getText(findTestObject('Homepage/Customertable/Customer data/society',['name':customerName])).trim());
		customerDetails.put('mobileNo', actions.getText(findTestObject('Homepage/Customertable/Customer data/mobileNo',['name':customerName])).trim());
		println(customerDetails);
		return customerDetails;
	}

	@Keyword
	def getCustomerDetailsFromOrderPage() {
		// Declaring he variable
		def customerDetails = [:]
		customerDetails.put('customerType',WebUI.getAttribute(findTestObject('Homepage/Orders/Customer data in order page/customerType'), 'value').trim());
		customerDetails.put('customerCategory', WebUI.getAttribute(findTestObject('Homepage/Orders/Customer data in order page/customerCategory'), 'value').trim());
		customerDetails.put('customerName',WebUI.getAttribute(findTestObject('Homepage/Orders/Customer data in order page/customerName'), 'value').trim());
		customerDetails.put('district', WebUI.getAttribute(findTestObject('Homepage/Orders/Customer data in order page/districtName'), 'value').trim());
		customerDetails.put('society', WebUI.getAttribute(findTestObject('Homepage/Orders/Customer data in order page/society'), 'value').trim());
		customerDetails.put('mobileNo',WebUI.getAttribute(findTestObject('Homepage/Orders/Customer data in order page/mobileNo'), 'value').trim());
		return customerDetails;
	}

	@Keyword
	def getDiscountValue(def row) {
		def discount = actions.getText(findTestObject('Object Repository/Homepage/Orders/discountValue',['number':row]))
		return discount;
	}

	@Keyword
	def verifyDiscountValue(def products, def row, def expectedDiscount) {
		addProducts(products);
		actions.click(findTestObject('Homepage/Orders/chooseProductTitle'));
		def discount = getDiscountValue(row);
		verifications.verifyTextMatch(discount, expectedDiscount,"Incorrect discount is shown for selected product")
	}
}
