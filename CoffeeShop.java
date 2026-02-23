import java.util.ArrayList;

public class CoffeeShop {

    // =========================
    // 1) BASIC INFO
    // =========================
    private String shopName;
    private String address;

    // =========================
    // 2) "TABLES" (ArrayList)
    // =========================
    private ArrayList<Staff> staffs;
    private ArrayList<Customer> customers;
    private ArrayList<MenuItem> menuItems;
    private ArrayList<Order> orders;

    // =========================
    // 3) LOGIN DEPENDENCY
    // =========================
    private Staff loggedInStaff;   // null = no staff login

    // =========================
    // 4) FEEDBACK MESSAGE
    // =========================
    private String lastMessage;

    // =========================
    // CONSTRUCTOR
    // =========================
    public CoffeeShop(String shopName, String address) {
        setShopName(shopName);
        setAddress(address);

        staffs = new ArrayList<>();
        customers = new ArrayList<>();
        menuItems = new ArrayList<>();
        orders = new ArrayList<>();

        loggedInStaff = null;

        // Default admin (so system can start)
        seedDefaultAdmin();

        lastMessage = "CoffeeShop created. Default staff: admin / 1234";
    }

    // =========================
    // GETTERS / SETTERS
    // =========================
    public String getShopName() { return shopName; }
    public String getAddress() { return address; }
    public String getLastMessage() { return lastMessage; }

    public boolean isStaffLoggedIn() { return loggedInStaff != null; }
    public Staff getLoggedInStaff() { return loggedInStaff; }

    public void setShopName(String shopName) {
        if (isBlank(shopName)) this.shopName = "CoffeeShop";
        else this.shopName = shopName.trim();
    }

    public void setAddress(String address) {
        if (isBlank(address)) this.address = "Unknown";
        else this.address = address.trim();
    }

    private void setLastMessage(String msg) {
        lastMessage = msg;
    }

    // =========================
    // DEFAULT STAFF (BOOTSTRAP)
    // =========================
    private void seedDefaultAdmin() {
        Staff admin = new Staff("S001", "Admin", "010000000", "admin", "1234", "Manager");
        staffs.add(admin);
    }

    // =========================
    // LOGIN CHECK (dependency)
    // =========================
    private boolean requireStaffLogin() {
        if (loggedInStaff == null) {
            setLastMessage("Action denied: staff must login first.");
            return false;
        }

        if (!loggedInStaff.isActive()) {
            loggedInStaff = null;
            setLastMessage("Action denied: staff is inactive (auto logout).");
            return false;
        }

        return true;
    }

    // =========================
    // STAFF LOGIN / LOGOUT
    // =========================
    public void staffLogin(String username, String password) {

        if (isBlank(username) || password == null) {
            setLastMessage("Login failed: missing username/password.");
            return;
        }

        for (int i = 0; i < staffs.size(); i++) {
            Staff s = staffs.get(i);

            if (s.getUsername().equalsIgnoreCase(username.trim())) {

                if (!s.isActive()) {
                    setLastMessage("Login failed: staff is inactive.");
                    return;
                }

                if (!s.checkPassword(password)) {
                    setLastMessage("Login failed: wrong password.");
                    return;
                }

                loggedInStaff = s;
                setLastMessage("Login success. Welcome " + s.getFullName() + "!");
                return;
            }
        }

        setLastMessage("Login failed: username not found.");
    }

    public void staffLogout() {
        loggedInStaff = null;
        setLastMessage("Logged out successfully.");
    }

    // =========================
    // CREATE STAFF (staff-only)
    // =========================
    public void createStaff(String staffId, String fullName, String phone,
                            String username, String password, String position) {

        if (!requireStaffLogin()) return;

        if (isBlank(staffId) || isBlank(username)) {
            setLastMessage("Cannot create staff: staffId/username is empty.");
            return;
        }

        // duplicate username check
        for (int i = 0; i < staffs.size(); i++) {
            if (staffs.get(i).getUsername().equalsIgnoreCase(username.trim())) {
                setLastMessage("Cannot create staff: username already exists.");
                return;
            }
        }

        staffs.add(new Staff(staffId, fullName, phone, username, password, position));
        setLastMessage("Staff created successfully.");
    }

    // =========================
    // CREATE CUSTOMER (staff-only)
    // =========================
    public void createCustomer(String customerId, String fullName, String phone,
                               String password, double balance) {

        if (!requireStaffLogin()) return;

        if (isBlank(customerId) || isBlank(phone)) {
            setLastMessage("Cannot create customer: customerId/phone is empty.");
            return;
        }

        // duplicate check
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerId().equalsIgnoreCase(customerId.trim())) {
                setLastMessage("Cannot create customer: customerId already exists.");
                return;
            }
            if (customers.get(i).getPhone().equals(phone.trim())) {
                setLastMessage("Cannot create customer: phone already exists.");
                return;
            }
        }

        customers.add(new Customer(customerId, fullName, phone, password, balance, loggedInStaff));
        setLastMessage("Customer created successfully.");
    }

    // =========================
    // CREATE MENU ITEM (staff-only)
    // =========================
    public void createMenuItem(String itemId, String name, String category, String size,
                               double price, boolean available) {

        if (!requireStaffLogin()) return;

        if (isBlank(itemId)) {
            setLastMessage("Cannot create menu item: itemId is empty.");
            return;
        }

        // duplicate itemId check
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getItemId().equalsIgnoreCase(itemId.trim())) {
                setLastMessage("Cannot create menu item: itemId already exists.");
                return;
            }
        }

        menuItems.add(new MenuItem(itemId, name, category, size, price, available, loggedInStaff));
        setLastMessage("Menu item created successfully.");
    }

    public void setMenuItemAvailability(String itemId, boolean available) {
        if (!requireStaffLogin()) return;

        MenuItem item = findMenuItemById(itemId);
        if (item == null) {
            setLastMessage("Menu item not found.");
            return;
        }

        item.setAvailable(available);
        setLastMessage("Menu item availability updated.");
    }

    // =========================
    // CREATE ORDER (staff-only)
    // input: customer phone + itemId
    // =========================
    public void createOrder(String customerPhone, String itemId, int qty) {

        if (!requireStaffLogin()) return;

        if (isBlank(customerPhone) || isBlank(itemId) || qty <= 0) {
            setLastMessage("Cannot create order: invalid input.");
            return;
        }

        Customer customer = findCustomerByPhone(customerPhone);
        if (customer == null) {
            setLastMessage("Cannot create order: customer not found.");
            return;
        }
        if (!customer.isActive()) {
            setLastMessage("Cannot create order: customer is inactive.");
            return;
        }

        MenuItem item = findMenuItemById(itemId);
        if (item == null) {
            setLastMessage("Cannot create order: menu item not found.");
            return;
        }
        if (!item.isAvailable()) {
            setLastMessage("Cannot create order: menu item is not available.");
            return;
        }

        double total = item.getPrice() * qty;

        if (!customer.deductBalance(total)) {
            setLastMessage("Cannot create order: insufficient balance.");
            return;
        }

        String orderId = "ORD" + (orders.size() + 1);
        orders.add(new Order(orderId, customer, item, qty, loggedInStaff));

        setLastMessage("Order created successfully: " + orderId);
    }

    // =========================
    // PRINT TABLES
    // =========================
    public void printCustomers() {
        System.out.println("\n--- Customers (" + customers.size() + ") ---");
        if (customers.size() == 0) System.out.println("No customers.");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ") " + customers.get(i));
        }
    }

    public void printMenuItems() {
        System.out.println("\n--- Menu Items (" + menuItems.size() + ") ---");
        if (menuItems.size() == 0) System.out.println("No menu items.");
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println((i + 1) + ") " + menuItems.get(i));
        }
    }

    public void printOrders() {
        System.out.println("\n--- Orders (" + orders.size() + ") ---");
        if (orders.size() == 0) System.out.println("No orders.");
        for (int i = 0; i < orders.size(); i++) {
            System.out.println((i + 1) + ") " + orders.get(i));
        }
    }

    // =========================
    // FIND HELPERS (keep inside CoffeeShop)
    // =========================
    private Customer findCustomerByPhone(String phone) {
        if (isBlank(phone)) return null;

        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getPhone().equals(phone.trim())) {
                return customers.get(i);
            }
        }
        return null;
    }

    private MenuItem findMenuItemById(String itemId) {
        if (isBlank(itemId)) return null;

        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getItemId().equalsIgnoreCase(itemId.trim())) {
                return menuItems.get(i);
            }
        }
        return null;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}