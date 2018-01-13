package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller
{

    private Connection connection;
    private Statement statement;
    private ResultSet result;

    private String nameToAddToTable;
    private Integer amountToAddToTable, selectedItemID;

    private Image itemImage;

    private ArrayList<String> tablesListFromDB;

    private ObservableList<String> tableList; //= FXCollections.observableArrayList("Instrumenty Smyczkowe", "Instrumenty dete", "Sprzet naglosnieniowy", "Akcesoria");

    @FXML
    private ComboBox tableListBox;

    @FXML
    private ListView<String> returnFromDBListView;

    @FXML
    private TextField nameFromTextBox;

    @FXML
    private TextField amountFromTextBox;

    @FXML
    private TextField idToDrop;

    @FXML
    private ImageView itemIconImageView;


    public Controller()
    {
        init();
    }

    // initiating  all void`s and vars needed on start
    private void init()
    {
        returnFromDBListView = new ListView<>();
        tablesListFromDB = new ArrayList<String>();
        itemIconImageView = new ImageView();

        connectToDB();
    }

    //connecting to database
    private void connectToDB()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "root", ""); //username and password should be changed for more security!!!
            statement = connection.createStatement();

            System.out.println("----------Connected to database!----------");
        }
        catch(Exception ex)
        {
            System.out.println("----------Cannot connect to database!----------\n" + "Error: "+ ex);
        }
    }

    private void showItemImageFromDatabase()
    {
        try
        {
            String query = "SELECT imageLink from " + tableListBox.getValue() + " WHERE id =" + selectedItemID;
            result = statement.executeQuery(query);

            while(result.next())
            {
                String imageLinkGetString = result.getString("imageLink");

                if(imageLinkGetString == "")
                {
                    itemImage = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqtb2g_qz-yqS6wtxd0L__o_VmkwH7t-XHTNBSwOXPcc6JIVD2"); //"NO IMAGE" if in DB there`s are not any image link
                }
                else itemImage = new Image(imageLinkGetString);
                itemIconImageView.setImage(itemImage);

                System.out.println("Image is set!");
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error with getting image from DB:" + ex);
        }

    }
    //some test void for getting info about eq list in table !DEPRECATED!
    @FXML
    private void getData()
    {
        try
        {
            String query = "select * from testtable";
            result = statement.executeQuery(query);
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


    //taking list of all tables from database
    private void getTableList()
    {
        try
        {
            // SELECT table_name FROM information_schema.tables where table_schema='<your_database_name>';

            tablesListFromDB.clear();
            
            String query = "SELECT table_name FROM information_schema.tables where table_schema='warehouse'";
            ResultSet tableListFromDB = statement.executeQuery(query);

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
        itemIconImageView.setImage(null);

        try
        {
            String query = "select * from " + tableListBox.getValue();

            result = statement.executeQuery(query);

            System.out.println("Data from database:");
            while(result.next())
            {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                Integer amount = result.getInt("amount");

                System.out.println("ID Instrumentu: " + id + " Nazwa instrumentu: " + name + " Ilosc instrumentow: " + amount);

                returnFromDBListView.getItems().addAll("ID: " + id + "\t\t Nazwa: " + name + "\t\t Ilosc: " + amount);
                returnFromDBListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Cannot get data from database! \n" + "Error: " + ex);
        }
    }


    @FXML
    private void insertValuesIntoDB()
    {
        // INSERT INTO `instrumenty_dete` (`id`, `name`, `amount`) VALUES (NULL, 'klarnet', '1');

        getValuesFromTextBoxes();

        String query = "INSERT INTO " + tableListBox.getValue()
                + " (name, amount) VALUES ('"
                + nameToAddToTable + "',"
                + amountToAddToTable + " );";

        try
        {
            statement.executeUpdate(query);
            System.out.println("Added values to table");
        }
        catch (Exception ex)
        {
            System.out.println("Error with adding values to table: \n" + ex);
        }
        nameFromTextBox.setText(null);
        amountFromTextBox.setText(null);
        getDataFromDatabase();

    }

    private void getValuesFromTextBoxes()
    {
        nameToAddToTable = nameFromTextBox.getText();
        if(Integer.parseInt(amountFromTextBox.getText())>=0 && amountFromTextBox != null)
        {
            amountToAddToTable = Integer.parseInt(amountFromTextBox.getText());
        }
        else System.out.println("Amount must be equal or greater then zero.");
    }

    @FXML
    private void  getItemID ()
    {
        String searchForItemID = returnFromDBListView.getSelectionModel().getSelectedItem().toString();

        Scanner findID = new Scanner(searchForItemID);
        findID.skip("ID:");

        selectedItemID  = findID.nextInt();

        System.out.println("Id from scanner: " + selectedItemID);

        showItemImageFromDatabase();
    }

    @FXML
    private  void dropLineFromTable()
    {
        Integer id = Integer.parseInt(idToDrop.getText());

        try
        {
            // DELETE FROM `akcesoria` WHERE `akcesoria`.`id` = 3;
            String query = "DELETE FROM " + tableListBox.getValue() + " WHERE " + tableListBox.getValue() + ".id =" + selectedItemID;
            System.out.println(query);
            statement.executeUpdate(query);
            System.out.println("Deleted line with id:" + id);
        }
        catch (Exception ex)
        {
            System.out.println("Deleting error" + ex);
        }
        idToDrop.setText(null);
        getDataFromDatabase();
    }


    /* !DEPRECATED!
    @FXML
    private void getDataFromDatabase()
    {
        returnFromDBListView.getItems().clear();

        if(tableListBox.getValue().equals("Instrumenty Smyczkowe"))
        {
            try
            {
                String query = "select * from instrumenty_smyczkowe";
                result = statement.executeQuery(query);
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
                result = statement.executeQuery(query);
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
                result = statement.executeQuery(query);
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
                result = statement.executeQuery(query);
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
                result = statement.executeQuery(query);
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
