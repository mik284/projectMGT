package com.company.projectmgt.app;

import com.company.projectmgt.entity.Project;
import com.company.projectmgt.entity.ProjectStats;
import com.company.projectmgt.entity.Task;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProjectStatsService {
    private final DataManager dataManager;

    public ProjectStatsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<ProjectStats> fetchProjectStatistics(){
        List<Project> projects = dataManager.load(Project.class).all().list();

       List<ProjectStats> projectStats = projects.stream().map(project -> {
            ProjectStats stats = dataManager.create(ProjectStats.class);
            stats.setId(project.getId());
            stats.setProjectName(project.getName());
            stats.setTasksCount(project.getTasks().size());
            stats.setActualEfforts(getActualEfforts(project.getId()));
           Integer plannedEfforts = project.getTasks().stream().map(Task::getEstimation).reduce(0, Integer::sum);
           stats.setPlannedEfforts(plannedEfforts);
           return stats;
        }).collect(Collectors.toList());

        return projectStats;
    }
    
    public Integer getActualEfforts(UUID projectId){
        return dataManager.loadValue("select SUM(te.timeSpent) from TimeEntry te " +
                "where te.task.project.id = :projectId", Integer.class)
                .parameter("projectId", projectId)
                .one();
    }
}