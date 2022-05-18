package todo.swu.applepicker;

public class TaskItem {
    String subject;
    String part;
    int resourceId;

    public TaskItem(String subject, String part, int resourceId) {
        this.subject = subject;
        this.part = part;
        this.resourceId = resourceId;
    }

    public String getSubject() {
        return subject;
    }

    public String getPart() {
        return part;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

}
