package engineer.thesis.core.batch;

import engineer.thesis.core.model.Drug;
import engineer.thesis.core.model.dto.ExternalDrugDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.util.InMemoryResource;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableBatchProcessing
public class ExternalApiXmlBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Autowired
    private DrugProcessor drugProcessor;

    @Autowired
    private DrugWriter drugWriter;

    @Bean
    public ItemReader<ExternalDrugDTO> xmlFileItemReader() {
        RestTemplate externalTemplate = new RestTemplate();
        String external = "http://pub.rejestrymedyczne.csioz.gov.pl/pobieranie_WS/Pobieranie.ashx?filetype=XMLFile&regtype=RPL_FILES";
        String xmlAsString = externalTemplate.getForObject(external, String.class);

        StaxEventItemReader<ExternalDrugDTO> xmlFileReader = new StaxEventItemReader<>();
        xmlFileReader.setResource(new InMemoryResource(xmlAsString));
        xmlFileReader.setFragmentRootElementName("produktLeczniczy");

        Jaxb2Marshaller drugMarshaller = new Jaxb2Marshaller();
        drugMarshaller.setClassesToBeBound(ExternalDrugDTO.class);
        xmlFileReader.setUnmarshaller(drugMarshaller);

        return xmlFileReader;
    }

    @Bean
    public Job importDrugsFromExternalDatabase(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();

    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<ExternalDrugDTO, Drug> chunk(10)
                .reader(xmlFileItemReader())
                .processor(drugProcessor)
                .writer(drugWriter)
                .build();
    }

}
