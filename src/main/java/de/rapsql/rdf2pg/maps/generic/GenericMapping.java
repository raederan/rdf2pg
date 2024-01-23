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

package de.rapsql.rdf2pg.maps.generic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.apache.jena.riot.lang.LabelToNode;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFParser;
import org.apache.jena.riot.system.FactoryRDF;
import org.apache.jena.riot.system.FactoryRDFStd;
import org.apache.jena.riot.system.stream.StreamManager;
// import org.apache.jena.util.FileManager;
import de.rapsql.rdf2pg.pgraph.PGEdge;
import de.rapsql.rdf2pg.pgraph.PGNode;
import de.rapsql.rdf2pg.pgraph.PropertyGraph;
import de.rapsql.rdf2pg.writers.PGWriter;
// import writers.YPGWriter;
// import writers.RapsqlCsvWriter;
import de.rapsql.rdf2pg.writers.RapsqlCsvWriter2;

public class GenericMapping {

    PropertyGraph pg_instance;
    PropertyGraph pg_schema;

    public GenericMapping() {
        pg_instance = new PropertyGraph();
        pg_schema = new PropertyGraph();
    }

    public PropertyGraph getPGInstance() {
        return pg_instance;
    }

    public PropertyGraph getPGSchema() {
        return pg_schema;
    }

    public void run(String inputFileName) {
        // RapsqlCsvWriter instance_pgwriter = new RapsqlCsvWriter(inputFileName.replace(".nt", "-i.ypg"));
        // RapsqlCsvWriter schema_pgwriter = new RapsqlCsvWriter(inputFileName.replace(".nt", "-s.ypg"));
        RapsqlCsvWriter2 instance_pgwriter = new RapsqlCsvWriter2("nodes.ypg", "edges.ypg");
        RapsqlCsvWriter2 schema_pgwriter = new RapsqlCsvWriter2("nodes_schema.ypg", "edges_schema.ypg");
        this.run(inputFileName, instance_pgwriter, schema_pgwriter);
    }

    public void run(String inputFileName, PGWriter instance_pgwriter, PGWriter schema_pgwriter) {
        this.runInstanceMapping(inputFileName, instance_pgwriter);
        this.runSchemaMapping(schema_pgwriter);
    }

    // $issue: each parser run creates new blanknode ids
    // https://stackoverflow.com/a/65060705
    public Model runModelMapping4(String inputFileName, PGWriter instance_pgwriter) {
        Model model = ModelFactory.createDefaultModel();
        try {
            instance_pgwriter.begin();
            // define input
            InputStream in= RDFDataMgr.open( inputFileName );
            if (in == null) {
                throw new IllegalArgumentException("File: " + inputFileName + " not found");
            } 
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            in.transferTo(baos);
            InputStream in1 = new ByteArrayInputStream(baos.toByteArray()); 
            InputStream in2 = new ByteArrayInputStream(baos.toByteArray()); 
            FactoryRDF factory = new FactoryRDFStd(LabelToNode.createUseLabelAsGiven());
            RDFParser.source(in1).factory(factory).lang(Lang.TTL).parse(new Reader2(instance_pgwriter));
            model = RDFParser.source(in2).factory(factory).lang(Lang.TTL).toModel();
            instance_pgwriter.end();
            
        } catch (Exception ex) {
            System.out.println("Error runModelMapping: " + ex.getMessage());
        }
        return model;
    }
    // // $issue: each parser run creates new blanknode ids
    // // https://stackoverflow.com/a/65060705
    // public void runModelMapping(Model model, PGWriter instance_pgwriter) {
    //     try {
    //         // handle model instead of file
    //         Reader2 reader = new Reader2(instance_pgwriter);
    //         System.out.println("\nDEBUG RDF2PG runModelMapping MODEL:\n" + model + "\n");
    //         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    //         instance_pgwriter.begin();

    //         model.write(outputStream, Lang.TTL.getName());
    //         InputStream in = new ByteArrayInputStream(outputStream.toByteArray());

    //         FactoryRDF factory = new FactoryRDFStd(LabelToNode.createUseLabelAsGiven());
    //         RDFParser.source(in).factory(factory).lang(Lang.TTL).parse(reader);

    //         instance_pgwriter.end();
    //     } catch (Exception ex) {
    //         System.out.println("Error runModelMapping: " + ex.getMessage());
    //     }
    // }

    public void runModelMapping2(Model model, PGWriter instance_pgwriter) {
        try {
            // handle model instead of file
            Reader2 reader = new Reader2(instance_pgwriter);
            System.out.println("\nDEBUG RDF2PG runModelMapping MODEL:\n" + model + "\n");
            
            // Create an OutputStream


            instance_pgwriter.begin();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            model.write(outputStream, Lang.TTL.getName());
            InputStream in = new ByteArrayInputStream(outputStream.toByteArray());
            RDFParser.source(in).lang(Lang.TTL).parse(reader);
            

            // System.out.println("\nDEBUG RDF2PG runModelMapping TTL:\n" + ttlString + "\n");
            // RDFParser.fromString(ttlString).lang(Lang.TTL).parse(reader);
            instance_pgwriter.end();


        } catch (Exception ex) {
            System.out.println("Error runModelMapping: " + ex.getMessage());
        }
    }


    public Model runModelMapping(String inputFileName, PGWriter instance_pgwriter) {
        // create default model factory
        Model model = ModelFactory.createDefaultModel();
        try {
            // handle model instead of file
            InputStream in = RDFDataMgr.open( inputFileName );
            if (in == null) {
                throw new IllegalArgumentException("File: " + inputFileName + " not found");
            }   

            instance_pgwriter.begin();
            Reader2 reader = new Reader2(instance_pgwriter);
            // System.out.println("\nDEBUG RDF2PG runModelMapping INPUTSTREAM:\n" + in + "\n");
            
            RDFParser parser = RDFParser.source(in).lang(Lang.TTL).build();
            parser.parse(reader);
            
            
            FactoryRDF factory = new FactoryRDFStd(LabelToNode.createUseLabelAsGiven());
            
            String rdf_str = reader.getRDFString();
            System.out.println("\nDEBUG RDF2PG runModelMapping RDFSTRING:\n" + rdf_str + "\n");
            model = RDFParser.fromString(rdf_str).factory(factory).lang(Lang.TTL).toModel();
            System.out.println("\nDEBUG RDF2PG runModelMapping MODEL:\n" + model + "\n");
            
            // RDFParser.source(in).lang(Lang.TTL).parse(reader);
            instance_pgwriter.end();

        } catch (Exception ex) {
            System.out.println("Error runModelMapping: " + ex.getMessage());
        }
        return model;
    }

    public void runInstanceMapping(String inputFileName, PGWriter pgwriter) {
        try {
            InputStream in = StreamManager.get().open(inputFileName);
            pgwriter.begin();
            Reader2 reader = new Reader2(pgwriter);
            if (in == null) {
                throw new IllegalArgumentException("File not found");
            }
            RDFParser.source(in).lang(Lang.TTL).parse(reader);
            pgwriter.end();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void runSchemaMapping(PGWriter pgwriter) {
        try {
            pgwriter.begin();

            PGNode rnode = new PGNode(1);
            rnode.addLabel("Resource");
            rnode.addProperty("rdfid", "String");
            pgwriter.writeNode(rnode);
            
            PGNode bnode = new PGNode(2);
            bnode.addLabel("BlankNode");
            bnode.addProperty("rdfid", "String");
            pgwriter.writeNode(bnode);

            PGNode lnode = new PGNode(3);
            lnode.addLabel("Literal");
            // change to rdfid: value^^type (ign String)
            lnode.addProperty("rdfid", "String");
            lnode.addProperty("type", "String");
            pgwriter.writeNode(lnode);
            
            PGEdge edge4 = new PGEdge(4,rnode.getId(),rnode.getId());
            edge4.addLabel("ObjectProperty");
            edge4.addProperty("rdfid","String");
            pgwriter.writeEdge(edge4);

            PGEdge edge5 = new PGEdge(5,rnode.getId(),bnode.getId());
            edge5.addLabel("ObjectProperty");
            edge5.addProperty("rdfid","String");
            pgwriter.writeEdge(edge5);
            
            PGEdge edge6 = new PGEdge(6,bnode.getId(),rnode.getId());
            edge6.addLabel("ObjectProperty");
            edge6.addProperty("rdfid","String");
            pgwriter.writeEdge(edge6);

            PGEdge edge7 = new PGEdge(7,bnode.getId(),bnode.getId());
            edge7.addLabel("ObjectProperty");
            edge7.addProperty("rdfid","String");
            pgwriter.writeEdge(edge7);
            
            PGEdge edge8 = new PGEdge(8,rnode.getId(),lnode.getId());
            edge8.addLabel("DatatypeProperty");
            edge8.addProperty("rdfid","String");
            pgwriter.writeEdge(edge8);

            PGEdge edge9 = new PGEdge(9,bnode.getId(),lnode.getId());
            edge9.addLabel("DatatypeProperty");
            edge9.addProperty("rdfid","String");
            pgwriter.writeEdge(edge9);
            
            pgwriter.end();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
