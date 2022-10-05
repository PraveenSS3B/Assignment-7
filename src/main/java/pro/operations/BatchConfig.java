package pro.operations;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pro.dto.DiffCsv;
import pro.listener.ConfigListener;
import pro.processors.ConfigProcessor;
import pro.readers.ConfigReader;
import pro.writers.ConfigWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ConfigReader configReader;

	@Autowired
	private ConfigWriter configWriter;

	@Autowired
	private ConfigProcessor configProcessor;

	@Autowired
	private ConfigListener configListener;

	Logger log = LoggerFactory.getLogger(BatchConfig.class);


	@Bean
	public Step successCsvStep() throws MalformedURLException {
		log.trace("Performing Step 1");
		return stepBuilderFactory.get("Step 1").<DiffCsv, DiffCsv>chunk(2).reader(configReader.currentReader())
				.processor(configProcessor.successProcessor())
				.writer(configWriter.successCsvWriter())
				.build();
	}

	@Bean
	public Step rejectCsvStep() throws MalformedURLException {
		log.trace("Performing Step 2");
		return stepBuilderFactory.get("Step 1").<DiffCsv, DiffCsv>chunk(2).reader(configReader.currentReader())
				.processor(configProcessor.rejectProcessor()).writer(configWriter.rejectCsvWriter()).build();
	}


	@Bean
	public Job job1() throws MalformedURLException {
		return jobBuilderFactory.get("Job 1").incrementer(new RunIdIncrementer())
				.listener(configListener)
				.start(successCsvStep())
				.next(rejectCsvStep())
				.build();
	}

}
