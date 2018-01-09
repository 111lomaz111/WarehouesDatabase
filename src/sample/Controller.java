package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.sql.*;

public class Controller
{

    private Connection conn;
    private Statement stat;
    private ResultSet result;

    ObservableList<String> tableList = FXCollections.observableArrayList("Instrumenty Smyczkowe", "Instrumenty dete", "Sprzet naglosnieniowy", "Akcesoria");

    @FXML
    private ComboBox tableListBox;

    @FXML
    private ListView returnFromDBListView;

    public Controller()
    {
        returnFromDBListView = new ListView<>();

        try
        {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/macias", "root", "");
            stat = conn.createStatement();

            System.out.println("Connected to database!");
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ ex);
            System.out.println("Cannot connect to database!");
        }
    }

    @FXML
    private void getData()
    {
        try
        {
            String query = "select * from testtable";
            result = stat.executeQuery(query);
            System.out.println("Data from database:");
            while(result.next())
            {
                String userID = result.getString("idUser");
                String username = result.getString("Username");
                System.out.println("User ID: " + userID + " " + "Username: " + username);
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ ex);
            System.out.println("Cannot get data from database!");
        }
    }
    @FXML
    private void AddValuesToCB()
    {
        tableListBox.setItems(tableList);
    }

    @FXML
    private void getDataFromDatabase()
    {
        returnFromDBListView.getItems().clear();

        if(tableListBox.getValue().equals("Instrumenty Smyczkowe"))
        {
            try
            {
                String query = "select * from instrumenty_smyczkowe";
                result = stat.executeQuery(query);
                System.out.println("Data from database:");
                while(result.next())
                {
                    Integer ID = result.getInt("idInsSmy");
                    String name = result.getString("InstrumentNazwa");
                    Integer amount = result.getInt("Ilosc");
                    System.out.println("ID Instrumentu: " + ID + " " + "Nazwa instrumentu: " + name + " Ilosc instrumentow: " + amount);

                    returnFromDBListView.getItems().addAll("ID Instrumentu: " + ID + "\t Nazwa instrumentu: " + name + "\t Ilosc instrumentow: " + amount);
                    returnFromDBListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                }
            }
            catch(Exception ex)
            {
                System.out.println("Error: "+ ex);
                System.out.println("Cannot get data from database!");
            }
        }
        else if (tableListBox.getValue().equals("Instrumenty dete"))
        {
            try
            {
                String query = "select * from instrumenty_dete";
                result = stat.executeQuery(query);
                System.out.println("Data from database:");
                while(result.next())
                {
                    String ID = result.getString("idInsDet");
                    String name = result.getString("InstrumentNazwa");
                    Integer amount = result.getInt("Ilosc");
                    System.out.println("ID Instrumentu: " + ID + " " + "Nazwa instrumentu: " + name + " Ilosc instrumentow: " + amount);

                    returnFromDBListView.getItems().addAll("ID Instrumentu: " + ID + " " + "Nazwa instrumentu: " + name + " Ilosc instrumentow: " + amount);
                    returnFromDBListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                }
            }
            catch(Exception ex)
            {
                System.out.println("Error: "+ ex);
                System.out.println("Cannot get data from database!");
            }
        }
        else if (tableListBox.getValue().equals("Sprzet naglosnieniowy"))
        {
            try
            {
                String query = "select * from sprzet_naglosnieniowy";
                result = stat.executeQuery(query);
                System.out.println("Data from database:");
                while(result.next())
                {
                    String ID = result.getString("idSprzetu");
                    String name = result.getString("NazwaSprzetu");
                    Integer amount = result.getInt("Ilosc");
                    System.out.println("ID Sprzetu: " + ID + " " + "Nazwa sprzetu: " + name + " Ilosc: " + amount);

                    returnFromDBListView.getItems().addAll("ID Instrumentu: " + ID + " " + "Nazwa instrumentu: " + name + " Ilosc instrumentow: " + amount);
                    returnFromDBListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                }
            }
            catch(Exception ex)
            {
                System.out.println("Error: "+ ex);
                System.out.println("Cannot get data from database!");
            }
        }
        else if (tableListBox.getValue().equals("Akcesoria"))
        {
            try
            {
                String query = "select * from akcesoria";
                result = stat.executeQuery(query);
                System.out.println("Data from database:");
                while(result.next())
                {
                    String ID = result.getString("idAkcesria");
                    String name = result.getString("NazwaAkcesoria");
                    Integer amount = result.getInt("Ilosc");
                    System.out.println("ID Akcesoria: " + ID + " " + "Nazwa akcesoria: " + name + " Ilosc: " + amount);

                    returnFromDBListView.getItems().addAll("ID Instrumentu: " + ID + " " + "Nazwa instrumentu: " + name + " Ilosc instrumentow: " + amount);
                    returnFromDBListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                }
            }
            catch(Exception ex)
            {
                System.out.println("Error: "+ ex);
                System.out.println("Cannot get data from database!");
            }
        }
        else if (tableListBox.getValue().equals(null))
        {
            System.out.println("Musisz wybrac kategorie z listy!");
        }
    }
}
