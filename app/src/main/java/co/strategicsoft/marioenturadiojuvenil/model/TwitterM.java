package co.strategicsoft.marioenturadiojuvenil.model;

public class TwitterM {

    public String getNicke() {
        return nicke;
    }

    public String getBody() {
        return body;
    }

    public void setNicke(String nicke) {
        this.nicke = nicke;
    }

    public void setBody(String body) {
        this.body = body;
    }

    String nicke;
    String body;
    String url;

    public String getUrl() {return url;}

    public TwitterM(String nicke, String body, String url) {
        this.nicke = nicke;
        this.body = body;
        this.url = url;
    }

}
