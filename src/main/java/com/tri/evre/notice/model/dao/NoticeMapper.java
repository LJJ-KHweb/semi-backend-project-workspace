package com.tri.evre.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.notice.model.dto.NoticeDeleteDto;
import com.tri.evre.notice.model.dto.NoticeDto;
import com.tri.evre.notice.model.vo.Notice;

@Mapper
public interface NoticeMapper {

	// 공지사항 작성
	int save(Notice noticeEntity);

	// 공지사항 전체조회
	List<NoticeDto> findAll(PageInfo pageInfo);

	// 공지사항 상세 조회
	NoticeDto findByNoticeNo(Long noticeNo);
	
	// 공지사항 일반게시판에 공유하는 게시글 조회
	List<NoticeDto> findPublicNotice();

	// 공지사항 수정
	int update(Notice notice);

	// 공지사항 삭제
	int delete(NoticeDeleteDto notice);

	// 공지사항 전체 갯수 조회
	int findNoticesCount();

	// 공지사항 조회수 증가
	int plusViews(Long noticeNo);

	// 공지사항 존재여부 확인
	// Integer로 반환타입을 지정한 이유는 null이 반환될때도 대비해서 설정함
	Integer existsByNoticeNo(Long noticeNo);

	
	

}
