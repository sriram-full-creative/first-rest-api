package com.fullcreative.sriramnsm;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.time.Instant;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class BooksServletUtilities {
	/**
	 * @param request
	 * @return BookData
	 * @throws IOException
	 */

	public static BookData requestPayloadToPojo(HttpServletRequest request) throws IOException {
		return stringBufferToPojo(payloadAsStringBufferFromRequest(request));
	}

	/**
	 * @param request
	 * @return StringBuffer
	 * @throws IOException
	 */
	public static StringBuffer payloadAsStringBufferFromRequest(HttpServletRequest request) throws IOException {
		StringBuffer jsonString = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			jsonString.append(line);
		}
		return jsonString;
	}

	/**
	 * @param request
	 * @return String
	 * @throws IOException
	 */
	public static String payloadAsStringFromRequest(HttpServletRequest request) throws IOException {
		StringBuffer jsonString = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			jsonString.append(line);
		}
		return jsonString.toString();
	}

	/**
	 * @param jsonstring
	 * @return BookData
	 * @throws JsonSyntaxException
	 */
	public static BookData stringBufferToPojo(StringBuffer jsonstring) throws JsonSyntaxException {
		Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation()
				.create();
		BookData requestBookData = (BookData) gson.fromJson(jsonstring.toString(), BookData.class);
		return requestBookData;
	}

	/**
	 * @param entity
	 * @return BookData
	 */
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


	/**
	 * @param entities
	 * @return List<BookData>
	 */
	public static List<BookData> booksFromEntities(List<Entity> entities) {
		List<BookData> books = new ArrayList<>();
		for (Entity entity : entities) {
			books.add(bookFromEntity(entity));
		}
		return books;
	}

	/**
	 * @param requestBookData
	 * @param bookKeyComponent
	 * @return Entity
	 */
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
		entity.setProperty("CreatedOrUpdated", Time.from(Instant.now()));
		return entity;
	}

	/**
	 * @param uri
	 * @return List<String>
	 */
	public static List<String> uriDecoder(String uri) {
		List<String> requestsArray = Arrays.asList(uri.split("/"));
		return requestsArray;
	}

	/**
	 * @param uri
	 * @return boolean
	 */
	public static boolean isOneBook(String uri) {
		List<String> requestsArray = Arrays.asList(uri.split("/"));
		Integer count = requestsArray.size();
		if (count == 3) {
			return true;
		} else {
			return false;
		}
	}



	/**
	 * @param request
	 * @return String
	 */
	public static String getBookKeyFromUri(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		String[] requestsArray = requestUri.split("/");
		String bookID = requestsArray[requestsArray.length - 1];
		return bookID;
	}

	/**
	 * @param request
	 * @return BookData
	 * @throws IOException
	 */
	public static BookData requestBookDataFromRequestBody(HttpServletRequest request) throws IOException {
		StringBuffer jsonstring = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			jsonstring.append(line);
		}
		Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation()
				.create();
		BookData requestBookData = (BookData) gson.fromJson(jsonstring.toString(), BookData.class);
		return requestBookData;
	}

	/**
	 * @param requestBookData
	 * @return Map<Integer, JsonObject>
	 */
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

	/**
	 * @param requestBookData
	 * @param jsonErrorString
	 * @return String
	 */
	public static String requestValidatorString(BookData requestBookData, JsonObject jsonErrorString) {
		int flag = 0;
		if (requestBookData.getTitle().length() <= 0 || requestBookData.getTitle() == null) {
			jsonErrorString.addProperty("TITLE_NAME_ERROR", "Title Name should contain atleast 1 character");
			flag = 1;
		}
		if (requestBookData.getAuthor().length() <= 0 || requestBookData.getAuthor() == null) {
			jsonErrorString.addProperty("AUTHOR_NAME_ERROR", "Author Name should contain atleast 1 character");
			flag = 1;
		}
		if (requestBookData.getPages() <= 20) {
			if (requestBookData.getPages() < 0) {
				jsonErrorString.addProperty("PAGES_ERROR", "Page Count should be Positive");
				flag = 1;
			} else {
				jsonErrorString.addProperty("PAGES_ERROR", "Book should have atleast 20 pages");
				flag = 1;
			}
		}
		if (requestBookData.getYear() <= 0) {
			jsonErrorString.addProperty("YEAR_NEGATIVE_VALUE_ERROR", "Year should be positive");
			flag = 1;
		}
		if (requestBookData.getYear() > Year.now().getValue()) {
			jsonErrorString.addProperty("YEAR_FUTURE_VALUE_ERROR",
					"Year should be less than or equal to the current year -> '" + Year.now().getValue() + "'");
			flag = 1;
		}
		if (requestBookData.getLanguage().length() <= 0 || requestBookData.getLanguage() == null) {
			jsonErrorString.addProperty("LANGUAGE_EMPTY_ERROR", "Language field can't be empty");
			flag = 1;
		}
		if (requestBookData.getCountry().length() <= 3 || requestBookData.getCountry() == null) {
			jsonErrorString.addProperty("COUNTRY_FIELD_ERROR",
					"Country field can't be empty and should atleast have 3 characters");
			flag = 1;
		}
		if (flag == 1) {
			jsonErrorString.addProperty("STATUS_CODE", 400);
		}
		return jsonErrorString.toString();
	}

	/**
	 * @param requestBookData
	 * @param errorMap
	 * @return JsonObject
	 */
	public static LinkedHashMap<String, Object> requestValidatorObject(BookData requestBookData,
			LinkedHashMap<String, Object> errorMap) {
		int flag = 0;
		if (requestBookData.getTitle().length() <= 0 || requestBookData.getTitle() == null) {
			errorMap.put("TITLE_NAME_ERROR", "Title Name should contain atleast 1 character");
			flag = 1;
		}
		if (requestBookData.getAuthor().length() <= 0 || requestBookData.getAuthor() == null) {
			errorMap.put("AUTHOR_NAME_ERROR", "Author Name should contain atleast 1 character");
			flag = 1;
		}
		if (requestBookData.getPages() <= 20) {
			if (requestBookData.getPages() < 0) {
				errorMap.put("PAGES_ERROR", "Page Count should be Positive");
				flag = 1;
			} else {
				errorMap.put("PAGES_ERROR", "Book should have atleast 20 pages");
				flag = 1;
			}
		}
		if (requestBookData.getYear() <= 0) {
			errorMap.put("YEAR_NEGATIVE_VALUE_ERROR", "Year should be positive");
			flag = 1;
		}
		if (requestBookData.getYear() > Year.now().getValue()) {
			errorMap.put("YEAR_FUTURE_VALUE_ERROR",
					"Year should be less than or equal to the current year -> '" + Year.now().getValue() + "'");
			flag = 1;
		}
		if (requestBookData.getLanguage().length() <= 0 || requestBookData.getLanguage() == null) {
			errorMap.put("LANGUAGE_EMPTY_ERROR", "Language field can't be empty");
			flag = 1;
		}
		if (requestBookData.getCountry().length() <= 3 || requestBookData.getCountry() == null) {
			errorMap.put("COUNTRY_FIELD_ERROR",
					"Country field can't be empty and should atleast have 3 characters");
			flag = 1;
		}
		if (flag == 1) {
			errorMap.put("STATUS_CODE", 400);
		}
		return errorMap;
	}

	public static String mapToJsonString(LinkedHashMap<String, Object> linkedHashMap) {
		Gson gson = new Gson();
		Type gsonType = new TypeToken<HashMap>() {
		}.getType();
		String gsonString = gson.toJson(linkedHashMap, gsonType);
		BookData book = gson.fromJson(gsonString, BookData.class);
		String jsonString = gson.toJson(book);
		return jsonString;
	}

	/**
	 * @param jsonInputString
	 * @return JsonObject
	 * @throws EntityNotFoundException
	 */


	public static LinkedHashMap<String, Object> createBook(String jsonInputString) throws EntityNotFoundException {
		Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation()
				.create();
		BookData requestBookData = gson.fromJson(jsonInputString, BookData.class);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
		responseMap = requestValidatorObject(requestBookData, responseMap);
//		System.out.println(responseMap);
		if (responseMap.size() != 0) {
			return responseMap;
		} else {
			String bookID = UUID.randomUUID().toString();
			Entity entity = entityFromBook(requestBookData, bookID);
			Key keyObj = datastore.put(entity);
			try {
			Entity responseEntity = datastore.get(keyObj);
			BookData responseBookData = new BookData();
			responseBookData = bookFromEntity(responseEntity);
			String responseAsJson = gson.toJson(responseBookData);
			responseMap = new Gson().fromJson(responseAsJson, LinkedHashMap.class);
			Float pagesFloatValue = Float.parseFloat(responseMap.get("pages").toString());
			Float yearFloatValue = Float.parseFloat(responseMap.get("year").toString());
			int pages = pagesFloatValue.intValue();
			int year = yearFloatValue.intValue();
			responseMap.replace("pages", pages);
			responseMap.replace("year", year);
			responseMap.put("BOOK_ID", keyObj.getName());
			responseMap.put("STATUS_CODE", 200);
		} catch (Exception e) {
			System.out.println("Thrown from createBook Method");
			responseMap.put("ERROR", "Book was not created");
			e.printStackTrace();
		}
			return responseMap;
		}
	}

	/**
	 * @param bookID
	 * @return String
	 * @throws EntityNotFoundException
	 */
	public static LinkedHashMap<String, Object> getOneBook(String bookID) throws EntityNotFoundException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key bookKey = KeyFactory.createKey("Books", bookID);
		LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
		try {
			Entity responseEntity = datastore.get(bookKey);
			BookData responseBookData = BooksServletUtilities.bookFromEntity(responseEntity);
			Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
			String responseAsJson = gson.toJson(responseBookData);

			responseMap = new Gson().fromJson(responseAsJson, LinkedHashMap.class);
			Float pagesFloatValue = Float.parseFloat(responseMap.get("pages").toString());
			Float yearFloatValue = Float.parseFloat(responseMap.get("year").toString());
			int pages = pagesFloatValue.intValue();
			int year = yearFloatValue.intValue();
			responseMap.replace("pages", pages);
			responseMap.replace("year", year);
			responseMap.put("STATUS_CODE", 200);
		} catch (Exception e) {
			System.out.println("Caught in getOneBook method");
			e.printStackTrace();
			responseMap.put("ERROR", "Book not Found, Invalid Key");
			responseMap.put("STATUS_CODE", 404);
		}
		return responseMap;
	}

	/**
	 * @return String
	 */
	public static String getAllBooks() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query();
		List<Entity> bookEntities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		List<BookData> books = BooksServletUtilities.booksFromEntities(bookEntities);
		Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation()
				.create();
		String jsonStringResponse = gson.toJson(books);
		return jsonStringResponse;
	}

	public static List<LinkedHashMap<String, Object>> getAllBooksAsList() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query();
		List<Entity> bookEntities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		List<BookData> books = BooksServletUtilities.booksFromEntities(bookEntities);
		Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation()
				.create();
		String jsonStringResponse = gson.toJson(books);
		List<LinkedHashMap<String, Object>> responseJsonArray = new Gson().fromJson(jsonStringResponse, List.class);
		return responseJsonArray;
	}


	/**
	 * @param jsonInputString
	 * @param bookID
	 * @return JsonObject
	 * @throws EntityNotFoundException
	 */
	public static LinkedHashMap<String, Object> UpdateBook(String jsonInputString, String bookID)
			throws EntityNotFoundException {
		Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation()
				.create();
		BookData requestBookData = gson.fromJson(jsonInputString, BookData.class);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
		Entity entity = entityFromBook(requestBookData, bookID);
		try {
			Entity checkEntity = datastore.get(entity.getKey());
			responseMap = requestValidatorObject(requestBookData, responseMap);
			if (responseMap.size() != 0) {
				return responseMap;
			} else {
				Key keyObj = datastore.put(entity);
				Entity responseEntity = datastore.get(keyObj);
				BookData responseBookData = new BookData();
				responseBookData = bookFromEntity(responseEntity);
				String responseAsJson = gson.toJson(responseBookData);
				responseMap = new Gson().fromJson(responseAsJson, LinkedHashMap.class);
				Float pagesFloatValue = Float.parseFloat(responseMap.get("pages").toString());
				Float yearFloatValue = Float.parseFloat(responseMap.get("year").toString());
				int pages = pagesFloatValue.intValue();
				int year = yearFloatValue.intValue();
				responseMap.replace("pages", pages);
				responseMap.replace("year", year);
				responseMap.put("BOOK_ID", keyObj.getName());
				responseMap.put("STATUS_CODE", 200);
			}
			} catch (Exception e) {
				System.out.println("Caught in updateBook method");
				e.printStackTrace();
				responseMap.put("ERROR", "Book not Found, Invalid Key");
				responseMap.put("STATUS_CODE", 404);
			}
			return responseMap;
		}

	/**
	 * @param bookID
	 * @throws EntityNotFoundException
	 */
	public static LinkedHashMap<String, Object> deleteBook(String bookID) throws EntityNotFoundException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key entityKey = KeyFactory.createKey("Books", bookID);
		LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
		try {
			datastore.get(entityKey);
			datastore.delete(entityKey);
			responseMap.put("SUCCESS", "Book was deleted");
			responseMap.put("STATUS_CODE", 200);
		} catch (Exception e) {
			System.out.println("Caught in deleteBook Method");
			responseMap.put("ERROR", "Book not Found, Invalid Key");
			responseMap.put("STATUS_CODE", 404);
		}
		return responseMap;
	}

}
