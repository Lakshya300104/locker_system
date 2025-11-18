package models;

public class Locker {
    private int lockerId;
    private String status;
    private int assignedTo;
    private String lockerPassword;
    private String assignedName;   // NEW FIELD

    public Locker(int lockerId, String status, int assignedTo, String lockerPassword, String assignedName) {
        this.lockerId = lockerId;
        this.status = status;
        this.assignedTo = assignedTo;
        this.lockerPassword = lockerPassword;
        this.assignedName = assignedName;
    }

    public int getLockerId() { return lockerId; }
    public String getStatus() { return status; }
    public int getAssignedTo() { return assignedTo; }
    public String getLockerPassword() { return lockerPassword; }

    // NEW GETTER
    public String getAssignedName() { return assignedName; }
}
