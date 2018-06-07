package clement.project_main.Data;

public class Message {
    private String time,msg,name;
    private int picId;

    public Message(String time, String msg, String name, int picId){
        super();
        this.time = time;
        this.msg = msg;
        this.name = name;
        this.picId = picId;
    }

    public String getTime(){
        return time;
    }
    public String getMsg(){
        return msg;
    }
    public String getName(){
        return name;
    }
    public int getPicId(){
        return picId;
    }
}
