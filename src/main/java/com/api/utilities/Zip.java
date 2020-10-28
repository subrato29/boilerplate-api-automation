package com.api.utilities;  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip extends Util{
		static String screenshotPath;
		private static String zipFile = "Results/Reports.zip";
		private static String[] srcFiles = {htmlReportPathGenerated()};
		public static void zipFile() {
			htmlReportPathGenerated();
			System.out.println("Zipping the files");
			try {
				byte[] buffer = new byte[1024];
				FileOutputStream fos = new FileOutputStream(zipFile);
				ZipOutputStream zos = new ZipOutputStream(fos);
				for (int i=0; i < srcFiles.length; i++) {
					File srcFile = new File(srcFiles[i]);
					FileInputStream fis = new FileInputStream(srcFile);
					zos.putNextEntry(new ZipEntry(srcFile.getName()));
					int length;
					while ((length = fis.read(buffer)) > 0) {
						zos.write(buffer, 0, length);
					}
					zos.closeEntry();
					fis.close();
				}
				zos.close();
			}
			catch (IOException ioe) {
				//System.out.println("Error creating zip file: " + ioe);
			}
		
	}

}