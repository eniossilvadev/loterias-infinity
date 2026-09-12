package br.com.silva.enio.loterias.commons.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import br.com.silva.enio.loterias.commons.download.ArquivoUtil;

public class ZipUtil {

	@SuppressWarnings("unchecked")
	public static final void unzip(File zipFileName) {

		ZipFile zipFile;
		try {
			zipFile = new ZipFile(zipFileName);

			Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (entry.isDirectory()) {
					String name = zipFileName.getParentFile() + File.separator + entry.getName();
					System.out.println("Creating dir " + name);
					(new File(name)).mkdir();
				}
				String file = zipFileName.getParentFile() + File.separator + entry.getName();
				System.out.println(file);
				ArquivoUtil.copytInputStream(zipFile.getInputStream(entry),
				        new BufferedOutputStream(new FileOutputStream(file)));
			}
			zipFile.close();

		} catch (ZipException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	public static final void unzip(String zipFileName) {
		try {
			unzip(new File(zipFileName));
		} catch (Exception e) {
			System.out.println("No more zip");
		}
	}

}
