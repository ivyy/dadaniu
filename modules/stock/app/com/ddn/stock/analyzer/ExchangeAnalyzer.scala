package com.ddn.stock.analyzer

import javax.inject.{Inject, Singleton}

import com.ddn.common.util.MathUtil
import com.ddn.stock.feeder.Feeder

@Singleton
class ExchangeAnalyzer @Inject()(feeder: Feeder) {

  def close(stockCode: String): Double = {
    feeder.fetchAll(stockCode)(0).close
  }


  def open(stockCode: String): Double = {
    feeder.fetchAll(stockCode)(0).open
  }


  def high(stockCode: String): Double = {
    feeder.fetchAll(stockCode)(0).high
  }


  def low(stockCode: String): Double = {
    feeder.fetchAll(stockCode)(0).low
  }


  def volume(stockCode: String): Double = {
    feeder.fetchAll(stockCode)(0).volume
  }

  /**
   * Get the average close price of latest day
   * @param stockCode
   * @param day
   * @return the average close price
   */
  def average(stockCode: String, days: Int = 5): Double = {
    feeder.fetchAll(stockCode).take(days).map(_.close).sum / days
  }


  def simpleMovingAverage(stockCode: String, days: Int = 5)(range: Int = 120): Array[Double] = {

    val array = feeder.fetchAll(stockCode).take(range + days - 1).reverse

    val ma = new Array[Double](range)

    var j = 0

    var sum = 0d

    for (i <- 0 until array.length) {
      sum += array(i).close
      if (i >= days - 1) {
        ma(j) = sum / days
        sum -= array(j).close
        j += 1
      }
    }

    ma

  }

  def weightedMovingAverage(stockCode: String, days: Int = 5)(range: Int = 120): Array[Double] = {

    val array = feeder.fetchAll(stockCode).take(range + days - 1).reverse

    val wma = new Array[Double](range)

    val sumOfDenominator = (days * (days + 1)) / 2

    var sumOfNumerator = 0d
    var lastSumOfNumerator = sumOfNumerator

    var sumOfWeightedNumerator = 0d
    var lastSumOfWeightedNumerator = sumOfWeightedNumerator

    var j = 0

    for (i <- 0 until array.length) {
      if (i <= days - 1) {
        sumOfNumerator += array(i).close
        sumOfWeightedNumerator = (i + 1) * array(i).close
        if (i == days - 1) {
          wma(j) = MathUtil.round(sumOfWeightedNumerator / sumOfDenominator, 2)
          j += 1
        }
      } else {
        lastSumOfNumerator = sumOfNumerator
        lastSumOfWeightedNumerator = sumOfWeightedNumerator

        sumOfNumerator += array(i).close - array(j - 1).close
        sumOfWeightedNumerator = days * array(i).close + lastSumOfNumerator - lastSumOfWeightedNumerator
        wma(j) = MathUtil.round(sumOfWeightedNumerator / sumOfDenominator)
        j += 1
      }

    }

    wma
  }
}
