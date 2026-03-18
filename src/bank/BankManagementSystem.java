package bank;

public class BankManagementSystem {

    public String openAno() {
        long acno;
        do {
            acno = (long) (Math.random() * 9999999999L);
        } while (acno <= 1000000000L);

        return String.valueOf(acno);
    }

    public long checkLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return -1;
        }
    }

    public boolean isDigitPresent(String value) {
        String[] digits = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };

        for (String string : digits) {
            if (value.contains(string)) {
                return true;
            }
        }
        return false;
    }

}
