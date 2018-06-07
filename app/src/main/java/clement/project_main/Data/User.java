package clement.project_main.Data;

/**
 * Created by clement on 8/10/16.
 */
public class User {
    private String email;
    private String password;
    private String name;
    private String dip;
    private String gender;
    private String UserUID;
    private String phone;
    private String carnum;
    private String Uri;

    public User(){
    }
    public String getUserUID(){
        return UserUID;
    }
    public void setUserUID(String UserUID){
        this.UserUID=UserUID;
    }

    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getCarnum(){
        return carnum;
    }
    public void setCarnum(String carnum){
        this.carnum = carnum;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getDip(){
        return dip;
    }

    public void setDip(String dip){
        this.dip=dip;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender=gender;
    }

    public String getUri(){
        return Uri;
    }
    public void setUri(String Uri){
        this.Uri=Uri;
    }

}
