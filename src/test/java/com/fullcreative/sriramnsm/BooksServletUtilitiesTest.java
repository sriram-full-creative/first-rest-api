package com.fullcreative.sriramnsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class BooksServletUtilitiesTest {

	Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();

	@Test
	public void validatorPostiveTest() {
		String jsonBookString = "{\r\n" + "    \"author\": \"Dante Alighieri\",\r\n"
				+ "    \"country\": \"Italy\",\r\n" + "    \"imageLink\": \"images/the-divine-comedy.jpg\",\r\n"
				+ "    \"language\": \"Italian\",\r\n"
				+ "    \"link\": \"https://en.wikipedia.org/wiki/Divine_Comedy\\n\",\r\n" + "    \"pages\": 928,\r\n"
				+ "    \"title\": \"The Divine Comedy\",\r\n" + "    \"year\": 1315\r\n" + "  }";
		BookData book = gson.fromJson(jsonBookString, BookData.class);
		Map<Integer, JsonObject> validation = BooksServletUtilities.requestValidator(book);
		assertEquals(null, validation.get(1));
	}

	@Test
	public void validatorAuthorNameEmptyTest() {
		String jsonBookString = "{\r\n" + "    \"author\": \"\",\r\n" + "    \"country\": \"Italy\",\r\n"
				+ "    \"imageLink\": \"images/the-divine-comedy.jpg\",\r\n" + "    \"language\": \"Italian\",\r\n"
				+ "    \"link\": \"https://en.wikipedia.org/wiki/Divine_Comedy\\n\",\r\n" + "    \"pages\": 928,\r\n"
				+ "    \"title\": \"The Divine Comedy\",\r\n" + "    \"year\": 1315\r\n" + "  }";
		BookData book = gson.fromJson(jsonBookString, BookData.class);
		Map<Integer, JsonObject> validation = BooksServletUtilities.requestValidator(book);
		assertNotNull(validation.get(1));
		assertEquals("{\"AUTHOR_NAME_ERROR\":\"Author Name should contain atleast 1 character\"}",
				validation.get(1).toString());
	}

	@Test
	public void validatorTitleNameEmptyTest() {
		String jsonBookString = "  {\r\n" + "    \"author\": \"Dante Alighieri\",\r\n"
				+ "    \"country\": \"Italy\",\r\n" + "    \"imageLink\": \"images/the-divine-comedy.jpg\",\r\n"
				+ "    \"language\": \"Italian\",\r\n"
				+ "    \"link\": \"https://en.wikipedia.org/wiki/Divine_Comedy\\n\",\r\n" + "    \"pages\": 928,\r\n"
				+ "    \"title\": \"\",\r\n" + "    \"year\": 1315\r\n" + "  }";
		BookData book = gson.fromJson(jsonBookString, BookData.class);
		Map<Integer, JsonObject> validation = BooksServletUtilities.requestValidator(book);
		assertNotNull(validation.get(1));
		assertEquals("{\"TITLE_NAME_ERROR\":\"Title Name should contain atleast 1 character\"}",
				validation.get(1).toString());
	}

	@Test
	public void validatorPagesNegativeCountTest() {
		String jsonBookString = "  {\r\n" + "    \"author\": \"Dante Alighieri\",\r\n"
				+ "    \"country\": \"Italy\",\r\n" + "    \"imageLink\": \"images/the-divine-comedy.jpg\",\r\n"
				+ "    \"language\": \"Italian\",\r\n"
				+ "    \"link\": \"https://en.wikipedia.org/wiki/Divine_Comedy\\n\",\r\n" + "    \"pages\": -928,\r\n"
				+ "    \"title\": \"The Divine Comedy\",\r\n" + "    \"year\": 1315\r\n" + "  }";
		BookData book = gson.fromJson(jsonBookString, BookData.class);
		Map<Integer, JsonObject> validation = BooksServletUtilities.requestValidator(book);
		assertNotNull(validation.get(1));
		assertEquals("{\"PAGES_ERROR\":\"Page Count should be Positive\"}",
				validation.get(1).toString());
	}

	@Test
	public void validatorPagesLessThanTwentyTest() {
		String jsonBookString = "  {\r\n" + "    \"author\": \"Dante Alighieri\",\r\n"
				+ "    \"country\": \"Italy\",\r\n" + "    \"imageLink\": \"images/the-divine-comedy.jpg\",\r\n"
				+ "    \"language\": \"Italian\",\r\n"
				+ "    \"link\": \"https://en.wikipedia.org/wiki/Divine_Comedy\\n\",\r\n" + "    \"pages\": 19,\r\n"
				+ "    \"title\": \"The Divine Comedy\",\r\n" + "    \"year\": 1315\r\n" + "  }";
		BookData book = gson.fromJson(jsonBookString, BookData.class);
		Map<Integer, JsonObject> validation = BooksServletUtilities.requestValidator(book);
		assertNotNull(validation.get(1));
		assertEquals("{\"PAGES_ERROR\":\"Book should have atleast 20 pages\"}",
				validation.get(1).toString());
	}

	@Test
	public void validatorYearNegativeValueTest() {
		String jsonBookString = "  {\r\n" + "    \"author\": \"Dante Alighieri\",\r\n"
				+ "    \"country\": \"Italy\",\r\n" + "    \"imageLink\": \"images/the-divine-comedy.jpg\",\r\n"
				+ "    \"language\": \"Italian\",\r\n"
				+ "    \"link\": \"https://en.wikipedia.org/wiki/Divine_Comedy\\n\",\r\n" + "    \"pages\": 928,\r\n"
				+ "    \"title\": \"The Divine Comedy\",\r\n" + "    \"year\": -1315\r\n" + "  }";
		BookData book = gson.fromJson(jsonBookString, BookData.class);
		Map<Integer, JsonObject> validation = BooksServletUtilities.requestValidator(book);
		assertNotNull(validation.get(1));
		assertEquals("{\"YEAR_NEGATIVE_VALUE_ERROR\":\"Year should be positive\"}",
				validation.get(1).toString());
	}

	@Test
	public void validatorYearValueInFutureTest() {
		String jsonBookString = "  {\r\n" + "    \"author\": \"Dante Alighieri\",\r\n"
				+ "    \"country\": \"Italy\",\r\n" + "    \"imageLink\": \"images/the-divine-comedy.jpg\",\r\n"
				+ "    \"language\": \"Italian\",\r\n"
				+ "    \"link\": \"https://en.wikipedia.org/wiki/Divine_Comedy\\n\",\r\n" + "    \"pages\": 928,\r\n"
				+ "    \"title\": \"The Divine Comedy\",\r\n" + "    \"year\": 2024\r\n" + "  }";
		BookData book = gson.fromJson(jsonBookString, BookData.class);
		Map<Integer, JsonObject> validation = BooksServletUtilities.requestValidator(book);
		assertNotNull(validation.get(1));
		assertEquals(
				"{\"YEAR_FUTURE_VALUE_ERROR\":\"Year should be less than or equal to the current year -> '2022'\"}",
				validation.get(1).toString());
	}

	@Test
	public void validatorCountryEmptyTest() {
		String jsonBookString = "  {\r\n" + "    \"author\": \"Dante Alighieri\",\r\n" + "    \"country\": \"\",\r\n"
				+ "    \"imageLink\": \"images/the-divine-comedy.jpg\",\r\n" + "    \"language\": \"Italian\",\r\n"
				+ "    \"link\": \"https://en.wikipedia.org/wiki/Divine_Comedy\\n\",\r\n" + "    \"pages\": 928,\r\n"
				+ "    \"title\": \"The Divine Comedy\",\r\n" + "    \"year\": 1315\r\n" + "  }";
		BookData book = gson.fromJson(jsonBookString, BookData.class);
		Map<Integer, JsonObject> validation = BooksServletUtilities.requestValidator(book);
		assertNotNull(validation.get(1));
		assertEquals("{\"COUNTRY_FIELD_ERROR\":\"Country field can't be empty and should atleast have 3 characters\"}",
				validation.get(1).toString());
	}

	@Test
	public void validatorLanguageEmptyTest() {
		String jsonBookString = "  {\r\n" + "    \"author\": \"Dante Alighieri\",\r\n"
				+ "    \"country\": \"Italy\",\r\n" + "    \"imageLink\": \"images/the-divine-comedy.jpg\",\r\n"
				+ "    \"language\": \"\",\r\n"
				+ "    \"link\": \"https://en.wikipedia.org/wiki/Divine_Comedy\\n\",\r\n" + "    \"pages\": 928,\r\n"
				+ "    \"title\": \"The Divine Comedy\",\r\n" + "    \"year\": 1315\r\n" + "  }";
		BookData book = gson.fromJson(jsonBookString, BookData.class);
		Map<Integer, JsonObject> validation = BooksServletUtilities.requestValidator(book);
		assertNotNull(validation.get(1));
		assertEquals("{\"LANGUAGE_EMPTY_ERROR\":\"Language field can't be empty\"}",
				validation.get(1).toString());

	}

}
