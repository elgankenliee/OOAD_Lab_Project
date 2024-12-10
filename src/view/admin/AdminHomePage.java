package view.admin;

import java.util.ArrayList;

import client.Main;
import controller.ItemController;
import controller.UserController;
import factories.GUIComponentFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.domain.Item;

public class AdminHomePage {

	public static ArrayList<Item> itemList = new ArrayList<>();

	public static void loadPage() {
		VBox screen = new VBox();
		HBox jumbotronMessageContainer = new HBox();

		Label pendingLabel = new Label("Pending ");
		Label itemsLabel = new Label("items");

		jumbotronMessageContainer.getChildren().addAll(pendingLabel, itemsLabel);

		ObservableList<Item> observableItemList = FXCollections.observableArrayList(itemList);

		TableView<Item> table = new TableView<>();
		TableColumn<Item, String> nameCol = new TableColumn<>("Item Name");
		TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
		TableColumn<Item, String> sizeCol = new TableColumn<>("Size");
		TableColumn<Item, String> priceCol = new TableColumn<>("Base Price");

		nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

		table.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol);

		table.setItems(observableItemList);

		HBox buttonContainer = new HBox();

		Button approveButton = GUIComponentFactory.createButton("Approve");
		approveButton.setOnAction(e -> {
			Item selectedItem = table.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				Alert confirmation = GUIComponentFactory.createConfirmation("confirmation",
						"Do you really want to approve this item?", "Please confirm your action");
				confirmation.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						ItemController.approveItem(selectedItem.getitemID());
					}
				});
			} else {
				Alert error = GUIComponentFactory.createError("Error", "No item selected",
						"Please select an item to take actions");
				error.showAndWait();
			}
		});

		Button declineButton = GUIComponentFactory.createButton("Decline");
		declineButton.setOnAction(e -> {
			Item selectedItem = table.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {

				Stage declineWindow = new Stage();
				declineWindow.setTitle("Item Decline Form");

				// Create the layout
				VBox layout = new VBox(15);
				layout.setAlignment(Pos.CENTER);
				layout.setPadding(new Insets(20));
				layout.setStyle(
						"-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");

				// Label for instructions
				Label instructionLabel = new Label("Enter decline reason :");
				instructionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
				instructionLabel.setTextFill(Color.web("#333333"));

				TextField reasonField = new TextField();
				reasonField.setPromptText("Enter decline reason");
				reasonField.setStyle(
						"-fx-padding: 8; -fx-border-color: #aaaaaa; -fx-border-radius: 5; -fx-background-radius: 5;");

				// Submit button
				Button submitReasonButton = GUIComponentFactory.createButton("Submit Reason");
				submitReasonButton.setOnAction(event -> {
					boolean isDeclined = ItemController.declineItem(selectedItem.getitemID(), reasonField.getText());
					if (isDeclined) {
						declineWindow.close();
					}
				});

				layout.getChildren().addAll(instructionLabel, reasonField, submitReasonButton);

				Scene scene = new Scene(layout, 350, 250);
				declineWindow.setScene(scene);
				declineWindow.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
				declineWindow.show();
			} else {
				Alert error = GUIComponentFactory.createError("Error", "No item selected",
						"Please select an item to take actions");
				error.showAndWait();
			}
		});

		Button logoutButton = GUIComponentFactory.createButton("Log Out");
		logoutButton.setOnAction(e -> {
			UserController.logout();
		});

		buttonContainer.getChildren().addAll(approveButton, declineButton, logoutButton);

		screen.getChildren().addAll(jumbotronMessageContainer, table, buttonContainer);

		Main.switchRoot(screen);

//		
//		
//		
//		
//		
//		
//		
//		
//------STYLING (Action and Logic doesn't exist here)--------------------------------------------------------------------------------------
		screen.setBackground(Main.defaultBg);
		screen.setSpacing(20);
		screen.setAlignment(Pos.TOP_CENTER);
		screen.setMinWidth(Main.viewPortWidth);
		screen.setMinHeight(10);

		jumbotronMessageContainer.setTranslateY(151);

		itemsLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		itemsLabel.setTextFill(Color.WHITE);

		pendingLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		pendingLabel.setTextFill(Color.web(Main.themeOrange));

		jumbotronMessageContainer.setMinWidth(Main.contentWidth);
		jumbotronMessageContainer.setMaxWidth(Main.contentWidth);
		jumbotronMessageContainer.setMinHeight(100);
		jumbotronMessageContainer.setAlignment(Pos.CENTER_LEFT);

		double tableWidth = Main.viewPortWidth * 0.7;
		table.setTranslateY(151);
		table.setMaxWidth(tableWidth);

		table.getColumns().forEach(column -> column.setPrefWidth(tableWidth / table.getColumns().size()));

		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.setTranslateY(151);
		buttonContainer.setSpacing(20);
	}
}
