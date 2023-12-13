package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class App 
{
    static String url = "jdbc:mysql://localhost:3306/sakila";
    static String user = "username";
    static String password = "password";


    static BasicDataSource dataSource;
    public static void main(String[] args){

        dataSource = new BasicDataSource();

        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        Scanner scan = new Scanner(System.in);

    System.out.println("Enter the last name of the actor/actress: ");
    String lastName = scan.next().toUpperCase();

    try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Actor WHERE last_name= ?");
             ){

            preparedStatement.setString(1,lastName);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    System.out.println("First Name: " + rs.getString("first_name"));
                    System.out.println("Last Name: " + rs.getString("last_name"));
                    System.out.println("-------------------------------------");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("To find a movie by actor/actress:" +
                "\nEnter the first name: ");
        String firstname = scan.next().toUpperCase();
        System.out.println("Enter the last name: ");
        String lastname = scan.next().toUpperCase();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Actor JOIN film_actor ON Actor.actor_id= film_actor.actor_id JOIN film ON film_actor.film_id=film.film_id WHERE last_name= ? AND first_name=?;");
        ){preparedStatement.setString(1,lastname);
            preparedStatement.setString(2,firstname);

            try(ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Title: " + rs.getString("film.title"));
                    System.out.println("-------------------------------------");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
