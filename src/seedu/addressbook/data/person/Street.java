package seedu.addressbook.data.person;

public class Street {

    private final String value;

    /**
     * Extract street from an address. Address should be validated already.
     *
     * @param address full address string.
     */
    public Street(String address) {
        value = extractAddress(address);
    }

    public String extractAddress(String address) {
        return address.split(",")[1];
    }

    @Override
    public String toString() {
        return value;
    }
}
