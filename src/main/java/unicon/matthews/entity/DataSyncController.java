package unicon.matthews.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import unicon.matthews.security.auth.JwtAuthenticationToken;
import unicon.matthews.security.model.UserContext;

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
