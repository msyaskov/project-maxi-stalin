package it.maxi.project.stalin.config.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "it.maxi.project.stalin.repository",
        entityManagerFactoryRef = "userEntityManager",
        transactionManagerRef = "userTransactionManager"
)
public class DataSourceConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean userEntityManager(
            DataSource userDataSource,
            @Value("${spring.jpa.properties.hibernate.dialect}") String hibernateDialect
//            @Value("${spring.jpa.generate-ddl}") boolean jpaGenerateDdl
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(userDataSource);
        em.setPackagesToScan("it.maxi.project.stalin.model");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", hibernateDialect);
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource userDataSource(
            @Value("${spring.datasource.stalin.driver}") String driver,
            @Value("${spring.datasource.stalin.url}") String url,
            @Value("${spring.datasource.stalin.username}") String username,
            @Value("${spring.datasource.stalin.password}") String password
    ) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager userTransactionManager(LocalContainerEntityManagerFactoryBean userEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(userEntityManager.getObject());
        return transactionManager;
    }


}
