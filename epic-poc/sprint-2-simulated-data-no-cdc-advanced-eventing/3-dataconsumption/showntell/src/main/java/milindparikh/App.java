package milindparikh;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.UUID;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.boon.json.JsonCreator;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;







public class App extends Thread {
    protected static ObjectMapper mapper = JsonFactory.create();
    private final Cluster cluster;
    private final Session session;
    

    public App(String contactPoint) throws java.net.UnknownHostException {
	cluster = Cluster.builder()                                                    
	    .addContactPoint(contactPoint)
	    .build();
	session = cluster.connect();                                           
	
    }
    
    public class SortedCounter {
	public String clazz, rank, estimate, numberofestimators;
	public SortedCounter (String sclazz, String srank, String sestimate, String snumberofestimators) {
	    clazz = sclazz;
	    rank = srank;
	    estimate = sestimate;
	    numberofestimators = snumberofestimators;
	}
	public String clazz() {
	    return clazz;
	}
	public String rank() {
	    return rank;
	}
	public String  estimate() {
	    return estimate;
	}
	public String numberofestimators() {
	    return numberofestimators;
	}
    }



    public class BaseTransaction {
	public String clazz, rank, zip, estimate, modelName, ts;
	public  BaseTransaction  (String sclazz, String srank, String szip , String sestimate, String smodelName, String sts) {
	    clazz = sclazz;
	    rank = srank;
	    zip = szip;
	    estimate = sestimate;
	    modelName = smodelName;
	    ts = sts;
	}
	public String clazz() {
	    return clazz;
	}
	public String rank() {
	    return rank;
	}
	public String zip() {
	    return zip;
	}
	public String  estimate() {
	    return estimate;
	}
	public String modelName() {
	    return modelName;
	}
	public String ts () {
	    return ts;
	}
    }






    
    public void run() {

		    
		Spark.get("/", (request, response) -> {

			Map<String, Object> attributes = new HashMap<>();
			return new ModelAndView(attributes, "index.mustache");
		}, new MustacheTemplateEngine());


		Spark.get(
			  "/search",
			  (request, response) -> {


			      String state = request.queryParams("state");
			      String dateReported = request.queryParams("dateReported");

			      String selectBE = "SELECT estimate FROM mykeyspace.bestestimate where datereported = \'" + dateReported + "' AND state = \'" + state + "\'";
			      System.out.println(selectBE);
			      
			      Map<String, Object> attributes = new HashMap<>();
			      ResultSet rs = session.execute(selectBE);
			      Iterator<Row> iter = rs.iterator();
			      Row row;

			      DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ssZ");
			      row = rs.one();
			      
			      
			      attributes.put("bestData", String.valueOf(row.getInt("estimate")));
			      
			      
			      
			      String selectSC = "SELECT class, rank,  estimate, numberofestimators FROM mykeyspace.sortedcounter where datereported = \'" + dateReported + "' AND state = \'" + state + "\'";
			      System.out.println(selectSC);
			      
			      List <SortedCounter> sortedCounters = new ArrayList<SortedCounter>();

			      rs = session.execute(selectSC);
			      iter = rs.iterator();

			      
			      while (iter.hasNext()) {
				  row = iter.next();
				  sortedCounters.add (new SortedCounter (String.valueOf(row.getInt("class")),
									 String.valueOf(row.getInt("rank")),
									 String.valueOf(row.getInt("estimate")),
									 String.valueOf(row.getInt("numberofestimators"))));
											
			      }

			      List <BaseTransaction> baseTransactions = new ArrayList<BaseTransaction>();
			      String selectBT = "SELECT class, rank, zip, estimate, modelname, ts FROM mykeyspace.basetransaction where datereported = \'" + dateReported + "' AND state = \'" + state + "\' ";
			      System.out.println(selectBT);
			      rs = session.execute(selectBT);
			      iter = rs.iterator();

			      while (iter.hasNext()) {
				  row = iter.next();
				  baseTransactions.add (new BaseTransaction (String.valueOf(row.getInt("class")),
									     String.valueOf(row.getInt("rank")),
									     String.valueOf(row.getInt("zip")),
									     String.valueOf(row.getInt("estimate")),
									     row.getString("modelname"),
									     df.format(row.getTimestamp("ts"))));
			      }


			      			      


			      
			   
			      attributes.put("State", state);
			      attributes.put("dateReported", dateReported);

			      attributes.put("sortedCounters", sortedCounters);
			      attributes.put("baseTransactions", baseTransactions);


			      return new ModelAndView(attributes, "index.mustache");
			  }, new MustacheTemplateEngine());


    }
    
    
	public static void main(String[] args) throws Exception {
	    	try {
		    App webThread = new App(args[0]);
		    webThread.start();
		}
		catch (java.net.UnknownHostException uhe) {
		    System.out.println(uhe);
		}
	}
}
