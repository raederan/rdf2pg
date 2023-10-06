// import maps.complete.CompleteMapping;
import maps.generic.GenericMapping;
// import maps.simple.SimpleMapping;


// public class RapsqlTester {
//   public static void main(String[] args) {
//     System.out.println("Simple database mapping");
//     SimpleMapping sim = new SimpleMapping();
//     RapsqlWriter pgwriter = new RapsqlWriter("sdm-instance.ypg");
//     sim.run("instance.nt", pgwriter);
            
//     System.out.println("Generic database mapping");
//     RapsqlWriter instance_pgwriter = new RapsqlWriter("gdm-instance.ypg");
//     RapsqlWriter schema_pgwriter = new RapsqlWriter("gdm-schema.ypg");
//     GenericMapping gdm = new GenericMapping();
//     gdm.run("instance.nt",instance_pgwriter,schema_pgwriter);
    
//     System.out.println("Complete database mapping");
//     RapsqlWriter instance_pgwriter2 = new RapsqlWriter("cdm-instance.ypg");
//     RapsqlWriter schema_pgwriter2 = new RapsqlWriter("cdm-schema.ypg");
//     CompleteMapping cdm = new CompleteMapping();
//     cdm.run("instance.nt", "schema.ttl",instance_pgwriter2,schema_pgwriter2);
//   }
// }

public class RapsqlTester {
  public static void main(String[] args) {
    // System.out.println("Simple database mapping");
    // SimpleMapping sim = new SimpleMapping();
    // RapsqlWriter pgwriter = new RapsqlWriter("sdm-instance.ypg");
    // sim.run("instance.nt", pgwriter);

    // String[] n_filenames = {"Resource.csv","gdm-schema.csv"};
    // String[] e_filenames = {"Resource.csv","gdm-schema.csv"};

    
    System.out.println("Generic database mapping");
    // RapsqlCsvWriter instance_pgwriter = new RapsqlCsvWriter(n_filenames, e_filenames);
    // RapsqlCsvWriter instance_pgwriter = new RapsqlCsvWriter(n_filenames, e_filenames);

    RapsqlCsvWriter instance_pgwriter = new RapsqlCsvWriter("gdm-instance.csv");
    RapsqlCsvWriter schema_pgwriter = new RapsqlCsvWriter("gdm-schema.ypg");
    GenericMapping gdm = new GenericMapping();
    gdm.run("instance.nt",instance_pgwriter,schema_pgwriter);
    
    // System.out.println("Complete database mapping");
    // RapsqlWriter instance_pgwriter2 = new RapsqlWriter("cdm-instance.ypg");
    // RapsqlWriter schema_pgwriter2 = new RapsqlWriter("cdm-schema.ypg");
    // CompleteMapping cdm = new CompleteMapping();
    // cdm.run("instance.nt", "schema.ttl",instance_pgwriter2,schema_pgwriter2);
  }
}
