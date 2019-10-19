package tsugi.test.fblt;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DynamicTest;

public class FileBasedTsugiTestFactory {
	
	public Collection<DynamicTest> fromFolder(String folder, String verifyFileExtension, Consumer<TestFiles> testExecution){
		var testDescriptions = loadTests(folder, verifyFileExtension);
		var tests = new ArrayList<DynamicTest>(testDescriptions.size());
		
		for(TestFiles desc : testDescriptions) {
			var test = DynamicTest.dynamicTest(
					baseName(desc.getSourceFile()),
					URI.create(desc.getSourceFile()),
					() -> {testExecution.accept(desc);}
					);
			tests.add(test);
		}
		
		return tests;
	}
	
	private List<TestFiles> loadTests(String folder, String verifyFileExtension) {
		
		
		try {
			
			ClassLoader load = Thread.currentThread().getContextClassLoader();
			File dir = new File(load.getResource(folder).getPath());
			
			
			List<File> files = Arrays.asList(dir.listFiles());
			
			List<File> sourceFiles = files.stream()
					.filter(f -> f.getName().endsWith(".tsugi"))
					.collect(Collectors.toList());
			Map<String, File> tokenFiles = files.stream()
					.filter(f -> f.getName().endsWith(verifyFileExtension))
					.collect(Collectors.toMap(f -> baseName(f.getName()), f -> f));
			
			List<TestFiles> testFilesList = new ArrayList<>();
			for(int i=0; i<sourceFiles.size(); i++) {
				String sourceFileName = sourceFiles.get(i).getName();
				String tokensFileName = tokenFiles.get(baseName(sourceFileName)).getName();
				if(tokensFileName != null) {
					var testFiles = new TestFiles();
					testFiles.setSourceFile(folder + "/" + sourceFileName);
					testFiles.setVerifyFile(folder + "/" + tokensFileName);
					testFilesList.add(testFiles);
				}
			}
			return testFilesList;
			
		} catch(Exception ex) {
			throw new AssertionError("Could not open " + folder, ex);
		}
	}
	
	private String baseName(String fileName) {
		return fileName.split("\\.")[0];
	}
	
	

	/*
	private Set<String> getFiles(String folderName) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    URL url = loader.getResource(folderName);
	    String fullPath = url.getPath();
	    File dir  = new File(fullPath);
	    
	    if(!dir.exists())
	    	throw new IllegalArgumentException("Directory not found: " + fullPath);
	    if(!dir.isDirectory())
	    	throw new IllegalArgumentException("Path is not a directory: " + fullPath);
	    
	    return getAllFiles(dir, dir.getName());
	}
	
	private Set<String> getAllFiles(File directory, String base) {
		var names = new HashSet<String>();
		for(File file : directory.listFiles()) {
			String name = base + "/" + file.getName();
			if(file.isFile())
				names.add(name);
			else
				names.addAll(getAllFiles(file, base));
		}
		return names;
    }
	
	private List<TestFilePair> findTestCases(Collection<String> files){
		files = files.stream()
				.filter(s -> s.endsWith(".code") || s.endsWith(".tokens"))
				.sorted()
		filesCopy.sort(null);
		
		
	}
	
	private String buildTestName
	
	@Data
	@RequiredArgsConstructor
	private static class TestFilePair {
		private final String testName;
		private final String inputFile;
		private final String verifyFile;
	} */

}
