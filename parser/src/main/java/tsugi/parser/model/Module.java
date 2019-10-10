package tsugi.parser.model;

import java.util.List;

import lombok.Data;

@Data
public class Module {
	private String name;
	private List<ModuleComponent> components;
}
