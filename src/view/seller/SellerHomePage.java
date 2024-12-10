package view.seller;

import java.util.ArrayList;

import client.Main;
import controller.ItemController;
import factories.GUIComponentFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.domain.Item;
import view.seller.forms.addItemForm;
import view.seller.forms.editItemForm;

public class SellerHomePage {
	public static ArrayList<Item> itemList = new ArrayList<>();

	public static void loadPage() {
		VBox screen = new VBox();
		HBox jumbotronMessageContainer = new HBox();

		Label sellerNameLabel = new Label(Main.currentUser.getUsername());
		Label itemsLabel = new Label("'s uploads");

		jumbotronMessageContainer.getChildren().addAll(sellerNameLabel, itemsLabel);

		ObservableList<Item> observableItemList = FXCollections.observableArrayList(itemList);

		TableView<Item> table = new TableView<>();
		TableColumn<Item, String> nameCol = new TableColumn<>("Item Name");
		TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
		TableColumn<Item, String> sizeCol = new TableColumn<>("Size");
		TableColumn<Item, String> priceCol = new TableColumn<>("Base Price");
		TableColumn<Item, String> statusCol = new TableColumn<>("Approval Status");

		nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

		table.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol, statusCol);

		table.setItems(observableItemList);

		HBox buttonContainer = new HBox();

		Button detailButton = GUIComponentFactory.createButton("View Details");
		detailButton.setOnAction(e -> {
			Item selectedItem = table.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				ItemController.viewDetail(selectedItem);
			} else {
				GUIComponentFactory.createError("Error", "No item selected", "Please select an item to take actions")
						.showAndWait();
			}
		});

		Button uploadButton = GUIComponentFactory.createButton("Add Item");
		uploadButton.setOnAction(e -> {

			Stage uploadWindow = addItemForm.createAddForm();
			uploadWindow.show();

		});

		Button editButton = GUIComponentFactory.createButton("Edit Item");
		editButton.setOnAction(e -> {
			Item selectedItem = table.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {

				editItemForm.createEditForm(selectedItem).show();

			} else {
				GUIComponentFactory.createError("Error", "No item selected", "Please select an item to take actions")
						.showAndWait();
			}
		});

		Button removeButton = GUIComponentFactory.createButton("Remove Item");
		removeButton.setOnAction(e -> {
			Item selectedItem = table.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				GUIComponentFactory.createConfirmation("Confirmation", "Do you want to remove this item?",
						"Select the option to proceed").showAndWait().ifPresent(response -> {
							if (response == ButtonType.OK) {
								ItemController.deleteItem(selectedItem.getitemID());
								GUIComponentFactory
										.createNotification("Notification", "Item deleted", "Press OK to proceed")
										.showAndWait();
								ItemController.viewItem();
							}
						});
			} else {
				GUIComponentFactory.createError("Error", "No item selected", "Please select an item").showAndWait();
			}
		});

		buttonContainer.getChildren().addAll(detailButton, uploadButton, editButton, removeButton);

		screen.getChildren().addAll(GUIComponentFactory.createNavbar(""), jumbotronMessageContainer, table,
				buttonContainer);

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

		jumbotronMessageContainer.setTranslateY(68);

		itemsLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		itemsLabel.setTextFill(Color.WHITE);

		sellerNameLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		sellerNameLabel.setTextFill(Color.web(Main.themeOrange));

		jumbotronMessageContainer.setMinWidth(Main.contentWidth);
		jumbotronMessageContainer.setMaxWidth(Main.contentWidth);
		jumbotronMessageContainer.setMinHeight(100);
		jumbotronMessageContainer.setAlignment(Pos.CENTER_LEFT);

		double tableWidth = Main.viewPortWidth * 0.7;
		table.setTranslateY(68);
		table.setMaxWidth(tableWidth);

		table.getColumns().forEach(column -> column.setPrefWidth(tableWidth / table.getColumns().size()));

		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.setTranslateY(68);
		buttonContainer.setSpacing(20);

		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				String itemStatus = newSelection.getItemStatus();
				if ("Waiting for approval".equalsIgnoreCase(itemStatus)) {
					editButton.setDisable(true); // Disable button
					styleInactiveBtn(editButton);
				} else {
					editButton.setDisable(false); // Enable button
					styleActiveBtn(editButton);
				}
			} else {
				editButton.setDisable(true); // Disable button when no item is selected
			}
		});
	}

	public static void styleActiveBtn(Button btn) {
		btn.setStyle("-fx-background-color: " + Main.themeOrange
				+ "; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

		btn.setOnMouseEntered(e -> {
			btn.setStyle(
					"-fx-background-color: #C67025; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
		});
		btn.setOnMouseExited(e -> {
			btn.setStyle("-fx-background-color: " + Main.themeOrange
					+ "; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
		});
	}

	public static void styleInactiveBtn(Button btn) {
		btn.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

	}
}
