package com.automation.training.autotraining.tests.suite1;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automation.training.autotraining.pages.FlightCheckOutPage;
import com.automation.training.autotraining.pages.FlightInfoPage;
import com.automation.training.autotraining.pages.FlightSearchPage;
import com.automation.training.autotraining.pages.TravelHomePage;

public class Exercise1 {
	private MyDriver driver;
	private TravelHomePage homePage;
	private FlightSearchPage searchPage;
	private FlightInfoPage infoPage;
	private FlightCheckOutPage checkoutPage;
	
	@BeforeSuite(groups = {"exercise1"})
	@Parameters({"browser"})
	public void beforeSuite(String browser) {
		driver = new MyDriver(browser);
		homePage = new TravelHomePage(driver.getDriver());
	}
	
	@BeforeTest (groups = {"exercise1"})
	@Parameters({"from-location", "to-location","departure-date","returning-date","adult-num"})
	public void SearchFlight(String from, String to, String departure, String returning, String adnum) {
		 searchPage = homePage.searchFlight(from, to, departure, returning, adnum);
		 searchPage.flightListLoadedWait();
	}
	
	@Test (groups = {"exercise1"})
	/*
	 * Must confirm that a dropdown box that lets you order by price, departure, arrival and duration exists.
	 */
	public void verifySortDropDown () {
		ArrayList<String> testDropdown = new ArrayList<String>();
		testDropdown.add("Price (Lowest)");
		testDropdown.add("Price (Highest)");
		testDropdown.add("Duration (Shortest)");
		testDropdown.add("Duration (Longest)");
		testDropdown.add("Departure (Earliest)");
		testDropdown.add("Departure (Latest)");
		testDropdown.add("Arrival (Earliest)");
		testDropdown.add("Arrival (Latest)");
		
		ArrayList<String> ddContents = searchPage.getSortDropdownContents();
		assertTrue(ddContents.containsAll(testDropdown));
	}
	
	@Test (groups = {"exercise1"})
	/*
	 * Compares flight listings size to select buttons count in order to verify that each listing contains a select button.
	 */
	public void verifySelectButtons() {
		assertEquals(searchPage.flightListCount(),searchPage.selectButtonsCount());
	}
	
	@Test (groups = {"exercise1"})
	/*
	 * Compares flight listings size to duration tags count in order to verify that each listing contains a duration tag.
	 */
	public void verifyDurationTags() {
		assertEquals(searchPage.flightListCount(),searchPage.flightDurationsCount());
	}
	
	@Test (groups = {"exercise1"})
	/*
	 * Compares flight listings size to details and baggage fees links count in order to verify
	 * that each listing contains a details and baggage fees link.
	 */
	public void verifyDetailsAndFees() {
		assertEquals(searchPage.flightListCount(),searchPage.flightDetailsAndFeesCount());
	}
	
	@Test (groups = {"exercise1"}, dependsOnMethods={"verifySelectButtons", "verifyDurationTags", "verifyDetailsAndFees"})
	/*
	 * Sorts flights by shorter durations using the page sorting dropdown, then checks
	 * if returned array containing flight durations is sorted by shorter durations.
	 */
	public void verifySortByDurationShorter() {
		searchPage.sortListByDurationShorter();
		ArrayList<Integer> durations = searchPage.durations();
		ArrayList<Integer> copy = durations;
	    Collections.sort(copy);
	    assertTrue(copy.equals(durations));
	}
	
	@Test (groups = {"exercise1"},dependsOnMethods= {"verifySortByDurationShorter"})
	/*
	 * Selects flight departure and returning dates, opening the flights details page verifies page total price, 
	 * price guarantee tag and departing/returning information.
	 */
	@Parameters({"departure-option", "return-option","departure-date","returning-date"})
	public void verifyTripDetailsPage(int departureOption, int returnOption, String departing, String returning) {
		infoPage = searchPage.selectFlights(departureOption, returnOption);
		assertEquals(infoPage.reviewTripHeader(), "Review your trip");
		assertTrue(infoPage.checkDates(departing, returning));
		assertEquals(infoPage.getPriceGuarantee(), "priceGuarantee");
		assertEquals(infoPage.getTotalPrice(), "packagePriceTotal");
	}
	
	@Test(groups = {"exercise1"},dependsOnMethods={"verifyTripDetailsPage"})
	/*
	 * Clicks Book Flight Button and proceeds to perform 5 validations to verify CheckOutPage is loaded.
	 */
	public void verifyBookingPage() {
		checkoutPage = infoPage.startBooking();
		assertEquals(checkoutPage.getPageHeader(),"Secure booking - only takes a few minutes!");
		assertEquals(checkoutPage.getPriceSummaryTitle(),"Your price summary");
		assertEquals(checkoutPage.getConfirmationHeader(),"Where should we send your confirmation?");
		assertEquals(checkoutPage.getCompleteBox(),"Review and book your trip");
		assertEquals(checkoutPage.getPageHeader(),"Secure booking - only takes a few minutes!");
	}
	
	@AfterClass (groups = {"exercise1"})
	public void dispose() {
		homePage.dispose();
		searchPage.dispose();
		infoPage.dispose();
		checkoutPage.dispose();
	}
}
