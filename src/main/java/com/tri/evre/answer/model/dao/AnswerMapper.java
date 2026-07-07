package com.tri.evre.answer.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.tri.evre.answer.model.vo.Answer;

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
						  , #{requireNo}
						  , #{userId}
						  , 'Y'
						)
						
			""")
	int insertAnswer(Answer answer);
	
}
