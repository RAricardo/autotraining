package com.automation.training.autotraining.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FlightInfoPage extends BasePage {
	
	@FindBy(className = "section-header-main")
	private WebElement sectionHeader;
	
	@FindBy(className = "tripSummaryContainer")
	private WebElement tripSummaryContainer;
	
	@FindBy(id = "bookButton")
	private WebElement bookButton;

	public FlightInfoPage(WebDriver pDriver) {
		super(pDriver);
	}
	
	public String reviewTripHeader() {
		return sectionHeader.getText();
	}
	
	public String getTotalPrice() {
		WebElement totalPrice = tripSummaryContainer.findElement(By.cssSelector("span.packagePriceTotal"));
		return totalPrice.getAttribute("class");
	}
	
	public boolean checkDates(String departing, String returning) {
		String [] ddate = departing.split("/");
		String [] rdate = returning.split("/");
		String testDeparting = UtilsClass.monthParser(ddate[1]) + " " + ddate[2];
		String testreturning = UtilsClass.monthParser(rdate[1]) + " " + rdate[2];
		List<WebElement> dates = getDriver().findElements(By.className("departureDate"));
		getWait().until(ExpectedConditions.visibilityOf(dates.get(0)));
		return dates.get(0).getText().contains(testDeparting) && dates.get(1).getText().contains(testreturning);
	}
	
	public String getPriceGuarantee() {
		WebElement priceGuarantee = tripSummaryContainer.findElement(By.cssSelector("div.priceGuarantee"));
		return priceGuarantee.getAttribute("class");
	}
	
	public FlightCheckOutPage startBooking() {
		getWait().until(ExpectedConditions.elementToBeClickable(bookButton));
		bookButton.click();
		return new FlightCheckOutPage(getDriver());
	}
}
