package user;

public class ManagerStaff extends Staff{

    private float salary;


    @Override
    public boolean can(String action) {
        // TODO Auto-generated method stub
        return true;
    }
    // ====== Constructor ======
    public ManagerStaff(String staffID, String fullName, String phone, String username, String password, float salary) {
        
        super(staffID, fullName, phone, username, password);
        this.setSalary(salary);
    }
    public float getSalary() {
        return salary;
    }
    public void setSalary(float salary) {
        if(salary < 1000)
        {
            System.out.println("error: need more salary");
        }else
        {
            this.salary = salary;
        }
    }
    @Override
    public String toString() {
        return super.toString()+"ManagerStaff [\"Position: Manager salary=" + salary + "]";
    }
    
    @Override
    public boolean equals(Object obj) {
       
        ManagerStaff other = (ManagerStaff) obj;

        if (!super.equals(obj))
        {
            return false;
        }else
        {

            if (Float.floatToIntBits(salary) != Float.floatToIntBits(other.salary))
            {
                return false;
            }
        }
        return true;
    }

    
    

    
    
    
}
