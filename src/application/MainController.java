package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MainController implements Initializable {

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_insert;

    @FXML
    private Button btn_update;

    @FXML
    private TableColumn<Sailors, Double> tv_age;

    @FXML
    private TableColumn<Sailors, String> tv_id;

    @FXML
    private TableColumn<Sailors, Integer> tv_rating;

    @FXML
    private TableView<Sailors> tv_sailors;

    @FXML
    private TableColumn<Sailors, String> tv_sname;

    @FXML
    private TextField txt_age;

    @FXML
    private TextField txt_rating;

    @FXML
    private TextField txt_sid;

    @FXML
    private TextField txt_sname;
    
   
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      showSailors();
    }
   
   public void handleRowSelection(MouseEvent event) {
	   System.out.println("Inside handleRowSelection.");
	    // Get selected row index
	    int index = tv_sailors.getSelectionModel().getSelectedIndex();
	    System.out.println("Index is :" + index);
	    if (index <= -1) {
	        System.out.println("No row selected.");
	        return;
	    }

	    // Get selected row data from the ObservableList
	    Sailors selectedSailor = tv_sailors.getSelectionModel().getSelectedItem();
	    if (selectedSailor != null) {
	        // Populate text fields with selected row's data
	        txt_sid.setText(String.valueOf(selectedSailor.getSid()));
	        txt_sname.setText(selectedSailor.getSname());
	        txt_age.setText(String.valueOf(selectedSailor.getAge()));
	        txt_rating.setText(String.valueOf(selectedSailor.getRating()));
	    }
	}

   public ObservableList<Sailors> getSailors(String query) {
	    ObservableList<Sailors> sailorsList = FXCollections.observableArrayList();
	    Connection conn = DBConnection.getConnection();

	    Statement st = null; // Initialize st to null
	    ResultSet rs = null; // Initialize rs to null

	    try {
	        st = conn.createStatement();
	        rs = st.executeQuery(query);
	        Sailors sailors;

	        if (!rs.isBeforeFirst()) {
	            System.out.println("No records found for the query: " + query);
	            return null; // Return null if no records found
	        } else {
	            while (rs.next()) {
	                sailors = new Sailors(rs.getInt("sid"), rs.getString("sname"), rs.getInt("rating"), rs.getDouble("age"));
	                sailorsList.add(sailors);
	            }
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        // Close resources in finally block
	        try {
	            if (rs != null) rs.close();
	            if (st != null) st.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return sailorsList;
	}

	
    public void showSailors() {

		String query ="SELECT * FROM book.sailors";
    	ObservableList<Sailors> sailorsList = getSailors(query);
        // Bind columns with properties
        tv_id.setCellValueFactory(new PropertyValueFactory<>("sid"));
        tv_sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
        tv_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tv_age.setCellValueFactory(new PropertyValueFactory<>("age"));

        tv_sailors.setItems(sailorsList);
    }
    /*
    public void insertRecord(){
        String query = "INSERT INTO book.sailors VALUES (" + txt_sid.getText() + ",'" + txt_sname.getText() + "','" + txt_age.getText() + "',"
                + txt_rating.getText() + ")";
        executeQuery(query);
        showSailors();
    }*/
    public void insertRecord() {
        // Get sid from txt_sid
    	int sid = Integer.parseInt(txt_sid.getText().trim());

        System.out.println("sid: " +sid);
        // Check if sid exists
     /*   if (sidExists(sid)) {
            // Handle sid exists case (e.g., show error message)
            System.out.println("SID already exists");
            return;
        }*/
        
        // If sid doesn't exist, proceed with insertion
        String query = "INSERT INTO book.sailors VALUES ('" + sid + "','" + txt_sname.getText() + "','" + txt_age.getText() + "',"
                + txt_rating.getText() + ")";
        executeQuery(query);
        showSailors();
    }

    private boolean sidExists(int sid) {
        boolean flag = false;
        String query = "SELECT COUNT(*) FROM book.sailors WHERE sailors.sid = '" + sid + "'";
     
            ObservableList<Sailors> sailorsList = getSailors(query);
            //System.out.println("sailorsList.isEmpty(:)" + !sailorsList.isEmpty());
            if (!sailorsList.isEmpty()) {
                // sailorsList is not empty, meaning sid exists
            	 displayAlert("SID already exists.");
                System.out.println("SID already exists.");
                flag = true;
            }
         
        return flag;
    }
    private void displayAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateRecord(){
        String query = "UPDATE  book.sailors SET sname  = '" + txt_sname.getText() + "', age = '" + txt_age.getText() + "', rating = " +
                txt_rating.getText() + " WHERE sid = " + txt_sid.getText() + "";
        executeQuery(query);
        showSailors();
    }
    


    public void deleteButton(){
        String query = "DELETE FROM book.sailors WHERE sid =" + txt_sid.getText() + "";
        executeQuery(query);
        showSailors();
    }

    public void executeQuery(String query) {
        Connection conn = DBConnection.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
