package in.hackslash.messsy.complaint;

public class NoticeBoxAdapterClass
{

    private String subject;
    private String Description;

    String timestamp;
    private String date, designation, hostel;



    public NoticeBoxAdapterClass (String subject, String Description, String date, String timestamp, String designation, String hostel){

        this.subject=subject;
        this.Description= Description;
        this.date=date;
        this.timestamp=timestamp;
        this.designation=designation;
        this.hostel=hostel;

    }
    public NoticeBoxAdapterClass ()
    {

    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }


}
