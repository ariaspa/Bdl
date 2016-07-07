package it.lispa.bdl;

import it.lispa.bdl.commons.utils.ConvertWrapper;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.im4java.core.CommandException;

public class CreaOggettiOriginali {
	
	private static final String imgBinPath = "D:\\apache-tomcat-bdl\\ImageMagick-6.9.1-2";
	private static final String dirNetappTmp = "D:\\apache-tomcat-bdl\\netapp\\tmp";
	
	private static final ConvertWrapper convertWrapper = new ConvertWrapper(imgBinPath);
	
	private static final Integer[] arrCodiceOO = {
		2,
		62,
		82
	};
	private static Integer cdOggettoOriginale = 781;
	
	private static final String DIR_LARGE 	  	= "large";
	private static final String DIR_MASTER    	= "master";
	private static final String DIR_MEDIUM    	= "medium";
	private static final String DIR_SMALL     	= "small";
	private static final String DIR_THUMBNAIL  	= "thumbnail";
	private static final String DIR_PDF  		= "pdf";
	private static final String DIR_PDFSINGLE 	= "pdfsingle";
	private static final String DIR_ORIGINALE 	= CreaOggettiOriginali.DIR_MASTER;
	
	private static final String PATH_LARGE 	   	= buildPath(CreaOggettiOriginali.DIR_LARGE);
	private static final String PATH_MASTER    	= buildPath(CreaOggettiOriginali.DIR_MASTER);
	private static final String PATH_MEDIUM    	= buildPath(CreaOggettiOriginali.DIR_MEDIUM);
	private static final String PATH_SMALL     	= buildPath(CreaOggettiOriginali.DIR_SMALL);
	private static final String PATH_THUMBNAIL  = buildPath(CreaOggettiOriginali.DIR_THUMBNAIL);
	private static final String PATH_PDF      	= buildPath(CreaOggettiOriginali.DIR_PDF);
	private static final String PATH_PDFSINGLE  = buildPath(CreaOggettiOriginali.DIR_PDFSINGLE);
	private static final String PATH_ORIGINALE 	= CreaOggettiOriginali.PATH_MASTER;
	
	private static final Character DOT = '.';
	
	private static final String EXT_MASTER		= "tiff";
	private static final String EXT_LARGE 	   	= "jpeg";
	private static final String EXT_PDF      	= "pdf";
	private static final String EXT_ORIGINALE 	= "jpg";
	
	private static final Integer DPI_MASTER    	= Integer.valueOf(400);
	private static final Integer DPI_LARGE 	   	= Integer.valueOf(300);
	private static final Integer DPI_MEDIUM    	= Integer.valueOf(150);
	private static final Integer DPI_SMALL     	= Integer.valueOf(72);
	@SuppressWarnings("unused")
	private static final Integer DPI_ORIGINALE 	= CreaOggettiOriginali.DPI_MASTER;
	
	private static final Integer PXL_THUMBNAIL   = Integer.valueOf(150);
	
	private static Boolean flagRinomina = Boolean.FALSE;

	public static void main(String[] args) {
		
		try {
			if(!CreaOggettiOriginali.flagRinomina) {
				init();
				if(checkDirOriginale()) {
					System.out.println("Creo le directory... ");
					createDirs();
		    		System.out.println("...directory create! ");
		    		System.out.println("Creo le <large>... ");
		    		makeImmaginiLarge();
		    		System.out.println("...<large> create! ");
//		    		System.out.println("Creo le <master>... ");
//		    		makeMaster();
//		    		System.out.println("...<master> create! ");
		    		System.out.println("Creo le <medium>... ");
		    		makeImmaginiMedium();
		    		System.out.println("...<medium> create! ");
		    		System.out.println("Creo le <small>... ");
		    		makeImmaginiSmall();
		    		System.out.println("...<small> create! ");
		    		System.out.println("Creo le <thumb>... ");
		    		makeImmaginiThumb();
		    		System.out.println("...<thumb> create! ");
		    		System.out.println("Creo il PDF dell'opera digitale completa... ");
		    		makePDF();
		    		System.out.println("...PDF creato! ");
		    		System.out.println("Creo i PDF delle singole pagine dell'opera digitale... ");
		    		makePaginaPDF();
		    		System.out.println("...PDF creati! ");
//		    		System.out.println("Cancello la dir <originale>... ");
//		    		deleteDirOriginale();
//		    		System.out.println("... Dir <originale> cancellata! ");
				} else {
					System.out.println("___La cartella " + CreaOggettiOriginali.DIR_ORIGINALE.toUpperCase() + " ha dei problemi. Controlla! ");
				}
			} else {
    			if(rinomina(PATH_MASTER)) {
    				System.out.println("___File in cartella " + CreaOggettiOriginali.DIR_MASTER + " rinominati. ");
    			}
    			if(rinomina(PATH_LARGE)) {
    				System.out.println("___File in cartella " + CreaOggettiOriginali.DIR_LARGE + " rinominati. ");
    			}
    			if(rinomina(PATH_MEDIUM)) {
    				System.out.println("___File in cartella " + CreaOggettiOriginali.DIR_MEDIUM + " rinominati. ");
    			}
    			if(rinomina(PATH_SMALL)) {
    				System.out.println("___File in cartella " + CreaOggettiOriginali.DIR_SMALL + " rinominati. ");
    			}
    			if(rinomina(PATH_THUMBNAIL)) {
    				System.out.println("___File in cartella " + CreaOggettiOriginali.DIR_THUMBNAIL + " rinominati. ");
    			}
			}
		} catch (Exception e) {
			System.out.println("___EXCEPTION: " + e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private static Boolean checkDirOriginale() {
		
		File myFile = new File(CreaOggettiOriginali.PATH_ORIGINALE);
		File[] immaginiOriginali = myFile.listFiles();
		
		String[] arrTmp 	 = null;
		String imgNome 		 = null;
		String imgEstensione = null;
		for(int i=0; i<immaginiOriginali.length; i++) {	
			
			arrTmp = immaginiOriginali[i].getName().split("\\.");
			imgNome 	  = arrTmp[0];
			imgEstensione = arrTmp[1];
			
			if(!imgEstensione.equals(CreaOggettiOriginali.EXT_ORIGINALE)) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	@SuppressWarnings("unused")
	private static Boolean rinomina(String myPath) {
		
		System.out.println("___entro");
		
		File myFile = new File(myPath);
		File[] immagini = myFile.listFiles();
		
		String[] arrTmp 	 = null;
		String imgNome 		 = null;
		String imgEstensione = null;
		File newFile = null;
		File oldFile = null;
		for(Integer i=0; i<immagini.length; i++) {	
			
			arrTmp = immagini[i].getName().split("\\.");
			
			imgNome 	  = arrTmp[0];
			imgEstensione = arrTmp[1];
			
			i++;
			newFile = new File(myPath + File.separatorChar + StringUtils.leftPad(i.toString(), 4, "0") + CreaOggettiOriginali.DOT + imgEstensione);
			i--;
			System.out.println(newFile.getPath());
			oldFile = new File(myPath + File.separatorChar + immagini[i].getName());
			System.out.println(oldFile.getPath());
			if(!oldFile.renameTo(newFile)) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	@SuppressWarnings("unused")
	private static void makeImmaginiMaster() throws IOException, CommandException {
		
		File myFile = new File(CreaOggettiOriginali.PATH_ORIGINALE);
		File[] immaginiOriginali = myFile.listFiles();
		
		String[] arrTmp 	 = null;
		String imgNome 		 = null;
		String imgEstensione = null;
		File fileTmp 		 = null;
		for(int i=0; i<immaginiOriginali.length; i++) {	
			
			arrTmp = immaginiOriginali[i].getName().split("\\.");
			imgNome 	  = arrTmp[0];
			imgEstensione = arrTmp[1];
			
			/* Definisco l'immagine */
			fileTmp = new File(CreaOggettiOriginali.PATH_MASTER + imgNome + CreaOggettiOriginali.DOT + "tiff");	
			/* Creo l'immagine */
			convertWrapper.tiffImage(immaginiOriginali[i].getPath(), fileTmp.getPath());
			convertWrapper.resampleImage(fileTmp.getPath(), fileTmp.getPath(), CreaOggettiOriginali.DPI_MASTER);
		}
	}
	
	private static void makeImmaginiLarge() throws IOException, CommandException {
		
		File myFile = new File(CreaOggettiOriginali.PATH_ORIGINALE);
		File[] immaginiOriginali = myFile.listFiles();
		
		String[] arrTmp 	 = null;
		String imgNome 		 = null;
		String imgEstensione = null;
		File fileTmp 		 = null;
		for(int i=0; i<immaginiOriginali.length; i++) {
			
			arrTmp = immaginiOriginali[i].getName().split("\\.");
			imgNome 	  = arrTmp[0];
			imgEstensione = arrTmp[1];
			
			if(imgEstensione.equalsIgnoreCase(CreaOggettiOriginali.EXT_ORIGINALE)) {
				imgNome = imgNome.replaceAll("^(0*)", "");		
				/* Definisco l'immagine */
				fileTmp = new File(CreaOggettiOriginali.PATH_LARGE + StringUtils.leftPad(imgNome, 4, "0") + CreaOggettiOriginali.DOT + CreaOggettiOriginali.EXT_LARGE);
				/* Creo l'immagine */
				convertWrapper.resampleImage(immaginiOriginali[i].getPath(), fileTmp.getPath(), CreaOggettiOriginali.DPI_LARGE);
			}
		}
	}
	
	private static void makePaginaPDF() throws IOException, CommandException {
		
		File myFile = new File(CreaOggettiOriginali.PATH_LARGE);
		File[] immaginiLarge = myFile.listFiles();
		
		String[] arrTmp 	 = null;
		String imgNome 		 = null;
		String imgEstensione = null;
		File fileTmp 		 = null;
		for(int i=0; i<immaginiLarge.length; i++) {
			
			arrTmp = immaginiLarge[i].getName().split("\\.");
			imgNome 	  = arrTmp[0];
			imgEstensione = arrTmp[1];
			
			if(imgEstensione.equalsIgnoreCase(CreaOggettiOriginali.EXT_LARGE)) {
				imgNome = imgNome.replaceAll("^(0*)", "");			
				/* Definisco il PDF */
				fileTmp = new File(CreaOggettiOriginali.PATH_PDFSINGLE + StringUtils.leftPad(imgNome, 4, "0") + CreaOggettiOriginali.DOT + CreaOggettiOriginali.EXT_PDF);	
				/* Creo il PDF */
				convertWrapper.pdfImage(immaginiLarge[i].getPath(), fileTmp.getPath());
			}
		}
	}
	
	private static void makePDF() throws IOException, CommandException {
		
		File myFile = new File(CreaOggettiOriginali.PATH_LARGE);
		File[] immaginiLarge = myFile.listFiles();
		
		String[] arrNomiImmaginiLarge = new String[immaginiLarge.length];
		for(int i=0; i<immaginiLarge.length; i++) {
			arrNomiImmaginiLarge[i] = immaginiLarge[i].getPath();
		}
		
		/* Definisco il PDF */
		File fileTmp = new File(CreaOggettiOriginali.PATH_PDF + cdOggettoOriginale + CreaOggettiOriginali.DOT + CreaOggettiOriginali.EXT_PDF);
		/* Creo il PDF */
		convertWrapper.pdfImages(arrNomiImmaginiLarge, fileTmp.getPath());
	}
	
	
	private static void makeImmaginiMedium() throws IOException, CommandException {
		
		File myFile = new File(CreaOggettiOriginali.PATH_LARGE);
		File[] immaginiLarge = myFile.listFiles();
		
		File fileTmp = null;
		for(int i=0; i<immaginiLarge.length; i++) {
			
			/* Definisco l'immagine */
			fileTmp = new File(CreaOggettiOriginali.PATH_MEDIUM + immaginiLarge[i].getName());	
			/* Creo l'immagine */
			convertWrapper.resampleImage(immaginiLarge[i].getPath(), fileTmp.getPath(), CreaOggettiOriginali.DPI_MEDIUM);
		}
	}
	
	private static void makeImmaginiSmall() throws IOException, CommandException {
		
		File myFile = new File(CreaOggettiOriginali.PATH_LARGE);
		File[] immaginiLarge = myFile.listFiles();
		
		File fileTmp = null;
		for(int i=0; i<immaginiLarge.length; i++) {	
			
			/* Definisco l'immagine */
			fileTmp = new File(CreaOggettiOriginali.PATH_SMALL + immaginiLarge[i].getName());	
			/* Creo l'immagine */
			convertWrapper.resampleImage(immaginiLarge[i].getPath(), fileTmp.getPath(), CreaOggettiOriginali.DPI_SMALL);
		}
	}
	
	private static void makeImmaginiThumb() throws IOException, CommandException {
		
		File myFile = new File(CreaOggettiOriginali.PATH_LARGE);
		File[] immaginiLarge = myFile.listFiles();
		
		File fileTmp = null;
		for(int i=0; i<immaginiLarge.length; i++) {	
			
			/* Definisco l'immagine */
			fileTmp = new File(CreaOggettiOriginali.PATH_THUMBNAIL + immaginiLarge[i].getName());	
			/* Creo l'immagine */
			convertWrapper.scaleImage(immaginiLarge[i].getPath(), fileTmp.getPath(), CreaOggettiOriginali.PXL_THUMBNAIL, CreaOggettiOriginali.PXL_THUMBNAIL, "!");
		}
	}
	
	private static void createDirs() throws Exception {
		
		createDir(CreaOggettiOriginali.DIR_LARGE);
		createDir(CreaOggettiOriginali.DIR_MEDIUM);
		createDir(CreaOggettiOriginali.DIR_PDF);
		createDir(CreaOggettiOriginali.DIR_PDFSINGLE);
		createDir(CreaOggettiOriginali.DIR_SMALL);
		createDir(CreaOggettiOriginali.DIR_THUMBNAIL);
	}
	
	private static void createDir(String dir) throws Exception {
		String dirToCreate = buildPath(dir);
		File dirFile = new File(dirToCreate);
		
		if(dirFile.exists()){
			FileUtils.deleteDirectory(dirFile);
		} else if(!dirFile.mkdir()){
			throw new Exception("[createDir] Ci sono stati dei problemi nella creazione della dir " + dirFile.getPath());
		}
	}
	
	private static void init() throws Exception {
		
		String dirToCreate = buildPath("");
		File dirObj = new File(dirToCreate);
		if (!dirObj.exists()) {
			throw new Exception("[createDir] La cartella " + CreaOggettiOriginali.DIR_ORIGINALE + " non esiste! ");
		} else {
			FileFilter dirFilter = null;
			/* Creo FileFilter per isolare gli elementi da eliminare */
			dirFilter = new FileFilter() {
				public boolean accept(File file) {
					return (file.isDirectory() && !file.getName().equalsIgnoreCase(CreaOggettiOriginali.DIR_ORIGINALE));
				}
			};
			/* Cancello tutto tranne ciò che e' indicato nel FileFilter */
			File[] arrFile = dirObj.listFiles(dirFilter);
			for (File file : arrFile) {
				if(file.isDirectory()) {
					FileUtils.deleteDirectory(file);
				} else if(file.isFile()) {
					FileUtils.forceDelete(file);
				}
			}
		}
	}
	
	private static String buildPath(String dir) {
			
		StringBuilder strTmp = new StringBuilder();
		
		strTmp.append(dirNetappTmp);
		strTmp.append(File.separator);
		for(Integer partOfCode : arrCodiceOO) {
			strTmp.append(partOfCode);
			strTmp.append(File.separator);
		}
		strTmp.append(cdOggettoOriginale);
		strTmp.append(File.separator);
		if(!"".equals(dir)) {
			strTmp.append(dir);
			strTmp.append(File.separator);
		}
		
		return strTmp.toString();
	}
	
	@SuppressWarnings("unused")
	private static void deleteDirOriginale() throws IOException {
		
		File fileOriginale = new File(CreaOggettiOriginali.PATH_ORIGINALE);
		if(fileOriginale.exists()) {
			FileUtils.deleteDirectory(fileOriginale);
		}
	}
}
