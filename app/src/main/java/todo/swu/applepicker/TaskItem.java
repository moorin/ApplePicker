package todo.swu.applepicker;

import com.google.firebase.Timestamp;

public class TaskItem {
    Timestamp timestamp;
    String subject;
    String part;
    boolean achievement;

    public TaskItem(){}

    public TaskItem(Timestamp timestamp, String subject, String part, boolean achievement) {
        this.timestamp = timestamp;
        this.subject = subject;
        this.part = part;
        this.achievement = achievement;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    };

    public String getSubject() {
        return subject;
    }

    public String getPart() {
        return part;
    }

    public boolean getAchievement() {
        return achievement;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public void switchAchievement() {
        this.achievement = !achievement;
    }

}
