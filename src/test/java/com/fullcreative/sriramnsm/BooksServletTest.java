package com.fullcreative.sriramnsm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class BooksServletTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}


	/**
	 * Request Response Tests - BooksServlet().doGet() service method
	 * 
	 * @throws Exception
	 */

	@Test
	public void requestContentTypePlainTextResponseContentTypeJsonDoGetAllBooksTest()
			throws ServletException, IOException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		mockRequest.setContentType("text/plain");
		mockRequest.setRequestURI("/books");
		new BooksServlet().doGet(mockRequest, mockResponse);
		Assert.assertEquals("application/json", mockResponse.getContentType());
	}

	@Test
	public void requestContentTypeJsonResponseContentTypeJsonDoGetAllBooksTest() throws ServletException, IOException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		mockRequest.setContentType("application/json");
		mockRequest.setRequestURI("/books");
		new BooksServlet().doGet(mockRequest, mockResponse);
		Assert.assertEquals("application/json", mockResponse.getContentType());
	}

	@Test
	public void requestContentTypePlainTextResponseContentTypeJsonDoGetOneBookTest()
			throws ServletException, IOException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		mockRequest.setContentType("text/plain");
		mockRequest.setRequestURI("/books/bookKey");
		new BooksServlet().doGet(mockRequest, mockResponse);
		Assert.assertEquals("application/json", mockResponse.getContentType());
	}

	@Test
	public void requestContentTypeJsonResponseContentTypeJsonDoGetOneBookTest() throws ServletException, IOException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		mockRequest.setContentType("application/json");
		mockRequest.setRequestURI("/books/bookKey");
		new BooksServlet().doGet(mockRequest, mockResponse);
		Assert.assertEquals("application/json", mockResponse.getContentType());
	}



}
