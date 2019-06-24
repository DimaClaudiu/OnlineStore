package Others;

import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;

public class Notification
{
    private LocalDate date;
    public enum NotificationType {ADD, REMOVE, MODIFY; };
    NotificationType notificationType;
    private int depID;
    private int prodID;

    public Notification(NotificationType notificationType, int prodID, int depID)
    {
        date = LocalDate.now();
        this.notificationType = notificationType;
        this.prodID = prodID;
        this.depID = depID;
    }

    public String getNotification()
    {
        String type = new String();
        if(notificationType == NotificationType.ADD)
            type = "ADD";
        else if(notificationType == NotificationType.REMOVE)
            type = "REMOVE";
        else
            type = "MODIFY";

        return type + ";" +
                Integer.toString(prodID) + ";" +
                Integer.toString(depID);
    }

}
