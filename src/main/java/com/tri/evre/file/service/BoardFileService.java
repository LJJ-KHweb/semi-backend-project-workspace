package com.tri.evre.file.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tri.evre.file.model.dao.FileMapper;
import com.tri.evre.file.model.dto.FileDto;
import com.tri.evre.file.model.vo.BoardFile;
import com.tri.evre.global.exception.board.file.BoardFileCreateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardFileService implements FileManagementService {

	private final FileStorageService fileStorageService;
	private final FileMapper fileMapper;

	@Override
	public void saveFile(List<MultipartFile> files, Long boardNo) {
		if(files == null || files.isEmpty()) {
			return;
		}
		int count = 1;
		for(MultipartFile file : files) {
			if(count > 5) {
				throw new BoardFileCreateException("파일이 너무 많습니다.");
			}
			String filePath = fileStorageService.store(file);
			BoardFile fileEntity = BoardFile.builder()
											.filePath(filePath)
											.fileOrder(count)
											.originalName(file.getOriginalFilename())
											.boardNo(boardNo)
											.build();
			saveBoardFile(fileEntity);
			count++;
		}
	}

	@Override
	public List<FileDto> findAll(Long boardNo) {
		// countBoardFiles(boardNo);
		return findBoardFiles(boardNo);
	
	}
	// MAX FILE_ORDER 조회하는메소드를 따로 구현하고
	// 새파일이 들어올시 MAX FILE_ORDER+1로 ORDER를 지정해준다.
	/*
	 * 	DELETE 
	 *    FROM
	 *    		BOARD_FILE
	 *  WHERE
	 *  		BOARD_NO = #{boardNo}
	 *    AND
	 *    		FILE_ORDER != #{order1}
	 *     OR
	 *    		FILE_ORDER != #{order2}
	 * 
	 */
	// FOR문을 돌려서 existingFiles.size() 만큼 변수 선언후 
	
	@Override
	public void updateFile(List<MultipartFile> files, List<Integer> deleteOrder, Long boardNo) {
	
		// 앞단에서 삭제한 파일 DB에서 삭제
		if (deleteOrder != null && !deleteOrder.isEmpty()) {
			for(Integer order : deleteOrder) {
				fileMapper.deleteBoardFile(boardNo, order);
			}
		}
		
		Integer maxOrder = fileMapper.findBoardFileCounts(boardNo);
		int count = 0;
		if(maxOrder != null) {
			count = maxOrder+1;
		}
		if(files != null&& !files.isEmpty()) {
			if(files.size() > 0) {
				for(MultipartFile file : files) {
					String filePath = fileStorageService.store(file);
					BoardFile fileEntity = BoardFile.builder()
							.filePath(filePath)
							.fileOrder(count++)
							.originalName(file.getOriginalFilename())
							.boardNo(boardNo)
							.build();
					saveBoardFile(fileEntity);
				}
			}
		}
		
	
		
		
		//현재 보드에 몇개의 파일이 저장되어있는지 확인
		
		/*
		 * if(existingFiles.size() < maxOrder) { int count = existingFiles.size(); for(;
		 * count <= maxOrder; count++) {
		 * 
		 * fileMapper.deleteBoardFile(boardNo, count+1); } }
		 */
		
		/*
		 * int boardFileCounts = countBoardFiles(boardNo);
		 * 
		 * 
		 * int count = existingFiles.isEmpty() ? existingFiles.size(): 0; if(count != 0)
		 * { for(FileDto file : existingFiles) { updateBoardFile(existingFiles); } }
		 * if(files == null || files.isEmpty()) { return; } for(MultipartFile file :
		 * files) { if(count >5) { throw new BoardFileCreateException("파일이 너무 많습니다."); }
		 * String filePath = fileStorageService.store(file); BoardFile fileEntity =
		 * BoardFile.builder() .filePath(filePath) .fileOrder(count)
		 * .originalName(file.getOriginalFilename()) .boardNo(boardNo) .build();
		 * 
		 * count++; } // 한 게시글의 저장되어있는 파일의 수가 업데이트의 파일수보다 크다면 더있는 파일들을 삭제해줌
		 * if(boardFileCounts > (count-1)) { deleteBoardFiles(boardNo, count-1); }
		 */
	}
	
	
	// Board 게시글의 BoardType에 맞는 테이블에 저장해주는 메소드 
	private void saveBoardFile( BoardFile file) {
		int result = fileMapper.saveBoardFile(file);
		
		if(result < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
	}
	
	// Board 게시글의 update를 해주는 메소드
	private void updateBoardFile(BoardFile file) {
		int result = fileMapper.updateBoardFile(file);
		
		if(result < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
	}
	
	// Board 게시글의 delete를 해주는 메소드
	private void deleteBoardFiles(Long boardNo, int maxOrder) {
		int result =  fileMapper.deleteBoardFile(boardNo, maxOrder);
			
		if(result < 1) {
			throw new BoardFileCreateException("파일 삭제에 실패했습니다.");
		}
	}
	
	// Board 게시글의 file이 있는지 없는지 검증
	private int  countBoardFiles(Long boardNo) {
		int boardFileCounts =  fileMapper.findBoardFileCounts(boardNo);
		
		if(boardFileCounts < 1) {
			throw new BoardFileCreateException("파일이 이상합니다.");
		}
		return boardFileCounts;
	}
	// Board 게시글의 파일 전체 조회
	private List<FileDto> findBoardFiles(Long boardNo) {
		List<FileDto> files= fileMapper.findBoardFileAll(boardNo);
			
//		if(files.isEmpty()) {
//			throw new BoardFileCreateException("파일을 조회하지 못했습니다.");
//		}
		return files;
		
	}
	
	
	
	@Override
	public void deleteFile(Long noticeNo) {
		//삭제 할때 구현함
		
	}
}
