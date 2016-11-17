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
public interface UserIdConverter {
  String convert(Tenant tenant, Event event);
}
