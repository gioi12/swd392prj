package entity;

import java.util.Date;

public class User {
    private int ID;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Phone;
    private Role Role;
    private Boolean GetNotifications;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;
    private Boolean IsDelete;
    private int DeletedBy;

    public User() {}

    public User(int ID, String FirstName, String LastName, String Email, String Phone, Role Role, Boolean GetNotifications, Date CreatedAt, Date UpdatedAt, Date DeletedAt, Boolean IsDelete, int DeletedBy) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Phone = Phone;
        this.Role = Role;
        this.GetNotifications = GetNotifications;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDelete = IsDelete;
        this.DeletedBy = DeletedBy;
    }

    public User(int ID, String FirstName, String LastName, String Email, String Phone, Role Role, Date CreatedAt, Date UpdatedAt, Date DeletedAt, Boolean IsDelete, int DeletedBy) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Phone = Phone;
        this.Role = Role;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDelete = IsDelete;
        this.DeletedBy = DeletedBy;
    }
    public User(int ID, String Email) {
        this.ID = ID;
        this.Email = Email;
    }
    public User(String email) {
    this.Email = email;
}


    // Getters and setters for all fields except Address
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public Role getRole() {
        return Role;
    }

    public void setRole(Role Role) {
        this.Role = Role;
    }

    public Boolean getGetNotifications() {
        return GetNotifications;
    }

    public void setGetNotifications(Boolean GetNotifications) {
        this.GetNotifications = GetNotifications;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date UpdatedAt) {
        this.UpdatedAt = UpdatedAt;
    }

    public Date getDeletedAt() {
        return DeletedAt;
    }

    public void setDeletedAt(Date DeletedAt) {
        this.DeletedAt = DeletedAt;
    }

    public Boolean getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(Boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public int getDeletedBy() {
        return DeletedBy;
    }

    public void setDeletedBy(int DeletedBy) {
        this.DeletedBy = DeletedBy;
    }

    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", FirstName=" + FirstName + ", LastName=" + LastName + ", Email=" + Email + ", Phone=" + Phone + ", Role=" + Role + ", CreatedAt=" + CreatedAt + ", UpdatedAt=" + UpdatedAt + ", DeletedAt=" + DeletedAt + ", IsDelete=" + IsDelete + ", DeletedBy=" + DeletedBy + '}';
    }
}
