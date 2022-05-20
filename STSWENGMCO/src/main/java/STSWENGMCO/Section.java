package STSWENGMCO;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.*;

import static org.apache.commons.lang3.Validate.*;

class Section {

    public static String startTime;
    //public static String startTime;
    private final String sectionId;
    private Schedule schedule;
    private Room room;
    private Subject subjectId;
    //private String startTime;
    public static String endTime;
    private int numberOfStudents;
    public static Collection<Subject> subjects =new HashSet<>();
    private Collection<Subject> prerequisite =new HashSet<>();
    private final ReentrantLock lock = new ReentrantLock();

    Section (String sectionId, Schedule schedule, Room room, Subject subject, Collection <Subject> subjects, int numberOfStudents) {
        notBlank(sectionId, "sectionId can't be null or whitespace");
        isTrue(StringUtils.isAlphanumeric(sectionId),
                "sectionId must be alphanumeric, was: " + sectionId
        );

        //this.prerequisite = prerequisite;
        this.subjects.addAll(subjects);
        this.sectionId = sectionId;
        this.schedule = schedule;
        this.subjectId = subject;

        this.room = new Room(room.getNameOfRoom(), room.getMaxCapacity());
        isTrue(numberOfStudents >= 0, "numberOfStudents must be non-negative, was: " + numberOfStudents);
        this.numberOfStudents = numberOfStudents;
    }


    void checkForConflict(Section other) {
        if (this.schedule.equals(other.schedule)) {
            throw new ScheduleConflictException(
                    "schedule conflict between current section " + this
                            + " and new section " +
                            other + " at schedule " + this.schedule + " " + other.schedule);
        }

        if (this.subjectId.equals(other.subjectId)) {
            throw new SubjectConflictException(
                    "SubjectID " +
                            other.subjectId + "  conflict with another section");
        }
    }

    void checkPrereqSubjects(Collection<Subject> subjectsTaken) {
        notNull(subjectsTaken);
        Collection<Subject> copy = new HashSet<>(subjectsTaken);
        subjectId.checkPrerequisites(copy);

    }

   /* void checkPeriods (String startTime, String endTime ) {
        notBlank(startTime);
        notBlank(endTime);
        try {
            schedule.checkPeriodDuration(startTime, endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }*/

    void lock(){
        lock.lock();
    }

    void unlock(){
        lock.unlock();
    }

    void incrementNumberOfStudents(){
        numberOfStudents++;
    }

    int getNumberOfStudents(){
        return numberOfStudents;
    }
    public Room getRoom(){ return room; }

    @Override
    public String toString() {
        return sectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        return sectionId != null ? sectionId.equals(section.sectionId) : section.sectionId == null;
    }

    @Override
    public int hashCode() {
        return sectionId != null ? sectionId.hashCode() : 0;
    }

}