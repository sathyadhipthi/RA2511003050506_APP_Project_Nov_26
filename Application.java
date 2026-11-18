package skillmatch.model;

public class Application {
    public enum Status {
        SUBMITTED, UNDER_REVIEW, SHORTLISTED, SELECTED, REJECTED, WITHDRAWN
    }

    private final int id;
    private final int studentId;
    private final int internshipId;
    private Status status;
    private final double matchScore;

    public Application(int id, int studentId, int internshipId,
                       Status status, double matchScore) {
        this.id = id;
        this.studentId = studentId;
        this.internshipId = internshipId;
        this.status = status;
        this.matchScore = matchScore;
    }

    public int getId()            { return id; }
    public int getStudentId()     { return studentId; }
    public int getInternshipId()  { return internshipId; }
    public Status getStatus()     { return status; }
    public double getMatchScore() { return matchScore; }

    /** Allowed life-cycle moves (see Figure 6). */
    public boolean canMoveTo(Status next) {
        switch (status) {
            case SUBMITTED:    return next == Status.UNDER_REVIEW || next == Status.WITHDRAWN
                                   || next == Status.REJECTED;
            case UNDER_REVIEW: return next == Status.SHORTLISTED || next == Status.REJECTED
                                   || next == Status.WITHDRAWN;
            case SHORTLISTED:  return next == Status.SELECTED || next == Status.REJECTED
                                   || next == Status.WITHDRAWN;
            default:           return false;
        }
    }

    public void moveTo(Status next) {
        if (!canMoveTo(next)) {
            throw new IllegalStateException(status + " -> " + next + " is not allowed");
        }
        this.status = next;
    }
}
