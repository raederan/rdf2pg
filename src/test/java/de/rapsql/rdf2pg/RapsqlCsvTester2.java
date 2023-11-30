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

import de.rapsql.rdf2pg.maps.generic.GenericMapping;

public class RapsqlCsvTester2 {
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
    String nodes_filename = "nodes.ypg";
    String edges_filename = "edges.ypg";
    RapsqlCsvWriter2 instance_pgwriter = new RapsqlCsvWriter2(nodes_filename, edges_filename);
    RapsqlCsvWriter2 schema_pgwriter = new RapsqlCsvWriter2(nodes_filename, edges_filename);
    GenericMapping gdm = new GenericMapping();
    gdm.run("src/test/resources/rdf-instance.ttl",instance_pgwriter,schema_pgwriter);
    
    // System.out.println("Complete database mapping");
    // RapsqlWriter instance_pgwriter2 = new RapsqlWriter("cdm-instance.ypg");
    // RapsqlWriter schema_pgwriter2 = new RapsqlWriter("cdm-schema.ypg");
    // CompleteMapping cdm = new CompleteMapping();
    // cdm.run("instance.nt", "schema.ttl",instance_pgwriter2,schema_pgwriter2);
  }
}
