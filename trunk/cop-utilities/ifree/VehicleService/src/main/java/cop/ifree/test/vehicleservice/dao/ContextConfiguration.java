package cop.ifree.test.vehicleservice.dao;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Oleg Cherednik
 * @since 15.05.2013
 */
@Configuration
public class ContextConfiguration {
	@Bean
	public DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();

		ds.setDriverClassName("org.hsqldb.jdbcDriver");
		ds.setUrl("jdbc:hsqldb:hsql://localhost/vsdb");
		ds.setUsername("sa");

		return ds;
	}
}
