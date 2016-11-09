/**
 * 
 */
package unicon.matthews.caliper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author ggilbert
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = Envelope.Builder.class)
public class Envelope implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotNull
  private String sensor;
  
  @NotNull
  private LocalDateTime sendTime;
  
  @NotNull
  private List<Event> data;
  
  private Envelope() {}

  public String getSensor() {
    return sensor;
  }

  public LocalDateTime getSendTime() {
    return sendTime;
  }

  public List<Event> getData() {
    return data;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((data == null) ? 0 : data.hashCode());
    result = prime * result + ((sendTime == null) ? 0 : sendTime.hashCode());
    result = prime * result + ((sensor == null) ? 0 : sensor.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Envelope other = (Envelope) obj;
    if (data == null) {
      if (other.data != null)
        return false;
    } else if (!data.equals(other.data))
      return false;
    if (sendTime == null) {
      if (other.sendTime != null)
        return false;
    } else if (!sendTime.equals(other.sendTime))
      return false;
    if (sensor == null) {
      if (other.sensor != null)
        return false;
    } else if (!sensor.equals(other.sensor))
      return false;
    return true;
  }

  public static class Builder {
    private Envelope _envelope = new Envelope();
    
    public Builder withSensor(String sensor) {
      _envelope.sensor = sensor;
      return this;
    }
    
    public Builder withSendTime(LocalDateTime sendTime) {
      _envelope.sendTime = sendTime;
      return this;
    }
    
    public Builder withData(List<Event> data) {
      _envelope.data = data;
      return this;
    }
    
    public Envelope build() {
      if (_envelope.data == null || _envelope.data.isEmpty()
          || StringUtils.isBlank(_envelope.sensor) 
          || _envelope.sendTime == null) {
        throw new IllegalStateException();
      }
      return _envelope;
    }
  }
}
