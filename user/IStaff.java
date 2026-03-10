package user;
public interface IStaff {

    String getStaffId();
    String getUsername();
    boolean isActive();
    boolean checkPassword(String input);
    String getFullName();

    public abstract boolean can(String action);
}
