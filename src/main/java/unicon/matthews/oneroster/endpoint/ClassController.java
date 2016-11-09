/**
 * 
 */
package unicon.matthews.oneroster.endpoint;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import unicon.matthews.oneroster.LineItem;
import unicon.matthews.oneroster.exception.LineItemNotFoundException;
import unicon.matthews.oneroster.service.LineItemService;
import unicon.matthews.security.auth.JwtAuthenticationToken;
import unicon.matthews.security.model.UserContext;

/**
 * @author ggilbert
 *
 */
@RestController
@RequestMapping("/api/classes")
public class ClassController {
  
  private LineItemService lineItemService;
  
  @Autowired
  public ClassController(LineItemService lineItemService) {
    this.lineItemService = lineItemService;
  }

  @RequestMapping(value = "/{classId}/lineitems", method = RequestMethod.GET)
  public Collection<LineItem> getLineItemsForClass(JwtAuthenticationToken token, @PathVariable final String classId) throws LineItemNotFoundException {
    UserContext userContext = (UserContext) token.getPrincipal();
    return lineItemService.getLineItemsForClass(userContext.getTenantId(), userContext.getOrgId(), classId);
  }
  
  @RequestMapping(value= "/{classId}/lineitems", method = RequestMethod.POST)
  public ResponseEntity<?> postLineItem(JwtAuthenticationToken token, @RequestBody LineItem lineItem) {
    UserContext userContext = (UserContext) token.getPrincipal();
    LineItem savedLineItem = this.lineItemService.save(userContext.getTenantId(), userContext.getOrgId(), lineItem);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedLineItem.getSourcedId()).toUri());
    return new ResponseEntity<>(savedLineItem, httpHeaders, HttpStatus.CREATED);
  }

}
