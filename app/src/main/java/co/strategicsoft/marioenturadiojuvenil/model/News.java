package co.strategicsoft.marioenturadiojuvenil.model;


public class News {
    private String title;
    private String body;
    private int icon;

    public News(int icon, String title, String body){
        this.icon = icon;
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
