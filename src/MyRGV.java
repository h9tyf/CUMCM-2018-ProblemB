import java.util.ArrayList;

public class MyRGV {
    private int location;
    private int leftTime;
    private ActionTypeForRGV state;
    private int destination;
    private int desId;
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

    public MyRGV(ArrayList<Integer>myArgs){
        this.location = 1;
        this.leftTime = 0;
        this.state = ActionTypeForRGV.STOP;
        this.destination = 0;
        this.myArgs = new ArrayList<>(myArgs);
    }

    public int getLocation(){
        return this.location;
    }

    public boolean isBusy(){
        return this.state != ActionTypeForRGV.STOP;
    }

    public void getNewDestinaton(int time, int destination, int desId){
        this.state = ActionTypeForRGV.MOVE;
        this.leftTime = time;
        this.destination = destination;
        this.desId = desId;
    }

    public void addTime(boolean ifNeedGet){
        if(this.leftTime != 0){
            this.leftTime--;
            if(this.state.equals(ActionTypeForRGV.MOVE)){
                if(this.leftTime == 0){
                    this.location = destination;
                    //System.out.println("RGV arrived " + destination);
                }
            }
        }
        if(this.leftTime == 0){
            if(this.state.equals(ActionTypeForRGV.MOVE)){
                if(!ifNeedGet){
                    this.state = ActionTypeForRGV.PUT;
                    if (desId % 2 == 0){
                        this.leftTime = myArgs.get(TimeToPutForEven);
                    } else {
                        this.leftTime = myArgs.get(TimeToPutForOdd);
                    }
                } else {
                    this.state = ActionTypeForRGV.GET;
                    if (desId % 2 == 0){
                        this.leftTime = myArgs.get(TimeToPutForEven);
                    } else {
                        this.leftTime = myArgs.get(TimeToPutForOdd);
                    }
                }
            } else if(this.state.equals(ActionTypeForRGV.GET)){
                this.state = ActionTypeForRGV.PUT;
                if (desId % 2 == 0){
                    this.leftTime = myArgs.get(TimeToPutForEven);
                } else {
                    this.leftTime = myArgs.get(TimeToPutForOdd);
                }
            } else if(this.state.equals(ActionTypeForRGV.PUT)){
                if(!ifNeedGet){
                    this.state = ActionTypeForRGV.STOP;
                } else {
                    this.state = ActionTypeForRGV.WASH;
                    this.leftTime = myArgs.get(TimeToWash);
                }
            } else {
                this.state = ActionTypeForRGV.STOP;
            }
        }
    }
}
