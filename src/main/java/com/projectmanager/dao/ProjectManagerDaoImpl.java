package com.projectmanager.dao;

import com.projectmanager.dto.Manager;
import com.projectmanager.dto.Project;
import com.projectmanager.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProjectManagerDaoImpl implements ProjectManagerDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void addProject(Project project) {
        final String INSERT_PROJECT = "INSERT INTO projects (title, description, status, manager_id) VALUES(?, ?, ?, ?)";

        jdbc.update(INSERT_PROJECT,
                project.getTitle(),
                project.getDescription(),
                project.getStatus().toString(),
                project.getManagerId());
    }

    @Override
    public List<Project> getProjects() {
        final String SELECT_PROJECTS = "SELECT * FROM projects";

        return jdbc.query(SELECT_PROJECTS, new ProjectMapper());
    }

    @Override
    public Project getProjectById(int id) {
        final String SELECT_PROJECT = "SELECT * FROM projects WHERE id = ?";
        Project project = jdbc.queryForObject(SELECT_PROJECT, new ProjectMapper(), id);

        return project;
    }

    @Override
    public List<Manager> getManagers() {
        final String SELECT_MANAGERS = "SELECT * FROM managers";

        return jdbc.query(SELECT_MANAGERS, new ManagerMapper());
    }

    @Override
    public Manager getManagerById(int id) {
        final String SELECT_MANAGER = "SELECT * FROM managers WHERE id = ?";
        Manager manager = jdbc.queryForObject(SELECT_MANAGER, new ManagerMapper(), id);

        return manager;
    }

    @Override
    public void editProject(Project project) {
        final String UPDATE_PROJECT = "UPDATE projects SET title = ?, description = ?, status = ?, manager_id = ? WHERE id = ?";

        jdbc.update(UPDATE_PROJECT,
                project.getTitle(),
                project.getDescription(),
                project.getStatus().toString(),
                project.getManagerId(),
                project.getId());
    }

    @Override
    public void deleteProjectById(int id) {
        final String DELETE_PROJECT = "DELETE FROM projects WHERE id = ?";

        jdbc.update(DELETE_PROJECT, id);
    }

    private static final class ProjectMapper implements RowMapper<Project> {

        @Override
        public Project mapRow(ResultSet rs, int index) throws SQLException {
            Project project = new Project();

            project.setId(rs.getInt("id"));
            project.setTitle(rs.getString("title"));
            project.setDescription(rs.getString("description"));
            project.setStatus(Status.valueOf(rs.getString("status").toLowerCase()));
            project.setManagerId(rs.getInt("manager_id"));

            return project;
        }
    }

    private static final class ManagerMapper implements RowMapper<Manager> {

        @Override
        public Manager mapRow(ResultSet rs, int index) throws SQLException {
            Manager manager = new Manager();

            manager.setId(rs.getInt("id"));
            manager.setFirstName(rs.getString("first_name"));
            manager.setLastName(rs.getString("last_name"));
            manager.setEmail(rs.getString("email"));

            return manager;
        }
    }
}
