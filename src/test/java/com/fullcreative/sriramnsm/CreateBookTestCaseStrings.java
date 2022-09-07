/**
 * 
 */
package com.fullcreative.sriramnsm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sriram
 *
 */
public class CreateBookTestCaseStrings {
	public static Map<String, LinkedHashMap<String, Object>> errorMessages = new LinkedHashMap<>();
	public static Map<String, LinkedHashMap<String, Object>> testCases = new LinkedHashMap<>();
	static {

		testCases.put("validBook", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "Hans Christian Andersen");
				put("country", "Denmark");
				put("imageLink", "images/fairy-tales.jpg");
				put("language", "Danish");
				put("link", "https://en.wikipedia.org/wiki/Fairy_Tales_Told_for_Children._First_Collection.");
				put("pages", 784);
				put("title", "Fairy tales");
				put("year", 1836);
			}
		});


		testCases.put("authorNameNull", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "");
				put("country", "Denmark");
				put("imageLink", "images/fairy-tales.jpg");
				put("language", "Danish");
				put("link", "https://en.wikipedia.org/wiki/Fairy_Tales_Told_for_Children._First_Collection.");
				put("pages", 784);
				put("title", "Fairy tales");
				put("year", 1836);
			}
		});

		errorMessages.put("authorNameNull", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("AUTHOR_NAME_ERROR", "Author Name should contain atleast 1 character");
			}
		});

		testCases.put("bookTitleNull", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "Hans Christian Andersen");
				put("country", "Denmark");
				put("imageLink", "images/fairy-tales.jpg");
				put("language", "Danish");
				put("link", "https://en.wikipedia.org/wiki/Fairy_Tales_Told_for_Children._First_Collection.");
				put("pages", 784);
				put("title", "");
				put("year", 1836);
			}
		});

		errorMessages.put("bookTitleNull", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("TITLE_NAME_ERROR", "Title Name should contain atleast 1 character");
			}
		});


		testCases.put("countryNull", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "Hans Christian Andersen");
				put("country", "");
				put("imageLink", "images/fairy-tales.jpg");
				put("language", "Danish");
				put("link", "https://en.wikipedia.org/wiki/Fairy_Tales_Told_for_Children._First_Collection.");
				put("pages", 784);
				put("title", "Fairy tales");
				put("year", 1836);
			}
		});

		errorMessages.put("countryNull", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("COUNTRY_FIELD_ERROR", "Country field can't be empty and should atleast have 3 characters");
			}
		});


		testCases.put("languageNull", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "Hans Christian Andersen");
				put("country", "Denmark");
				put("imageLink", "images/fairy-tales.jpg");
				put("language", "");
				put("link", "https://en.wikipedia.org/wiki/Fairy_Tales_Told_for_Children._First_Collection.");
				put("pages", 784);
				put("title", "Fairy tales");
				put("year", 1836);
			}
		});

		errorMessages.put("languageNull", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("LANGUAGE_EMPTY_ERROR", "Language field can't be empty");
			}
		});


		testCases.put("negativePages", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "Hans Christian Andersen");
				put("country", "Denmark");
				put("imageLink", "images/fairy-tales.jpg");
				put("language", "Danish");
				put("link", "https://en.wikipedia.org/wiki/Fairy_Tales_Told_for_Children._First_Collection.");
				put("pages", -784);
				put("title", "Fairy tales");
				put("year", 1836);
			}
		});

		errorMessages.put("negativePages", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("PAGES_ERROR", "Page Count should be Positive");
			}
		});


		testCases.put("minPageCount", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "Hans Christian Andersen");
				put("country", "Denmark");
				put("imageLink", "images/fairy-tales.jpg");
				put("language", "Danish");
				put("link", "https://en.wikipedia.org/wiki/Fairy_Tales_Told_for_Children._First_Collection.");
				put("pages", 7);
				put("title", "Fairy tales");
				put("year", 1836);
			}
		});

		errorMessages.put("minPageCount", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("PAGES_ERROR", "Book should have atleast 20 pages");
			}
		});


		testCases.put("negativeYear", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "Hans Christian Andersen");
				put("country", "Denmark");
				put("imageLink", "images/fairy-tales.jpg");
				put("language", "Danish");
				put("link", "https://en.wikipedia.org/wiki/Fairy_Tales_Told_for_Children._First_Collection.\\n");
				put("pages", 784);
				put("title", "Fairy tales");
				put("year", -1836);
			}
		});

		errorMessages.put("negativeYear", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("YEAR_NEGATIVE_VALUE_ERROR", "Year should be positive");
			}
		});


		testCases.put("futureYearValue", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "Hans Christian Andersen");
				put("country", "Denmark");
				put("imageLink", "images/fairy-tales.jpg");
				put("language", "Danish");
				put("link", "https://en.wikipedia.org/wiki/Fairy_Tales_Told_for_Children._First_Collection.\\n");
				put("pages", 784);
				put("title", "Fairy tales");
				put("year", 2023);
			}
		});

		errorMessages.put("futureYearValue", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("YEAR_FUTURE_VALUE_ERROR", "Year should be less than or equal to the current year -> '2022'");
			}
		});

	}

}
