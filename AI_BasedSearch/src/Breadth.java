import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Breadth {

        final int MAX = 100; //max no. of connections

        FlightInfo[] flights = new FlightInfo[MAX];

        int numFlights = 0; // no. of entries in Flight Array
        Stack btStack = new Stack(); //backtrack stack;

        public static void second (String[]args){
            String to, from;
            Breadth ob = new Breadth();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            ob.setup();

            try {
                System.out.println("From? ");
                from = br.readLine();
                System.out.println("To? ");
                to = br.readLine();

                ob.isflight(from, to);

                if (ob.btStack.size() != 0) {
                    ob.route(to);
                }
            } catch (IOException exc) {
                System.out.println("Error on input.");
            }
        }

        void addFlight (String from, String to,int dist){
            if (numFlights < MAX) {
                flights[numFlights] = new FlightInfo(from, to, dist);

                numFlights++;
            } else {
                System.out.println("Flight Database full. \n");
            }
        }

        void setup () {
            addFlight("New York", "Chicago", 900);
            addFlight("New York", "Toronto", 500);
            addFlight("New York", "Denver", 1800);
            addFlight("Chicago", "Denver", 1000);
            addFlight("Houston", "Los Angeles", 1500);
            addFlight("Toronto", "Calgary", 1700);
            addFlight("Toronto", "Los Angeles", 2500);
            addFlight("Toronto", "Chicago", 500);
            addFlight("Denver", "Urbana", 1000);
            addFlight("Denver", "Houston", 1000);
            addFlight("Denver", "Los Angeles", 1000);
        }

        void route (String to){
            Stack rev = new Stack();
            int dist = 0;
            FlightInfo f;
            int num = btStack.size();

            for (int i = 0; i < num; i++) {
                rev.push(btStack.pop());
            }

            for (int i = 0; i < num; i++) {
                f = (FlightInfo) rev.pop();
                System.out.println(f.from + " to ");
                dist += f.distance;
            }
            System.out.println(to);
            System.out.println("Distance is " + dist);
        }

        int match (String from, String to){
            for (int i = numFlights - 1; i > -1; i--) {
                if (flights[i].from.equals(from) && flights[i].to.equals(to) && !flights[i].skip) {
                    flights[i].skip = true;
                    return flights[i].distance;
                }
            }
            return 0; //not found
        }

        //Given from, find any connection flight
        public FlightInfo find (String from){
            for (int i = 0; i < numFlights; i++) {
                if (flights[i].from.equals(from) && !flights[i].skip) {
                    FlightInfo f = new FlightInfo(flights[i].from, flights[i].to, flights[i].distance);
                    flights[i].skip = true;

                    return f;
                }
            }
            return null;
        }

        //Determine if there is route btw from and to
        void isflight (String from, String to){
            int dist;
            FlightInfo f;

            Stack resetStck = new Stack();

            //See if at destination
            dist = match(from, to);
            if (dist != 0) {
                btStack.push(new FlightInfo(from, to, dist));
                return;
            }

          while((f=find(from))!=null){
              resetStck.push(f);
              if((dist=match(f.to,to))!=0){
                  resetStck.push(f.to);
                  btStack.push(new FlightInfo(from,f.to,f.distance));
                  btStack.push(new FlightInfo(f.to,to,dist));
                  return;
              }
          }


            int i=resetStck.size();
            for(; i!=0; i--) {
                resetSkip((FlightInfo) resetStck.pop());


            }
        }

        void resetSkip (FlightInfo f){
            for (int i = 0; i < numFlights; i++) {
                if (flights[i].from.equals(f.from) && flights[i].to.equals(f.to)) {
                    flights[i].skip = false;
                }
            }
        }


    }
