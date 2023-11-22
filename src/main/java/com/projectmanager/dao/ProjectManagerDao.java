package com.projectmanager.dao;

import com.projectmanager.dto.Manager;
import com.projectmanager.dto.Project;

import java.util.List;

public interface ProjectManagerDao {

    void addProject(Project project);

    List<Project> getProjects();

    Project getProjectById(int id);

    List<Manager> getManagers();

    Manager getManagerById(int id);

    void editProject(Project project);

    void deleteProjectById(int id);
}
