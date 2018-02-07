package seedu.addressbook.data.person;

public class Unit {

    private final String value;

    /**
     * Extract unit from an address. Address should be validated already.
     *
     * @param address full address string.
     */
    public Unit(String address) {
        value = extractAddress(address);
    }

    public String extractAddress(String address) {
        return address.split(",")[2];
    }

    @Override
    public String toString() {
        return value;
    }
}
