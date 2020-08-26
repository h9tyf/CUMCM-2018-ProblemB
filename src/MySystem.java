import java.util.ArrayList;
import java.util.HashMap;

public class MySystem {
    private HashMap<Integer, MyCNC> cncs;
    private MyRGV myRGV;
    private ArrayList<Integer> myArgs;
    private int number;
    public static final int TimeToMoveOne = 0;
    public static final int TimeToMoveTwo = 1;
    public static final int TimeToMoveThree = 2;
    public static final int TimeToProcess = 3;
    public static final int TimeToProcess21 = 4;
    public static final int TimeToProcess22 = 5;
    public static final int TimeToPutForOdd = 6;
    public static final int TimeToPutForEven = 7;
    public static final int TimeToWash = 8;



    public MySystem(ArrayList<Integer> myArgs){
        this.number = 0;
        this.cncs = new HashMap<>();
        for (int i = 1; i < 9; i++) {
            this.cncs.put(i, new MyCNC(i, myArgs));
        }
        this.myRGV = new MyRGV(myArgs);
        this.myArgs = new ArrayList<>(myArgs);
    }

    public void run(){
        int totalTime = 8 * 60 * 60;
        int i = 0;
        double moveTime = 0;
        while (i < totalTime) {
            boolean ifNeedGet = false;
            if(!this.myRGV.isBusy()){
                //System.out.println("time is " + i);
                //System.out.println("RGV location is " + myRGV.getLocation());

                int desId = chooseDestination(myRGV.getLocation(), cncs);
                //System.out.println("destination is " + desId);

                int desLoc = desId / 2 + desId % 2;
                int length = desLoc - myRGV.getLocation();
                //System.out.println("desLoc is " + desLoc + ", and wil move "+ length);

                if(desId == 0){
                    i++;
                    addTime(false);
                    continue;
                }

                if(cncs.get(desId).hasFinished()){
                    ifNeedGet = true;
                    this.number++;
                } else {
                    ifNeedGet = false;
                }


                if(length == 3 || length == -3){
                    moveTime += myArgs.get(TimeToMoveThree);
                    myRGV.getNewDestinaton(myArgs.get(TimeToMoveThree), desLoc,desId);
                } else if(length == 2 || length == -2){
                    moveTime += myArgs.get(TimeToMoveTwo);
                    myRGV.getNewDestinaton(myArgs.get(TimeToMoveTwo), desLoc,desId);
                } else if(length == 1 || length == -1){
                    moveTime += myArgs.get(TimeToMoveOne);
                    myRGV.getNewDestinaton(myArgs.get(TimeToMoveOne), desLoc,desId);
                } else {
                    myRGV.getNewDestinaton(0, desLoc,desId);
                }
                cncs.get(desId).put();
            }
            i++;
            addTime(ifNeedGet);
        }
        double totalWorkingTime = 0;
        for(MyCNC myCNC: cncs.values()){
            totalWorkingTime += myCNC.getWorkingTime();
        }
        double effectiveness = (totalWorkingTime - moveTime)/(totalTime * 8);
        System.out.println("moveTime is " + moveTime);
        System.out.println("effectiveness is " + effectiveness);
        System.out.println("number is " + number);
    }

    public int chooseDestination(int id, HashMap<Integer, MyCNC> map){
            ArrayList<Integer> list = new ArrayList<>();
            for (Object key : map.keySet()) {
                if(!(map.get(key).isBusy())){
                    list.add((int) key);
                }
            }
            int toID = 0;        // 要前往的id
            int time = 3600; // 初始时间
            for (int i : list) {
                if (getDistance(id, i) < time) {
                    time = getDistance(id, i);
                    toID = i;
                }
            }
            return toID;
    }

    public int getDistance(int from, int toID) {
        int to = toID % 2 == 0 ? toID / 2 : toID / 2 + 1;
        int processTime = toID % 2 == 0 ? myArgs.get(TimeToPutForEven) : myArgs.get(TimeToPutForOdd);
        if (from == to)
            // 原位置不动
            return processTime;
        else if (Math.abs(from - to) == 1)
            // 挪动一步
            return processTime + myArgs.get(TimeToMoveOne);
        else if (Math.abs(from - to) == 2)
            // 挪动两步
            return processTime + myArgs.get(TimeToMoveTwo);
        else
            // 挪动三步
            return processTime + myArgs.get(TimeToMoveThree);
    }

    public void addTime(boolean ifNeedGet){
        for (int i = 1; i < 9; i++) {
            this.cncs.get(i).addTime();
        }
        this.myRGV.addTime(ifNeedGet);
    }

}
