package org.apereo.openlrw.admin;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = EventAlias.Builder.class)
public class EventAlias {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private Date lastUpdated = new Date();
	private String verb;
	private String alias;
	private boolean display;	    
    @Id protected String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


	public String getTenantId() {
		return tenantId;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public static class Builder {
		private EventAlias _eventAlias = new EventAlias();

		public Builder withId(String id) {
			_eventAlias.id = id;
			return this;
		}
		
		public Builder withTenantId(String tenantId) {
			_eventAlias.tenantId = tenantId;
			return this;
		}

		public Builder withLastUpdated(Date lastUpdated) {
			_eventAlias.lastUpdated = lastUpdated;
			return this;
		}

		public Builder withVerb(String verb) {
			_eventAlias.verb = verb;
			return this;
		}
		
		public Builder withAlias(String alias) {
			_eventAlias.alias = alias;
			return this;
		}
		
		public Builder withDisplay(Boolean display) {
			_eventAlias.display = display;
			return this;
		}
		
		public EventAlias build() {
			return _eventAlias;
		}		
		
	}
}
