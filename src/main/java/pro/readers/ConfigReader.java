package pro.readers;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import pro.dto.DiffCsv;

@Configuration
public class ConfigReader {


	Logger log = LoggerFactory.getLogger(ConfigReader.class);


	@Bean
	public FlatFileItemReader<DiffCsv> currentReader() throws MalformedURLException {
		FlatFileItemReader<DiffCsv> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new ClassPathResource("input/currentNew.csv"));
		flatFileItemReader.setLineMapper(new DefaultLineMapper<DiffCsv>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "gender", "mark", "classRoom", "name", "id" });
//						setNames(new String[] { "id", "name", "classRoom", "mark", "gender" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<DiffCsv>() {
					{
						setTargetType(DiffCsv.class);
					}
				});
			}
		});
		// flatFileItemReader.setLinesToSkip(1);
		log.trace("Read From current.csv file");
		return flatFileItemReader;
	}


}
