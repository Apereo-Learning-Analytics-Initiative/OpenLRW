package org.apereo.openlrw.oneroster.service;

import org.apache.commons.lang3.StringUtils;
import org.apereo.model.oneroster.LineItem;
import org.apereo.openlrw.oneroster.exception.LineItemNotFoundException;
import org.apereo.openlrw.oneroster.service.repository.MongoLineItem;
import org.apereo.openlrw.oneroster.service.repository.MongoLineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.sun.tools.sjavac.Log;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 *
 */
@Service
public class LineItemService {
  
  private MongoLineItemRepository mongoLineItemRepository;
  
  @Autowired
  public LineItemService(MongoLineItemRepository mongoLineItemRepository) {
    this.mongoLineItemRepository = mongoLineItemRepository;
  }
  
  public LineItem save(final String tenantId, final String orgId, LineItem lineItem, boolean check) {
    if (StringUtils.isBlank(orgId) || lineItem == null)
      throw new IllegalArgumentException();

    MongoLineItem toSave, existingMongoLineItem = null;

    if (check)
      existingMongoLineItem = mongoLineItemRepository.findByTenantIdAndOrgIdAndLineItemSourcedId(tenantId,orgId,lineItem.getSourcedId());

    if (existingMongoLineItem == null) {
      toSave = new MongoLineItem.Builder()
            .withClassSourcedId(lineItem.getKlass().getSourcedId())
            .withLineItem(lineItem)
            .withOrgId(orgId)
            .withTenantId(tenantId)
            .build();
    } else {
      toSave = new MongoLineItem.Builder()
            .withId(existingMongoLineItem.getId())
            .withClassSourcedId(lineItem.getKlass().getSourcedId())
            .withLineItem(lineItem)
            .withOrgId(existingMongoLineItem.getOrgId())
            .withTenantId(existingMongoLineItem.getTenantId())
            .build();
    }

    MongoLineItem saved = mongoLineItemRepository.save(toSave);

    return saved.getLineItem();
  }
  
  public Collection<LineItem> getLineItemsForClass(final String tenantId, final String orgId, final String classSourcedId) throws LineItemNotFoundException {
    Collection<MongoLineItem> mongoLineItems = mongoLineItemRepository.findByOrgIdAndClassSourcedId(orgId, classSourcedId);
    
    System.out.println("mongoLineItems: " + mongoLineItems);
    if (mongoLineItems != null && !mongoLineItems.isEmpty()) {
      
      Collection<LineItem> t = mongoLineItems.stream().map(MongoLineItem::getLineItem).collect(Collectors.toList());
      System.out.println("LineItems: " + t);
      return t;
    }
    throw new LineItemNotFoundException("Line item not found");
  }

  public MongoLineItem findById(final String tenantId, final String orgId, final String lineItemId) throws LineItemNotFoundException {
    MongoLineItem mongoLineItem = mongoLineItemRepository.findByTenantIdAndOrgIdAndLineItemSourcedId(tenantId, orgId, lineItemId);
    return mongoLineItem;
  }

  public Collection<MongoLineItem> findAll(final String tenantId, final String orgId) throws IllegalArgumentException {
    if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId))
      throw new IllegalArgumentException();

    return mongoLineItemRepository.findByTenantIdAndOrgId(tenantId, orgId);
  }


}
