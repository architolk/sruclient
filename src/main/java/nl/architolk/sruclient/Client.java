package nl.architolk.sruclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

  private static final Logger LOG = LoggerFactory.getLogger(Client.class);

  private static final long START_RECORD = 1;
  private static final long MAX_RECORDS = 5000;
  private static final long LAST_RECORD = 999999999;

  public static void main(String[] args) {
    LOG.info("Start retrieving data from https://zoekservice.overheid.nl");

    if (args.length<1) {
      LOG.error("Usage: sruclient [dump|txt|xml|meta]");
    } else {

      try {
        SRURecords records = new SRURecords();

        long index = START_RECORD;
        while ((index<=LAST_RECORD) && SRUClient.retrieve(index,MAX_RECORDS,records)) {
          index+=MAX_RECORDS;
        };

        if (args[0].matches("dump")) { records.dump(); }
        if (args[0].matches("txt")) { records.generateCurlTXT(); }
        if (args[0].matches("xml")) { records.generateCurlXML(); }
        if (args[0].matches("dump")) { records.generateMetadata(); }

      } catch (Exception e) {
        LOG.error(e.getMessage());
      }
    }
  }
}
