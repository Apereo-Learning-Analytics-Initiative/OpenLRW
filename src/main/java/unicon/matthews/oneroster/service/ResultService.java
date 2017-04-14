package unicon.matthews.oneroster.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.oneroster.Result;
import unicon.matthews.oneroster.exception.ResultNotFoundException;
import unicon.matthews.oneroster.service.repository.MongoResult;
import unicon.matthews.oneroster.service.repository.MongoResultRepository;

/**
 * @author stalele
 *
 */
/**
 * @author stalele
 *
 */
/**
 * @author stalele
 *
 */
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
            .withClassSourcedId(classSourcedId)
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
  
  public Collection<Result> getResultsForClass(final String tenantId, final String orgId, final String classSourcedId) throws ResultNotFoundException {
    Collection<MongoResult> mongoResults = mongoResultRepository.findByTenantIdAndOrgIdAndClassSourcedId(tenantId, orgId, classSourcedId);
    if (mongoResults != null && !mongoResults.isEmpty()) {
      return mongoResults.stream().map(MongoResult::getResult).collect(Collectors.toList());
    }
    
    throw new ResultNotFoundException(String.format("Result not found for %s", classSourcedId));
  }

  /** Returns the result for lineitem
   * @param tenantId
   * @param orgId
   * @param lineItemSourcedId
   * @return Result
   * @throws ResultNotFoundException
   */
  public Result getResultsForlineItem(final String tenantId, final String orgId, final String lineItemSourcedId) throws ResultNotFoundException{
	  MongoResult mongoResult = mongoResultRepository.findByTenantIdAndOrgIdAndLineitemSourcedId(tenantId, orgId, lineItemSourcedId);
	  return getResult(lineItemSourcedId, mongoResult);
  }

  private Result getResult(final String parameter, MongoResult mongoResult) throws ResultNotFoundException {
	  Result result = Optional.ofNullable(mongoResult)
			  .map(MongoResult::getResult)
			  .orElse(null);
	  if(result == null) {
		  throw new ResultNotFoundException(String.format("Result not found for %s", parameter));
	  }
	  return result;
  }

  /** Returns the result for user
   * @param tenantId
   * @param orgId
   * @param userSourcedId
   * @return Result
   * @throws ResultNotFoundException
   */
  public Result getResultsForUser(final String tenantId, final String orgId, final String userSourcedId) throws ResultNotFoundException {
	  MongoResult mongoResult = mongoResultRepository.findByTenantIdAndOrgIdAndUserSourcedId(tenantId, orgId, userSourcedId);
	  return getResult(userSourcedId, mongoResult);
  }

}
