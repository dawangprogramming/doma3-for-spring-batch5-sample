package com.example.batchprocessing;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BatchProcessingApplication {

    
	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
//		var a = SpringApplication.run(BatchProcessingApplication.class, args);
//		System.exit(SpringApplication.exit(a));
		
		
		// start by main and set job name
SpringApplication app = new SpringApplication(BatchProcessingApplication.class);		
		ConfigurableApplicationContext ctx = app.run(args);
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		
		JobParameters jobParameters = new JobParametersBuilder().addDate("data", new Date()).toJobParameters();
		Job newJob = ctx.getBean("importUserJob1", Job.class);
		
		JobExecution jobExecution = jobLauncher.run(newJob, jobParameters);
		System.exit(0);
	}

}
