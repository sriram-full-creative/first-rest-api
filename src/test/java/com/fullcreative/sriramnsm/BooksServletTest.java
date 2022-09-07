package com.fullcreative.sriramnsm;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;

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

	private LinkedHashMap<String, Object> createBookInTestEnv(LinkedHashMap<String, Object> ValidTestCaseMap)
			throws EntityNotFoundException {
		String inputString = BooksServletUtilities.mapToJsonString(ValidTestCaseMap);
		LinkedHashMap<String, Object> createdBook = BooksServletUtilities.createBook(inputString);
		return createdBook;
	}

	private LinkedHashMap<String, Object> stripBookInTestEnv(LinkedHashMap<String, Object> createdBook) {
		LinkedHashMap<String, Object> strippedMap = new LinkedHashMap<>();
		int code = Integer.parseInt(createdBook.remove("STATUS_CODE").toString());
		strippedMap.put("STATUS_CODE", code);
		if (createdBook.containsKey("BOOK_ID")) {
			String key = createdBook.remove("BOOK_ID").toString();
			strippedMap.put("BOOK_ID", key);
		}
		return strippedMap;
	}

	/**
	 * Tests for POST Methods
	 */
	@Test
	public void validBookcreateBookTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> testCaseMap = CreateBookTestCaseStrings.testCases.get("validBook");
		String inputString = BooksServletUtilities.mapToJsonString(testCaseMap);
		System.out.println(inputString);
		LinkedHashMap<String, Object> actualBookValue = BooksServletUtilities.createBook(inputString);
		int code = Integer.parseInt(actualBookValue.remove("STATUS_CODE").toString());
		if (actualBookValue.containsKey("BOOK_ID")) {
			String key = actualBookValue.remove("BOOK_ID").toString();
		}
		assertEquals(code, 200);
		assertEquals(testCaseMap.toString(), actualBookValue.toString());
	}

	@Test
	public void authorNameNullcreateBookTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> testCaseMap = CreateBookTestCaseStrings.testCases.get("authorNameNull");
		LinkedHashMap<String, Object> errorMessage = CreateBookTestCaseStrings.errorMessages.get("authorNameNull");
		String inputString = BooksServletUtilities.mapToJsonString(testCaseMap);
		System.out.println(inputString);
		LinkedHashMap<String, Object> actualBookValue = BooksServletUtilities.createBook(inputString);
		int code = Integer.parseInt(actualBookValue.remove("STATUS_CODE").toString());
		assertEquals(code, 400);
		assertEquals(errorMessage.toString(), actualBookValue.toString());
	}

	@Test
	public void bookTitleNullcreateBookTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> testCaseMap = CreateBookTestCaseStrings.testCases.get("bookTitleNull");
		LinkedHashMap<String, Object> errorMessage = CreateBookTestCaseStrings.errorMessages.get("bookTitleNull");
		String inputString = BooksServletUtilities.mapToJsonString(testCaseMap);
		System.out.println(inputString);
		LinkedHashMap<String, Object> actualBookValue = BooksServletUtilities.createBook(inputString);
		int code = Integer.parseInt(actualBookValue.remove("STATUS_CODE").toString());
		assertEquals(code, 400);
		assertEquals(errorMessage.toString(), actualBookValue.toString());
	}

	@Test
	public void countryNullcreateBookTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> testCaseMap = CreateBookTestCaseStrings.testCases.get("countryNull");
		LinkedHashMap<String, Object> errorMessage = CreateBookTestCaseStrings.errorMessages.get("countryNull");
		String inputString = BooksServletUtilities.mapToJsonString(testCaseMap);
		System.out.println(inputString);
		LinkedHashMap<String, Object> actualBookValue = BooksServletUtilities.createBook(inputString);
		int code = Integer.parseInt(actualBookValue.remove("STATUS_CODE").toString());
		assertEquals(code, 400);
		assertEquals(errorMessage.toString(), actualBookValue.toString());
	}

	@Test
	public void languageNullcreateBookTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> testCaseMap = CreateBookTestCaseStrings.testCases.get("languageNull");
		LinkedHashMap<String, Object> errorMessage = CreateBookTestCaseStrings.errorMessages.get("languageNull");
		String inputString = BooksServletUtilities.mapToJsonString(testCaseMap);
		System.out.println(inputString);
		LinkedHashMap<String, Object> actualBookValue = BooksServletUtilities.createBook(inputString);
		int code = Integer.parseInt(actualBookValue.remove("STATUS_CODE").toString());
		assertEquals(code, 400);
		assertEquals(errorMessage.toString(), actualBookValue.toString());
	}

	@Test
	public void negativePagescreateBookTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> testCaseMap = CreateBookTestCaseStrings.testCases.get("negativePages");
		LinkedHashMap<String, Object> errorMessage = CreateBookTestCaseStrings.errorMessages.get("negativePages");
		String inputString = BooksServletUtilities.mapToJsonString(testCaseMap);
		System.out.println(inputString);
		LinkedHashMap<String, Object> actualBookValue = BooksServletUtilities.createBook(inputString);
		int code = Integer.parseInt(actualBookValue.remove("STATUS_CODE").toString());
		assertEquals(code, 400);
		assertEquals(errorMessage.toString(), actualBookValue.toString());
	}

	@Test
	public void minPageCountcreateBookTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> testCaseMap = CreateBookTestCaseStrings.testCases.get("minPageCount");
		LinkedHashMap<String, Object> errorMessage = CreateBookTestCaseStrings.errorMessages.get("minPageCount");
		String inputString = BooksServletUtilities.mapToJsonString(testCaseMap);
		System.out.println(inputString);
		LinkedHashMap<String, Object> actualBookValue = BooksServletUtilities.createBook(inputString);
		int code = Integer.parseInt(actualBookValue.remove("STATUS_CODE").toString());
		assertEquals(code, 400);
		assertEquals(errorMessage.toString(), actualBookValue.toString());
	}

	@Test
	public void negativeYearcreateBookTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> testCaseMap = CreateBookTestCaseStrings.testCases.get("negativeYear");
		LinkedHashMap<String, Object> errorMessage = CreateBookTestCaseStrings.errorMessages.get("negativeYear");
		String inputString = BooksServletUtilities.mapToJsonString(testCaseMap);
		System.out.println(inputString);
		LinkedHashMap<String, Object> actualBookValue = BooksServletUtilities.createBook(inputString);
		int code = Integer.parseInt(actualBookValue.remove("STATUS_CODE").toString());
		assertEquals(code, 400);
		assertEquals(errorMessage.toString(), actualBookValue.toString());
	}

	@Test
	public void futureYearValuecreateBookTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> testCaseMap = CreateBookTestCaseStrings.testCases.get("futureYearValue");
		LinkedHashMap<String, Object> errorMessage = CreateBookTestCaseStrings.errorMessages.get("futureYearValue");
		String inputString = BooksServletUtilities.mapToJsonString(testCaseMap);
		System.out.println(inputString);
		LinkedHashMap<String, Object> actualBookValue = BooksServletUtilities.createBook(inputString);
		int code = Integer.parseInt(actualBookValue.remove("STATUS_CODE").toString());
		assertEquals(code, 400);
		assertEquals(errorMessage.toString(), actualBookValue.toString());
	}

	/**
	 * Tests for GET Methods
	 * 
	 * @throws EntityNotFoundException
	 */

	@Test
	public void getOneBookPositiveTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> validBookMap = GetBookTestCaseStrings.testCases.get("validBook1");
		LinkedHashMap<String, Object> createdBook = createBookInTestEnv(validBookMap);
		LinkedHashMap<String, Object> strippedMap = stripBookInTestEnv(createdBook);
		int StatusCode = Integer.parseInt(strippedMap.get("STATUS_CODE").toString());
		String createdBookString = BooksServletUtilities.mapToJsonString(createdBook);
		LinkedHashMap<String, Object> actualBookMap = BooksServletUtilities
				.getOneBook(strippedMap.get("BOOK_ID").toString());
		String actualBookString = BooksServletUtilities.mapToJsonString(actualBookMap);

		assertEquals(200, StatusCode);
		assertEquals(createdBookString, actualBookString);
	}


	@Test
	public void getOneBookNegativeTest() throws EntityNotFoundException {
		LinkedHashMap<String, Object> actualBookMap = BooksServletUtilities
				.getOneBook(GetBookTestCaseStrings.bookID.get("INVALID_BOOK_ID"));
		LinkedHashMap<String, Object> expectedResponseMap = GetBookTestCaseStrings.testCases.get("noBookResponse");
		assertEquals(expectedResponseMap.toString(), actualBookMap.toString());
	}

	/**
	 * Tests for PUT Methods
	 */

	@Test
	public void updateBookPositiveTest() throws EntityNotFoundException {

		// Creating a book with valid but wrong details that are to be updated
		LinkedHashMap<String, Object> validBookMapBeforeUpdate = UpdateBookTestCaseStrings.testCases
				.get("validBookBeforeUpdate");

		LinkedHashMap<String, Object> createdBookBeforeUpdate = createBookInTestEnv(validBookMapBeforeUpdate);
		LinkedHashMap<String, Object> strippedMapBeforeUpdate = stripBookInTestEnv(createdBookBeforeUpdate);
		String bookIDBeforeUpdate = strippedMapBeforeUpdate.get("BOOK_ID").toString();
		int codeBeforeUpdate = Integer.parseInt(strippedMapBeforeUpdate.get("STATUS_CODE").toString());

		String createdBookStringBeforeUpdate = BooksServletUtilities.mapToJsonString(createdBookBeforeUpdate);

		LinkedHashMap<String, Object> actualBookMapBeforeUpdate = BooksServletUtilities
				.getOneBook(bookIDBeforeUpdate);
		String actualBookStringBeforeUpdate = BooksServletUtilities.mapToJsonString(actualBookMapBeforeUpdate);

		assertEquals(200, codeBeforeUpdate);
		assertEquals(createdBookStringBeforeUpdate, actualBookStringBeforeUpdate);

		// Updating the book with valid and correct details
		LinkedHashMap<String, Object> validBookMapAfterUpdate = UpdateBookTestCaseStrings.testCases
				.get("validBookAfterUpdate");
		String createdBookStringAfterUpdate = BooksServletUtilities.mapToJsonString(validBookMapAfterUpdate);
		LinkedHashMap<String, Object> actualBookMapAfterUpdate = BooksServletUtilities
				.updateBook(createdBookStringAfterUpdate, strippedMapBeforeUpdate.get("BOOK_ID").toString());
		LinkedHashMap<String, Object> strippedMapAfterUpdate = stripBookInTestEnv(actualBookMapAfterUpdate);
		String bookIDAfterUpdate = strippedMapAfterUpdate.get("BOOK_ID").toString();
		int codeAfterUpdate = Integer.parseInt(strippedMapAfterUpdate.get("STATUS_CODE").toString());

		String actualBookStringAfterUpdate = BooksServletUtilities.mapToJsonString(actualBookMapAfterUpdate);

		assertEquals(200, codeAfterUpdate);
		assertEquals(createdBookStringAfterUpdate, actualBookStringAfterUpdate);

		assertEquals(bookIDAfterUpdate, bookIDBeforeUpdate);

	}

	@Test
	public void updateBookNegativeTest() throws EntityNotFoundException {
		// Creating a book with valid but wrong details that are to be updated
		LinkedHashMap<String, Object> validBookMapBeforeUpdate = UpdateBookTestCaseStrings.testCases
				.get("validBookBeforeUpdate");

		LinkedHashMap<String, Object> createdBookBeforeUpdate = createBookInTestEnv(validBookMapBeforeUpdate);
		LinkedHashMap<String, Object> strippedMapBeforeUpdate = stripBookInTestEnv(createdBookBeforeUpdate);

		String createdBookStringBeforeUpdate = BooksServletUtilities.mapToJsonString(createdBookBeforeUpdate);

		LinkedHashMap<String, Object> actualBookMapBeforeUpdate = BooksServletUtilities
				.getOneBook(strippedMapBeforeUpdate.get("BOOK_ID").toString());
		String actualBookStringBeforeUpdate = BooksServletUtilities.mapToJsonString(actualBookMapBeforeUpdate);

		assertEquals(createdBookStringBeforeUpdate, actualBookStringBeforeUpdate);

		// Updating the book with valid and correct details
		LinkedHashMap<String, Object> validBookMapAfterUpdate = UpdateBookTestCaseStrings.testCases
				.get("validBookAfterUpdate");
		String createdBookStringAfterUpdate = BooksServletUtilities.mapToJsonString(validBookMapAfterUpdate);
		LinkedHashMap<String, Object> actualBookMapAfterUpdate = BooksServletUtilities.updateBook(
				createdBookStringAfterUpdate, UpdateBookTestCaseStrings.bookID.get("INVALID_BOOK_ID").toString());
		LinkedHashMap<String, Object> expectedResponseMap = GetBookTestCaseStrings.testCases.get("noBookResponse");
		System.out.println(expectedResponseMap);

		String actualBookStringAfterUpdate = actualBookMapAfterUpdate.toString();
		assertEquals(expectedResponseMap.toString(), actualBookStringAfterUpdate);
	}


}

