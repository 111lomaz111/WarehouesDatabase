package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import sun.reflect.annotation.ExceptionProxy;

import java.sql.*;
import java.util.ArrayList;

public class Controller
{

    private Connection conn;
    private Statement stat;
    private ResultSet result;

    ArrayList<String> tablesListFromDB;

    ObservableList<String> tableList; //= FXCollections.observableArrayList("Instrumenty Smyczkowe", "Instrumenty dete", "Sprzet naglosnieniowy", "Akcesoria");

    @FXML
    private ComboBox tableListBox;

    @FXML
    private ListView returnFromDBListView;


    //connecting to database
    public Controller()
    {
        returnFromDBListView = new ListView<>();
        tablesListFromDB = new ArrayList<String>();

        try
        {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "root", "");
            stat = conn.createStatement();

            System.out.println("----------Connected to database!----------");
        }
        catch(Exception ex)
        {
            System.out.println("----------Cannot connect to database!----------\n"+"Error: "+ ex);
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
            System.out.println("Cannot get data from database! \n" + "Error: "+ ex);
            System.out.println();
        }
    }

    private void getTableList()
    {
        try
        {
            // SELECT table_name FROM information_schema.tables where table_schema='<your_database_name>';

            tablesListFromDB.clear();

            String query = "SELECT table_name FROM information_schema.tables where table_schema='warehouse'";
            ResultSet tableListFromDB = stat.executeQuery(query);

            System.out.println("----------Data from database:----------");
            while(tableListFromDB.next())
            {
                String tableName = tableListFromDB.getString("table_name");
                System.out.println("Table: " + tableName);
                tablesListFromDB.add(tableName);
                tableList = FXCollections.observableArrayList(tablesListFromDB);
            }
        }
        catch(Exception ex)
        {
            System.out.println("Cannot get table list data from database! \n" + "Error: "+ ex);
            System.out.println();
        }
        System.out.println("----------DONE----------");
    }
    @FXML //Getting information what tables we have in our database in every click and adding them to combo box
    private void AddValuesToCB()
    {
        tableListBox.valueProperty().set(null);
        getTableList();
        tableListBox.setItems(tableList);
    }

    @FXML
    private void getDataFromDatabase() //more universal void for getting info about eq, saved ~130lines of code
    {
        returnFromDBListView.getItems().clear();

        try
        {
            String query = "select * from " + tableListBox.getValue();
            result = stat.executeQuery(query);
            System.out.println("Data from database:");
            while(result.next())
            {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                Integer amount = result.getInt("amount");
                System.out.println("ID Instrumentu: " + id + " " + "Nazwa instrumentu: " + name + " Ilosc instrumentow: " + amount);

                returnFromDBListView.getItems().addAll("ID Instrumentu: " + id + "\t\t Nazwa instrumentu: " + name + "\t\t Ilosc instrumentow: " + amount);
                returnFromDBListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Cannot get data from database! \n" + "Error: " + ex);
        }
    }


    /*
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
        //universal I~M working on it
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


        else
        {
            System.out.println("Musisz wybrac kategorie z listy!");
        }
    }
    */
}
