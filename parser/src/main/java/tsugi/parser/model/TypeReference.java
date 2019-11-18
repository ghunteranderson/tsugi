package tsugi.parser.model;

import java.util.ArrayList;
import java.util.List;

public class TypeReference {

	public List<String> parts = new ArrayList<>();
	
	public void add(String qualifier) {
		parts.add(qualifier);
	}
	
	@Override
	public String toString() {
		StringBuilder bob = new StringBuilder();
		for(int i=0; i<parts.size()-1; i++) {
			bob.append(parts.get(i)).append('.');
		}
		bob.append(parts.get(parts.size()-1));
		return bob.toString();
	}
}
