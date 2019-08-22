package org.apereo.openlrw.risk.service;

import org.apache.commons.lang3.StringUtils;
import org.apereo.openlrw.caliper.exception.EventNotFoundException;
import org.apereo.openlrw.common.exception.BadRequestException;
import org.apereo.openlrw.oneroster.exception.OneRosterNotFoundException;
import org.apereo.openlrw.risk.MongoRisk;
import org.apereo.openlrw.risk.service.repository.MongoRiskRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */

@Service
public class RiskService {
    private static Logger logger = LoggerFactory.getLogger(RiskService.class);

    private MongoRiskRepository mongoRiskRepository;
    private final MongoOperations mongoOps;

    @Autowired
    public RiskService(MongoRiskRepository mongoUserRepository, MongoOperations mongoOperations) {
        this.mongoRiskRepository = mongoUserRepository;
        this.mongoOps = mongoOperations;
    }

    /**
     * Save a RiskScore into the database
     * check if the record already exists, if it does it will updates some fields
     *
     * @param tenantId
     * @param orgId
     * @param mongoRisk
     * @param check
     * @return
     */
    public MongoRisk save(final String tenantId, final String orgId, MongoRisk mongoRisk, boolean check) {
        if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId) || mongoRisk == null)
            throw new IllegalArgumentException();

        if (StringUtils.isBlank(mongoRisk.getUserSourcedId()) || StringUtils.isBlank(mongoRisk.getClassSourcedId()))
            throw new IllegalArgumentException();


        MongoRisk risk = null;
        Long offset = TimeUnit.MILLISECONDS.toSeconds(TimeZone.getDefault().getRawOffset());

        if (mongoRisk.getDateTime() == null)
            mongoRisk.setDateTime(Instant.now());

        if (check)
            risk = mongoRiskRepository.findTopByTenantIdAndOrgIdAndUserSourcedIdAndClassSourcedIdAndNameAndDateTime(
                    tenantId,
                    orgId,
                    mongoRisk.getUserSourcedId(),
                    mongoRisk.getClassSourcedId(),
                    mongoRisk.getName(),
                    mongoRisk.getDateTime()
            );

        if (risk != null){
            risk = new MongoRisk.Builder()
                    .withActive(true)
                    .withClassSourcedId(risk.getClassSourcedId())
                    .withScore(mongoRisk.getScore())
                    .withDateTime(mongoRisk.getDateTime())
                    .withTimeZoneOffset(offset)
                    .withSourcedId(risk.getSourcedId())
                    .withModelType(mongoRisk.getModelType())
                    .withUserSourcedId(risk.getUserSourcedId())
                    .withName(mongoRisk.getName())
                    .withVelocity(mongoRisk.getVelocity())
                    .withOrgId(orgId)
                    .withTenantId(tenantId)
                    .withMetadata(mongoRisk.getMetadata())
                    .build();
        } else {
            risk = new MongoRisk.Builder()
                    .withActive(true)
                    .withClassSourcedId(mongoRisk.getClassSourcedId())
                    .withScore(mongoRisk.getScore())
                    .withDateTime(mongoRisk.getDateTime())
                    .withTimeZoneOffset(offset)
                    .withSourcedId(UUID.randomUUID().toString().replace("-", ""))
                    .withModelType(mongoRisk.getModelType())
                    .withUserSourcedId(mongoRisk.getUserSourcedId())
                    .withName(mongoRisk.getName())
                    .withVelocity(mongoRisk.getVelocity())
                    .withOrgId(orgId)
                    .withTenantId(tenantId)
                    .withMetadata(mongoRisk.getMetadata())
                    .build();
        }

        return mongoRiskRepository.save(risk);
    }

    /**
     * Get RiskScore for a user and a class given
     *
     * @param tenantId
     * @param orgId
     * @param classId
     * @param date : format 'yyyy-mm-dd' or keyword 'latest'
     * @return Collection<MongoRisk>
     */
    public Collection<MongoRisk> getRisksForClass(final String tenantId, final String orgId, final String classId, final String date, final int limit){
        if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId) || StringUtils.isBlank(classId))
            throw new IllegalArgumentException();

        Collection<MongoRisk> mongoRisks;

        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "dateTime")); // Order by date: the most recent
        query.addCriteria(where("classSourcedId").is(classId).and("orgId").is(orgId).and("tenantId").is(tenantId));

        if (limit > 0)
            query.limit(limit);

        this.dayCriteria(query, date);
        mongoRisks = mongoOps.find(query, MongoRisk.class);

        if (!mongoRisks.isEmpty())
            return new ArrayList<>(mongoRisks);

        throw new OneRosterNotFoundException("Risks not found.");
    }


    /**
     * Get RiskScore for a user and a class given
     *
     * @param tenantId
     * @param orgId
     * @param classId
     * @param userId
     * @param date : format 'yyyy-mm-dd' or keyword 'latest'
     * @return Collection<MongoRisk>
     */
    public Collection<MongoRisk> getRisksForUserAndClass(
            final String tenantId, final String orgId, final String classId,
            final String userId, final String date, final int limit
    ){
        if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId) || StringUtils.isBlank(userId) || StringUtils.isBlank(classId))
            throw new IllegalArgumentException();

        Collection<MongoRisk> mongoRisks;

        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "dateTime")); // Order by date: the most recent
        query.addCriteria(where("userSourcedId").is(userId).and("classSourcedId").is(classId).and("orgId").is(orgId).and("tenantId").is(tenantId));

        if (limit > 0)
            query.limit(limit);

        this.dayCriteria(query, date);

        mongoRisks = mongoOps.find(query, MongoRisk.class);

        if (!mongoRisks.isEmpty())
            return new ArrayList<>(mongoRisks);

        throw new OneRosterNotFoundException("Risks not found.");
    }


    /**
     * Add a criteria to get the Risks for the given day
     *
     * @param query
     * @param date yyyy-MM-dd or latest
     */
    private void dayCriteria(Query query, String date){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        if (!date.isEmpty()) {
            if (date.equals("latest")){
                query.limit(1);
            } else {
                try {
                    DateTime startDate = formatter.parseDateTime(date).withZone(DateTimeZone.UTC);
                    DateTime endDate = startDate.plusDays(1);
                    query.addCriteria(where("dateTime").gte(startDate).lt(endDate)); // Get the risks for the day given
                } catch (Exception e) {
                    throw new BadRequestException("Not able to parse the date, it has to be in the following format: `yyyy-MM-dd` ");
                }
            }
        }
    }
}


