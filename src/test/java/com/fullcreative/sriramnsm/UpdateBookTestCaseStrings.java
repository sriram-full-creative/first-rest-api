/**
 * 
 */
package com.fullcreative.sriramnsm;

import java.util.LinkedHashMap;
import java.util.Map;


public class UpdateBookTestCaseStrings {
	public static Map<String, LinkedHashMap<String, Object>> testCases = new LinkedHashMap<>();
	public static Map<String, String> bookID = new LinkedHashMap<>();
	static {

		testCases.put("validBookBeforeUpdate", new LinkedHashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("author", "Hans Christian");
				put("country", "Denmark");
				put("imageLink", "images/fairy-tales.jpg");
				put("language", "Danish");
				put("link", "https://en.wikipedia.org/wiki/Fairy_Tales_Told_for_Children._First_Collection.");
				put("pages", 784);
				put("title", "Fairy tales");
				put("year", 1836);
			}
		});

		testCases.put("validBookAfterUpdate", new LinkedHashMap<String, Object>() {
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
