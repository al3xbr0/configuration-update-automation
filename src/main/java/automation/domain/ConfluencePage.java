package automation.domain;

import java.io.Serializable;

public class ConfluencePage implements Serializable {

    private final String spaceKey;
    private final String title;
    private String id;
    private int version;
    private final String body;

    public String getSpaceKey() {
        return spaceKey;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getBody() {
        return body;
    }

    public ConfluencePage(String spaceKey, String title, String body) {
        this.spaceKey = spaceKey;
        this.title = title;
        this.body = body;
        this.id = "";
        this.version = 0;
    }

    @SuppressWarnings("unused")
    public boolean exists() {
        return !"".equals(id);
    }
}
