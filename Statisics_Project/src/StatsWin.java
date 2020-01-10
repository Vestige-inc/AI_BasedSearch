import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

public class StatsWin extends Frame implements ItemListener, ActionListener {

    NumberFormat nf=NumberFormat.getInstance();

    TextArea statsTA;
    Checkbox bar=new Checkbox("Bar Graph");
    Checkbox scatter=new Checkbox("Scatter Graph");
    Checkbox regplot=new Checkbox("Regression Line Plot");
    Checkbox datawin=new Checkbox("Show Data");

    double[] data;

    Graphs bg;
    Graphs sg;
    Graphs rp;
    DataWin da;
    RegData rd;



    public StatsWin(double[] vals) {
        data=vals;

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                shutdown();
            }
        });

        createMenu();

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(new Dimension(300,240));
        setTitle("Statistical Data");

        rd=Stats.regress(data);
        nf.setMaximumFractionDigits(2);

        String mstr;
        try{
            mstr=nf.format(Stats.mode(data));
        }catch(NoModeException exc){
            mstr=exc.toString();
        }

        String str="Mean: "+nf.format(Stats.mean(data))+"\n"+
                "Median: "+nf.format(Stats.median(data))+"\n"+
                "Mode: "+mstr+"\n"+
                "Standard Deviation: "+ nf.format(Stats.stdDev(data))+"\n\n"+
                "Regression Equation: "+ rd.equation + "\nCorrelation Coefficient: "+
                nf.format(rd.cor);

        statsTA =new TextArea(str,6,38,TextArea.SCROLLBARS_NONE);
        statsTA.setEditable(false);

        add(statsTA);
        add(bar);
        add(scatter);
        add(regplot);
        add(datawin);

        bar.addItemListener(this);
        scatter.addItemListener(this);
        regplot.addItemListener(this);
        datawin.addItemListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String arg=(String) ae.getActionCommand();

        if(arg=="Close"){
            shutdown();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(bar.getState()){
            if(bg==null){
                bg=new Graphs(data,Graphs.BAR);
                bg.addWindowListener(new WindowAdapter() {
                   public void windowClosing(WindowEvent we){
                       bar.setState(false);
                       bg=null;
                   }
                });
            }
        }else{
            if(bg!=null){
                bg.dispose();
                bg=null;
            }
        }

        if(scatter.getState()){
            if(sg==null){
                sg=new Graphs(data,Graphs.SCATTER);
                sg.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we){
                        scatter.setState(false);
                        sg=null;
                    }
                });
            }
        }else{
            if(sg!=null){
                sg.dispose();
                sg=null;
            }
        }

        if(regplot.getState()){
            if(rp==null){
                rp=new Graphs(data,Graphs.REGPLOT);
                rp.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we){
                        regplot.setState(false);
                        rp=null;
                    }
                });
            }
        }else{
            if(rp!=null){
                rp.dispose();
                rp=null;
            }
        }

        if(datawin.getState()){
            if(da==null){
                da=new DataWin(data);
                da.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we){
                        datawin.setState(false);
                        da=null;
                    }
                });
            }
        }else{
            if(da!=null){
                da.dispose();
                da=null;
            }
        }
    }

    private void createMenu(){
        MenuBar mbar=new MenuBar();
        setMenuBar(mbar);

        Menu file=new Menu("File");
        MenuItem close=new MenuItem("Close");
        file.add(close);
        mbar.add(file);
        close.addActionListener(this);
    }

    private void shutdown(){
        if(bg !=null) bg.dispose();
        if(sg !=null) sg.dispose();
        if(rp !=null) rp.dispose();
        if(da !=null) da.dispose();
        setVisible(false);
        dispose();
    }

}
