package com.EasyBuy.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EasyBuy.Entity.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {
	List<Asset> findByTeamId(Long teamId);

	List<Asset> findByOwnerUserId(Long ownerUserId);
}
