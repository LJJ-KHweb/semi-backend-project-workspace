package com.tri.evre.rank.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.rank.model.dao.RankMapper;
import com.tri.evre.rank.model.dto.RankResponseDto;
import com.tri.evre.user.model.dao.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankService {
	
	private final RankMapper rankMapper;
	private final UserMapper userMapper;
		
	@Transactional
	public RankResponseDto findByUserRanking(PageInfo pageInfo, String userId) {
		
		pageInfo.setBoardCounts(userMapper.sumUsers());
		
		return RankResponseDto.builder().pageInfo(pageInfo)
										.ranks(rankMapper.findByUserRanking(pageInfo))
										.myRank(rankMapper.findMyRank(userId))
										.build();
	}

}
