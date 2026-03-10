package user;

import controller.CoffeeShop;

public class BaristaStaff extends Staff{
    
    String salary;
    Boolean active;

    @Override
    public boolean can(String action) {
       if(action.equals(CoffeeShop.CREATE_MENU_ITEM) || action.equals(CoffeeShop.VIEW_ORDERS) || action.equals(CoffeeShop.UPDATE_ORDER_STATUS))
        {
            return true;
        }
        // TODO Auto-generated method stub
        return false;
    }

    // ====== Constructor ======
    public BaristaStaff(String staffId, String fullName, String phone,
                 String username, String password, String position) {
        super(staffId, fullName, phone, username, password);

        this.active = true;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString()+"BaristaStaff [salary=" + salary + ", active=" + active + "]";
    }
}
