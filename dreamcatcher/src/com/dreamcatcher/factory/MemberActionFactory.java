package com.dreamcatcher.factory;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.member.action.*;

public class MemberActionFactory {
	
	private static Action memberJoinAction;
	private static Action memberMyInfoAction;
	private static Action memberModifyAction;
	private static Action memberLoginAction;
	private static Action memberLogoutAction;
	private static Action memberIdCheckAction;
	private static Action memberResetPasswordAction;
	private static Action memberPasswordCheckAction;
	private static Action memberContactAction;
	private static Action memberLoginWithOAuthAction;
	private static Action memberLoginWithTwitterAction;
	private static Action memberLoginWithTwitterCallbackAction;

	static{	
		memberJoinAction = new MemberJoinAction();
		memberMyInfoAction = new MemberMyInfoAction();
		memberModifyAction = new MemberModifyAction();
		memberLoginAction = new MemberLoginAction();
		memberLogoutAction = new MemberLogoutAction();
		memberIdCheckAction = new MemberIdCheckAction();
		memberResetPasswordAction = new MemberResetPasswordAction();
		memberPasswordCheckAction = new MemberPasswordCheckAction();
		memberContactAction = new MemberContactAction();
		memberLoginWithOAuthAction = new MemberLoginWithOAuthAction();
		memberLoginWithTwitterAction = new MemberLoginWithTwitterAction();
		memberLoginWithTwitterCallbackAction = new MemberLoginWithTwitterCallbackAction();
	}

	public static Action getMemberLoginWithTwitterCallbackAction() {
		return memberLoginWithTwitterCallbackAction;
	}

	public static Action getMemberLoginWithTwitterAction() {
		return memberLoginWithTwitterAction;
	}

	public static Action getMemberLoginWithOAuthAction() {
		return memberLoginWithOAuthAction;
	}

	public static Action getMemberContactAction() {
		return memberContactAction;
	}

	public static Action getMemberMyInfoAction() {
		return memberMyInfoAction;
	}

	public static Action getMemberPasswordCheckAction() {
		return memberPasswordCheckAction;
	}

	public static Action getMemberJoinAction() {
		return memberJoinAction;
	}

	public static Action getMemberModifyAction() {
		return memberModifyAction;
	}

	public static Action getMemberLoginAction() {
		return memberLoginAction;
	}

	public static Action getMemberLogoutAction() {
		return memberLogoutAction;
	}

	public static Action getMemberIdCheckAction() {
		return memberIdCheckAction;
	}

	public static Action getMemberResetPasswordAction() {
		return memberResetPasswordAction;
	}


}
