import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4 {
    public static void main(String[] args) {
        if (args.length == 2){
            System.out.println( mathcStrs(args[0], args[1]));
        } else {
            System.out.println("ERROR: Некорректные аргументы");
        }
    }

    public static String mathcStrs(String str1, String str2) {
        Pattern starPattern = Pattern.compile("\\*+");
        String result = starPattern.matcher(str2).replaceAll(".*");
        Pattern pattern = Pattern.compile(result);
        Matcher matcher = pattern.matcher(str1);
        if (matcher.find()) {

            return "OK";
        } else {

            return "KO";
        }
    }
}
