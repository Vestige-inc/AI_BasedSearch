import java.text.NumberFormat;
import java.util.Arrays;


public class Stats {


    public static double mean(double[] vals) {
        double total = 0,
                avg = 0;


        for (int i = 0; i < vals.length; i++) {
            total += vals[i];
            avg = total / vals.length;
        }
        return avg;
    }

    public static double median(double[] vals) {
        double temp[] = new double[vals.length];
        System.arraycopy(vals, 0, temp, 0, vals.length);
        Arrays.sort(temp);

        if ((vals.length % 2 == 0)) {
            return (temp[temp.length / 2] + temp[(temp.length / 2) - 1]) / 2;
        } else return temp[temp.length / 2];
    }

    public static double mode(double[] vals) throws NoModeException {
        double m, modeVal = 0;
        int count, oldcount = 0;

        for (int i = 0; i < vals.length; i++) {
            m = vals[i];
            count = 0;

            for (int j = i + 1; j < vals.length; j++) {
                if (m == vals[j]) count++;

                if (count > oldcount) {
                    modeVal = m;
                    oldcount = count;
                }
            }
        }

        if (oldcount == 0) {
            throw new NoModeException();
        } else return modeVal;

    }

    public static double stdDev(double[] vals) {
        double std = 0, MD = 0, Var = 0;
        double avg = mean(vals);

        for (int i = 0; i < vals.length; i++) {
            MD += Math.pow((vals[i] - avg), 2);
            Var = MD / vals.length;
            std = Math.sqrt(Var);

        }
        return std;
    }


    public static RegData regress(double[] vals) {
        double a, b, yAvg, xAvg, temp = 0, temp2 = 0, cor = 0;
        double vals2[] = new double[vals.length];

        //Create a number format with 2 decimal digits.
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        yAvg = mean(vals);

        xAvg = 0.0;
        for (int i = 0; i < vals.length; i++) {
            temp += (vals[i] - yAvg) * (i - xAvg);
            temp2 += (i - xAvg) * (i - xAvg);
        }
        b = temp / temp2;
        a = yAvg - (b * xAvg);

        for (int i = 0; i < vals.length; i++) {
            vals2[i] = i + 1;
            cor = temp / vals.length;
            cor /= stdDev(vals) * stdDev(vals2);

        }
        return new RegData(a, b, cor, "Y= " + nf.format(a) + " + " + nf.format(b) + "* X");


    }


}
