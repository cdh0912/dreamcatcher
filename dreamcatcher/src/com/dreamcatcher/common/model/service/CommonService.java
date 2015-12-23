package com.dreamcatcher.common.model.service;

import java.util.List;
import java.util.Map;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.site.model.LocationDto;
import com.dreamcatcher.site.model.NationDto;
import com.dreamcatcher.util.PageNavigator;

public interface CommonService {
	public List<KeywordDto> autoComplete(String keyword);
	public List<NationDto> getNationList(Map searchMap);
	public List<LocationDto> getLocationList(Map searchMap);
	
	public List<NationDto> getNationListWithPaging(Map searchMap);
	public List<LocationDto> getLocationListWithPaging(Map searchMap);
	
}
