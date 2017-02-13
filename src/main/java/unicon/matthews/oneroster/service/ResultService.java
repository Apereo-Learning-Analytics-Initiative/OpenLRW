package unicon.matthews.oneroster.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.oneroster.Result;
import unicon.matthews.oneroster.service.repository.MongoResult;
import unicon.matthews.oneroster.service.repository.MongoResultRepository;

@Service
public class ResultService {
  
  private MongoResultRepository mongoResultRepository;
  
  @Autowired
  public ResultService(MongoResultRepository mongoResultRepository) {
    this.mongoResultRepository = mongoResultRepository;
  }
  
  public Result save(final String tenantId, final String orgId, final String classSourcedId, Result result) {
    if (StringUtils.isBlank(orgId) || result == null) {
      throw new IllegalArgumentException();
    }
    
    MongoResult existingMongoResult 
      = mongoResultRepository.findByTenantIdAndOrgIdAndResultSourcedId(tenantId,orgId,result.getSourcedId());
    MongoResult toSave = null;
    
    if (existingMongoResult == null) {
      toSave =
          new MongoResult.Builder()
            .withClassSourcedId(classSourcedId)
            .withLineitemSourcedId(result.getLineitem().getSourcedId())
            .withOrgId(orgId)
            .withResult(result)
            .withTenantId(tenantId)
            .withUserSourcedId(result.getStudent().getSourcedId())
            .build();
    }
    else {
      toSave =
          new MongoResult.Builder()
            .withId(existingMongoResult.getId())
            .withLineitemSourcedId(result.getLineitem().getSourcedId())
            .withOrgId(orgId)
            .withResult(result)
            .withTenantId(tenantId)
            .withUserSourcedId(result.getStudent().getSourcedId())
            .build();
    }
    
    MongoResult saved = mongoResultRepository.save(toSave);
    return saved.getResult();
  }
  
  public Collection<Result> getResultsForClass(final String tenantId, final String orgId, final String classSourcedId) {
    Collection<MongoResult> mongoResults = mongoResultRepository.findByTenantIdAndOrgIdAndClassSourcedId(tenantId, orgId, classSourcedId);
    if (mongoResults != null && !mongoResults.isEmpty()) {
      return mongoResults.stream().map(MongoResult::getResult).collect(Collectors.toList());
    }
    
    return null;//TODO throw exception
  }

}
