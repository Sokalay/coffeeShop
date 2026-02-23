public interface IStaff {

    String getStaffId();
    String getUsername();
    String getPosition();
    boolean isActive();
    boolean checkPassword(String input);
    String getFullName();

    boolean can(String action);
}
