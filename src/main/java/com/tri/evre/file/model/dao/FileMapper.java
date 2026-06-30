package com.tri.evre.file.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tri.evre.file.model.dto.FileDto;
import com.tri.evre.file.model.vo.File;


@Mapper
public interface FileMapper {
	
	
	
	@Insert("""
				INSERT 
				  INTO
				 		BOARD_FILE
				VALUES
						(
						#{filePath}
					 ,	#{fileOrder}
					 ,	#{originalName}
					 ,	#{boardNo}
					 	)
			""")
	int save(File file);

	@Select("""
				SELECT
				  		FILE_PATH
				  	 ,	ORIGINAL_NAME
				  	 ,	FILE_ORDER
				  FROM
				  		BOARD_FILE
				 WHERE
				 		BOARD_NO = #{boardNo}
			""")
	List<FileDto> findAll(Long boardNo);

	
	@Update("""
				UPDATE
						BOARD_FILE
				   SET
				   		FILE_PATH = #{filePath}
				   	 ,	ORIGINAL_NAME = #{originalName}
				 WHERE
				 		BOARD_NO = #{boardNo}
				   AND
				   		FILE_ORDER = #{fileOrder}
			""")
	int update(File fileEntity);

	@Select("""
				SELECT
						COUNT(*)
				  FROM
				  	   BOARD_FILE
				 WHERE
				 		BOARD_NO = #{boardNo}
			""")
	int fileCounts(Long boardNo);

	@Delete("""
				DELETE
				  FROM
				  	   BOARD_FILE
				 WHERE
				 	   BOARD_NO = #{boardNo}
				   AND
				   	   FILE_ORDER > #{order}
			""")
	void deleteFile(@Param(value="boardNo") Long boardNo, @Param(value="order") int order);

	
	
}
