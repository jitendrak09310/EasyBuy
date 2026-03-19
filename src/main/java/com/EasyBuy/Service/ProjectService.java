package com.EasyBuy.Service;

import com.EasyBuy.DTO.ProjectRequest;
import com.EasyBuy.Entity.Project;
import com.EasyBuy.Entity.User;
import com.EasyBuy.Repositories.ProjectRepository;
import com.EasyBuy.Utility.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

	private final ProjectRepository projectRepository;

	private User getCurrentUser() {
		log.info("Inside ProjectService getCurrentUser .. ");
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public String createProject(ProjectRequest request) {
		log.info("Inside ProjectService createProject .. ");

		User user = getCurrentUser();

		Project project = new Project();
		project.setName(request.getName());
		project.setDescription(request.getDescription());
		project.setCreatedAt(LocalDateTime.now());

		project.setOwnerUserId(user.getId());
		project.setTeamId(user.getTeamId());

		projectRepository.save(project);

		return "Project created successfully";
	}

	public List<Project> getAllProjects() {
		log.info("Inside ProjectService getAllProjects .. ");
		User user = getCurrentUser();

		if (user.getRole() == Role.ADMIN) {
			return projectRepository.findAll();
		}

		if (user.getRole() == Role.MANAGER) {
			return projectRepository.findByTeamId(user.getTeamId());
		}

		return projectRepository.findByOwnerUserId(user.getId());
	}

	public Project getProjectById(Long id) {
		log.info("Inside ProjectService getProjectById .. ");

		Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

		validateAccess(project);

		return project;
	}

	public String updateProjectById(Long id, ProjectRequest request) {
		log.info("Inside ProjectService updateProjectById .. ");

		Project project = getProjectById(id);

		if (request.getName() != null) {
			project.setName(request.getName());
		}

		if (request.getDescription() != null) {
			project.setDescription(request.getDescription());
		}

		projectRepository.save(project);

		return "Project updated successfully";
	}

	public String deleteProjectById(Long id) {
		log.info("Inside ProjectService deleteProjectById .. ");
		Project project = getProjectById(id);

		projectRepository.delete(project);

		return "Project deleted successfully";
	}

	private void validateAccess(Project project) {
		log.info("Inside ProjectService validateAccess .. ");
		User user = getCurrentUser();

		if (user.getRole() == Role.ADMIN)
			return;

		if (user.getRole() == Role.MANAGER && project.getTeamId().equals(user.getTeamId()))
			return;

		if (user.getRole() == Role.USER && project.getOwnerUserId().equals(user.getId()))
			return;

		throw new AccessDeniedException("Unauthorized access");
	}
}