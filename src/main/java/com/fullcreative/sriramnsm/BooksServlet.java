package com.fullcreative.sriramnsm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

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
				String book = BooksServletUtilities.getOneBook(bookID);
				response.setContentType("application/json");
				response.getWriter().println(book);
			} else {
				String books = BooksServletUtilities.getAllBooks();
				response.setContentType("application/json");
				response.getWriter().println(books);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(404);
		}
	}


	/**
	 * @AcceptedEndpoint /books
	 * @ServiceMethodNote Creates a New Book Entity
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String jsonString = BooksServletUtilities.payloadAsStringFromRequest(request);
			JsonObject responseAsJsonObject = BooksServletUtilities.createBook(jsonString);
			int code = responseAsJsonObject.remove("STATUS_CODE").getAsInt();
			response.setContentType("application/json");
			response.getWriter().print(responseAsJsonObject);
			response.setStatus(code);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
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
			JsonObject responseAsJsonObject = BooksServletUtilities.UpdateBook(jsonString, BookID);
			int code = responseAsJsonObject.remove("STATUS_CODE").getAsInt();
			response.setContentType("application/json");
			response.getWriter().print(responseAsJsonObject);
			response.setStatus(code);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
		}
	}
	
	/**
	 * @AcceptedEndpoint /books/{bookEntityID}
	 * @ServiceMethodNote Deletes the book entity with ID = bookEntityID
	 */

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String bookID = BooksServletUtilities.getBookKeyFromUri(request);
			BooksServletUtilities.deleteBook(bookID);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(404);
		}
	}
}

