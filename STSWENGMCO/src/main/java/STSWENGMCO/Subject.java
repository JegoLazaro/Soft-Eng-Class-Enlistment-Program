package STSWENGMCO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isAlphanumeric;
import static org.apache.commons.lang3.Validate.*;

public class Subject {
    private final String subjectId;
    private final Collection<Subject> prerequisites = new HashSet<>();

    Subject(String subjectId, Collection<Subject> prerequisites) {
        notBlank(subjectId);
        notNull(prerequisites);
        isTrue(isAlphanumeric(subjectId), "subjectId must be alphanumeric, was: " + subjectId);
        this.subjectId = subjectId;
        this.prerequisites.addAll(prerequisites);
        this.prerequisites.removeIf(Objects::isNull);
    }
    Subject(String subjectId) {
        this(subjectId, Collections.emptyList());
    }

    void checkPrerequisites(Collection<Subject> prereqSubjsTaken) {
        notNull(prereqSubjsTaken);
        Collection<Subject> listSubjectsTaken = new HashSet<>(prereqSubjsTaken);
        if (!listSubjectsTaken.containsAll(prerequisites)) {
            Collection<Subject> PrereqsCopy = new HashSet<>(prerequisites);
            PrereqsCopy.removeAll(listSubjectsTaken);

            throw new PreReqMissingException(
                    "ERROR! Missing prerequisites: " + PrereqsCopy);
        }
    }

    public String toString(){
        return subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        return subjectId != null ? subjectId.equals(subject.subjectId) : subject.subjectId == null;
    }


    @Override
    public int hashCode() {
        return subjectId != null ? subjectId.hashCode() : 0;
    }

}
