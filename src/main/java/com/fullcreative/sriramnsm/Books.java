package com.fullcreative.sriramnsm;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@WebServlet(name = "Books", urlPatterns = { "/books", "/books/*" })
public class Books extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	/**
	 * Endpoints and their responses => 1. /books (Fetches all the books from the
	 * datastore) 2. /books/{key} (Fetches the detail of the book {key}).
	 */

	Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {

			/**
			 * Processing the request URI to find the endpoint to which the request was
			 * sent.
			 */
			String requestUri = request.getRequestURI();
			String[] requestsArray = requestUri.split("/");

			/**
			 * If the endpoint was /books the length of the @requestArray will be 2 and
			 * whole datastore will be returned.
			 */
			if (requestsArray.length == 2) {
				Query query = new Query();
				List<Entity> bookEntities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
				response.setContentType("application/json");
				response.getWriter().println("[");
				for (Entity responseEntity : bookEntities) {
					ResponseBookData responseBookData = new ResponseBookData();
					responseBookData.setTitle(responseEntity.getProperty("Title").toString());
					responseBookData.setAuthor(responseEntity.getProperty("Author").toString());
					responseBookData.setCountry(responseEntity.getProperty("Country").toString());
					responseBookData.setImageLink(responseEntity.getProperty("ImageLink").toString());
					responseBookData.setLanguage(responseEntity.getProperty("Language").toString());
					responseBookData.setLink(responseEntity.getProperty("Link").toString());
					responseBookData.setYear(Integer.parseInt(responseEntity.getProperty("Year").toString()));
					responseBookData.setPages(Integer.parseInt(responseEntity.getProperty("Pages").toString()));

					/**
					 * Formatting the response as a JSON
					 */
					response.getWriter().print(gson.toJson(responseBookData));
					response.getWriter().println(",");

				}
				response.getWriter().println("]");
				/**
				 * If the endpoint was /books/{key} the length of the @requestArray will be 3
				 * and the {key} entity will be returned.
				 */

			} else if (requestsArray.length == 3) {
//				long bookKeyComponent = Long.parseLong(requestsArray[requestsArray.length - 1]);

				String bookKeyComponent = requestsArray[requestsArray.length - 1];

				/**
				 * Creating an Entity Object from the request to get the key.
				 */
				Key bookKey = KeyFactory.createKey("Books", bookKeyComponent);
				Entity responseEntity = datastore.get(bookKey);
				ResponseBookData responseBookData = new ResponseBookData();
				responseBookData.setTitle(responseEntity.getProperty("Title").toString());
				responseBookData.setAuthor(responseEntity.getProperty("Author").toString());
				responseBookData.setCountry(responseEntity.getProperty("Country").toString());
				responseBookData.setImageLink(responseEntity.getProperty("ImageLink").toString());
				responseBookData.setLanguage(responseEntity.getProperty("Language").toString());
				responseBookData.setLink(responseEntity.getProperty("Link").toString());
				responseBookData.setYear(Integer.parseInt(responseEntity.getProperty("Year").toString()));
				responseBookData.setPages(Integer.parseInt(responseEntity.getProperty("Pages").toString()));

				/**
				 * Formatting the response as a JSON
				 */
				response.setContentType("application/json");
				response.getWriter().println(gson.toJson(responseBookData));
				response.setStatus(200);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(404);
		}
	}

	/**
	 * Endpoint => /books with the details of the {key} entity sent in the request
	 * body(Creates the {key} entity).
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Processing the request data as a stream using BufferedReader() and building a
		 * string using the StringBuffer().
		 */
		StringBuffer jsonstring = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			jsonstring.append(line);
		}

		/**
		 * Processing the StringBuilder() object into actual JSON using the GSON
		 * library.
		 * 
		 * @line47 Deserializing the JSON data into BookData POJO.
		 * 
		 */
		RequestBookData requestBookData = (RequestBookData) gson.fromJson(jsonstring.toString(), RequestBookData.class);

		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			/**
			 * Validating the data after creating the POJO before updating it into the
			 * entity
			 */
			Integer errorFlag = 0;
			JsonObject jsonErrorString = new JsonObject();
			if (requestBookData.getTitle().length() <= 0 || requestBookData.getTitle() == null) {
				jsonErrorString.addProperty("TITLE_NAME_ERROR", "Title Name should contain atleast 1 character");
				errorFlag = 1;
			}
			if (requestBookData.getAuthor().length() <= 0 || requestBookData.getAuthor() == null) {
				jsonErrorString.addProperty("AUTHOR_NAME_ERROR", "Author Name should contain atleast 1 character");
				errorFlag = 1;
			}
			if (requestBookData.getPages() <= 20) {
				jsonErrorString.addProperty("PAGES_ERROR", "Book should have atleast 20 pages");
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
			if (errorFlag.intValue() == 1) {
				response.setContentType("application/json");
				response.getWriter().println(jsonErrorString.toString());
				response.setStatus(400);
				return;
			}


			/**
			 * Constructing an Entity out of the data received post validation.
			 */
			String bookID = UUID.randomUUID().toString();
			Entity entity = new Entity("Books", bookID);
			entity.setProperty("Title", requestBookData.getTitle());
			entity.setProperty("Author", requestBookData.getAuthor());
			entity.setProperty("Country", requestBookData.getCountry());
			entity.setProperty("ImageLink", requestBookData.getImageLink());
			entity.setProperty("Language", requestBookData.getLanguage());
			entity.setProperty("Link", requestBookData.getLink());
			entity.setProperty("Year", requestBookData.getYear());
			entity.setProperty("Pages", requestBookData.getPages());

			/**
			 * Updating the datastore. Storing the key from the entity updated to serve back
			 * as a response.
			 */

			Key obj = datastore.put(entity);

			/**
			 * Fetching the updated entity and building a POJO to serve back the response.
			 */
			Entity responseEntity = datastore.get(obj);
			ResponseBookData responseBookData = new ResponseBookData();
			responseBookData.setTitle(responseEntity.getProperty("Title").toString());
			responseBookData.setAuthor(responseEntity.getProperty("Author").toString());
			responseBookData.setCountry(responseEntity.getProperty("Country").toString());
			responseBookData.setImageLink(responseEntity.getProperty("ImageLink").toString());
			responseBookData.setLanguage(responseEntity.getProperty("Language").toString());
			responseBookData.setLink(responseEntity.getProperty("Link").toString());
			responseBookData.setYear(Integer.parseInt(responseEntity.getProperty("Year").toString()));
			responseBookData.setPages(Integer.parseInt(responseEntity.getProperty("Pages").toString()));

			/**
			 * Formatting the response as a JSON
			 */
			response.setContentType("application/json");
			response.getWriter().println(gson.toJson(responseBookData));

			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
		}

	}

	/**
	 * Endpoint => /books/{key} (Updates the {key} entity).
	 */

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/**
		 * Processing the request data as a stream using BufferedReader() and building a
		 * string using the StringBuffer().
		 */
		StringBuffer jsonstring = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			jsonstring.append(line);
		}

		/**
		 * Processing the StringBuilder() object into actual JSON using the GSON
		 * library.
		 * 
		 * @line47 Deserializing the JSON data into BookData POJO.
		 * 
		 */
		RequestBookData requestBookData = (RequestBookData) gson.fromJson(jsonstring.toString(), RequestBookData.class);
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			/**
			 * Processing the request URL to find the entity to updated.
			 */

			String requestUri = request.getRequestURI();
			String[] requestsArray = requestUri.split("/");
			String bookKeyComponent = requestsArray[requestsArray.length - 1];

			/**
			 * Validating the data after creating the POJO before updating it into the
			 * entity
			 */
			Integer errorFlag = 0;
			JsonObject jsonErrorString = new JsonObject();
			if (requestBookData.getTitle().length() <= 0 || requestBookData.getTitle() == null) {
				jsonErrorString.addProperty("TITLE_NAME_ERROR", "Title Name should contain atleast 1 character");
				errorFlag = 1;
			}
			if (requestBookData.getAuthor().length() <= 0 || requestBookData.getAuthor() == null) {
				jsonErrorString.addProperty("AUTHOR_NAME_ERROR", "Author Name should contain atleast 1 character");
				errorFlag = 1;
			}
			if (requestBookData.getPages() <= 20) {
				jsonErrorString.addProperty("PAGES_ERROR", "Book should have atleast 20 pages");
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
			if (errorFlag.intValue() == 1) {
				response.setContentType("application/json");
				response.getWriter().println(jsonErrorString.toString());
				response.setStatus(400);
				return;
			}

			/**
			 * Constructing an Entity out of the data received.
			 */
			Entity entity = new Entity("Books", bookKeyComponent);
			entity.setProperty("Title", requestBookData.getTitle());
			entity.setProperty("Author", requestBookData.getAuthor());
			entity.setProperty("Country", requestBookData.getCountry());
			entity.setProperty("ImageLink", requestBookData.getImageLink());
			entity.setProperty("Language", requestBookData.getLanguage());
			entity.setProperty("Link", requestBookData.getLink());
			entity.setProperty("Year", requestBookData.getYear());
			entity.setProperty("Pages", requestBookData.getPages());

			/**
			 * Updating the datastore. Storing the key from the entity updated to serve back
			 * as a response.
			 */

			Key obj = datastore.put(entity);

			/**
			 * Fetching the updated entity and building a POJO to serve back the response.
			 */
			Entity responseEntity = datastore.get(obj);
			ResponseBookData responseBookData = new ResponseBookData();
			responseBookData.setTitle(responseEntity.getProperty("Title").toString());
			responseBookData.setAuthor(responseEntity.getProperty("Author").toString());
			responseBookData.setCountry(responseEntity.getProperty("Country").toString());
			responseBookData.setImageLink(responseEntity.getProperty("ImageLink").toString());
			responseBookData.setLanguage(responseEntity.getProperty("Language").toString());
			responseBookData.setLink(responseEntity.getProperty("Link").toString());
			responseBookData.setYear(Integer.parseInt(responseEntity.getProperty("Year").toString()));
			responseBookData.setPages(Integer.parseInt(responseEntity.getProperty("Pages").toString()));


			/**
			 * Formatting the response as a JSON
			 */
			response.setContentType("application/json");
			response.getWriter().println(gson.toJson(responseBookData));
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
		}
	}

	/**
	 * Endpoint => /books/{key} (Deletes the {key} entity).
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {

			/**
			 * Processing the request URI to find the entity to deleted.
			 */
			String requestUri = request.getRequestURI();
			String[] requestsArray = requestUri.split("/");
//			long bookKey = Long.parseLong(requestsArray[requestsArray.length - 1]);
			String bookKeyComponent = requestsArray[requestsArray.length - 1];

			/**
			 * Constructing an Entity out of the data received.
			 */
			Key entityKey = KeyFactory.createKey("Books", bookKeyComponent);
			Entity responseEntity = datastore.get(entityKey);
			datastore.delete(entityKey);
			response.setStatus(204);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(404);
		}
	}


}
