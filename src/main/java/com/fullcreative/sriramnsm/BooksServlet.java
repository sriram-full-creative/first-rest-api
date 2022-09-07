package com.fullcreative.sriramnsm;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet(name = "BooksServlet", urlPatterns = { "/books", "/books/*" })
public class BooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
			if (BooksServletUtilities.isOneBook(request.getRequestURI())) {
				String bookID = BooksServletUtilities.getBookKeyFromUri(request);
				Map<String, Object> responseMap = new LinkedHashMap<>();
				responseMap = BooksServletUtilities.getOneBook(bookID);
				int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
				String responseAsJson = new Gson().toJson(responseMap);
				response.setContentType("application/json");
				response.getWriter().println(responseAsJson);
				response.setStatus(code);
			} else {
				String arrayOfBooks = BooksServletUtilities.getAllBooks();
				response.setContentType("application/json");
				response.getWriter().println(arrayOfBooks);
				response.setStatus(200);
			}
		} catch (Exception e) {
			System.out.println("Caught in doGet servlet service method");
			e.printStackTrace();
			response.setStatus(500);
			Map<String, String> internalServerErrorMap = new LinkedHashMap<String, String>() {
				{
					put("500", "Something went wrong");
				}
			};
			String internalServerError = new Gson().toJson(internalServerErrorMap);
			response.getWriter().println(internalServerError);
		}
	}


	/**
	 * @AcceptedEndpoint /books
	 * @ServiceMethodNote Creates a New Book Entity
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String jsonString = BooksServletUtilities.payloadAsStringFromRequest(request);
			Map<String, Object> responseMap = new LinkedHashMap<>();
			responseMap = BooksServletUtilities.createBook(jsonString);
			int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
			if (responseMap.containsKey("BOOK_ID")) {
				String key = responseMap.remove("BOOK_ID").toString();
			}
			String responseAsJson = new Gson().toJson(responseMap);
			response.setContentType("application/json");
			response.getWriter().print(responseAsJson);
			response.setStatus(code);
		} catch (Exception e) {
			System.out.println("Caught in doPost servlet service method");
			e.printStackTrace();
			response.setStatus(500);
			Map<String, String> internalServerErrorMap = new LinkedHashMap<String, String>() {
				{
					put("500", "Something went wrong");
				}
			};
			String internalServerError = new Gson().toJson(internalServerErrorMap);
			response.getWriter().println(internalServerError);
		}
	}

	/**
	 * @AcceptedEndpoint /books/{bookEntityID}
	 * @ServiceMethodNote Updates the book entity with ID = bookEntityID
	 */

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String jsonString = BooksServletUtilities.payloadAsStringFromRequest(request);
			String BookID = BooksServletUtilities.getBookKeyFromUri(request);
			Map<String, Object> responseMap = new LinkedHashMap<>();
			responseMap = BooksServletUtilities.updateBook(jsonString, BookID);
			int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
			if (responseMap.containsKey("BOOK_ID")) {
				String key = responseMap.remove("BOOK_ID").toString();
			}
			String responseAsJson = new Gson().toJson(responseMap);
			response.setContentType("application/json");
			response.getWriter().print(responseAsJson);
			response.setStatus(code);
		} catch (Exception e) {
			System.out.println("Caught in doPut servlet service method");
			e.printStackTrace();
			response.setStatus(500);
			Map<String, String> internalServerErrorMap = new LinkedHashMap<String, String>() {
				{
					put("500", "Something went wrong");
				}
			};
			String internalServerError = new Gson().toJson(internalServerErrorMap);
			response.getWriter().println(internalServerError);
		}
	}
	
	/**
	 * @AcceptedEndpoint /books/{bookEntityID}
	 * @ServiceMethodNote Deletes the book entity with ID = bookEntityID
	 */

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String bookID = BooksServletUtilities.getBookKeyFromUri(request);
			LinkedHashMap<String, Object> responseMap = new LinkedHashMap<>();
			responseMap = BooksServletUtilities.deleteBook(bookID);
			int code = Integer.parseInt(responseMap.remove("STATUS_CODE").toString());
			String responseAsJson = new Gson().toJson(responseMap);
			response.setContentType("application/json");
			response.getWriter().print(responseAsJson);
			response.setStatus(code);
		} catch (Exception e) {
			System.out.println("Caught in doDelete servlet service method");
			e.printStackTrace();
			response.setStatus(500);
			Map<String, String> internalServerErrorMap = new LinkedHashMap<String, String>() {
				{
					put("500", "Something went wrong");
				}
			};
			String internalServerError = new Gson().toJson(internalServerErrorMap);
			response.getWriter().println(internalServerError);
		}
	}
}

