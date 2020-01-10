import java.io.IOException;

public class DemoStat {
    public static void main(String[] args) throws IOException {

        //Totally unrelated and for curiousity
        double[] elements = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double average = Stats.mean(elements);
        double middle = Stats.median(elements);
        System.out.println(average);

        double[] nums ={
                10,10,11,9,8,8,9,
                10,10,13,11,11,11,
                11,12,13,14,16,17,
                15,15,16,14,16
        };

        new StatsWin(nums);

    }


}
