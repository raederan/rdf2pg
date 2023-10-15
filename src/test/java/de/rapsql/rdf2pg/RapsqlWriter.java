package de.rapsql.rdf2pg;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import de.rapsql.rdf2pg.pgraph.PGEdge;
import de.rapsql.rdf2pg.pgraph.PGNode;
import de.rapsql.rdf2pg.pgraph.PGProperty;
import de.rapsql.rdf2pg.writers.PGWriter;

public class RapsqlWriter implements PGWriter {
  Writer writer;
  String filename = "output.ypg";
  HashMap<Integer, Integer> oidmap = new HashMap<Integer, Integer>();
  int oid = 1;
  HashMap<Integer, PGNode> nodemap = new HashMap<Integer, PGNode>();


  public RapsqlWriter(String _filename) {
    this.filename = _filename;
  }

  private void writeLine(String line) {
    try {
      writer.write(line);
    } catch (Exception ex) {
      System.out.println("Error2: " + ex.getMessage());
    }
  }

  private String getNodeProperties (PGNode node, Integer oid){
    int cnt = 0;
    String props = "";
    Iterator<PGProperty> it = node.getProperties();
    while (it.hasNext()) {
      PGProperty prop = it.next();
      cnt++;
      if (cnt < node.propertiesCounter()) {
        props = props + "n" + oid + "." + prop.getLabel() + "='" + prop.getValue() + "'" + " AND ";
      } else {
        props = props + "n" + oid + "." + prop.getLabel() + "='" + prop.getValue() + "'";
      }
    }
    return props;
  }

  @Override
  public void begin() {
    try {
      writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
    } catch (Exception ex) {
      System.out.println("Error1: " + ex.getMessage());
    }
  }


  @Override
  public void writeNode(PGNode node) {
    // System.out.println("NODE:" + node);
    // System.out.println("NODE ID:" + node.getId());
    // System.out.println("NODE LABELS:" + node.getLabels());
    // System.out.println("NODE PROPERTIES:" + node.getProperties());
    Integer node_id = oidmap.get(node.getId());
    // System.out.println("NODE_ID:" + node_id);
    if(node_id == null){
      node_id = oid;
      oidmap.put(node.getId(), oid);
      nodemap.put(node.getId(), node);
      oid++;
    }
    // System.out.println("NODEMAP:" + nodemap);

    String labels = "";
    Iterator<String> it1 = node.getLabels();
    while (it1.hasNext()) {
      String label = it1.next();
      labels = labels + ":" + label;
    }

    int cnt = 0;
    String props = "";
    Iterator<PGProperty> it2 = node.getProperties();
    while (it2.hasNext()) {
      PGProperty prop = it2.next();
      cnt++;
      if (cnt < node.propertiesCounter()) {
        props = props + prop.getLabel() + ":'" + prop.getValue() + "'" + ",";
      } else {
        props = props + prop.getLabel() + ":'" + prop.getValue() + "'";
      }
    }

    String line;
    if(props.compareTo("")==0) {
      // line = "CREATE (n" + labels + ")\n";
      line = "CREATE (n" +  node.getId() + labels + ")\n";
    } else {
      // line = "CREATE (n" + labels +  " {" + props + "} )\n";
      line = "CREATE (n" +  node.getId() + labels +  " {" + props + "} )\n";
    }
    this.writeLine(line);
    // System.out.println("\n" + line);
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
      labels = labels + ":" + label;
    }

    int cnt = 0;
    String props = "";
    Iterator<PGProperty> it2 = edge.getProperties();
    while (it2.hasNext()) {
      PGProperty prop = it2.next();
      cnt++;
      if (cnt < edge.propertiesCounter()) {
        props = props + prop.getLabel() + ":\"" + prop.getValue() + "\"" + ",";
      } else {
        props = props + prop.getLabel() + ":\"" + prop.getValue() + "\"";
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

    // switch case for snode.propertiesCounter() and tnode.propertiesCounter()
    // if both are 0, then no need to use WHERE clause
    // if one of them is 0, then use WHERE clause for the other one
    // if both are not 0, then use WHERE clause for both of them
    Boolean snode_props = snode.emptyProperties();
    Boolean tnode_props = tnode.emptyProperties();
    String where_clause = "";

    if (!snode_props && !tnode_props) {
        where_clause = "WHERE " + getNodeProperties(snode, snode_oid) + " AND " + getNodeProperties(tnode, tnode_oid) + " ";
    } else if (!snode_props && tnode_props) {
        where_clause = "WHERE " + getNodeProperties(snode, snode_oid) + " ";
    } else if (!tnode_props && snode_props) {
        where_clause = "WHERE " + getNodeProperties(tnode, tnode_oid) + " ";
    } else {
        where_clause = "";
    }


    // CREATE (a)-[r:RELTYPE]->(b)
    String line;
    if (edge.propertiesCounter() == 0) {
      line =
            "MATCH (n" + snode_oid + ":" + snode.getLabel() + "), (n" + tnode_oid + ":" + tnode.getLabel() + ") "
            + where_clause 
            + "CREATE (n" + snode_oid
            + ")-[e" + labels + "]->(n" + tnode_oid
            + ")" 
            + "\n";
    } else {
      line =
            "MATCH (n" + snode_oid + ":" + snode.getLabel() + "), (n" + tnode_oid + ":" + tnode.getLabel() + ") " 
            + where_clause
            + "CREATE (n" + snode_oid
            + ")-[e" + labels 
            + " {n" + props + "}]->(n" + tnode_oid
            + ")" 
            + "\n";
    }
    this.writeLine(line);
    // System.out.println(line);
  }

  @Override
  public void end() {
    try {
      // System.out.println(oidmap);
      writer.close();
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }
}

// ```sql
// select * from cypher('test', $$                                      
// MATCH (a:Resource), (b:Literal)
// WHERE a.iri = 'http://example.org/book/book1' AND b.value = 'SPARQL Tutorial' CREATE (a)-[e:DatatypeProperty {ntype: 'http://purl.org/dc/elements/1.1/title'}]->(b) RETURN b $$) as (b agtype);
// ```