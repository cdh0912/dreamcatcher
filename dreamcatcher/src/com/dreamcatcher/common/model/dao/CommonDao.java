package com.dreamcatcher.common.model.dao;

import java.util.List;
import java.util.Map;

import com.dreamcatcher.common.model.KeywordDto;
import com.dreamcatcher.site.model.LocationDto;
import com.dreamcatcher.site.model.NationDto;

public interface CommonDao {
	public List<KeywordDto> autoComplete(String keyword);
	public List<NationDto> getNationList(Map searchMap);
	public List<LocationDto> getLocationList(Map searchMap);
	
	public List<NationDto> getNationListWithPaging(Map searchMap);
	public List<LocationDto> getLocationListWithPaging(Map searchMap);
}
