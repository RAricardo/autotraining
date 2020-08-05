package com.automation.training.autotraining.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.util.Calendar;
import java.util.List;

public class TravelHomePage extends BasePage {

	@FindBy(id="tab-flight-tab-hp")
	private WebElement flightsButton;
	
	@FindBy(id="flight-origin-hp-flight")
	private WebElement flightOrigin;
	
	@FindBy(id="flight-destination-hp-flight")
	private WebElement flightDestination;
	
	@FindBy(id="flight-departing-wrapper-hp-flight")
	private WebElement departingPicker;
	
	@FindBy(id="flight-returning-wrapper-hp-flight")
	private WebElement returningPicker;
	
	@FindBy(id="flight-adults-hp-flight")
	private WebElement adultNumPicker;
	
	@FindBy(className ="gcw-submit")
	private WebElement searchButton;
	
	public TravelHomePage(WebDriver pDriver) {
		super(pDriver);
		getDriver().get("https://www.travelocity.com/");
	}
	
	/*
	 * @inputDate The date to be picked
	 */
	private void pickDate(String inputDate) {
		//Splitting date.
		String [] date = inputDate.split("/");
		
		//Formating our calendar date to match with the date picker header for comparison.
		String formatedMonthAndYear = UtilsClass.monthParser(date[1]) + " " + date[0];
		String dtMonthAndYearHeader = getDriver()
				.findElements(By.className("datepicker-cal-month-header")).get(0).getText();

		//Clicking the next month button until we match the month and the year.
		while (!formatedMonthAndYear.equals(dtMonthAndYearHeader)) {
			WebElement nextBtn = getDriver().findElement(By.className("datepicker-next"));
			getActions().moveToElement(nextBtn).click().perform();
			dtMonthAndYearHeader = getDriver()
					.findElements(By.className("datepicker-cal-month-header")).get(0).getText();
		}
		
		//Selecting day from calendar table
		WebElement dateWidget = getDriver().findElement(By.className("datepicker-cal-weeks"));
		List<WebElement> columns=dateWidget.findElements(By.className("datepicker-cal-date"));
		for (WebElement cell: columns){
			String day = cell.getAttribute("data-day");
			if (day!=null && day.equals(String.valueOf(date[2]))) {
				cell.click();
				break;
			}
		}
	}
	
	public FlightSearchPage searchFlight(String from, String to, String departure, String returning, String adnum) {
		getWait().until(ExpectedConditions.elementToBeClickable(flightsButton));
		flightsButton.click();
		getWait().until(ExpectedConditions.elementToBeClickable(flightOrigin));
		flightOrigin.sendKeys(from);
		flightDestination.sendKeys(to);
		departingPicker.click();
		pickDate(departure);
		returningPicker.click();
		pickDate(returning);
		adultNumPicker.click();
		Select select = new Select(adultNumPicker); 
		select.selectByValue(adnum);
		searchButton.click();
		return new FlightSearchPage(getDriver());
	}
	
}
