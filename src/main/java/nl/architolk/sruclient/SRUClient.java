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
import javax.xml.stream.events.EndElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLEventReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SRUClient {

  private static final Logger LOG = LoggerFactory.getLogger(SRUClient.class);

  private static final QName NUMBER_OF_RECORDS = new QName("http://docs.oasis-open.org/ns/search-ws/sruResponse","numberOfRecords");
  private static final QName NEXT_RECORD_POSITION = new QName("http://docs.oasis-open.org/ns/search-ws/sruResponse","nextRecordPosition");
  private static final QName RECORD = new QName("http://docs.oasis-open.org/ns/search-ws/sruResponse","record");
  private static final QName TITLE = new QName("http://purl.org/dc/terms/","title");
  private static final QName IDENTIFIER = new QName("http://purl.org/dc/terms/","identifier");
  private static final QName TOESTAND = new QName("http://standaarden.overheid.nl/bwb/terms/","toestand");
  private static final QName TOESTANDLOCATIE = new QName("http://standaarden.overheid.nl/bwb/terms/","locatie_toestand");

  public static boolean retrieve(long index, long length, SRURecords records) throws Exception {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(SearchURL.create(index,length))
      .build();

    InputStream response = client.send(request, BodyHandlers.ofInputStream()).body();

    XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    XMLEventReader reader = xmlInputFactory.createXMLEventReader(response);

    boolean moreRecords = false;
    SRURecord currentRecord = null;
    boolean currentRecordActive = false;

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
          moreRecords = true;
        }
        if (startElement.getName().equals(RECORD)) {
          currentRecord = records.addRecord();
          currentRecordActive = true;
        }
        if (currentRecordActive) {
          if (startElement.getName().equals(TITLE)) {
            nextEvent = reader.nextEvent();
            currentRecord.setTitle(nextEvent.asCharacters().getData());
          }
          if (startElement.getName().equals(IDENTIFIER)) {
            nextEvent = reader.nextEvent();
            currentRecord.setIdentifier(nextEvent.asCharacters().getData());
          }
          if (startElement.getName().equals(TOESTAND)) {
            nextEvent = reader.nextEvent();
            currentRecord.setToestand(nextEvent.asCharacters().getData());
          }
          if (startElement.getName().equals(TOESTANDLOCATIE)) {
            nextEvent = reader.nextEvent();
            currentRecord.setToestandlocatie(nextEvent.asCharacters().getData());
          }
        }
      }
      if (nextEvent.isEndElement()) {
        EndElement endElement = nextEvent.asEndElement();
        if (endElement.getName().equals(RECORD)) {
          currentRecordActive = false;
        }
      }
    }

    return moreRecords;
  }
}
