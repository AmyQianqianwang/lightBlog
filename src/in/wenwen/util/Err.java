package in.wenwen.util;

public class Err {
    private String err;
    private String msg;
    public Err() {
        this("1", "");
    }
    public Err(String msg) {
        this("1", msg);
    }
    public Err(String err, String msg) {
        this.err = err;
        this.msg = msg;
    }
    public String getErr() {
        return err;
    }
    public void setErr(String err) {
        this.err = err;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
