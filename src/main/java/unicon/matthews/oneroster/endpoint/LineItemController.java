package unicon.matthews.oneroster.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import unicon.matthews.entity.MongoClassMappingRepository;
import unicon.matthews.oneroster.Class;
import unicon.matthews.oneroster.exception.LineItemNotFoundException;
import unicon.matthews.oneroster.service.LineItemService;
import unicon.matthews.oneroster.service.repository.MongoLineItem;
import unicon.matthews.oneroster.service.repository.MongoUser;
import unicon.matthews.security.auth.JwtAuthenticationToken;
import unicon.matthews.security.model.UserContext;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

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
     * Returns all the line items for a tenant id and an organization id given.
     * @param token a JWT to get authenticated
     * @return Collection<MongoLineItem> the line items
     * @throws IllegalArgumentException
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<MongoLineItem> getUsers(JwtAuthenticationToken token) throws IllegalArgumentException {
        UserContext userContext = (UserContext) token.getPrincipal();
        return lineItemService.findAll(userContext.getTenantId(), userContext.getOrgId());
    }


}
