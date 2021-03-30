package no.dataingnioer.yamo.demo.utils;

/**
 *
 */
public class MailUtils {

    /**
     *
     */
    private static final String UPPER_ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     *
     */
    private static final String LOWER_ALPHA_STRING = "abcdefghijklmnopqrstuvwxyz";

    /**
     *
     */
    private static final String NUMERIC_STRING = "0123456789";

    /**
     *
     * @param count
     * @param domain
     * @return
     */
    public static String generateRandomEmail(int count, String domain) {
        return String.format("%s@%s",
                generateRandomString(count, UPPER_ALPHA_STRING + LOWER_ALPHA_STRING + NUMERIC_STRING), domain);
    }

    /**
     *
     * @param length
     * @return
     */
    public static String generateRandomName(int length){
        return String.format("%s%s",
                generateRandomString(1, UPPER_ALPHA_STRING),
                generateRandomString(length-1, LOWER_ALPHA_STRING));
    }

    /**
     *
     * @param count
     * @return
     */
    public static String generateRandomString(int count, String from){
        StringBuilder builder = new StringBuilder();

        while (count-- != 0) {

            int character = (int)(Math.random()*from.length());
            builder.append(from.charAt(character));
        }

        return builder.toString();
    }

}
