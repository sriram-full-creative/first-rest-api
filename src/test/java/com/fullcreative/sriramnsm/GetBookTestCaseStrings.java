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
public class GetBookTestCaseStrings {
	public static Map<String, LinkedHashMap<String, Object>> testCases = new LinkedHashMap<>();
	public static Map<String, String> bookID = new LinkedHashMap<>();
	static {

		testCases.put("validBook1", new LinkedHashMap<String, Object>() {
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

		testCases.put("validBook2", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "Jorge Luis Borges");
				put("country", "Argentina");
				put("imageLink", "images/ficciones.jpg");
				put("language", "Spanish");
				put("link", "https://en.wikipedia.org/wiki/Ficciones");
				put("pages", 224);
				put("title", "Ficciones");
				put("year", 1965);
			}
		});

		testCases.put("validBook3", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "Chinua Achebe");
				put("country", "Nigeria");
				put("imageLink", "images/things-fall-apart.jpg");
				put("language", "English");
				put("link", "https://en.wikipedia.org/wiki/Things_Fall_Apart");
				put("pages", 209);
				put("title", "Things Fall Apart");
				put("year", 1958);
			}
		});

		testCases.put("noBookResponse", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("ERROR", "Book not Found. Invalid Key");
				put("STATUS_CODE", 404);
			}
		});

		bookID.put("INVALID_BOOK_ID", "08f38688-9de2-4682-b05b-38f40d42c4e8");

	}
}
