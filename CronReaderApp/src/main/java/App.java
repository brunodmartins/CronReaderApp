import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;


public class App {
	
	public static void main(String[] args){
		CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
		CronParser parser = new CronParser(cronDefinition);
		String file = null;
		if( new File("./quartz.properties").exists()){
			file = "./quartz.properties";
		} else {
			if (args.length != 0){
				file = args[0];
			} else {
				System.err.println("Forneça um arquivo como parametro!");
				System.exit(0);
			}
		}
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(file));
		} catch (Exception e) {
			System.err.println("Arquivo não encontrado!");
			e.printStackTrace();
		}
		
		prop.forEach((k,v) -> {
			System.out.printf("%s;", k);
			Cron quatzCron = parser.parse((String) v);
			CronDescriptor descriptor = CronDescriptor.instance(Locale.US);
			String timer = descriptor.describe(quatzCron);
			System.out.printf("%s \n", timer);
		});
		
		
	}

}
