package com.EasyBuy.Controller;

import com.EasyBuy.DTO.ProjectRequest;
import com.EasyBuy.Entity.Project;
import com.EasyBuy.Service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

	private final ProjectService projectService;

	@PostMapping("/create-project")
	public String createProject(@RequestBody ProjectRequest request) {
		log.info("Inside Create project.. ");
		return projectService.createProject(request);
	}

	@GetMapping("/fetch-projects")
	public List<Project> getAllProjects() {
		log.info("Inside fetch-projects .. ");
		return projectService.getAllProjects();
	}

	@GetMapping("find-project-by-id/{id}")
	public Project getProjectById(@PathVariable Long id) {
		log.info("Inside getProjectById.. ");
		return projectService.getProjectById(id);
	}

	@PutMapping("update-project/{id}")
	public String updateProjectById(@PathVariable Long id, @RequestBody ProjectRequest request) {
		log.info("Inside updateProjectById.. ");
		return projectService.updateProjectById(id, request);
	}

	@DeleteMapping("delete-project/{id}")
	public String deleteProjectById(@PathVariable Long id) {
		log.info("Inside getProjectById.. ");
		return projectService.deleteProjectById(id);
	}
}
