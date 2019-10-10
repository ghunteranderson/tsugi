package compiler;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

import static org.apache.bcel.Const.*;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.InstructionConst;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.Type;

public class BCELSandbox {

	public static void main(String[] args) throws Exception {
		
		ClassGen  cg = new ClassGen(
				"HelloWorld",
				"java.lang.Object",
		        "<generated>",
		        ACC_PUBLIC | ACC_SUPER,
		        null);
		cg.setMajor(MAJOR_11);
		cg.setMinor(MINOR_11);
		
		ConstantPoolGen cp = cg.getConstantPool();
		InstructionList il = buildInstructions(cp);
		MethodGen mg = new MethodGen(
				ACC_STATIC | ACC_PUBLIC,
		        Type.VOID,
		        new Type[] { new ArrayType(Type.STRING, 1) }, 
		        new String[] { "argv" },
		        "main",
		        "HelloWorld",
		        il,
		        cp);
		mg.setMaxStack();
		cg.addMethod(mg.getMethod());
		cg.setConstantPool(cp);
		il.dispose(); // Reuse instruction handles of list
		
		JavaClass javaClass = cg.getJavaClass();
		MyClassLoader loader = new MyClassLoader();
		byte[] bytes = javaClass.getBytes();
		saveClass("HelloWorld.class", bytes);
		runMain(loader.defineClass("HelloWorld", bytes));
	}
	
	private static void saveClass(String name, byte[] bytes) throws Exception{
		try(FileOutputStream fos = new FileOutputStream(name)){
			fos.write(bytes);
		}
	}
	
	private static void runMain(Class<?> clazz) throws Exception {
		Method m = clazz.getMethod("main", String[].class);
		m.invoke(null, new Object[] {new String[0]});
	}
	
	private static InstructionList buildInstructions(ConstantPoolGen cp) {
		int outField = cp.addFieldref("java/lang/System", "out", "Ljava/io/PrintStream;");
		int helloWorldString = cp.addString("Hello World!");
		int printLnMethod = cp.addMethodref("java/io/PrintStream", "println", "(Ljava/lang/String;)V");
		
		var instructions = new InstructionList();
		instructions.append(new GETSTATIC(outField));
		instructions.append(new LDC(helloWorldString));
		instructions.append(new INVOKEVIRTUAL(printLnMethod));
		instructions.append(InstructionConst.RETURN);
		return instructions;
	}
	
	private static class MyClassLoader extends ClassLoader {
		public Class<?> defineClass(String name, byte[] bytes) {
			return defineClass(name, bytes, 0, bytes.length);
		}
	}
}
