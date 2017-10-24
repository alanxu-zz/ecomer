package me.alanx.ecomer.core.model.naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class DefaultNamingStrategy extends PhysicalNamingStrategyStandardImpl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl#toPhysicalColumnName(org.hibernate.boot.model.naming.Identifier, org.hibernate.engine.jdbc.env.spi.JdbcEnvironment)
	 */
	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return toUpperCase(name);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl#toPhysicalCatalogName(org.hibernate.boot.model.naming.Identifier, org.hibernate.engine.jdbc.env.spi.JdbcEnvironment)
	 */
	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
		return toUpperCase(name);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl#toPhysicalSchemaName(org.hibernate.boot.model.naming.Identifier, org.hibernate.engine.jdbc.env.spi.JdbcEnvironment)
	 */
	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
		return toUpperCase(name);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl#toPhysicalTableName(org.hibernate.boot.model.naming.Identifier, org.hibernate.engine.jdbc.env.spi.JdbcEnvironment)
	 */
	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return toUpperCase(name);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl#toPhysicalSequenceName(org.hibernate.boot.model.naming.Identifier, org.hibernate.engine.jdbc.env.spi.JdbcEnvironment)
	 */
	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
		return toUpperCase(name);
	}
	
	private Identifier toUpperCase(Identifier name) {
		if(name != null)
			return new Identifier(name.getText().toUpperCase(), name.isQuoted());
		return name;
	}

}
