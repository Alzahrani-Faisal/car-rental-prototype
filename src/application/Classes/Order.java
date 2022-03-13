package application.Classes;

public class Order
{
    private String customerEmail;
    private int carID;
    private String date;
    private String status;
    private double price;

    public Order(String customerEmail, int carID, String date, double price, String status) {
        this.customerEmail = customerEmail;
        this.carID = carID;
        this.date = date;
        this.status = status;
        this.price = price;
    }


    // Setters & Getters

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerID(String newCustomerEmail) {
        this.customerEmail = newCustomerEmail;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int newCarID) {
        this.carID = newCarID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String newDate) {
        this.date = newDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String newStatus) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    @Override
    public String toString() {
        return "customer Email: " + customerEmail +
                " car ID: " + carID +
                " date: " + date +
                " price: " + price +
                " status: " + status;
    }
}
