package student;

import javafx.scene.shape.VertexFormat;
import model.*;
import model.Player;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Test class for Rep
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class FindifValid {
    public FindifValid(){}


    /**
     * Returns an ArrayList of Rep Stations
     * @param P1 - Player
     * @return - ArrayList<Rep>
     */
    public static ArrayList<Rep> MakeAllP2(Player P1) {
        Collection<model.Route> L = P1.getClaimedRoutes();
        HashMap<model.Station, Rep> MAP = new HashMap<>();
        //Iterate through every single Route
        for (model.Route R : L) {
            //Get the two stations
            model.Station Station1 = R.getDestination();
            model.Station Station2 = R.getOrigin();
            //Check if the stations are already mapped with their Rep
            if (!MAP.containsKey(Station1)) {
                //If the hashmap already contains the first station
                Rep Station1Rep = new Rep(Station1, 0);
                MAP.put(Station1, Station1Rep);
                if (MAP.containsKey(Station2)) {
                    //Checks if Station2 is already in the hashmap
                    MAP.get(Station1).AddNeighbor(MAP.get(Station2));
                    MAP.get(Station2).AddNeighbor(MAP.get(Station1));
                } else {
                    //If the map does not contain station 2
                    Rep Station2Rep = new Rep(Station2, 0);
                    MAP.put(Station2, Station2Rep);
                    MAP.get(Station1).AddNeighbor(MAP.get(Station2));
                    if (MAP.containsKey(Station1)) {
                        //Adds the reference of the first rep to second
                        MAP.get(Station2).AddNeighbor(MAP.get(Station1));
                    }

                }
            } else {
                //The map already contains station1
                if (MAP.containsKey(Station2)) {
                    MAP.get(Station1).AddNeighbor(MAP.get(Station2));
                    MAP.get(Station2).AddNeighbor(MAP.get(Station1));

                } else {
                    //If the map does not contain station 2
                    Rep Station2Rep = new Rep(Station2, 0);
                    MAP.put(Station2, Station2Rep);
                    MAP.get(Station1).AddNeighbor(MAP.get(Station2));
                    if (MAP.containsKey(Station1)) {
                        //Adds the reference of the first rep to second
                        MAP.get(Station2).AddNeighbor(MAP.get(Station1));
                    }

                }
            }
        }
        ArrayList<Rep> ReturnL = new ArrayList<>();
        for (model.Station S : MAP.keySet()) {
            ReturnL.add(MAP.get(S));
        }
        return ReturnL;
    }



    /**
     * Finds all the Horizontal Starting Node
     * @param In - Collection<student.Rep>
     * @return - Collection<student.Rep>
     */
    public static Collection<student.Rep> FindHorStart(Collection<student.Rep> In,int C){
        LinkedList<student.Rep> ReturnL = new LinkedList<>();
        for(Rep I: In){
            if(I.getStation().getCol() == C){
                ReturnL.add(I);
            }
        }
        return ReturnL;
    }

    /**
     * Finds all the Horizontal end node
     * @param In -Collection<student.Rep>
     * @param C - int
     * @return - Collection<student.Rep>
     */
    public static Collection<student.Rep> FindHorEnd(Collection<student.Rep> In, int C){
        LinkedList<student.Rep> ReturnL = new LinkedList<>();
        for(student.Rep I: In){
            if(I.getStation().getCol() == C){
                ReturnL.add(I);
            }
        }
        return ReturnL;
    }

    /**
     * Finds all the Vertical start node
     * @param In - Collection<student.Rep>
     * @return - Collection<student.Rep>
     */
    public static Collection<student.Rep> FindVertStart(Collection<student.Rep> In,int  C){
        LinkedList<student.Rep> ReturnL = new LinkedList<>();

        for(student.Rep I:In){
            if(I.getStation().getRow() ==C ){
                ReturnL.add(I);
            }

        }
        return ReturnL;
    }

    /**
     * Finds all the vertical end node
     * @param In - Collection<student.Rep>
     * @param C - int
     * @return - Collection<student.Rep>
     */
    public static Collection<student.Rep> FindVertEnd(Collection<student.Rep> In, int C){
        LinkedList<student.Rep> ReturnL = new LinkedList<>();
        for(student.Rep I: In){
            if(I.getStation().getRow() == C){
                ReturnL.add(I);
            }
        }
        return ReturnL;

    }

    /**
     * Returns if the start and the end connect
     * @param Start - Rep
     * @param End - Rep
     * @param counter - int
     * @param Checked - LinkedList<Rep>
     * @param Counter - Rep
     * @return - true or false
     */
    public static boolean GraphDFS(Rep Start, Rep End, int counter,LinkedList<Rep> Checked,Rep Counter){
        int c2 = counter;
        Checked.add(Start);
        if(Start.equals(End)){
            if(counter >=3){
                Counter.IncreaseI();
            }


        }
        for (Rep R: Start.getNeighbors()){


            if(!Checked.contains(R)){
                System.out.println("before counter" + counter);
                c2++;
             GraphDFS(R,End,c2,Checked,Counter);
            }
        }
        return Start.equals(End);
    }

    /**
     * Returns if Rep Counter has an integer greater than 0
     * @param StartNodes - Collection<Rep>
     * @param EndNodes - Collection<Rep>
     * @return - true or false
     */
    public static boolean CheckforHorizontal(Collection<Rep> StartNodes,Collection<Rep> EndNodes){
        Station CheckStation = new Station(2000000000,2000000000,"WE",200000000);
        Rep Counter = new Rep(CheckStation,0);
        for(Rep Start: StartNodes){
            for(Rep End: EndNodes){
                LinkedList<Rep> New = new LinkedList<>();
                FindifValid.GraphDFS(Start,End,1,New,Counter);
            }
        }

        return (Counter.getI()>0);

    }

    /**
     * Finds the first column for a horizontal cross route
     * @param MAP - model.RailroadMap
     * @return - int
     */
    public static int findHorBegan(model.RailroadMap MAP){

        int comp = 1000000000;
        for(model.Route R:MAP.getRoutes()){
            model.Station Temp = R.getDestination();
            model.Station Temp2 = R.getOrigin();
            if(Temp.getCol() < comp){
                comp = Temp.getCol();
            }
            if(Temp2.getCol() < comp){
                comp = Temp2.getCol();
            }
        }
        return comp;
    }

    /**
     * Finds the first row for a vertical cross route
     * @param MAP - model.RailroadMap
     * @return - int
     */
    public static int findVertBegan(model.RailroadMap MAP){
        int comp = 1000000000;
        for(model.Route R:MAP.getRoutes()){
            model.Station Temp = R.getDestination();
            model.Station Temp2 = R.getOrigin();
            if(Temp.getRow() < comp){
                comp = Temp.getRow();
            }
            if(Temp2.getRow() < comp){
                comp = Temp2.getRow();
            }
        }
        return comp;
    }

    /**
     * Finds a list of the possible cross route bonuses
     * @param MAP - model.RailroadMap
     * @return - ArrayList<Integer>
     */
    public static ArrayList<Integer> FindBonusVals(model.RailroadMap MAP){
        ArrayList<Integer> HorVertBonus = new ArrayList<Integer>();
        int VertBonus = MAP.getRows()-FindifValid.findVertBegan(MAP);
        int HortBonus = MAP.getCols()- FindifValid.findHorBegan(MAP);
        HorVertBonus.add(HortBonus);
        HorVertBonus.add(VertBonus);
        return HorVertBonus;
    }

    /**
     * Checks for if the players should get the bonus
     * @param P - Player
     * @param MAP - RailroadMap
     * @return - ArrayList<Boolean>
     */
    public static ArrayList<Boolean> BonusQ(Player P, RailroadMap MAP){

        ArrayList<Rep>  ConvertoGraph = FindifValid.MakeAllP2(P);
        int HorStart = FindifValid.findHorBegan(MAP);
        Collection<Rep> HorizontalStart = FindifValid.FindHorStart(ConvertoGraph,HorStart);
        Collection<Rep> HorizontalEnd = FindifValid.FindHorEnd(ConvertoGraph,MAP.getCols()-1);
        int VertStart = FindifValid.findVertBegan(MAP);
        Collection<Rep> VerticalStart = FindifValid.FindVertStart(ConvertoGraph,VertStart);
        System.out.println("HorStart:" + HorStart + " VertStart: "+ VertStart);
        Collection<Rep> VerticalEnd = FindifValid.FindVertEnd(ConvertoGraph,MAP.getRows()-1);
        System.out.println(P.getBaron().toString() + "This format Hor Start, Hor End, vert Start,vert end");
        System.out.println(HorizontalStart.size()+","+HorizontalEnd.size()+","+VerticalStart.size()+","+VerticalEnd.size());
        Rep HorizontalCounter = new Rep(new Station(2000000000,200000000,"Some",100000),0);
        Rep VerticalCounter = new Rep (new Station(2000000,200000,"Where",200000),0);
        //Goes through every horizontal and check if its true
        boolean HorizontalTruth = FindifValid.CheckforHorizontal(HorizontalStart,HorizontalEnd);
        boolean VertTruth = FindifValid.CheckforHorizontal(VerticalStart,VerticalEnd);
        ArrayList<Boolean> returnval = new ArrayList<>();
        returnval.add(HorizontalTruth);
        returnval.add(VertTruth);
        return returnval;
    }





    /**
     * Test case
     * @param args
     */
    public static void main(String[] args){
        Collection<Card> ca = new LinkedList<>();
        student.Player P = new student.Player(Baron.BLUE,ca);
        Station S1 = new Station(1,2,"12",0);
        Station S2 = new Station(1,0,"10",1);
        Station S3 = new Station(1,1,"11",2);
        Station S4 = new Station(0,1,"01",4);
        Station S5 = new Station(2, 1, "21", 5);
        Station S6 = new Station(2, 2, "22", 6);
        Station S7 = new Station(6, 1, "61", 7);
        Station S8 = new Station(1, 3, "13", 8);
        Station S9 = new Station(2, 3, "23", 9);
        Station S10 = new Station(4,6,"46",10);
        Route R1 = new Route(S1,S3, Orientation.HORIZONTAL);
        Route R2 = new Route(S2,S3,Orientation.HORIZONTAL);
        Route R3 = new Route(S4,S3,Orientation.VERTICAL);
        Route R4 = new Route(S3,S5,Orientation.VERTICAL);
        Route R5 = new Route(S5,S7,Orientation.VERTICAL);
        Route R6 = new Route(S5,S6,Orientation.HORIZONTAL);
        Route R7 = new Route(S1,S8,Orientation.HORIZONTAL);
        Route R8 = new Route(S8, S9, Orientation.VERTICAL);
        Route R9 = new Route(S6,S9,Orientation.HORIZONTAL);
        P.Add(R1);
        P.Add(R2);
        P.Add(R3);
        P.Add(R4);
        P.Add(R5);
        P.Add(R6);
        P.Add(R7);
        P.Add(R8);
        P.Add(R9);
        P.Add(R1);
        P.Add(R2);
        P.Add(R3);
        P.Add(R4);
        ArrayList<Rep> PleaseWork = FindifValid.MakeAllP2(P);
        //Collection<Rep> HorizontalStart = FindifValid.FindHorStart(PleaseWork);
        //Collection<Rep> HorizontalEnd = FindifValid.FindHorEnd(PleaseWork,3);
        //Collection<Rep> VertS = FindifValid.FindVertStart(PleaseWork);
        //Collection<Rep> VertE =  FindifValid.FindVertEnd(PleaseWork,6);
        /**
        for(Rep R: HorizontalStart){
            System.out.println("This is a Hor Start");
            System.out.println(R);
        }
        for(Rep R: HorizontalEnd){
            System.out.println("This is a Hor End");
            System.out.println(R);
        }
        for(Rep R: VertS){
            System.out.println("This is a Vert Start");
            System.out.println(R);
        }
        for(Rep R: VertE){
            System.out.println("This is a Vert End");
            System.out.println(R);
        }
        //boolean B = FindifValid.CheckforHorizontal(HorizontalStart,HorizontalEnd);
        boolean V = FindifValid.CheckforHorizontal(VertE,VertS);
        for(Rep R:PleaseWork){
            System.out.println(R.toString()+R.getRank());
        }**/
        //System.out.println(V);
        //System.out.println(B);


        //System.out.println(Start);
        //System.out.println(End);
        //End = new Rep(S10,0);
        //Station Random = new Station(-1,-1,"Random",123131231);
        //Rep RandomRep = new Rep(Random,0);
        //boolean B  = FindifValid.GraphDFS(Start,End,15,new LinkedList<>(),RandomRep);
        //System.out.println(RandomRep.getI()>0);
        //System.out.println(B);





    }
}
