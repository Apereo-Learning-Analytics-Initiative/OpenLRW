/**
 * 
 */
package unicon.matthews.caliper.service;

import unicon.matthews.caliper.Event;
import unicon.matthews.tenant.Tenant;

/**
 * @author ggilbert
 *
 */
public interface ClassIdConverter {
  String convert(Tenant tenant, Event event);
}
