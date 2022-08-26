package com.fullcreative.sriramnsm;

import com.google.gson.annotations.Expose;

public class ResponseBookData {
	@Expose(serialize = false, deserialize = false)
	private String bookId;
	@Expose(serialize = true, deserialize = true)
	private String author;
	@Expose(serialize = true, deserialize = true)
	private String country;
	@Expose(serialize = true, deserialize = true)
	private String imageLink;
	@Expose(serialize = true, deserialize = true)
	private String language;
	@Expose(serialize = true, deserialize = true)
	private String link;
	@Expose(serialize = true, deserialize = true)
	private int pages;
	@Expose(serialize = true, deserialize = true)
	private String title;
	@Expose(serialize = true, deserialize = true)
	private int year;

	public ResponseBookData() {
	};

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "BookData [author=" + author + ", country=" + country + ", imageLink=" + imageLink + ", language="
				+ language + ", link=" + link + ", pages=" + pages + ", title=" + title + ", year=" + year + "]";
	}

}
