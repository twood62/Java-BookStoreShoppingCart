package com.pluralsight;

//Book DAO.java is just all of the methods and such related to the database
//We import these packages so that we can use the methods in them and in their libraries
// Java holds more of the backend stuff and the webapp section holds the front end jsp stuff
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

//These methods are public so they can be used when adding a book to the car
// ex- maybe you cann insertBook when you add a book to the car
public class BookDAO {
    private Connection jdbcConnection;
    public BookDAO(Connection connection)
    {
      jdbcConnection = connection;
    }

    public Book getBook(int id) {
      Book book = null;
      String sql = "SELECT * FROM book WHERE id = ?";

      try {
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        // This is saying if the DB is connected and if there are more rows of data to get, don't throw a SQL exception
        // if (resultSet.next() just means if there is a next row aka it returns true)
        if (resultSet.next()) {
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            float price = resultSet.getFloat("price");

            // Setting the retrieved data in a new book object
            book = new Book(id, title, author, price);
        }

        resultSet.close();
        statement.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }

      return book;
    }

    public ArrayList<Book> listAllBooks() {
      ArrayList<Book> listBook = new ArrayList<>();

      String sql = "SELECT * FROM book";

		  try {
			    Statement statement = jdbcConnection.createStatement();

	        ResultSet resultSet = statement.executeQuery(sql);

	        while (resultSet.next()) {
              int id = resultSet.getInt("id");
	            String title = resultSet.getString("title");
	            String author = resultSet.getString("author");
	            float price = resultSet.getFloat("price");

	            Book book = new Book(id, title, author, price);
	            listBook.add(book);
	        }

	        resultSet.close();
	        statement.close();
  		} catch (SQLException e) {
  			e.printStackTrace();
  		}
        return listBook;
    }

    public boolean insertBook(Book book)  {
        String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";

        try {
	        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	        statement.setString(1, book.getTitle());
	        statement.setString(2, book.getAuthor());
	        statement.setFloat(3, book.getPrice());

	        boolean rowInserted = statement.executeUpdate() > 0;
	        statement.close();
	        return rowInserted;
        } catch (SQLException e) {
        		e.printStackTrace();
        }

        return false;
    }

    public void deleteBook(int id) {
      String sql = "DELETE FROM book WHERE id = ?";

      try {
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();

        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    public void updateBook(Book book) {
        String sql = "UPDATE book SET title = ?, author = ?, price = ?" +
                     " WHERE id = ?";

        try {
          PreparedStatement statement = jdbcConnection.prepareStatement(sql);
          statement.setString(1, book.getTitle());
          statement.setString(2, book.getAuthor());
          statement.setFloat(3, book.getPrice());
          statement.setInt(4, book.getId());

          statement.executeUpdate();
          statement.close();
        } catch(SQLException e) {
          e.printStackTrace();
        }
    }
}
