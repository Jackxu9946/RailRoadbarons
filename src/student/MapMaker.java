package student;

import model.*;
import model.Station;

import java.io.*;
import java.util.*;

/**
 * Used to load and save maps
 *
 * @author Jack Xu
 * @author Savannah Mattia
 */

public class MapMaker implements model.MapMaker, Serializable {
    private RailroadMap map;
    private List<model.Route> route;
    public MapMaker(){this.route = new LinkedList<>();};


    /**
     * Loads a map using hte data in the given input stream
     * @param in The {@link InputStream} used to read the {@link RailroadMap
     * map} data.
     * @return - RailroadMap
     * @throws RailroadBaronsException
     */
    @Override
    public RailroadMap readMap(InputStream in) throws RailroadBaronsException {
        try{
        Scanner sc = new Scanner(in);
        HashMap<Integer,Station> station = new HashMap<>();
        HashMap<Station,Integer> stationtoint = new HashMap<>();
        List<model.Route> route = new LinkedList<>();
        int compr = 0;
        int compc = 0;
        while( sc.hasNextLine()){
            String parse = sc.nextLine();
            if(parse.equals("##ROUTES##")){
                break;
            }
            else{
                String[] parse2 = parse.split(" ");
                int stationnumber = Integer.parseInt(parse2[0]);
                int row = Integer.parseInt(parse2[1]);
                if( row > compr){
                    compr = row;
                }
                int column = Integer.parseInt(parse2[2]);
                if( column > compc){
                    compc = column;
                }
                String stationname = parse2[3];
                student.Station st = new student.Station(row,column,stationname,stationnumber);
                station.put(stationnumber,st);
                stationtoint.put(st,stationnumber);
            }
        }
        while(sc.hasNext()){
            String parseroute = sc.nextLine();
            String[] parseroute2 = parseroute.split(" ");
            int originstationnumber = Integer.parseInt(parseroute2[0]);
            int finalstationnumber = Integer.parseInt(parseroute2[1]);
            Station originstation = station.get(originstationnumber);
            Station endstation = station.get(finalstationnumber);

            int originr = originstation.getRow();
            int finalr = endstation.getRow();
            Orientation  or = null;
            if( originr == finalr){
                or = Orientation.HORIZONTAL;
            }
            else{
                or = Orientation.VERTICAL;
            }
            Route rou = new Route(originstation,endstation,or);
            route.add(rou);
        }
        //System.out.println(compr);
        //System.out.println(compc);
        student.RailRoadMap rm = new RailRoadMap(compr+1,compc+1,route);
        return rm;}
        catch( Exception ex){
            throw new RailroadBaronsException("Error");
        }

    }

    /**
     * Writes a specified map in the Railroad Barons map file format to the given output stream
     * @param map The {@link RailroadMap map} to write out to the
     * {@link OutputStream}.
     * @param out The {@link OutputStream} to which the
     * {@link RailroadMap map} data should be written.
     *
     * @throws RailroadBaronsException
     */
    @Override
    public void writeMap(RailroadMap map, OutputStream out) throws RailroadBaronsException {
        try{
        PrintWriter pw = new PrintWriter(out,true);
        Collection<model.Route> route = map.getRoutes();
        LinkedList<String> routestring = new LinkedList<>();
        int number = 0;
        for(model.Route r: route){
            Station begin = r.getOrigin();
            Station end = r.getDestination();
            String beginnum = Integer.toString(number);
            String beginr = Integer.toString(begin.getRow());
            String beginc = Integer.toString(begin.getCol());
            String beginname = begin.getName();
            String out1 = beginnum +" " + beginr + " "+ beginc+ " "+ beginname;
            pw.println(out1);
            number++;
            String endnum = Integer.toString(number);
            String endr = Integer.toString(end.getRow());
            String endc = Integer.toString(end.getCol());
            String endname = begin.getName();
            String out2 = endnum + " " + endr+ " " + endc + " " + endname;
            pw.println(out2);
            String routest = beginnum + " " + endnum+ " "+  r.getBaron();
            routestring.add(routest);
            }
            pw.println("##ROUTES##");
        for(String s: routestring){
            pw.println(s);
        }
    }
    catch(Exception ex){
            throw new RailroadBaronsException("Error");
    }
}
}
