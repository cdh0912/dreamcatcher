package com.dreamcatcher.site.model;

public class SiteImageDto extends SiteDto{
	private String origin_picture;
	private String saved_picture;
	private String savefolder;
	private int type;

	public String getOrigin_picture() {
		return origin_picture;
	}

	public void setOrigin_picture(String origin_picture) {
		this.origin_picture = origin_picture;
	}

	public String getSaved_picture() {
		return saved_picture;
	}

	public void setSaved_picture(String saved_picture) {
		this.saved_picture = saved_picture;
	}

	public String getSavefolder() {
		return savefolder;
	}

	public void setSavefolder(String savefolder) {
		this.savefolder = savefolder;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
