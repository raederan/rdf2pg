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


package writers; // Include for writer integration

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import pgraph.PGEdge;
import pgraph.PGNode;
import pgraph.PGProperty;
// import writers.PGWriter; // Extend for writer integration


/**
 * @author Andreas Raeder
 */
public class RapsqlSplitWriter implements PGWriter{
    Writer nres_writer;
    Writer nlit_writer;
    Writer nbn_writer;
    Writer eop_writer;
    Writer edtp_writer;
    Writer eop_part_writer;
    Writer edtp_part_writer;
    String filename_nres;
    String filename_nlit;
    String filename_nbn;
    String filename_eop;
    String filename_edtp;
    String filename_eop_part;
    String filename_edtp_part;
    HashMap<Integer,Integer> oidmap = new HashMap<Integer,Integer>();
    int oid = 1;
    HashMap<Integer, PGNode> nodemap = new HashMap<Integer, PGNode>();
    // define list of unique edge iri's
    ArrayList<String> edge_iri_list = new ArrayList<String>();

    public RapsqlSplitWriter(
        String _filename_nres,
        String _filename_nlit,
        String _filename_nbn,
        String _filename_eop,
        String _filename_edtp,
        String _filename_eop_part,
        String _filename_edtp_part
    ) {
        this.filename_nres = _filename_nres;
        this.filename_nlit = _filename_nlit;
        this.filename_nbn = _filename_nbn;
        this.filename_eop = _filename_eop;
        this.filename_edtp = _filename_edtp;
        this.filename_eop_part = _filename_eop_part;
        this.filename_edtp_part = _filename_edtp_part;
    }

    @Override
    public void begin() {
        try {
            nres_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename_nres), "UTF-8"));
            // Resource header
            this.writeLine("id,iri\n", "Resource");
        } catch (Exception ex) {
            System.out.println("Error1: " + ex.getMessage());
        }
        try {
            nlit_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename_nlit), "UTF-8"));
            // Literal header
            this.writeLine("id,value,type\n", "Literal");
        } catch (Exception ex) {
            System.out.println("Error1: " + ex.getMessage());
        }
        try {
            nbn_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename_nbn), "UTF-8"));
            // BlankNode header
            this.writeLine("id,bnid\n", "BlankNode");
        } catch (Exception ex) {
            System.out.println("Error1: " + ex.getMessage());
        }
        try {
            eop_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename_eop), "UTF-8"));
            // ObjectProperty header
            this.writeLine("start_id,start_vertex_type,end_id,end_vertex_type,iri\n", "ObjectProperty");
        } catch (Exception ex) {
            System.out.println("Error1: " + ex.getMessage());
        }
        try {
            edtp_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename_edtp), "UTF-8"));
            // DatatypeProperty header
            this.writeLine("start_id,start_vertex_type,end_id,end_vertex_type,iri\n", "DatatypeProperty");
        } catch (Exception ex) {
            System.out.println("Error1: " + ex.getMessage());
        }
        try {
            eop_part_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename_eop_part), "UTF-8"));
            // ObjectProperty header
        } catch (Exception ex) {
            System.out.println("Error1: " + ex.getMessage());
        }
        try {
            edtp_part_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename_edtp_part), "UTF-8"));
            // DatatypeProperty header
        } catch (Exception ex) {
            System.out.println("Error1: " + ex.getMessage());
        }
    }
    // private void writeLine(String line, Boolean isNode) {
    private void writeLine(String line, String selector) {
        try {
            // if (isNode) {
            if (selector.equals("Resource")) {
                nres_writer.write(line);
            } else if (selector.equals("Literal")) {
                nlit_writer.write(line);
            } else if (selector.equals("BlankNode")) {
                nbn_writer.write(line);
            } else if (selector.equals("ObjectProperty")) {
                eop_writer.write(line);
            } else if (selector.equals("DatatypeProperty")) {
                edtp_writer.write(line);
            } else if (selector.equals("ObjectPropertyPart")) {
                eop_part_writer.write(line);
            } else if (selector.equals("DatatypePropertyPart")) {
                edtp_part_writer.write(line);
            }
        } catch (Exception ex) {
            System.out.println("Error2: " + ex.getMessage());
        }

    }

    @Override
    public void writeNode(PGNode node) {
        
        Integer node_id = oidmap.get(node.getId());
        if(node_id == null){
            node_id = oid;
            oidmap.put(node.getId(), oid);
            nodemap.put(node.getId(), node);
            oid++;
        }
        
        String labels = "";
        Iterator<String> it1 = node.getLabels();
        while (it1.hasNext()) {
            String label = it1.next();
            labels = label + labels + ",id";
        }

        int cnt = 0;
        String props = "";
        Iterator<PGProperty> it2 = node.getProperties();
        while (it2.hasNext()) {
            PGProperty prop = it2.next();
            cnt++;
            // ensure values with commas are quoted
            if (prop.getValue().contains(",")) {
                prop.setValue("\"" + prop.getValue() + "\"");
            }
            if (cnt < node.propertiesCounter()) {
                props = props + prop.getValue() + ",";
            } else {
                props = props + prop.getValue();
            }
        }

        String line;
        if(props.compareTo("")==0){
            line =  node.getId() + "," + "\n";
        }else{
            line =  node.getId() + "," + props + "\n";
        }
        this.writeLine(line, node.getLabel());
    }

    @Override
    public void writeEdge(PGEdge edge) {
        Integer edge_id = oidmap.get(edge.getId());
        if(edge_id == null){
            edge_id = oid;
            oidmap.put(edge.getId(), oid);
            oid++;
        }
        
        String labels = "";
        Iterator<String> it1 = edge.getLabels();
        while (it1.hasNext()) {
            String label = it1.next();
            labels = labels + "" + label;
        }

        int cnt = 0;
        String props = "";
        Iterator<PGProperty> it2 = edge.getProperties();
        while (it2.hasNext()) {
            PGProperty prop = it2.next();
            cnt++;
            if (cnt < edge.propertiesCounter()) {
                props = props + prop.getValue() + ",";
            } else {
                props = props + prop.getValue();
            }
        }

        Integer snode_oid = oidmap.get(edge.getSourceNode());
        if(snode_oid == null){
            snode_oid = oid;
            oidmap.put(edge.getSourceNode(),oid);
            oid++;
        }
        Integer tnode_oid = oidmap.get(edge.getTargetNode());
        if(tnode_oid == null){
            tnode_oid = oid;
            oidmap.put(edge.getTargetNode(), oid);
            oid++;
        }
        
        snode_oid = edge.getSourceNode();
        tnode_oid = edge.getTargetNode();
        
        PGNode snode = nodemap.get(snode_oid);
        PGNode tnode = nodemap.get(tnode_oid);
        
        String line;
        if (edge.propertiesCounter() == 0) {
            line = snode_oid + ","
                    + snode.getLabel() + ","
                    + tnode_oid + ","
                    + tnode.getLabel() + ","
                    + labels + "\n";
        } else {
            line = snode_oid + ","
                    + snode.getLabel() + ","
                    + tnode_oid + ","
                    + tnode.getLabel() + ","
                    + props + "\n";
        }

        // add unique edge iri's to list and write to file
        if (!edge_iri_list.contains(props)) {
            edge_iri_list.add(props);
            this.writeLine(props + "\n", labels + "Part");
            // System.out.println("Edge IRI: " + props);
            // System.out.println("Edge Label: " + labels);
        }
        this.writeLine(line, labels);
    }

    @Override
    public void end() {
        try {
            nres_writer.close();
            nlit_writer.close();
            nbn_writer.close();
            eop_writer.close();
            edtp_writer.close();
            eop_part_writer.close();
            edtp_part_writer.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}

