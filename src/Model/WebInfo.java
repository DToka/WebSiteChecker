package Model;

import java.io.Serializable;

public class WebInfo implements Serializable {
    private String website;
    private String result;

    public WebInfo(String website, String result) {
        this.website = website;
        this.result = result;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }



}
