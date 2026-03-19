package com.EasyBuy.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class AssetRequest {

	private String productName;
	private LocalDate purchaseDate;
	private Integer quantity;
	private BigDecimal unitPrice;

}
