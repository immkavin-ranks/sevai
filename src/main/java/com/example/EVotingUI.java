package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class EVotingUI extends JFrame implements ActionListener {
    private static final String DATABASE_NAME = "sevai";
    private static final String COLLECTION_NAME = "voters";

    private JTextField voterIdField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public EVotingUI() {
        setTitle("E-Voting Authority");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel voterIDLabel = new JLabel("Voter ID:");
        voterIdField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        panel.add(voterIDLabel);
        panel.add(voterIdField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {
            String voterId = voterIdField.getText();
            String password = new String(passwordField.getPassword());
            // Connect to MongoDB server
            try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
                // Access the database
                MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

                // Access the collection
                MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

                // Perform login validation using MongoDB
                if (isValidLogin(voterId, password, collection)) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    // Proceed to the next screen or action
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == registerButton) {
            // Perform registration logic here
            JOptionPane.showMessageDialog(this, "Registration functionality not implemented yet!");
        }
    }

    private boolean isValidLogin(String voterId, String password, MongoCollection<Document> collection) {
        // Query the database for the voterId and password
        Document query = new Document("voterId", voterId)
                .append("password", password);
        Document result = collection.find(query).first();
    
        // Return true if a document is found, false otherwise
        return result != null;
    }

    public static void main(String[] args) {
        new EVotingUI();
    }
}