package in.org.practice.multidb.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "getMySqlEmf",
        transactionManagerRef = "getMySqlTransactionManager",
        basePackages = "in.org.practice.multidb.repository.sql"
)
public class MySqlDbConfig {
    //DataSource
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "mysql.datasource")
    public DataSource mySqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    //Entity Manager
    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean getMySqlEmf(EntityManagerFactoryBuilder emfb) {
        Map<String, Object> properties = new HashMap<>();

        properties.put("show-sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

        return emfb
                .dataSource(mySqlDataSource())
                .packages("in.org.practice.multidb.entity.sql")
                .properties(properties)
                .build();
    }

    //Tx Manager
    @Bean
    @Primary
    PlatformTransactionManager getMySqlTransactionManager(
            @Qualifier("getMySqlEmf")
            EntityManagerFactory emf) {

        return new JpaTransactionManager(emf);
    }
}
