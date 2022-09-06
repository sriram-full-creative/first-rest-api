/**
 * 
 */
package com.fullcreative.sriramnsm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sriram
 *
 */
public class TestCaseStrings {
	public static Map<String, String> testCases = new HashMap<>();
	public static Map<String, String> errorMessages = new HashMap<>();
	static {
		/**
		 * Valid Test Case CreateBook
		 */
		testCases.put("validBook",
				"{\"author\":\"Jane Austen\",\"country\":\"United Kingdom\",\"imageLink\":\"images/pride-and-prejudice.jpg\",\"language\":\"English\",\"link\":\"https://en.wikipedia.org/wiki/Pride_and_Prejudice\\n\",\"pages\":226,\"title\":\"Pride and Prejudice\",\"year\":1813}");

		/**
		 * 
		 * @TestPass Invalid
		 * @TestCase Author Name Null
		 */
		testCases.put("authorNameNull",
				"{\"author\":\"\",\"country\":\"United Kingdom\",\"imageLink\":\"images/pride-and-prejudice.jpg\",\"language\":\"English\",\"link\":\"https://en.wikipedia.org/wiki/Pride_and_Prejudice\\n\",\"pages\":226,\"title\":\"Pride and Prejudice\",\"year\":1813}");

		errorMessages.put("authorNameNull",
				"{\"AUTHOR_NAME_ERROR\":\"Author Name should contain atleast 1 character\"}");

		/**
		 * @TestPass Invalid
		 * @TestCase Book Title Null
		 */
		testCases.put("bookTitleNull",
				"{\"author\":\"Jane Austen\",\"country\":\"United Kingdom\",\"imageLink\":\"images/pride-and-prejudice.jpg\",\"language\":\"English\",\"link\":\"https://en.wikipedia.org/wiki/Pride_and_Prejudice\\n\",\"pages\":226,\"title\":\"\",\"year\":1813}");
		errorMessages.put("bookTitleNull", "{\"TITLE_NAME_ERROR\":\"Title Name should contain atleast 1 character\"}");

		/**
		 * @TestPass Invalid
		 * @TestCase Country Null
		 */
		testCases.put("countryNull",
				"{\"author\":\"Jane Austen\",\"country\":\"\",\"imageLink\":\"images/pride-and-prejudice.jpg\",\"language\":\"English\",\"link\":\"https://en.wikipedia.org/wiki/Pride_and_Prejudice\\n\",\"pages\":226,\"title\":\"Pride and Prejudice\",\"year\":1813}");
		errorMessages.put("countryNull",
				"{\"COUNTRY_FIELD_ERROR\":\"Country field can't be empty and should atleast have 3 characters\"}");

		/**
		 * @TestPass Invalid
		 * @TestCase Language Null
		 */
		testCases.put("languageNull",
				"{\"author\":\"Jane Austen\",\"country\":\"United Kingdom\",\"imageLink\":\"images/pride-and-prejudice.jpg\",\"language\":\"\",\"link\":\"https://en.wikipedia.org/wiki/Pride_and_Prejudice\\n\",\"pages\":226,\"title\":\"Pride and Prejudice\",\"year\":1813}");
		errorMessages.put("languageNull", "{\"LANGUAGE_EMPTY_ERROR\":\"Language field can't be empty\"}");

		/**
		 * @TestPass Invalid
		 * @TestCase Negative Pages Value
		 */
		testCases.put("negativePages",
				"{\"author\":\"Jane Austen\",\"country\":\"United Kingdom\",\"imageLink\":\"images/pride-and-prejudice.jpg\",\"language\":\"English\",\"link\":\"https://en.wikipedia.org/wiki/Pride_and_Prejudice\\n\",\"pages\":-226,\"title\":\"Pride and Prejudice\",\"year\":1813}");
		errorMessages.put("negativePages", "{\"PAGES_ERROR\":\"Page Count should be Positive\"}");

		/**
		 * @TestPass Invalid
		 * @TestCase Page Count Less than twenty
		 */
		testCases.put("minPageCount",
				"{\"author\":\"Jane Austen\",\"country\":\"United Kingdom\",\"imageLink\":\"images/pride-and-prejudice.jpg\",\"language\":\"English\",\"link\":\"https://en.wikipedia.org/wiki/Pride_and_Prejudice\\n\",\"pages\":19,\"title\":\"Pride and Prejudice\",\"year\":1813}");
		errorMessages.put("minPageCount", "{\"PAGES_ERROR\":\"Book should have atleast 20 pages\"}");

		/**
		 * @TestPass Invalid
		 * @TestCase Negative Year Value
		 */
		testCases.put("negativeYear",
				"{\"author\":\"Jane Austen\",\"country\":\"United Kingdom\",\"imageLink\":\"images/pride-and-prejudice.jpg\",\"language\":\"English\",\"link\":\"https://en.wikipedia.org/wiki/Pride_and_Prejudice\\n\",\"pages\":226,\"title\":\"Pride and Prejudice\",\"year\":-1813}");
		errorMessages.put("negativeYear", "{\"YEAR_NEGATIVE_VALUE_ERROR\":\"Year should be positive\"}");

		/**
		 * @TestPass Invalid
		 * @TestCase Year Future Value
		 */
		testCases.put("futureYearValue",
				"{\"author\":\"Jane Austen\",\"country\":\"United Kingdom\",\"imageLink\":\"images/pride-and-prejudice.jpg\",\"language\":\"English\",\"link\":\"https://en.wikipedia.org/wiki/Pride_and_Prejudice\\n\",\"pages\":226,\"title\":\"Pride and Prejudice\",\"year\":2023}");
		errorMessages.put("futureYearValue",
				"{\"YEAR_FUTURE_VALUE_ERROR\":\"Year should be less than or equal to the current year -> '2022'\"}");
	}

}
