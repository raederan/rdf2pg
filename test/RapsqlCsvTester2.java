import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import maps.generic.GenericMapping;
import writers.RapsqlCsvWriter2;

public class RapsqlCsvTester2 {
  // private static final Logger logger = Logger.getLogger(RapsqlPartTester.class);

  public static void main(String[] args) {

    BasicConfigurator.configure();
    Logger.getRootLogger().setLevel(Level.OFF);    
    // logger.setLevel(Level.OFF);

    System.out.println("\nGeneric database mapping");
    
    RapsqlCsvWriter2 instance_pgwriter = 
      new RapsqlCsvWriter2(
        "nodes.csv", 
        "edges.csv"
      );
    RapsqlCsvWriter2 schema_pgwriter = 
      new RapsqlCsvWriter2(
        "nodes-schema.csv", 
        "edges-schema.csv"
      );

    GenericMapping gdm = new GenericMapping();
    gdm.run("sp100.n3",instance_pgwriter,schema_pgwriter);
    
  }
}
