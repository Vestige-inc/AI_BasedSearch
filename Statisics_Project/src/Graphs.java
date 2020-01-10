import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class Graphs extends Frame {

    public final static int BAR = 0;
    public final static int SCATTER = 1;
    public final static int REGPLOT = 2;
    private final int leftGap = 2, topGap = 2, bottomGap = 2;
    Color gridColor = new Color(238, 31, 255);
    Color dataColor = new Color(255, 136, 26);
    private int graphStyle;
    private int rightGap = 2;
    private double min, max;
    private double[] data;
    private int hGap;
    private int spread;
    private double scale;
    private int baseline;

    private int top, bottom, left, right;


    public Graphs(double[] vals,int style) {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                setVisible(false);
                try {
                    dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent ce) {
                repaint();
            }

        });

        graphStyle = style;
              data=vals;

        double[] t= new double[vals.length];
        System.arraycopy(vals, 0, t, 0, vals.length);
        Arrays.sort(t);
        min = t[0];
        max = t[t.length - 1];

        setSize(new Dimension(200, 120));

        switch (graphStyle) {
            case BAR:
                setTitle("Bar Graph");
                setLocation(25, 250);
                break;
            case SCATTER:
                setTitle("Bar Graph");
                setLocation(250, 250);
                break;
            case REGPLOT:
                setTitle("Bar Graph");
                setLocation(475, 250);
                break;
        }
        setVisible(true);
    }

    public void paint(Graphics g){
        Dimension winSize = getSize();
        Insets ins = getInsets();
        FontMetrics fm=g.getFontMetrics();

        rightGap=fm.stringWidth(""+data.length);

        left=ins.left+leftGap+fm.charWidth('0');
        top=ins.top+topGap+fm.getAscent();
        bottom=ins.bottom+bottomGap+fm.getAscent();
        right=ins.right+rightGap;

        if(min>0) {min=0;}
        if(max<0) {max=0;}

        spread=(int) (max-min);
        scale= (double) (winSize.height-bottom-top)/spread;
        baseline=(int) (winSize.height-bottom+min*scale);

        hGap=(winSize.width-left-right)/(data.length-1);

        g.setColor(gridColor);
        g.drawLine(left,baseline,left+(data.length-1)*hGap,baseline);

        if(graphStyle!=BAR){
            g.drawLine(left,winSize.height-bottom,left,top);
            g.drawString("0",ins.left,baseline+fm.getAscent()/2);
        }

        if(max!=0){
            g.drawString(""+max,ins.left,baseline-(int)(max*scale)-4);
        }

        if(min!=0){
            g.drawString(""+min,ins.left,baseline-(int)(min*scale)+fm.getAscent());
            g.drawString(""+data.length,(data.length-1)*(hGap)+left,baseline+fm.getAscent());
        }

        g.setColor(dataColor);

        switch(graphStyle){
            case BAR:
                bargraph(g);
                break;
            case SCATTER:
                scatter(g);
                break;
            case REGPLOT:
                regplot(g);
                break;
        }

    }

    private void bargraph(Graphics g){
        int v;

        for(int i=0; i<data.length; i++){
            v=(int) (data[i]*scale);

            g.drawLine(i*hGap+left,baseline,i*hGap+left,baseline-v);
        }
    }

    private void scatter(Graphics g){
        int v;

        for(int i=0; i<data.length; i++){
            v=(int) (data[i]*scale);

            g.drawRect(i*hGap+left,baseline-v,1,1);
        }
    }

    private void regplot(Graphics g){
        int v;

        RegData rd=Stats.regress(data);
        for(int i=0; i<data.length; i++){
            v=(int) (data[i]*scale);

            g.drawRect(i*hGap+left,baseline-v,1,1);
        }

        g.drawLine(left,baseline-(int)((rd.a)*scale),hGap*(data.length-1)+left+1,baseline-(int)((rd.a+(rd.b*(data.length-1))*scale)));
    }

}
