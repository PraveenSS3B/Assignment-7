package pro.writers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import pro.dto.DiffCsv;

@Configuration
public class ConfigWriter {
	Logger log = LoggerFactory.getLogger(ConfigWriter.class);



	@Bean
	public FlatFileItemWriter<DiffCsv> successCsvWriter() {
		FlatFileItemWriter<DiffCsv> writer = new FlatFileItemWriter<DiffCsv>();
		writer.setResource(
				new FileSystemResource(System.getProperty("user.dir") + "/src/main/resources/output/success.csv"));
		DelimitedLineAggregator<DiffCsv> delimitedLineAggregator = new DelimitedLineAggregator<DiffCsv>();
		delimitedLineAggregator.setDelimiter(",");

		BeanWrapperFieldExtractor<DiffCsv> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<DiffCsv>();
		beanWrapperFieldExtractor.setNames(new String[] { "gender", "mark", "classRoom", "name", "id" });
		delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

		writer.setLineAggregator(delimitedLineAggregator);
		log.trace("Writing into success.csv from current.csv....");
		return writer;
	}

	@Bean
	public FlatFileItemWriter<DiffCsv> rejectCsvWriter() {
		FlatFileItemWriter<DiffCsv> writer = new FlatFileItemWriter<DiffCsv>();
		writer.setResource(
				new FileSystemResource(System.getProperty("user.dir") + "/src/main/resources/output/reject.csv"));
		DelimitedLineAggregator<DiffCsv> delimitedLineAggregator = new DelimitedLineAggregator<DiffCsv>();
		delimitedLineAggregator.setDelimiter(",");

		BeanWrapperFieldExtractor<DiffCsv> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<DiffCsv>();
		beanWrapperFieldExtractor.setNames(new String[] { "gender", "mark", "classRoom", "name", "id" });
		delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

		writer.setLineAggregator(delimitedLineAggregator);
		log.trace("Writing into reject.csv from current.csv....");
		return writer;
	}

}
