import chisel3._
import chisel3.iotesters._


class STA_VDBB_Tester(c: STA_VDBB) extends PeekPokeTester(c) {
  //matrices A 4 * 16
  val matrices_A = Array(
    Array(1,1,1,1, 1,1,1,1, 1,1,1,1, 1,1,1,1),
    Array(1,1,1,1, 1,1,1,1, 1,1,1,1, 1,1,1,1),
    Array(1,1,1,1, 1,1,1,1, 1,1,1,1, 1,1,1,1),
    Array(1,1,1,1, 1,1,1,1, 1,1,1,1, 1,1,1,1)
  )

  //matrices A 8 * 16
  val matrices_B = Array(
    Array(1,0,0,0, 0,0,0,0,  2,0,0,0, 0,0,0,0),
    Array(2,0,0,0, 0,0,0,0,  3,0,0,0, 0,0,0,0),
    Array(3,0,0,0, 0,0,0,0,  4,0,0,0, 0,0,0,0),
    Array(4,0,0,0, 0,0,0,0,  5,0,0,0, 0,0,0,0),
    Array(5,0,0,0, 0,0,0,0,  6,0,0,0, 0,0,0,0),
    Array(6,0,0,0, 0,0,0,0,  7,0,0,0, 0,0,0,0),
    Array(7,0,0,0, 0,0,0,0,  8,0,0,0, 0,0,0,0),
    Array(8,0,0,0, 0,0,0,0,  9,0,0,0, 0,0,0,0)
  )


  //poke
  println("matrices A: ")
  for (i <- 0 until 4){
    print("| ")
    for (j <- 0 until 16){
      poke(c.io.in_A(i)(j), matrices_A(i)(j)*(i+1))
      print(" " + matrices_A(i)(j)*(i+1) + " ")
    }
    println("| ")
  }
  println("matrices B: ")
  for (i <- 0 until 8){
    print("| ")
    for (j <- 0 until 16){
      poke(c.io.in_B(i)(j), matrices_B(i)(j))
      print(" " + matrices_B(i)(j) + " ")
    }
    println("| ")
  }

  println("Expected:")
  val result_exp = Array(
    Array(0, 0, 0, 0, 0, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 0, 0))

  for (i <- 0 until 4) {
    print("| ")
    for(j <- 0 until 8) {
      for(k <- 0 until 16) {
        result_exp(i)(j) = result_exp(i)(j) + matrices_A(i)(k) * (i+1) * matrices_B(j)(k)
      }
      print(" " + result_exp(i)(j).toString + " ")
    }
    println("| ")
  }

  println("Actually:")
  poke(c.io.in_cal, false.B)
  step(1)
  poke(c.io.in_cal, true.B)
  step(1)
  poke(c.io.in_cal, false.B)
  step(1)

  step(300)
  for (i <- 0 until 4) {
    print("| ")
    for(j <- 0 until 8) {
      print(" " + peek(c.io.out_C(i)(j)).toString + " ")
    }
    println("| ")
  }




}

object STA_VDBB_Tester extends App {
  chisel3.iotesters.Driver(() => new STA_VDBB()){c =>
    new STA_VDBB_Tester(c)
  }
}