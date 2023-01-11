package usth.edu.dropboxclient;

public class User {
    String email, username, password, phone, img;

    public User() {
    }

    public User(String email, String username, String password, String phone, String img) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.img = img;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
