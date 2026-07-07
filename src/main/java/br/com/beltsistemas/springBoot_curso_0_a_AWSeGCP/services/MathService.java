package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.services;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.request.converters.NumberConverter;
import org.springframework.stereotype.Service;

@Service
public class MathService {

    public Double sum(String numberOne, String numberTwo) {
        NumberConverter.notNumbersError(numberOne, numberTwo);
        return NumberConverter.convertToDouble(numberOne) + NumberConverter.convertToDouble(numberTwo);
    }

    public Double subtraction(String numberOne, String numberTwo) {
        NumberConverter.notNumbersError(numberOne, numberTwo);
        return NumberConverter.convertToDouble(numberOne) - NumberConverter.convertToDouble(numberTwo);
    }

    public Double multiplication(String numberOne, String numberTwo) {
        NumberConverter.notNumbersError(numberOne, numberTwo);
        return NumberConverter.convertToDouble(numberOne) * NumberConverter.convertToDouble(numberTwo);
    }

    public Double division(String numberOne, String numberTwo) {
        NumberConverter.notNumbersError(numberOne, numberTwo);
        return NumberConverter.convertToDouble(numberOne) / NumberConverter.convertToDouble(numberTwo);
    }

    public Double average(String numberOne, String numberTwo) {
        NumberConverter.notNumbersError(numberOne, numberTwo);
        return (NumberConverter.convertToDouble(numberOne) + NumberConverter.convertToDouble(numberTwo)) / 2;
    }

    public Double squareRoot(String number) {
        NumberConverter.notNumberError(number);
        return Math.sqrt(NumberConverter.convertToDouble(number));
    }
}
