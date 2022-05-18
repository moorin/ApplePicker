package todo.swu.applepicker;

public class SNSPostItem {
    String user_name;
    String comment;
    int resourceId;
    String friend_list;


    public SNSPostItem(String name, String comment, int resourceId, String friend_list) {
        this.user_name = name;
        this.comment = comment;
        this.resourceId = resourceId;
        this.friend_list = friend_list;
    }


    public String getUser_name() {
        return user_name;
    }

    public String getComment() {
        return comment;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getFriend_list() {
        return friend_list;
    }


    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setFriend_list(String friend_list) {
        this.friend_list = friend_list;
    }
}
