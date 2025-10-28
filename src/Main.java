import First.Par;
import RecRelation.pr01;
import Second.BinartSearch;
import Treees.tre3;

public class Main {
    public static void main(String[] args) {
        Testingclass testingclass = new Testingclass();
        testingclass.start();
        tre3.start();
        Testingclass.MeasurementResult res = testingclass.stop();
        System.out.println(res);
    }
}