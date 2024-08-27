package nl.architolk.sruclient;

import java.util.List;
import java.util.ArrayList;

public class SRURecords {

  private List<SRURecord> records = new ArrayList<SRURecord>();

  public SRURecord addRecord() {
    SRURecord record = new SRURecord();
    records.add(record);
    return record;
  }

  public void dump() {
    for (SRURecord record : records) {
      record.dump();
    }
  }

  public void generateCurlXML() {
    for (SRURecord record : records) {
      record.generateCurlXML();
    }
  }

  public void generateCurlTXT() {
    for (SRURecord record : records) {
      record.generateCurlTXT();
    }
  }

  public void generateMetadata() {
    System.out.println("<toestanden>");
    for (SRURecord record : records) {
      record.generateMetadata();
    }
    System.out.println("</toestanden>");
  }

}
