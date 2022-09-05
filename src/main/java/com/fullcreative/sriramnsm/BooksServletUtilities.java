package com.fullcreative.sriramnsm;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class BooksServletUtilities {


	public static BookData bookFromEntity(Entity entity) {
		BookData bookData = new BookData();
		bookData.setTitle(entity.getProperty("Title").toString());
		bookData.setAuthor(entity.getProperty("Author").toString());
		bookData.setCountry(entity.getProperty("Country").toString());
		bookData.setImageLink(entity.getProperty("ImageLink").toString());
		bookData.setLanguage(entity.getProperty("Language").toString());
		bookData.setLink(entity.getProperty("Link").toString());
		bookData.setYear(Integer.parseInt(entity.getProperty("Year").toString()));
		bookData.setPages(Integer.parseInt(entity.getProperty("Pages").toString()));
		return bookData;
	}


	public static List<BookData> booksFromEntities(List<Entity> entities) {
		List<BookData> books = new ArrayList<>();
		for (Entity entity : entities) {
			books.add(bookFromEntity(entity));
		}
		return books;
	}

	public static Entity entityFromBook(BookData requestBookData, String bookKeyComponent) {
		Entity entity = new Entity("Books", bookKeyComponent);
		entity.setProperty("Title", requestBookData.getTitle());
		entity.setProperty("Author", requestBookData.getAuthor());
		entity.setProperty("Country", requestBookData.getCountry());
		entity.setProperty("ImageLink", requestBookData.getImageLink());
		entity.setProperty("Language", requestBookData.getLanguage());
		entity.setProperty("Link", requestBookData.getLink());
		entity.setProperty("Year", requestBookData.getYear());
		entity.setProperty("Pages", requestBookData.getPages());
		return entity;
	}

	public static List<String> uriDecoder(String uri) {
		List<String> requestsArray = Arrays.asList(uri.split("/"));
		return requestsArray;
	}

	public static BookData requestBookDataFromRequestBody(HttpServletRequest request) throws IOException {
		StringBuffer jsonstring = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			jsonstring.append(line);
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation()
				.create();
		BookData requestBookData = (BookData) gson.fromJson(jsonstring.toString(), BookData.class);
		return requestBookData;
	}

	public static Map<Integer, JsonObject> requestValidator(BookData requestBookData) {
		Map<Integer, JsonObject> validation = new HashMap<>();
		JsonObject jsonErrorString = new JsonObject();
		Integer errorFlag = 0;
		if (requestBookData.getTitle().length() <= 0 || requestBookData.getTitle() == null) {
			jsonErrorString.addProperty("TITLE_NAME_ERROR", "Title Name should contain atleast 1 character");
			errorFlag = 1;
		}
		if (requestBookData.getAuthor().length() <= 0 || requestBookData.getAuthor() == null) {
			jsonErrorString.addProperty("AUTHOR_NAME_ERROR", "Author Name should contain atleast 1 character");
			errorFlag = 1;
		}
		if (requestBookData.getPages() <= 20) {
			if (requestBookData.getPages() < 0) {
				jsonErrorString.addProperty("PAGES_ERROR", "Page Count should be Positive");
			} else {
				jsonErrorString.addProperty("PAGES_ERROR", "Book should have atleast 20 pages");
			}
			errorFlag = 1;
		}
		if (requestBookData.getYear() <= 0) {
			jsonErrorString.addProperty("YEAR_NEGATIVE_VALUE_ERROR", "Year should be positive");
			errorFlag = 1;
		}
		if (requestBookData.getYear() > Year.now().getValue()) {
			jsonErrorString.addProperty("YEAR_FUTURE_VALUE_ERROR",
					"Year should be less than or equal to the current year -> '" + Year.now().getValue() + "'");
			errorFlag = 1;
		}
		if (requestBookData.getLanguage().length() <= 0 || requestBookData.getLanguage() == null) {
			jsonErrorString.addProperty("LANGUAGE_EMPTY_ERROR", "Language field can't be empty");
			errorFlag = 1;
		}
		if (requestBookData.getCountry().length() <= 3 || requestBookData.getCountry() == null) {
			jsonErrorString.addProperty("COUNTRY_FIELD_ERROR",
					"Country field can't be empty and should atleast have 3 characters");
			errorFlag = 1;
		}
		validation.put(errorFlag, jsonErrorString);
		return validation;
	}
}
