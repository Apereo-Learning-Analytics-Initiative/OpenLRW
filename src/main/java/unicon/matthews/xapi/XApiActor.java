package unicon.matthews.xapi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Holds a representation of a statement actor
 *
 * @author Robert E. Long (rlong @ unicon.net)
 */
@JsonInclude(Include.NON_NULL)
public class XApiActor {

    private XApiActorTypes objectType;
    
    private String mbox;
    
    private String name;
    
    private String mbox_sha1sum;
    private String openid;
    
    private XApiAccount account;
    
    private List<XApiActor> member;
 
    public XApiActor() {}
    
    public XApiActorTypes getObjectType() {
        return objectType;
    }

    public void setObjectType(XApiActorTypes objectType) {
        this.objectType = objectType;
    }

	/**
	 * @return the mbox
	 */
	public String getMbox() {
		return mbox;
	}

	/**
	 * @param mbox the mbox to set
	 */
	public void setMbox(String mbox) {
		this.mbox = mbox;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mbox_sha1sum
	 */
	public String getMbox_sha1sum() {
		return mbox_sha1sum;
	}

	/**
	 * @param mbox_sha1sum the mbox_sha1sum to set
	 */
	public void setMbox_sha1sum(String mbox_sha1sum) {
		this.mbox_sha1sum = mbox_sha1sum;
	}

	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @return the account
	 */
	public XApiAccount getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(XApiAccount account) {
		this.account = account;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LRSActor [objectType=" + objectType + ", mbox=" + mbox
				+ ", name=" + name + ", mbox_sha1sum=" + mbox_sha1sum
				+ ", openid=" + openid + ", account=" + account + "]";
	}

	/**
	 * @return the member
	 */
	public List<XApiActor> getMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(List<XApiActor> member) {
		this.member = member;
	}
}
