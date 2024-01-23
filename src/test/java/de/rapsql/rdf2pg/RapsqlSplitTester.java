/* 
 * Copyright 2023 Andreas Raeder
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package de.rapsql.rdf2pg;

import java.util.ArrayList;

import org.apache.jena.rdf.model.Model;

import de.rapsql.rdf2pg.maps.generic.GenericMapping;


public class RapsqlSplitTester {
  public static void main(String[] args) {
    // String inputFileName = "src/test/resources/w3c/test4.ttl";
    // System.out.println("RapsqlWriter: Generic database mapping");
    // RapsqlWriter2 instance_pgwriter = new RapsqlWriter2();
    // RapsqlWriter2 schema_pgwriter = new RapsqlWriter2();
    // GenericMapping gdm = new GenericMapping();
    // gdm.run(inputFileName,instance_pgwriter,schema_pgwriter);
    // ArrayList<String> lines;
    // lines = instance_pgwriter.getLines();

    // // System.out.println("Instance:\n" + lines + "\n");

    // System.out.println("\nInstance_by_line:");
    // // print single lines
    // for (String line : lines) {
    //   System.out.println(line);
    // }

    // // /////////// TEST runModelMapping file input ///////////
    // String inputFileName = "src/test/resources/w3c/test4.ttl";
    // System.out.println("RapsqlWriter2: gdm -> runModelMapping");
    // RapsqlWriter2 instance_pgwriter = new RapsqlWriter2();
    // Model model = RDFDataMgr.loadModel(inputFileName.toString());
    // GenericMapping gdm = new GenericMapping();
    // model = gdm.runModelMapping(inputFileName, instance_pgwriter);
    // ArrayList<String> lines;
    // lines = instance_pgwriter.getLines();

    // System.out.println("Model:\n" + model + "\n");

    // System.out.println("\nInstance_by_line:");
    // // print single lines
    // for (String line : lines) {
    //   System.out.println(line);
    // }
    
    /////////// TEST runModelMapping4 Model input ///////////
    String inputFileName = "src/test/resources/spcustom.n3";
    System.out.println("RapsqlSplitWriter: gdm -> runModelMapping");
    RapsqlSplitWriter instance_pgwriter = new RapsqlSplitWriter();
    GenericMapping gdm = new GenericMapping();
    Model rdf_model = gdm.runModelMapping4(inputFileName, instance_pgwriter);
    ArrayList<String> lines;
    lines = instance_pgwriter.getLines();

    System.out.println("Model:\n" + rdf_model.numPrefixes() + "\n");

    System.out.println("\nInstance_by_line:");
    // print single lines
    Integer cnt = 0;
    // String observe = ")-[e";
    // String observe = ":BlankNode";
    // String observe = "booktitle";
    // String observe = "creator";
    // String observe = "WHERE";
    for (String line : lines) {
      // if (line.contains(observe)){
      // // if (line.contains(":Resource")||line.contains(":BlankNode")||line.contains(":Literal")){
      //   cnt++;
      //   System.out.println(line);
      // }
      System.out.println(line);
    }
    System.out.println("Lines: " + cnt);



    // /////////// TEST runModelMapping Model input ///////////
    // String inputFileName = "src/test/resources/w3c/test4.ttl";
    // System.out.println("RapsqlWriter2: gdm -> runModelMapping");
    // RapsqlWriter2 instance_pgwriter = new RapsqlWriter2();
    // Model rdf_model = RDFDataMgr.loadModel(inputFileName.toString());
    // GenericMapping gdm = new GenericMapping();
    // gdm.runModelMapping(rdf_model, instance_pgwriter);
    // ArrayList<String> lines;
    // lines = instance_pgwriter.getLines();

    // System.out.println("\nInstance_by_line:");
    // // print single lines
    // for (String line : lines) {
    //   System.out.println(line);
    // }

    // /////////// TEST runModelMapping Model return ///////////
    // String inputFileName = "src/test/resources/w3c/test4.ttl";
    // System.out.println("RapsqlWriter2: gdm -> runModelMapping");
    // RapsqlWriter2 instance_pgwriter = new RapsqlWriter2();
    // GenericMapping gdm = new GenericMapping();
    // Model model = gdm.runModelMapping(inputFileName, instance_pgwriter);
    // ArrayList<String> lines;
    // lines = instance_pgwriter.getLines();

    // System.out.println("Model:\n" + model + "\n");

    // System.out.println("\nInstance_by_line:");
    // // print single lines
    // for (String line : lines) {
    //   System.out.println(line);
    // }
  
  
  }
}
