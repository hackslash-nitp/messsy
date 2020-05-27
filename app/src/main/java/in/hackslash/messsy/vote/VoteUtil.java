package in.hackslash.messsy.vote;

public class VoteUtil {

    private onCompleteListener onCompleteListener;

    public VoteUtil createVote(Vote vote){

        // TODO Check if a user is logged in and return NO_USER if no logged in user is found

        // TODO Validate vote here and return WRONG_INPUT if anything is wrong

        // TODO Check DB if the vote exists and return DUPLICATE_COMPLAINT if user exists

        // TODO Create new user using FirebaseDatabase.getInstance().getReference().child('votes/' + vote.getMealType).push().set(vote) and return SUCCESS if successful

        // Leave as it is
        return this;
    }

    public void setOnCompleteListener(VoteUtil.onCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public interface onCompleteListener {
        void onComplete(Status status, Vote vote);
    }

    public enum Status {
        SUCCESS, DUPLICATE_VOTE, WRONG_INPUT, NO_USER
    }


}
