import First.Par;
import RecRelation.pr01;
import Second.BinartSearch;

public class Main {
    public static void main(String[] args) {
        Testingclass testingclass = new Testingclass();
        testingclass.start();
        pr01.start();
        Testingclass.MeasurementResult res = testingclass.stop();
        System.out.println(res);
    }

    //       Testingclass test = new Testingclass();
    //       test.start();
    //        Par.start();
    //        Testingclass.MeasurementResult result = test.stop();
    //        System.out.println(result);
}