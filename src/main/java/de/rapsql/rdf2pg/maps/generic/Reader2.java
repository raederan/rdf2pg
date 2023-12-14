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
package de.rapsql.rdf2pg.maps.generic;

import java.io.OutputStream;
import java.util.HashMap;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.sparql.core.Quad;
import de.rapsql.rdf2pg.pgraph.PGNode;
import de.rapsql.rdf2pg.pgraph.PGEdge;
import de.rapsql.rdf2pg.writers.PGWriter;


/*
 * Modified by Andreas Raeder
 */
public class Reader2 implements StreamRDF {
    int oid = 1;
    int cnt = 0;
    OutputStream out;
    PGWriter pgwriter;
    //HashSet<Integer> nodeset = new HashSet();
    HashMap<Integer,PGNode> hash_node_map = new HashMap<>();
    String rdf_str = "";
    
    public Reader2(PGWriter _pgwriter) {
        this.pgwriter = _pgwriter;
    }

    public String getRDFString() {
        return rdf_str;
    }

    @Override
    public void start() {
    }

    @Override
    public void triple(Triple triple) {
        // System.out.println("DEBUG TRIPLE " + triple.toString());
        
        cnt++;
        Node s = triple.getSubject();
        Node p = triple.getPredicate();
        Node o = triple.getObject();
        //System.out.println(this.getNodeString(s) + " - " + this.getNodeString(p) + " - " + this.getNodeString(o));
        
        PGNode snode = hash_node_map.get(s.hashCode());

        rdf_str += "\n";

        
        if (snode == null) {
            if (s.isURI()) {
                snode = new PGNode(oid++);
                snode.addLabel("Resource");
                snode.addProperty("rdfid", s.getURI());

                rdf_str += s.getURI() + "\n";
            } else if(s.isBlank()) {
                snode = new PGNode(oid++);
                snode.addLabel("BlankNode");
                // get blanknode id directly instead of hashcode
                // String id = "_:b" + s.hashCode();
                // System.out.println("DEBUG BN SUB rdfid: " + s.getBlankNodeId().toString());
                // System.out.println("DEBUG BN SUB bnLbl: " + s.getBlankNodeLabel());
                snode.addProperty("rdfid", s.getBlankNodeId().toString());

                rdf_str += "_:" + s.getBlankNodeId().toString() + " ";
            } else{
                System.out.println("Error in Reader2.java");
                System.out.println("Invalid triple");
            }
            hash_node_map.put(s.hashCode(), snode);
            pgwriter.writeNode(snode);
        }
        
        if (o.isURI() || o.isBlank()) {
            PGNode tnode = hash_node_map.get(o.hashCode());
            if (tnode ==  null) {
                if (o.isURI()) {
                    tnode = new PGNode(oid++);
                    tnode.addLabel("Resource");
                    tnode.addProperty("rdfid", o.getURI());

                    rdf_str += "_:" + o.getURI() + " ";
                } else {
                    tnode = new PGNode(oid++);
                    tnode.addLabel("BlankNode");
                    // get blanknode id directly instead of hashcode
                    // String id = "_:b" + o.hashCode();
                    tnode.addProperty("rdfid", o.getBlankNodeId().toString());

                    rdf_str += o.getBlankNodeId().toString() + "\n";
                }
                hash_node_map.put(o.hashCode(), tnode);
                pgwriter.writeNode(tnode);

            }
            PGEdge edge = new PGEdge(oid++,snode.getId(),tnode.getId());
            edge.addLabel("ObjectProperty");
            edge.addProperty("rdfid", p.getURI());
            pgwriter.writeEdge(edge);

            rdf_str += p.getURI() + " ";

        } else {
            //the object is a literal 
            PGNode tnode = new PGNode(oid++);
            tnode.addLabel("Literal");
            // rdfid: value^^type (ign String)
            if (o.getLiteral().getDatatypeURI().equals(org.apache.jena.datatypes.xsd.XSDDatatype.XSDstring.getURI())) {
                tnode.addProperty("rdfid", o.getLiteral().getLexicalForm());
            } else {
                tnode.addProperty("rdfid", o.getLiteral().getLexicalForm() + "^^" + o.getLiteral().getDatatypeURI());
            }
            pgwriter.writeNode(tnode);
            
            PGEdge edge = new PGEdge(oid++,snode.getId(),tnode.getId());
            edge.addLabel("DatatypeProperty");
            edge.addProperty("rdfid", p.getURI());
            pgwriter.writeEdge(edge);

            // rdf_str += o.getLiteral().getLexicalForm() + "^^" + o.getLiteral().getDatatypeURI() + " .";
            rdf_str += "<" + p.getURI() + "> \"" + o.getLiteral().getLexicalForm() + "\" .";
        }
    }

    @Override
    public void quad(Quad quad) {
        //System.out.println("quad");
    }

    @Override
    public void base(String string) {
        //System.out.println("base");
    }

    @Override
    public void prefix(String string, String string1) {
        // System.out.println("prefix");
    }


    @Override
    public void finish() {
        // System.out.println("Number of RDF triples processed: " + cnt);
        // TODO: COPY READER FOR BENCHMARK TO DISPLAY NUMBER OF TRIPLES
    }

}
