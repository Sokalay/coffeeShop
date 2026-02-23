public class Order {

    private String orderId;
    private Customer customer;
    private MenuItem item;
    private int quantity;
    private double totalPrice;

    private Staff createdBy;     // staff who created the order
    private boolean paid;        // for now: true when order is created (because we deduct balance)

    public Order(String orderId, Customer customer, MenuItem item, int quantity, Staff createdBy) {
        setOrderId(orderId);
        setCustomer(customer);
        setItem(item);
        setQuantity(quantity);
        setCreatedBy(createdBy);

        calculateTotal();
        this.paid = true;
    }

    // ===== Getters =====
    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public MenuItem getItem() { return item; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
    public Staff getCreatedBy() { return createdBy; }
    public boolean isPaid() { return paid; }

    // ===== Setters =====
    public void setOrderId(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) this.orderId = "UNKNOWN";
        else this.orderId = orderId.trim();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) this.quantity = 1;
        else this.quantity = quantity;
    }

    public void setCreatedBy(Staff createdBy) {
        this.createdBy = createdBy;
    }

    // ===== Methods =====
    public void calculateTotal() {
        if (item == null) {
            totalPrice = 0;
        } else {
            totalPrice = item.getPrice() * quantity;
        }
    }

    @Override
    public String toString() {
        String customerPhone = (customer == null) ? "UNKNOWN" : customer.getPhone();
        String itemName = (item == null) ? "UNKNOWN" : item.getName();
        String staffId = (createdBy == null) ? "UNKNOWN" : createdBy.getStaffId();

        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", item='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", createdByStaff='" + staffId + '\'' +
                ", paid=" + paid +
                '}';
    }
}