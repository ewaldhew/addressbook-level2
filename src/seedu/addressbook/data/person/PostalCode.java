package seedu.addressbook.data.person;

public class PostalCode {

    public final String value;

    /**
     * Make a new PostalCode object.
     *
     * @param value PostalCode.
     */
    public PostalCode(String value) {
        if (value != null) {
            this.value = value.trim();
        } else {
            this.value = "";
        }
    }
}
