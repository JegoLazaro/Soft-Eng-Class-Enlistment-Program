package STSWENGMCO;

import java.util.*;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

class Student {

    private final int studentNumber;
    private final Collection <Section> sections =new HashSet<>();

    Student (int studentNumber, Collection <Section> sections) {
        isTrue(studentNumber >= 0,"studentNumber cannot be negative, was:" + studentNumber);


        if(sections == null){
            throw new NullPointerException("sections can't be null");
        }

        this.studentNumber = studentNumber;
        this.sections.addAll(sections);
        this.sections.removeIf(Objects::isNull);
    }

    Student(int studentNumber) {
        this(studentNumber, Collections.emptyList());
    }

    void enlist  (Section newSection) {
        notNull(sections, "Section can't be null");
        sections.forEach( currSection -> currSection.checkForConflict(newSection));
        newSection.getRoom().checkRoomCapacity();
        newSection.checkPrereqSubjects(Section.subjects);
        sections.add(newSection);
        newSection.getRoom().addToRoom();
        newSection.lock();
        try{
            newSection.incrementNumberOfStudents();
            sections.add(newSection);
        }finally {
            newSection.unlock();
        }
    }

    void cancelEnlist(Section enlistedSection) {
        notNull(sections, "sections can't be null");
        sections.remove(enlistedSection);
        enlistedSection.getRoom().removeFromRoom();
    }

    Collection<Section> getSections() { return this.sections; }

    @Override
    public String toString() {
        return "Student #" + studentNumber;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Student student = (Student) o;

        return studentNumber == student.studentNumber;
    }

    @Override
    public int hashCode() {
        return studentNumber;
    }

}