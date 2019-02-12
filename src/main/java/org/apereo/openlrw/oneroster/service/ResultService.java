package org.apereo.openlrw.oneroster.service;

import org.apache.commons.lang3.StringUtils;
import org.apereo.model.oneroster.Result;
import org.apereo.openlrw.oneroster.exception.ResultNotFoundException;
import org.apereo.openlrw.oneroster.service.repository.MongoResult;
import org.apereo.openlrw.oneroster.service.repository.MongoResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author stalele
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */

@Service
public class ResultService {
  
  private MongoResultRepository mongoResultRepository;
  private final MongoOperations mongoOps;
  
  @Autowired
  public ResultService(MongoResultRepository mongoResultRepository, MongoOperations mongoOperations) {
    this.mongoResultRepository = mongoResultRepository;
    this.mongoOps = mongoOperations;
  }
  
  public Result save(final String tenantId, final String orgId, final String classSourcedId, Result result, boolean check) {
    if (StringUtils.isBlank(orgId) || result == null)
      throw new IllegalArgumentException();
    
    MongoResult toSave, existingMongoResult = null;

    if (check)
        existingMongoResult = mongoResultRepository.findByTenantIdAndOrgIdAndResultSourcedId(tenantId,orgId,result.getSourcedId());


    if (existingMongoResult == null) {
      toSave = new MongoResult.Builder()
            .withClassSourcedId(classSourcedId)
            .withLineitemSourcedId(result.getLineitem().getSourcedId())
            .withOrgId(orgId)
            .withResult(result)
            .withTenantId(tenantId)
            .withUserSourcedId(result.getStudent().getSourcedId())
            .build();
    } else {
      toSave = new MongoResult.Builder()
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

    if (mongoResults != null && !mongoResults.isEmpty())
      return mongoResults.stream().map(MongoResult::getResult).collect(Collectors.toList());

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

  private Result getResult(final String parameter, MongoResult mongoResult) {
	  Result result = Optional.ofNullable(mongoResult).map(MongoResult::getResult).orElse(null);
	  if (result == null)
	    throw new ResultNotFoundException("Result not found for " + parameter);

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

  /**
   * Get the results of a user for a class given
   * @param tenantId
   * @param orgId
   * @param classId
   * @param userId
   * @return
   * @throws ResultNotFoundException
   */
  public Collection<Result> getResultsForClassAndUser(final String tenantId, final String orgId, final String classId, final String userId) throws ResultNotFoundException {
    Query query = new Query();

    query.addCriteria(where("userSourcedId").is(userId)
            .and("classSourcedId").is(classId)
            .and("orgId").is(orgId)
            .and("tenantId").is(tenantId)
    );

    Collection<MongoResult> mongoResults = mongoOps.find(query, MongoResult.class);

    if (mongoResults != null && !mongoResults.isEmpty())
      return mongoResults.stream().map(MongoResult::getResult).collect(Collectors.toList());

    throw new ResultNotFoundException(String.format("Result not found for %s", classId));
  }

}
