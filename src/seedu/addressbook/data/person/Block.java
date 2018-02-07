package seedu.addressbook.data.person;

public class Block {

    private final String value;

    /**
     * Extract block number from an address. Address should be validated already.
     *
     * @param address full address string.
     */
    public Block(String address) {
        value = extractAddress(address);
    }

    public String extractAddress(String address) {
        return address.split(",")[0];
    }

    @Override
    public String toString() {
        return value;
    }
}
