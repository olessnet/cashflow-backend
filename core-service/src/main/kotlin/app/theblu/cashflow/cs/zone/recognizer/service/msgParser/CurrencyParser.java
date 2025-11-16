package app.theblu.cashflow.cs.zone.recognizer.service.msgParser;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

// https://mockoon.com/templates/currency-code/
public class CurrencyParser {
    private static List<String> codes = new LinkedList<String>();

    private static void init() {
        if (!codes.isEmpty()) return;

        var gson = new Gson();
        var jo = gson.fromJson(jsonRaw, JsonObject.class);
        jo.keySet().forEach(key -> codes.add(key.trim()));
    }

    public static String parse(String code) {
        init();
        if (code.toLowerCase().equals("rs")) {
            return "INR";
        } else if (codes.contains(code.toUpperCase())) {
            return code.toUpperCase();
        }
        return null;
    }

    private static String jsonRaw = "{\n" +
            "  \"AED\": {\n" +
            "    \"code\": \"AED\",\n" +
            "    \"name\": \"United Arab Emirates Dirham\",\n" +
            "    \"symbol\": \"د.إ.\",\n" +
            "    \"symbolNative\": \"د.إ.\"\n" +
            "  },\n" +
            "  \"AFN\": {\n" +
            "    \"code\": \"AFN\",\n" +
            "    \"name\": \"Afghan Afghani\",\n" +
            "    \"symbol\": \"Af\",\n" +
            "    \"symbolNative\": \"؋\"\n" +
            "  },\n" +
            "  \"ALL\": {\n" +
            "    \"code\": \"ALL\",\n" +
            "    \"name\": \"Albanian Lek\",\n" +
            "    \"symbol\": \"L\",\n" +
            "    \"symbolNative\": \"L\"\n" +
            "  },\n" +
            "  \"AMD\": {\n" +
            "    \"code\": \"AMD\",\n" +
            "    \"name\": \"Armenian Dram\",\n" +
            "    \"symbol\": \"֏\",\n" +
            "    \"symbolNative\": \"դր\"\n" +
            "  },\n" +
            "  \"ANG\": {\n" +
            "    \"code\": \"ANG\",\n" +
            "    \"name\": \"Netherlands Antillean Guilder\",\n" +
            "    \"symbol\": \"ƒ\",\n" +
            "    \"symbolNative\": \"ƒ\"\n" +
            "  },\n" +
            "  \"AOA\": {\n" +
            "    \"code\": \"AOA\",\n" +
            "    \"name\": \"Angolan Kwanza\",\n" +
            "    \"symbol\": \"Kz\",\n" +
            "    \"symbolNative\": \"Kz\"\n" +
            "  },\n" +
            "  \"ARS\": {\n" +
            "    \"code\": \"ARS\",\n" +
            "    \"name\": \"Argentine Peso\",\n" +
            "    \"symbol\": \"AR$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"AUD\": {\n" +
            "    \"code\": \"AUD\",\n" +
            "    \"name\": \"Australian Dollar\",\n" +
            "    \"symbol\": \"AU$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"AWG\": {\n" +
            "    \"code\": \"AWG\",\n" +
            "    \"name\": \"Aruban Florin\",\n" +
            "    \"symbol\": \"ƒ\",\n" +
            "    \"symbolNative\": \"ƒ\"\n" +
            "  },\n" +
            "  \"AZN\": {\n" +
            "    \"code\": \"AZN\",\n" +
            "    \"name\": \"Azerbaijani Manat\",\n" +
            "    \"symbol\": \"ман\",\n" +
            "    \"symbolNative\": \"₼\"\n" +
            "  },\n" +
            "  \"BAM\": {\n" +
            "    \"code\": \"BAM\",\n" +
            "    \"name\": \"Bosnia and Herzegovina Convertible Mark\",\n" +
            "    \"symbol\": \"KM\",\n" +
            "    \"symbolNative\": \"КМ\"\n" +
            "  },\n" +
            "  \"BBD\": {\n" +
            "    \"code\": \"BBD\",\n" +
            "    \"name\": \"Barbadian Dollar\",\n" +
            "    \"symbol\": \"BBD$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"BDT\": {\n" +
            "    \"code\": \"BDT\",\n" +
            "    \"name\": \"Bangladeshi Taka\",\n" +
            "    \"symbol\": \"৳\",\n" +
            "    \"symbolNative\": \"৳\"\n" +
            "  },\n" +
            "  \"BGN\": {\n" +
            "    \"code\": \"BGN\",\n" +
            "    \"name\": \"Bulgarian Lev\",\n" +
            "    \"symbol\": \"лв.\",\n" +
            "    \"symbolNative\": \"лв.\"\n" +
            "  },\n" +
            "  \"BHD\": {\n" +
            "    \"code\": \"BHD\",\n" +
            "    \"name\": \"Bahraini Dinar\",\n" +
            "    \"symbol\": \"BD\",\n" +
            "    \"symbolNative\": \"د.ب.\"\n" +
            "  },\n" +
            "  \"BIF\": {\n" +
            "    \"code\": \"BIF\",\n" +
            "    \"name\": \"Burundian Franc\",\n" +
            "    \"symbol\": \"FBu\",\n" +
            "    \"symbolNative\": \"FBu\"\n" +
            "  },\n" +
            "  \"BMD\": {\n" +
            "    \"code\": \"BMD\",\n" +
            "    \"name\": \"Bermudian Dollar\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"BND\": {\n" +
            "    \"code\": \"BND\",\n" +
            "    \"name\": \"Brunei Dollar\",\n" +
            "    \"symbol\": \"B$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"BOB\": {\n" +
            "    \"code\": \"BOB\",\n" +
            "    \"name\": \"Bolivian Boliviano\",\n" +
            "    \"symbol\": \"Bs.\",\n" +
            "    \"symbolNative\": \"Bs.\"\n" +
            "  },\n" +
            "  \"BRL\": {\n" +
            "    \"code\": \"BRL\",\n" +
            "    \"name\": \"Brazilian Real\",\n" +
            "    \"symbol\": \"R$\",\n" +
            "    \"symbolNative\": \"R$\"\n" +
            "  },\n" +
            "  \"BSD\": {\n" +
            "    \"code\": \"BSD\",\n" +
            "    \"name\": \"Bahamian Dollar\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"BTN\": {\n" +
            "    \"code\": \"BTN\",\n" +
            "    \"name\": \"Bhutanese Ngultrum\",\n" +
            "    \"symbol\": \"Nu.\",\n" +
            "    \"symbolNative\": \"Nu.\"\n" +
            "  },\n" +
            "  \"BWP\": {\n" +
            "    \"code\": \"BWP\",\n" +
            "    \"name\": \"Botswana Pula\",\n" +
            "    \"symbol\": \"P\",\n" +
            "    \"symbolNative\": \"P\"\n" +
            "  },\n" +
            "  \"BYN\": {\n" +
            "    \"code\": \"BYN\",\n" +
            "    \"name\": \"Belarusian Ruble\",\n" +
            "    \"symbol\": \"Br\",\n" +
            "    \"symbolNative\": \"руб.\"\n" +
            "  },\n" +
            "  \"BZD\": {\n" +
            "    \"code\": \"BZD\",\n" +
            "    \"name\": \"Belize Dollar\",\n" +
            "    \"symbol\": \"BZ$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"CAD\": {\n" +
            "    \"code\": \"CAD\",\n" +
            "    \"name\": \"Canadian Dollar\",\n" +
            "    \"symbol\": \"CA$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"CDF\": {\n" +
            "    \"code\": \"CDF\",\n" +
            "    \"name\": \"Congolese Franc\",\n" +
            "    \"symbol\": \"FC\",\n" +
            "    \"symbolNative\": \"₣\"\n" +
            "  },\n" +
            "  \"CHF\": {\n" +
            "    \"code\": \"CHF\",\n" +
            "    \"name\": \"Swiss Franc\",\n" +
            "    \"symbol\": \"Fr.\",\n" +
            "    \"symbolNative\": \"₣\"\n" +
            "  },\n" +
            "  \"CKD\": {\n" +
            "    \"code\": \"CKD\",\n" +
            "    \"name\": \"Cook Islands Dollar\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"CLP\": {\n" +
            "    \"code\": \"CLP\",\n" +
            "    \"name\": \"Chilean Peso\",\n" +
            "    \"symbol\": \"CL$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"CNY\": {\n" +
            "    \"code\": \"CNY\",\n" +
            "    \"name\": \"Chinese Yuan\",\n" +
            "    \"symbol\": \"CN¥\",\n" +
            "    \"symbolNative\": \"¥元\"\n" +
            "  },\n" +
            "  \"COP\": {\n" +
            "    \"code\": \"COP\",\n" +
            "    \"name\": \"Colombian Peso\",\n" +
            "    \"symbol\": \"CO$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"CRC\": {\n" +
            "    \"code\": \"CRC\",\n" +
            "    \"name\": \"Costa Rican Colon\",\n" +
            "    \"symbol\": \"₡\",\n" +
            "    \"symbolNative\": \"₡\"\n" +
            "  },\n" +
            "  \"CUC\": {\n" +
            "    \"code\": \"CUC\",\n" +
            "    \"name\": \"Cuban convertible Peso\",\n" +
            "    \"symbol\": \"CUC$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"CUP\": {\n" +
            "    \"code\": \"CUP\",\n" +
            "    \"name\": \"Cuban Peso\",\n" +
            "    \"symbol\": \"$MN\",\n" +
            "    \"symbolNative\": \"₱\"\n" +
            "  },\n" +
            "  \"CVE\": {\n" +
            "    \"code\": \"CVE\",\n" +
            "    \"name\": \"Cabo Verdean Escudo\",\n" +
            "    \"symbol\": \"CV$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"CZK\": {\n" +
            "    \"code\": \"CZK\",\n" +
            "    \"name\": \"Czech Koruna\",\n" +
            "    \"symbol\": \"Kč\",\n" +
            "    \"symbolNative\": \"Kč\"\n" +
            "  },\n" +
            "  \"DJF\": {\n" +
            "    \"code\": \"DJF\",\n" +
            "    \"name\": \"Djiboutian Franc\",\n" +
            "    \"symbol\": \"Fdj\",\n" +
            "    \"symbolNative\": \"ف.ج.\"\n" +
            "  },\n" +
            "  \"DKK\": {\n" +
            "    \"code\": \"DKK\",\n" +
            "    \"name\": \"Danish Krone\",\n" +
            "    \"symbol\": \"kr.\",\n" +
            "    \"symbolNative\": \"kr.\"\n" +
            "  },\n" +
            "  \"DOP\": {\n" +
            "    \"code\": \"DOP\",\n" +
            "    \"name\": \"Dominican Peso\",\n" +
            "    \"symbol\": \"RD$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"DZD\": {\n" +
            "    \"code\": \"DZD\",\n" +
            "    \"name\": \"Algerian Dinar\",\n" +
            "    \"symbol\": \"DA\",\n" +
            "    \"symbolNative\": \"د.ج.\"\n" +
            "  },\n" +
            "  \"EGP\": {\n" +
            "    \"code\": \"EGP\",\n" +
            "    \"name\": \"Egyptian Pound\",\n" +
            "    \"symbol\": \"E£\",\n" +
            "    \"symbolNative\": \"ج.م.\"\n" +
            "  },\n" +
            "  \"EHP\": {\n" +
            "    \"code\": \"EHP\",\n" +
            "    \"name\": \"Sahrawi Peseta\",\n" +
            "    \"symbol\": \"Ptas.\",\n" +
            "    \"symbolNative\": \"Ptas.\"\n" +
            "  },\n" +
            "  \"ERN\": {\n" +
            "    \"code\": \"ERN\",\n" +
            "    \"name\": \"Eritrean Nakfa\",\n" +
            "    \"symbol\": \"Nkf\",\n" +
            "    \"symbolNative\": \"ناكفا\"\n" +
            "  },\n" +
            "  \"ETB\": {\n" +
            "    \"code\": \"ETB\",\n" +
            "    \"name\": \"Ethiopian Birr\",\n" +
            "    \"symbol\": \"Br\",\n" +
            "    \"symbolNative\": \"ብር\"\n" +
            "  },\n" +
            "  \"EUR\": {\n" +
            "    \"code\": \"EUR\",\n" +
            "    \"name\": \"Euro\",\n" +
            "    \"symbol\": \"€\",\n" +
            "    \"symbolNative\": \"€\"\n" +
            "  },\n" +
            "  \"FJD\": {\n" +
            "    \"code\": \"FJD\",\n" +
            "    \"name\": \"Fijian Dollar\",\n" +
            "    \"symbol\": \"FJ$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"FKP\": {\n" +
            "    \"code\": \"FKP\",\n" +
            "    \"name\": \"Falkland Islands Pound\",\n" +
            "    \"symbol\": \"FK£\",\n" +
            "    \"symbolNative\": \"£\"\n" +
            "  },\n" +
            "  \"FOK\": {\n" +
            "    \"code\": \"FOK\",\n" +
            "    \"name\": \"Faroese Króna\",\n" +
            "    \"symbol\": \"kr\",\n" +
            "    \"symbolNative\": \"kr\"\n" +
            "  },\n" +
            "  \"GBP\": {\n" +
            "    \"code\": \"GBP\",\n" +
            "    \"name\": \"Pound Sterling\",\n" +
            "    \"symbol\": \"£\",\n" +
            "    \"symbolNative\": \"£\"\n" +
            "  },\n" +
            "  \"GEL\": {\n" +
            "    \"code\": \"GEL\",\n" +
            "    \"name\": \"Georgian Lari\",\n" +
            "    \"symbol\": \"₾\",\n" +
            "    \"symbolNative\": \"₾\"\n" +
            "  },\n" +
            "  \"GGP\": {\n" +
            "    \"code\": \"GGP\",\n" +
            "    \"name\": \"Guernsey Pound\",\n" +
            "    \"symbol\": \"£\",\n" +
            "    \"symbolNative\": \"£\"\n" +
            "  },\n" +
            "  \"GHS\": {\n" +
            "    \"code\": \"GHS\",\n" +
            "    \"name\": \"Ghanaian Cedi\",\n" +
            "    \"symbol\": \"GH₵\",\n" +
            "    \"symbolNative\": \"₵\"\n" +
            "  },\n" +
            "  \"GIP\": {\n" +
            "    \"code\": \"GIP\",\n" +
            "    \"name\": \"Gibraltar Pound\",\n" +
            "    \"symbol\": \"£\",\n" +
            "    \"symbolNative\": \"£\"\n" +
            "  },\n" +
            "  \"GMD\": {\n" +
            "    \"code\": \"GMD\",\n" +
            "    \"name\": \"Gambian Dalasi\",\n" +
            "    \"symbol\": \"D\",\n" +
            "    \"symbolNative\": \"D\"\n" +
            "  },\n" +
            "  \"GNF\": {\n" +
            "    \"code\": \"GNF\",\n" +
            "    \"name\": \"Guinean Franc\",\n" +
            "    \"symbol\": \"FG\",\n" +
            "    \"symbolNative\": \"FG\"\n" +
            "  },\n" +
            "  \"GTQ\": {\n" +
            "    \"code\": \"GTQ\",\n" +
            "    \"name\": \"Guatemalan Quetzal\",\n" +
            "    \"symbol\": \"Q\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"GYD\": {\n" +
            "    \"code\": \"GYD\",\n" +
            "    \"name\": \"Guyanese Dollar\",\n" +
            "    \"symbol\": \"G$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"HKD\": {\n" +
            "    \"code\": \"HKD\",\n" +
            "    \"name\": \"Hong Kong Dollar\",\n" +
            "    \"symbol\": \"HK$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"HNL\": {\n" +
            "    \"code\": \"HNL\",\n" +
            "    \"name\": \"Honduran Lempira\",\n" +
            "    \"symbol\": \"L\",\n" +
            "    \"symbolNative\": \"L\"\n" +
            "  },\n" +
            "  \"HRK\": {\n" +
            "    \"code\": \"HRK\",\n" +
            "    \"name\": \"Croatian Kuna\",\n" +
            "    \"symbol\": \"kn\",\n" +
            "    \"symbolNative\": \"kn\"\n" +
            "  },\n" +
            "  \"HTG\": {\n" +
            "    \"code\": \"HTG\",\n" +
            "    \"name\": \"Haitian Gourde\",\n" +
            "    \"symbol\": \"G\",\n" +
            "    \"symbolNative\": \"G\"\n" +
            "  },\n" +
            "  \"HUF\": {\n" +
            "    \"code\": \"HUF\",\n" +
            "    \"name\": \"Hungarian Forint\",\n" +
            "    \"symbol\": \"Ft\",\n" +
            "    \"symbolNative\": \"Ft\"\n" +
            "  },\n" +
            "  \"IDR\": {\n" +
            "    \"code\": \"IDR\",\n" +
            "    \"name\": \"Indonesian Rupiah\",\n" +
            "    \"symbol\": \"Rp\",\n" +
            "    \"symbolNative\": \"Rp\"\n" +
            "  },\n" +
            "  \"ILS\": {\n" +
            "    \"code\": \"ILS\",\n" +
            "    \"name\": \"Israeli new Shekel\",\n" +
            "    \"symbol\": \"₪\",\n" +
            "    \"symbolNative\": \"₪\"\n" +
            "  },\n" +
            "  \"IMP\": {\n" +
            "    \"code\": \"IMP\",\n" +
            "    \"name\": \"Manx Pound\",\n" +
            "    \"symbol\": \"£\",\n" +
            "    \"symbolNative\": \"£\"\n" +
            "  },\n" +
            "  \"INR\": {\n" +
            "    \"code\": \"INR\",\n" +
            "    \"name\": \"Indian Rupee\",\n" +
            "    \"symbol\": \"Rs.\",\n" +
            "    \"symbolNative\": \"₹\"\n" +
            "  },\n" +
            "  \"IQD\": {\n" +
            "    \"code\": \"IQD\",\n" +
            "    \"name\": \"Iraqi Dinar\",\n" +
            "    \"symbol\": \"د.ع.\",\n" +
            "    \"symbolNative\": \"د.ع.\"\n" +
            "  },\n" +
            "  \"IRR\": {\n" +
            "    \"code\": \"IRR\",\n" +
            "    \"name\": \"Iranian Rial\",\n" +
            "    \"symbol\": \"﷼\",\n" +
            "    \"symbolNative\": \"﷼\"\n" +
            "  },\n" +
            "  \"ISK\": {\n" +
            "    \"code\": \"ISK\",\n" +
            "    \"name\": \"Icelandic Krona\",\n" +
            "    \"symbol\": \"kr\",\n" +
            "    \"symbolNative\": \"kr\"\n" +
            "  },\n" +
            "  \"JEP\": {\n" +
            "    \"code\": \"JEP\",\n" +
            "    \"name\": \"Jersey Pound\",\n" +
            "    \"symbol\": \"£\",\n" +
            "    \"symbolNative\": \"£\"\n" +
            "  },\n" +
            "  \"JMD\": {\n" +
            "    \"code\": \"JMD\",\n" +
            "    \"name\": \"Jamaican Dollar\",\n" +
            "    \"symbol\": \"J$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"JOD\": {\n" +
            "    \"code\": \"JOD\",\n" +
            "    \"name\": \"Jordanian Dinar\",\n" +
            "    \"symbol\": \"JD\",\n" +
            "    \"symbolNative\": \"د.أ.\"\n" +
            "  },\n" +
            "  \"JPY\": {\n" +
            "    \"code\": \"JPY\",\n" +
            "    \"name\": \"Japanese Yen\",\n" +
            "    \"symbol\": \"¥\",\n" +
            "    \"symbolNative\": \"¥\"\n" +
            "  },\n" +
            "  \"KES\": {\n" +
            "    \"code\": \"KES\",\n" +
            "    \"name\": \"Kenyan Shilling\",\n" +
            "    \"symbol\": \"KSh\",\n" +
            "    \"symbolNative\": \"KSh\"\n" +
            "  },\n" +
            "  \"KGS\": {\n" +
            "    \"code\": \"KGS\",\n" +
            "    \"name\": \"Kyrgyzstani Som\",\n" +
            "    \"symbol\": \"с\",\n" +
            "    \"symbolNative\": \"с\"\n" +
            "  },\n" +
            "  \"KHR\": {\n" +
            "    \"code\": \"KHR\",\n" +
            "    \"name\": \"Cambodian Riel\",\n" +
            "    \"symbol\": \"៛\",\n" +
            "    \"symbolNative\": \"៛\"\n" +
            "  },\n" +
            "  \"KID\": {\n" +
            "    \"code\": \"KID\",\n" +
            "    \"name\": \"Kiribati Dollar\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"KMF\": {\n" +
            "    \"code\": \"KMF\",\n" +
            "    \"name\": \"Comorian Franc\",\n" +
            "    \"symbol\": \"CF\",\n" +
            "    \"symbolNative\": \"CF\"\n" +
            "  },\n" +
            "  \"KPW\": {\n" +
            "    \"code\": \"KPW\",\n" +
            "    \"name\": \"North Korean Won\",\n" +
            "    \"symbol\": \"₩\",\n" +
            "    \"symbolNative\": \"₩\"\n" +
            "  },\n" +
            "  \"KRW\": {\n" +
            "    \"code\": \"KRW\",\n" +
            "    \"name\": \"South Korean Won\",\n" +
            "    \"symbol\": \"₩\",\n" +
            "    \"symbolNative\": \"₩\"\n" +
            "  },\n" +
            "  \"KWD\": {\n" +
            "    \"code\": \"KWD\",\n" +
            "    \"name\": \"Kuwaiti Dinar\",\n" +
            "    \"symbol\": \"KD\",\n" +
            "    \"symbolNative\": \"د.ك.\"\n" +
            "  },\n" +
            "  \"KYD\": {\n" +
            "    \"code\": \"KYD\",\n" +
            "    \"name\": \"Cayman Islands Dollar\",\n" +
            "    \"symbol\": \"CI$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"KZT\": {\n" +
            "    \"code\": \"KZT\",\n" +
            "    \"name\": \"Kazakhstani Tenge\",\n" +
            "    \"symbol\": \"₸\",\n" +
            "    \"symbolNative\": \"₸\"\n" +
            "  },\n" +
            "  \"LAK\": {\n" +
            "    \"code\": \"LAK\",\n" +
            "    \"name\": \"Lao Kip\",\n" +
            "    \"symbol\": \"₭N\",\n" +
            "    \"symbolNative\": \"₭\"\n" +
            "  },\n" +
            "  \"LBP\": {\n" +
            "    \"code\": \"LBP\",\n" +
            "    \"name\": \"Lebanese Pound\",\n" +
            "    \"symbol\": \"LL.\",\n" +
            "    \"symbolNative\": \"ل.ل.\"\n" +
            "  },\n" +
            "  \"LKR\": {\n" +
            "    \"code\": \"LKR\",\n" +
            "    \"name\": \"Sri Lankan Rupee\",\n" +
            "    \"symbol\": \"Rs.\",\n" +
            "    \"symbolNative\": \"රු or ரூ\"\n" +
            "  },\n" +
            "  \"LRD\": {\n" +
            "    \"code\": \"LRD\",\n" +
            "    \"name\": \"Liberian Dollar\",\n" +
            "    \"symbol\": \"L$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"LSL\": {\n" +
            "    \"code\": \"LSL\",\n" +
            "    \"name\": \"Lesotho Loti\",\n" +
            "    \"symbol\": \"L\",\n" +
            "    \"symbolNative\": \"L\"\n" +
            "  },\n" +
            "  \"LYD\": {\n" +
            "    \"code\": \"LYD\",\n" +
            "    \"name\": \"Libyan Dinar\",\n" +
            "    \"symbol\": \"LD\",\n" +
            "    \"symbolNative\": \"ل.د.\"\n" +
            "  },\n" +
            "  \"MAD\": {\n" +
            "    \"code\": \"MAD\",\n" +
            "    \"name\": \"Moroccan Dirham\",\n" +
            "    \"symbol\": \"DH\",\n" +
            "    \"symbolNative\": \"د.م.\"\n" +
            "  },\n" +
            "  \"MDL\": {\n" +
            "    \"code\": \"MDL\",\n" +
            "    \"name\": \"Moldovan Leu\",\n" +
            "    \"symbol\": \"L\",\n" +
            "    \"symbolNative\": \"L\"\n" +
            "  },\n" +
            "  \"MGA\": {\n" +
            "    \"code\": \"MGA\",\n" +
            "    \"name\": \"Malagasy Ariary\",\n" +
            "    \"symbol\": \"Ar\",\n" +
            "    \"symbolNative\": \"Ar\"\n" +
            "  },\n" +
            "  \"MKD\": {\n" +
            "    \"code\": \"MKD\",\n" +
            "    \"name\": \"Macedonian Denar\",\n" +
            "    \"symbol\": \"den\",\n" +
            "    \"symbolNative\": \"ден\"\n" +
            "  },\n" +
            "  \"MMK\": {\n" +
            "    \"code\": \"MMK\",\n" +
            "    \"name\": \"Myanmar Kyat\",\n" +
            "    \"symbol\": \"Ks\",\n" +
            "    \"symbolNative\": \"Ks\"\n" +
            "  },\n" +
            "  \"MNT\": {\n" +
            "    \"code\": \"MNT\",\n" +
            "    \"name\": \"Mongolian Tögrög\",\n" +
            "    \"symbol\": \"₮\",\n" +
            "    \"symbolNative\": \"₮\"\n" +
            "  },\n" +
            "  \"MOP\": {\n" +
            "    \"code\": \"MOP\",\n" +
            "    \"name\": \"Macanese Pataca\",\n" +
            "    \"symbol\": \"MOP$\",\n" +
            "    \"symbolNative\": \"MOP$\"\n" +
            "  },\n" +
            "  \"MRU\": {\n" +
            "    \"code\": \"MRU\",\n" +
            "    \"name\": \"Mauritanian Ouguiya\",\n" +
            "    \"symbol\": \"UM\",\n" +
            "    \"symbolNative\": \"أ.م.\"\n" +
            "  },\n" +
            "  \"MUR\": {\n" +
            "    \"code\": \"MUR\",\n" +
            "    \"name\": \"Mauritian Rupee\",\n" +
            "    \"symbol\": \"Rs.\",\n" +
            "    \"symbolNative\": \"रु \"\n" +
            "  },\n" +
            "  \"MVR\": {\n" +
            "    \"code\": \"MVR\",\n" +
            "    \"name\": \"Maldivian Rufiyaa\",\n" +
            "    \"symbol\": \"MRf\",\n" +
            "    \"symbolNative\": \".ރ\"\n" +
            "  },\n" +
            "  \"MWK\": {\n" +
            "    \"code\": \"MWK\",\n" +
            "    \"name\": \"Malawian Kwacha\",\n" +
            "    \"symbol\": \"MK\",\n" +
            "    \"symbolNative\": \"MK\"\n" +
            "  },\n" +
            "  \"MXN\": {\n" +
            "    \"code\": \"MXN\",\n" +
            "    \"name\": \"Mexican Peso\",\n" +
            "    \"symbol\": \"MX$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"MYR\": {\n" +
            "    \"code\": \"MYR\",\n" +
            "    \"name\": \"Malaysian Ringgit\",\n" +
            "    \"symbol\": \"RM\",\n" +
            "    \"symbolNative\": \"RM\"\n" +
            "  },\n" +
            "  \"MZN\": {\n" +
            "    \"code\": \"MZN\",\n" +
            "    \"name\": \"Mozambican Metical\",\n" +
            "    \"symbol\": \"MTn\",\n" +
            "    \"symbolNative\": \"MT\"\n" +
            "  },\n" +
            "  \"NAD\": {\n" +
            "    \"code\": \"NAD\",\n" +
            "    \"name\": \"Namibian Dollar\",\n" +
            "    \"symbol\": \"N$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"NGN\": {\n" +
            "    \"code\": \"NGN\",\n" +
            "    \"name\": \"Nigerian Naira\",\n" +
            "    \"symbol\": \"₦\",\n" +
            "    \"symbolNative\": \"₦\"\n" +
            "  },\n" +
            "  \"NIO\": {\n" +
            "    \"code\": \"NIO\",\n" +
            "    \"name\": \"Nicaraguan Córdoba\",\n" +
            "    \"symbol\": \"C$\",\n" +
            "    \"symbolNative\": \"C$\"\n" +
            "  },\n" +
            "  \"NOK\": {\n" +
            "    \"code\": \"NOK\",\n" +
            "    \"name\": \"Norwegian Krone\",\n" +
            "    \"symbol\": \"kr\",\n" +
            "    \"symbolNative\": \"kr\"\n" +
            "  },\n" +
            "  \"NPR\": {\n" +
            "    \"code\": \"NPR\",\n" +
            "    \"name\": \"Nepalese Rupee\",\n" +
            "    \"symbol\": \"Rs.\",\n" +
            "    \"symbolNative\": \"रू\"\n" +
            "  },\n" +
            "  \"NZD\": {\n" +
            "    \"code\": \"NZD\",\n" +
            "    \"name\": \"New Zealand Dollar\",\n" +
            "    \"symbol\": \"NZ$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"OMR\": {\n" +
            "    \"code\": \"OMR\",\n" +
            "    \"name\": \"Omani Rial\",\n" +
            "    \"symbol\": \"OR\",\n" +
            "    \"symbolNative\": \"ر.ع.\"\n" +
            "  },\n" +
            "  \"PAB\": {\n" +
            "    \"code\": \"PAB\",\n" +
            "    \"name\": \"Panamanian Balboa\",\n" +
            "    \"symbol\": \"B/.\",\n" +
            "    \"symbolNative\": \"B/.\"\n" +
            "  },\n" +
            "  \"PEN\": {\n" +
            "    \"code\": \"PEN\",\n" +
            "    \"name\": \"Peruvian Sol\",\n" +
            "    \"symbol\": \"S/.\",\n" +
            "    \"symbolNative\": \"S/.\"\n" +
            "  },\n" +
            "  \"PGK\": {\n" +
            "    \"code\": \"PGK\",\n" +
            "    \"name\": \"Papua New Guinean Kina\",\n" +
            "    \"symbol\": \"K\",\n" +
            "    \"symbolNative\": \"K\"\n" +
            "  },\n" +
            "  \"PHP\": {\n" +
            "    \"code\": \"PHP\",\n" +
            "    \"name\": \"Philippine Peso\",\n" +
            "    \"symbol\": \"₱\",\n" +
            "    \"symbolNative\": \"₱\"\n" +
            "  },\n" +
            "  \"PKR\": {\n" +
            "    \"code\": \"PKR\",\n" +
            "    \"name\": \"Pakistani Rupee\",\n" +
            "    \"symbol\": \"Rs.\",\n" +
            "    \"symbolNative\": \"Rs\"\n" +
            "  },\n" +
            "  \"PLN\": {\n" +
            "    \"code\": \"PLN\",\n" +
            "    \"name\": \"Polish Zloty\",\n" +
            "    \"symbol\": \"zł\",\n" +
            "    \"symbolNative\": \"zł\"\n" +
            "  },\n" +
            "  \"PND\": {\n" +
            "    \"code\": \"PND\",\n" +
            "    \"name\": \"Pitcairn Islands Dollar\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"PRB\": {\n" +
            "    \"code\": \"PRB\",\n" +
            "    \"name\": \"Transnistrian Ruble\",\n" +
            "    \"symbol\": \"р.\",\n" +
            "    \"symbolNative\": \"р.\"\n" +
            "  },\n" +
            "  \"PYG\": {\n" +
            "    \"code\": \"PYG\",\n" +
            "    \"name\": \"Paraguayan Guaraní\",\n" +
            "    \"symbol\": \"₲\",\n" +
            "    \"symbolNative\": \"₲\"\n" +
            "  },\n" +
            "  \"QAR\": {\n" +
            "    \"code\": \"QAR\",\n" +
            "    \"name\": \"Qatari Riyal\",\n" +
            "    \"symbol\": \"QR\",\n" +
            "    \"symbolNative\": \"ر.ق.\"\n" +
            "  },\n" +
            "  \"RON\": {\n" +
            "    \"code\": \"RON\",\n" +
            "    \"name\": \"Romanian Leu\",\n" +
            "    \"symbol\": \"L\",\n" +
            "    \"symbolNative\": \"L\"\n" +
            "  },\n" +
            "  \"RSD\": {\n" +
            "    \"code\": \"RSD\",\n" +
            "    \"name\": \"Serbian Dinar\",\n" +
            "    \"symbol\": \"din\",\n" +
            "    \"symbolNative\": \"дин\"\n" +
            "  },\n" +
            "  \"RUB\": {\n" +
            "    \"code\": \"RUB\",\n" +
            "    \"name\": \"Russian Ruble\",\n" +
            "    \"symbol\": \"₽\",\n" +
            "    \"symbolNative\": \"₽\"\n" +
            "  },\n" +
            "  \"RWF\": {\n" +
            "    \"code\": \"RWF\",\n" +
            "    \"name\": \"Rwandan Franc\",\n" +
            "    \"symbol\": \"FRw\",\n" +
            "    \"symbolNative\": \"R₣\"\n" +
            "  },\n" +
            "  \"SAR\": {\n" +
            "    \"code\": \"SAR\",\n" +
            "    \"name\": \"Saudi Riyal\",\n" +
            "    \"symbol\": \"SR\",\n" +
            "    \"symbolNative\": \"ر.س.\"\n" +
            "  },\n" +
            "  \"SBD\": {\n" +
            "    \"code\": \"SBD\",\n" +
            "    \"name\": \"Solomon Islands Dollar\",\n" +
            "    \"symbol\": \"SI$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"SCR\": {\n" +
            "    \"code\": \"SCR\",\n" +
            "    \"name\": \"Seychellois Rupee\",\n" +
            "    \"symbol\": \"Rs.\",\n" +
            "    \"symbolNative\": \"Rs\"\n" +
            "  },\n" +
            "  \"SDG\": {\n" +
            "    \"code\": \"SDG\",\n" +
            "    \"name\": \"Sudanese Pound\",\n" +
            "    \"symbol\": \"£SD\",\n" +
            "    \"symbolNative\": \"ج.س.\"\n" +
            "  },\n" +
            "  \"SEK\": {\n" +
            "    \"code\": \"SEK\",\n" +
            "    \"name\": \"Swedish Krona\",\n" +
            "    \"symbol\": \"kr\",\n" +
            "    \"symbolNative\": \"kr\"\n" +
            "  },\n" +
            "  \"SGD\": {\n" +
            "    \"code\": \"SGD\",\n" +
            "    \"name\": \"Singapore Dollar\",\n" +
            "    \"symbol\": \"S$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"SHP\": {\n" +
            "    \"code\": \"SHP\",\n" +
            "    \"name\": \"Saint Helena Pound\",\n" +
            "    \"symbol\": \"£\",\n" +
            "    \"symbolNative\": \"£\"\n" +
            "  },\n" +
            "  \"SLL\": {\n" +
            "    \"code\": \"SLL\",\n" +
            "    \"name\": \"Sierra Leonean Leone\",\n" +
            "    \"symbol\": \"Le\",\n" +
            "    \"symbolNative\": \"Le\"\n" +
            "  },\n" +
            "  \"SLS\": {\n" +
            "    \"code\": \"SLS\",\n" +
            "    \"name\": \"Somaliland Shilling\",\n" +
            "    \"symbol\": \"Sl\",\n" +
            "    \"symbolNative\": \"Sl\"\n" +
            "  },\n" +
            "  \"SOS\": {\n" +
            "    \"code\": \"SOS\",\n" +
            "    \"name\": \"Somali Shilling\",\n" +
            "    \"symbol\": \"Sh.So.\",\n" +
            "    \"symbolNative\": \"Ssh\"\n" +
            "  },\n" +
            "  \"SRD\": {\n" +
            "    \"code\": \"SRD\",\n" +
            "    \"name\": \"Surinamese Dollar\",\n" +
            "    \"symbol\": \"Sr$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"SSP\": {\n" +
            "    \"code\": \"SSP\",\n" +
            "    \"name\": \"South Sudanese Pound\",\n" +
            "    \"symbol\": \"SS£\",\n" +
            "    \"symbolNative\": \"SS£\"\n" +
            "  },\n" +
            "  \"STN\": {\n" +
            "    \"code\": \"STN\",\n" +
            "    \"name\": \"Sao Tome and Príncipe Dobra\",\n" +
            "    \"symbol\": \"Db\",\n" +
            "    \"symbolNative\": \"Db\"\n" +
            "  },\n" +
            "  \"SVC\": {\n" +
            "    \"code\": \"SVC\",\n" +
            "    \"name\": \"Salvadoran Colón\",\n" +
            "    \"symbol\": \"₡\",\n" +
            "    \"symbolNative\": \"₡\"\n" +
            "  },\n" +
            "  \"SYP\": {\n" +
            "    \"code\": \"SYP\",\n" +
            "    \"name\": \"Syrian Pound\",\n" +
            "    \"symbol\": \"LS\",\n" +
            "    \"symbolNative\": \"ل.س.\"\n" +
            "  },\n" +
            "  \"SZL\": {\n" +
            "    \"code\": \"SZL\",\n" +
            "    \"name\": \"Swazi Lilangeni\",\n" +
            "    \"symbol\": \"L\",\n" +
            "    \"symbolNative\": \"L\"\n" +
            "  },\n" +
            "  \"THB\": {\n" +
            "    \"code\": \"THB\",\n" +
            "    \"name\": \"Thai Baht\",\n" +
            "    \"symbol\": \"฿\",\n" +
            "    \"symbolNative\": \"฿\"\n" +
            "  },\n" +
            "  \"TJS\": {\n" +
            "    \"code\": \"TJS\",\n" +
            "    \"name\": \"Tajikistani Somoni\",\n" +
            "    \"symbol\": \"SM\",\n" +
            "    \"symbolNative\": \"SM\"\n" +
            "  },\n" +
            "  \"TMT\": {\n" +
            "    \"code\": \"TMT\",\n" +
            "    \"name\": \"Turkmenistan Manat\",\n" +
            "    \"symbol\": \"m.\",\n" +
            "    \"symbolNative\": \"T\"\n" +
            "  },\n" +
            "  \"TND\": {\n" +
            "    \"code\": \"TND\",\n" +
            "    \"name\": \"Tunisian Dinar\",\n" +
            "    \"symbol\": \"DT\",\n" +
            "    \"symbolNative\": \"د.ت.\"\n" +
            "  },\n" +
            "  \"TOP\": {\n" +
            "    \"code\": \"TOP\",\n" +
            "    \"name\": \"Tongan Paʻanga\",\n" +
            "    \"symbol\": \"T$\",\n" +
            "    \"symbolNative\": \"PT\"\n" +
            "  },\n" +
            "  \"TRY\": {\n" +
            "    \"code\": \"TRY\",\n" +
            "    \"name\": \"Turkish Lira\",\n" +
            "    \"symbol\": \"TL\",\n" +
            "    \"symbolNative\": \"₺\"\n" +
            "  },\n" +
            "  \"TTD\": {\n" +
            "    \"code\": \"TTD\",\n" +
            "    \"name\": \"Trinidad and Tobago Dollar\",\n" +
            "    \"symbol\": \"TT$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"TVD\": {\n" +
            "    \"code\": \"TVD\",\n" +
            "    \"name\": \"Tuvaluan Dollar\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"TWD\": {\n" +
            "    \"code\": \"TWD\",\n" +
            "    \"name\": \"New Taiwan Dollar\",\n" +
            "    \"symbol\": \"NT$\",\n" +
            "    \"symbolNative\": \"圓\"\n" +
            "  },\n" +
            "  \"TZS\": {\n" +
            "    \"code\": \"TZS\",\n" +
            "    \"name\": \"Tanzanian Shilling\",\n" +
            "    \"symbol\": \"TSh\",\n" +
            "    \"symbolNative\": \"TSh\"\n" +
            "  },\n" +
            "  \"UAH\": {\n" +
            "    \"code\": \"UAH\",\n" +
            "    \"name\": \"Ukrainian Hryvnia\",\n" +
            "    \"symbol\": \"₴\",\n" +
            "    \"symbolNative\": \"грн\"\n" +
            "  },\n" +
            "  \"UGX\": {\n" +
            "    \"code\": \"UGX\",\n" +
            "    \"name\": \"Ugandan Shilling\",\n" +
            "    \"symbol\": \"USh\",\n" +
            "    \"symbolNative\": \"Sh\"\n" +
            "  },\n" +
            "  \"USD\": {\n" +
            "    \"code\": \"USD\",\n" +
            "    \"name\": \"United States Dollar\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"UYU\": {\n" +
            "    \"code\": \"UYU\",\n" +
            "    \"name\": \"Uruguayan Peso\",\n" +
            "    \"symbol\": \"$U\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"UZS\": {\n" +
            "    \"code\": \"UZS\",\n" +
            "    \"name\": \"Uzbekistani Som\",\n" +
            "    \"symbol\": \"сум\",\n" +
            "    \"symbolNative\": \"сум\"\n" +
            "  },\n" +
            "  \"VED\": {\n" +
            "    \"code\": \"VED\",\n" +
            "    \"name\": \"Venezuelan bolívar digital\",\n" +
            "    \"symbol\": \"Bs.\",\n" +
            "    \"symbolNative\": \"Bs.\"\n" +
            "  },\n" +
            "  \"VES\": {\n" +
            "    \"code\": \"VES\",\n" +
            "    \"name\": \"Venezuelan Bolívar Soberano\",\n" +
            "    \"symbol\": \"Bs.F\",\n" +
            "    \"symbolNative\": \"Bs.F\"\n" +
            "  },\n" +
            "  \"VND\": {\n" +
            "    \"code\": \"VND\",\n" +
            "    \"name\": \"Vietnamese Dong\",\n" +
            "    \"symbol\": \"₫\",\n" +
            "    \"symbolNative\": \"₫\"\n" +
            "  },\n" +
            "  \"VUV\": {\n" +
            "    \"code\": \"VUV\",\n" +
            "    \"name\": \"Vanuatu Vatu\",\n" +
            "    \"symbol\": \"VT\",\n" +
            "    \"symbolNative\": \"VT\"\n" +
            "  },\n" +
            "  \"WST\": {\n" +
            "    \"code\": \"WST\",\n" +
            "    \"name\": \"Samoan Tala\",\n" +
            "    \"symbol\": \"T\",\n" +
            "    \"symbolNative\": \"ST\"\n" +
            "  },\n" +
            "  \"XAF\": {\n" +
            "    \"code\": \"XAF\",\n" +
            "    \"name\": \"Central African CFA Franc BEAC\",\n" +
            "    \"symbol\": \"Fr\",\n" +
            "    \"symbolNative\": \"Fr.\"\n" +
            "  },\n" +
            "  \"XCD\": {\n" +
            "    \"code\": \"XCD\",\n" +
            "    \"name\": \"East Caribbean Dollar\",\n" +
            "    \"symbol\": \"$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"XOF\": {\n" +
            "    \"code\": \"XOF\",\n" +
            "    \"name\": \"West African CFA Franc BCEAO\",\n" +
            "    \"symbol\": \"₣\",\n" +
            "    \"symbolNative\": \"₣\"\n" +
            "  },\n" +
            "  \"XPF\": {\n" +
            "    \"code\": \"XPF\",\n" +
            "    \"name\": \"CFP Franc (Franc Pacifique)\",\n" +
            "    \"symbol\": \"₣\",\n" +
            "    \"symbolNative\": \"₣\"\n" +
            "  },\n" +
            "  \"YER\": {\n" +
            "    \"code\": \"YER\",\n" +
            "    \"name\": \"Yemeni Rial\",\n" +
            "    \"symbol\": \"YR\",\n" +
            "    \"symbolNative\": \"ر.ي.\"\n" +
            "  },\n" +
            "  \"ZAR\": {\n" +
            "    \"code\": \"ZAR\",\n" +
            "    \"name\": \"South African Rand\",\n" +
            "    \"symbol\": \"R\",\n" +
            "    \"symbolNative\": \"R\"\n" +
            "  },\n" +
            "  \"ZMW\": {\n" +
            "    \"code\": \"ZMW\",\n" +
            "    \"name\": \"Zambian Kwacha\",\n" +
            "    \"symbol\": \"ZK\",\n" +
            "    \"symbolNative\": \"ZK\"\n" +
            "  },\n" +
            "  \"ZWB\": {\n" +
            "    \"code\": \"ZWB\",\n" +
            "    \"name\": \"RTGS Dollar\",\n" +
            "    \"symbol\": \"\",\n" +
            "    \"symbolNative\": \"\"\n" +
            "  },\n" +
            "  \"ZWL\": {\n" +
            "    \"code\": \"ZWL\",\n" +
            "    \"name\": \"Zimbabwean Dollar\",\n" +
            "    \"symbol\": \"Z$\",\n" +
            "    \"symbolNative\": \"$\"\n" +
            "  },\n" +
            "  \"Abkhazia\": {\n" +
            "    \"code\": \"Abkhazia\",\n" +
            "    \"name\": \"Abkhazian Apsar\",\n" +
            "    \"symbol\": \"\",\n" +
            "    \"symbolNative\": \"\"\n" +
            "  },\n" +
            "  \"Artsakh\": {\n" +
            "    \"code\": \"Artsakh\",\n" +
            "    \"name\": \"Artsakh Dram\",\n" +
            "    \"symbol\": \"դր.\",\n" +
            "    \"symbolNative\": \"դր.\"\n" +
            "  }\n" +
            "}";
}
