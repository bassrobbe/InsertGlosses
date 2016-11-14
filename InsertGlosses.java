//InsertGlosses.java

import java.io.*;

class InsertGlosses {

  final String mmoonPath = "/path/to/mmoon.ttl/";
  final String glossPath = "/path/to/triple.txt/";
  String mmoon = "";

  private void run() {

    //read mmoon.ttl
    BufferedReader mmoonReader = null;
    try {
      mmoonReader = new BufferedReader(new FileReader(mmoonPath + "mmoon.ttl"));
      String current = null;
      while ((current = mmoonReader.readLine()) != null) {
        mmoon += current + "\n";
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (mmoonReader != null) try {
        mmoonReader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    //read new triple and insert into mmoon string
    BufferedReader glossReader = null;
    try {
      glossReader = new BufferedReader(new FileReader(glossPath + "triple.txt"));
      String current = null;
      while ((current = glossReader.readLine()) != null) {
        String[] currentSplit = current.split("\\t");
        insertTriple(currentSplit);
        insertIndividual(currentSplit);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (glossReader != null) try {
        glossReader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    //write mmoon to file
    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter( new FileWriter(mmoonPath + "mmoon_new.ttl"));
      writer.write(mmoon);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (writer != null) try {
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  private void insertTriple(String[] triple) {

    int idx_entry = mmoon.indexOf(":" + triple[0] + " a owl:Class");
    int idx_line = mmoon.indexOf(";", idx_entry) + 1;

    StringBuilder builder = new StringBuilder(mmoon);
    builder.insert(idx_line, "\n\t:hasAbstractIdentity :MorphemicGloss_" + triple[2] + " ;");
    mmoon = builder.toString();

  }

  private void insertIndividual(String[] triple) {

    mmoon += "\n\n:MorphemicGloss_" + triple[2] +
    " a owl:NamedIndividual , :MorphemicGloss ;\n\trdfs:comment \"" + triple[0] +
    " gloss.\"@en ;\n\trdfs:label \"" + triple[2] + "\"^^xsd:string .";

/*    int idx = 0;
    StringBuilder builder = new StringBuilder(mmoon);
    builder.insert(idx, "\n\n:MorphemicGloss_" + triple[2] +
    " a owl:NamedIndividual , :MorphemicGloss ;\n\t rdfs:comment \"" + triple[0] +
    " gloss.\"@en ;\n\trdfs:label \"" + triple[2] + "\"^^xsd:string .");
    mmoon = builder.toString();*/

  }

  public static void main(String[] args) {

    InsertGlosses main = new InsertGlosses();
    main.run();

  }
}
