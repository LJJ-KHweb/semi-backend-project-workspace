package com.tri.evre.rank.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.rank.model.rank.RankDto;

@Mapper
public interface RankMapper {

	@Select("""
			
			""")
	List<RankDto> findByUserRanking(PageInfo pageInfo);

}
