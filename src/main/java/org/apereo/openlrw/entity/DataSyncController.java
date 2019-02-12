package org.apereo.openlrw.entity;

import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sync")
public class DataSyncController {
  
  private MongoDataSyncRepository mongoDataSyncRepository;
  
  @Autowired
  public DataSyncController(MongoDataSyncRepository mongoDataSyncRepository) {
    this.mongoDataSyncRepository = mongoDataSyncRepository;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> postLineItem(JwtAuthenticationToken token, @RequestBody DataSync dataSync) {
    UserContext userContext = (UserContext) token.getPrincipal();
    
    DataSync fullyPopulatedSync
      = new DataSync.Builder()
        .withId(null)
        .withOrgId(userContext.getOrgId())
        .withSyncDateTime(dataSync.getSyncDateTime())
        .withSyncStatus(dataSync.getSyncStatus())
        .withSyncType(dataSync.getSyncType())
        .withTenantId(userContext.getTenantId())
        .build();
    
    return new ResponseEntity<>(mongoDataSyncRepository.save(fullyPopulatedSync), null, HttpStatus.CREATED);
  }
  
  @RequestMapping(value = "/{syncType}/latest", method = RequestMethod.GET)
  public DataSync getEventForClassAndUser(JwtAuthenticationToken token, @PathVariable String syncType) {
    UserContext userContext = (UserContext) token.getPrincipal();
    return mongoDataSyncRepository.findTopByTenantIdAndOrgIdAndSyncTypeOrderBySyncDateTimeDesc(userContext.getTenantId(),userContext.getOrgId(), syncType);
  }


}
