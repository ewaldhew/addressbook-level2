package seedu.addressbook.data.person;

public class Street {

    public final String value;

    /**
     * Make a new Street object.
     *
     * @param value Street.
     */
    public Street(String value) {
        if (value != null) {
            this.value = value.trim();
        } else {
            this.value = "";
        }
    }
}
