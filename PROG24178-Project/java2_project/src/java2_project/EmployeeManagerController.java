package java2_project;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class EmployeeManagerController implements Initializable {

    @FXML
    private ToggleGroup Gender;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private CheckBox chkFulltime;

    @FXML
    private ComboBox<String> cbJobTitle;

    @FXML
    private Label lbGenderError;

    @FXML
    private Label lbIDError;

    @FXML
    private Label lbJobTitleError;

    @FXML
    private Label lbNameError;

    @FXML
    private Label lbTotalNum;

    @FXML
    private ListView<Employee> lvEmployees;

    @FXML
    private RadioButton rdbFemale;

    @FXML
    private RadioButton rdbMale;

    @FXML
    private RadioButton rdbOther;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfName;

    private int id;
    private String name ;
    private String position;
    private boolean isFulltime;
    private String gender;
    private List<Employee> list;
    private List<Integer> ids = new ArrayList<>(); //create an id List for checking duplicated id later.
    ReadAndWriteEmployee rwe = new ReadAndWriteEmployee();
    private int countEmployees;
    @FXML
    
    void btnAddHandler(ActionEvent event) {
        Employee employee = readFromTextFields();
        addEmployeeToList(employee);
        lvEmployees.getItems().clear();
        lvEmployees.getItems().addAll(list);
        lbTotalNum.setText(Integer.toString(countEmployees));
    }

    

    @FXML
    void btnClearHandler(ActionEvent event) {
        tfID.setText("");
        tfName.setText("");
        chkFulltime.setSelected(false);
        cbJobTitle.getSelectionModel().clearSelection();
        cbJobTitle.setValue(null);
        rdbFemale.setSelected(false);
        rdbMale.setSelected(false);
        rdbOther.setSelected(false);
        lbGenderError.setText("");
        lbIDError.setText("");
        lbJobTitleError.setText("");
        lbNameError.setText("");
    }

    @FXML
    void btnDeleteHandler(ActionEvent event) {
        Employee employee = lvEmployees.getSelectionModel().getSelectedItem();
        if(employee != null) {
            list.remove(employee);
            lvEmployees.getItems().clear();
            lvEmployees.getItems().addAll(list);
            countEmployees--;
            lbTotalNum.setText(Integer.toString(countEmployees));
        }
        
    }
    
    @FXML
    void btnSaveToFileHandler(ActionEvent event) {
        rwe.overWriteEmployeeToFile("employees.csv", name, position, gender,list);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbJobTitle.getItems().addAll("Director", "Manager", "Developer", "Tester", "Salesman");
        lvEmployees.getItems().addAll(rwe.readFromFile("employees.csv"));
        countEmployees = rwe.getCountEmployees();
        lbTotalNum.setText(Integer.toString(countEmployees));
        list = rwe.getList();
        for(Employee employee: list) {
            ids.add(employee.getId());
        }
    }

    public Employee readFromTextFields() {
        isFulltime = chkFulltime.isSelected(); 
    // validate Id text field and read it's value.
        try{
            id = Integer.parseInt(tfID.getText());
            lbIDError.setText("");
            if(ids.contains(id)) {
                lbIDError.setText("The ID is already exist.");
            }
            ids.add(id);
        } catch(NumberFormatException e) {
            lbIDError.setText("ID must be an integer.");
        }
        if(tfID.getText().isEmpty()) {
            lbIDError.setText("ID is required.");
        } else if (id < 0){
            lbIDError.setText("ID can't be negative.");
        } 
    //validate name text field and read it's value
        name = tfName.getText(); 
        if(name.isEmpty()) {
            lbNameError.setText("Name is required.");
        } else {
            lbNameError.setText("");
        }
    //validate job title combo box and read it's value
        position = cbJobTitle.getSelectionModel().getSelectedItem();
        if(position == null) {
            lbJobTitleError.setText("Job Title is required.");
        } else {
            lbJobTitleError.setText("");
        }
    //validate gender radio button and read it's value
        if(rdbFemale.isSelected()) {
            gender = "Female";
            lbGenderError.setText("");
        } else if (rdbMale.isSelected()) {
            gender = "Male";
            lbGenderError.setText("");
        } else if (rdbOther.isSelected()){
            gender = "Other";
            lbGenderError.setText("");
        } else{
            lbGenderError.setText("Please select a gender");
        }
        return new Employee(id, name, position, isFulltime, gender);
    }

    public void addEmployeeToList(Employee employee) {
        if(lbIDError.getText().equals("") && lbNameError.getText().equals("") 
            && lbJobTitleError.getText().equals("") && lbGenderError.getText().equals(("")) 
            && lbIDError.getText().equals("")) { 
                list.add(employee);
                countEmployees++;
        }
    }
    
}
