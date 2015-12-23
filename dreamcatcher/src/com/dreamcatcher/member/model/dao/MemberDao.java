package com.dreamcatcher.member.model.dao;

import java.util.Map;

import com.dreamcatcher.member.model.MemberDto;

public interface MemberDao {
	public int idCheck(String id);
	public int join(MemberDto memberDto);
	public MemberDto login(Map<String,String> validMap);
	public int modifyInfo(MemberDto memberDto);
	public int resetPassword(MemberDto memberDto);
	public MemberDto getMemberInfo(String id);	
	public int passwordCheck(Map<String, String> validMap);

}
