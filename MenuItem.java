import user.IStaff;

public class MenuItem {

    private String itemId;
    private String name;
    private String category;   // Coffee, Tea, Cake...
    private String size;       // S, M, L, None
    private double price;
    private boolean available;

    private IStaff createdBy;

    public MenuItem(String itemId, String name, String category, String size,
                    double price, boolean available, IStaff createdBy) {

        setCreatedBy(createdBy);   // ✅ use setter (with validation)
        setItemId(itemId);
        setName(name);
        setCategory(category);
        setSize(size);
        setPrice(price);
        setAvailable(available);
    }

    // ===== Getters =====
    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getSize() { return size; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }
    public IStaff getCreatedBy() { return createdBy; }

    // ===== Setters =====
    public void setCreatedBy(IStaff createdBy) {
        // Enforce: staff must exist
        if (createdBy == null) {
            System.out.println("Cannot create menu item: Staff is required.");
        } else {
            this.createdBy = createdBy;
        }
    }

    public void setItemId(String itemId) {
        if (isBlank(itemId)) this.itemId = "UNKNOWN";
        else this.itemId = itemId.trim();
    }

    public void setName(String name) {
        if (isBlank(name)) this.name = "No Name";
        else this.name = name.trim();
    }

    public void setCategory(String category) {
        if (isBlank(category)) this.category = "Unknown";
        else this.category = category.trim();
    }

    public void setSize(String size) {
        if (isBlank(size)) {
            this.size = "None";
        } else {
            String s = size.trim().toUpperCase();
            if (s.equals("S") || s.equals("M") || s.equals("L") || s.equals("NONE")) {
                this.size = s.equals("NONE") ? "None" : s;
            } else {
                this.size = "None";
            }
        }
    }

    public void setPrice(double price) {
        if (price < 0) this.price = 0;
        else this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "MenuItem [itemId=" + itemId + ", name=" + name + ", category=" + category + ", size=" + size
                + ", price=" + price + ", available=" + available + ", createdBy=" + createdBy + "]";
    }

    
}