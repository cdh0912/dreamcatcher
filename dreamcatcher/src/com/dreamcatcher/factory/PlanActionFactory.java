package com.dreamcatcher.factory;

import com.dreamcatcher.action.Action;
import com.dreamcatcher.plan.action.PlanDeleteAction;
import com.dreamcatcher.plan.action.PlanDislikeAction;
import com.dreamcatcher.plan.action.PlanGetLatLngAction;
import com.dreamcatcher.plan.action.PlanLikeAction;
import com.dreamcatcher.plan.action.PlanMakeAction;
import com.dreamcatcher.plan.action.PlanModifyAction;
import com.dreamcatcher.plan.action.PlanModifyViewAction;
import com.dreamcatcher.plan.action.RouteModifyEndAction;
import com.dreamcatcher.plan.action.RouteModifyStartAction;
import com.dreamcatcher.plan.action.PlanViewAction;
import com.dreamcatcher.plan.action.PlanAutoCompleteInMapAction;
import com.dreamcatcher.plan.action.PlanMapViewAction;

public class PlanActionFactory {
	
	private static Action routeModifyStartAction;
	private static Action routeModifyEndAction;
	private static Action planMapViewAction;
	private static Action planAutoCompleteInMapAction;

	
	private static Action planMakeAction;
	private static Action planViewAction;
	private static Action planModifyAction;
	private static Action planDeleteAction;
	private static Action planModifyViewAction;
	private static Action planLikeAction;
	private static Action planDislikeAction;
	private static Action planGetLatLngAction;


	static{
		routeModifyStartAction = new RouteModifyStartAction();
		routeModifyEndAction = new RouteModifyEndAction();
		planMapViewAction = new PlanMapViewAction();
		planAutoCompleteInMapAction = new PlanAutoCompleteInMapAction();
		
		planMakeAction = new PlanMakeAction();
		planViewAction = new PlanViewAction();
		planModifyAction = new PlanModifyAction();
		planDeleteAction = new PlanDeleteAction();
		planModifyViewAction = new PlanModifyViewAction();
		planLikeAction= new PlanLikeAction();
		planDislikeAction= new PlanDislikeAction();		
		
		planGetLatLngAction=new PlanGetLatLngAction();
	}
	
	public static Action getPlanGetLatLngAction() {
		return planGetLatLngAction;
	}

	public static Action getplanLikeAction() {
		return planLikeAction;
	}
	
	public static Action getplanDisLikeAction() {
		return planDislikeAction;
	}
	
	public static Action getPlanViewAction() {
		return planViewAction;
	}

	public static Action getRouteModifyStartAction() {
		return routeModifyStartAction;
	}

	public static Action getRouteModifyEndAction() {
		return routeModifyEndAction;
	}

	public static Action getPlanMakeAction() {
		return planMakeAction;
	}

	public static Action getPlanModifyAction() {
		return planModifyAction;
	}
	
	public static Action getPlanDeleteAction() {
		return planDeleteAction;
	}
	
	public static Action getPlanModifyViewAction() {
		return planModifyViewAction;
	}
	
	public static Action getPlanMapViewAction() {
		return planMapViewAction;
	}
	
	public static Action getPlanAutoCompleteInMapAction() {
		return planAutoCompleteInMapAction;
	}
	

}
