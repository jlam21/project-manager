package com.projectmanager.dao;

import com.projectmanager.TestApplicationConfiguration;
import com.projectmanager.dto.Manager;
import com.projectmanager.dto.Project;
import com.projectmanager.dto.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class ProjectManagerDaoImplTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private ProjectManagerDao dao;

    @BeforeEach
    public void setUp() {
        jdbc.update("DELETE FROM projects;");
    }

    @Test
    public void testAddProjectAndGetProjectById() {
        Project expectedProject = new Project();

        expectedProject.setTitle("Test Title");
        expectedProject.setDescription("Test Description");
        expectedProject.setStatus(Status.active);
        expectedProject.setManagerId(1);

        // Add project to the database and set the id of the project to the id last inserted
        dao.addProject(expectedProject);
        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        expectedProject.setId(id);

        // Get the project by expectedProject's id
        Project actualProject = dao.getProjectById(expectedProject.getId());

        assertEquals(expectedProject, actualProject);
    }

    @Test
    public void testGetProjects() {
        // Add first project
        Project expectedProject1 = new Project();
        expectedProject1.setTitle("Test Title 1");
        expectedProject1.setDescription("Test Description 1");
        expectedProject1.setStatus(Status.active);
        expectedProject1.setManagerId(1);

        dao.addProject(expectedProject1);
        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        expectedProject1.setId(id);

        // Add second project
        Project expectedProject2 = new Project();
        expectedProject2.setTitle("Test Title 2");
        expectedProject2.setDescription("Test Description 2");
        expectedProject2.setStatus(Status.active);
        expectedProject2.setManagerId(1);

        dao.addProject(expectedProject2);
        id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        expectedProject2.setId(id);

        // Get a list of all projects and check that the size is 2
        List<Project> projectList = dao.getProjects();
        assertEquals(2, projectList.size());

        // Check that the added projects are in the list
        assertTrue(projectList.contains(expectedProject1));
        assertTrue(projectList.contains(expectedProject2));
    }

    @Test
    public void testGetManagers() {
        Manager expectedManager1 = new Manager();
        expectedManager1.setId(1);
        expectedManager1.setFirstName("Tony");
        expectedManager1.setLastName("Stark");
        expectedManager1.setEmail("tonystark@gmail.com");

        Manager expectedManager2 = new Manager();
        expectedManager2.setId(2);
        expectedManager2.setFirstName("Bruce");
        expectedManager2.setLastName("Wayne");
        expectedManager2.setEmail("brucewayne@gmail.com");

        // Get a list of all managers and check that the size is 2
        List<Manager> managerList = dao.getManagers();
        assertEquals(2, managerList.size());

        // Check that the managers are in the list
        assertTrue(managerList.contains(expectedManager1));
        assertTrue(managerList.contains(expectedManager2));
    }

    @Test
    public void testGetManagerById() {
        Manager expectedManager1 = new Manager();
        expectedManager1.setId(1);
        expectedManager1.setFirstName("Tony");
        expectedManager1.setLastName("Stark");
        expectedManager1.setEmail("tonystark@gmail.com");

        Manager expectedManager2 = new Manager();
        expectedManager2.setId(2);
        expectedManager2.setFirstName("Bruce");
        expectedManager2.setLastName("Wayne");
        expectedManager2.setEmail("brucewayne@gmail.com");

        // Get managers by id
        Manager actualManager1 = dao.getManagerById(expectedManager1.getId());
        Manager actualManager2 = dao.getManagerById(expectedManager2.getId());

        assertEquals(expectedManager1, actualManager1);
        assertEquals(expectedManager2, actualManager2);
    }

    @Test
    public void testEditProject() {
        // Add project
        Project expectedProject = new Project();
        expectedProject.setTitle("Test Title");
        expectedProject.setDescription("Test Description");
        expectedProject.setStatus(Status.active);
        expectedProject.setManagerId(1);

        dao.addProject(expectedProject);
        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        expectedProject.setId(id);

        // Update the project
        expectedProject.setTitle("Test Title Edit");
        expectedProject.setDescription("Test Description Edit");
        expectedProject.setStatus(Status.completed);
        expectedProject.setManagerId(2);

        dao.editProject(expectedProject);

        // Get the project by expectedProject's id and check that the project received is updated
        Project actualProject = dao.getProjectById(expectedProject.getId());

        assertEquals(expectedProject, actualProject);
    }

    @Test
    public void testDeleteProjectById() {
        // Add first project
        Project expectedProject1 = new Project();
        expectedProject1.setTitle("Test Title 1");
        expectedProject1.setDescription("Test Description 1");
        expectedProject1.setStatus(Status.active);
        expectedProject1.setManagerId(1);

        dao.addProject(expectedProject1);
        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        expectedProject1.setId(id);

        // Add second project
        Project expectedProject2 = new Project();
        expectedProject2.setTitle("Test Title 2");
        expectedProject2.setDescription("Test Description 2");
        expectedProject2.setStatus(Status.active);
        expectedProject2.setManagerId(1);

        dao.addProject(expectedProject2);
        id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        expectedProject2.setId(id);

        // Delete first project
        dao.deleteProjectById(expectedProject1.getId());

        // Get a list of all projects and check that the size is 1
        List<Project> projectList = dao.getProjects();
        assertEquals(1, projectList.size());

        // Check that the first project is not in the list and the second project is
        assertFalse(projectList.contains(expectedProject1));
        assertTrue(projectList.contains(expectedProject2));
    }
}
