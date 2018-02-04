package test.notification.com.notification;


public class Quote {


    public String name;
    public String content;
    public String more;
    public String imgAddress;
    public int id;

    public Quote(String name, String content, String more, String imgAddress, int id) {
        this.name = name;
        this.content = content;
        this.more = more;
        this.imgAddress = imgAddress;
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
