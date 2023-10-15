package com.study.mapper;

import com.study.model.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {

    //reply 불러
    public List<ReplyDTO> getReplyList(@Param("boardNum") int boardNum);

    //reply 저장
    public void updateReply(@Param("boardNum") int boardNum, @Param("content") String content);

    //reply 삭제
    public void deleteReply(@Param("boardNum") int boardNum);
}
