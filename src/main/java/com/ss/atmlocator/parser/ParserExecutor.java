package com.ss.atmlocator.parser;

import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.service.DbParserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;

/**
 *
 */
@Configurable
public abstract class ParserExecutor implements Job, IParser {
    final static org.slf4j.Logger logger = LoggerFactory.getLogger(ParserExecutor.class);
    protected Properties parserProperties = new Properties();

    private DbParserService parserService;

    public  void execute(JobExecutionContext executionContext){
        parserService = (DbParserService)initAppContext(executionContext).getBean("dbParserService");
        Map parameters = executionContext.getJobDetail().getJobDataMap();
        setParameter(parameters);

        int bankId = Integer.valueOf((String)parameters.get("bankid"));
        List<AtmOffice> atms = null;
        try {
            atms = parse();
            parserService.update(atms, bankId);
        } catch (IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
        }

    }

    /**
     * Setting parameters to parser from property file or given map
     * Properties that are set by admin page override properties from file if the names are same
     * @param parameters that will by set to parser
     */
    @Override
    public void setParameter(Map<String, String> parameters){
        Properties fromFile = loadProperties();
        for(String paramName : fromFile.stringPropertyNames()){
            if(parameters.containsKey(paramName)){
                parserProperties.put(paramName, parameters.get(paramName));
                parameters.remove(paramName);
            }else {
                parserProperties.put(paramName, fromFile.get(paramName));
            }
        }
        parserProperties.putAll(parameters);
    }

    /**
     * Load properties from file
     */
    private Properties loadProperties(){
        try {
            Properties properties = new Properties();
            logger.info("Try to load properties from file " + getFilePath());
            InputStream propFile = new FileInputStream(getFilePath());
            InputStreamReader isr = new InputStreamReader(propFile, "UTF8");
            properties.load(isr);
            logger.info("File successfully loaded.");
            return properties;
        }catch (IOException ioe){
            logger.error("Loading file failed. Properties from admin page will be used");
            return new Properties();
        }
    }

    /**
     *
     * @return path to property file based on parser class name
     * @throws IOException if can't find directory
     */
    private String getFilePath() throws IOException {
        String dirPath = new ClassPathResource("parserProperties").getURI().getPath();
        String className = this.getClass().getSimpleName();
        String propertyFileName = className.substring(0,1).toLowerCase() + className.substring(1) + ".properties";
        return dirPath + "/" + propertyFileName;
    }


    /**
     *
     * @param context
     * @return spring app context for using in non spring beens
     */
    private ApplicationContext initAppContext(JobExecutionContext context){
        ApplicationContext applicationContext;
        try{
            applicationContext = (ApplicationContext)context
                    .getScheduler().getContext().get("applicationContext");
        }
        catch(SchedulerException exp){
            logger.error(exp.getMessage(),exp);
            return null;
        }
        return applicationContext;
    }

    /**
     * @return formatted address string based on rawAddress
     * parameters for formatting get from properties
     */
    protected String  prepareAddress(String rawAddress) {
        String result = rawAddress;
        for (String paramName : new TreeSet<>(parserProperties.stringPropertyNames())) {
            if (paramName.matches("replace\\.regexp\\..*")) {
                String regexp = parserProperties.getProperty(paramName);
                String replaceValue = parserProperties.getProperty(paramName.replace("regexp", "value"));
                result = result.replaceAll(regexp, replaceValue);
            }
        }
        return result.trim();
    }

    /**
     * Wrapper on properties
     * @param prop property key
     * @return property value
     */
    protected String getProp(String prop){
        return (String) parserProperties.get(prop);
    }

}
