package com.tri.evre.answer.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tri.evre.answer.model.vo.Answer;
import com.tri.evre.answer.model.vo.ResponseAnswer;

@Mapper
public interface AnswerMapper {
	
	
	@Insert("""
				INSERT
					INTO
						ANSWER
						(
							ANSWER_NO
						  , ANSWER_CONTENT
						  , REQUIRE_NO
						  , USER_ID
						  , STATUS
						)
						VALUES
						(
							SEQ_ANS.NEXTVAL
						  , #{answerContent}
						  , #{requiredNo}
						  , #{userId}
						  , 'Y'
						)
						
			""")
	int insertAnswer(Answer answer);

	
	@Select("""
				SELECT
						ANSWER_NO
					  , ANSWER_CONTENT
					  , USER_ID
					FROM
						ANSWER
					WHERE
						REQUIRE_NO = #{requireNo}
					AND 
						STATUS='Y'
					ORDER
						BY
							ANSWER_NO DESC
			""")
	List<ResponseAnswer> findAllAnswerByRequireNo(Long requireNo);
	
	
	@Select("""
			SELECT
					ANSWER_NO
				  , ANSWER_CONTENT
				  , USER_ID
				FROM
					ANSWER
				WHERE
					REQUIRE_NO = #{requireNo}
				ORDER
					BY
						ANSWER_NO DESC
		""")
List<ResponseAnswer> findAllAnswerByRequireNoAdmin(Long requireNo);
	
	
	
	
	
	
}
