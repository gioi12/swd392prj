package entity;
// 


import java.util.Date;

public class GoogleLogin {
//DAT

    private String id;
    private String email;
    private boolean verified_email;
    private String given_name;
    private String family_name;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;
    private boolean IsDelete;
    private int DeletedByID;
    private User user;

    public GoogleLogin() {
    }

    public GoogleLogin(String id, String email, boolean verified_email, String given_name, String family_name, Date CreatedAt, Date UpdatedAt, Date DeletedAt, boolean IsDelete, int DeletedByID, User user) {
        this.id = id;
        this.email = email;
        this.verified_email = verified_email;
        this.given_name = given_name;
        this.family_name = family_name;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.DeletedAt = DeletedAt;
        this.IsDelete = IsDelete;
        this.DeletedByID = DeletedByID;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified_email() {
        return verified_email;
    }

    public void setVerified_email(boolean verified_email) {
        this.verified_email = verified_email;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
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

    public boolean isIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public int getDeletedByID() {
        return DeletedByID;
    }

    public void setDeletedByID(int DeletedByID) {
        this.DeletedByID = DeletedByID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GoogleLogin{" + "id=" + id + ", email=" + email + ", verified_email=" + verified_email + ", given_name=" + given_name + ", family_name=" + family_name + ", CreatedAt=" + CreatedAt + ", UpdatedAt=" + UpdatedAt + ", DeletedAt=" + DeletedAt + ", IsDelete=" + IsDelete + ", DeletedByID=" + DeletedByID + ", user=" + user + '}';
    }
    
    
}
