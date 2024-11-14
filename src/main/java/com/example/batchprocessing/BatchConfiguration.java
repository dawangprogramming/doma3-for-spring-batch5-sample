package com.example.batchprocessing;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class BatchConfiguration {

//	@Autowired
//	MessageDao messageDao;
	
	
	private final JobRepository jobRepository;
	private final DataSourceTransactionManager transactionManager;
	
	
	public BatchConfiguration(JobRepository jobRepository, DataSourceTransactionManager transactionManager) {
		
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
		
	}
	
	// tag::readerwriterprocessor[]
	@Bean
	public FlatFileItemReader<Person> reader() {
		return new FlatFileItemReaderBuilder<Person>()
			.name("personItemReader")
			.resource(new ClassPathResource("sample-data.csv"))
			.delimited()
			.names("firstName", "lastName")
			.targetType(Person.class)
			.build();
	}

	@Bean
	public PersonItemProcessor processor() {
		
		return new PersonItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Person>()
			.sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
			.dataSource(dataSource)
			.beanMapped()
			.build();
	}
	
//	@Bean 
//	public FlatFileItemWriter<Person> writer(List<Person> p) {
//		
//		
//		System.out.println("wsssssssssss"+ p.size());
//		
//		
//		//LineAggregator or a DelimitedBuilder or a FormattedBuilder
//		return new FlatFileItemWriterBuilder<Person>()
//				.name("persionItemWriter")
//				.lineAggregator(it -> it.firstName() + it.lastName())
//				.resource(new FileSystemResource(".¥wdwsample-data.csv"))
//				.build();
//	}
	
	
	
	
	
	// end::readerwriterprocessor[]

	// tag::jobstep[]
	@Bean
	public Job importUserJob(JobRepository jobRepository,Step step1, JobCompletionNotificationListener listener) {
		return new JobBuilder("importUserJob", jobRepository)
			.listener(listener)
			.start(step1)
			.build();
	}

	@Bean
	public Job importUserJob1(JobRepository jobRepository,Step step1, JobCompletionNotificationListener listener) {
		return new JobBuilder("importUserJob1", jobRepository)
			.listener(listener)
			.start(step1)
			.build();
	}

	
	
	@Bean
	public TaskExecutor taskExecutor() {
	    return new SimpleAsyncTaskExecutor("spring_batch");
	}

	
	@Bean
	public Step step1(TaskExecutor taskExecutor, JobRepository jobRepository, DataSourceTransactionManager transactionManager,
					  FlatFileItemReader<Person> reader, PersonItemProcessor processor, JdbcBatchItemWriter<Person> writer) {
		return new StepBuilder("step1", jobRepository)
			.<Person, Person> chunk(3, transactionManager)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.taskExecutor(taskExecutor)
			.build();
	}
	// end::jobstep[]
}
