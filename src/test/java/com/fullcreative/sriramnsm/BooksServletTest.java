package com.fullcreative.sriramnsm;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class BooksServletTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}
	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
	@Test
	public void validBookcreateBook() throws EntityNotFoundException {
		String testCase = TestCaseStrings.testCases.get("validBook");
		String expected = TestCaseStrings.testCases.get("validBook");
		JsonObject actualBookValue = BooksServletUtilities.createBook(expected);
		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
		assertEquals(code, 200);
		assertEquals(expected, actualBookValue.toString());
	}

	@Test
	public void authorNameNullcreateBook() throws EntityNotFoundException {
		String testCase = TestCaseStrings.testCases.get("authorNameNull");
		String errorMessage = TestCaseStrings.errorMessages.get("authorNameNull");
		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
		assertEquals(code, 400);
		assertEquals(errorMessage, actualBookValue.toString());
	}

	@Test
	public void countryNullcreateBook() throws EntityNotFoundException {
		String testCase = TestCaseStrings.testCases.get("countryNull");
		String errorMessage = TestCaseStrings.errorMessages.get("countryNull");
		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
		assertEquals(code, 400);
		assertEquals(errorMessage, actualBookValue.toString());
	}

	@Test
	public void languageNullcreateBook() throws EntityNotFoundException {
		String testCase = TestCaseStrings.testCases.get("languageNull");
		String errorMessage = TestCaseStrings.errorMessages.get("languageNull");
		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
		assertEquals(code, 400);
		assertEquals(errorMessage, actualBookValue.toString());
	}

	@Test
	public void negativePagescreateBook() throws EntityNotFoundException {
		String testCase = TestCaseStrings.testCases.get("negativePages");
		String errorMessage = TestCaseStrings.errorMessages.get("negativePages");
		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
		assertEquals(code, 400);
		assertEquals(errorMessage, actualBookValue.toString());
	}

	@Test
	public void minPageCountcreateBook() throws EntityNotFoundException {
		String testCase = TestCaseStrings.testCases.get("minPageCount");
		String errorMessage = TestCaseStrings.errorMessages.get("minPageCount");
		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
		assertEquals(code, 400);
		assertEquals(errorMessage, actualBookValue.toString());
	}

	@Test
	public void negativeYearcreateBook() throws EntityNotFoundException {
		String testCase = TestCaseStrings.testCases.get("negativeYear");
		String errorMessage = TestCaseStrings.errorMessages.get("negativeYear");
		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
		assertEquals(code, 400);
		assertEquals(errorMessage, actualBookValue.toString());
	}

	@Test
	public void bookTitleNullcreateBook() throws EntityNotFoundException {
		String testCase = TestCaseStrings.testCases.get("bookTitleNull");
		String errorMessage = TestCaseStrings.errorMessages.get("bookTitleNull");
		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
		assertEquals(code, 400);
		assertEquals(errorMessage, actualBookValue.toString());
	}

	@Test
	public void futureYearValuecreateBook() throws EntityNotFoundException {
		String testCase = TestCaseStrings.testCases.get("futureYearValue");
		String errorMessage = TestCaseStrings.errorMessages.get("futureYearValue");
		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
		assertEquals(code, 400);
		assertEquals(errorMessage, actualBookValue.toString());
	}
}

