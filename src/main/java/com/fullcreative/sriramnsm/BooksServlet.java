package com.fullcreative.sriramnsm;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet(name = "BooksServlet", urlPatterns = { "/books", "/books/*" })
public class BooksServlet extends HttpServlet {
	private static final long serialVersionUID = 7365798110491894876L;
	/**
	 * @AcceptedEndpoint /books
	 * @ServiceMethodNote Fetches all the Book entities
	 * 
	 * 
	 * @AcceptedEndpoint /books/{bookEntityID}
	 * @ServiceMethodNote Fetches the book entity with ID = bookEntityID
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Map<String, Object> responseMap = new LinkedHashMap<>();
			if (BooksServletUtilities.isValidEndPoint(request.getRequestURI())) {
				if (BooksServletUtilities.hasBookKey(request.getRequestURI())) {
					String bookID = BooksServletUtilities.getBookKeyFromUri(request);
					responseMap = BooksServletUtilities.getOneBook(bookID);
					int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
					String responseAsJson = new Gson().toJson(responseMap);
					response.setContentType("application/json");
					response.getWriter().println(responseAsJson);
					response.setStatus(code);
				} else {
					LinkedList<String> arrayOfBooks = BooksServletUtilities.getAllBooks();
					response.setContentType("application/json");
					response.getWriter().println(arrayOfBooks);
					response.setStatus(200);
				}
			} else {
				responseMap = BooksServletUtilities.invalidRequestEndpoint(responseMap);
				int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
				String responseAsJson = new Gson().toJson(responseMap);
				response.setContentType("application/json");
				response.getWriter().print(responseAsJson);
				response.setStatus(code);
			}
		} catch (Exception e) {
			System.out.println("Caught in doGet servlet service method");
			e.printStackTrace();
			Map<String, String> internalServerErrorMap = new LinkedHashMap<String, String>();
			response.setContentType("application/json");
			internalServerErrorMap.put("500", "Something went wrong");
			String internalServerError = new Gson().toJson(internalServerErrorMap);
			response.getWriter().println(internalServerError);
			response.setStatus(500);
		}
	}

	/**
	 * @AcceptedEndpoint /books
	 * @ServiceMethodNote Creates a New Book Entity
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Map<String, Object> responseMap = new LinkedHashMap<>();
			if (BooksServletUtilities.hasBookKey(request.getRequestURI()) == false
					&& BooksServletUtilities.isValidEndPoint(request.getRequestURI())) {
				String jsonString = BooksServletUtilities.payloadAsStringFromRequest(request);
				responseMap = BooksServletUtilities.createBook(jsonString);
				int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
				if (responseMap.containsKey("BOOK_ID")) {
					String key = responseMap.remove("BOOK_ID").toString();
				}
				String responseAsJson = new Gson().toJson(responseMap);
				response.setContentType("application/json");
				response.getWriter().print(responseAsJson);
				response.setStatus(code);
			} else {
				responseMap = BooksServletUtilities.invalidRequestEndpoint(responseMap);
				int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
				String responseAsJson = new Gson().toJson(responseMap);
				response.setContentType("application/json");
				response.getWriter().print(responseAsJson);
				response.setStatus(code);
			}
		} catch (Exception e) {
			System.out.println("Caught in doPost servlet service method");
			e.printStackTrace();
			e.printStackTrace();
			Map<String, String> internalServerErrorMap = new LinkedHashMap<String, String>();
			response.setContentType("application/json");
			internalServerErrorMap.put("500", "Something went wrong");
			String internalServerError = new Gson().toJson(internalServerErrorMap);
			response.getWriter().println(internalServerError);
			response.setStatus(500);
		}
	}

	/**
	 * @AcceptedEndpoint /books/{bookEntityID}
	 * @ServiceMethodNote Updates the book entity with ID = bookEntityID
	 */

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Map<String, Object> responseMap = new LinkedHashMap<>();
			if (BooksServletUtilities.hasBookKey(request.getRequestURI()) == true
					&& BooksServletUtilities.isValidEndPoint(request.getRequestURI())) {
				String jsonString = BooksServletUtilities.payloadAsStringFromRequest(request);
				String BookID = BooksServletUtilities.getBookKeyFromUri(request);
				responseMap = BooksServletUtilities.updateBook(jsonString, BookID);
				int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
				if (responseMap.containsKey("BOOK_ID")) {
					String key = responseMap.remove("BOOK_ID").toString();
				}
				String responseAsJson = new Gson().toJson(responseMap);
				response.setContentType("application/json");
				response.getWriter().print(responseAsJson);
				response.setStatus(code);
			} else {
				responseMap = BooksServletUtilities.invalidRequestEndpoint(responseMap);
				int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
				String responseAsJson = new Gson().toJson(responseMap);
				response.setContentType("application/json");
				response.getWriter().print(responseAsJson);
				response.setStatus(code);
			}

		} catch (Exception e) {
			System.out.println("Caught in doPut servlet service method");
			e.printStackTrace();
			Map<String, String> internalServerErrorMap = new LinkedHashMap<String, String>();
			response.setContentType("application/json");
			internalServerErrorMap.put("500", "Something went wrong");
			String internalServerError = new Gson().toJson(internalServerErrorMap);
			response.getWriter().println(internalServerError);
			response.setStatus(500);
		}
	}
	
	/**
	 * @AcceptedEndpoint /books/{bookEntityID}
	 * @ServiceMethodNote Deletes the book entity with ID = bookEntityID
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Map<String, Object> responseMap = new LinkedHashMap<>();
			if (BooksServletUtilities.hasBookKey(request.getRequestURI()) == true
					&& BooksServletUtilities.isValidEndPoint(request.getRequestURI())) {
				String bookID = BooksServletUtilities.getBookKeyFromUri(request);
				responseMap = BooksServletUtilities.deleteBook(bookID);
				int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
				String responseAsJson = new Gson().toJson(responseMap);
				response.setContentType("application/json");
				response.getWriter().print(responseAsJson);
				response.setStatus(code);
			} else {
				responseMap = BooksServletUtilities.invalidRequestEndpoint(responseMap);
				int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
				String responseAsJson = new Gson().toJson(responseMap);
				response.setContentType("application/json");
				response.getWriter().print(responseAsJson);
				response.setStatus(code);
			}
		} catch (Exception e) {
			System.out.println("Caught in doDelete servlet service method");
			e.printStackTrace();
			Map<String, String> internalServerErrorMap = new LinkedHashMap<String, String>();
			response.setContentType("application/json");
			internalServerErrorMap.put("500", "Something went wrong");
			String internalServerError = new Gson().toJson(internalServerErrorMap);
			response.getWriter().println(internalServerError);
			response.setStatus(500);
		}
	}
}

