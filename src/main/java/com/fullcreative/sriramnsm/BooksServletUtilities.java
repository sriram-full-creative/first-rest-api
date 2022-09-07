package com.fullcreative.sriramnsm;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.time.Instant;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	 * @param book
	 * @param map
	 * @return LinkedHashMap<String, Object>
	 */
	public static LinkedHashMap<String, Object> mapFromBook(BookData book, LinkedHashMap<String, Object> map) {
		map.put("author", book.getAuthor());
		map.put("country", book.getCountry());
		map.put("imageLink", book.getImageLink());
		map.put("language", book.getLanguage());
		map.put("link", book.getLink());
		map.put("pages", book.getPages());
		map.put("title", book.getTitle());
		map.put("year", book.getYear());
		return map;
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
	 * @param errorMap
	 * @return JsonObject
	 */
	public static LinkedHashMap<String, Object> requestBookValidator(BookData requestBookData,
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
		if (requestBookData.getPages() < 20) {
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

	/**
	 * @param linkedHashMap
	 * @return String
	 */
	public static String mapToJsonString(LinkedHashMap<String, Object> linkedHashMap) {
		Gson gson = new Gson();
		Type gsonType = new TypeToken<LinkedHashMap>() {
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
		responseMap = requestBookValidator(requestBookData, responseMap);
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
			responseMap = mapFromBook(responseBookData, responseMap);
			responseMap.put("BOOK_ID", keyObj.getName());
			responseMap.put("STATUS_CODE", 200);
		} catch (Exception e) {
			System.out.println("Thrown from createBook Method");
			responseMap.put("ERROR", "Book was not created");
			responseMap.put("STATUS_CODE", 503);
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
			responseMap = mapFromBook(responseBookData, responseMap);
			responseMap.put("STATUS_CODE", 200);
		} catch (Exception e) {
			System.out.println("Caught in getOneBook method");
			e.printStackTrace();
			responseMap.put("ERROR", "Book not Found. Invalid Key");
			responseMap.put("STATUS_CODE", 404);
		}
		return responseMap;
	}

	/**
	 * @return List<LinkedHashMap<String, Object>>
	 */
	public static LinkedList<String> getAllBooks() {
		LinkedHashMap<String, Object> bookAsMap = new LinkedHashMap<String, Object>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Books").addSort("CreatedOrUpdated", SortDirection.DESCENDING);
		List<Entity> bookEntities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		List<BookData> booksFromEntities = BooksServletUtilities.booksFromEntities(bookEntities);
		LinkedList<String> books = new LinkedList<>();
		for (BookData book : booksFromEntities) {
			books.add(mapToJsonString(mapFromBook(book, bookAsMap)));
		}
		return books;
	}


	/**
	 * @param jsonInputString
	 * @param bookID
	 * @return JsonObject
	 * @throws EntityNotFoundException
	 */
	public static LinkedHashMap<String, Object> updateBook(String jsonInputString, String bookID)
			throws EntityNotFoundException {
		Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation()
				.create();
		BookData requestBookData = gson.fromJson(jsonInputString, BookData.class);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
		Entity entity = entityFromBook(requestBookData, bookID);
		try {
			datastore.get(entity.getKey());
			responseMap = requestBookValidator(requestBookData, responseMap);
			if (responseMap.size() != 0) {
				return responseMap;
			} else {
				Key keyObj = datastore.put(entity);
				Entity responseEntity = datastore.get(keyObj);
				BookData responseBookData = new BookData();
				responseBookData = bookFromEntity(responseEntity);
				responseMap = mapFromBook(responseBookData, responseMap);
				responseMap.put("BOOK_ID", keyObj.getName());
				responseMap.put("STATUS_CODE", 200);
			}
			} catch (Exception e) {
				System.out.println("Caught in updateBook method");
				e.printStackTrace();
				responseMap.put("ERROR", "Book not Found. Invalid Key");
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
			e.printStackTrace();
			responseMap.put("ERROR", "Book not Found. Invalid Key");
			responseMap.put("STATUS_CODE", 404);
		}
		return responseMap;
	}

}
