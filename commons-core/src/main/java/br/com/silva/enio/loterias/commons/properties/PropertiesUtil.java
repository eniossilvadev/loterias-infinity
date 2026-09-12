package br.com.silva.enio.loterias.commons.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.silva.enio.loterias.commons.download.ReplacingInputStream;
import org.apache.commons.lang3.StringUtils;

public class PropertiesUtil {

	private static final Properties prop = new Properties();

	private static InputStream input = null;

	private static final PropertiesUtil instance = new PropertiesUtil();

	public static PropertiesUtil getInstance() {
		return instance;
	}

	private PropertiesUtil() {
		new PropertiesUtil("config.properties");
	}

	// private constructor to avoid client applications to use constructor
	private PropertiesUtil(String properties) {

		try {
			input = PropertiesUtil.class.getClassLoader().getResourceAsStream(properties);
			if (input == null) {
				System.out.println("Sorry, unable to find " + properties);
				return;
			}

			prop.load(new ReplacingInputStream(input, "\\", "\\\\"));

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String getProperty(String key) {
		String value = prop.getProperty(key);
		System.out.println("Property Value: " + value);
		return StringUtils.trim(value);
	}

	public Integer getPropertyInteger(String key) {
		Integer retorno = Integer.valueOf(0);
		String value = prop.getProperty(key);
		try {
			retorno = Integer.valueOf(value);
		} catch (Exception e) {
			System.out.println("Property Value: " + e.getMessage());
		} finally {
			System.out.println("Property Value: " + retorno);
		}
		return retorno;
	}

}