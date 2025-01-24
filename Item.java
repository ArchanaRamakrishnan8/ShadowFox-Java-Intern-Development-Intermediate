//Data model for inventory items
import javafx.beans.property.*;

 class Item {
    private final StringProperty itemName;
    private final IntegerProperty quantity;
    private final DoubleProperty price;

    public Item(String itemName, int quantity, double price) {
        this.itemName = new SimpleStringProperty(itemName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public DoubleProperty totalPriceProperty() {
        return new SimpleDoubleProperty(quantity.get() * price.get());
    }
}
