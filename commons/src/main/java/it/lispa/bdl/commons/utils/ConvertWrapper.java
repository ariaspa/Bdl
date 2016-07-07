package it.lispa.bdl.commons.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.im4java.core.CommandException;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.InfoException;
import org.im4java.process.ArrayListOutputConsumer;

/**
 * Class ConvertWrapper.
 */
public class  ConvertWrapper {	
	
	/** log. */
	private static Logger log = Logger.getLogger(ConvertWrapper.class);
	
	private static final String prependLogStrInizio = "______";
	
	/** img bin path. */
	String imgBinPath;
	
	/** tile dimension. */
	Integer tileDimension;
	
	/** max zoom level. */
	Integer maxZoomLevel;
	
	/**
	 * Istanzia un nuovo convert wrapper.
	 *
	 * @param imgBinPath  img bin path
	 */
	public ConvertWrapper(String imgBinPath){
		this.imgBinPath = imgBinPath;
		tileDimension = 256;
		maxZoomLevel = 18;
	}

	/**
	 * Legge pixels height.
	 *
	 * @param width  width
	 * @param height  height
	 * @return pixels height
	 */
	private ImageDimensions getPixelsHeight(Integer width, Integer height){
		
		ImageDimensions imgDim = null;
		Double widthDbl = width.doubleValue();
		Double heightDbl = height.doubleValue();
		Double newWidthDbl = null;
		Double newHeightDbl = new Double(0);
		Integer zoomLevel = 0;
		
		Double myPow = null;
		Double myLastPow = null;
		for(zoomLevel=0;zoomLevel<=maxZoomLevel;zoomLevel++){
			double exponential = Math.pow(2, zoomLevel);
			myPow = (double) (256*exponential);
			log.debug("[getPixelsHeight] myPow="+myPow);
			
			if(myPow.compareTo(heightDbl)>=0){
				log.debug("[getPixelsHeight] myPow="+myPow+" is bigger than "+heightDbl);
				log.debug("[getPixelsHeight] myLastPow="+myLastPow);
				break;
			}
			
			myLastPow = myPow;
		}
		if(myPow!=null){
			if(myLastPow==null) {
				myLastPow = myPow;
			}
			Double ratio = myPow/heightDbl;
			if(ratio.compareTo(1.1)>0){
				log.debug("[getPixelsHeight] ratio="+ratio+"(>1.1): returning "+myLastPow);
				newHeightDbl = myLastPow;
			}else{
				log.debug("[getPixelsHeight] ratio="+ratio+" (<1.1): returning "+myPow);
				newHeightDbl = myPow;
			}
		}
		
		newWidthDbl = Math.ceil((widthDbl/heightDbl)*newHeightDbl);
		Double myDouble = new Double(1);
		if(Math.abs(newWidthDbl%2 - myDouble) < .0000001){
			newWidthDbl++;
		}
		
		imgDim = new ImageDimensions(newWidthDbl.intValue(),newHeightDbl.intValue());
		return imgDim;
	}
	
	/**
	 * Legge pixels width.
	 *
	 * @param width  width
	 * @param height  height
	 * @return pixels width
	 */
	private ImageDimensions getPixelsWidth(Integer width, Integer height){
		
		ImageDimensions imgDim = null;
		Double widthDbl = width.doubleValue();
		Double heightDbl = height.doubleValue();
		Double newWidthDbl = new Double(0);
		Integer zoomLevel = 0;
		
		Double myPow = null;
		for(zoomLevel=0;zoomLevel<=maxZoomLevel;zoomLevel++){
			double exponential = Math.pow(2, zoomLevel);
			myPow = (double) (256*exponential);
			log.debug("[getPixelsWidth] myPow="+myPow);
			
			if(myPow.compareTo(widthDbl)>=0){
				log.debug("[getPixelsWidth] myPow="+myPow+" is bigger than "+widthDbl);
				break;
			}
		}
		if(myPow!=null){
			newWidthDbl = myPow;
		}
		
		imgDim = new ImageDimensions(newWidthDbl.intValue(),heightDbl.intValue());
		return imgDim;
	}
	
	/**
	 * Crea thumb image.
	 *
	 * @param srcImg  src img
	 * @param pathForTiles  path for tiles
	 */
	public void createThumbImage(String srcImg, String pathForTiles) {
		// nothing to do...
	}
	
	/**
	 * Tile image.
	 *
	 * @param srcImg  src img
	 * @param pathForTiles  path for tiles
	 * @return map
	 * @throws CommandException command exception
	 * @throws InfoException info exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception exception
	 */
	public Map<String, BigDecimal> tileImage(String srcImg, String pathForTiles) throws CommandException, InfoException, IOException, Exception {
		
		Map<String, BigDecimal> hashRet = new HashMap<String, BigDecimal>();
		
		log.debug("[tileImage] Entro nel metodo ");
		log.debug("[tileImage] srcImg="+srcImg+" pathForTiles="+pathForTiles);

		File dir4Tiles = new File(pathForTiles);
		log.debug("[tileImage] Cancello il contenuto della dir "+dir4Tiles.getPath());
		if(dir4Tiles.exists()){
			FileUtils.deleteDirectory(dir4Tiles);
		}

		log.debug("[tileImage] Ri-creo la dir "+dir4Tiles.getPath());
		if(!dir4Tiles.exists()){
			if(!dir4Tiles.mkdir()){
				throw new Exception("[tileImage] Ci sono stati dei problemi nella creazione della dir "+dir4Tiles.getPath());
			}
		}
		
		String srcExt = FilenameUtils.getExtension(srcImg);
				
		String destImg = pathForTiles+File.separator+"normalized."+srcExt;
		
		ImageDimensions normalizedDim = normalizeImage(srcImg, destImg);
		
		Integer currZoomLevel = maxZoomLevel;
		String destImgCmp;		
		destImgCmp = pathForTiles+File.separator+currZoomLevel+"."+srcExt;
		compressImage(destImg, destImgCmp, 0.75);
		destImg = destImgCmp; 
	
		String myDestImg = destImg;
		String mySrcImg;
		File myDir4Tiles;
		Integer myPxLarghezza = normalizedDim.getWidth();
		Integer myPxAltezza = normalizedDim.getHeight();
		boolean isFirst = true;
		while(myPxLarghezza>=256 && myPxAltezza>=256){
			mySrcImg = myDestImg;
			myDestImg = pathForTiles+File.separator+currZoomLevel+"."+srcExt;
			if(isFirst){
				isFirst = false;
			}else{
				scaleImage(mySrcImg, myDestImg, myPxLarghezza, myPxAltezza);
			}	
			myDir4Tiles = new File(pathForTiles+File.separator+currZoomLevel);
			if(!myDir4Tiles.exists()) {
				myDir4Tiles.mkdir();
			}
			

			String finalFile = myDir4Tiles.getAbsolutePath()+File.separator+"img_%d."+srcExt;
			IMOperation op = new IMOperation();
			op.addImage(myDestImg);
			op.crop(tileDimension, tileDimension);
			op.p_repage();
			op.p_adjoin();
			op.addImage(finalFile);

			try {
				ConvertCmd convert = new ConvertCmd();
				convert.setSearchPath(imgBinPath);
				
				ArrayListOutputConsumer output = new ArrayListOutputConsumer();
				convert.setOutputConsumer(output);
				
				log.debug("### [tileImage] Eseguo \"convert "+op.toString()+"\" ###");
				convert.run(op);

				Integer filesPerRow = fileCounter(myPxLarghezza);
				Integer filesPerCol = fileCounter(myPxAltezza);
				Double totalFilesD = (Double)filesPerRow.doubleValue()*filesPerCol.doubleValue();
				Integer totalFiles = totalFilesD.intValue();

				log.debug("### [tileImage] myPxLarghezza="+myPxLarghezza+" myPxAltezza="+myPxAltezza+" filesPerRow="+filesPerRow+" filesPerCol="+filesPerCol+" totalFiles="+totalFiles+" ");

				Integer row = 0;
				Integer column = 0;
				for(Integer i=0;i<totalFiles;i++){
					String strFile = myDir4Tiles.getAbsolutePath()+File.separator+"img_"+i+"."+srcExt;
					String strFile2 = myDir4Tiles.getAbsolutePath()+File.separator+"img_"+column+"_"+row+"."+srcExt;
					File file = new File(strFile);
				    File file2 = new File(strFile2);
				    if(!file.renameTo(file2)){
						throw new Exception("[tileImage] Impossibile rinominare "+strFile+" in "+strFile2);
				    }
				    column++;
				    if(column>=filesPerRow){
				    	column=0;
				    	row++;
				    }
				}

			} catch (Exception ex) {
				throw new CommandException(ex);
			}
			myPxLarghezza = halfInteger(myPxLarghezza);
			myPxAltezza = halfInteger(myPxAltezza);
			
			currZoomLevel--;
		}
		hashRet.put("MapMaxZoomLevel", new BigDecimal(maxZoomLevel));
		hashRet.put("MapMinZoomLevel", new BigDecimal(currZoomLevel+1));
		hashRet.put("MapWidth", new BigDecimal(normalizedDim.getWidth()));
		hashRet.put("MapHeight", new BigDecimal(normalizedDim.getHeight()));
		hashRet.put("MapBorderWidth", new BigDecimal(normalizedDim.getBorderWidth()));
		hashRet.put("MapBorderHeight", new BigDecimal(normalizedDim.getBorderHeight()));

		log.debug("### [tileImage] Operazione terminata:");
		log.debug("### [tileImage] MapMaxZoomLevel: "+hashRet.get("MapMaxZoomLevel"));
		log.debug("### [tileImage] MapMinZoomLevel: "+hashRet.get("MapMinZoomLevel"));
		log.debug("### [tileImage] MapWidth: "+hashRet.get("MapWidth"));
		log.debug("### [tileImage] MapHeight: "+hashRet.get("MapHeight"));
		log.debug("### [tileImage] MapBorderWidth: "+hashRet.get("MapBorderWidth"));
		log.debug("### [tileImage] MapBorderHeight: "+hashRet.get("MapBorderHeight"));
		return hashRet;
	}

	/**
	 * Compress image.
	 *
	 * @param srcImg  src img
	 * @param destImg  dest img
	 * @param quality  quality
	 * @throws CommandException command exception
	 */
	private void compressImage(String srcImg, String destImg, Double quality) throws CommandException {

		IMOperation op = new IMOperation();
		op.addImage(srcImg);
		op.quality(quality);
		op.addImage(destImg);
		
		createAndRunConvertCmd("compressImage", op);
	}
	
	private void createAndRunConvertCmd(String methodName, IMOperation op) throws CommandException {
		try {
			ConvertCmd convert = new ConvertCmd();
			convert.setSearchPath(imgBinPath);			
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			convert.setOutputConsumer(output);
			
			log.info(ConvertWrapper.prependLogStrInizio + "### ["+methodName+"] Eseguo \"convert "+op.toString()+"\" ###");
			convert.run(op);
		} catch (Exception ex) {
			throw new CommandException(ex);
		}
	}
	
	/**
	 * Normalize image.
	 *
	 * @param srcImg  src img
	 * @param destImg  dest img
	 * @return image dimensions
	 * @throws CommandException command exception
	 * @throws InfoException info exception
	 */
	private ImageDimensions normalizeImage(String srcImg, String destImg) throws CommandException, InfoException{

		ImageDimensions imgDim = null;
		

		IdentifyWrapper imageInfo = new IdentifyWrapper(srcImg, imgBinPath);		
		Integer pxLarghezza = Integer.parseInt(imageInfo.getWidth());
		Integer pxAltezza = Integer.parseInt(imageInfo.getHeight());
		log.debug("[normalizeImage] Le dimensioni dell'immagine originale sono pxLarghezza="+pxLarghezza+"px X pxAltezza="+pxAltezza+"px");
		
		
		IMOperation op = new IMOperation();
//		StringBuilder buf = new StringBuilder();  // local buffer for option-args
		op.addImage(srcImg);
		
		boolean rotate=false;
		if(pxAltezza.compareTo(pxLarghezza)>=0){
			rotate=true;
			Integer oldPxAltezza = pxAltezza;
			pxAltezza = pxLarghezza;
			pxLarghezza = oldPxAltezza;
			op.rotate(new Double(90));
		}
		// al termine della rotazione l'altezza è la dimensione minore
		
		ImageDimensions imgDimNh = getPixelsHeight(pxLarghezza,pxAltezza);
		
		op.scale();
		op.addRawArgs(imgDimNh.getWidth()+"x"+imgDimNh.getHeight()+"!");
		
		
		ImageDimensions imgDimNw = getPixelsWidth(imgDimNh.getWidth(),imgDimNh.getHeight());
		
		Double borderDbl = Math.ceil((imgDimNw.getWidth().doubleValue()-imgDimNh.getWidth().doubleValue())/2);
		
		op.bordercolor("\"#dddddd\"");
		op.border(borderDbl.intValue(),0);
		
		pxLarghezza =  imgDimNw.getWidth();
		pxAltezza = imgDimNw.getHeight();
		
		imgDim = new ImageDimensions();
		imgDim.setWidth(pxLarghezza);
		imgDim.setHeight(pxAltezza);
		imgDim.setBorderWidth(borderDbl.intValue());
		imgDim.setBorderHeight(0);
		if(rotate){
			op.rotate(new Double(-90));
			Integer oldPxAltezza = pxAltezza;
			pxAltezza = pxLarghezza;
			pxLarghezza = oldPxAltezza;
			imgDim.setWidth(pxLarghezza);
			imgDim.setHeight(pxAltezza);
			imgDim.setBorderWidth(0);
			imgDim.setBorderHeight(borderDbl.intValue());
		}
		op.addImage(destImg);
		
		createAndRunConvertCmd("normalizeImage", op);

		return imgDim;
	}

	/**
	 * Scale image.
	 *
	 * @param srcImg  src img
	 * @param destImg  dest img
	 * @param width  width
	 * @param height  height
	 * @param modifier  modifier
	 * @throws CommandException command exception
	 */
	public void scaleImage(String srcImg, String destImg, Integer width, Integer height, String modifier) throws CommandException {

		IMOperation op = new IMOperation();
	    StringBuilder buf = new StringBuilder();  // local buffer for option-args
		op.addImage(srcImg);
		op.scale();

	    if (width != null) {
	        buf.append(width.toString());
	    }
	    if (height != null) {
	        buf.append("x");
	    }
	    if (height != null) {
	        buf.append(height.toString());
	    }
	    if (modifier != null) {
	        buf.append(modifier);
	    }
	    if (buf.length()>0) {
	    	op.addRawArgs(buf.toString());
	    }
		op.addImage(destImg);
		
		createAndRunConvertCmd("scaleImage", op);
	}
	
	/**
	 * Scale image.
	 *
	 * @param srcImg  src img
	 * @param destImg  dest img
	 * @param width  width
	 * @param height  height
	 * @throws CommandException command exception
	 */
	public void scaleImage(String srcImg, String destImg, Integer width, Integer height) throws CommandException {
		scaleImage(srcImg, destImg, width, height, null);
	}
	
	/**
	 * Density image.
	 *
	 * @param srcImg  src img
	 * @param destImg  dest img
	 * @param resample  density
	 * @throws CommandException command exception
	 */
	public void resampleImage(String srcImg, String destImg, Integer resample) throws CommandException{

		IMOperation op = new IMOperation();
		op.addImage(srcImg);
		op.resample(resample, resample);
		op.addImage(destImg);
		
		try {
			ConvertCmd convert = new ConvertCmd();			
			convert.setSearchPath(imgBinPath);			
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			convert.setOutputConsumer(output);
			
			log.info(ConvertWrapper.prependLogStrInizio + "### [resampleImage] Eseguo \"convert "+op.toString()+"\" ###");
			convert.run(op);

		} catch (Exception ex) {
			throw new CommandException(ex);
		}
	}
	
	/**
	 * Tiff image.
	 *
	 * @param srcImg  src img
	 * @param destImg  dest img
	 * @throws CommandException command exception
	 */
	public void tiffImage(String srcImg, String destImg) throws CommandException{

		IMOperation op = new IMOperation();
		op.alpha("set");
		op.autoLevel();
		op.autoGamma();
		op.compress("none");
		op.addImage(srcImg);
		op.addImage(destImg);
		
		createAndRunConvertCmd("tiffImage", op);
	}
	
	/**
	 * Pdf images.
	 *
	 * @param srcImgs  src imgs
	 * @param destImg  dest img
	 * @throws CommandException command exception
	 */
	public void pdfImages(String[] srcImgs, String destImg) throws CommandException {

		IMOperation op = new IMOperation();
		for(int i=0;i<srcImgs.length;i++){
			op.addImage(srcImgs[i]);
		}
		op.compress("jpeg");
		op.quality(0.75);		
		op.addImage(destImg);
		
		createAndRunConvertCmd("pdfImages", op);
	}
	
	public void pdfImage(String srcImg, String destImg) throws CommandException {

		IMOperation op = new IMOperation();
		
		op.addImage(srcImg);
		
		op.compress("jpeg");
		op.quality(0.75);		
		op.addImage(destImg);
		
		createAndRunConvertCmd("pdfImage", op);
	}
	
	/**
	 * Class ImageDimensions.
	 */
	public class ImageDimensions {
		
		/** width. */
		private Integer width;
		
		/** height. */
		private Integer height;
		
		/** border width. */
		private Integer borderWidth;
		
		/** border height. */
		private Integer borderHeight;
		
		/**
		 * Istanzia un nuovo image dimensions.
		 */
		public ImageDimensions() {
			// do nothing...
		}
		
		/**
		 * Istanzia un nuovo image dimensions.
		 *
		 * @param width  width
		 * @param height  height
		 */
		public ImageDimensions(Integer width, Integer height) {
			this.width = width;
			this.height = height;
		}
		
		/**
		 * Istanzia un nuovo image dimensions.
		 *
		 * @param width  width
		 * @param height  height
		 * @param borderWidth  border width
		 * @param borderHeight  border height
		 */
		public ImageDimensions(Integer width, Integer height, Integer borderWidth, Integer borderHeight) {
			this.width = width;
			this.height = height;
			this.borderWidth = borderWidth;
			this.borderHeight = borderHeight;
		}
		
		/**
		 * Legge width.
		 *
		 * @return the width
		 */
		public Integer getWidth() {
			return width;
		}
		
		/**
		 * Imposta width.
		 *
		 * @param width the new width
		 */
		public void setWidth(Integer width) {
			this.width = width;
		}
		
		/**
		 * Legge height.
		 *
		 * @return the height
		 */
		public Integer getHeight() {
			return height;
		}
		
		/**
		 * Imposta height.
		 *
		 * @param height the new height
		 */
		public void setHeight(Integer height) {
			this.height = height;
		}
		
		/**
		 * Legge border width.
		 *
		 * @return the border width
		 */
		public Integer getBorderWidth() {
			return borderWidth;
		}
		
		/**
		 * Imposta border width.
		 *
		 * @param borderWidth the new border width
		 */
		public void setBorderWidth(Integer borderWidth) {
			this.borderWidth = borderWidth;
		}
		
		/**
		 * Legge border height.
		 *
		 * @return the border height
		 */
		public Integer getBorderHeight() {
			return borderHeight;
		}
		
		/**
		 * Imposta border height.
		 *
		 * @param borderHeight the new border height
		 */
		public void setBorderHeight(Integer borderHeight) {
			this.borderHeight = borderHeight;
		}
	}
	
	/**
	 * Half integer.
	 *
	 * @param pixel  pixel
	 * @return integer
	 */
	public Integer halfInteger(Integer pixel){
		Double myDouble = new Double(2);
		Double ratio = pixel.doubleValue()/myDouble;
		Long rRes = Math.round(ratio);
		return rRes.intValue();
	}
	
	/**
	 * File counter.
	 *
	 * @param pixel  pixel
	 * @return integer
	 */
	public Integer fileCounter(Integer pixel){
		Double ratio = pixel.doubleValue()/tileDimension.doubleValue();
		Double rRes = Math.ceil(ratio);
		return rRes.intValue();
	}
}
