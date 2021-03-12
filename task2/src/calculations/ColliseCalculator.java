package calculations;

import models.Line;
import models.Point;
import models.Sphere;

public class ColliseCalculator {

	private final Line line;
	private final Sphere sphere;

	public ColliseCalculator(Line line, Sphere sphere) {
		this.line = line;
		this.sphere = sphere;
	}

	public void calculateCollisionLineAndSphere(){

		Point V = getVectorProduct(getVector(line.getP2(), sphere.getCenter()),
				getVector(line.getP1(), sphere.getCenter()));
		Double lengthAB = pointAbs(getVector(line.getP2(), line.getP1()));
		Double h = pointAbs(new Point(V.getX()/lengthAB,
				V.getY()/lengthAB,
				V.getZ()/lengthAB));
		Point op = getVectorProduct(V,getVector(line.getP2(), line.getP1()));
		Double opAbs = pointAbs(op);
		op.setX(op.getX()/opAbs);
		op.setY(op.getY()/opAbs);
		op.setZ(op.getZ()/opAbs);

		Point pSub = new Point(op.getX()*h, op.getY()*h, op.getZ()*h);
		Point p = new Point(sphere.getCenter().getX()+pSub.getX(),
				sphere.getCenter().getY()+pSub.getY(),
				sphere.getCenter().getZ()+ pSub.getZ());
		Double d = Math.sqrt(Math.pow(sphere.getR(),2)-Math.pow(h,2));
		Point pd = getVector(line.getP2(), line.getP1());
		Point pdsub2 = new Point(pd.getX()/lengthAB,
				pd.getY()/lengthAB,
				pd.getZ()/lengthAB);
		pd = new Point(pdsub2.getX()*d,
				pdsub2.getY()*d,
				pdsub2.getZ()*d);

		Point crossP1 = new Point(p.getX()+pd.getX(),
				p.getY()+ pd.getY(),
				p.getZ()+ pd.getZ());

		Point crossP2 = new Point(p.getX()-pd.getX(),
				p.getY()-pd.getY(),
				p.getZ()-pd.getZ());

		Boolean isP1 = comparePoints(crossP1, sphere.getCenter(), sphere.getR());
		Boolean isP2 = comparePoints(crossP2, sphere.getCenter(), sphere.getR());
		if(!isP1 && !isP2){System.out.println("Коллизий не найдено");}
		else if(isP1){crossP1.printCoords();}
		if(isP2){crossP2.printCoords();}
	}


	public static Boolean comparePoints(Point p1, Point p2, Double radius){
		Double dist = pointCalcs(p1, p2);
		if(dist<0)dist = dist*(-1);

		if(dist<=radius)return true;
		else return false;
	}

	public static Double pointCalcs(Point p1, Point p2){
		return Math.sqrt(Math.pow(p1.getX()-p2.getX(), 2) + Math.pow(p1.getY()-p2.getY(), 2) + Math.pow(p1.getZ() - p2.getZ(),2));
	}

	public static Point getVectorProduct(Point p1, Point p2){
		Point vectorProduct = new Point(p1.getY()*p2.getZ()-p1.getZ()*p2.getY(),
				p1.getZ()*p2.getX()-p1.getX()*p2.getZ(),
				p1.getX()*p2.getY()-p1.getY()*p2.getX());
		return vectorProduct;
	}

	public static Point getVector(Point p2, Point p1){
		Point vector = new Point(p2.getX() - p1.getX(),
				p2.getY() - p1.getY(),
				p2.getZ()- p1.getZ());
		return vector;
	}

	public static Double pointAbs(Point point){
		Double f = Math.pow(point.getX(), 2);
		Double s = Math.pow(point.getY(), 2);
		Double t = Math.pow(point.getZ(), 2);
		Double sum = f+s+t;
		return Math.sqrt(sum);
	}
}
