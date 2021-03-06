import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sun.tools.serialver.SerialVer.usage;

public class Task1 {
    public static void main(String[] args) {
        Pattern decimalPattern = Pattern.compile("(^\\d+$)");
       if (args.length == 2){
           Matcher matcher = decimalPattern.matcher(args[0]);
           if(matcher.find()){
               matcher = decimalPattern.matcher(args[1]);
               if(matcher.find()){
                   itoBase(Integer.parseInt(args[0]), args[1]);
                   return;
               }
           }
       } else if (args.length == 3) {
           Matcher matcher;
           for(int i = 0; i < args.length; i++){
               matcher = decimalPattern.matcher(args[i]);
               if (!matcher.find()) {
                   break;
               }
               if (i == 2){
                   itoBase(args[0], args[1], args[2]);
                   return;
               }
           }
       }
       printUsage();
    }

    private static void printUsage(){
        System.out.println("Первый параметр: число, второй параметр: номер системы исчисления: 2 - двоичная, " +
                "3 - троичная и т.д. , с третьим параметром аналогично (если он есть)");
        usage();
    }

    public static String itoBase(int nb, String base) {
        int num = nb, baseI = Integer.parseInt(base);
        int n1 = 0, n2 = 0;
        String result = new String();
        if (baseI >= 16) {
            char s;
            do {
                n1 = num % baseI;
                if (n1 > 9) {
                    s = (char)('A'- 10 + n1);
                    result += s;
                } else {
                    s = (char) ('0' + n1);
                    result += s;
                }
                num = num / baseI;
            } while (num > 0);
        } else {
            while (num >= baseI) {
                n1 = num;
                num = num / baseI;
                n2 = n1 % baseI;
                if (n2 == 0) {
                    result += "0";
                } else {
                    result += n2 + "";
                }
            }
            result = result + "" + num;
        }
        result = new StringBuffer(result).reverse().toString();
        System.out.println(result);
        return result;
    }

    public static String itoBase(String nb, String srcBase, String dstBase){
        int dstNumBase = Integer.parseInt(dstBase);
        int srcNumBase = Integer.parseInt(srcBase);

        if (dstNumBase == 1) {
            return "Ошибка система исчисления должна быть 2, либо больше";
        }

        if (srcNumBase == dstNumBase) {
            return nb;
        }

        int numInTenBase = 0;
        for (int i = 0; i < nb.length(); i++) {
            numInTenBase += Integer.parseInt(String.valueOf(nb.charAt(i))) * Math.pow(srcNumBase, (nb.length() - i - 1));
        }

        System.out.println(numInTenBase);

        if (dstNumBase == 10) {
            return String.valueOf(numInTenBase);
        } else {
            return itoBase(numInTenBase, dstBase);
        }
    }
}
