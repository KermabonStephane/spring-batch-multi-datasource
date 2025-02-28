package org.demis27.example.datasource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "org.demis27.example.input.jpa",
        entityManagerFactoryRef = "inputEntityManager",
        transactionManagerRef = "inputTransactionManager"
)
public class InputDataSourceConfiguration {

    @Bean(name = "inputDataSource")
    public DataSource inputDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "inputEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean inputEntityManager(@Qualifier("inputDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("org.demis27.example.input.jpa");

        return entityManager;
    }

    @Bean(name = "inputTransactionManager")
    public PlatformTransactionManager inputTransactionManager(@Qualifier("inputEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
