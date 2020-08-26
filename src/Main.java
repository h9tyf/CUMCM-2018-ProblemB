import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args){
        //ArrayList<Integer> myArgs = new ArrayList<Integer>(Arrays.asList(20, 33, 46, 560, 400, 378, 28, 31, 25));
        //ArrayList<Integer> myArgs = new ArrayList<Integer>(Arrays.asList(23, 41, 59, 580, 280, 500, 30, 35, 30));
        ArrayList<Integer> myArgs = new ArrayList<Integer>(Arrays.asList(18, 32, 46, 545, 455, 182, 27, 32, 25));
        MySystem mySystem = new MySystem(myArgs);
        mySystem.run();
    }
}
