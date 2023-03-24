import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.sql.*;
import java.text.NumberFormat;
import java.util.*;

public class Stacks extends Application {
    private static Stage stage;
    private static Scene scene;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {


        // Create a scene and place it in the stage
        stage = primaryStage;
        scene = startPage();
        primaryStage.setTitle("[ Stacks ]"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    //Start Page
    public Scene startPage() {//first scene
        //Create vbox

        //Title Text
        Text titleText = new Text(20,20,"STACKS");
        titleText.setFont(new Font("Arial", 40));
        titleText.setFill(Color.valueOf("#68A691"));
        //titleText.setFill(Color.WHITE);

        //Main Logo
        Image logo = new Image("./logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(300);
        logoView.setFitWidth(250);
        logoView.setPreserveRatio(true);

        //Login button that takes user to login page when clicked
        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent t){
                stage.setScene(logInScene());
            }
        });

        //Register button that takes user to the register page when clicked
        Button regBtn = new Button("Register");
        regBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent t){
                stage.setScene(registerScene());
            }
        });

        //Create vbox and add children
        VBox mainPanel = new VBox();
        mainPanel.getChildren().add(logoView);
        mainPanel.getChildren().add(titleText);
        mainPanel.getChildren().add(loginBtn);
        mainPanel.getChildren().add(regBtn);
        mainPanel.setAlignment(Pos.CENTER);
        mainPanel.setSpacing(20);

        //Create border pane to center the content
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("backgroundColor");
        pane.setCenter(mainPanel);

        //Set up the main scene and add the style sheet
        Scene mainScene = new Scene(pane,400,800);
        mainScene.getStylesheets().add("style.css");
        return mainScene;
    }

    //Username and password text fields
    public TextField userName = new TextField();
    public TextField userPass = new TextField();

    //Login in page
    public Scene logInScene(){
        //Create a pane to attach everything to
        BorderPane pane = new BorderPane();



        //Set up the login grid
        GridPane loginPane = new GridPane();

        //The user name
        Label userNameLabel = new Label("Username");
        userNameLabel.getStyleClass().add("loginLabel");

        //The user password
        Label userPassLabel = new Label("Password");
        userPassLabel.getStyleClass().add("loginLabel");

        //Add everything to the grid
        loginPane.add(userName,1,0);
        loginPane.add(userNameLabel,0,0);
        loginPane.add(userPassLabel,0,1);
        loginPane.add(userPass,1,1);

        //Style the login meny
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.setAlignment(Pos.CENTER);

        //Main Logo
        Image logo = new Image("./logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(300);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);

        //Error text(empty by default)
        Text errorText = new Text();
        errorText.setFill(Color.RED);

        //Login Button
        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                try {
                    if(attemptLogin(userName.getText(), userPass.getText())){
                        getUserType(userName.getText(), userPass.getText());
                    }else{
                        errorText.setText("Invalid Credentials!");
                        userName.setText("");
                        userPass.setText("");
                    }
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

                System.out.println();
            }
        });
        loginBtn.getStyleClass().add("loginBtn");


        //Go to register page
        Button regBtn = new Button("Register");
        regBtn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                stage.setScene(registerScene());
            }
        });
        regBtn.getStyleClass().add("loginBtn");
        VBox botPane = new VBox();
        Text helpText = new Text("Don't have an account?");
        botPane.getChildren().add(helpText);
        botPane.getChildren().add(regBtn);
        botPane.setAlignment(Pos.CENTER);
        botPane.setSpacing(10);



        //Create the main pane
        VBox mainPane = new VBox();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setSpacing(20);
        mainPane.getChildren().add(logoView);
        mainPane.getChildren().add(loginPane);
        mainPane.getChildren().add(loginBtn);
        mainPane.getChildren().add(errorText);

        //Style the main pane
        pane.getStyleClass().add("backgroundColor");
        pane.setCenter(mainPane);
        pane.setBottom(botPane);

        //Set up the main scene and add the style sheet
        Scene mainScene = new Scene(pane,400,800);
        mainScene.getStylesheets().add("style.css");
        return mainScene;
    }

    //Username and password text fields
    public TextField userName_reg = new TextField();
    public TextField userPass_reg = new TextField();

    //Dropdown for associated user
    public static ComboBox<String> parentDrop = new ComboBox<>();

    //Login in page
    public Scene registerScene(){
        //Create a pane to attach everything to
        BorderPane pane = new BorderPane();

        //Set up the login grid
        GridPane regPane = new GridPane();

        //The user name
        Label userNameLabel = new Label("Username");
        userNameLabel.getStyleClass().add("loginLabel");

        //The user password
        Label userPassLabel = new Label("Password");
        userPassLabel.getStyleClass().add("loginLabel");

        //Add everything to the grid
        regPane.add(userName_reg,1,0);
        regPane.add(userNameLabel,0,0);
        regPane.add(userPassLabel,0,1);
        regPane.add(userPass_reg,1,1);

        //Style the register menu
        regPane.setVgap(10);
        regPane.setHgap(10);
        regPane.setAlignment(Pos.CENTER);

        //Main Logo
        Image logo = new Image("./logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(300);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);

        //UserType selector
        final ToggleGroup registerGroup = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Parent");
        rb1.setToggleGroup(registerGroup);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("Child");
        rb2.setToggleGroup(registerGroup);

        HBox typeSelect = new HBox();
        typeSelect.getChildren().add(rb1);
        typeSelect.getChildren().add(rb2);
        typeSelect.setSpacing(10);
        typeSelect.setAlignment(Pos.CENTER);


        //Error text
        Text errorText = new Text();
        errorText.setFill(Color.RED);

        //Register Button
        Button regBtn = new Button("Register");
        regBtn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                try {
                    if (attemptRegister(userName_reg.getText(), userPass_reg.getText(), rb1.isSelected(), parentDrop.getValue())) {
                        stage.setScene(logInScene());

                    } else {
                        errorText.setText("User already exists!");
                    }
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        regBtn.getStyleClass().add("loginBtn");

        //Go to login page
        Button logBtn = new Button("Login");
        logBtn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                stage.setScene(logInScene());
            }
        });
        regBtn.getStyleClass().add("loginBtn");
        VBox botPane = new VBox();
        Text helpText = new Text("Already have an account?");
        botPane.getChildren().add(helpText);
        botPane.getChildren().add(logBtn);
        botPane.setAlignment(Pos.CENTER);
        botPane.setSpacing(10);

        //Create the main pane
        VBox mainPane = new VBox();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setSpacing(20);
        mainPane.getChildren().add(logoView);
        mainPane.getChildren().add(regPane);
        mainPane.getChildren().add(typeSelect);

        try {
            //Set up a dropdown with all available children
            parentDrop.getItems().clear();
            parentDrop.getItems().addAll(getParents());
            mainPane.getChildren().add(parentDrop);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        //Add the remaining children
        mainPane.getChildren().add(regBtn);
        mainPane.getChildren().add(errorText);

        //Style the main pane
        pane.getStyleClass().add("backgroundColor");
        pane.setCenter(mainPane);
        pane.setBottom(botPane);

        //Set up the main scene and add the style sheet
        Scene mainScene = new Scene(pane,400,800);
        mainScene.getStylesheets().add("style.css");
        return mainScene;
    }

    //Spend money amount textField
    public TextField spendAmount = new TextField();

    //Child view page
    public Scene childPage(int id){
        //The main borderPane
        BorderPane mainPane = new BorderPane();

        //Create the main money display
        Text moneyDisplay = new Text("$0.00");
        moneyDisplay.setFont(Font.font("Arial", FontWeight.LIGHT, 80));
        moneyDisplay.setFill(Color.valueOf("#68A691"));
        VBox moneyBox = new VBox();
        moneyBox.setAlignment(Pos.CENTER);
        moneyBox.getChildren().add(moneyDisplay);
        try{
            moneyDisplay.setText(convertToUSD(getBalance(id)));

        }catch(SQLException | ClassNotFoundException throwables){

        }

        //Create the spend money menu
        HBox spendMoney = new HBox();
        Label spendMoneyLabel = new Label("Amount");
        Button spendMoneyBtn = new Button("Spend");
        Text errorText = new Text();
        spendMoneyBtn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                errorText.setText(spendMoney(id, spendAmount.getText()));
                if(errorText.getText().contains("spent!")){
                    try {
                        moneyDisplay.setText(convertToUSD(getBalance(id)));
                    } catch (ClassNotFoundException | SQLException e) {

                    }
                }else{
                }
            }
        });
        spendMoney.getChildren().add(spendMoneyLabel);
        spendMoney.getChildren().add(spendAmount);
        spendMoney.getChildren().add(spendMoneyBtn);
        spendMoney.setAlignment(Pos.CENTER);
        spendMoney.setSpacing(10);
        moneyBox.getChildren().add(spendMoney);
        moneyBox.getChildren().add(errorText);

        //Create button to check transactions
        Button transacBtn = new Button("Check transactions");
        transacBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                stage.setScene(transacView(id, false));
            }
        });

        //Add children to the main borderpane
        mainPane.setCenter(moneyBox);
        VBox transacHolder = new VBox();
        transacHolder.getChildren().add(transacBtn);
        transacHolder.setAlignment(Pos.CENTER);
        mainPane.setBottom(transacHolder);


        //Set up the main scene and add the style sheet
        Scene mainScene = new Scene(mainPane,600,800);
        mainScene.getStylesheets().add("style.css");
        return mainScene;
    }

    //Dropdown for associated user
    public static ComboBox<String> childDrop = new ComboBox<>();

    //Textfield for transfer amount
    public static TextField moneyAmount = new TextField("Amount");

    //Textfield for add amount
    public static TextField addAmount = new TextField("Amount");

    //Parent view page
    public Scene parentPage(int id){
        //The main borderPane
        BorderPane mainPane = new BorderPane();

        //Create the main money display
        Text moneyDisplay = new Text("$0.00");
        moneyDisplay.setFont(Font.font("Arial", FontWeight.LIGHT, 80));
        moneyDisplay.setFill(Color.valueOf("#68A691"));
        VBox moneyBox = new VBox();
        moneyBox.setAlignment(Pos.CENTER);
        moneyBox.getChildren().add(moneyDisplay);

        //Fill the associated users correctly
        try {
            //Set the money display to the correct amount
            moneyDisplay.setText(convertToUSD(getBalance(id)));
            //Set up a dropdown with all available children
            childDrop.getItems().addAll(getChildren(id));
            moneyBox.getChildren().add(childDrop);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        //Menu To Transfer Money
        VBox moneyMenu = new VBox();
        HBox transferMenu = new HBox();
        transferMenu.setAlignment(Pos.CENTER);
        transferMenu.setSpacing(10);
        Text errorText = new Text(); //Text for displaying errors
        Label transferLabel = new Label("Transfer to user");
        Button transferButton = new Button("Transfer");
        transferButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent t){
                try {
                    errorText.setText(transferMoney(childDrop.getValue(), moneyAmount.getText(), id));
                    if(errorText.getText().contains("complete!")){
                        moneyDisplay.setText(convertToUSD(getBalance(id)));
                    }
                } catch (ClassNotFoundException | SQLException e) {

                }
            }
        });

        moneyMenu.setAlignment(Pos.CENTER);
        transferMenu.getChildren().add(transferLabel);
        transferMenu.getChildren().add(childDrop);
        transferMenu.getChildren().add(moneyAmount);
        transferMenu.getChildren().add(transferButton);

        //Create button to check transactions
        Button transacBtn = new Button("Check transactions");
        transacBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                stage.setScene(transacView(id, true));
            }
        });

        //Menu to add money
        HBox addMenu = new HBox();
        addMenu.setAlignment(Pos.CENTER);
        addMenu.setSpacing(10);
        Text errorText_add = new Text();
        Label addLabel = new Label("Add funds");
        Button addBtn = new Button("Add");
        addBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent t){
                try {
                    errorText.setText(addMoney(id, addAmount.getText()));
                    if(errorText.getText().contains("added!")){
                        moneyDisplay.setText(convertToUSD(getBalance(id)));
                    }
                } catch (ClassNotFoundException | SQLException e) {

                }
            }
        });
        addMenu.getChildren().add(addLabel);
        addMenu.getChildren().add(addAmount);
        addMenu.getChildren().add(addBtn);

        //Set up the VBOX correctly
        moneyMenu.setSpacing(10);
        moneyMenu.getChildren().add(transferMenu);
        moneyMenu.getChildren().add(errorText);
        moneyMenu.getChildren().add(addMenu);
        moneyMenu.getChildren().add(errorText_add);

        //Add children to the main borderpane
        mainPane.setCenter(moneyBox);
        VBox transacHolder = new VBox();
        transacHolder.getChildren().add(transacBtn);
        transacHolder.setAlignment(Pos.CENTER);
        mainPane.setBottom(transacHolder);
        moneyBox.getChildren().add(moneyMenu);
        moneyBox.getChildren().add(errorText);

        //Set up the main scene and add the style sheet
        Scene mainScene = new Scene(mainPane,600,800);
        mainScene.getStylesheets().add("style.css");
        return mainScene;
    }

    //Transaction view page
    public Scene transacView(int id, boolean parent){
        //Main pane to add everything to
        BorderPane transacView = new BorderPane();

        //VBox to display transaction
        VBox transacPane = new VBox();
        Text title = new Text("Transaction History");
        transacPane.getChildren().add(title);
        transacPane.setAlignment(Pos.CENTER);

        //Loop through all transactions with the id of the current user
        try{
            ResultSet transacs = getTransactionsForUser(id);
            while(transacs.next()){
                HBox transacContainer = new HBox();
                transacContainer.setSpacing(16);
                Text ID = new Text("ID: "+ Integer.toString(transacs.getInt(1)));
                ID.setFont(Font.font(16));

                Text amount = new Text("Amount : " + Integer.toString(transacs.getInt(3)));
                amount.setFont(Font.font(16));

                System.out.println(transacs.getInt(4));
                if(transacs.getInt(4) == 0){
                    amount.setFill(Color.RED);
                }else{
                    amount.setFill(Color.GREEN);
                }

                transacContainer.setAlignment(Pos.CENTER);
                transacContainer.getChildren().add(ID);
                transacContainer.getChildren().add(amount);
                transacPane.getChildren().add(transacContainer);
            }
        }catch(ClassNotFoundException | SQLException e){

        }

        //Button to return to the correct account
        Button returnBtn = new Button("Return");
        returnBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent t){
                if(parent){
                    stage.setScene(parentPage(id));
                }else{
                    stage.setScene(childPage(id));
                }
            }
        });

        //Set up the main scene and add the style sheet
        transacView.setCenter(transacPane);
        VBox transacHolder = new VBox();
        transacHolder.getChildren().add(returnBtn);
        transacHolder.setAlignment(Pos.CENTER);
        transacView.setBottom(transacHolder);

        Scene mainScene = new Scene(transacView,600,800);
        mainScene.getStylesheets().add("style.css");
        return mainScene;
    }

    //Statement on which to call SQL queries
    private static Statement statement;

    //Called when registering a parent
    public static boolean attemptRegister(String user, String pass, boolean userType, String assocUser) throws ClassNotFoundException, SQLException{
        int tempUserType;
        if(userType == true){
            tempUserType = 0;
        }else{
            tempUserType = 1;
        };
        ResultSet resultSet = statement.executeQuery("select * FROM users WHERE userName = " + "'" + user + "'" + " AND " + "userPass = " + "'" + pass + "'");
        if(!resultSet.next()){
            System.out.println(getParentIndex(assocUser));
            statement.executeUpdate("INSERT INTO users (userName,userPass,userType,userBalance,associatedParent)" + " VALUES ('"+user+"','"+pass+"','"+tempUserType+"'" + ",0," + getParentIndex(assocUser) + ")");
            return true;
        }else{
            return false;
        }
    }

    //Attempts a login
    public static boolean attemptLogin(String user, String pass) throws ClassNotFoundException, SQLException {
        ResultSet resultSet = statement.executeQuery("select * FROM users WHERE userName = " + "'" + user + "'" + " AND " + "userPass = " + "'" + pass + "'");

        if(!resultSet.next()){
            return false;
        }else{
            return true;
        }

    }

    //Helper function that navigates the user to the correct page based on type
    public void getUserType(String user, String pass) throws ClassNotFoundException, SQLException{
        ResultSet resultSet = statement.executeQuery("select userType,ID FROM users WHERE userName = "+ "'" + user + "'" + " AND " + "userPass = " + "'" + pass + "'");
        if(resultSet.next()){
            if(resultSet.getInt(1) == 0){
                stage.setScene(parentPage(resultSet.getInt(2)));
            }else{
                stage.setScene(childPage(resultSet.getInt(2)));
            }
        }
    }

    //Pulls and returns all parents from the database
    public List<String> getParents() throws ClassNotFoundException, SQLException{
        ResultSet resultSet = statement.executeQuery("select userName FROM users WHERE userType = 0");

        List<String> parentNames = new ArrayList<>();

        while (resultSet.next()){
            parentNames.add(resultSet.getString(1));
        }
        return parentNames;
    }

    //Returns the index of a user based on passed in string
    public static int getParentIndex(String inParent) throws ClassNotFoundException, SQLException {
        ResultSet resultSet = statement.executeQuery("select ID FROM users WHERE userName ='" + inParent + "'");
        if(resultSet.next()){
            return resultSet.getInt(1);
        }else{
            return 9999999;
        }
    }

    //Pulls and returns all children associated with the passed in parent from the database
    public List<String> getChildren(int id) throws ClassNotFoundException, SQLException{
        ResultSet resultSet = statement.executeQuery("select userName FROM users WHERE userType = 1 AND associatedParent =" + id);
        System.out.println(id);
        List<String> childNames = new ArrayList<>();

        while (resultSet.next()){
            childNames.add(resultSet.getString(1));
        }
        return childNames;
    }

    //Pulls and returns all children associated with the passed in parent from the database
   public static int getChildID(String name) throws ClassNotFoundException, SQLException{
        ResultSet resultSet = statement.executeQuery("select ID FROM users WHERE userName ='" + name + "'");
        if(resultSet.next()){
            System.out.println(resultSet.getInt(1));
            return resultSet.getInt(1);
        }else{
            return 999999;
        }
    }

    //Handles transfering money between a parent and child
    public String transferMoney(String user, String amount, int ID) throws ClassNotFoundException, SQLException{
        if(user == null){
            return "Invalid user.";
        }
        try{
            Double.parseDouble(amount);
        }catch(NumberFormatException e){
            return "Invalid amount";
        }

        try{
            if(getBalance(ID) -  Double.parseDouble(amount) < 0){
                return "Balance is too low to complete that transfer!";
            } else{
                double moneyExchanged = getBalance(getChildID(user)) + Double.parseDouble(amount);
                double moneyRemoved = getBalance(ID) - Double.parseDouble(amount);
                statement.executeUpdate("UPDATE users SET userBalance = " + moneyExchanged + " WHERE ID = " + getChildID(user)); //Add money to the child
                statement.executeUpdate("UPDATE users SET userBalance = " + moneyRemoved + " WHERE ID = " + ID); //Remove money from the parent
                statement.executeUpdate("INSERT INTO transactions (transactionTarget, transactionAmount, transactionType) VALUES (" + ID + "," + Double.parseDouble(amount) + "," + "0" +")"); //Add transaction
                statement.executeUpdate("INSERT INTO transactions (transactionTarget, transactionAmount, transactionType) VALUES (" + getChildID(user) + "," + Double.parseDouble(amount) + "," + "1" +")"); //Add transaction

                return  "Transfer complete! New balance is " + convertToUSD(moneyRemoved);
            }
        }catch (SQLException | ClassNotFoundException throwables){

        }

        return  "General error.";
    }

    //Adds money to the parents account
    public String addMoney(int ID, String amount){
        try{
            Double.parseDouble(amount);
        }catch(NumberFormatException e){
            return "Invalid amount";
        }
        try{
            double moneyExchanged = getBalance(ID) + Double.parseDouble(amount);
            statement.executeUpdate("UPDATE users SET userBalance = " + moneyExchanged + " WHERE ID = " + ID); //Add money to the parent
            statement.executeUpdate("INSERT INTO transactions (transactionTarget, transactionAmount, transactionType) VALUES (" + ID + "," + Double.parseDouble(amount) + "," + "1" +")"); //Add transaction
            return  "Money added! New balance is " + convertToUSD(moneyExchanged);
        }catch (SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }

        return  "General error.";
    }

    //Spends child money
    public String spendMoney(int ID, String amount){
        try{
            Double.parseDouble(amount);
        }catch(NumberFormatException e){
            return "Invalid amount";
        }
        try{
            if(getBalance(ID) -  Double.parseDouble(amount) < 0){
                return "Balance is too low to spend!";
            } else{
                double moneyExchanged = getBalance(ID) - Double.parseDouble(amount);
                statement.executeUpdate("UPDATE users SET userBalance = " + moneyExchanged + " WHERE ID = " + ID); //Add money to the child
                statement.executeUpdate("INSERT INTO transactions (transactionTarget, transactionAmount, transactionType) VALUES (" + ID + "," + Double.parseDouble(amount) + "," + "0" +")"); //Add transaction
                return  "Money spent! New balance is " + convertToUSD(moneyExchanged);
            }
        }catch (SQLException | ClassNotFoundException throwables){

        }

        return  "General error.";
    }

    //Returns the balance of the user with the given ID
    public static double getBalance(int id) throws ClassNotFoundException, SQLException{
        ResultSet resultSet = statement.executeQuery("select userBalance FROM users WHERE ID = " + id);
        if(resultSet.next()){
            return resultSet.getDouble(1);
        }else{
            return 123456789;
        }
    }

    //Returns the name of the user based on their ID
    public static String getName(int ID) throws ClassNotFoundException, SQLException{
        ResultSet resultSet = statement.executeQuery("select userName FROM users WHERE ID = " + ID);
        if(resultSet.next()){
            String temp = resultSet.getString(1);
            return temp;
        }else{
            return "No string found!";
        }
    }

    //Returns the transactions of a user based on their ID
    public ResultSet getTransactionsForUser(int ID) throws  ClassNotFoundException, SQLException{
        ResultSet resultSet = statement.executeQuery("select * FROM transactions WHERE transactionTarget = " +ID);
        return resultSet;
    }

    //Utility function to format double as money
    public static String convertToUSD(double money){
        NumberFormat usdConvert = NumberFormat.getCurrencyInstance(Locale.US);
        return(usdConvert.format(money));
    }


    //Initializes JDBC, as well as ensuring connection is made with the database
    public static void initServer() throws SQLException {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/stack", "root","");
            System.out.println("Database connected");

            statement = connection.createStatement();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        initServer();
        launch(args);
    }
}