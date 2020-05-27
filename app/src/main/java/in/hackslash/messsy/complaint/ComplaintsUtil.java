package in.hackslash.messsy.complaint;


public class ComplaintsUtil {

    private onCompleteListener onCompleteListener;

    public ComplaintsUtil createComplaint(Complaint complaint){

        // TODO Check if a user is logged in and return NO_USER if no logged in user is found

        // TODO Validate complaint here and return WRONG_INPUT if anything is wrong

        // TODO Check DB if the complaint exists and return DUPLICATE_COMPLAINT if complaint exists

        // TODO Create new complaint using FirebaseDatabase.getInstance().getReference().child('complaints/').push().set(complaint) and return SUCCESS if successful

        // Leave as it is
        return this;
    }

    public ComplaintsUtil fetchNotice() {

        // TODO Check DB if a notice exists and return NOTICE_DOES_NOT_EXIST if notice is not found

        // TODO Return the notice in a Notice object if found with a SUCCESS Status

        // Leave as it is
        return this;

    }

    public void setOnCompleteListener(ComplaintsUtil.onCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public interface onCompleteListener {
        void onComplete(Status status, Complaint complaint);
    }

    public enum Status {
        SUCCESS, DUPLICATE_COMPLAINT, WRONG_INPUT, NOTICE_DOES_NOT_EXIST, NO_USER
    }
}

