package it.lispa.bdl;

import it.lispa.bdl.commons.utils.ConvertWrapper;

public class TestConvertWrapper {
	public static void main(String[] args) throws Exception {
		System.out.println("######### TestConvert #########");
		test();
	}
	private static void test() throws Exception {
		final String pathImg = "C:\\sencha\\ImageMagick\\bdl\\native.jpg";
		final String pathDirImg = "C:\\sencha\\ImageMagick\\bdl\\map";
		final String imgBinPath = "C:\\sencha\\ImageMagick";
		
		ConvertWrapper convertWrapper = new ConvertWrapper(imgBinPath);
		convertWrapper.tileImage(pathImg, pathDirImg);
	}
}
