package org.apereo.openlrw.oneroster.endpoint;

import org.apereo.model.oneroster.LineItem;
import org.apereo.openlrw.entity.MongoClassMappingRepository;
import org.apereo.openlrw.oneroster.service.LineItemService;
import org.apereo.openlrw.oneroster.service.repository.MongoLineItem;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RestController
@RequestMapping("/api/lineitems")
public class LineItemController {

    private LineItemService lineItemService;
    private MongoClassMappingRepository mongoClassMappingRepository;

    @Autowired
    public LineItemController(LineItemService lineItemService, MongoClassMappingRepository mongoClassMappingRepository) {
        this.lineItemService = lineItemService;
        this.mongoClassMappingRepository = mongoClassMappingRepository;
    }

    /**
     * GET /api/lineitems
     *
     * Return all the line items for a tenant id and an organization id given.
     * @param token a JWT to get authenticated
     * @return Collection<MongoLineItem> the line items
     * @throws IllegalArgumentException
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<MongoLineItem> getUsers(JwtAuthenticationToken token) throws IllegalArgumentException {
        UserContext userContext = (UserContext) token.getPrincipal();
        return lineItemService.findAll(userContext.getTenantId(), userContext.getOrgId());
    }


    /**
     * POST /api/lineitems
     *
     * Create a line item
     * @param token a JWT to get authenticated
     * @param lineItem the object to build
     * @return LineItem the object created
     * @throws IllegalArgumentException
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postLineItem(JwtAuthenticationToken token, @RequestBody LineItem lineItem, @RequestParam(value="check", required=false) Boolean check) throws IllegalArgumentException {
        UserContext userContext = (UserContext) token.getPrincipal();
        LineItem savedLineItem = this.lineItemService.save(userContext.getTenantId(), userContext.getOrgId(), lineItem, (check == null) ? true : check);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedLineItem.getSourcedId()).toUri());
        return new ResponseEntity<>(savedLineItem, httpHeaders, HttpStatus.CREATED);
    }

}
