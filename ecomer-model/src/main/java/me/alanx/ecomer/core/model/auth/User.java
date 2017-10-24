package me.alanx.ecomer.core.model.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import me.alanx.ecomer.core.model.generic.AuditableEntity;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;

@Entity
@Table(name = "EC_USERS")
public class User extends AuditableEntity<Long, User> {
	
	
	private static final long serialVersionUID = 5401059537544058710L;

	public User() {}
	
	public User(String username, String email) {
		
		this.username = username;
		this.email = email;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
	private Long id;
	
	@Column
	private String username;
	
	@ManyToMany
	@JoinTable(name = "USER_GROUPS",
				joinColumns = @JoinColumn(name = "USER_ID"),
				inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
	private Collection<Group> groups = new ArrayList<>();
	
	@NotNull
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotNull
	@Column(nullable = false)
	private String password;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID")
	private MerchantStore merchantStore;
	
	@NotNull
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	
	@NotNull
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@Column
	private boolean active = true;
	
	@ManyToOne
	@JoinColumn(name = "LANGUAGE_ID")
	private Language defaultLanguage;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", nullable = false)
	@OrderColumn(name = "QUESTION_ORDER")
	private List<SecurityAnswer> securityAnswers = new ArrayList<>(3);
	
	@Column(name = "LAST_ACCESS")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastAccess;
	
	@Column(name = "LOGIN_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginTime;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getAdminPassword() {
		return password;
	}

	public void setAdminPassword(String adminPassword) {
		this.password = adminPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Language getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(Language defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}



	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the securityAnswers
	 */
	public List<SecurityAnswer> getSecurityAnswers() {
		return securityAnswers;
	}

	/**
	 * @param securityAnswers the securityAnswers to set
	 */
	public void setSecurityAnswers(List<SecurityAnswer> securityAnswers) {
		this.securityAnswers = securityAnswers;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Collection<Group> getGroups() {
		return groups;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

}
