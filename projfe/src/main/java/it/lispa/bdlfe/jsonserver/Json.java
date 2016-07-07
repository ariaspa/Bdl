package it.lispa.bdlfe.jsonserver;

import it.lispa.bdlfe.consumer.JsonConsumer;
import it.lispa.bdlfe.dto.BdlfeRef;
import it.lispa.bdlfe.dto.BdlfeSemanticSearch;
import it.lispa.bdlfe.utils.BdlfeServerProperties;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/")
public class Json {

	@Autowired private BdlfeServerProperties serverProps;
	
	@POST
	@Path("semantic-search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public List<JsonserverSemanticSearch> searchSemantic(@FormParam("textToSearch") String textToSearch, @FormParam("typeToSearch") String typeToSearchStr, @Context ServletConfig servletConfig) {


		BdlfeSemanticSearch result = JsonConsumer.searchSemantic(serverProps.getBackendUrl(),textToSearch, typeToSearchStr);
		List<JsonserverSemanticSearch> toRet = new ArrayList<JsonserverSemanticSearch>();

		List<BdlfeRef> titles = result.getTitles();
		for(BdlfeRef item:titles){
			toRet.add(new JsonserverSemanticSearch(item.getId(),item.getName(),"Oggetti Digitali"));
		}
		List<BdlfeRef> authors = result.getAuthors();
		for(BdlfeRef item:authors){
			toRet.add(new JsonserverSemanticSearch(item.getId(),item.getName(),"Autori"));
		}
		List<BdlfeRef> subjects = result.getSubjects();
		for(BdlfeRef item:subjects){
			toRet.add(new JsonserverSemanticSearch(item.getId(),item.getName(),"Soggetti"));
		}
		
		
		return toRet;
		
	}
	
}
