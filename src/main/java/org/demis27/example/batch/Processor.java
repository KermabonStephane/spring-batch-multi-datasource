package org.demis27.example.batch;

import org.demis27.example.input.jpa.Person;
import org.demis27.example.output.jpa.Employee;
import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<Person, Employee> {
    @Override
    public Employee process(Person item) throws Exception {
        return Employee.builder().firstname(item.getFirstname()).lastname(item.getLastname()).build();
    }
}
