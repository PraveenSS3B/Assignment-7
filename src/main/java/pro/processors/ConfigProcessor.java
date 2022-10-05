package pro.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pro.dto.DiffCsv;

@Configuration
public class ConfigProcessor {
	Logger log = LoggerFactory.getLogger(ConfigProcessor.class);


	@Bean
	public ItemProcessor<DiffCsv, DiffCsv> successProcessor() {
		return diffCsv -> {
			if (diffCsv.getMark().equalsIgnoreCase("mark"))
				return diffCsv;
			if (diffCsv.getId().equalsIgnoreCase("id"))
				return diffCsv;
			if (Integer.parseInt(diffCsv.getMark()) > 80)
				return diffCsv;
			return null;
		};
	}

	@Bean
	public ItemProcessor<DiffCsv, DiffCsv> rejectProcessor() {
		return diffCsv -> {
			if (diffCsv.getMark().equalsIgnoreCase("mark"))
				return diffCsv;
			if (diffCsv.getId().equalsIgnoreCase("id"))
				return diffCsv;
			if (Integer.parseInt(diffCsv.getMark()) < 80)
				return diffCsv;
			return null;
		};
	}

}
