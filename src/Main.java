import First.Par;
import RecRelation.pr01;
import Second.BinartSearch;
import SpecSD.specSD3;
import Treees.HuffmanAutoTester;
import Treees.IndTree;
import Treees.tre3;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();

        Instant end = Instant.now();
        Duration duration = Duration.between(start,end);
        System.out.println(duration.toMillis() + "мс");
    }
}