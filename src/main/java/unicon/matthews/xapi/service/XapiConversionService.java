package unicon.matthews.xapi.service;

import java.net.URISyntaxException;

import unicon.matthews.caliper.Event;
import unicon.matthews.xapi.Statement;

public interface XapiConversionService {

  Event fromXapi(Statement statement);

  Statement toXapi(Event event) throws URISyntaxException;

}