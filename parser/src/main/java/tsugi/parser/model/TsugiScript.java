package tsugi.parser.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TsugiScript extends ModuleComponent {
	private List<Statement> statements;
}
