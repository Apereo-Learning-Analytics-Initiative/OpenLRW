package org.apereo.openlrw.caliper.service;

import org.apereo.openlrw.tenant.Tenant;
import org.apereo.openlrw.caliper.v1p1.Event;


/**
 * @author ggilbert
 *
 */
public interface UserIdConverter {
  String convert(Tenant tenant, Event event);

}
