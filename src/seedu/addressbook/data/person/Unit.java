package seedu.addressbook.data.person;

public class Unit {

    public final String value;

    /**
     * Make a new Unit object.
     *
     * @param value Unit.
     */
    public Unit(String value) {
        if (value != null) {
            this.value = value.trim();
        } else {
            this.value = "";
        }
    }
}
