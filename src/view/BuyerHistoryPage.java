package view;

import java.util.ArrayList;

import client.Main;
import factories.GUIComponentFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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
import model.presentation.TransactionView;

public class BuyerHistoryPage {

	public static ArrayList<TransactionView> historyList = new ArrayList<>();

	public static void loadPage() {

		VBox screen = new VBox();
		HBox jumbotronMessageContainer = new HBox();

		Label historyLabel = new Label("'s history");

		Label customerNameLabel = new Label(Main.currentUser.getUsername());

		jumbotronMessageContainer.getChildren().addAll(customerNameLabel, historyLabel);

		ObservableList<TransactionView> observableHistoryList = FXCollections.observableArrayList(historyList);

		TableView<TransactionView> table = new TableView<>();
		TableColumn<TransactionView, String> idCol = new TableColumn<>("Transaction ID");
		TableColumn<TransactionView, String> dateCol = new TableColumn<>("Date");
		TableColumn<TransactionView, String> itemNameCol = new TableColumn<>("Item");
		TableColumn<TransactionView, String> itemCategoryCol = new TableColumn<>("itemCategory");
		TableColumn<TransactionView, String> itemSizeCol = new TableColumn<>("Size");
		TableColumn<TransactionView, Double> itemPriceCol = new TableColumn<>("Price");

		idCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
		itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		itemCategoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
		itemSizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
		itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

		table.getColumns().addAll(idCol, dateCol, itemNameCol, itemCategoryCol, itemSizeCol, itemPriceCol);

		table.setItems(observableHistoryList);

		screen.getChildren().addAll(GUIComponentFactory.createNavbar(Main.defaultPlaceholder),
				jumbotronMessageContainer, table);

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

		jumbotronMessageContainer.setTranslateY(97);

		historyLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		historyLabel.setTextFill(Color.WHITE);

		customerNameLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		customerNameLabel.setTextFill(Color.web(Main.themeOrange));

		jumbotronMessageContainer.setMinWidth(Main.contentWidth);
		jumbotronMessageContainer.setMaxWidth(Main.contentWidth);
		jumbotronMessageContainer.setMinHeight(100);
		jumbotronMessageContainer.setAlignment(Pos.CENTER_LEFT);

		double tableWidth = Main.viewPortWidth * 0.7;
		table.setTranslateY(97);
		table.setMaxWidth(tableWidth);

		table.getColumns().forEach(column -> column.setPrefWidth(tableWidth / table.getColumns().size()));

	}
}
