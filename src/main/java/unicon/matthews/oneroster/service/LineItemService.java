/**
 * 
 */
package unicon.matthews.oneroster.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.oneroster.LineItem;
import unicon.matthews.oneroster.exception.LineItemNotFoundException;
import unicon.matthews.oneroster.service.repository.MongoLineItem;
import unicon.matthews.oneroster.service.repository.MongoLineItemRepository;

/**
 * @author ggilbert
 *
 */
@Service
public class LineItemService {
  
  private MongoLineItemRepository mongoLineItemRepository;
  
  @Autowired
  public LineItemService(MongoLineItemRepository mongoLineItemRepository) {
    this.mongoLineItemRepository = mongoLineItemRepository;
  }
  
  public LineItem save(final String tenantId, final String orgId, LineItem lineItem) {
    if (StringUtils.isBlank(orgId) || lineItem == null) {
      throw new IllegalArgumentException();
    }
    
    MongoLineItem existingMongoLineItem 
      = mongoLineItemRepository.findByTenantIdAndOrgIdAndLineItemSourcedId(tenantId,orgId,lineItem.getSourcedId());
    MongoLineItem toSave = null;
    
    if (existingMongoLineItem == null) {
      toSave =
          new MongoLineItem.Builder()
            .withClassSourcedId(lineItem.getKlass().getSourcedId())
            .withLineItem(lineItem)
            .withOrgId(orgId)
            .withTenantId(tenantId)
            .build();
    }
    else {
      toSave =
          new MongoLineItem.Builder()
            .withId(existingMongoLineItem.getId())
            .withClassSourcedId(existingMongoLineItem.getClassSourcedId())
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
    if (mongoLineItems != null && !mongoLineItems.isEmpty()) {
      return mongoLineItems.stream().map(MongoLineItem::getLineItem).collect(Collectors.toList());
    }
    throw new LineItemNotFoundException();
  }

}
