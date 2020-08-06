package com.automation.training.autotraining.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class FlightSearchPage extends BasePage {

	@FindBy(id="sortDropdown")
	private WebElement sortDropdown;
	
	@FindBy(id="flight-listing-container")
	private WebElement flightList;
	
	@FindBy(id="change-filters-message-box")
	private WebElement changeFiltersBox;
	
	public FlightSearchPage(WebDriver pDriver) {
		super(pDriver);
		
	}

	public ArrayList<String> getSortDropdownContents(){
		closePopUp(2);
		ArrayList<String> ddContents = new ArrayList<String>();
		getWait().until(ExpectedConditions.elementToBeClickable(sortDropdown));
		List<WebElement> options=sortDropdown.findElements(By.tagName("option"));
		for (WebElement option: options){
			ddContents.add(option.getText());
		}
		return ddContents;
	}
	
	private List<WebElement> getFlightList(){
		return flightList.findElements(By.className("flight-module"));
	}
	
	public void flightListLoadedWait() {
		getWait().until(ExpectedConditions.visibilityOf(flightList));
		getWait().until(ExpectedConditions.visibilityOf(changeFiltersBox));
	}
	
	public int flightListCount() {
		return getFlightList().size();
	}
	
	public List<WebElement> selectButtons(){
		return flightList.findElements(By.cssSelector("button[data-test-id='select-button']"));
	}
	
	public int selectButtonsCount() {
		return selectButtons().size();
	}
	
	public int flightDurationsCount() {
		return flightList.findElements(By.className("duration-emphasis")).size();
	}
	
	public int flightDetailsAndFeesCount() {
		return flightList.findElements(By.className("flight-details-link")).size();
	}
	
	public void sortListByDurationShorter() {
		sortDropdown.click();
		Select select = new Select(sortDropdown); 
		select.selectByValue("duration:asc");
	}
	
	public ArrayList<Integer> durations(){
		ArrayList<Integer> durationsMinutes = new ArrayList<Integer>();
		getWait().until(ExpectedConditions.elementToBeClickable(sortDropdown));
		flightList = getDriver().findElement(By.id("flight-listing-container"));
		List<WebElement> flightDurations = flightList.findElements(By.className("duration-emphasis"));
		for (WebElement duration: flightDurations){
			String hourString = duration.getText();
			durationsMinutes.add(
					(Integer.parseInt(hourString.substring(0, hourString.indexOf('h')))*60) +
					Integer.parseInt(hourString.substring(hourString.indexOf(" ")+1, hourString.indexOf("m"))));
		}
		return durationsMinutes;
	}
	
	public void clickSelectButton(int optionIndex) {
		selectButtons().get(optionIndex-1).click();
		
		//getWait().until(ExpectedConditions.visibilityOf(fareButton));
		if(!getDriver().findElements(By.cssSelector("button[data-test-id='select-button-1']")).isEmpty()) {
			WebElement fareButton = getDriver().findElement(By.cssSelector("button[data-test-id='select-button-1']"));
			fareButton.click();
		}
	}
	
	/*
	 * @Departure date option index
	 * @Return date option index
	 */
	public FlightInfoPage selectFlights(int departureOption, int returnOption) {
		clickSelectButton(departureOption);
		flightListLoadedWait();
		System.out.println(selectButtons().get(returnOption-1).getAttribute("id"));
		clickSelectButton(returnOption);
		WebElement noThanksHotel = getDriver().findElement(By.id("forcedChoiceNoThanks"));
		getWait().until(ExpectedConditions.elementToBeClickable(noThanksHotel));
		noThanksHotel.click();
		ArrayList<String> tabs = new ArrayList<String> (getDriver().getWindowHandles());
	    getDriver().switchTo().window(tabs.get(1));
		return new FlightInfoPage(getDriver());
	}
	
	/*
	 * @Departure date option index
	 * @Return date option index
	 */
	public PackageInfoPage selectFlightsPackage(int departureOption, int returnOption) {
		clickSelectButton(departureOption);
		flightListLoadedWait();
		System.out.println(selectButtons().get(returnOption-1).getAttribute("id"));
		clickSelectButton(returnOption);
		WebElement noThanksHotel = getDriver().findElement(By.id("forcedChoiceNoThanks"));
		getWait().until(ExpectedConditions.elementToBeClickable(noThanksHotel));
		noThanksHotel.click();
		ArrayList<String> tabs = new ArrayList<String> (getDriver().getWindowHandles());
	    getDriver().switchTo().window(tabs.get(1));
		return new PackageInfoPage(getDriver());
	}
}
