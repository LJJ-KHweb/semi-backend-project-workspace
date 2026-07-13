package com.tri.evre.product.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.product.model.dto.UpdateProductDto;
import com.tri.evre.product.model.vo.Product;

@Mapper
public interface ProductMapper {
	
	
	int insertProductTable(Product product);
	
	int insertInventoryTable(@Param("product")Product product
						   ,@Param("filePath") String filePath);

	int deleteProduct(Long productNo);

	int updateProduct(@Param("productNo") Long productNo, @Param("product") UpdateProductDto product);

	int updateInventory(@Param("productNo") Long productNo,@Param("amount") Integer amount,@Param("file") String file);


}
