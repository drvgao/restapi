package com.rest.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

	public static String getXMLString(String path) throws IOException {
			return new String(Files.readAllBytes(Paths.get(path)));
	}
	
}
