public class Obstacle {
    private int UpBound;
    private int bottomBound;
    private int leftBound;
    private int rightBound;

    public Obstacle (int X1, int X2, int Y1, int Y2){
        if (X1 > X2){
            leftBound = X2;
            rightBound=X1;
        }else{
            leftBound = X1;
            rightBound=X2;
        }
        if (Y1 > Y2){
            UpBound = Y2;
            bottomBound=Y1;
        }else{
            UpBound = Y1;
            bottomBound=Y2;
        }

    }
    public int getUpBound (){
        return UpBound;
    }
    public int getBottomBound(){
        return bottomBound;
    }
    public int getLeftBound(){
        return leftBound;
    }
    public int getRightBound(){
        return rightBound;
    }

    public void update(int X1, int X2, int Y1, int Y2){
        if (X1 > X2){
            leftBound = X2;
            rightBound=X1;
        }else{
            leftBound = X1;
            rightBound=X2;
        }
        if (Y1 > Y2){
            UpBound = Y2;
            bottomBound=Y1;
        }else{
            UpBound = Y1;
            bottomBound=Y2;
        }
    }
}
