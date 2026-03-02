package user;

public class ManagerStaff extends Staff implements IStaff{

    private float salary;


    @Override
    public boolean can(String action) {
        // TODO Auto-generated method stub
        return true;
    }
    // ====== Constructor ======
    public ManagerStaff(Staff s, float salary) {
        
        super(s.getStaffId(), s.getFullName(), s.getPhone(), s.getUsername(), s.getPassword());
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
