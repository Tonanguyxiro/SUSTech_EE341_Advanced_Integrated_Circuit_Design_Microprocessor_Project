import chisel3._


/**
 * Description: tag the input data
 *
 * Inputs:
 * in_data: input data, 8 ints
 *
 * Regs:
 * tag 8 ints
 *
 * Outputs:
 *
 * out_tag, out_count
 *
 * Function:
 *
 * Author: YUAN Tong
 * Version: V2.0
 * Date: 3/12/2020
 *
 */

class tag extends Module{
  val io = IO(new Bundle {
    val in_data = Input(Vec(8, SInt(32.W)))
    val out_tag = Output(Vec(8, Bool()))
    val out_count = Output(UInt(4.W))
  })

  val tag = RegInit(Vec(Seq.fill(8)(false.B)))
  val count = RegInit(0.U(4.W))

  for (i <- 0 until 7) {
    if (io.in_data != 0.S) {
      tag(i) := true.B
      count := count + 1.U
    }
  }

  io.out_tag := tag
  io.out_count := count
}

//object Main {
//  def main(args: Array[String]): Unit = {
//    println("tag main function")
//    chisel3.Driver.execute(args, () => new tag)
//  }
//}

//run --target-dir generated --compiler verilog

