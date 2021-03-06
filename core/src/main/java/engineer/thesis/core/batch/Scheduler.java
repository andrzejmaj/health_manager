package engineer.thesis.core.batch;

import engineer.thesis.core.repository.DrugRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Scheduler {

    @Autowired
    private JobLauncher jobLauncher;


    @Autowired
    private DrugProcessor drugProcessor;

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    @Qualifier("importDrugs")
    private Job importDrugJob;


    @Scheduled(cron = "0 0 1 * * 7")
    public void importDrugs() {
        try {
            drugProcessor.setExistingDrugs(drugRepository.findAll());
            jobLauncher.run(importDrugJob, new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobParametersInvalidException | JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        }
    }

}

