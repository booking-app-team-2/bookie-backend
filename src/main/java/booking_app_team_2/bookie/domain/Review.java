package booking_app_team_2.bookie.domain;

public abstract class Review {
    private Long id = null;
    private float grade;
    private String comment;
    private long dateOfCreation;
    private boolean isApproved = false;
    private boolean isReported = false;
    private Guest reviewer;
}
