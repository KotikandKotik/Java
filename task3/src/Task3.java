import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sun.tools.serialver.SerialVer.usage;

public class Task3 {

    public static void main(String[] args) {
//        String filePath, startDate, finishDate;
//        if (args.length == 1) {
//            filePath = args[0];
//            readFile(filePath);
//        } else if (args.length == 3) {
//            filePath = args[0];
//            startDate = args[1];
//            finishDate = args[2];
//            readFile(filePath, true, startDate, finishDate);
//        } else {
//            usage();
//        }
        readFile("/Users/evgeny/IdeaProjects/task3/out/artifacts/task3_jar/log.log", true, "2020-01-01T12:56:34.769Z", "2020-01-01T19:33:32.124Z");
    }

    public static void readFile(String filePath) {
        readFile(filePath, false, "", "");
    }

    public static void readFile(String filePath, Boolean isInterval, String startDate, String finishDate) {
        try {
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            line = reader.readLine();
            int maxV = Integer.parseInt(line);
            line = reader.readLine();
            int currentV = Integer.parseInt(line);
            line = reader.readLine();
            Analyzer analyzer = new Analyzer(maxV, currentV);
            while (line != null) {
                if (isInterval) {
                    lineHandler(line, analyzer, startDate, finishDate);
                    line = reader.readLine();
                } else {
                    lineHandler(line, analyzer);
                    line = reader.readLine();
                }
            }
            printResult(analyzer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void lineHandler(String line, Analyzer analyzer) {
        Pattern pattern = Pattern.compile(".*(wanna \\D*).*\\s(\\d+)l");
        Pattern actionPattern = Pattern.compile("scoop");
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        String action = matcher.group(1);
        String countLiter = matcher.group(2);
        matcher = actionPattern.matcher(action);
        Boolean isScoop = matcher.find();
        int liters = Integer.parseInt(countLiter);
        if (isScoop) {
            if (analyzer.finishV - liters >= 0) {
                analyzer.minusV += liters;
                analyzer.finishV -= liters;
                analyzer.tryMinus += 1;
                analyzer.countSuccessMinus += 1;
                analyzer.countSuccess += 1;
            } else {
                analyzer.tryMinus += 1;
                analyzer.unMinusV += liters;
                analyzer.countFailMinus += 1;
                analyzer.countFail += 1;
            }
        } else {
            if (analyzer.finishV + liters <= analyzer.maxV) {
                analyzer.addV += liters;
                analyzer.finishV += liters;
                analyzer.tryAdd += 1;
                analyzer.countSuccessAdd += 1;
                analyzer.countSuccess += 1;
            } else {
                analyzer.tryAdd += 1;
                analyzer.unAddV += liters;
                analyzer.countFailAdd += 1;
                analyzer.countFail += 1;
            }
        }
    }

    public static void lineHandler(String line, Analyzer analyzer, String start, String finish){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Pattern pattern = Pattern.compile("(^\\S*)(Z)");
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        String strDate = matcher.group(1);
        matcher = pattern.matcher(start);
        matcher.find();
        start = matcher.group(1);
        matcher = pattern.matcher(finish);
        matcher.find();
        finish = matcher.group(1);
        try{
            Date startDate = sdf.parse(start);
            Date date = sdf.parse(strDate);
            Date finishDate = sdf.parse(finish);
            if (date.after(startDate) && date.before(finishDate)) {
                lineHandler(line, analyzer);
            } else if (date.before(startDate)) {
                Pattern pattern2 = Pattern.compile(".*(wanna \\D*).*\\s(\\d+)l");
                Pattern actionPattern = Pattern.compile("scoop");
                matcher = pattern2.matcher(line);
                matcher.find();
                String action = matcher.group(1);
                String countLiter = matcher.group(2);
                matcher = actionPattern.matcher(action);
                Boolean isScoop = matcher.find();
                int liters = Integer.parseInt(countLiter);
                if (isScoop) {
                    if (analyzer.finishV - liters > 0) {
                        analyzer.startV -= liters;
                        analyzer.finishV -= liters;
                    }
                } else {
                    if (analyzer.finishV + liters < analyzer.maxV) {
                        analyzer.startV += liters;
                        analyzer.finishV += liters;
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void printResult(Analyzer analyzer){
        try (PrintWriter writer = new PrintWriter(new File("result.csv"))) {

            StringBuilder builder = new StringBuilder();
            String columnsName = "количество попыток налить воду в бочку за указанный период," +
                    "процент ошибок был допущен за указанный период," +
                    "объем воды был налит в бочку за указанный период," +
                    "Какой объем воды был не налит в бочку за указанный период," +
                    "количество попыток вычерпать воду в бочку за указанный период," +
                    "процент ошибок был допущен за указанный период," +
                    "объем воды был вычерпан в бочку за указанный период," +
                    "Какой объем воды был не налит в бочку за указанный период," +
                    "объем воды был в бочке в начале указанного периода, " +
                    "какой в конце указанного периода";
            builder.append(columnsName +"\n");
            String resultStr = String.valueOf(analyzer.tryAdd) + ',' +
                    (getErrorProcent(analyzer, true)) + ',' +
                    (analyzer.addV) + ',' +
                    (analyzer.unAddV) + ',' +
                    (analyzer.tryMinus) + ',' +
                    (getErrorProcent(analyzer, false)) + ',' +
                    (analyzer.minusV) + ',' +
                    (analyzer.unMinusV) + ',' +
                    (analyzer.startV) + ',' +
                    (analyzer.finishV) + '\n';
            builder.append(resultStr);
            writer.write(builder.toString());
            writer.close();
            System.out.println("done!");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Double getErrorProcent(Analyzer analyzer, Boolean isAdd) {
        Double result = 0.0;
        if (isAdd){
            result = analyzer.countFailAdd / ((analyzer.countFailAdd + analyzer.countSuccessAdd) / 100.0);
        } else {
            result = analyzer.countFailMinus / ((analyzer.countFailMinus + analyzer.countSuccessMinus) / 100.0);
        }
        return result;
    }
}
