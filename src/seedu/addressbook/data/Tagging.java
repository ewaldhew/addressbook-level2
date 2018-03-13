package seedu.addressbook.data;

import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.tag.Tag;

/**
 * Represent an adding or deleting of a tag for a specific person in the current session.
 */
public class Tagging {

    private final Person person;
    private final Tag tag;
    private boolean isAdd; // true = added false = deleted

    /**
     * Creates a tagging record.
     * @param person The person affected
     * @param tag The tag added/deleted
     * @param isAdd {@code true} if added, {@code false} if deleted.
     */
    public Tagging(Person person, Tag tag, boolean isAdd) {
        this.person = person;
        this.tag = tag;
        this.isAdd = isAdd;
    }

    @Override
    public String toString() {
        return (isAdd ? "+ " : "- ") + person.getName().toString()
                + " [" + tag.tagName + "]";
    }
}
