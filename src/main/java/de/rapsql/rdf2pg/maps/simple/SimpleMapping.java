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

/*
 * Modified by Andreas Raeder
*/

package de.rapsql.rdf2pg.maps.simple;

import java.io.InputStream;
// import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFParser;
import org.apache.jena.riot.system.stream.StreamManager;
import de.rapsql.rdf2pg.writers.PGWriter;
// import de.rapsql.rdf2pg.writers.YPGWriter;
import de.rapsql.rdf2pg.writers.RapsqlCsvWriter;

public class SimpleMapping {
    
    public void run(String input_instance_filename) {
        // PGWriter pgwriter = new YPGWriter("instance.ypg");
        RapsqlCsvWriter pgwriter = new RapsqlCsvWriter("sdm-instance.csv");
        this.run(input_instance_filename, pgwriter);
    }
        
    public void run(String inputFileName, PGWriter pgwriter) {
        try {
            pgwriter.begin();
            Reader1a reader1a = new Reader1a(pgwriter);
            // InputStream in1 = FileManager.get().open(inputFileName);
            InputStream in1 = StreamManager.get().open(inputFileName);
            RDFParser.source(in1).lang(Lang.TTL).parse(reader1a);
            if (in1 == null) {
                throw new IllegalArgumentException("File not found");
            }
            in1.close();
            
            Reader1b reader1b = new Reader1b(pgwriter);
            reader1b.pos_hash_map = reader1a.pos_hash_map;
            reader1b.hash_node_map = reader1a.hash_node_map;
            
            InputStream in2 = StreamManager.get().open(inputFileName);
            RDFParser.source(in2).lang(Lang.TTL).parse(reader1b);
            if (in2 == null) {
                throw new IllegalArgumentException("File not found");
            }
            in2.close();

            pgwriter.end();
        } catch (Exception ex) {
            System.out.println("Error SimpleMapping.run():" + ex.getMessage());
        }
    }
    
    // private String getString(RDFNode node) {
    //     if (node.isURIResource()) {
    //         return node.asResource().getURI();
    //     } else if (node.isAnon()) {
    //         return node.asResource().getId().getLabelString();
    //     } else {
    //         return node.asLiteral().getString();
    //     }
    // }
    
    
}
