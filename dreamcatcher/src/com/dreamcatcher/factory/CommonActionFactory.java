package com.dreamcatcher.factory;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.admin.action.*;
import com.dreamcatcher.common.action.*;

public class CommonActionFactory {
	
	private static Action commonAutoCompleteAction;
	private static Action commonLocationCategoryAction;
	
	static{
		commonAutoCompleteAction = new CommonAutoCompleteAction();
		commonLocationCategoryAction = new CommonLocationCategoryAction();

	}

	public static Action getCommonLocationCategoryAction() {
		return commonLocationCategoryAction;
	}

	public static Action getCommonAutoCompleteAction() {
		return commonAutoCompleteAction;
	}


}
