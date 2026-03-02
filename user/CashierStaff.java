package user;

import controller.CoffeeShop;

public class CashierStaff extends Staff{

    private float salary;

    public CashierStaff(Staff s1, float salary)
    {
        super(s1.getStaffId(), s1.getFullName(), s1.getPhone(), s1.getUsername(), s1.getPassword());
        this.setSalary(salary);
    }

    public float getSalary() {
        
        return salary;
    }

    public void setSalary(float salary) {
        if(salary<400)
        {
            System.out.println("error: need more salary");
        }else
        {
            this.salary = salary;
        }
    }


    @Override
    public boolean equals(Object obj) {
        CashierStaff other = (CashierStaff) obj;

        if(!super.equals(obj))
        {
            return false;
        }
        else
        {
            if (Float.floatToIntBits(salary) != Float.floatToIntBits(other.salary))
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return super.toString() + 
                ", salary=" + salary +
                '}';
    }


    @Override
    public boolean can(String action) {
        if(action.equals(CoffeeShop.CREATE_CUSTOMER) || action.equals(CoffeeShop.CREATE_ORDER) || action.equals(CoffeeShop.VIEW_CUSTOMERS) || action.equals(CoffeeShop.VIEW_ORDERS))
        {
            return true;
        }
        return false;
    }

}
