package product;

import org.apache.hadoop.hbase.util.Bytes;

public class Book {
	
	private String productId  = null ;
	private String productName  = null ;
	private String author = null ;
	private String pubdate  = null ;
	private String press	= null ;
	private String nameSrc = null ;
	private String serials = null ;
	private String language = null ;
	private String format = null ;
	private String brand = null ;
	private String isbn = null ;
	private String size = null ;
	private String weight = null ;
	private String imageUrl = null ;
	private String asin = null ;
	private String sort  = null ;
	
	public Book(){}
	public Book(byte[] bookId ,byte[] productName, byte[] author, byte[] pubdate, byte[] press, byte[] nameSrc, byte[] serials,
			byte[] language, byte[] format, byte[] brand, byte[] isbn, byte[] size, byte[] weight, byte[] imageUrl,
			byte[] sort) {
		super();
		if(bookId!=null) this.productId =  Bytes.toString(bookId);
		if(productName!=null) this.productName = Bytes.toString(bookId);
		if(author!=null) this.author =  Bytes.toString(author);
		if(pubdate!=null) this.pubdate =  Bytes.toString(pubdate);
		if(press!=null) this.press =  Bytes.toString(press);
		if(nameSrc!=null) this.nameSrc =  Bytes.toString(nameSrc );
		if(serials!=null) this.serials =  Bytes.toString(serials);
		if(language!=null) this.language =  Bytes.toString(language);
		if(format!=null) this.format =  Bytes.toString(format);
		if(brand!=null) this.brand =  Bytes.toString(brand);
		if(isbn!=null) this.isbn =  Bytes.toString(isbn);
		if(size!=null) this.size =  Bytes.toString(size);
		if(weight!=null) this.weight =  Bytes.toString(weight);
		if(imageUrl!=null) this.imageUrl =  Bytes.toString(imageUrl);
		if(sort!=null) this.sort =  Bytes.toString(sort) ;
	}
	public Book(String productId ,String productName, String author, String pubdate, String press, String nameSrc, String serials,
			String language, String format, String brand, String isbn, String size, String weight, String imageUrl,
			String asin , String sort) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.author = author;
		this.pubdate = pubdate;
		this.press = press;
		this.nameSrc = nameSrc;
		this.serials = serials;
		this.language = language;
		this.format = format;
		this.brand = brand;
		this.isbn = isbn;
		this.size = size;
		this.weight = weight;
		this.imageUrl = imageUrl;
		this.asin = asin;
		this.sort = sort ;
	}
	public Book(String productName, String author, String pubdate, String press, String nameSrc, String serials,
			String language, String format, String brand, String isbn, String size, String weight, String imageUrl,
			String asin , String sort) {
		super();
		this.productName = productName;
		this.author = author;
		this.pubdate = pubdate;
		this.press = press;
		this.nameSrc = nameSrc;
		this.serials = serials;
		this.language = language;
		this.format = format;
		this.brand = brand;
		this.isbn = isbn;
		this.size = size;
		this.weight = weight;
		this.imageUrl = imageUrl;
		this.asin = asin;
		this.sort = sort ;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getNameSrc() {
		return nameSrc;
	}

	public void setNameSrc(String nameSrc) {
		this.nameSrc = nameSrc;
	}

	public String getSerials() {
		return serials;
	}

	public void setSerials(String serials) {
		this.serials = serials;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
