package com.company.projectmgt.app;

import com.company.projectmgt.entity.ProjectStats;

import java.util.List;

public interface ProjectStatsService {
    List<ProjectStats> fetchProjectStatistics();
}
