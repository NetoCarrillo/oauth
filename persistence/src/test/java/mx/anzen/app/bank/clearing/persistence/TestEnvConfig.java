package mx.anzen.app.bank.clearing.persistence;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:test.properties")
@ComponentScan(basePackages = {
	"mx.anzen.app.bank.clearing.persistence.model",
	"mx.anzen.app.bank.clearing.persistence.repositories"})
@EnableTransactionManagement
@EnableJpaRepositories("mx.anzen.app.bank.clearing.persistence.repositories")
public class TestEnvConfig{

	@Value("${spring.datasource.url}")
	private String dbUrl;
	@Value("${spring.datasource.username}")
	private String dbUsr;
	@Value("${spring.datasource.password}")
	private String dbPsw;
	@Value("${spring.datasource.driver-class-name}")
	private String dbDrv;
	@Value("${hibernate.dialect}")
	private String dialect;
	@Value("${hibernate.default_schema}")
	private String schema;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(dbDrv);
		ds.setUrl(dbUrl);
		ds.setUsername(dbUsr);
		ds.setPassword(dbPsw);
		
		return ds;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory(){
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter.setShowSql(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("mx.anzen.app.bank.clearing.persistence.model");
		factory.setDataSource(dataSource());
		
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", dialect);
		props.setProperty("hibernate.default_schema", schema);
//		props.setProperty("hibernate.format_sql", "true");
		factory.setJpaProperties(props);
		
		factory.afterPropertiesSet();
		
		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		txManager.setDataSource(dataSource());
		
		return txManager;
	}
}
