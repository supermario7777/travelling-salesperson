public class Tour {

    private class Node {
        private Point p;
        private Node next;

        // we create one parameter constructor
        public Node(Point p) {
            this.p = p;
            this.next = null;
        }

        // we create two parameter constructor
        public Node(Point p, Node next) {
            this.p = p;
            this.next = next;
        }
    }

    private Node head;


    // we create a new empty tour
    public Tour() {

        head = null;

    }

    // we create the 4-point tour a->b->c->d->a (for debugging)
    public Tour(Point a, Point b, Point c, Point d) {

        Node nodeA = new Node(a);
        Node nodeB = new Node(b);
        Node nodeC = new Node(c);
        Node nodeD = new Node(d);

        nodeC.next = nodeD;
        nodeB.next = nodeC;
        nodeA.next = nodeB;
        nodeD.next = nodeA;

        head = nodeA;
    }

    // returns the number of points in this tour
    public int size() {

        // the count variable will return the number of nodes in circle linked list
        int count = 0;
        Node temp = head;

        do {
            temp = temp.next;
            count++;

        } while (temp != head);
        return count;

    }

    // we calculate the length of this tour
    public double length() {
        // we create a current node which will store link to each node during the iteration below
        Node current = head;
        // we created a distance variable which will calculate sum of length between all points
        double distance = 0;

        for (int i = 0; i < size(); i++) {
            // we add each Euclidean distance between of each points
            distance += current.p.distanceTo(current.next.p);
            current = current.next;
        }

        return distance;

    }

    public String toString() {
        // we create an empty string variable which will show us a string representation of the tour
        String result = "";
        Node current = head;


        for (int i = 0; i < size(); i++) {
            // we add each representation of x and y points to the final string result
            result += current.p.toString();
            // If the next value is not null we have to add a new line
            if (current.next != null) {
                result += "\n";
            }
            current = current.next;

        }
        return result;
    }

    // draws this tour to standard drawing
    public void draw() {
        Node temp = head;
        // we create a two point type variables which will store x and y value of each point
        Point a, b;

        for (int i = 0; i < size(); i++) {
            // a variable will store a value of the head node
            a = temp.p;
            // b variable will store a value of the node after the head node
            b = temp.next.p;

            // we draw a line between these two points
            a.drawTo(b);

            // we update temp node data through each iteration
            temp = temp.next;
        }
    }

    public void insertNearest(Point point) {
        Node temp = head;
        // Stores the minimum distance node
        Node minNode = null;
        // Stores the minimum distance
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < size(); i++) {
            // Get the distance from p to the current node point
            double distance = point.distanceTo(temp.p);
            // If distance is less than the minimum distance
            if (distance < minDistance) {
                // Set minDistance to newest minimum distance
                minDistance = distance;
                // Set minNode to newest minimum distance node
                minNode = temp;
            }

            temp = temp.next;
        }

        // if minNode is null
        if (minNode == null) {
            // Automatically adds new element to list
            head = new Node(point);
            // Loop the list
            head.next = head;
        }
        else {
            // Inputs new node into list with loop
            minNode.next = new Node(point, minNode.next);
        }
    }

    // inserts p using the smallest increase heuristic
    public void insertSmallest(Point point) {
        // Stores minimum difference
        double minDelta = Double.MAX_VALUE;
        // Temp variable that points to head
        Node temp = head;
        // Stores minimum node
        Node minNode = null;

        // Loops through list
        for (int i = 0; i < size(); i++) {
            // Gets the distance from temp to p
            double tempToNewDist = temp.p.distanceTo(point);
            // Gets the distance from p to temp.next
            double newToNextDist = point.distanceTo(temp.next.p);
            // Gets the distance from temp to temp.next
            double tempToNextDist = temp.p.distanceTo(temp.next.p);

            // Calculates difference
            double delta = tempToNewDist + newToNextDist - tempToNextDist;

            // If the difference is less than the minimum difference
            if (delta < minDelta) {
                // Set minDelta to new difference
                minDelta = delta;
                // Set minDelta to temp
                minNode = temp;
            }

            temp = temp.next;
        }

        // if minNode is null
        if (minNode == null) {
            // Automatically adds new element to list
            head = new Node(point);
            // Loop the list
            head.next = head;
        }
        else {
            // Inputs new node into list with loop
            minNode.next = new Node(point, minNode.next);
        }
    }

    // tests this class by calling all constructors and instance methods
    public static void main(String[] args) {

        StdDraw.setScale(0, 6);

        // define 4 points, corners of a square
        Point a = new Point(1.0, 1.0);
        Point b = new Point(1.0, 4.0);
        Point c = new Point(4.0, 4.0);
        Point d = new Point(4.0, 1.0);

        // create the tour a -> b -> c -> d -> a
        Tour squareTour = new Tour(a, b, c, d);

        System.out.println(squareTour);

        // print the size to standard output
        int size = squareTour.size();
        StdOut.println("Count of points = " + size);

        // print the tour length to standard output
        double length = squareTour.length();
        StdOut.println("Tour length = " + length);

        squareTour.draw();
    }
}


// For usa13509.txt we get tour lengths of 77449.9794 and 45074.7769 for nearest insertion and smallest insertion, respectively.
// For circuit1290.txt we get 25029.7905 and 14596.0971, respectively.

