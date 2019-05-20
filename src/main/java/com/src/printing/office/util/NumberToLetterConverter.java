package com.src.printing.office.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class NumberToLetterConverter {

	public String numberToLetter(Double number) {
		String result = "";

		String numberString = number.toString();

		int decimalPlace = number.toString().indexOf('.');

		int iterationNumber = 0;

		if (decimalPlace >= 4 && decimalPlace <= 6)
			iterationNumber = 3;
		if (decimalPlace >= 1 && decimalPlace <= 3)
			iterationNumber = 2;

		// i=1 - decimale ... i=2 - stotine ... i=3 - hiljade
		for (int i = iterationNumber; i > 0; i--) {
			switch (i) {
			case 3:
				String thousandsString = numberString.substring(0, decimalPlace - 3);
				switch (thousandsString) {
				case "1":
					result += "hiljadu";
					break;
				case "2":
					result += "dvehiljade";
					break;
				default:
					result += createStringFromTreeDigitNumber(thousandsString);
					result += getThousand(thousandsString.length() == 3 ? Integer.parseInt(thousandsString.substring(1))
							: Integer.parseInt(thousandsString));
					break;
				}
				break;
			case 2:
				String hundredsString = iterationNumber == 3
						? numberString.substring(numberString.indexOf('.') - 3, numberString.indexOf('.'))
						: numberString.substring(0, numberString.indexOf('.'));
				result += createStringFromTreeDigitNumber(hundredsString);
				result += " "
						+ getEuroString(hundredsString.length() == 3 ? Integer.parseInt(hundredsString.substring(1))
								: Integer.parseInt(hundredsString));
				break;
			case 1:
				result += " i ";
				String decimal = numberString.substring(numberString.indexOf('.') + 1);
				decimal = decimal.length() == 1 ? decimal + "0" : decimal;
				result += getDecadesAndUnits(decimal);
				result += " " + getCents(Integer.parseInt(decimal));

			default:
				break;
			}
		}

		return result;
	}

	private String getCents(int number) {
		switch (number) {
		case 1:
		case 21:
		case 31:
		case 41:
		case 51:
		case 61:
		case 71:
		case 81:
		case 91:
			return "cent";
		case 2:
		case 3:
		case 4:
		case 22:
		case 23:
		case 24:
		case 32:
		case 33:
		case 34:
		case 42:
		case 43:
		case 44:
		case 52:
		case 53:
		case 54:
		case 62:
		case 63:
		case 64:
		case 72:
		case 73:
		case 74:
		case 82:
		case 83:
		case 84:
		case 92:
		case 93:
		case 94:
			return "centa";

		default:
			return "centi";
		}
	}

	private String getThousand(int number) {
		switch (number) {
		case 2:
		case 3:
		case 4:
		case 22:
		case 23:
		case 24:
		case 32:
		case 33:
		case 34:
		case 42:
		case 43:
		case 44:
		case 52:
		case 53:
		case 54:
		case 62:
		case 63:
		case 64:
		case 72:
		case 73:
		case 74:
		case 82:
		case 83:
		case 84:
		case 92:
		case 93:
		case 94:
			return "hiljade";

		default:
			return "hiljada";
		}
	}

	private String getEuroString(int number) {
		switch (number) {
		case 1:
		case 21:
		case 31:
		case 41:
		case 51:
		case 61:
		case 71:
		case 81:
		case 91:
			return "euro";

		default:
			return "eura";
		}
	}

	private String createStringFromTreeDigitNumber(String number) {
		String result = "";
		if (number.length() == 3)
			result += getHundreds(number.charAt(0));

		String decadeNumber = number.length() == 3 ? number.substring(1) : number;
		if (Integer.parseInt(decadeNumber) != 0)
			result += getDecadesAndUnits(number.length() == 3 ? number.substring(1) : number);

		return result;
	}

	private String getDecadesAndUnits(String number) {
		String result = "";

		if (Integer.parseInt(number) < 20)
			result += getUnits(Integer.parseInt(number));
		else {
			result += getDecades(Character.getNumericValue(number.charAt(0)));
			if (Character.getNumericValue(number.charAt(1)) > 0)
				result += getUnits(Character.getNumericValue(number.charAt(1)));
		}

		return result;
	}

	private String getUnits(int number) {
		switch (number) {
		case 0:
			return "nula";
		case 1:
			return "jedan";
		case 2:
			return "dva";
		case 3:
			return "tri";
		case 4:
			return "četiri";
		case 5:
			return "pet";
		case 6:
			return "šest";
		case 7:
			return "sedam";
		case 8:
			return "osam";
		case 9:
			return "devet";
		case 10:
			return "deset";
		case 11:
			return "jedanaest";
		case 12:
			return "dvanaest";
		case 13:
			return "trinaest";
		case 14:
			return "četrnaest";
		case 15:
			return "petnaest";
		case 16:
			return "šestnaest";
		case 17:
			return "sedamnaest";
		case 18:
			return "osamnaest";
		case 19:
			return "devetnaest";
		default:
			break;
		}
		return "";
	}

	private String getDecades(int number) {
		switch (number) {
		case 2:
			return "dvadeset";
		case 3:
			return "trideset";
		case 4:
			return "četrdeset";
		case 5:
			return "pedeset";
		case 6:
			return "šezdeset";
		case 7:
			return "sedamdeset";
		case 8:
			return "osamdeset";
		case 9:
			return "devedeset";
		default:
			break;
		}
		return "";
	}

	private String getHundreds(char number) {
		switch (number) {
		case '1':
			return "sto";
		case '2':
			return "dvestotine";
		case '3':
			return "tristotine";
		case '4':
			return "četiristotine";
		case '5':
			return "petstotina";
		case '6':
			return "šeststotina";
		case '7':
			return "sedamstotina";
		case '8':
			return "osamstotina";
		case '9':
			return "devetstotina";
		default:
			return "";
		}
	}
}
