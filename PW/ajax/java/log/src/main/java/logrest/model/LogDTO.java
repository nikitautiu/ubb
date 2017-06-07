package logrest.model;

import java.util.Date;

/**
 * Created by vitiv on 6/6/17.
 */
public class LogDTO {
    private long id;
    private String type;
    private Severity severity;
    private Date date;
    private String text;
    private String username;

    public LogDTO() {
    }

    public LogDTO(Log log) {
        id = log.getId();
        type = log.getType();
        severity = log.getSeverity();
        date = log.getDate();
        text = log.getText();
        username = log.getCreator().getUsername();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
