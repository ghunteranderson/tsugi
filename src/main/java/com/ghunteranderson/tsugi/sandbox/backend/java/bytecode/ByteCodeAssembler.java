package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import com.ghunteranderson.tsugi.sandbox.utils.BinaryWriter;

public class ByteCodeAssembler {

  public BinaryWriter assemble(JClassFile classFile) {
    var ctx = buildContext(classFile);
    var out = new BinaryWriter();
    writeClassFile(ctx, out);
    return out;
  }

  private void writeClassFile(AssemblerContext ctx, BinaryWriter out) {
    var classFile = ctx.classFile;

    out.writeU4(0xCAFEBABE); // magic
    out.writeU2(0); // minor_version
    out.writeU2(55); // major_version
    var constantPoolWriter = out.insertLiveWriter();
    out.writeU2(buildAccessFlags(classFile.accessFlags)); // access_flags
    out.writeU2(ctx.constantPool.findConstant(classFile.thisClass)); // this_class
    out.writeU2(ctx.constantPool.findConstant(classFile.superClass)); // super_class
    writeInterfaces(ctx, out);
    writeFields(ctx, out);
    writeMethods(ctx, out);
    writeAttributes(ctx, out, classFile.attributes);

    // Delaying constant pool since it's lazy built
    writeConstantPool(ctx, constantPoolWriter);
  }

  private short buildAccessFlags(Collection<JAccessFlags> accessFlags){
    short value = 0;
    for(var flag : accessFlags)
      value |= flag.getFlag();
    return value;
  }
  
  private AssemblerContext buildContext(JClassFile classFile) {
    var ctx = new AssemblerContext();
    ctx.classFile = classFile;
    ctx.constantPool = new ConstantPool();

    return ctx;
  }

  private void writeConstantPool(AssemblerContext ctx, BinaryWriter out) {
    var sizeWriter = out.insertLiveWriter();

    var cp = ctx.constantPool.values();
    // The size of cp changes may grow as we lookup nested constants
    for (int i = 1; i < cp.size(); i++) {
      writeConstantPoolInfo(ctx, out, cp.get(i));
    }

    // Final size isn't known until traversing all of the constants
    sizeWriter.writeU2(cp.size());
  }

  private void writeConstantPoolInfo(AssemblerContext ctx, BinaryWriter out, JConst_Info entry) {
    out.writeU1(entry.tag.getCode());

    if (entry instanceof JConst_ClassInfo classInfo) {
      int nameIndex = ctx.constantPool.findConstant(classInfo.name);
      out.writeU2(nameIndex);
    } else if (entry instanceof JConst_FieldRefInfo ref) {
      int classIndex = ctx.constantPool.findConstant(ref.clazz);
      int nameAndTypeIndex = ctx.constantPool.findConstant(ref.nameAndType);
      out.writeU2(classIndex);
      out.writeU2(nameAndTypeIndex);
    } else if (entry instanceof JConst_MethodRefInfo ref) {
      int classIndex = ctx.constantPool.findConstant(ref.clazz);
      int nameAndTypeIndex = ctx.constantPool.findConstant(ref.nameAndType);
      out.writeU2(classIndex);
      out.writeU2(nameAndTypeIndex);
    } else if (entry instanceof JConst_InterfaceMethodRefInfo ref) {
      int classIndex = ctx.constantPool.findConstant(ref.clazz);
      int nameAndTypeIndex = ctx.constantPool.findConstant(ref.nameAndType);
      out.writeU2(classIndex);
      out.writeU2(nameAndTypeIndex);
    } else if (entry instanceof JConst_StringInfo stringInfo) {
      int stringIndex = ctx.constantPool.findConstant(stringInfo.string);
      out.writeU2(stringIndex);
    } else if (entry instanceof JConst_IntegerInfo integerInfo) {
      out.writeU4(integerInfo.value);
    } else if (entry instanceof JConst_FloatInfo floatInfo) {
      int bytes = Float.floatToIntBits(floatInfo.value);
      out.writeU4(bytes);
    } else if (entry instanceof JConst_LongInfo longInfo) {
      int highBytes = (int) longInfo.value >> 32;
      int lowBytes = (int) longInfo.value;
      out.writeU4(highBytes);
      out.writeU4(lowBytes);
    } else if (entry instanceof JConst_DoubleInfo doubleInfo) {
      long bytes = Double.doubleToLongBits(doubleInfo.value);
      int highBytes = (int) bytes >> 32;
      int lowBytes = (int) bytes;
      out.writeU4(highBytes);
      out.writeU4(lowBytes);
    } else if (entry instanceof JConst_NameAndTypeInfo nameAndTypeInfo) {
      int nameIndex = ctx.constantPool.findConstant(nameAndTypeInfo.name);
      int descriptorIndex = ctx.constantPool.findConstant(nameAndTypeInfo.descriptor);
      out.writeU2(nameIndex);
      out.writeU2(descriptorIndex);
    } else if (entry instanceof JConst_Utf8Info utf8Info) {
      var bytes = utf8Info.value.getBytes(StandardCharsets.UTF_8);
      out.writeU2(bytes.length);
      out.writeAll(bytes);
    } else if (entry instanceof JConst_MethodHandleInfo handleInfo) {
      var referenceIndex = ctx.constantPool.findConstant(handleInfo.reference);
      out.writeU1(handleInfo.kind.getValue());
      out.writeU2(referenceIndex);
    } else if (entry instanceof JConst_MethodTypeInfo methodTypeInfo) {
      var descriptorIndex = ctx.constantPool.findConstant(methodTypeInfo.descriptor);
      out.writeU2(descriptorIndex);
    } else {
      throw new ByteCodeAssemblyException("Assembler does not recognize constant pool entry: " + entry);
    }
  }

  private void writeInterfaces(AssemblerContext ctx, BinaryWriter out){
    var classFile = ctx.classFile;
    out.writeU2(classFile.interfaces.size());
    for(var interfce : classFile.interfaces){
      out.writeU2(ctx.constantPool.findConstant(interfce));
    }
  }

  private void writeFields(AssemblerContext ctx, BinaryWriter out){
    var fields = ctx.classFile.fields;
    out.writeU2(fields.size());
    for(var field : fields){
      writeField(ctx, out, field);
    }
  }

  private void writeField(AssemblerContext ctx, BinaryWriter out, JFieldInfo field) {
    var flags = buildAccessFlags(field.accessFlags);
    var nameIndex = ctx.constantPool.findConstant(field.name);
    var descriptorIndex = ctx.constantPool.findConstant(field.descriptor);
    
    out.writeU2(flags);
    out.writeU2(nameIndex);
    out.writeU2(descriptorIndex);
    writeAttributes(ctx, out, field.attributes);
  }

  private void writeMethods(AssemblerContext ctx, BinaryWriter out) {
    var methods = ctx.classFile.methods;
    out.writeU2(methods.size());
    for(var method : methods){
      writeMethod(ctx, out, method);
    }
  }

  private void writeMethod(AssemblerContext ctx, BinaryWriter out, JMethodInfo method){
    var flags = buildAccessFlags(method.accessFlags);
    var nameIndex = ctx.constantPool.findConstant(method.name);
    var descriptorIndex = ctx.constantPool.findConstant(method.descriptor);

    out.writeU2(flags);
    out.writeU2(nameIndex);
    out.writeU2(descriptorIndex);
    writeAttributes(ctx, out, method.attributes);
  }

  private void writeAttributes(AssemblerContext ctx, BinaryWriter out, List<JAttr_Info> attributes) {
    out.writeU2(attributes.size());
    for(var attribute : attributes){
      writeAttribute(ctx, out, attribute);
    }
  }

  private void writeAttribute(AssemblerContext ctx, BinaryWriter out, JAttr_Info attribute){
    // attribute_name_index
    var attributeNameIndex = ctx.constantPool.findConstant(new JConst_Utf8Info(attribute.attributeName));
    out.writeU2(attributeNameIndex);

    // attribute_length place holder
    var attrLengthWriter = out.insertLiveWriter();
    
    // info
    var sizeBeforeInfo = out.size();
    writeAttributeInfo(ctx, out, attribute);
    var attributeLength = out.size() - sizeBeforeInfo;
    
    // retroactively insert attribute length (in bytes)
    attrLengthWriter.writeU4(attributeLength);
    
  }

  private void writeAttributeInfo(AssemblerContext ctx, BinaryWriter out, JAttr_Info attribute){
    if(attribute instanceof JAttr_ConstantValue constantValue){
      var constantValueIndex = ctx.constantPool.findConstant(constantValue.constantValue);
      out.writeU2(constantValueIndex);
    }
    else if(attribute instanceof JAttr_Code code) {
      var maxStack = code.maxStack;
      var maxLocals = code.maxLocals;

      out.writeU2(maxStack);
      out.writeU2(maxLocals);

      var codeSizeWriter = out.insertLiveWriter();
      var sizeBeforeCode = out.size();
      writeCode(ctx, out, code);
      var codeSize = out.size() - sizeBeforeCode;
      codeSizeWriter.writeU4(codeSize);

      out.writeU2(0); // Skipping the exception table
      writeAttributes(ctx, out, code.attributes);
    }
    else
      throw new ByteCodeAssemblyException("Unrecognized attribute type: " + attribute.getClass().getName());
  }

  private void writeCode(AssemblerContext ctx, BinaryWriter out, JAttr_Code code){
    var instAssembler = new InstructionAssembler();
    instAssembler.assemble(ctx, out, code.code);
  }
}
