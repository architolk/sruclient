package nl.architolk.sruclient;

import java.net.URI;
import java.util.Date;

public class SearchURL {

  private static final String SEARCH_LOCATION = "https://zoekservice.overheid.nl/sru/Search";
  private static final String SEARCH_PARAMS = "?operation=searchRetrieve&version=2.0";
  private static final String SEARCH_COLLECTION_PARAM = "&x-connection=";
  private static final String SEARCH_START_PARAM = "&startRecord=";
  private static final String SEARCH_MAX_PARAM = "&maximumRecords=";
  private static final String SEARCH_QUERY_PARAM = "&query=";
  private static final String SEARCH_FACET_VALID = "overheidbwb.geldigheidsdatum=";

  private static final String COLLECTION = "bwb";

  public static URI create(long start, long length) {
    Date currentDate = new Date();
    return URI.create(String.format("%s%s%s%s%s%s%s%s%s%s%tY-%11$tm-%11$td"
                                   ,SEARCH_LOCATION,SEARCH_PARAMS
                                   ,SEARCH_COLLECTION_PARAM,COLLECTION
                                   ,SEARCH_START_PARAM,start
                                   ,SEARCH_MAX_PARAM,length
                                   ,SEARCH_QUERY_PARAM,SEARCH_FACET_VALID,currentDate));
  }

}
