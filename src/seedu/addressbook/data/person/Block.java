package seedu.addressbook.data.person;

public class Block {

    public final String value;

    /**
     * Make a new Block object.
     *
     * @param value Block.
     */
    public Block(String value) {
        if (value != null) {
            this.value = value.trim();
        } else {
            this.value = "";
        }
    }
}
