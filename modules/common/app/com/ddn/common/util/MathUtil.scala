package com.ddn.common.util

/**
 * User: bigfish
 * Date: 16-1-31
 * Time: 下午3:27
 */
object MathUtil {

   def round(n:Double, digits:Int = 2) = {
     BigDecimal(n).setScale(digits, BigDecimal.RoundingMode.HALF_UP).toDouble
   }
}
