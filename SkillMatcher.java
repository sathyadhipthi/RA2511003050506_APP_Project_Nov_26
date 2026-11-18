package skillmatch.service;

import java.util.OptionalDouble;
import java.util.Set;

/** Interface implemented by the matching service (abstraction). */
public interface SkillMatcher {
    OptionalDouble calculateMatch(Set<String> student, Set<String> required);
    Set<String> missingSkills(Set<String> student, Set<String> required);
}
