package nl.architolk.sruclient;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import javax.xml.namespace.QName;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLEventReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

  private static final Logger LOG = LoggerFactory.getLogger(Client.class);

  private static final QName NUMBER_OF_RECORDS = new QName("http://docs.oasis-open.org/ns/search-ws/sruResponse","numberOfRecords");
  private static final QName NEXT_RECORD_POSITION = new QName("http://docs.oasis-open.org/ns/search-ws/sruResponse","nextRecordPosition");
  private static final QName TITLE = new QName("http://purl.org/dc/terms/","title");

  public static void main(String[] args) {
    LOG.info("Start retrieving data from https://zoekservice.overheid.nl");
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("https://zoekservice.overheid.nl/sru/Search?operation=searchRetrieve&version=2.0&startRecord=1&maximumRecords=2&x-connection=BWB&query=dcterms.modified%3E=1900-01-01"))
      .build();

    try {
      InputStream response = client.send(request, BodyHandlers.ofInputStream()).body();

      XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
      XMLEventReader reader = xmlInputFactory.createXMLEventReader(response);

      while (reader.hasNext()) {
        XMLEvent nextEvent = reader.nextEvent();
        if (nextEvent.isStartElement()) {
          StartElement startElement = nextEvent.asStartElement();
          if (startElement.getName().equals(NUMBER_OF_RECORDS)) {
            nextEvent = reader.nextEvent();
            LOG.info("Recordcount: {}",nextEvent.asCharacters().getData());
          }
          if (startElement.getName().equals(NEXT_RECORD_POSITION)) {
            nextEvent = reader.nextEvent();
            LOG.info("Next record position: {}",nextEvent.asCharacters().getData());
          }
          if (startElement.getName().equals(TITLE)) {
            nextEvent = reader.nextEvent();
            System.out.println(nextEvent.asCharacters().getData());
          }
        }
      }
    } catch (Exception e) {
      LOG.error("Error executing http request");
    }
  }
}
