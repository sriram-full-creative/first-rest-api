package com.fullcreative.sriramnsm;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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

@WebServlet(name = "BooksServlet", urlPatterns = { "/books", "/books/*" })
public class BooksServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	/**
	 * Endpoints and their responses => 1. /books (Fetches all the books from the
	 * datastore) 2. /books/{key} (Fetches the detail of the book {key}).
	 */

	Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {

			/**
			 * Processing the request URI to find the endpoint to which the request was
			 * sent.
			 */
			List<String> requestList = BooksServletUtilities.uriDecoder(request.getRequestURI());

			if (requestList.size() == 2) {
				Query query = new Query();
				List<Entity> bookEntities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
				List<ResponseBookData> books = BooksServletUtilities.booksFromEntities(bookEntities);
				response.getWriter().println(gson.toJson(books));
			} else if (requestList.size() == 3) {

				String bookKeyComponent = requestList.get(requestList.size() - 1);

				/**
				 * Creating an Entity Object from the request to get the key.
				 */
				Key bookKey = KeyFactory.createKey("Books", bookKeyComponent);
				Entity responseEntity = datastore.get(bookKey);
				ResponseBookData responseBookData = BooksServletUtilities.bookFromEntity(responseEntity);
				/**
				 * Formatting the response as a JSON
				 */
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
		 * Processing the StringBuffer() object into actual JSON using the GSON library.
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
			Map<Integer, JsonObject> validator = BooksServletUtilities.requestValidator(requestBookData);
			if (validator.containsKey(1)) {
				response.setContentType("application/json");
				response.getWriter().println(validator.get(1).toString());
				response.setStatus(400);
				return;
			}
			/**
			 * Constructing an Entity out of the data received post validation.
			 */
			String bookID = UUID.randomUUID().toString();
			Entity entity = BooksServletUtilities.entityFromBook(requestBookData, bookID);
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
			responseBookData = BooksServletUtilities.bookFromEntity(responseEntity);
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
		 * Processing the StringBuffer() object into actual JSON using the GSON library.
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
			String bookID = requestsArray[requestsArray.length - 1];

			/**
			 * Validating the data after creating the POJO before updating it into the
			 * entity
			 */
			Map<Integer, JsonObject> validator = BooksServletUtilities.requestValidator(requestBookData);
			if (validator.containsKey(1)) {
				response.setContentType("application/json");
				response.getWriter().println(validator.get(1).toString());
				response.setStatus(400);
				return;
			}

			/**
			 * Constructing an Entity out of the data received.
			 */
			Entity entity = BooksServletUtilities.entityFromBook(requestBookData, bookID);
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
			responseBookData = BooksServletUtilities.bookFromEntity(responseEntity);

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
			String bookKeyComponent = requestsArray[requestsArray.length - 1];

			/**
			 * Constructing an Entity out of the data received.
			 */
			Key entityKey = KeyFactory.createKey("Books", bookKeyComponent);
			datastore.delete(entityKey);
			response.setStatus(204);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(404);
		}
	}


}
