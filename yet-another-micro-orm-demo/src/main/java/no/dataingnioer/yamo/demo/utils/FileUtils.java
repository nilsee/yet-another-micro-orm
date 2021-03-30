package no.dataingnioer.yamo.demo.utils;

public class FileUtils {

    /**
     *
     * @param fileName
     * @return
     */
    public static String getConfigFilePath(String fileName) {

        return String.format("%s/yet-another-micro-orm-demo/src/main/java/no/dataingnioer/yamo/demo/%s"
                , System.getProperty("user.dir")
                , fileName);
    }

}
