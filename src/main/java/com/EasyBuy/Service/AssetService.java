package com.EasyBuy.Service;

import com.EasyBuy.DTO.AssetRequest;
import com.EasyBuy.Entity.Asset;
import com.EasyBuy.Entity.User;
import com.EasyBuy.Repositories.AssetRepository;
import com.EasyBuy.Utility.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetService {

	private final AssetRepository assetRepository;

	private User getCurrentUser() {
		log.info("Inside AssetService getCurrentUser .. ");
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public String createAsset(AssetRequest request) {
		log.info("Inside AssetService createAsset .. ");
		User user = getCurrentUser();

		Asset asset = new Asset();
		asset.setProductName(request.getProductName());
		asset.setPurchaseDate(request.getPurchaseDate());
		asset.setQuantity(request.getQuantity());
		asset.setUnitPrice(request.getUnitPrice());

		asset.setOwnerUserId(user.getId());
		asset.setTeamId(user.getTeamId());

		assetRepository.save(asset);

		return "Asset created successfully";
	}

	public List<Asset> getAllAssets() {
		log.info("Inside AssetService getAllAssets .. ");

		User user = getCurrentUser();

		if (user.getRole() == Role.ADMIN) {
			return assetRepository.findAll();
		}

		if (user.getRole() == Role.MANAGER) {
			return assetRepository.findByTeamId(user.getTeamId());
		}

		return assetRepository.findByOwnerUserId(user.getId());
	}

	public Asset getAssetById(Long id) {
		log.info("Inside AssetService getAssetById .. ");

		Asset asset = assetRepository.findById(id).orElseThrow(() -> new RuntimeException("Asset not found"));

		validateAccess(asset);

		return asset;
	}

	public String updateAssetById(Long id, AssetRequest request) {
		log.info("Inside AssetService updateAssetById .. ");

		Asset asset = getAssetById(id);

		if (request.getProductName() != null) {
			asset.setProductName(request.getProductName());
		}

		if (request.getPurchaseDate() != null) {
			asset.setPurchaseDate(request.getPurchaseDate());
		}

		if (request.getQuantity() != null) {
			asset.setQuantity(request.getQuantity());
		}

		if (request.getUnitPrice() != null) {
			asset.setUnitPrice(request.getUnitPrice());
		}

		assetRepository.save(asset);

		return "Asset updated successfully";
	}

	public String deleteAssetById(Long id) {
		log.info("Inside AssetService deleteAssetById .. ");

		Asset asset = getAssetById(id);
		assetRepository.delete(asset);
		return "Asset deleted successfully";
	}

	private void validateAccess(Asset asset) {
		log.info("Inside AssetService validateAccess .. ");

		User user = getCurrentUser();
		if (user.getRole() == Role.ADMIN)
			return;

		if (user.getRole() == Role.MANAGER && asset.getTeamId().equals(user.getTeamId()))
			return;

		if (user.getRole() == Role.USER && asset.getOwnerUserId().equals(user.getId()))
			return;

		throw new AccessDeniedException("Unauthorized access");
	}
}
