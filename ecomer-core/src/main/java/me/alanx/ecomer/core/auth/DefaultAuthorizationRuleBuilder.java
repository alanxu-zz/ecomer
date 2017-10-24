package me.alanx.ecomer.core.auth;

import java.util.HashSet;
import java.util.Set;

import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.GroupType;
import me.alanx.ecomer.core.model.auth.Permission;

public class DefaultAuthorizationRuleBuilder implements AuthorizationRuleBuilder {

	private final Set<Permission> permissions = new HashSet<>();
	private final Set<Group> groups = new HashSet<>();
	
	private final Permission storeAdmin = new Permission("STORE_ADMIN");
	private final Permission superAdmin = new Permission("SUPERADMIN");
	private final Permission admin = new Permission("ADMIN");
	private final Permission auth = new Permission("AUTH");
	private final Permission products = new Permission("PRODUCTS");
	private final Permission order = new Permission("ORDER");
	private final Permission store = new Permission("STORE");
	private final Permission tax = new Permission("TAX");
	private final Permission payment = new Permission("PAYMENT");
	private final Permission customer = new Permission("CUSTOMER");
	private final Permission shipping = new Permission("SHIPPING");
	private final Permission content = new Permission("CONTENT");
	private final Permission authCustomer = new Permission("AUTH_CUSTOMER");
	
	private final Group superAdminGrp = new Group("SUPERADMIN");
	private final Group adminGrp = new Group("ADMIN");
	private final Group adminCatalogGrp = new Group("ADMIN_CATALOGUE");
	private final Group adminStoreGrp = new Group("ADMIN_STORE");
	private final Group adminOrderGrp = new Group("ADMIN_ORDER");
	private final Group adminContentGrp = new Group("ADMIN_CONTENT");
	private final Group customerGrp = new Group("CUSTOMER");
	
	{
		superAdminGrp.addPermission(storeAdmin);
		adminGrp.addPermission(storeAdmin);
		
		superAdminGrp.addPermission(superAdmin);
		
		superAdminGrp.addPermission(auth);
		adminGrp.addPermission(auth);
		adminCatalogGrp.addPermission(auth);
		adminStoreGrp.addPermission(auth);
		adminOrderGrp.addPermission(auth);
		
		superAdminGrp.addPermission(products);
		adminGrp.addPermission(products);
		adminCatalogGrp.addPermission(products);
		
		superAdminGrp.addPermission(order);
		adminGrp.addPermission(order);
		adminOrderGrp.addPermission(order);
		
		superAdminGrp.addPermission(content);
		adminGrp.addPermission(content);
		adminContentGrp.addPermission(content);
		
		superAdminGrp.addPermission(store);
		adminGrp.addPermission(store);
		adminStoreGrp.addPermission(store);
		
		superAdminGrp.addPermission(tax)
						.addPermission(payment)
						.addPermission(shipping)
						.addPermission(customer);
		adminGrp.addPermission(tax)
				.addPermission(payment)
				.addPermission(shipping)
				.addPermission(customer);
		adminStoreGrp.addPermission(tax)
						.addPermission(payment)
						.addPermission(shipping)
						.addPermission(customer);
		
		customerGrp.addPermission(authCustomer);
		
		this.permissions.add(storeAdmin);
		this.permissions.add(admin);
		this.permissions.add(auth);
		this.permissions.add(products);
		this.permissions.add(order);
		this.permissions.add(store);
		this.permissions.add(tax);
		this.permissions.add(payment);
		this.permissions.add(customer);
		this.permissions.add(shipping);
		this.permissions.add(content);
		this.permissions.add(authCustomer);
		
		this.groups.add(superAdminGrp);
		this.groups.add(adminGrp);
		this.groups.add(adminCatalogGrp);
		this.groups.add(adminContentGrp);
		this.groups.add(adminOrderGrp);
		this.groups.add(adminStoreGrp);
		this.groups.add(customerGrp);
		
		this.superAdminGrp.setGroupType(GroupType.ADMIN);
		this.adminGrp.setGroupType(GroupType.ADMIN);
		this.adminCatalogGrp.setGroupType(GroupType.ADMIN);
		this.adminContentGrp.setGroupType(GroupType.ADMIN);
		this.adminOrderGrp.setGroupType(GroupType.ADMIN);
		this.adminStoreGrp.setGroupType(GroupType.ADMIN);
		this.customerGrp.setGroupType(GroupType.CUSTOMER);
		
	}
	
	@Override
	public Set<Permission> getPermissions() {
		return this.permissions;
	}

	@Override
	public Set<Group> getPermissionedGroups() {
		return this.groups;
	}

}
