package org.demis27.example.batch;

import jakarta.persistence.EntityManagerFactory;
import org.demis27.example.input.jpa.Person;
import org.demis27.example.output.jpa.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfiguration {

    @Bean(name = "person-become-employee-job")
    public Job personBecomeEmployeeJob(JobRepository repository, Step step) {
        return new JobBuilder("person-become-employee-job", repository)
                .start(step)
                .build();
    }

    @Bean(name = "person-become-employee-step")
    public Step step(
            JobRepository repository,
            PlatformTransactionManager transactionManager,
            JpaPagingItemReader<Person> reader,
            ItemProcessor<Person, Employee> processor,
            ItemWriter<Employee> writer) {
        return new StepBuilder("person-become-employee-step", repository)
                .<Person, Employee>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Person> itemReader(@Qualifier("inputEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Person>().name("itemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select * from Person p")
                .build();
    }
    @Bean
    private ItemProcessor<Person, Employee> processor() {
        return new Processor();
    }

    @Bean
    public JpaItemWriter<Employee> itemWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<Employee>().entityManagerFactory(entityManagerFactory).build();
    }
}
