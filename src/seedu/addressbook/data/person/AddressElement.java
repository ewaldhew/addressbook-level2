package seedu.addressbook.data.person;

public class AddressElement {

    protected final String value;

    /**
     * Make a new AddressElement object with the given value.
     * Values must be validated before creating.
     */
    public AddressElement(String value) {
        if (value != null) {
            this.value = value.trim();
        } else {
            this.value = "";
        }
    }

    public String getValue(boolean withComma) {
        return (this.value.isEmpty() && withComma ? "" : ", ") + this.value;
    }
}
