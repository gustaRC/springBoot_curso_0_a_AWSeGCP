package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.request.converters;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception.UnsupportedMathOperationException;

public class NumberConverter {

    public static Double convertToDouble(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) throw new UnsupportedMathOperationException("Please set a numeric value!");
        String number = strNumber.replace(",", ".");

        return Double.parseDouble(number);
    }

    public static boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",", ".");

        return number.matches("[-+]?[0-9]*\\.?[0-9]+"); //regex para validar se é um número válido (positivo ou negativo, inteiro ou decimal)
    }

    public static void notNumberError(String numberOne) {
        if(!isNumeric(numberOne)) throw new UnsupportedMathOperationException("Please set a numeric value!");
    }

    public static void notNumbersError(String numberOne, String numberTwo) {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
}
