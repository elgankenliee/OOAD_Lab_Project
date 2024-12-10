package view.seller.forms;

import controller.ItemController;
import factories.GUIComponentFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.domain.Item;

public class editItemForm {
	public static Stage createEditForm(Item item) {
		Stage editWindow = new Stage();
		editWindow.setTitle("Edit Item Details");

		// Create layout
		GridPane layout = new GridPane();
		layout.setHgap(10); // Horizontal spacing between columns
		layout.setVgap(15); // Vertical spacing between rows
		layout.setPadding(new Insets(20));
		layout.setStyle(
				"-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");

		// Labels and fields
		Label nameLabel = new Label("Item Name:");
		TextField nameField = new TextField(item.getItemName());

		Label categoryLabel = new Label("Item Category:");
		TextField categoryField = new TextField(item.getItemCategory());

		Label sizeLabel = new Label("Item Size:");
		TextField sizeField = new TextField(item.getItemSize());

		Label priceLabel = new Label("Item Price:");
		TextField priceField = new TextField(String.valueOf(item.getItemPrice()));

		// Add labels and fields to GridPane
		layout.add(nameLabel, 0, 0);
		layout.add(nameField, 1, 0);
		layout.add(categoryLabel, 0, 1);
		layout.add(categoryField, 1, 1);
		layout.add(sizeLabel, 0, 2);
		layout.add(sizeField, 1, 2);
		layout.add(priceLabel, 0, 3);
		layout.add(priceField, 1, 3);

		// Submit button
		Button submitButton = GUIComponentFactory.createButton("Save Changes");
		submitButton.setOnAction(event -> {
			boolean updateSuccess = ItemController.editItem(item.getitemID(), nameField.getText(),
					categoryField.getText(), sizeField.getText(), priceField.getText());
			if (updateSuccess) {
				Alert notification = GUIComponentFactory.createNotification("Information", "Update success",
						"You can update this item again if needed");
				notification.showAndWait();
				editWindow.close();

			}
		});

		layout.add(submitButton, 1, 4); // Place the submit button

		editWindow.setScene(new Scene(layout, 400, 250));
		editWindow.initModality(Modality.APPLICATION_MODAL);
		return editWindow;
	}
}
