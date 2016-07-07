package it.lispa.bdl.server.servlet;

import it.lispa.bdl.commons.domain.BdlContestoArch;
import it.lispa.bdl.commons.domain.BdlLingua;
import it.lispa.bdl.commons.domain.BdlLivelloBiblio;
import it.lispa.bdl.commons.domain.BdlQualificaAutore;
import it.lispa.bdl.commons.domain.BdlQualificatoreData;
import it.lispa.bdl.commons.domain.BdlSoggettoProd;
import it.lispa.bdl.commons.domain.BdlSupporto;
import it.lispa.bdl.commons.domain.BdlTecnicaGrafica;
import it.lispa.bdl.commons.domain.BdlTipoArchivio;
import it.lispa.bdl.commons.domain.BdlTipoGrafica;
import it.lispa.bdl.commons.domain.BdlTipoIdentificativo;
import it.lispa.bdl.commons.domain.BdlTipoOggetto;
import it.lispa.bdl.server.repository.BdlContestoArchRepository;
import it.lispa.bdl.server.repository.BdlLinguaRepository;
import it.lispa.bdl.server.repository.BdlLivelloBiblioRepository;
import it.lispa.bdl.server.repository.BdlQualificaAutoreRepository;
import it.lispa.bdl.server.repository.BdlQualificatoreDataRepository;
import it.lispa.bdl.server.repository.BdlSoggettoProdRepository;
import it.lispa.bdl.server.repository.BdlSupportoRepository;
import it.lispa.bdl.server.repository.BdlTecnicaGraficaRepository;
import it.lispa.bdl.server.repository.BdlTipoArchivioRepository;
import it.lispa.bdl.server.repository.BdlTipoGraficaRepository;
import it.lispa.bdl.server.repository.BdlTipoIdentificativoRepository;
import it.lispa.bdl.server.repository.BdlTipoOggettoRepository;
import it.lispa.bdl.server.utils.ExcelCatalogazioneTpl;
import it.lispa.bdl.server.utils.ExcelWriterUtils;
import it.lispa.bdl.server.utils.SecurityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Class CatalogazioneTplServer.
 */
public class CatalogazioneTplServer extends HttpServlet{

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 2957134699111113299L;

	/** log. */
	private static Logger log = Logger.getLogger(CatalogazioneTplServer.class);

	/** sec utils. */
	@Autowired
	protected SecurityUtils secUtils;
	
	/** bdl tipo oggetto repository. */
	@Autowired
	private BdlTipoOggettoRepository bdlTipoOggettoRepository;
	
	/** qualifica autore repository. */
	@Autowired
	private BdlQualificaAutoreRepository qualificaAutoreRepository;
	
	/** lingua repository. */
	@Autowired
	private BdlLinguaRepository linguaRepository;
	
	/** contesto arch repository. */
	@Autowired
	private BdlContestoArchRepository contestoArchRepository;
	
	/** tipo identificativo repository. */
	@Autowired
	private BdlTipoIdentificativoRepository tipoIdentificativoRepository;
	
	/** livello biblio repository. */
	@Autowired
	private BdlLivelloBiblioRepository livelloBiblioRepository;
	
	/** qualificatore data repository. */
	@Autowired
	private BdlQualificatoreDataRepository qualificatoreDataRepository;
	
	/** soggetto prod repository. */
	@Autowired
	private BdlSoggettoProdRepository soggettoProdRepository;
	
	/** supporto repository. */
	@Autowired
	private BdlSupportoRepository supportoRepository;
	
	/** tipo grafica repository. */
	@Autowired
	private BdlTipoGraficaRepository tipoGraficaRepository;
	
	/** tecnica grafica repository. */
	@Autowired
	private BdlTecnicaGraficaRepository tecnicaGraficaRepository;
	
	/** tipo archivio repository. */
	@Autowired
	private BdlTipoArchivioRepository tipoArchivioRepository;

	
	/** my excel. */
	private ExcelCatalogazioneTpl myExcel;

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String calledPath = request.getPathInfo();
		log.debug("Entro nella doGet con calledPath="+calledPath); // /cdOggetto

		BigDecimal filterCode = null;
		try {
			String[] pathParts = calledPath.split("/");
			for(int i =0;i<pathParts.length;i++){
				log.debug("pathParts["+i+"]="+pathParts[i]);
			}
			if(pathParts[1]!=null){
				filterCode = new BigDecimal(Long.valueOf(pathParts[1]));
			}			
		}catch (Exception e){
			log.debug("Exception nella parsificazione della riga di input: "+e.getMessage());
		}

		log.debug("Servlet richiamata con parametri:");
		log.debug("filterCode="+filterCode);

		BdlTipoOggetto myTipoOggetto = bdlTipoOggettoRepository.findByCdTipoOggetto(filterCode);
		if(myTipoOggetto!=null) {
			try {
				myExcel = new ExcelCatalogazioneTpl(myTipoOggetto.getCdTipoOggetto(), getServletContext().getResourceAsStream("/WEB-INF/CatalogazioneTpl.xlsx"));

				this.addLivelloBiblio();
				this.addQualificaAutore();
				this.addLingua();
				this.addTipoIdentificativo();
				this.addQualificatoreData();
				this.addContestoArch();
				this.addSoggettoProd();
				this.addTipoGrafica();
				this.addSupporto();
				this.addTecnicaGrafica();
				this.addTipoArchivio();
				
				Sheet sh = myExcel.getWb().getSheet("Dati");
				switch(myTipoOggetto.getCdTipoOggetto().intValue()) {
    				case 1: 	// 1 Testo a stampa 
    				case 3: 	// 3 Musica a stampa 
    				case 4: 	// 4 Musica manoscritta 
    				case 7: 	// 7 Materiali video 
    				case 8: 	// 8 Registrazioni sonore non musicali 
    				case 9: 	// 9 Registrazioni sonore musicali 
    				case 11: 	// 11 Incunaboli 
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColScala, true);                              
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColProiezione, true);                         
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColCoordinate, true);                         
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColContestoarchivistico, true);               
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSoggettiproduttori, true);                 
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTipografica, true);                        
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSupportoprimario, true);                   
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTecnicagrafica, true);                     
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTipoarchivio, true);      
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSegnatura, true);        
    					break;                                                                                      
    				//____________________________________________________Fine gruppo
    				case 5: 	// 5 Cartografia a stampa 
    				case 6:		// 6 Cartografia Manoscritta 
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColLingua, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColContestoarchivistico, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSoggettiproduttori, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTipografica, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSupportoprimario, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTecnicagrafica, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTipoarchivio, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSegnatura, true);    
    					break;
    				//____________________________________________________Fine gruppo
    				case 2:		// 2 Materiale Manoscritto 
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColEditore, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColScala, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColProiezione, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColCoordinate, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColContestoarchivistico, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSoggettiproduttori, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTipografica, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTecnicagrafica, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTipoarchivio, true);
    					break;
    				//____________________________________________________Fine gruppo
    				case 10:		// 10 Materiale grafico 
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColLingua, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColScala, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColProiezione, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColCoordinate, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSoggettiproduttori, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTipoarchivio, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSegnatura, true);    
    					break;
    				//____________________________________________________Fine gruppo
    				case 12:		// 12 Archivi 
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSoggetto, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColQualificatoreautore, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColAutore, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColQualificatoreautoreDue, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColAutore2, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColEditore, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColLingua, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTipoidentificativo, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColIdentificativo, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColLuogopubblicazione, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColScala, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColProiezione, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColCoordinate, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTipografica, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSupportoprimario, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColTecnicagrafica, true);
    					sh.setColumnHidden(ExcelCatalogazioneTpl.startColSegnatura, true);    

    					break;
    				//____________________________________________________Fine gruppo
    				default:
    					break;
				}
				myExcel.getWb().setSheetHidden(1, true);
				
				response.setContentType(ExcelWriterUtils.xlsxMimeType);
				response.setHeader("Content-Disposition", "attachment; filename=CatalogazioneTpl_"+myTipoOggetto.getCdTipoOggetto()+".xlsx");
				
				myExcel.getWb().write(response.getOutputStream());
				response.getOutputStream().close();
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else {
			log.debug("myTipoOggetto is null");
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	/**
	 * Aggiunge livello biblio.
	 *
	 * @throws Exception exception
	 */
	private void addLivelloBiblio() throws Exception{
		List<BdlLivelloBiblio> myList = (List<BdlLivelloBiblio>) livelloBiblioRepository.findAll();
		List<String> cmbContent = new ArrayList<String>();
		for(BdlLivelloBiblio myObj : myList) {
			cmbContent.add(myObj.getDsDescrizione());
		}
		myExcel.addCmbContent("LivelloBiblio",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColLivellobibliografico);
	}
	
	/**
	 * Aggiunge qualifica autore.
	 *
	 * @throws Exception exception
	 */
	private void addQualificaAutore() throws Exception{
		List<BdlQualificaAutore> myList = (List<BdlQualificaAutore>) qualificaAutoreRepository.findAll();
		List<String> cmbContent = new ArrayList<String>();
		for(BdlQualificaAutore myObj : myList) {
			cmbContent.add(myObj.getDsDescrizione());
		}
		myExcel.addCmbContent("QualificaAutore",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColQualificatoreautore);
		myExcel.addCmbContent("QualificaAutoreDue",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColQualificatoreautoreDue);
	}
	
	/**
	 * Aggiunge lingua.
	 *
	 * @throws Exception exception
	 */
	private void addLingua() throws Exception{
		List<BdlLingua> myList = (List<BdlLingua>) linguaRepository.findAll();
		List<String> cmbContent = new ArrayList<String>();
		for(BdlLingua myObj : myList) {
			cmbContent.add(myObj.getDsDescrizione());
		}
		myExcel.addCmbContent("Lingua",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColLingua);
	}
	
	/**
	 * Aggiunge tipo identificativo.
	 *
	 * @throws Exception exception
	 */
	private void addTipoIdentificativo() throws Exception{
		List<BdlTipoIdentificativo> myList = (List<BdlTipoIdentificativo>) tipoIdentificativoRepository.findAll();
		List<String> cmbContent = new ArrayList<String>();
		for(BdlTipoIdentificativo myObj : myList) {
			cmbContent.add(myObj.getDsDescrizione());
		}
		myExcel.addCmbContent("TipoIdentificativo",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColTipoidentificativo);
	}
	
	/**
	 * Aggiunge qualificatore data.
	 *
	 * @throws Exception exception
	 */
	private void addQualificatoreData() throws Exception{
		List<BdlQualificatoreData> myList = (List<BdlQualificatoreData>) qualificatoreDataRepository.findAll();
		List<String> cmbContent = new ArrayList<String>();
		for(BdlQualificatoreData myObj : myList) {
			cmbContent.add(myObj.getDsDescrizione());
		}
		myExcel.addCmbContent("QualificatoreData",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColQualificatoredata);
	}
	
	/**
	 * Aggiunge contesto arch.
	 *
	 * @throws Exception exception
	 */
	private void addContestoArch() throws Exception{
		List<BdlContestoArch> myList = (List<BdlContestoArch>) contestoArchRepository.findAll();
		List<String> cmbContent = new ArrayList<String>();
		for(BdlContestoArch myObj : myList) {
			cmbContent.add(myObj.getDsDescrizione());
		}
		myExcel.addCmbContent("ContestoArch",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColContestoarchivistico);
	}
	
	/**
	 * Aggiunge soggetto prod.
	 *
	 * @throws Exception exception
	 */
	private void addSoggettoProd() throws Exception{
		List<BdlSoggettoProd> myList = (List<BdlSoggettoProd>) soggettoProdRepository.findAll();
		List<String> cmbContent = new ArrayList<String>();
		for(BdlSoggettoProd myObj : myList) {
			cmbContent.add(myObj.getDsDescrizione());
		}
		myExcel.addCmbContent("SoggettoProd",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColSoggettiproduttori);
	}
	
	/**
	 * Aggiunge tipo grafica.
	 *
	 * @throws Exception exception
	 */
	private void addTipoGrafica() throws Exception{
		List<BdlTipoGrafica> myList = (List<BdlTipoGrafica>) tipoGraficaRepository.findAll();
		List<String> cmbContent = new ArrayList<String>();
		for(BdlTipoGrafica myObj : myList) {
			cmbContent.add(myObj.getDsDescrizione());
		}
		myExcel.addCmbContent("TipoGrafica",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColTipografica);
	}
	
	/**
	 * Aggiunge supporto.
	 *
	 * @throws Exception exception
	 */
	private void addSupporto() throws Exception{
		List<BdlSupporto> myList = (List<BdlSupporto>) supportoRepository.findAll();
		List<String> cmbContent = new ArrayList<String>();
		for(BdlSupporto myObj : myList) {
			cmbContent.add(myObj.getDsDescrizione());
		}
		myExcel.addCmbContent("Supporto",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColSupportoprimario);
	}
	
	/**
	 * Aggiunge tecnica grafica.
	 *
	 * @throws Exception exception
	 */
	private void addTecnicaGrafica() throws Exception{
		List<BdlTecnicaGrafica> myList = (List<BdlTecnicaGrafica>) tecnicaGraficaRepository.findAll();
		List<String> cmbContent = new ArrayList<String>();
		for(BdlTecnicaGrafica myObj : myList) {
			cmbContent.add(myObj.getDsDescrizione());
		}
		myExcel.addCmbContent("TecnicaGrafica",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColTecnicagrafica);
	}
	
	/**
	 * Aggiunge tipo archivio.
	 *
	 * @throws Exception exception
	 */
	private void addTipoArchivio() throws Exception{
		List<BdlTipoArchivio> myList = (List<BdlTipoArchivio>) tipoArchivioRepository.findAll();
		List<String> cmbContent = new ArrayList<String>();
		for(BdlTipoArchivio myObj : myList) {
			cmbContent.add(myObj.getDsDescrizione());
		}
		myExcel.addCmbContent("TipoArchivio",cmbContent,ExcelCatalogazioneTpl.startRow,ExcelCatalogazioneTpl.startColTipoarchivio);
	}
}
