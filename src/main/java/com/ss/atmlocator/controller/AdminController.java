package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.parser.scheduler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by roman on 11.11.14.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String ERROR = "Internal error. ";

    @Autowired
    SchcedService service;


    @RequestMapping
    public String adminPage(ModelMap model, Principal user){
        model.addAttribute("active","admin");
        List<JobTemplate> jobs = service.getJobs();
        if (jobs != null){model.addAttribute("jobs",jobs);}
        else {model.addAttribute("error",ERROR);}
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "admin";
    }


    @RequestMapping(value = "/addnew")
    public String addNewJob(ModelMap model, Principal user) {
        model.addAttribute("add","add");
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "jobs";
    }

    @RequestMapping(value = "/{currentJobName}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteJob(@PathVariable("currentJobName") String currentJobName
           ) {
        List<JobTemplate> jobs = service.getJobs();
        for(JobTemplate job : jobs){
            if(job.getJobName().toLowerCase().equals(currentJobName.toLowerCase())){
                    String error = service.removeJob(job);
                    if(error != null){
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{currentJobName}/enable", method = RequestMethod.POST)
      public ResponseEntity<Void> enableJob(@PathVariable("currentJobName") String currentJobName
    ) {
        List<JobTemplate> jobs = service.getJobs();
        for(JobTemplate job : jobs){
            if(job.getJobName().toLowerCase().equals(currentJobName.toLowerCase())){
                String error = service.resumeJob(job);
                if(error != null){
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/edit/{currentJobName}", method = RequestMethod.GET)
    public String editJob(@PathVariable("currentJobName") String currentJobName,
    ModelMap model, Principal user) {
        List<JobTemplate> jobs = service.getJobs();
        for(JobTemplate job : jobs){
            if(job.getJobName().toLowerCase().equals(currentJobName.toLowerCase())){
                JobModel jobModel = new JobModel();
                jobModel.setJobName(job.getJobName());
                jobModel.setJobGroup(job.getJobGroup());
                jobModel.setTriggerName(job.getTriggerName());
                jobModel.setTriggerGroup(job.getTriggerGroup());
                jobModel.setJobClassName(job.getJobClassName());
                jobModel.setCronSched(job.getCronSched());
                jobModel.setParams(service.getStringParam(job.getMap()));
                model.addAttribute("job",jobModel);
                model.addAttribute("edit","edit");
            }
        }
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "jobs";
    }

    @RequestMapping(value = "/{currentJobName}/disable", method = RequestMethod.POST)
    public ResponseEntity<Void> disableJob(@PathVariable("currentJobName") String currentJobName
    ) {
        List<JobTemplate> jobs = service.getJobs();
        for(JobTemplate job : jobs){
            if(job.getJobName().toLowerCase().equals(currentJobName.toLowerCase())){
                String error = service.pauseJob(job);
                if(error != null){
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{currentJobName}/run", method = RequestMethod.POST)
    public ResponseEntity<Void> runJob(@PathVariable("currentJobName") String currentJobName
    ) {
        List<JobTemplate> jobs = service.getJobs();
        for(JobTemplate job : jobs){
            if(job.getJobName().toLowerCase().equals(currentJobName.toLowerCase())){
                String error = service.runJob(job);
                if(error != null){
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @RequestMapping(value = "/save")
    public String saveJob(
            @ModelAttribute("job")JobModel jobModel,
            @RequestParam(value = "currentJobName", required = false) String currentJobName,
            ModelMap modelMap) {


        JobTemplateBuilder jobTemplateBuilder = JobTemplateBuilder.newJob()
                 .withJobName(jobModel.getJobName())
                 .withJobGroup(jobModel.getJobGroup())
                 .withTriggerName(jobModel.getJobGroup())
                 .withTriggerGroup(jobModel.getTriggerGroup())
                 .withCronSched(jobModel.getCronSched())
                 .withJobClass(jobModel.getJobClassName());

        if (jobModel.getParams() != null && !jobModel.getParams().isEmpty() ){
            jobTemplateBuilder.withMap(service.getMapParam(jobModel.getParams()));
        }

        JobTemplate jobTemplate = jobTemplateBuilder.build();


        if(currentJobName == null || currentJobName.isEmpty()){
            List<String> errorList = new LinkedList<>();
            JobTrigerHolder jobHolder = CreateJobFactory.createJob(jobTemplate, errorList);

            if(jobHolder == null){
                modelMap.addAttribute("add", "add");
                modelMap.addAttribute("job",jobModel);
                modelMap.addAttribute("error",ERROR+errorList.get(0));
                return "jobs";
            }

            service.addJob(jobHolder);
            return "redirect:/admin";
        }

        List<JobTemplate> jobs = service.getJobs();
        List<String> errorList = new LinkedList<>();
        for(JobTemplate job : jobs){
                if(job.getJobName().toLowerCase().equals(currentJobName.toLowerCase())){
                    JobTrigerHolder jobHolder = CreateJobFactory.createJob(jobTemplate,errorList);
                    if(jobHolder != null){
                        service.removeJob(jobTemplate);
                        service.addJob(jobHolder);
                        return "redirect:/admin";
                    }
                }
        }

        modelMap.addAttribute("add","add");
        modelMap.addAttribute("job",jobModel);
        modelMap.addAttribute("error",ERROR+errorList.get(0));
        return "jobs";
    }


    @RequestMapping(value = "/users")
    public String adminUsers(ModelMap model, Principal user) {
        model.addAttribute("active", "adminUsers");
        model.addAttribute("userName", user.getName());
        return "adminUsers";
    }
}
