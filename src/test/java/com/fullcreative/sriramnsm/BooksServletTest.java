package com.fullcreative.sriramnsm;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class BooksServletTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	/**
	 * Tests for createBook Method
	 */
	@Test
	public void validBookcreateBookTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> testCaseMap = TestCaseStrings.testCases.get("validBook");
		String inputString = BooksServletUtilities.mapToJsonString(testCaseMap);
		System.out.println(inputString);
		Map<String, Object> actualBookValue = BooksServletUtilities.createBook(inputString);
		int code = Integer.parseInt(actualBookValue.remove("STATUS_CODE").toString());
		if (actualBookValue.containsKey("BOOK_ID")) {
			String key = actualBookValue.remove("BOOK_ID").toString();
		}
		assertEquals(code, 200);
		assertEquals(testCaseMap.toString(), actualBookValue.toString());
	}

//	@Test
//	public void authorNameNullcreateBookTest() throws EntityNotFoundException {
//		Map<String, Object> testCase = TestCaseStrings.testCases.get("authorNameNull");
//		Map<String, Object> errorMessage = TestCaseStrings.errorMessages.get("authorNameNull");
//		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
//		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
//		assertEquals(code, 400);
//		assertEquals(errorMessage, actualBookValue.toString());
//	}
//
//	@Test
//	public void countryNullcreateBookTest() throws EntityNotFoundException {
//		Map<String, Object> testCase = TestCaseStrings.testCases.get("countryNull");
//		Map<String, Object> errorMessage = TestCaseStrings.errorMessages.get("countryNull");
//		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
//		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
//		assertEquals(code, 400);
//		assertEquals(errorMessage, actualBookValue.toString());
//	}
//
//	@Test
//	public void languageNullcreateBookTest() throws EntityNotFoundException {
//		Map<String, Object> testCase = TestCaseStrings.testCases.get("languageNull");
//		Map<String, Object> errorMessage = TestCaseStrings.errorMessages.get("languageNull");
//		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
//		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
//		assertEquals(code, 400);
//		assertEquals(errorMessage, actualBookValue.toString());
//	}
//
//	@Test
//	public void negativePagescreateBookTest() throws EntityNotFoundException {
//		Map<String, Object> testCase = TestCaseStrings.testCases.get("negativePages");
//		Map<String, Object> errorMessage = TestCaseStrings.errorMessages.get("negativePages");
//		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
//		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
//		assertEquals(code, 400);
//		assertEquals(errorMessage, actualBookValue.toString());
//	}
//
//	@Test
//	public void minPageCountcreateBookTest() throws EntityNotFoundException {
//		Map<String, Object> testCase = TestCaseStrings.testCases.get("minPageCount");
//		Map<String, Object> errorMessage = TestCaseStrings.errorMessages.get("minPageCount");
//		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
//		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
//		assertEquals(code, 400);
//		assertEquals(errorMessage, actualBookValue.toString());
//	}
//
//	@Test
//	public void negativeYearcreateBookTest() throws EntityNotFoundException {
//		Map<String, Object> testCase = TestCaseStrings.testCases.get("negativeYear");
//		Map<String, Object> errorMessage = TestCaseStrings.errorMessages.get("negativeYear");
//		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
//		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
//		assertEquals(code, 400);
//		assertEquals(errorMessage, actualBookValue.toString());
//	}
//
//	@Test
//	public void bookTitleNullcreateBookTest() throws EntityNotFoundException {
//		Map<String, Object> testCase = TestCaseStrings.testCases.get("bookTitleNull");
//		Map<String, Object> errorMessage = TestCaseStrings.errorMessages.get("bookTitleNull");
//		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
//		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
//		assertEquals(code, 400);
//		assertEquals(errorMessage, actualBookValue.toString());
//	}
//
//	@Test
//	public void futureYearValuecreateBookTest() throws EntityNotFoundException {
//		Map<String, Object> testCase = TestCaseStrings.testCases.get("futureYearValue");
//		Map<String, Object> errorMessage = TestCaseStrings.errorMessages.get("futureYearValue");
//		JsonObject actualBookValue = BooksServletUtilities.createBook(testCase);
//		int code = actualBookValue.remove("STATUS_CODE").getAsInt();
//		assertEquals(code, 400);
//		assertEquals(errorMessage, actualBookValue.toString());
//	}
//
//	/**
//	 * Tests for getBooks Method
//	 * 
//	 * @throws EntityNotFoundException
//	 */
//	@Test
//	public void getAllBooksTest() throws EntityNotFoundException {
//		JsonObject responseObject = BooksServletUtilities.createBook(TestCaseStrings.testCases.get("validBook"));
//		String response = BooksServletUtilities.getAllBooks();
//		System.out.println(responseObject.toString());
//		System.out.println(response);
//	}
}

