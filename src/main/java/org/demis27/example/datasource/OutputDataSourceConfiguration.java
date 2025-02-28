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
        basePackages = "org.demis27.example.output.jpa",
        entityManagerFactoryRef = "outputEntityManager",
        transactionManagerRef = "outputTransactionManager"
)
public class OutputDataSourceConfiguration {
    
    @Bean(name = "outputDataSource")
    public DataSource outputDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "outputEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean outputEntityManager(@Qualifier("outputDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("org.demis27.example.output.jpa");
        
        return entityManager;
    }
    
    @Bean(name = "outputTransactionManager")
    public PlatformTransactionManager outputTransactionManager(@Qualifier("outputEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
     
}
