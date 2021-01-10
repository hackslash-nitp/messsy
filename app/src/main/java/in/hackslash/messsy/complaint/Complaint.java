package in.hackslash.messsy.complaint;

public class Complaint {
private String issue;
private String description;
private String image;
private String id;
public Complaint(String issue,String description,String id){
    this.issue=issue;
    this.description=description;
    this.id=id;
}

    public String getIssue() {
        return issue;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    // TODO Add complaint fields here
}
