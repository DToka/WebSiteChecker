/*package view;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("wut");
	}

}
*/
package view;


import controller.Controller;
import model.Functions;
import model.WebInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


public class Main extends Application {

    Controller controller;
    @FXML
    private ComboBox<String> functionDrop;
    @FXML
    private TableView<WebInfo> websiteTableView;
    @FXML
    private TableColumn webSiteColumn;
    @FXML
    private TableColumn resultColumn;
    @FXML
    private TextField websiteInput;

    Functions functionClass;
    Method[] methods;
    //ComboBox Options
    ObservableList<String> options;


    //Initalize function for FXML file
    //Sets CellValue inside TableView so adding/removing data is properly updated automatically
    @FXML
    public void initialize() {

        controller = new Controller();


        Collection<String> functionNames=new ArrayList<String>();

        functionClass = new Functions();
        methods = functionClass.getClass().getDeclaredMethods();

        for(Method method : methods){
            System.out.println(method.getName());
            functionNames.add(method.getName());
        }

        //Combobox options
        options =
                FXCollections.observableArrayList(functionNames);
        functionDrop.setItems(options);


        //Tableview cells
        webSiteColumn.setCellValueFactory(new PropertyValueFactory<WebInfo, String>("website"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<WebInfo, String>("result"));
        websiteTableView.getItems().setAll(controller.getWebsites());

    }

    //Add button
    @FXML
    private void addWebsiteData(ActionEvent event){
        addWebsite(websiteInput.getText());
    }

    @FXML
    private void deleteData(ActionEvent event){
        WebInfo selected = websiteTableView.getSelectionModel().getSelectedItem();
        if(selected != null){
            controller.removeWebsite(selected);
            websiteTableView.getItems().setAll(controller.getWebsites());
        }

    }




    //method for function button
    @FXML
    private void handleButtonAction(ActionEvent event){
        String selectedFunction=functionDrop.getValue();
        String result=null;
        functionClass = new Functions();
        if(selectedFunction==null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("No function selected");
            errorAlert.setContentText("Please select a function to execute on the website list");
            errorAlert.showAndWait();
        }else {
            try{
                Method declaredMethod = functionClass.getClass().getDeclaredMethod(selectedFunction, String.class);
                System.out.println("method set");
                int counter=0;
                for(WebInfo web : controller.getWebsites()){
                    System.out.println("result being set");
                    result=(String)declaredMethod.invoke(functionClass.getClass(),web.getWebsite());
                    //web.setResult(result);
                    controller.setResult(counter,result);
                    counter++;
                }

                websiteTableView.getItems().setAll(controller.getWebsites());
                websiteTableView.refresh();
            }catch(Exception e){
                System.out.println("Method error"+e.toString());
            }
        }
    }

    @FXML
    public void loadWebsites(ActionEvent event) throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile!=null) {

            System.out.println("loading:" + selectedFile.getName());

            controller.loadFromFile(selectedFile);
            websiteTableView.getItems().setAll(controller.getWebsites());

            //System.out.println(data.get(0).getWebsite());
            websiteTableView.refresh();
        }


    }

    @FXML
    public void exportResults(ActionEvent event) throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile!=null)
            controller.exportResult(selectedFile);
    }


    @FXML
    private void saveResults(ActionEvent event) throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null)
            controller.saveToFile(selectedFile);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Website Checker");
        Parent root = FXMLLoader.load(getClass().getResource("mainStage.fxml"));
        primaryStage.setScene(new Scene(root, 450, 300));
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(450);
        primaryStage.getIcons().add(new Image("/resources/webIcon.png"));
        primaryStage.show();


    }

    @FXML
    public void close(ActionEvent event){
        Platform.exit();
    }

    @FXML
    public void addWebsitePrompt(ActionEvent event){

        TextInputDialog dialog = new TextInputDialog("Add Website");
        dialog.setTitle("Add Website");
        dialog.setHeaderText("Enter the name of the website to add");
        dialog.setContentText("Website name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            addWebsite(result.get());
        }

    }

    @FXML
    public void aboutPrompt(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WebSite Checker Information");
        alert.setHeaderText("WebSiteChecker 1.0");
        alert.setContentText("Dmitry Tokarev\n" +
                "July 6, 2019");

        alert.showAndWait();
    }

    public static void main(String args[]) {
        launch(args);

    }


    public void addWebsite(String result){
        System.out.println("Website:" + result);
        StringBuffer resultWebsite=new StringBuffer(result);
        System.out.println(resultWebsite.toString().startsWith("http://"));
        if(resultWebsite.toString().startsWith("http://")) {
            resultWebsite.delete(0,7);
        }
        if(!resultWebsite.toString().startsWith("https://")){

            resultWebsite=new StringBuffer("https://").append(resultWebsite);
        }
        controller.addWebsite(resultWebsite.toString());
        websiteTableView.getItems().setAll(controller.getWebsites());
        websiteInput.clear();
    }

}
