import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import maps.generic.GenericMapping;
public class RapsqlSplitTester {
  // private static final Logger logger = Logger.getLogger(RapsqlPartTester.class);

  public static void main(String[] args) {

    BasicConfigurator.configure();
    Logger.getRootLogger().setLevel(Level.OFF);    
    // logger.setLevel(Level.OFF);

    System.out.println("\nGeneric database mapping");

    RapsqlSplitWriter instance_pgwriter = 
      new RapsqlSplitWriter(
        "nres.csv",
        "nlit.csv",
        "nbn.csv",
        "eop.csv",
        "edtp.csv",
        "eop_part.txt",
        "edtp_part.txt"
      );
    RapsqlSplitWriter schema_pgwriter = 
      new RapsqlSplitWriter(
        "nres_schema.csv",
        "nlit_schema.csv",
        "nbn_schema.csv",
        "eop_schema.csv",
        "edtp_schema.csv",
        "eop_part_schema.txt",
        "edtp_part_schema.txt"
      );

    GenericMapping gdm = new GenericMapping();
    gdm.run("sp100.n3",instance_pgwriter,schema_pgwriter);
    // gdm.run("sp100k.n3",instance_pgwriter,schema_pgwriter);
    
  }
}
