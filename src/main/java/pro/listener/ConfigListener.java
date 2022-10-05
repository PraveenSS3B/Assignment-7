package pro.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigListener implements JobExecutionListener {
	String previous = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/input/previous.csv";
	String current = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/input/current.csv";
	String currentNew = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/input/currentNew.csv";

	@Override
	public void beforeJob(JobExecution jobExecution) {
		ArrayList<String> prevAl = new ArrayList<>();
		ArrayList<String> currAl = new ArrayList<>();

		String prevCsvRow;
		try {
			BufferedReader prevCSV = new BufferedReader(new FileReader(previous));
			prevCsvRow = prevCSV.readLine();
			while (prevCsvRow != null) {
				String[] rowArray = prevCsvRow.split(",");
				for (String item1 : rowArray) {
					prevAl.add(item1);
				}

				prevCsvRow = prevCSV.readLine();
			}

			prevCSV.close();

			BufferedReader currCSV = new BufferedReader(new FileReader(current));
			String currCsvRow = currCSV.readLine();
			while (currCsvRow != null) {
				String[] dataArray2 = currCsvRow.split(",");
				for (String item2 : dataArray2) {
					currAl.add(item2);

				}
				currCsvRow = currCSV.readLine();
			}
			currCSV.close();

			for (String sA : prevAl) {
				currAl.remove(sA);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int size = currAl.size();
		try {
			FileWriter writer = new FileWriter(current, false);
			 BufferedWriter bw = new BufferedWriter(writer);
			writer.append("gender");
			writer.append(",");
			writer.append("mark");
			writer.append(",");
			writer.append("class");
			writer.append(",");
			writer.append("name");
			writer.append(",");
			writer.append("id");
			writer.append('\n');
			while (size != 0) {
				size--;
				bw.write("" + currAl.get(size));
				if (size % 5 == 0)
					bw.append('\n');
				else
					bw.append(",");
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
//		 File src = new File(Paths.get("").toAbsolutePath().toString() + "/src/main/resources/output/current.csv");
//	        File dest = new File(Paths.get("").toAbsolutePath().toString() + "/src/main/resources/input/current.csv");
//	             
//	        try {
//				Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
	}

}
