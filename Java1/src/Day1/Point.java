package Day1;

public class Point {  //Class is the blueprint
    //Define the object (Attributes, properties, Fields)
    String name;
    float x;
    float y;
    float z;
    float rotationX;
    float rotationY;
    float rotationZ;
    float scaleX = 1;
    float scaleY = 1;
    float scaleZ = 1;

    //Default constructor
    public Point(String name) { this.name = name; }

    //Actions that it can do (Verbs, methods/Functions)
    public void setXYZ(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void movePointsBy(float moveX, float moveY, float moveZ)
    {
        this.x += moveX;
        this.y += moveY;
        this.z += moveZ;
        System.out.print("Moving ");
        printPoints();
    }


    public void printPoints()
    {
        System.out.println(">" +name + "\n Position" + " [" + x + ", " + y + ", " + z + "]" +"\n"
                                + " Rotation" + " [" + rotationX + ", " + rotationY + ", " + rotationZ + "]" + "\n"
                                + " Scale" + " [" + scaleX + ", " + scaleY + ", " + scaleZ + "]");
    }



    //The main function
    public static void main(String[] args)
    {
        //Create different points, with their chosen names
        Point pointA = new  Point("Day1.Point A");
        Point pointB = new  Point ("Day1.Point B");
        Point pointC = new  Point ("Day1.Point C");

        //Create the coordinates
        pointA.setXYZ(1.25f, 5.14f, .2f);
        pointB.setXYZ(1f, 2.00005f, 0.01546f);

        //Print the points
        pointA.printPoints();
        pointB.printPoints();
        pointC.printPoints();

        System.out.println("-------------------------------");

        //Let's move pointC
        pointC.movePointsBy(5f, .2564f, 1.2564f);
        pointA.movePointsBy(0f, 0.00001f, .0002f);
        pointB.printPoints();


    }

}
