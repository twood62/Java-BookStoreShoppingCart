package com.pluralsight;

//Lines 4- 12 are the class declaration of cartItem
public class CartItem {
	// bringing in the book class in the class declaration
	private Book book;
    private int quantity;

    public CartItem(Book book, int quantity) {
      this.book = book;
      this.quantity = quantity;
    }

    public String getTitle() {
        return book.getTitle();
    }
    public String getAuthor() {
        return book.getAuthor();
    }
    public float getPrice() {
        return book.getPrice();
    }
    public int getQuantity() {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public float getTotalCost() {
        return book.getPrice()*this.quantity;
    }
}
