package tsugi.parser.model;

import java.util.List;

import lombok.Data;

@Data
public class FunctionInvocation {
	private String functionName;
	private List<Value> arguments;
}
