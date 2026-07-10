package com.tri.evre.rank.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tri.evre.common.model.dto.PageInfo;
import com.tri.evre.rank.model.dto.RankDto;

@Mapper
public interface RankMapper {

	@Select("""
						SELECT
			       RANKING,
			       USER_ID,
			       USER_NAME,
			       CARBON_SUM,
			       DISTANCE_SUM
			FROM (
			        SELECT
			               DENSE_RANK() OVER(ORDER BY CARBON_SUM DESC) AS RANKING,
			               USER_ID,
			               USER_NAME,
			               CARBON_SUM,
			               DISTANCE_SUM
			        FROM (
			                SELECT
			                       S.USER_ID,
			                       S.USER_NAME,
			                       NVL(
			                           SUM(C.DISTANC_SUM)
			                           * (
			                               0.15 -
			                               (
			                                   0.46 /
			                                   (SUM(C.DISTANC_SUM) / SUM(C.KILOWATT_SUM))
			                               )
			                           ),
			                           0
			                       ) AS CARBON_SUM,
			                       NVL(SUM(C.DISTANC_SUM), 0) AS DISTANCE_SUM
			                FROM SEMI_USER S
			                LEFT JOIN CAR_HISTORY C
			                       ON S.USER_ID = C.USER_ID
			                      AND C.CREATE_DATE BETWEEN SYSDATE - 6 AND SYSDATE
			                GROUP BY
			                       S.USER_ID,
			                       S.USER_NAME
			             )
			     )
			ORDER BY RANKING
						""")
	List<RankDto> findByUserRanking(PageInfo pageInfo);

	@Select("""
			SELECT
			       RANKING,
			       USER_ID,
			       USER_NAME,
			       CARBON_SUM,
			       DISTANCE_SUM
			FROM (
			        SELECT
			               DENSE_RANK() OVER(ORDER BY CARBON_SUM DESC) AS RANKING
			             , USER_ID
			             , USER_NAME
			             , CARBON_SUM
			             , DISTANCE_SUM
			        FROM (
			                SELECT
			                       S.USER_ID
			                     , S.USER_NAME
			                     , NVL(
			                           SUM(C.DISTANC_SUM)
			                           * (
			                               0.15 -
			                               (
			                                   0.46 /
			                                   (SUM(C.DISTANC_SUM) / SUM(C.KILOWATT_SUM))
			                               )
			                             )
			                         , 0) AS CARBON_SUM
			                     , NVL(SUM(C.DISTANC_SUM), 0) AS DISTANCE_SUM
			                FROM SEMI_USER S
			                LEFT JOIN CAR_HISTORY C
			                       ON S.USER_ID = C.USER_ID
			                      AND C.CREATE_DATE BETWEEN SYSDATE - 6 AND SYSDATE
			                GROUP BY S.USER_ID,
			                		 S.USER_NAME
			             )
			     )
			WHERE USER_ID = #{userId}
			""")
	RankDto findMyRank(String userId);
}
