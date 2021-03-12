import Parser.Parser;
import calculations.ColliseCalculator;
import models.Line;
import models.Sphere;

public class Main {

    public static void main(String[] args) {

        String filePath;
        if(args.length>0) filePath = args[0];
        else {filePath = "Collise.txt";}
        Parser parser = new Parser(filePath);
        Sphere sphere = parser.getSphereData();
        Line line = parser.getLineData();

        ColliseCalculator colliseCalculator = new ColliseCalculator(line, sphere);
        colliseCalculator.calculateCollisionLineAndSphere();


    }



}
