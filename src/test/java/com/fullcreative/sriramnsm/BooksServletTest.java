package com.fullcreative.sriramnsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.LinkedHashMap;
import java.util.LinkedList;

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

	private LinkedHashMap<String, Object> createAndStrip(LinkedHashMap<String, Object> ValidTestCaseMap)
			throws EntityNotFoundException {
		LinkedHashMap<String, Object> createdBook = createBookInTestEnv(ValidTestCaseMap);
		return createdBook;
	}

	/**
	 * Tests for POST Methods
	 */
	@Test
	public void validBookcreateBookTest() throws EntityNotFoundException {
		System.out.println();
		System.out.println("validBookcreateBookTest()");
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
		System.out.println();
		System.out.println("authorNameNullcreateBookTest()");
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
		System.out.println();
		System.out.println("bookTitleNullcreateBookTest()");
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
		System.out.println();
		System.out.println("countryNullcreateBookTest()");
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
		System.out.println();
		System.out.println("languageNullcreateBookTest()");
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
		System.out.println();
		System.out.println("negativePagescreateBookTest()");
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
		System.out.println();
		System.out.println("minPageCountcreateBookTest()");
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
		System.out.println();
		System.out.println("negativeYearcreateBookTest()");
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
		System.out.println();
		System.out.println("futureYearValuecreateBookTest()");
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
	public void getAllBooksTest() throws EntityNotFoundException {
		System.out.println();
		System.out.println("getAllBooksTest()");
		LinkedHashMap<String, Object> validBook1Map = GetBookTestCaseStrings.testCases.get("validBook1");
		LinkedHashMap<String, Object> createdBook1 = createAndStrip(validBook1Map);
		String createdBook1String = BooksServletUtilities.mapToJsonString(createdBook1);
		LinkedHashMap<String, Object> validBook2Map = GetBookTestCaseStrings.testCases.get("validBook2");
		LinkedHashMap<String, Object> createdBook2 = createAndStrip(validBook2Map);
		String createdBook2String = BooksServletUtilities.mapToJsonString(createdBook2);
		LinkedHashMap<String, Object> validBook3Map = GetBookTestCaseStrings.testCases.get("validBook3");
		LinkedHashMap<String, Object> createdBook3 = createAndStrip(validBook3Map);
		String createdBook3String = BooksServletUtilities.mapToJsonString(createdBook3);
		// Construction the expected array
		LinkedList<String> expectedArrayOfBooks = new LinkedList<>();
		expectedArrayOfBooks.add(createdBook3String);
		expectedArrayOfBooks.add(createdBook2String);
		expectedArrayOfBooks.add(createdBook1String);
		// Calling getAllBooks
		LinkedList<String> actualArrayOfBooks = BooksServletUtilities.getAllBooks();
		assertEquals(expectedArrayOfBooks, actualArrayOfBooks);
	}

	@Test
	public void getOneBookPositiveTest() throws EntityNotFoundException {
		System.out.println();
		System.out.println("getOneBookPositiveTest()");

		LinkedHashMap<String, Object> validBookMap = GetBookTestCaseStrings.testCases.get("validBook1");
		// Creating a book using test utility method
		LinkedHashMap<String, Object> createdBook = createBookInTestEnv(validBookMap);
		LinkedHashMap<String, Object> strippedMap = stripBookInTestEnv(createdBook);
		int StatusCode = Integer.parseInt(strippedMap.get("STATUS_CODE").toString());
		String createdBookString = BooksServletUtilities.mapToJsonString(createdBook);
		// Calling getOneBook
		LinkedHashMap<String, Object> actualBookMap = BooksServletUtilities
				.getOneBook(strippedMap.get("BOOK_ID").toString());
		String actualBookString = BooksServletUtilities.mapToJsonString(actualBookMap);

		assertEquals(200, StatusCode);
		assertEquals(createdBookString, actualBookString);
	}


	@Test
	public void getOneBookNegativeTest() throws EntityNotFoundException {
		System.out.println();
		System.out.println("getOneBookNegativeTest()");
		// Calling getOneBook
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
		System.out.println();
		System.out.println("updateBookPositiveTest()");

		// Creating a book with valid but wrong details that are to be updated
		LinkedHashMap<String, Object> validBookMapBeforeUpdate = UpdateBookTestCaseStrings.testCases
				.get("validBookBeforeUpdate");
		// Creating a book using test utility method
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
		// Calling updatBook
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
		System.out.println();
		System.out.println("updateBookNegativeTest()");

		// Creating a book with valid but wrong details that are to be updated
		LinkedHashMap<String, Object> validBookMapBeforeUpdate = UpdateBookTestCaseStrings.testCases
				.get("validBookBeforeUpdate");
		// Creating a book using test utility method
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
		// Calling updatBook
		LinkedHashMap<String, Object> actualBookMapAfterUpdate = BooksServletUtilities.updateBook(
				createdBookStringAfterUpdate, UpdateBookTestCaseStrings.bookID.get("INVALID_BOOK_ID").toString());
		LinkedHashMap<String, Object> expectedResponseMap = GetBookTestCaseStrings.testCases.get("noBookResponse");
		System.out.println(expectedResponseMap);

		String actualBookStringAfterUpdate = actualBookMapAfterUpdate.toString();
		assertEquals(expectedResponseMap.toString(), actualBookStringAfterUpdate);
	}

	@Test
	public void deleteBookPositiveTest() throws EntityNotFoundException {
		System.out.println();
		System.out.println("deleteBookPositiveTest()");

		// Creating a book with valid details
		LinkedHashMap<String, Object> validBookMapBeforeDelete = DeleteBookTestCaseStrings.testCases.get("validBook");
		// Creating a book using test utility method
		LinkedHashMap<String, Object> createdBookBeforeDelete = createBookInTestEnv(validBookMapBeforeDelete);
		LinkedHashMap<String, Object> strippedMapBeforeDelete = stripBookInTestEnv(createdBookBeforeDelete);
		String bookIDBeforeDelete = strippedMapBeforeDelete.get("BOOK_ID").toString();
		int codeBeforeDelete = Integer.parseInt(strippedMapBeforeDelete.get("STATUS_CODE").toString());

		String createdBookStringBeforeDelete = BooksServletUtilities.mapToJsonString(createdBookBeforeDelete);

		LinkedHashMap<String, Object> actualBookMapBeforeDelete = BooksServletUtilities.getOneBook(bookIDBeforeDelete);
		String actualBookStringBeforeDelete = BooksServletUtilities.mapToJsonString(actualBookMapBeforeDelete);

		assertEquals(200, codeBeforeDelete);
		assertEquals(createdBookStringBeforeDelete, actualBookStringBeforeDelete);

		// Deleting the book with Correct Book ID
		LinkedHashMap<String, Object> expectedResponseMapAfterDelete = DeleteBookTestCaseStrings.testCases
				.get("responseAfterSuccessfullDelete");
		// Calling deleteBook
		LinkedHashMap<String, Object> actualResponseMapAfterDelete = BooksServletUtilities
				.deleteBook(strippedMapBeforeDelete.get("BOOK_ID").toString());
		System.out.println(actualResponseMapAfterDelete);
		System.out.println(expectedResponseMapAfterDelete);
		LinkedHashMap<String, Object> strippedMapAfterDelete = stripBookInTestEnv(actualResponseMapAfterDelete);
		int codeAfterDelete = Integer.parseInt(strippedMapAfterDelete.get("STATUS_CODE").toString());

		assertEquals(expectedResponseMapAfterDelete, actualResponseMapAfterDelete);
		assertEquals(200, codeAfterDelete);
	}

	@Test
	public void deleteBookNegativeTest() throws EntityNotFoundException {
		System.out.println();
		System.out.println("deleteBookNegativeTest()");

		// Creating a book with valid details
		LinkedHashMap<String, Object> validBookMapBeforeDelete = DeleteBookTestCaseStrings.testCases.get("validBook");
		// Creating a book using test utility method
		LinkedHashMap<String, Object> createdBookBeforeDelete = createBookInTestEnv(validBookMapBeforeDelete);
		LinkedHashMap<String, Object> strippedMapBeforeDelete = stripBookInTestEnv(createdBookBeforeDelete);
		String bookIDBeforeDelete = strippedMapBeforeDelete.get("BOOK_ID").toString();
		int codeBeforeDelete = Integer.parseInt(strippedMapBeforeDelete.get("STATUS_CODE").toString());

		String createdBookStringBeforeDelete = BooksServletUtilities.mapToJsonString(createdBookBeforeDelete);

		LinkedHashMap<String, Object> actualBookMapBeforeDelete = BooksServletUtilities.getOneBook(bookIDBeforeDelete);
		String actualBookStringBeforeDelete = BooksServletUtilities.mapToJsonString(actualBookMapBeforeDelete);

		assertEquals(200, codeBeforeDelete);
		assertEquals(createdBookStringBeforeDelete, actualBookStringBeforeDelete);

		// Deleting the book with Wrong Book ID
		LinkedHashMap<String, Object> expectedResponseMapAfterDelete = DeleteBookTestCaseStrings.testCases
				.get("noBookResponse");
		String wrongBookId = DeleteBookTestCaseStrings.bookID.get("INVALID_BOOK_ID");
		// Calling deleteBook
		LinkedHashMap<String, Object> actualResponseMapAfterDelete = BooksServletUtilities
				.deleteBook(wrongBookId);
		System.out.println(actualResponseMapAfterDelete);
		System.out.println(expectedResponseMapAfterDelete);
		LinkedHashMap<String, Object> strippedMapAfterDelete = stripBookInTestEnv(actualResponseMapAfterDelete);
		int codeAfterDelete = Integer.parseInt(strippedMapAfterDelete.get("STATUS_CODE").toString());

		assertNotEquals(bookIDBeforeDelete, wrongBookId);
		assertEquals(expectedResponseMapAfterDelete, actualResponseMapAfterDelete);
		assertEquals(404, codeAfterDelete);
	}

}

