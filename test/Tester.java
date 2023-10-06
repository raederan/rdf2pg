/* 
 * Copyright 2020 Renzo Angles (http://renzoangles.com/)
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

import maps.complete.CompleteMapping;
// import maps.generic.GenericMapping;
// import maps.simple.SimpleMapping;
// import pgraph.PropertyGraph;
// import writers.YPGWriter;

/**
 * Modified by Andreas Raeder
*/
public class Tester {

    public static void main(String[] args) {

        /*
        System.out.println("Simple database mapping");
        SimpleMapping sim = new SimpleMapping();
        sim.run("instance.nt");
        */

        /*
        System.out.println("Generic database mapping");
        GenericMapping gdm = new GenericMapping();
        gdm.run("instance.nt");
        */
        
        System.out.println("Complete database mapping");
        CompleteMapping cdm = new CompleteMapping();
        cdm.run("instance.nt", "schema.ttl");

        System.out.println("OK");
    }

}
