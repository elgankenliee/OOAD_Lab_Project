package model.presentation;

public class TransactionView {

	private String transactionID;
	private String itemName;
	private String itemCategory;
	private String itemSize;
	private double itemPrice;
	private String transactionDate;

	public TransactionView(String transactionID, String itemName, String itemCategory, String itemSize,
			double itemPrice, String transactionDate) {
		super();
		this.transactionID = transactionID;
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
		this.transactionDate = transactionDate;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

}
