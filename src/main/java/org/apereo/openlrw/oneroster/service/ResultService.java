package org.apereo.openlrw.oneroster.service;

import org.apache.commons.lang3.StringUtils;
import org.apereo.model.oneroster.Result;
import org.apereo.openlrw.oneroster.exception.ResultNotFoundException;
import org.apereo.openlrw.oneroster.service.repository.MongoClass;
import org.apereo.openlrw.oneroster.service.repository.MongoResult;
import org.apereo.openlrw.oneroster.service.repository.MongoResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author stalele
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */

@Service
public class ResultService {

  private static Logger logger = LoggerFactory.getLogger(ResultService.class);
  private MongoResultRepository mongoResultRepository;
  private final MongoOperations mongoOps;
  
  @Autowired
  public ResultService(MongoResultRepository mongoResultRepository, MongoOperations mongoOperations) {
    this.mongoResultRepository = mongoResultRepository;
    this.mongoOps = mongoOperations;
  }


  /**
   * Insert a Result into the database
   *
   * @param tenantId
   * @param orgId
   * @param classSourcedId
   * @param result
   * @param check - if true, it patches the result if it already exists
   * @return
   */
  public Result save(final String tenantId, final String orgId, final String classSourcedId, Result result, boolean check) {
    if (StringUtils.isBlank(orgId) || result == null)
      throw new IllegalArgumentException();
    
    MongoResult toSave, existingMongoResult = null;

    if (check) {
      Query query = new Query();
      query.addCriteria(where("tenantId").is(tenantId).and("orgId").is(orgId).and("resultSourcedId").is(result.getSourcedId()));
      existingMongoResult = mongoOps.findOne(query, MongoResult.class);
    }


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

  /** Return the results for lineitem
   * @param tenantId
   * @param orgId
   * @param lineItemSourcedId
   * @return Result
   * @throws ResultNotFoundException
   */
  public Collection<Result> getResultsForlineItem(final String tenantId, final String orgId, final String lineItemSourcedId) throws ResultNotFoundException{

      Collection<MongoResult> mongoResults = mongoResultRepository.findByTenantIdAndOrgIdAndLineitemSourcedId(tenantId, orgId, lineItemSourcedId);
      if (mongoResults == null || mongoResults.isEmpty())
        throw new ResultNotFoundException("Results not found");
	  return mongoResults.stream().map(MongoResult::getResult).collect(Collectors.toList());
  }

  private Result getResult(final String parameter, MongoResult mongoResult) {
	  Result result = Optional.ofNullable(mongoResult).map(MongoResult::getResult).orElse(null);
	  if (result == null)
	    throw new ResultNotFoundException("Result not found for " + parameter);

	  return result;
  }

  /**
   * Get all the results (pageable)
   *
   * @param tenantId
   * @param orgId
   * @param page
   * @param limit
   * @return List<Result>
   * @throws Exception
   */
  public List<Result> findAll(final String tenantId, final String orgId, String page, String limit) throws Exception {
    try {
      PageRequest pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.unsorted());
      List<MongoResult> mongoResults = mongoResultRepository.findTopByTenantIdAndOrgIdOrderByResultDateDesc(tenantId, orgId, pageRequest);
      if (mongoResults != null && !mongoResults.isEmpty()) {
        return mongoResults.stream().map(MongoResult::getResult).collect(Collectors.toList());
      }
      return null;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }


  /**
   * Get the Results for a User
   *
   * @param tenantId
   * @param orgId
   * @param userSourcedId
   * @return
   * @throws ResultNotFoundException
   */
  public Collection<Result> getResultsForUser(final String tenantId, final String orgId, final String userSourcedId) throws ResultNotFoundException {
    Query query = new Query();

    query.addCriteria(where("userSourcedId").is(userSourcedId).and("orgId").is(orgId).and("tenantId").is(tenantId));

    Collection<MongoResult> mongoResults = mongoOps.find(query, MongoResult.class);

    if (!mongoResults.isEmpty())
      return mongoResults.stream().map(MongoResult::getResult).collect(Collectors.toList());

    throw new ResultNotFoundException(String.format("Result not found for %s", userSourcedId));
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

    if (!mongoResults.isEmpty())
      return mongoResults.stream().map(MongoResult::getResult).collect(Collectors.toList());

    throw new ResultNotFoundException(String.format("Result not found for %s", classId));
  }

}
