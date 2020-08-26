import java.util.ArrayList;

public class MyCNC {
    private boolean isProcessing;
    private int leftTime;
    private int id;
    private boolean finished;
    private ArrayList<Integer>myArgs;
    public static final int TimeToMoveOne = 0;
    public static final int TimeToMoveTwo = 1;
    public static final int TimeToMoveThree = 2;
    public static final int TimeToProcess = 3;
    public static final int TimeToProcess21 = 4;
    public static final int TimeToProcess22 = 5;
    public static final int TimeToPutForOdd = 6;
    public static final int TimeToPutForEven = 7;
    public static final int TimeToWash = 8;

    private double workingTime;

    public MyCNC(int id, ArrayList<Integer> myArgs){
        this.isProcessing = false;
        this.leftTime = 0;
        this.id = id;
        this.finished = false;
        this.myArgs = new ArrayList<>(myArgs);
        this.workingTime = 0;
    }

    public void put(){
        //System.out.println("CNC " + id + " is get,put,process");
        this.leftTime = myArgs.get(TimeToProcess);
        this.isProcessing = true;
        this.finished = false;
    }

    public void addTime(){
        if(isProcessing){
            workingTime++;
        }
        if(this.leftTime == 1){
            this.isProcessing = false;
            this.finished = true;
            //System.out.println("CNC " + id + " finish process");
        }
        this.leftTime--;
    }

    public boolean hasFinished(){
        return this.finished;
    }

    public boolean isBusy(){
        return this.isProcessing;
    }

    public double getWorkingTime(){
        return this.workingTime;
    }

}
