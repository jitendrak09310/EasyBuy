package com.EasyBuy.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyBuy.Entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findByTeamId(Long teamId);

	List<Project> findByOwnerUserId(Long ownerUserId);
}
