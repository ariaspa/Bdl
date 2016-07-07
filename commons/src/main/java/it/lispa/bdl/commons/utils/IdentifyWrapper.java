package it.lispa.bdl.commons.utils;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.core.InfoException;
import org.im4java.process.ArrayListOutputConsumer;

/**
 * Class IdentifyWrapper.
 */
public class IdentifyWrapper {
	
	/** width. */
	private String width;
	
	/** height. */
	private String height;
	
	/** resolutionw. */
	private String resolutionw;
	
	/** resolutionh. */
	private String resolutionh;
	
	/** search path. */
	private String searchPath;

	/** log. */
	private static Logger log = Logger.getLogger(IdentifyWrapper.class);
	
	/**
	 * Istanzia un nuovo identify wrapper.
	 *
	 * @param pImage image
	 * @param path  path
	 * @throws InfoException info exception
	 */
	public IdentifyWrapper(String pImage,String path) throws InfoException {
		log.debug("[IdentifyWrapper] entro nel constructor con pImage="+pImage+" --- path="+path);
		searchPath = path;
		getBaseInfo(pImage);
	}

	/**
	 * Legge base info.
	 *
	 * @param pImage image
	 * @return base info
	 * @throws InfoException info exception
	 */
	private void getBaseInfo(String pImage) throws InfoException {
		log.debug("[getBaseInfo] entro con pImage="+pImage);
		// create operation
		IMOperation op = new IMOperation();
		op.ping();
		op.format("%[width]\n%[height]\n%[fx:resolution.x]\n%[fx:resolution.y]");
		op.addImage(pImage);

		try {
			// executing identify -format "%w\n%h\n%x\n%y" "$f"
			IdentifyCmd identify = new IdentifyCmd();
			identify.setSearchPath(searchPath);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identify.setOutputConsumer(output);

			log.debug("### [getBaseInfo] eseguo \""+op.toString().replace("\n", "")+"\" ###");
			
			identify.run(op);

			// ... and parse result
			List<String> cmdOutput = output.getOutput();
			Iterator<String> iter = cmdOutput.iterator();
			width = iter.next();
			height = iter.next();
			resolutionw = iter.next();
			resolutionh = iter.next();
			
			if(width==null){
				throw new InfoException("Width null for image "+pImage);
			}
			
			if(height==null){
				throw new InfoException("Height null for image "+pImage);
			}
			
			if(resolutionw==null){
				throw new InfoException("Resolutionw null for image "+pImage);
			}
			
			if(resolutionh==null){
				throw new InfoException("Resolutionh null for image "+pImage);
			}
			log.debug("[getBaseInfo] width="+width+" height="+height+" resolutionw="+resolutionw+" resolutionh="+resolutionh);

			
		} catch (Exception ex) {
			throw new InfoException(ex);
		}
	}

	/**
	 * Legge width.
	 *
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * Legge height.
	 *
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * Legge resolutionw.
	 *
	 * @return the resolutionw
	 */
	public String getResolutionw() {
		return resolutionw;
	}

	/**
	 * Legge resolutionh.
	 *
	 * @return the resolutionh
	 */
	public String getResolutionh() {
		return resolutionh;
	}

	/**
	 * Legge search path.
	 *
	 * @return the search path
	 */
	public String getSearchPath() {
		return searchPath;
	}

	/**
	 * Imposta search path.
	 *
	 * @param searchPath the new search path
	 */
	public void setSearchPath(String searchPath) {
		this.searchPath = searchPath;
	}

}
