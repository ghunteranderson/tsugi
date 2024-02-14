package com.ghunteranderson.java.bytecode;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.ghunteranderson.java.utils.BinaryWriter;
import com.ghunteranderson.java.utils.Table;

public class InstructionAssembler {

  public void assemble(AssemblerContext ctx, BinaryWriter out, List<JInst> code) {
    var nextByteIndex = new AtomicInteger(0);

    var instTable = new Table<>(code, c -> {
      var meta = new InstMetadata();
      meta.inst = c;
      meta.byteIndex = nextByteIndex.getAndAdd(getInstSize(meta.inst));
      return meta;
    });

    writeInstruction(ctx, out, instTable);

  }

  private BinaryWriter writeInstruction(AssemblerContext ctx, BinaryWriter out, Table<JInst, InstMetadata> code) {
    var cp = ctx.constantPool;
    
    for(var meta : code.values()){
      var instruction = meta.inst;

      if(instruction instanceof JInst_ConstPoolArg inst){
        out.writeU1(inst.opcode.code);
        out.writeU2(cp.findConstant(inst.constant));
      }
      else if(instruction instanceof JInst_InstArg inst){
        out.writeU1(inst.opcode.code);
        out.writeU4(code.get(inst.instruction).byteIndex);
      }
      else if(instruction instanceof JInst_NoArgs inst){
        out.writeU1(inst.opcode.code);
      }

    }
    return out;
  }

  private int getInstSize(JInst inst) {
    if (inst instanceof JInst_ConstPoolArg) {
      // Some instructions like ldc/ldc_w allow this to be compressed
      // For now, we assume worse case
      return 3;
    } else if (inst instanceof JInst_LiteralArg i) {
      // TODO: Lookup this value based on the instruction provided
      throw new ByteCodeAssemblyException("Unknown instruction length " + i);
    } else if (inst instanceof JInst_LocalArg) {
      // opcode + local
      return 2;
    } else if (inst instanceof JInst_NoArgs) {
      return 1;
    }

    throw new ByteCodeAssemblyException("Could not determine length of instruction:" + inst);
  }

  private static class InstMetadata {
    private int byteIndex;
    private JInst inst;
  }

}
