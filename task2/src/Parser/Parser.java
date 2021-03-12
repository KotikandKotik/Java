package Parser;

import models.Line;
import models.Point;
import models.Sphere;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    File file;
    String textData;

    public Parser(String filePath){
        this.file = new File(filePath);
        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        this.textData = bufferedReader.readLine();
    }

    public String getByAtribute(String atribute, String data){
        Pattern pattern = Pattern.compile("(?<="+atribute+": \\{).*");
        Matcher matcher = pattern.matcher(data);
        matcher.find();

        String suBresult = data.substring(matcher.start(), matcher.end());
        pattern = Pattern.compile("[^}]*");
        matcher = pattern.matcher(suBresult);
        matcher.find();

        String result = suBresult.substring(matcher.start(), matcher.end());
        return result;
        }

    public Sphere getSphereData(){
        String atrs = getByAtribute("sphere", textData);
        Pattern pattern = Pattern.compile("\\[([\\s\\S]+?)]");
        Matcher matcher = pattern.matcher(atrs);

        matcher.find();
        String firstPoint = matcher.group(0);

        firstPoint = firstPoint.replace("[","");
        firstPoint = firstPoint.replace("]", "");

        String[] array = firstPoint.split(",");
        Point point1 = new Point(Double.parseDouble(array[0]),
                Double.parseDouble(array[1]),
                Double.parseDouble(array[2]));

        pattern = Pattern.compile("radius:([\\s\\S]+?),");
        matcher = pattern.matcher(atrs);

        matcher.find();
        String radius = matcher.group(0);

        radius = radius.replace("radius: ","");
        radius = radius.replace(",","");
        Double rad = Double.parseDouble(radius);

        return new Sphere(point1, rad);

    }
    public Line getLineData(){
        String atrs = getByAtribute("line", textData);
        Pattern pattern = Pattern.compile("\\[([\\s\\S]+?)]");
        Matcher matcher = pattern.matcher(atrs);

        matcher.find();
        String firstPoint = matcher.group(0);
        matcher.find();
        String secondPoint = matcher.group(0);

        firstPoint = firstPoint.replace("[","");
        firstPoint = firstPoint.replace("]", "");

        secondPoint = secondPoint.replace("[", "");
        secondPoint = secondPoint.replace("]","");

        String[] array = firstPoint.split(",");
        Point point1 = new Point(Double.parseDouble(array[0]),
                Double.parseDouble(array[1]),
                Double.parseDouble(array[2]));


        array = secondPoint.split(",");
        Point point2 = new Point(Double.parseDouble(array[0]),
                Double.parseDouble(array[1]),
                Double.parseDouble(array[2]));

        return new Line(point1, point2);
    }



}
