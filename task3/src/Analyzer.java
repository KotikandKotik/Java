public class Analyzer {
    int maxV, startV, finishV, addV, unAddV, minusV, unMinusV, countSuccess, countFail, tryAdd, tryMinus;
    int countSuccessAdd, countFailAdd, countSuccessMinus, countFailMinus;

    public Analyzer(int maxV, int startV) {
        this.maxV = maxV;
        this.startV = startV;
        finishV = startV;
        addV = 0;
        unAddV = 0;
        minusV = 0;
        unMinusV = 0;
        countSuccessAdd = 0;
        countFailAdd = 0;
        countSuccessMinus = 0;
        countFailMinus = 0;
        countSuccess = 0;
        countFail = 0;
        tryAdd = 0;
        tryMinus = 0;
    }
}
