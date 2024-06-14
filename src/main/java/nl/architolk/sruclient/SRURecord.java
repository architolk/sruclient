package nl.architolk.sruclient;

public class SRURecord {

  protected String title = "";
  protected String identifier = "";
  protected String toestandlocatie = "";
  protected String toestand = "";

  public void setTitle(String _title) {
    title = _title;
  }
  public void setIdentifier(String _identifier) {
    identifier = _identifier;
  }
  public void setToestandlocatie(String _toestandlocatie) {
    toestandlocatie = _toestandlocatie;
  }
  public void setToestand(String _toestand) {
    toestand = _toestand;
  }

  public void dump() {
    System.out.println(String.format("%s %s",identifier,title));
  }

  public void generateCurlXML() {
    System.out.println(String.format("curl %s -o %s.xml",toestandlocatie,identifier));
  }

  public void generateCurlTXT() {
    System.out.println(String.format("curl %s/txt -L -o %s.txt",toestand,identifier));
  }
}
