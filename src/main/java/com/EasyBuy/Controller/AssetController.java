package com.EasyBuy.Controller;

import com.EasyBuy.DTO.AssetRequest;
import com.EasyBuy.Entity.Asset;
import com.EasyBuy.Service.AssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
@Slf4j
public class AssetController {

	private final AssetService assetService;

	@PostMapping("/create-asset")
	public String createAsset(@RequestBody AssetRequest request) {
		log.info("Inside createAsset ...");
		return assetService.createAsset(request);
	}

	@GetMapping("/getAllAssets")
	public List<Asset> getAllAssets() {
		log.info("Inside getAll ...");
		return assetService.getAllAssets();
	}

	@GetMapping("getAssetById/{id}")
	public Asset getAssetById(@PathVariable Long id) {
		log.info("Inside getAssetById ...");
		return assetService.getAssetById(id);
	}

	@PutMapping("update-asset/{id}")
	public String updateAssetById(@PathVariable Long id, @RequestBody AssetRequest request) {
		log.info("Inside updateAssetById ...");
		return assetService.updateAssetById(id, request);
	}

	@DeleteMapping("delete-asset/{id}")
	public String deleteAssetById(@PathVariable Long id) {
		log.info("Inside deleteAssetById ...");
		return assetService.deleteAssetById(id);
	}
}
