package kenneth.jf.siaapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static android.R.id.message;

/**
 * Created by Kenneth on 13/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private Long id;
    private String message;
    private String subject;
    private String senderName;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
