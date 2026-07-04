package com.tri.evre.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.notice.model.dto.NoticeDto;

@Mapper
public interface NoticeMapper {

	List<NoticeDto> findAll(PageInfo pageInfo);

}
