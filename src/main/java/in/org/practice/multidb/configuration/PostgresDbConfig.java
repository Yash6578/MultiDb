package in.org.practice.multidb.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "getPostgresEmf",
        transactionManagerRef = "getPostgresTransactionManager",
        basePackages = "in.org.practice.multidb.repository.postgres"
)
public class PostgresDbConfig {
    //DataSource
    @Bean
    @ConfigurationProperties(prefix = "postgres.datasource")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }

    //Entity Manager
    @Bean
    LocalContainerEntityManagerFactoryBean getPostgresEmf(EntityManagerFactoryBuilder emfb) {
        Map<String, Object> properties = new HashMap<>();

        properties.put("show-sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");

        return emfb
                .dataSource(postgresDataSource())
                .packages("in.org.practice.multidb.entity.postgres")
                .properties(properties)
                .build();
    }

    //Tx Manager
    @Bean
    PlatformTransactionManager getPostgresTransactionManager(
            @Qualifier("getPostgresEmf")
            EntityManagerFactory emf) {

        return new JpaTransactionManager(emf);
    }
}
