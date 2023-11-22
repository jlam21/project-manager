package com.projectmanager.controller;

import com.projectmanager.dao.ProjectManagerDao;
import com.projectmanager.dto.Manager;
import com.projectmanager.dto.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("managerList")
public class ProjectManagerController {

    @Autowired
    private ProjectManagerDao dao;

    @GetMapping("/")
    public String displayHome(Model model) {
        List<Project> projectList = dao.getProjects();

        model.addAttribute("projectList", projectList);

        return "index";
    }

    @GetMapping("addProject")
    public String addProject(Model model, Project project) {
        List<Manager> managerList = dao.getManagers();

        model.addAttribute("managerList", managerList);

        return "addProject";
    }

    @PostMapping("addProject")
    public String performAddProject(@Valid Project project, BindingResult bindingResult, @SessionAttribute("managerList") List<Manager> managerList, SessionStatus sessionStatus, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("managerList", managerList);
            return "addProject";
        }
        sessionStatus.setComplete();

        dao.addProject(project);

        return "redirect:/";
    }

    @GetMapping("displayProject")
    public String displayProject(Integer id, Model model) {
        Project project = dao.getProjectById(id);
        Manager manager = dao.getManagerById(project.getManagerId());

        model.addAttribute("project", project);
        model.addAttribute("manager", manager);

        return "displayProject";
    }

    @GetMapping("editProject")
    public String editProject(Integer id, Model model) {
        Project project = dao.getProjectById(id);
        List<Manager> managerList = dao.getManagers();

        model.addAttribute("project", project);
        model.addAttribute("managerList", managerList);

        return "editProject";
    }

    @PostMapping("editProject")
    public String performEditProject(@Valid Project project, BindingResult bindingResult, @SessionAttribute("managerList") List<Manager> managerList, SessionStatus sessionStatus, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("managerList", managerList);
            return "editProject";
        }
        sessionStatus.setComplete();

        dao.editProject(project);

        return "redirect:/";
    }

    @GetMapping("deleteProject")
    public String deleteProject(Integer id) {
        dao.deleteProjectById(id);

        return "redirect:/";
    }
}
