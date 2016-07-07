package it.lispa.bdlfe.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BdlfeServerProperties {
	
	@Value("${backend.url}")
	private String backendUrl;
	
	@Value("${backend.public.url}")
	private String backendPublicUrl;
	
	@Value("${bookreader.url}")
	private String bookreaderUrl;
	
	@Value("${mapreader.url}")
	private String mapreaderUrl;
	
	@Value("${audioplayer.url}")
	private String audioplayerUrl;
	
	@Value("${newItems.num}")
	private Integer newItemsNum;
	
	@Value("${otherFilters.num}")
	private Integer otherFiltersNum;
	
	public String getBackendUrl() {
		return backendUrl.trim();
	}
	public void setBackendUrl(String backendUrl) {
		this.backendUrl = backendUrl;
	}
	public String getBackendPublicUrl() {
		return backendPublicUrl;
	}
	public void setBackendPublicUrl(String backendPublicUrl) {
		this.backendPublicUrl = backendPublicUrl;
	}
	public String getBookreaderUrl() {
		return bookreaderUrl.trim();
	}
	public void setBookreaderUrl(String bookreaderUrl) {
		this.bookreaderUrl = bookreaderUrl;
	}
	public String getMapreaderUrl() {
		return mapreaderUrl.trim();
	}
	public void setMapreaderUrl(String mapreaderUrl) {
		this.mapreaderUrl = mapreaderUrl;
	}
	public String getAudioplayerUrl() {
		return audioplayerUrl.trim();
	}
	public void setAudioplayerUrl(String audioplayerUrl) {
		this.audioplayerUrl = audioplayerUrl;
	}
	public Integer getNewItemsNum() {
		return newItemsNum;
	}
	public void setNewItemsNum(Integer newItemsNum) {
		this.newItemsNum = newItemsNum;
	}
	public Integer getOtherFiltersNum() {
		return otherFiltersNum;
	}
	public void setOtherFiltersNum(Integer otherFiltersNum) {
		this.otherFiltersNum = otherFiltersNum;
	}
}