import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InventoryManagementSystemFX extends Application {
    private TableView<Item> tableView;
    private TextField itemNameField, quantityField, priceField;
    private ObservableList<Item> inventory;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Management System");

        // Layouts
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        HBox inputLayout = new HBox(10);
        inputLayout.setPadding(new Insets(10));

        // Inventory List
        inventory = FXCollections.observableArrayList();

        // TableView setup
        tableView = new TableView<>();
        tableView.setItems(inventory);

        TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());

        TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price per Unit");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        TableColumn<Item, Double> totalColumn = new TableColumn<>("Total Price");
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        tableView.getColumns().addAll(nameColumn, quantityColumn, priceColumn, totalColumn);

        // Input fields and labels
        itemNameField = new TextField();
        itemNameField.setPromptText("Item Name");

        quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        priceField = new TextField();
        priceField.setPromptText("Price per Unit");

        Button addButton = new Button("Add Item");
        addButton.setOnAction(e -> addItem());

        Button removeButton = new Button("Remove Selected");
        removeButton.setOnAction(e -> removeSelectedItem());

        inputLayout.getChildren().addAll(itemNameField, quantityField, priceField, addButton, removeButton);

        // Add everything to the main layout
        mainLayout.getChildren().addAll(tableView, inputLayout);

        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addItem() {
        String itemName = itemNameField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();

        try {
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);

            // Add the item to the inventory list
            Item newItem = new Item(itemName, quantity, price);
            inventory.add(newItem);

            // Clear the input fields
            itemNameField.clear();
            quantityField.clear();
            priceField.clear();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values for quantity and price.");
        }
    }

    private void removeSelectedItem() {
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            inventory.remove(selectedItem);
        } else {
            showAlert("No Selection", "Please select an item to remove.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

