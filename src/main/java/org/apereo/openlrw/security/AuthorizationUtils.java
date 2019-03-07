package org.apereo.openlrw.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

/**
 * @author scody
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 *
 */
public class AuthorizationUtils {
  private static Logger log = LoggerFactory.getLogger(AuthorizationUtils.class);

  public static String getKeyFromHeader(String authorizationHeader) {

    if (log.isDebugEnabled()) {
      log.debug(String.format("Authorization Header: %s", authorizationHeader));
    }

    if (StringUtils.isNotBlank(authorizationHeader)) {
      return getKeyBasic(authorizationHeader);
    }
    throw new Error("Couldn't retrieve key from header: " + authorizationHeader);
  }
  
  public static String getSecretFromHeader(String authorizationHeader) {
    if (log.isDebugEnabled()) {
      log.debug(String.format("Authorization Header: %s", authorizationHeader));
    }

    if (StringUtils.isNotBlank(authorizationHeader)) {
      return getSecretBasic(authorizationHeader);
    }
    throw new Error("Couldn't retrieve key from header: " + authorizationHeader);

  }

  private static String getKeyBasic(String authorizationHeader) {
    StringTokenizer st = new StringTokenizer(authorizationHeader);
    if (st.hasMoreTokens()) {
      String basic = st.nextToken();

      if (basic.equalsIgnoreCase("Basic") || basic.equalsIgnoreCase("Base64")) {

        try {
          String credentials = new String(Base64.decodeBase64(st.nextToken()), "UTF-8");

          int colon = credentials.indexOf(":");

          if (colon != -1) {
            return credentials.substring(0, colon).trim();
          } else {
            return "";
          }
        } catch (UnsupportedEncodingException e) {
          throw new Error("Couldn't retrieve key", e);
        }
      } else {
        throw new Error("Couldn't retrieve key from header: " + authorizationHeader);
      }
    } else {
      throw new Error("Couldn't retrieve key from header: " + authorizationHeader);
    }
  }
  
  private static String getSecretBasic(String authorizationHeader) {
    StringTokenizer st = new StringTokenizer(authorizationHeader);
    if (st.hasMoreTokens()) {
      String basic = st.nextToken();

      if (basic.equalsIgnoreCase("Basic") || basic.equalsIgnoreCase("Base64")) {

        try {
          String credentials = new String(Base64.decodeBase64(st.nextToken()), "UTF-8");
           return  StringUtils.substringAfter(credentials, ":");
        } catch (UnsupportedEncodingException e) {
          throw new Error("Couldn't retrieve key", e);
        }
      } else {
        throw new Error("Couldn't retrieve key from header: " + authorizationHeader);
      }
    } else {
      throw new Error("Couldn't retrieve key from header: " + authorizationHeader);
    }
  }

}
