import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Optimal {
    final int MAX=100; //max no. of connections

    FlightInfo[] flights=new FlightInfo[MAX];

    int numFlights=0 ; // no. of entries in Flight Array
    Stack btStack=new Stack(); //backtrack stack;
    Stack optimal;
    int minDist=10000;

    public static void main(String[] args){
        String to, from;
       Optimal ob= new Optimal();
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        boolean done=false;
        FlightInfo f;

        ob.setup();

        try{
            System.out.println("From? ");
            from=br.readLine();
            System.out.println("To? ");
            to=br.readLine();

            ob.isflight(from,to);

            do{
                ob.isflight(from,to);

                if(ob.btStack.size()==0){done=true;}
                else{
                    ob.route(to);
                    ob.btStack=new Stack();
                }

            }while(!done);

            if(ob.optimal!=null){
                System.out.println("Optimal Solution is: ");

                int num=ob.optimal.size();
                for(int i=0;i<num;i++){
                    f=(FlightInfo) ob.optimal.pop();
                    System.out.println(f.from+" to ");
                }

                System.out.println(to);
                System.out.println("Distance is "+ob.minDist);
            }
        }catch(IOException exc){
            System.out.println("Error on input.");
        }
    }

    void addFlight(String from, String to, int dist){
        if(numFlights<MAX){
            flights[numFlights]=new FlightInfo(from,to,dist);

            numFlights++;
        }else{
            System.out.println("Flight Database full. \n");
        }
    }

    void setup(){
        addFlight("New York","Chicago",900);
        addFlight("New York","Toronto",500);
        addFlight("New York","Denver",1800);
        addFlight("Chicago","Denver",1000);
        addFlight("Houston","Los Angeles",1500);
        addFlight("Toronto","Calgary",1700);
        addFlight("Toronto","Los Angeles",2500);
        addFlight("Toronto","Chicago",500);
        addFlight("Denver","Urbana",1000);
        addFlight("Denver","Houston",1000);
        addFlight("Denver","Los Angeles",1000);
    }

    void route(String to){
        Stack optTemp=new Stack();
        int dist=0;
        FlightInfo f;
        int num= btStack.size();

        for(int i=0; i<num; i++){
            f=(FlightInfo) btStack.pop();
            optTemp.push(f);
            dist +=f.distance;
        }

        if(minDist>dist){
        optimal=optTemp;
        minDist=dist;
        }

        System.out.println(to);
        System.out.println("Distance is "+ dist);
    }

    //StackOverflow Error injected into isflight from here
    int match(String from,String to){
        for(int i=numFlights-1; i>-1; i--){
            if(flights[i].from.equals(from)&&flights[i].to.equals(to)&&!flights[i].skip){
                flights[i].skip=true;
                return flights[i].distance;
            }
        }
        return 0; //not found
    }

    //Given from, find any connection flight
    public FlightInfo find(String from) {

        int pos=-1;
        int dist=10000;

        for (int i = 0; i < numFlights; i++) {

            if(flights[i].distance<dist){
                pos=i;
                dist=flights[i].distance;
            }

            if (pos !=1) {
                flights[pos].skip = true;
                FlightInfo f = new FlightInfo(flights[pos].from, flights[pos].to, flights[pos].distance);


                return f;
            }
        }
        return null;
    }

    //Determine if there is route btw from and to
    void isflight(String from,String to){
        int dist;
        FlightInfo f;

        //See if at destination
        dist=match(from,to);
        if(dist!=0){
            btStack.push(new FlightInfo(from,to,dist));
            return;
        }

        //Try to find another connection
        f=find(from);
        if(f!=null){
            btStack.push(new FlightInfo(from,to,f.distance));
            isflight(f.to,to);
        }else if(btStack.size()>0){
            //Backtrack and try another connection
            f=(FlightInfo) btStack.pop();
            isflight(f.from,f.to);
        }
    }
}
