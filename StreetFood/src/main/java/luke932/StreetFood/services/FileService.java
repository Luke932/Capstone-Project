package luke932.StreetFood.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class FileService {

	private static final String FILE_UPLOAD_PATH = "C:\\Users\\Luca\\Desktop\\Luca\\fileServer";

	public void saveFile(String fileName, byte[] fileData) throws IOException {
		Path filePath = Paths.get(FILE_UPLOAD_PATH, fileName);
		Files.write(filePath, fileData);
	}

	public byte[] getFile(String fileName) throws IOException {

		String filePath = FILE_UPLOAD_PATH + File.separator + fileName;

		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException("File not found: " + fileName);
		}

		return Files.readAllBytes(file.toPath());
	}

}
