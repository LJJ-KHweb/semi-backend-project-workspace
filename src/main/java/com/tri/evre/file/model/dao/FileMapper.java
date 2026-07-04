package com.tri.evre.file.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tri.evre.file.model.dto.FileDto;
import com.tri.evre.file.model.vo.BoardFile;
import com.tri.evre.file.model.vo.NoticeFile;


@Mapper
public interface FileMapper {
	
	//=================일반게시판========================//
	
	// 일반 게시판 파일 추가(등록)
	int saveBoardFile(BoardFile file);

	//일반 게시판 파일 전체 조회
	List<FileDto> findBoardFileAll(Long boardNo);

	// 일반 게시판 파일 수정
	int updateBoardFile(BoardFile fileEntity);

	// 일반 게시판 파일 삭제
	int deleteBoardFile(@Param(value="boardNo") Long boardNo, @Param(value="order") int order);

	// 일반 게시판 한 게시글의 파일의 총 갯수
	int findBoardFileCounts(Long boardNo);

	//=====================공지사항============================//
	
	//공지사항 파일 추가(등록)
	int saveNoticeFile(NoticeFile file);
	
	//공지사항 파일 전체 조회
	List<FileDto> findNoticeFileAll(Long boardNo);

	//공지사항 파일 수정
	int updateNoticeFile(NoticeFile file);

	//공지사항 파일 삭제
	int deleteNoticeFile(Long boardNo, int maxOrder);

	//공지사항 한 게시글의 파일의 총 갯수
	int findNoticeFileCounts(Long boardNo);

	
	
}
