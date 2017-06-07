package logrest.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "log")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "type")
	private String type;

	@Column(name = "severity")
	private Severity severity;

	@Column(name = "text")
	private String text;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Enumerated(EnumType.ORDINAL)
    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Column(name = "date")
	private Date date;

	@ManyToOne(fetch=FetchType.EAGER)
    private User creator;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
