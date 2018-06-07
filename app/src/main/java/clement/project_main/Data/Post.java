package clement.project_main.Data;


/**
 * Created by clement on 9/8/16.
 */

public class Post {

    private String from;
    private String to;
    private String date;
    private String time;
    private String people;
    private String note;
    private String price;
    private String UID;
    private String Uri;
    private String vehicle;
    private String name;
    private String user1,user2,user3;
    private String key;
    private String Image,Type;

    public Post() {
    }


    public String getFrom(){
        return from;
    }
    public void setFrom(String from){
        this.from=from;
    }

    public String getTo(){
        return to;
    }
    public void setTo(String to){
        this.to = to;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time=time;
    }

    public String getPeople(){
        return people;
    }

    public void setPeople(String people){
        this.people=people;
    }
    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note=note;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price=price;
    }

    public String getUID(){
        return UID;
    }
    public void setUID(String UID){
        this.UID=UID;
    }

    public String getUri(){
        return Uri;
    }
    public void setUri(String Uri){
        this.Uri=Uri;
    }

    public String getVehicle(){
        return vehicle;
    }
    public void setVehicle(String vehicle){
        this.vehicle=vehicle;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getUser1(){return user1;}

    public void setUser1(String user1){
        this.user1=user1;
    }

    public String getUser2(){return user2;}

    public void setUser2(String user2){
        this.user2=user2;
    }

    public String getUser3(){return user3;}

    public void setUser3(String user3){
        this.user3=user3;
    }

    public String getKey(){
        return key;}

    public void setKey(String key){
        this.key=key;
    }

    public String getImage(){
        return Image;
    }
    public void setImage(String Image){
        this.Image=Image;
    }

    public String getType(){return Type;}
    public void setType(String Type){this.Type=Type;}

}
