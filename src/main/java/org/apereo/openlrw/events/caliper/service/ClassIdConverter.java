package org.apereo.openlrw.events.caliper.service;


import org.apereo.openlrw.tenant.Tenant;
import org.apereo.openlrw.caliper.Event;

/**
 * @author ggilbert
 *
 */
public interface ClassIdConverter {
  String convert(Tenant tenant, Event event);
}
