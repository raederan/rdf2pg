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

import de.rapsql.rdf2pg.maps.generic.GenericMapping;


public class RapsqlTester {
  public static void main(String[] args) {

    System.out.println("RapsqlWriter: Generic database mapping");
    RapsqlWriter instance_pgwriter = new RapsqlWriter();
    RapsqlWriter schema_pgwriter = new RapsqlWriter();
    GenericMapping gdm = new GenericMapping();
    gdm.run("src/test/resources/rdf-instance.ttl",instance_pgwriter,schema_pgwriter);
    ArrayList<String> lines;
    lines = instance_pgwriter.getLines();
    System.out.println("Instance:" + lines);

  }
}
