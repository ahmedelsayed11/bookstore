package com.domains;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String Author;
	private String title;
	private String publisher;
	private  String PublicationDate;
	private String langauge;
	private String category;
	private int NumberOfPages;
	private String format;
	private int ISBN;
	private double ShippingWeight;
	private double ListPrice;
	private double OurPrice;
	
	private boolean active;
	private int inStockNumber;
	
	//to upload Multiplephotos
	@Transient
	private MultipartFile bookImage;
	
	@Column(columnDefinition = "text")
   private String Description;
	
	public book() {}
	
	//Setter and Getter
   public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPublicationDate() {
		return PublicationDate;
	}
	public void setPublicationDate(String publicationDate) {
		PublicationDate = publicationDate;
	}
	public String getLangauge() {
		return langauge;
	}
	public void setLangauge(String langauge) {
		this.langauge = langauge;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getNumberOfPages() {
		return NumberOfPages;
	}
	public void setNumberOfPages(int numberOfPages) {
		NumberOfPages = numberOfPages;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public int getISBN() {
		return ISBN;
	}
	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}
	public double getShippingWeight() {
		return ShippingWeight;
	}
	public void setShippingWeight(double shippingWeight) {
		ShippingWeight = shippingWeight;
	}
	public double getListPrice() {
		return ListPrice;
	}
	public void setListPrice(double listPrice) {
		ListPrice = listPrice;
	}
	public double getOurPrice() {
		return OurPrice;
	}
	public void setOurPrice(double ourPrice) {
		OurPrice = ourPrice;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public int getInStockNumber() {
		return inStockNumber;
	}
	public void setInStockNumber(int inStockNumber) {
		this.inStockNumber = inStockNumber;
	}
	public MultipartFile getBookImage() {
		return bookImage;
	}
	public void setBookImage(MultipartFile bookImage) {
		this.bookImage = bookImage;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
